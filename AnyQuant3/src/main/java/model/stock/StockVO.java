package model.stock;

import model.analyse.ComputableStock;
import po.StockPO;
import util.exception.BadInputException;
import util.time.DateCount;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by kylin on 16/3/4. Modified by seven on 16/3/5
 */
public class StockVO implements ComputableStock {
    /**
     * 股票编号
     */
    private String number;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 数据的起始日期
     */
    private String startDate;

    private String endDate;

    /**
     * 股票属性 (日期 --> (指标名称,指标数据))
     */
    private List<StockAttribute> attributes;

    /**
     * 股票属性是否按照日期排过序
     */
    private boolean sorted;

    /**
     * 根据获得的数据构造StockVO
     *
     * @param number
     * @param name
     * @param attributes
     */
    public StockVO(String number, String name, List<StockAttribute> attributes) {
        this.number = number;
        this.name = name;
        this.attributes = attributes;
        this.sortAttr();
        this.startDate = attributes.get(0).getDate();
        this.endDate = attributes.get(attributes.size() - 1).getDate();
    }

    /**
     * 根据PO构造
     *
     * @param po
     * @param beginDate
     * @param endDate
     * @param fields
     */
    public StockVO(StockPO po, String beginDate, String endDate, String fields) {
        List<StockAttribute> atts = new ArrayList<StockAttribute>();
        HashMap<String, HashMap<String, String>> info = new HashMap<String, HashMap<String, String>>();
        if (beginDate.compareTo(endDate) <= 0) {
            info = po.getInfomation();
            // 选择的数据域
            String[] field = fields.split("\\+");

            // 获得筛选的日期取出对应的信息
            List<String> days = DateCount.splitDays(beginDate, endDate);
            for (int i = 0; i < days.size(); i++) {
                String day = days.get(i);
                HashMap<String, String> allatts = info.get(day);
                // 获得有效的日期的属性
                if (allatts == null) {
                    continue;
                }
                HashMap<String, String> temp = new HashMap<String, String>();
                // 遍历筛选的字段，获得属性
                for (int j = 0; j < field.length; j++) {
                    String key = field[j];
                    String value = allatts.get(key);
                    if (value != null) {
                        double d = Double.parseDouble(value);
                        DecimalFormat df = new DecimalFormat("");
                        if (key.equals("volume")) {
                            df = new DecimalFormat("0");
                        } else {
                            df = new DecimalFormat("0.00");
                        }

                        value = df.format(d);
                    }
                    temp.put(key, value);
                }
                StockAttribute stockatt = new StockAttribute(day, temp);
                atts.add(stockatt);
            }

        }
        this.startDate = beginDate;
        this.endDate = endDate;

        this.number = po.getNumber();
        this.name = po.getName();
        this.attributes = atts;
        this.sortAttr();
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public List<StockAttribute> getAttributes() {
        return attributes;
    }

    public List<StockAttribute> getAttributes(String start, String end) throws BadInputException {
        if (start.compareTo(this.startDate) < 0 || end.compareTo(this.endDate) > 0) {
            throw new BadInputException("日期返回超出VO内部范围");
        }

        List<StockAttribute> list = new ArrayList<>();

        for (int i = 0; i < attributes.size(); i++) {
            StockAttribute attribute = this.attributes.get(i);
            String day = attribute.getDate();
            if (day.compareTo(start) >= 0 && day.compareTo(end) <= 0) {
                list.add(attribute);
            }
        }
        return list;
    }

    @Override
    public int numberOfDays() {
        return this.attributes.size();
    }

    @Override
    public double priceAtDay(int dayIndex) {
        return Double.parseDouble(this.attributes.get(dayIndex).getAttribute().get("close"));
    }

    @Override
    public List<Double> pricelistAtDay(int dayIndex) {
        this.sortAttr();
        double open = Double.parseDouble(this.attributes.get(dayIndex).getAttribute().get("open"));
        double close = Double.parseDouble(this.attributes.get(dayIndex).getAttribute().get("close"));
        double high = Double.parseDouble(this.attributes.get(dayIndex).getAttribute().get("high"));
        double low = Double.parseDouble(this.attributes.get(dayIndex).getAttribute().get("low"));
        List<Double> result = new ArrayList<>();
        result.add(open);
        result.add(close);
        result.add(high);
        result.add(low);
        return result;
    }

    @Override
    public double changeAtDay(int dayIndex) {
        this.sortAttr();
        String strClose = this.attributes.get(dayIndex).getAttribute().get("close");
        String strOpen = this.attributes.get(dayIndex).getAttribute().get("open");
        double close = Double.parseDouble(strClose);
        double open = Double.parseDouble(strOpen);
        return close - open;
    }

    @Override
    public String dateString(int dayIndex) {
        this.sortAttr();
        return this.attributes.get(dayIndex).getDate();
    }

    //根据日期对属性进行排序
    private void sortAttr() {
        if (!this.sorted) {
            this.attributes.sort((at1, at2) ->
                    at1.getDate().compareTo(at2.getDate()));
            this.sorted = true;
        }
    }

    public void setAttributes(List<StockAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setInfo(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}



