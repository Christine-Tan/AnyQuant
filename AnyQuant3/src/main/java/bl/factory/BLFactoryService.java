package bl.factory;

import bl.service.*;
import bl.analyse.SingleStatisticBLService;

/**
 * Created by kylin on 16/5/16.
 */
public interface BLFactoryService {

    SingleViewService getSingleViewService();

    IndustryViewService getIndustryViewService();

    SingleStatisticBLService getSingleStatisticBLService();

    GetStockService getGetStockService();

    BarChartService getBarChartService();

    PieChartService getPieChartService();
}
