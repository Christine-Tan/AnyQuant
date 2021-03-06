package bl.analyse;

import bl.service.GetStockService;
import model.analyse.ARBRresult;
import model.analyse.MACDResult;
import model.stock.StockVO;
import util.calculate.LinearRegression;
import util.calculate.MyHashItem;
import util.calculate.MySort;
import util.constant.SomeConstant;
import util.exception.BadInputException;
import util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JiachenWang on 2016/5/24.
 */
public class PredictBLImpl implements PredictBLService {

    private SingleStatisticBLService stasticbl;

    private GetStockService getStockService;

    private TechnicalAnalysisStrategy strategy;

    public PredictBLImpl(SingleStatisticBLService stasticbl, GetStockService getStockService) throws NotFoundException {
        this.stasticbl = stasticbl;
        this.strategy = new TechnicalAnalysis();
        this.getStockService = getStockService;
    }

    @Override
    public String getMACDInfo(String stock_num) throws BadInputException, NotFoundException {

        StockVO stock = this.getStockService.getLastestStock(
                stock_num, 150, SomeConstant.field.all, new ArrayList<>());

        MACDResult macdResult = strategy.calculateMACD(stock);
        List<MyHashItem> list = MySort.sortHashmapByKey(macdResult.getMacd());
        double[] arg1 = new double[list.size()];
        double[] arg2 = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arg1[i] = i + 1;
            arg2[2] = (double) list.get(i).getValue();
        }
        double gradient = LinearRegression.calculateLR_b(arg1, arg2, list.size());

        double dif_avg = 0;
        for (double value : macdResult.getDif().values())
            dif_avg += value;
        dif_avg = dif_avg / macdResult.getDif().size();

        double macd_avg = 0;
        for (double value : macdResult.getMacd().values())
            macd_avg += value;
        macd_avg = macd_avg / macdResult.getMacd().size();

        if (macd_avg * dif_avg <= 0)
            return "";

        String result = "结合150天有效数据，从MACD指数来看，";
        if (macd_avg > 0 && gradient > 0)
            result += "该股票行情行情处于多头行情中，可以买入开仓或多头持仓。";
        else if (macd_avg < 0 && gradient < 0)
            result += "该股票行情处于空头行情中，可以卖出开仓或观望。";
        else if (macd_avg > 0 && gradient < 0)
            result += "该股票行情处于下跌阶段，可以卖出开仓和观望。";
        else if (macd_avg < 0 && gradient > 0)
            result += "该股票行情即将上涨，可以买入开仓或多头持仓。";
        else if (gradient == 0)
            result += "该股票行情走势稳定。";

        return result;
    }

    @Override
    public String getVarianceInfo(String stock_num) throws BadInputException, NotFoundException {
        StockVO stock = this.getStockService.getLastestStock(
                stock_num, 15, SomeConstant.field.all, new ArrayList<>());

        double variance = stasticbl.getVarianceOfPrice(stock);
        String[] list = stasticbl.getAllVarianceOfPrice(stock.getStartDate(), stock.getEndDate()).split("-");
        double v1 = Double.parseDouble(list[0]);
        double v2 = Double.parseDouble(list[1]);
        double v3 = Double.parseDouble(list[2]);
        String result = "结合15天的有效数据，";
        if (variance <= v1)
            result += "从稳定程度看，该股票特别稳定，稳定程度超过系统中75%的股票。";
        else if (variance > v1 && variance <= v2)
            result += "从稳定程度看，该股票具有一定的稳定性，稳定程度超过系统中50%的股票。";
        else if (variance > v2 && variance <= v3)
            result += "从稳定程度看，该股票不是特别稳定，稳定程度仅仅超过系统中25%的股票。";
        else if (variance > v3)
            result += "从稳定程度看，该股票波动很大，具有一定的风险性。";

        return result;
    }

    @Override
    public String getArbrInfo(String stock_num) throws BadInputException, NotFoundException {

        //TODO
        ARBRresult arbr = null;

        Map<String, Double> arList = arbr.getAr();
        Map<String, Double> brList = arbr.getBr();

        double ar = 0;

        double[] arg1 = new double[arList.size()];
        double[] arg2 = arbr.getArArray();
        for (int i = 0; i < arList.size(); i++) {
            arg1[i] = i + 1;
        }
        double ar_b = LinearRegression.calculateLR_b(arg1, arg2, arList.size());

        arg2 = arbr.getBrArray();
        double br_b = LinearRegression.calculateLR_b(arg1, arg2, brList.size());

        String result = "根据当前AR指数，";
        if (ar <= 50)
            result += "股价已严重超卖，股价随时可能反弹，可逢低买入。";
        else if (ar >= 80 && ar <= 120)
            result += "盘整行情，股价走势平稳，出现大幅涨跌的概率比较低。";
        else if (ar >= 150)
            result += "股价已进入高价区，随时可能大幅回落下跌，应及时卖出股票。";

        result += "根据ARBR曲线走势，";
        if (ar_b>=0 && br_b>=0 && ar<150)
            result += "该股票的人气不断提升，投资者可以考虑及时买入。";
        else if (ar_b>=0 && br_b>=0 && ar>=150)
            result += "股价已经达到高位，持股者应注意及时获利了结。";
        else if (ar>80 && ar<120 && br_b<0)
            result += "建议股民逢低买入。";
        else if (ar_b<0 && br_b>=0)
            result += "建议股民逢高出货。";

        return result;
    }

}
