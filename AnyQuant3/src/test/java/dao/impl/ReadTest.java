package dao.impl;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public class ReadTest {

    @Test
    public void testGetStockNumAndName() {
        ReadImpl read = new ReadImpl();
        HashMap map = read.getStockNumAndName();
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String)entry.getKey();
            String val = (String)entry.getValue();
            System.out.println(key + "----" + val);
        }
    }

    @Test
    public void testGetNamesAndNames() {
        ReadImpl read = new ReadImpl();
        HashMap map = read.getNamesAndNames();
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String)entry.getKey();
            HashMap val = (HashMap)entry.getValue();
            System.out.println(key + "----" + val.size());
        }
    }
//
//    @Test
//    public void testGetStock() throws NotFoundException {
//        ReadImpl read = new ReadImpl();
//        List<StockAttribute> list = read.getStock("sh600600");
//        System.out.println(list.size());
//        for(StockAttribute tmp : list){
//            System.out.println(tmp.getId() + "----" + tmp.getDate());
//        }
//    }
//
//    @Test
//    public void testGetAllBenchmark() {
//        ReadImpl read = new ReadImpl();
//        List<String> list = read.getAllBenchmark();
//        System.out.println(list.size());
//        System.out.println(list.get(0));
//    }

}
