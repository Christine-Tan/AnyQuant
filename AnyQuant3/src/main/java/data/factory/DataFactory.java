package data.factory;

import data.dataservice.CacheDataService;
import data.dataservice.StockDataService;
import data.impl.CacheDataTxtImpl;
import data.impl.StockDataImpl;
import data.impl.stub.StockDataStub;

import java.io.IOException;

/**
 * Created by JiachenWang on 2016/5/16.
 */
public class DataFactory{
    /**
     * 数据实现的提供工厂,单件模式
     * volatile确保实例被初始化的时候,多个线程正确处理实例变量
     */
    private volatile static DataFactory dataFactory;

    private StockDataService stockDataService;
    private CacheDataService cacheDataService;

    private DataFactory()  {
        //TODO
        //测试用
        stockDataService = new StockDataStub();
        try {
            cacheDataService = new CacheDataTxtImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataFactory getInstance(){
        if (dataFactory == null) {
            //如果实例没有被创建,进行同步,只有第一次同步加锁
            synchronized (DataFactory.class) {
                if (dataFactory == null)
                    dataFactory = new DataFactory();
            }
        }
        return dataFactory;
    }

    public StockDataService getStockDataService() {
        return stockDataService;
    }

    public CacheDataService getCacheDataService() {
        return cacheDataService;
    }
}