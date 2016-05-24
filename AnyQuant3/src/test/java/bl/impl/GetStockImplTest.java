package bl.impl;

import bl.service.GetStockService;
import model.analyse.RiseAndFallVO;
import model.common.LinearChartVO;
import model.common.MyChartSeries;
import org.junit.Test;
import util.constant.StockConstant;
import util.exception.NotFoundException;

import java.util.List;

/**
 * Created by kylin on 16/5/19.
 */
public class GetStockImplTest {

    private GetStockService getStockService = new GetStockImpl();

    public GetStockImplTest() throws NotFoundException {
    }

    @Test
    public void initial() throws Exception {

    }

    @Test
    public void getIndustryName() throws Exception {
        getStockService.getIndustryName("sh600519");
    }

    @Test
    public void getStockName() throws Exception {
        getStockService.getStockName("sh600519");
    }

    @Test
    public void getStock() throws Exception {
        getStockService.getStock("sh600519","2016-01-01","2016-05-05", StockConstant.AllFields,null);
    }

    @Test
    public void getAmountInADayBarchart() throws Exception {
        getStockService.getAmountInADayBarchart("sh600519","2016-05-19");
    }

    @Test
    public void getLineChartVO() throws Exception {
        LinearChartVO linearChartVO = getStockService.getLineChartVO("sh600519","2016-05-19");
        List<MyChartSeries> chartSeries = linearChartVO.getChartSeries();
        System.out.println(chartSeries.get(0).getXyItem());
        System.out.println(chartSeries.get(1).getXyItem());

    }

    @Test
    public void testGetRiseAndFallList() {
        List<RiseAndFallVO> riseAndFallVOs = getStockService.getRiseAndFallList();
        for (RiseAndFallVO vo:riseAndFallVOs){
            System.out.println(vo.getRiseStr());
            System.out.println(vo.getName());
        }
    }
}