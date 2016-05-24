package model.analyse;

import java.util.Map;

/**
 * Created by kylin on 16/5/24.
 * All rights reserved.
 */
public class ARBRresult {
    Map<String, Double> ar;
    Map<String, Double> br;

    public ARBRresult(Map<String, Double> ar, Map<String, Double> br) {
        this.ar = ar;
        this.br = br;
    }

    public Map<String, Double> getAr() {
        return ar;
    }

    public Map<String, Double> getBr() {
        return br;
    }
}
