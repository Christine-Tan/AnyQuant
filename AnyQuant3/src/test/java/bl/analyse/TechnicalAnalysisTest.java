package bl.analyse;

import bl.impl.GetStockStub;
import bl.service.GetStockService;
import model.analyse.MACDResult;
import model.common.LinearChartVO;
import model.stock.StockVO;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by kylin on 16/5/19.
 */
public class TechnicalAnalysisTest {

    private GetStockService getStock = new GetStockStub();

    private TechnicalAnalysisStrategy strategy = new TechnicalAnalysis();

    @Test
    public void calculateRSI() throws Exception {
        StockVO stockVO = this.getStock.getStock("sh600015","2016-01-01","2016-04-13",
                "open+close+high",null);
        HashMap<String, Double> hashMap = this.strategy.calculateRSI(stockVO,6);
        System.out.println(hashMap.size());
    }

    @Test
    public void calculateMACD() throws Exception {
        StockVO stockVO = this.getStock.getStock("sh600000", "2015-01-01", "2016-04-13",
                "open+close+high", null);
        MACDResult macdResult = this.strategy.calculateMACD(stockVO);
        HashMap<String, Double> hashMap = macdResult.getBarValue();
        System.out.println(hashMap.size());
    }

    @Test
    public void calculateEMA() throws Exception {
        StockVO stockVO = getStock.getStock("sh600015", "2015-01-01", "2016-04-13",
                "open+close+high", null);
        HashMap<String, Double> hashMap = this.strategy.calculateEMA(stockVO, 6);
        System.out.println(hashMap.size());
    }

    @Test
    public void calculateARBR() throws Exception {
        StockVO stockVO = getStock.getStock("sh600015", "2016-01-01", "2016-05-13",
                "open+close+high+low", null);
        LinearChartVO chartVO = this.strategy.calculateARBR(stockVO, 26);
        System.out.println(chartVO.getChartSeries().get(0).getXyItem().size());
    }
}