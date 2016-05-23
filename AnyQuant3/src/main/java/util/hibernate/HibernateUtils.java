package util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by JiachenWang on 2016/5/12.
 */
public class HibernateUtils {
    /**
     * 读取Hibernate.cfg.xml文件
     */
    private static SessionFactory factory;

    static {
        try {
            //读取hibernate.cfg.xml文件
            Configuration cfg = new Configuration().configure(HibernateUtils.class.getResource("hibernate.cfg.xml"));

            //建立SessionFactory
            factory = cfg.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开Session
     */
    public static Session getSession() {
        return factory.openSession();
    }

    /**
     * 关闭Session
     */
    public static void closeSession(Session session) {
        if (session != null) {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     *关闭factory
     */
    public static void closeFactory() {
        if (factory != null) {
            if (!factory.isClosed()) {
                factory.close();
            }
        }
    }
}
