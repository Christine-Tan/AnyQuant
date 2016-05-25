package model.analyse;

import util.exception.BadInputException;
import util.time.DateCount;

import java.util.Map;

/**
 * Created by kylin on 16/5/24.
 * All rights reserved.
 */
public class ARBRresult {
    Map<String, Double> ar;
    Map<String, Double> br;

    String start_date;
    String end_date;

    public ARBRresult(Map<String, Double> ar, Map<String, Double> br) {
        this.ar = ar;
        this.br = br;
        try {
            start_date = DateCount.getMinDate(ar.keySet());
            end_date = DateCount.getMaxDate(ar.keySet());
        } catch (BadInputException e) {
            e.printStackTrace();
        }
        System.out.println(start_date + end_date);
    }

    public Map<String, Double> getAr() {
        return ar;
    }

    public Map<String, Double> getBr() {
        return br;
    }

    public double[] getArArray() {
        double[] array = new double[ar.size()];
        int ptr = 0;

        String tmp_date = start_date;
        while (!tmp_date.equalsIgnoreCase(DateCount.count(end_date, 1))) {
            if (ar.get(tmp_date) != null) {
                array[ptr] = ar.get(tmp_date);
                ptr++;
            }
            tmp_date = DateCount.count(tmp_date, 1);
        }
        return array;
    }

    public double[] getBrArray() {
        double[] array = new double[br.size()];
        int ptr = 0;

        String tmp_date = start_date;
        while (!tmp_date.equalsIgnoreCase(DateCount.count(end_date, 1))) {
            if (br.get(tmp_date) != null) {
                array[ptr] = br.get(tmp_date);
                ptr++;
            }
            tmp_date = DateCount.count(tmp_date, 1);
        }
        return array;
    }

}
