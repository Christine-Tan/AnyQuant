package bl.impl;

import bl.factory.BLFactory;
import bl.service.GetStockService;
import model.stock.StockVO;
import org.junit.Test;
import util.constant.StockConstant;
import util.exception.BadInputException;
import util.exception.NotFoundException;

/**
 * Created by kylin on 16/5/19.
 */
public class SingleViewImplTest {

    private GetStockService getStockService = new GetStockStub();

    private SingleViewImpl singleView
            = new SingleViewImpl(BLFactory.getInstance().getSingleStatisticBLService(),BLFactory.getInstance().getGetStockService());

    private StockVO stockVO= getStockService.getStock("sh600519","2015-01-1","2016-05-05", StockConstant.AllFields,null);

    public SingleViewImplTest() throws NotFoundException, BadInputException {
    }

    @Test
    public void getBasicSingleInfo() throws Exception {
        System.out.println(stockVO.numberOfDays());
        singleView.getBasicSingleInfo(stockVO,"2016-01-10","2016-05-05");
    }

    @Test
    public void getStockRSI() throws Exception {
        singleView.getStockRSI(stockVO);
    }

    @Test
    public void getStockEMA() throws Exception {
        singleView.getStockEMA(stockVO);
    }

    @Test
    public void getStockMACD() throws Exception {
        singleView.getStockMACD(stockVO);
    }

}