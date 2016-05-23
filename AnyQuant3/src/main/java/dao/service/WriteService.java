package dao.service;

import dao.object.*;
import po.StockPO;
import po.TradeInfoPO;
import model.stock.ResultMsg;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public interface WriteService {
    /**
     * 插入新的行业
     * @param industry 行业持久化对象
     * @return 操作信息
     */
    ResultMsg insertIndustry(Industry industry);

    /**
     * 在列表中插入新的股票
     * @param stock 股票基本信息持久化对象
     * @return 操作信息
     */
    ResultMsg insertStock(StockBase stock);

//    /**
//     * 插入新的股市信息(股票)
//     * @param attribute 股票属性指数的集合
//     * @return 操作信息
//     */
//    ResultMsg insertStcokAttr(StockAttribute attribute);

    /**
     * 在更新股票数据
     * @param po 单股信息封装集合
     * @return 操作信息
     */
    ResultMsg insertStockAttrs(StockPO po);

//    /**
//     * 插入新的股市信息(大盘)
//     * @param attribute 股票属性指数的集合
//     * @return 操作信息
//     */
//    ResultMsg insertBenchmarkAttr(BenchmarkAttribute attribute);

    /**
     * 在更新大盘数据
     * @param po 单股信息封装集合
     * @return 操作信息
     */
    ResultMsg insertBenchmarkAttrs(StockPO po);

    /**
     * 更新分时图数据
     * @param po
     * @return 操作信息
     */
    ResultMsg insertTradeInfo(TradeInfoPO po);

//    /**
//     * 更新分时图数据一行
//     * @param trade
//     * @return 操作信息
//     */
//    ResultMsg insertTradeInfo(TradeDao trade);
}
