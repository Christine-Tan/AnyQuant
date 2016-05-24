package util.json;

import bl.factory.BLFactory;
import bl.impl.GetStockStub;
import bl.service.GetStockService;
import bl.service.IndustryViewService;
import bl.service.SingleViewService;
import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.common.PieChartVO;
import model.stock.StockNumber;
import model.stock.StockVO;
import org.junit.Test;
import util.constant.StockConstant;
import util.enums.IndustryPeriodEnum;
import util.exception.NotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 16/5/19.
 */
public class JsonConverterTest {

    private GetStockService getStockService = new GetStockStub();

    private SingleViewService singleViewService = BLFactory.getInstance().getSingleViewService();

    private IndustryViewService industryViewService = BLFactory.getInstance().getIndustryViewService();

    public JsonConverterTest() throws IOException, NotFoundException {
    }

    @Test
    public void jsonOfObject() throws Exception {
//        StockVO stockVO = getStockService.getStock(
//                "sh600519","2016-01-01","2016-05-19", StockConstant.AllFields,null);
//        System.out.println(JsonConverter.jsonOfObject(stockVO));
        System.out.println(JsonConverter.jsonOfObject(new StockNumber("sh5555")));
    }

    @Test
    public void jsonOfLinearChartVO() throws Exception {
        StockVO stockVO = getStockService.getStock(
                "sh600519","2016-01-01","2016-05-19", StockConstant.AllFields,null);
        LinearChartVO linearChartVO = singleViewService.getStockRSI(stockVO);

//        getStockService = BLFactory.getInstance().getGetStockService();
//        LinearChartVO linearChartVO = getStockService.getLineChartVO("sh600519","2016-05-19");
        System.out.println(JsonConverter.jsonOfLinearChartVO(linearChartVO));
    }

    @Test
    public void jsonOfIndustryVolumeVO() throws Exception {
        VolumeVO industryVolumeData = industryViewService.getIndustryVolume("酒业", IndustryPeriodEnum.FOURTH);
        System.out.println(JsonConverter.jsonOfVolumeVO(industryVolumeData));
    }

    @Test
    public void jsonKlinearOfVO() throws Exception {
        StockVO stockVO = getStockService.getStock(
                "sh600519","2016-01-01","2016-05-19", StockConstant.AllFields,null);
        System.out.println(JsonConverter.jsonKlinearOfVO(stockVO));
    }

    @Test
    public void convertPieVO() throws Exception {
        Map<String,Double> map = new HashMap<>();
        map.put("1",1.1);
        map.put("2",2.2);
        map.put("3",3.3);
        PieChartVO pieChartVO = new PieChartVO("test",map);
        List<String> re = JsonConverter.convertPieVO(pieChartVO);
        System.out.println(re);
    }

}