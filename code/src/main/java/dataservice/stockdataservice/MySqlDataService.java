package dataservice.stockdataservice;

import po.StockBean;
import po.StockPO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by JiachenWang on 2016/4/25.
 */
public interface MySqlDataService {
    /**
     * 获取某股票某天的参数封装集合
     * @param stock_num 股票编号
     * @param start 开始日期
     * @param end 截至日期
     * @return
     */
    public List<StockBean> getStockBean(String stock_num, String start, String end) throws SQLException;
}
