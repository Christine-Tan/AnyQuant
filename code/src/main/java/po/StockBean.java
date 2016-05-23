package po;

/**
 * Created by JiachenWang on 2016/4/25.
 */
public class StockBean {
    String ID;//股票ID
    String date;// 日期
    String open;//开盘价
    String high;//  最高价
    String low;//最低价
    String close;//收盘价
    String adj_price;// 后复权价
    String volume;//  成交量
    String turnover;//换手率
    String pe_ttm;//市盈率
    String pb;//市净率

    public StockBean() {
    }

    public String getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getAdj_price() {
        return adj_price;
    }

    public String getVolume() {
        return volume;
    }

    public String getTurnover() {
        return turnover;
    }

    public String getPe_ttm() {
        return pe_ttm;
    }

    public String getPb() {
        return pb;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setOpen(String open) {
        this.open = open;
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

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setPe_ttm(String pe_ttm) {
        this.pe_ttm = pe_ttm;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public void setPb(String pb) {
        this.pb = pb;
    }

    public String getValue(String field) {
        this.pb = pb;//TODO
        return null;
    }
}
