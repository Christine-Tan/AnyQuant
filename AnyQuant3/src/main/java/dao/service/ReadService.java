package dao.service;

import dao.object.BenchmarkAttribute;
import dao.object.StockAttribute;
import dao.object.TradeDao;
import util.exception.NotFoundException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public interface ReadService {

    /**
     * 返回数据库中股票编号和名称
     *
     * @return
     */
    HashMap<String, String> getStockNumAndName();

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
     * @return 属性集合
     */
    List<StockAttribute> getStock(String num) throws NotFoundException;

    /**
     * 获取所有可用的benchmark，大盘指数，目前只有沪深300指数
     *
     * @return 沪深300指数
     */
    List<String> getAllBenchmark();

    /**
     * 获取指定大盘指数的数据
     *
     * @param bench 大盘指数编号
     * @return 指定大盘指数的属性集合
     */
    List<BenchmarkAttribute> getBenchmark(String bench) throws NotFoundException;

    /**
     *  获得分时图数据
     * @param num 股票编号
     * @param date 日期
     * @return
     * @throws NotFoundException
     */
    List<TradeDao> getTrade(String num, String date) throws NotFoundException;

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
