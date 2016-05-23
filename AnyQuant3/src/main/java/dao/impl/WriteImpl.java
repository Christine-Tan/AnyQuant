package dao.impl;

import dao.object.*;
import dao.service.WriteService;
import dao.tool.BenchmarkHelper;
import dao.tool.StockHelper;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import po.StockPO;
import po.TradeInfoPO;
import model.stock.ResultMsg;
import util.hibernate.HibernateUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public class WriteImpl implements WriteService {

    @Override
    public ResultMsg insertStockAttrs(StockPO po) {
        boolean flag = true;

        HashMap<String, HashMap<String, String>> infomation = po.getInfomation();

        for (String key : infomation.keySet()) {
            StockAttribute attr = new StockAttribute(po.getNumber(), key);
            HashMap<String, String> map = infomation.get(key);
            for (String field : map.keySet()) {
                StockHelper.setInfo(attr, field, map.get(field));
            }
            ResultMsg tmp = this.insertStcokAttr(attr);
            if (!tmp.isPass())
                flag = false;
        }

        if (flag)
            return new ResultMsg(true, "操作成功");
        return new ResultMsg(false, "操作失败");
    }

    private ResultMsg insertBenchmarkAttr(BenchmarkAttribute attribute) {
        ResultMsg result = null;
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(attribute);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            result = new ResultMsg(false, "操作失败");
        } finally {
            HibernateUtils.closeSession(session);
        }
        result = new ResultMsg(true, "操作成功");
        return result;
    }

    @Override
    public ResultMsg insertBenchmarkAttrs(StockPO po) {
        boolean flag = true;

        HashMap<String, HashMap<String, String>> infomation = po.getInfomation();

        for (String key : infomation.keySet()) {
            BenchmarkAttribute attr = new BenchmarkAttribute(po.getNumber(), key);
            HashMap<String, String> map = infomation.get(key);
            for (String field : map.keySet()) {
                BenchmarkHelper.setInfo(attr, field, map.get(field));
            }
            ResultMsg tmp = this.insertBenchmarkAttr(attr);
            if (!tmp.isPass())
                flag = false;
        }

        if (flag)
            return new ResultMsg(true, "操作成功");
        return new ResultMsg(false, "操作失败");
    }

    @Override
    public ResultMsg insertTradeInfo(TradeInfoPO po) {
        boolean flag = true;
        List<TradeDao> list = po.getTradeDao();
        for (TradeDao element : list) {
            ResultMsg tmp = this.insertTradeInfo(element);
            if (!tmp.isPass())
                flag = false;
        }

        if (flag)
            return new ResultMsg(true, "操作成功");
        return new ResultMsg(false, "操作失败");
    }

    private ResultMsg insertTradeInfo(TradeDao trade) {
        ResultMsg result = null;
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(trade);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            result = new ResultMsg(false, "操作失败");
        } finally {
            HibernateUtils.closeSession(session);
        }
        result = new ResultMsg(true, "操作成功");
        return result;
    }

    private ResultMsg insertStcokAttr(StockAttribute attribute) {
        ResultMsg result = null;
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(attribute);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            result = new ResultMsg(false, "操作失败");
        } finally {
            HibernateUtils.closeSession(session);
        }
        result = new ResultMsg(true, "操作成功");
        return result;
    }

    @Override
    public ResultMsg insertIndustry(Industry industry) {
        ResultMsg result = null;
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(industry);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            result = new ResultMsg(false, "操作失败");
        } finally {
            HibernateUtils.closeSession(session);
        }
        result = new ResultMsg(true, "操作成功");
        return result;
    }

    @Override
    public ResultMsg insertStock(StockBase stock) {
        ResultMsg result = null;
        Session session = null;
        try {
            session = HibernateUtils.getSession();
            session.beginTransaction();
            session.save(stock);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            result = new ResultMsg(false, "操作失败");
        } finally {
            HibernateUtils.closeSession(session);
        }
        result = new ResultMsg(true, "操作成功");
        return result;
    }

}
