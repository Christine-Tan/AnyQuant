package bl.factory;

import bl.analyse.SingleStatisticBLImpl;
import bl.analyse.SingleStatisticBLService;
import bl.impl.GetStockImpl;
import bl.impl.IndustryViewImpl;
import bl.impl.PieChartImpl;
import bl.impl.SingleViewImpl;
import bl.impl.barchart.BarChartImpl;
import bl.service.*;
import util.exception.NotFoundException;

import java.io.IOException;

/**
 * Created by kylin on 16/3/17.
 */
public class BLFactory implements BLFactoryService {

    private GetStockService getStockService;

    private SingleViewService singleViewService;

    private IndustryViewService industryViewService;

    private SingleStatisticBLService singleStatisticBLService;

    private BarChartService barChartService;

    private PieChartService pieChartService;

    private static BLFactory instance;

    private BLFactory() throws NotFoundException {
        getStockService = new GetStockImpl();

        singleStatisticBLService = new SingleStatisticBLImpl(getStockService);

        singleViewService = new SingleViewImpl(singleStatisticBLService, getStockService);
        industryViewService = new IndustryViewImpl(getStockService);

        barChartService = new BarChartImpl();
        pieChartService = new PieChartImpl();
    }

    public static BLFactory getInstance() throws NotFoundException {
        if (instance == null)
            instance = new BLFactory();
        return instance;
    }

    @Override
    public SingleViewService getSingleViewService() {
        return singleViewService;
    }

    @Override
    public IndustryViewService getIndustryViewService() {
        return industryViewService;
    }

    public GetStockService getGetStockService() {
        return getStockService;
    }

    @Override
    public SingleStatisticBLService getSingleStatisticBLService() {
        return singleStatisticBLService;
    }

    public BarChartService getBarChartService() {
        return barChartService;
    }

    public PieChartService getPieChartService() {
        return pieChartService;
    }
}
