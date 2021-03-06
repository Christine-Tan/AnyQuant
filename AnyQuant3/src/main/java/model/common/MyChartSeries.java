package model.common;

import javafx.scene.chart.XYChart;
import util.calculate.MySort;

import java.util.Map;

/**
 * Created by kylin on 16/4/12.
 */
public class MyChartSeries {

    private String name;

    private Map<String,Double> xyItem;

    public MyChartSeries(String name, Map<String, Double> xyItem) {
        this.name = name;
        this.xyItem = xyItem;
        MySort.sortHashmapByKey(xyItem);
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getXyItem() {
        return xyItem;
    }

    XYChart.Series<String, Number> getStrNumLineSeries(){
        return null;
    }
}
