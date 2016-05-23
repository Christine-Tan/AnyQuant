package dao.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by JiachenWang on 2016/5/19.
 */

@Entity
@Table(name = "trade", catalog = "anyquant")
public class TradeDao implements Serializable {
    /**
     * 股票编号
     */
    private String id;
    /**
     * 日期
     */
    private String date;

    /**
     * 交易时间形式15:00:00
     */
    private String time;

    /**
     * 涨跌幅
     */
    private String price;

    /**
     * 价格变化-0.01 or 0.0
     */
    private String change;

    /**
     * 成交手
     */
    private String volume;

    /**
     * 成交金额(元)
     */
    private String amount;

    /**
     * 买卖类型【买盘、卖盘、中性盘】
     */
    private String type;

    public TradeDao() {
    }

    @Id
    @Column(name="ID")
    public String getId() {
        return id;
    }

    @Id
    @Column(name="date")
    public String getDate() {
        return date;
    }
    @Id
    @Column(name="time")
    public String getTime() {
        return time;
    }

    @Column(name="type")
    public String getType() {
        return type;
    }

    @Column(name="amount")
    public String getAmount() {
        return amount;
    }

    @Column(name="volume")
    public String getVolume() {
        return volume;
    }

    @Column(name="stock_change")
    public String getChange() {
        return change;
    }

    @Column(name="price")
    public String getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }
}
