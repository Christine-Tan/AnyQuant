package dao.impl;

import dao.object.*;
import dao.service.ReadService;
import model.common.PieChartVO;
import org.hibernate.Query;
import org.hibernate.Session;
import po.TradeInfoPO;
import util.enums.TradeEnum;
import util.exception.NotFoundException;
import util.hibernate.HibernateUtils;
import util.time.DateCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public class ReadImpl implements ReadService {

    @Override
    public HashMap<String, String> getStockNumAndName() {
        String hqlSelect = "from dao.object.StockBase";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        List<StockBase> list = querySelect.list();
        HashMap<String, String> map = new HashMap();
        for (StockBase tmp : list) {
            map.put(tmp.getName(), tmp.getId());
        }
        return map;
    }

    @Override
    public HashMap<String, HashMap<String, String>> getNamesAndNames() {
        HashMap<String, HashMap<String, String>> map = new HashMap();

        String hqlSelect = "from dao.object.Industry";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        List<Industry> list = querySelect.list();

        hqlSelect = "from dao.object.StockBase";
        querySelect = session.createQuery(hqlSelect);
        List<StockBase> stocks = querySelect.list();

        for (Industry industry : list) {

            HashMap<String, String> inner_map = new HashMap();

            for (StockBase stock : stocks) {
                if (stock.getIndustry().equalsIgnoreCase(industry.getId())){
                    inner_map.put(stock.getName(), stock.getId());
                }
            }
            map.put(industry.getName(), inner_map);
        }
        return map;
    }

    @Override
    public List<StockAttribute> getStock(String num) throws NotFoundException {
        String hqlSelect = "from dao.object.StockAttribute where id = :num";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        querySelect.setParameter("num", num);
        List<StockAttribute> list = querySelect.list();
        if (list.size() == 0)
            throw new NotFoundException("没有对应数据" + num);
        return list;
    }

    @Override
    public List<String> getAllBenchmark() {
        String hqlSelect = "from dao.object.BenchmarkBase";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        List<BenchmarkBase> list = querySelect.list();
        List<String> result = new ArrayList<String>();
        for (BenchmarkBase tmp : list) {
            result.add(tmp.getId());
        }
        return result;
    }

    @Override
    public List<BenchmarkAttribute> getBenchmark(String bench) throws NotFoundException {
        String hqlSelect = "from dao.object.BenchmarkAttribute where id = :bench";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        querySelect.setParameter("bench", bench);
        List<BenchmarkAttribute> list = querySelect.list();
        if (list.size() == 0)
            throw new NotFoundException("没有对应数据");
        return list;
    }

    @Override
    public List<TradeDao> getTrade(String num, String date) throws NotFoundException {
        String hqlSelect = "from dao.object.TradeDao where id = :num and date = :date";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        querySelect.setParameter("num", num);
        querySelect.setParameter("date", date);
        List<TradeDao> list = querySelect.list();
        if (list.size() == 0)
            throw new NotFoundException("没有对应数据" + num + "!!!" + date);
        return list;
    }

    @Override
    public HashMap<String, Double> getPieVolumeInfo(String number, String start, String end) {
        double buy = 0;
        double sold = 0;
        double mid = 0;
        while (!start.equalsIgnoreCase(end)) {
            String hqlSelect = "from dao.object.TradeDao where id = :num and date = :date";
            Session session = HibernateUtils.getSession();
            Query querySelect = session.createQuery(hqlSelect);
            querySelect.setParameter("num", number);
            querySelect.setParameter("date", start);
            List<TradeDao> list = querySelect.list();

            for (TradeDao dao : list) {
                if (dao.getType().equalsIgnoreCase("1"))
                    buy += Double.parseDouble(dao.getVolume());
                else if (dao.getType().equalsIgnoreCase("0"))
                    mid += Double.parseDouble(dao.getVolume());
                else if (dao.getType().equalsIgnoreCase("-1"))
                    sold += Double.parseDouble(dao.getVolume());
            }

            start = DateCount.count(start, 1);
        }

        String hqlSelect = "from dao.object.TradeDao where id = :num and date = :date";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        querySelect.setParameter("num", number);
        querySelect.setParameter("date", end);
        List<TradeDao> list = querySelect.list();

        for (TradeDao dao : list) {
            if (dao.getType().equalsIgnoreCase("1"))
                buy += Double.parseDouble(dao.getVolume());
            else if (dao.getType().equalsIgnoreCase("0"))
                mid += Double.parseDouble(dao.getVolume());
            else if (dao.getType().equalsIgnoreCase("-1"))
                sold += Double.parseDouble(dao.getVolume());
        }


        HashMap<String, Double> originMap = new HashMap<>();
        originMap.put("买盘", buy);
        originMap.put("卖盘", sold);
        originMap.put("中性盘", mid);
        return originMap;
    }

    @Override
    public HashMap<String, Double> getPieAmountInfo(String number, String start, String end) {
        double buy = 0;
        double sold = 0;
        double mid = 0;
        while (!start.equalsIgnoreCase(end)) {
            String hqlSelect = "from dao.object.TradeDao where id = :num and date = :date";
            Session session = HibernateUtils.getSession();
            Query querySelect = session.createQuery(hqlSelect);
            querySelect.setParameter("num", number);
            querySelect.setParameter("date", start);
            List<TradeDao> list = querySelect.list();

            for (TradeDao dao : list) {
                if (dao.getType().equalsIgnoreCase("1"))
                    buy += Double.parseDouble(dao.getAmount());
                else if (dao.getType().equalsIgnoreCase("0"))
                    mid += Double.parseDouble(dao.getAmount());
                else if (dao.getType().equalsIgnoreCase("-1"))
                    sold += Double.parseDouble(dao.getAmount());
            }

            start = DateCount.count(start, 1);
        }

        String hqlSelect = "from dao.object.TradeDao where id = :num and date = :date";
        Session session = HibernateUtils.getSession();
        Query querySelect = session.createQuery(hqlSelect);
        querySelect.setParameter("num", number);
        querySelect.setParameter("date", end);
        List<TradeDao> list = querySelect.list();

        for (TradeDao dao : list) {
            if (dao.getType().equalsIgnoreCase("1"))
                buy += Double.parseDouble(dao.getAmount());
            else if (dao.getType().equalsIgnoreCase("0"))
                mid += Double.parseDouble(dao.getAmount());
            else if (dao.getType().equalsIgnoreCase("-1"))
                sold += Double.parseDouble(dao.getAmount());
        }


        HashMap<String, Double> originMap = new HashMap<>();
        originMap.put("买盘", buy / 1000000);
        originMap.put("卖盘", sold / 1000000);
        originMap.put("中性盘", mid / 1000000);
        return originMap;
    }

}
