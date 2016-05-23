package dao;

import dao.impl.ReadImpl;
import dao.impl.WriteImpl;
import dao.service.WriteService;
import data.api.APIDataServiceUrlImplJson;
import data.dataservice.CacheDataService;
import data.dataservice.StockDataService;
import data.factory.DataFactory;
import po.TradeInfoPO;
import util.exception.NotFoundException;
import util.json.JsonReader;
import util.constant.StockConstant;
import util.exception.BadInputException;
import util.hibernate.HibernateUtils;
import util.time.DateCount;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public class RefreshSQL {

    public static void main(String[] args) throws NotFoundException, IOException, InterruptedException {
        RefreshSQL sql = new RefreshSQL();
        sql.refresh("2016-05-19");
    }

    public void refresh(String date) throws InterruptedException, NotFoundException, IOException {
//        this.refreshStock(date);
//        this.refreshBenchmark(date);
        this.refreshTrade(date);
        System.exit(1);
    }
    private void refreshStock(String date){
        ReadImpl read = new ReadImpl();
        HashMap<String, String> map = read.getStockNumAndName();

        APIDataServiceUrlImplJson api = new APIDataServiceUrlImplJson();
        JsonReader reader = new JsonReader(StockConstant.AllFields);
        WriteImpl write = new WriteImpl();

        for (String id : map.keySet()) {
            String json = null;
            try {
                json = api.getStockJson(id,date, DateCount.dateToStr(new Date()), StockConstant.AllFields);
                write.insertStockAttrs(reader.readStock(json));
            } catch (BadInputException e) {
                System.err.println("输入信息有误");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("访问出错");
                e.printStackTrace();
            }
        }

        HibernateUtils.closeFactory();
    }

    private void refreshBenchmark(String date){
        ReadImpl read = new ReadImpl();
        List<String> list = read.getAllBenchmark();

        APIDataServiceUrlImplJson api = new APIDataServiceUrlImplJson();
        JsonReader reader = new JsonReader(StockConstant.BenchFields);
        WriteImpl write = new WriteImpl();

        for (String id : list) {
            String json = null;
            try {
                json = api.getBenchmarkJson(id,date, DateCount.dateToStr(new Date()), StockConstant.BenchFields);
                write.insertBenchmarkAttrs(reader.readStock(json));
            } catch (BadInputException e) {
                System.err.println("输入信息有误");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("访问出错");
                e.printStackTrace();
            }
        }

        HibernateUtils.closeFactory();
    }

    private void refreshTrade(String date) throws InterruptedException, IOException, NotFoundException {
        //TODO,路径
//        Process proc = Runtime.getRuntime().exec("python  D:\\mysql.py");
//        proc.waitFor();

        CacheDataService cacheDataService =  DataFactory.getInstance().getCacheDataService();
        StockDataService stockDataService = DataFactory.getInstance().getStockDataService();
        WriteService writeService = new WriteImpl();

        HashMap<String, String> nums = stockDataService.getStockNumAndName();
        for (String num : nums.values()) {
            TradeInfoPO tradepo = cacheDataService.getLatestDayTradeInfo(num, date);
            writeService.insertTradeInfo(tradepo);
        }

        HibernateUtils.closeFactory();
    }

}
