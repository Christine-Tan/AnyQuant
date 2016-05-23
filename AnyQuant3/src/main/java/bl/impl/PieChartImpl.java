package bl.impl;


import bl.service.PieChartService;
import data.dataservice.StockDataService;
import data.factory.DataFactory;
import model.common.PieChartVO;
import po.TradeInfoPO;
import util.exception.NotFoundException;
import util.time.DateCount;

import java.util.*;

/**
 * Created by JiachenWang on 2016/4/10.
 */

public class PieChartImpl implements PieChartService {

    private StockDataService stockDataService;

    public PieChartImpl() {
        stockDataService = DataFactory.getInstance().getStockDataService();
    }

    @Override
    public PieChartVO getPieVolumeVO(String name, String number, String start, String end){

        return new PieChartVO("交易量(单位:手)", stockDataService.getPieVolumeInfo(number, start, end));
    }

    @Override
    public PieChartVO getPieAmountVO(String name, String number, String start, String end) {
        return new PieChartVO("交易金额(单位:百万)", stockDataService.getPieAmountInfo(number, start, end));
    }

}
