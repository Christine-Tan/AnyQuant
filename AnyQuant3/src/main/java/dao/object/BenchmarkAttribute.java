package dao.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by JiachenWang on 2016/5/13.
 */
@Entity
@Table(name = "benchmark_attribute", catalog = "anyquant")
public class BenchmarkAttribute implements Serializable {
    /**
     * 股票编号
     */
    private String id;
    /**
     * 日期
     */
    private String date;
    /**
     * 开盘价
     */
    private String open;
    /**
     * 最高价
     */
    private String high;
    /**
     * 最低价
     */
    private String low;
    /**
     * 收盘价
     */
    private String close;
    /**
     * 后复权价
     */
    private String adj_price;

    public BenchmarkAttribute() {
    }

    public BenchmarkAttribute(String id, String date) {
        this.id = id;
        this.date = date;
    }

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    @Id
    @Column(name = "date")
    public String getDate() {
        return date;
    }

    @Column(name = "high")
    public String getHigh() {
        return high;
    }

    @Column(name = "open")
    public String getOpen() {
        return open;
    }

    @Column(name = "low")
    public String getLow() {
        return low;
    }

    @Column(name = "close")
    public String getClose() {
        return close;
    }

    @Column(name = "adj_price")
    public String getAdj_price() {
        return adj_price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setAdj_price(String adj_price) {
        this.adj_price = adj_price;
    }

}
