package data.download;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kylin on 16/5/7.
 */
public class APIDataServiceUrlImplJsonTest {

    private APIDataServiceUrlImplJson json = new APIDataServiceUrlImplJson();

    @Test
    public void getAllStockJson() throws Exception {
        json.getAllStockJson("2015","sh");
        json.getAllStockJson("2015","sz");
    }

    @Test
    public void testGetStockJson() throws Exception {
        assertEquals(json.getStockJson("sh600015","2015-01-01","2016-05-03","open").length() > 100,true);
        assertEquals(json.getStockJson("sh600015","2015-01-03","2016-05-03","open").length() > 100,true);
    }

    @Test
    public void getAllBenchmarkJson() throws Exception {
        json.getAllBenchmarkJson();
    }

    @Test
    public void getBenchmarkJson() throws Exception {
        json.getBenchmarkJson("sh300","2015-01-01","2016-05-03","open");
        json.getBenchmarkJson("sh300","2013-01-01","2016-05-03","open+close");
        json.getBenchmarkJson("300","2013-01-01","2016-05-03","open+close");
    }

    @Test
    public void getAllFieldsJson() throws Exception {
        json.getAllFieldsJson();
    }

}