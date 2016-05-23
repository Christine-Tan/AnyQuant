package bl.factory;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by kylin on 16/5/19.
 */
public class BLFactoryTest {

    private BLFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = BLFactory.getInstance();
    }

    @Test
    public void getInstance() throws Exception {
        factory = BLFactory.getInstance();
    }

    @Test
    public void getSingleViewService() throws Exception {
        factory.getSingleViewService();
    }

    @Test
    public void getIndustryViewService() throws Exception {
        factory.getIndustryViewService();
    }

    @Test
    public void getGetStockService() throws Exception {
        factory.getGetStockService();
    }

    @Test
    public void getSingleStatisticBLService() throws Exception {
        factory.getSingleStatisticBLService();
    }

    @Test
    public void getBarChartService() throws Exception {
        factory.getBarChartService();
    }

    @Test
    public void getPieChartService() throws Exception {
        factory.getPieChartService();
    }

}