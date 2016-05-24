package bl.impl;

import bl.service.GetStockService;
import data.dataservice.StockDataService;
import data.impl.stub.StockDataStub;
import model.analyse.RiseAndFallVO;
import model.barchart.VolumeChartVO;
import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.common.MyChartSeries;
import model.stock.StockVO;
import po.StockPO;
import model.stock.ConditionSelect;
import util.constant.StockConstant;
import util.enums.LinearChartType;
import util.enums.PeriodEnum;
import util.enums.TypeOfVolumn;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.time.DateCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kylin on 16/5/20.
 */
public class GetStockStub implements GetStockService {

    private StockDataService stockDataService = new StockDataStub();

    @Override
    public String getIndustryName(String num) throws NotFoundException {
        return "就业";
    }

    @Override
    public String getStockName(String num) throws NotFoundException {
        return "青岛啤酒";
    }

    @Override
    public StockVO getStock(String num, String start, String end, String fields, List<ConditionSelect> ranges) throws NotFoundException, BadInputException {
        StockPO po = stockDataService.getStock(num);
        return new StockVO(po,start,end,fields);
    }

    @Override
    public StockVO getLastestStock(String num, int numOfDays, String fields, List<ConditionSelect> ranges) throws NotFoundException, BadInputException {
        StockPO po = stockDataService.getStock(num);
        return new StockVO(po, "2016-04-22", "2016-05-22",fields);
    }

    @Override
    public StockVO getStock(String name, String start, String end) throws NotFoundException, BadInputException {
        StockPO po = stockDataService.getStock(name);
        return new StockVO(po,start,end, StockConstant.AllFields);
    }

    @Override
    public LinearChartVO getLineChartVO(String number, String date) throws NotFoundException {
        List<MyChartSeries> chartSeries = new ArrayList<>();
        HashMap<String,Double> xyItem  = new HashMap<>();
        String strToday = date;
        for (int i = 1; i <= 150; i++) {
            strToday = DateCount.count(strToday,-1);
            xyItem.put(strToday,i+0.1);
        }
        chartSeries.add(new MyChartSeries("stub数据列",xyItem));
        return new LinearChartVO(chartSeries, LinearChartType.INDUSTRY);
    }

    @Override
    public VolumeVO getAmountInADayBarchart(String number, String date) throws NotFoundException {
        ArrayList<VolumeChartVO> list = new ArrayList<>();
        String strToday = date;
        for (int i = 1; i <= 150; i++) {
            strToday = DateCount.count(strToday,-1);
            list.add(new VolumeChartVO(PeriodEnum.DAY,strToday,i+0.5));
        }
        return new VolumeVO(list, TypeOfVolumn.SINGLE);
    }

    @Override
    public List<RiseAndFallVO> getRiseAndFallList() {
        return null;
    }
}
