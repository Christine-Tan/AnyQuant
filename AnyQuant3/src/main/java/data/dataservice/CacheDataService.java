package data.dataservice;

import po.StockPO;
import po.TradeInfoPO;
import util.constant.StockConstant;
import util.exception.NotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kylin on 16/3/7.
 */
public interface CacheDataService {


    /**
     * 返回指定股票代码的一个工作日的详细交易情况
     *
     * @param number
     * @return
     */
    TradeInfoPO getLatestDayTradeInfo(String number, String time) throws NotFoundException, IOException;

}
