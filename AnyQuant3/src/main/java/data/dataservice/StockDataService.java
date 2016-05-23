package data.dataservice;

import po.StockPO;
import po.TradeInfoPO;
import util.exception.NotFoundException;

import java.util.HashMap;

/**
 * Created by kylin on 16/3/7.
 */
public interface StockDataService {
	/**
	 * 返回数据库中股票编号和名称
	 * 
	 * @return
	 */
    HashMap<String,String> getStockNumAndName();

    /**
     * 返回行业
     * 股票编号,股票名字
     *
     * @return
     */
    HashMap<String, HashMap<String, String>> getNamesAndNames();

    /**
     * 返回指定股票代码的股票所有交易数据
     *
     * @param num 指定股票代码,如"sh600000"
     * @return 指定股票代码的所有股票交易数据
     */
    StockPO getStock(String num) throws NotFoundException;


    /**
     * 获取指定大盘指数的数据
     *
     * @param bench 大盘指数编号
     * @return 指定大盘指数的所有数据
     */
    StockPO getBenchmark(String bench) throws NotFoundException;

    /**
     * 返回指定股票代码的一个工作日的详细交易情况
     * @param number 股票编号
     * @param time 日期
     * @return 一个工作日的详细交易情况
     * @throws NotFoundException
     */
    TradeInfoPO getTradeInfo(String number, String time) throws NotFoundException;


    /**
     *  获得饼图信息（时间段内不同交易类型的交易量）
     * @param number 股票编号
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    HashMap<String, Double> getPieVolumeInfo(String number, String start, String end);

    /**
     *  获得饼图信息（时间段内不同交易类型的交易金额）
     * @param number 股票编号
     * @param start 开始日期
     * @param end 结束日期
     * @return
     */
    HashMap<String, Double> getPieAmountInfo(String number, String start, String end);
}
