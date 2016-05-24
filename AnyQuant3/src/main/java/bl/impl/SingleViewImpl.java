package bl.impl;

import bl.service.GetStockService;
import bl.service.SingleViewService;
import bl.analyse.SingleStatisticBLService;
import bl.analyse.TechnicalAnalysis;
import bl.analyse.TechnicalAnalysisStrategy;
import model.analyse.ARBRresult;
import model.stock.StockAttribute;
import model.stock.StockVO;
import util.calculate.LinearRegression;
import util.calculate.MyHashItem;
import util.calculate.MySort;
import util.constant.SomeConstant;
import util.enums.LinearChartType;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.time.DateCount;
import model.common.LinearChartVO;
import model.common.MyChartSeries;
import model.stock.BasicSingleVO;
import model.analyse.MACDResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JiachenWang on 2016/3/23.
 */
public class SingleViewImpl implements SingleViewService {

    private SingleStatisticBLService stasticbl;

    private GetStockService getStockService;

    private TechnicalAnalysisStrategy strategy;

    public SingleViewImpl(SingleStatisticBLService stasticbl, GetStockService getStockService) throws NotFoundException {
        this.stasticbl = stasticbl;
        this.strategy = new TechnicalAnalysis();
        this.getStockService = getStockService;
    }

    @Override
    public BasicSingleVO getBasicSingleInfo(StockVO stockVO,String startDate,String endDate)
            throws BadInputException, NotFoundException {

        BasicSingleVO basicSingleVO = new BasicSingleVO();
        basicSingleVO.setStock_name(stockVO.getName());
        basicSingleVO.setStock_num(stockVO.getNumber());


        StockVO stock_macd = this.getStockService.getStock(
                stockVO.getNumber(), DateCount.count(endDate, -150),
                endDate, SomeConstant.field.all, new ArrayList<>());

        basicSingleVO.setConclusion(
                getPredict(
                stasticbl.getAllVarianceOfPrice(startDate, endDate), stock_macd));

        //计算各种指标

        //心理指数
        int dayUp = 0;
        int days = stockVO.numberOfDays();
        for (int j = 0; j < days; j++) {
            double change = stockVO.changeAtDay(j);
            if (change > 0)
                dayUp += 1;
        }
        basicSingleVO.setPsychologicalValue(dayUp + 0.0 / days * 100);

        List<StockAttribute> stockAttributeList = stockVO.getAttributes();
        double max = -1;
        double min = 1000;
        double dayOne = stockVO.priceAtDay(0);
        double yesterdayPrice = dayOne;

        double sumOfVar = 0;
        double sumOfTurnover = 0;
        double sumOfPe_ttm = 0;
        double sumOfPb = 0;
        for (StockAttribute stockAttribute : stockAttributeList) {
            double close = Double.parseDouble(stockAttribute.getAttribute("close"));
            //涨跌幅 = 所有天数涨跌幅的平均值
            double var = (close - yesterdayPrice) / yesterdayPrice * 100;
            sumOfVar += var;
            yesterdayPrice = close;

            //股票平均换手率
            double turnover = Double.parseDouble(stockAttribute.getAttribute("turnover"));
            sumOfTurnover += turnover;

            //股票平均市盈率
            double pe_ttm = Double.parseDouble(stockAttribute.getAttribute("pe_ttm"));
            sumOfPe_ttm += pe_ttm;

            double pb = Double.parseDouble(stockAttribute.getAttribute("pb"));
            sumOfPb += pb;

            //统计最大最小值
            if (close > max)
                max = close;
            if (close < min)
                min = close;
        }
        //振幅:以本周期的最高价与最低价的差，除以上一周期的收盘价，再以百分数表示的数值。
        double rise = max - min;
        basicSingleVO.setVariableRange(rise / dayOne * 100);

        //涨跌幅 = 所有天数涨跌幅的平均值
        double varEven = sumOfVar / days;
        basicSingleVO.setRiseAndFall(varEven);


        basicSingleVO.setAvgTurnover(sumOfTurnover / days);

        basicSingleVO.setAvgPe(sumOfPe_ttm / days);

        basicSingleVO.setAvgPb(sumOfPb / days);

        return basicSingleVO;
    }

    @Override
    public LinearChartVO getStockRSI(StockVO stockVO) throws BadInputException, NotFoundException {

        HashMap<String, Double> EMA6 = strategy.calculateRSI(stockVO, 6);
        HashMap<String, Double> EMA12 = strategy.calculateRSI(stockVO, 12);
        HashMap<String, Double> EMA50 = strategy.calculateRSI(stockVO, 25);

        MyChartSeries series1 = new MyChartSeries("6日指标", EMA6);
        MyChartSeries series2 = new MyChartSeries("12日指标", EMA12);
        MyChartSeries series3 = new MyChartSeries("24日指标", EMA50);

        List<MyChartSeries> myChartSeries = new ArrayList<>();
        myChartSeries.add(series1);
        myChartSeries.add(series2);
        myChartSeries.add(series3);

        return new LinearChartVO(myChartSeries, LinearChartType.RSI);
    }

    @Override
    public LinearChartVO getStockEMA(StockVO stockVO) throws BadInputException, NotFoundException {

        HashMap<String, Double> EMA6 = strategy.calculateEMA(stockVO, 6);
        HashMap<String, Double> EMA12 = strategy.calculateEMA(stockVO, 12);
        HashMap<String, Double> EMA50 = strategy.calculateEMA(stockVO, 35);

        MyChartSeries ema6s = new MyChartSeries("6日指数", EMA6);
        MyChartSeries ema12s = new MyChartSeries("12日指数", EMA12);
        MyChartSeries ema50s = new MyChartSeries("35日指数", EMA50);

        List<MyChartSeries> myChartSeries = new ArrayList<>();
        myChartSeries.add(ema6s);
        myChartSeries.add(ema12s);
        myChartSeries.add(ema50s);

        return new LinearChartVO(myChartSeries, LinearChartType.EMA);
    }

    @Override
    public LinearChartVO getStockMACD(StockVO stockVO) throws BadInputException, NotFoundException {

        MACDResult macdResult = strategy.calculateMACD(stockVO);

        MyChartSeries series1 = new MyChartSeries("MACD指数", macdResult.getMacd());
        MyChartSeries series2 = new MyChartSeries("DIF指数", macdResult.getDif());

        MyChartSeries series3 = new MyChartSeries("柱状图属性", macdResult.getBarValue());

        List<MyChartSeries> myChartSeries = new ArrayList<>();
        myChartSeries.add(series1);
        myChartSeries.add(series2);

        return new LinearChartVO(myChartSeries, LinearChartType.MACD);

    }

    @Override
    public LinearChartVO getStockARBR(StockVO stockVO) throws BadInputException, NotFoundException {
        ARBRresult arbRresult = strategy.calculateARBR(stockVO,26);

        Map<String, Double> ar = arbRresult.getAr();
        Map<String, Double> br = arbRresult.getBr();

        MyChartSeries series1 = new MyChartSeries("AR指标", ar);
        MyChartSeries series2 = new MyChartSeries("BR指标", br);

        List<MyChartSeries> myChartSeries = new ArrayList<>();
        myChartSeries.add(series1);
        myChartSeries.add(series2);

        return new LinearChartVO(myChartSeries, LinearChartType.RSI);
    }

    private String getPredict(String variance_three, StockVO stock_macd) throws BadInputException {
        String result = "通过对" + stock_macd.getName() + "历史数据的分析，可以得到一些结论。";

        result += this.getMACDInfo(stock_macd);

        double variance = stasticbl.getVarianceOfPrice(stock_macd);
        String[] list = variance_three.split("-");
        double v1 = Double.parseDouble(list[0]);
        double v2 = Double.parseDouble(list[1]);
        double v3 = Double.parseDouble(list[2]);
        result += "\n" + "        " + "结合半个月的数据，";
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

    private String getMACDInfo(StockVO stock) throws BadInputException {
        String result = "";
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

//        System.out.println("fff" + gradient);
        result += "\n" + "        " + "结合近半年的数据，从MACD指数来看，";
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

}

