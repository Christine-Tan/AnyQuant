package data.impl;

import dao.impl.ReadImpl;
import dao.object.BenchmarkAttribute;
import dao.object.StockAttribute;
import dao.object.TradeDao;
import dao.service.ReadService;
import dao.tool.BenchmarkHelper;
import dao.tool.StockHelper;
import data.dataservice.StockDataService;
import po.StockPO;
import po.TradeInfoLine;
import po.TradeInfoPO;
import util.exception.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public class StockDataImpl implements StockDataService {

    private HashMap<String, String> numAndName;
    private HashMap<String, HashMap<String, String>> namesAndNames;
    private ReadService readservice;

    public StockDataImpl() {
        readservice = new ReadImpl();
        numAndName = readservice.getStockNumAndName();
        namesAndNames = readservice.getNamesAndNames();
    }

    @Override
    public HashMap<String, String> getStockNumAndName() {
        return numAndName;
    }

    @Override
    public HashMap<String, HashMap<String, String>> getNamesAndNames() {
        return namesAndNames;
    }

    @Override
    public StockPO getStock(String num) throws NotFoundException {
        List<StockAttribute> list = readservice.getStock(num);
        HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
        for (StockAttribute attr : list) {
            map.put(attr.getDate(), StockHelper.getAttribute(attr));
        }
        StockPO po = new StockPO(num, map);
        //获取股票名字
        String name = "";
        for (Map.Entry<String, String> entry : this.numAndName.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(num))
                name =  entry.getKey();
        }
        po.setName(name);
        return po;
    }

    @Override
    public StockPO getBenchmark(String bench) throws NotFoundException {
        List<BenchmarkAttribute> list = readservice.getBenchmark(bench);
        HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
        for (BenchmarkAttribute attr : list) {
            map.put(attr.getDate(), BenchmarkHelper.getAttribute(attr));
        }
        StockPO po = new StockPO(bench, map);
        po.setName("大盘");
        return po;
    }

    @Override
    public TradeInfoPO getTradeInfo(String number, String date) throws NotFoundException {
        List<TradeDao> list = readservice.getTrade(number, date);
        HashMap<String, TradeInfoLine> info = new HashMap<String,TradeInfoLine>();
        for (TradeDao dao : list){
            info.put(dao.getTime(), new TradeInfoLine(dao));
        }
        return new TradeInfoPO(info, number, date);
    }

    @Override
    public HashMap<String, Double> getPieVolumeInfo(String number, String start, String end) {
        return readservice.getPieVolumeInfo(number, start, end);
    }

    @Override
    public HashMap<String, Double> getPieAmountInfo(String number, String start, String end) {
        return readservice.getPieAmountInfo(number, start, end);
    }

}
