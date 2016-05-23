package bl.statisticbl;

import bl.stockviewbl.StockViewImpl;
import blservice.statisticsblservice.SingleStatisticBLService;
import blservice.stockviewblservice.StockViewService;
import org.junit.Test;
import tool.constant.R;
import tool.enums.PeriodEnum;
import tool.exception.NotFoundException;
import vo.analyse.MACDResult;
import vo.chart.common.StockVO;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by JiachenWang on 2016/4/11.
 */
public class SingleStatisticTest {
//
//    private SingleStatisticBLService singleBL;
//    private StockViewService stockViewService;
//
//    public SingleStatisticTest() throws NotFoundException {
//        stockViewService = new StockViewImpl();
//        singleBL = new SingleStatisticBLImpl(stockViewService);
//    }
//
//    @Test
//    public void testMACDValue() throws Exception {
//        MACDResult result = singleBL.getMACDValue("航天机电", "sh600151", PeriodEnum.DAY,"2015-03-01","2016-04-20");
//        System.out.println("dif");
//        Set<Map.Entry<String, Double>> vo_datas = result.getDif().entrySet();
//        for (Map.Entry<String, Double> vo_data : vo_datas) {
//            System.out.println(vo_data.getKey() + " + " + vo_data.getValue());
//        }
//        System.out.println("macd");
//        vo_datas = result.getMacd().entrySet();
//        for (Map.Entry<String, Double> vo_data : vo_datas) {
//            System.out.println(vo_data.getKey() + " + " + vo_data.getValue());
//        }
//        System.out.println("barValue");
//        vo_datas = result.getBarValue().entrySet();
//        for (Map.Entry<String, Double> vo_data : vo_datas) {
//            System.out.println(vo_data.getKey() + " + " + vo_data.getValue());
//        }
//    }
//
//    @Test
//    public void testVariance() throws Exception {
//        StockVO stock = stockViewService.getStock("sh600151", "2016-03-20", "2016-03-29", R.field.all, new ArrayList<>());
//        System.out.println(singleBL.getVarianceOfPrice(stock));
//    }
//
//    @Test
//    public void testAvgPrice() throws Exception {
//        StockVO stock = stockViewService.getStock("sh600151", "2016-03-20", "2016-03-29", R.field.all, new ArrayList<>());
//        System.out.println(singleBL.getAvgPrice(stock));
//    }
//
//    @Test
//    public void testAllVariance() throws Exception {
////        System.out.println(singleBL.getAllVarianceOfPrice("2016-03-20", "2016-03-29"));
//    }
}
