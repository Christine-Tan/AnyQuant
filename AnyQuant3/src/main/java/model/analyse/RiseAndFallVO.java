package model.analyse;

import util.calculate.NumberFormater;

/**
 * Created by kylin on 16/5/23.
 * All rights reserved.
 */
public class RiseAndFallVO implements Comparable{

    private String name;

    private double rise;

    private String riseStr;

    public RiseAndFallVO(String name, double rise) {
        this.name = name;
        this.rise = rise;
        riseStr = NumberFormater.formatPercent(rise);
    }

    public String getName() {
        return name;
    }

    public double getRise() {
        return rise;
    }

    public String getRiseStr() {
        return riseStr;
    }

    @Override
    public int compareTo(Object o) {
        RiseAndFallVO another = (RiseAndFallVO) o;
        if(this.rise < another.getRise())
            return 1;
        else if(this.rise == another.getRise())
            return 0;
        else
            return -1;
    }
}
