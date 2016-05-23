package model.stock;

import bl.factory.BLFactory;
import bl.impl.GetStockImpl;
import bl.service.GetStockService;
import org.junit.Test;
import util.constant.StockConstant;
import util.exception.BadInputException;
import util.exception.NotFoundException;

import java.util.List;

/**
 * Created by kylin on 16/5/21.
 */
public class StockVOTest {

    private GetStockService getStockService = new GetStockImpl();

    public StockVOTest() throws NotFoundException {
    }

    @Test
    public void getAttributes()  {
        StockVO stockVOBig = null;
        try {
            stockVOBig = getStockService.getStock("sh600519","2016-01-01","2016-05-05", StockConstant.AllFields,null);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (BadInputException e) {
            e.printStackTrace();
        }
        List<StockAttribute> samll = null;
        try {
            samll = stockVOBig.getAttributes("2016-03-03","2016-04-04");
            System.out.println(samll.size());
        } catch (BadInputException e) {
            e.printStackTrace();
        }

    }

}