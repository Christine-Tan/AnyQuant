package data.mysql;

import dataservice.stockdataservice.APIDataService;
import dataservice.stockdataservice.MySqlDataService;
import po.StockBean;
import po.StockPO;
import tool.exception.NotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JiachenWang on 2016/4/25.
 */
public class StockDataImpl implements APIDataService {

    MySqlDataService mySqlDataService;

    public StockDataImpl(MySqlDataService mySqlDataService) {
        this.mySqlDataService = mySqlDataService;
    }

    @Override
    public List<String> getAllStock(String year, String exchange) throws IOException {
        return null;
    }

    @Override
    public StockPO getStock(String num, String start, String end, String fields) throws IOException, NotFoundException {
        List<StockBean> list = null;
        try {
            list = mySqlDataService.getStockBean(num, start, end);
        } catch (SQLException e) {
            System.err.println("数据库访问出错！");
            e.printStackTrace();
        }
        HashMap<String, HashMap<String, String>> infomation = new HashMap<>();
        for(StockBean bean : list){
            HashMap<String, String> arrt = new HashMap<>();
            for(String field : fields.split("\\+")){
                arrt.put(field, bean.getValue(field));
            }
            infomation.put(bean.getDate(), arrt);
        }
        StockPO result = new StockPO(num, infomation);
        result.setName("");//TODO
        return result;
    }

    @Override
    public List<String> getAllBenchmark() throws IOException {
        return null;
    }

    @Override
    public StockPO getBenchmark(String bench, String start, String end, String fields) throws IOException, NotFoundException {
        return null;
    }

    @Override
    public String getAllFields() throws IOException {
        return null;
    }
}
