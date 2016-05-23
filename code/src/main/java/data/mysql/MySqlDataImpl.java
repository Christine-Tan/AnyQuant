package data.mysql;

import dataservice.stockdataservice.MySqlDataService;
import po.StockBean;
import tool.utility.DateCount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiachenWang on 2016/4/25.
 */
public class MySqlDataImpl implements MySqlDataService {

    private Connection conn = null;

    public MySqlDataImpl() {
        super();
        conn = ConnectHelper.getConn();
    }

    @Override
    public List<StockBean> getStockBean(String stock_num, String start, String end) throws SQLException {
        List<StockBean> list = new ArrayList<>();
        for(String date : DateCount.splitDays(start,end)) {
            String select = "select * from `attribute` where ID = ? and date = ?";
            PreparedStatement statement=conn.prepareStatement(select);
            statement.setString(1, stock_num);
            statement.setString(2,date);
            ResultSet rs = null;
            rs = statement.executeQuery();
//            String select = "select * from `attribute` where ID = '" + stock_num + "' and date = '" + date + "'";
//            PreparedStatement statement=conn.prepareStatement(select);
//            ResultSet rs = null;
//            rs = statement.executeQuery(select);
            rs.next();
            if(rs == null) {
                continue;
            }
            StockBean bean = new StockBean();
            bean.setID(rs.getString("ID"));
            bean.setDate(rs.getString("date"));
            bean.setOpen(rs.getString("open"));
            bean.setHigh(rs.getString("high"));
            bean.setLow(rs.getString("low"));
            bean.setClose(rs.getString("close"));
            bean.setAdj_price(rs.getString("adj_price"));
            bean.setVolume(rs.getString("volume"));
            bean.setTurnover(rs.getString("turnover"));
            bean.setPe_ttm(rs.getString("pe_ttm"));
            bean.setPb(rs.getString("pb"));
            list.add(bean);
        }
        return list;
    }
}
