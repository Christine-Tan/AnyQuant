package data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by JiachenWang on 2016/4/25.
 */
public class ConnectHelper {

    private static Connection conn = connSQL();

    public static Connection getConn() {
        return conn;
    }

    public static void main(String[] args){
        ConnectHelper.connSQL();
    }

    // connect to MySQL
    public static Connection connSQL() {
        String url = "jdbc:mysql://localhost:3306/anyquant?characterEncoding=UTF-8";
        String username = "root";
        String password = "123456"; // 加载驱动程序以连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        }
        // 捕获加载驱动程序异常
        catch (ClassNotFoundException cnfex) {
            System.err.println("装载 JDBC/ODBC 驱动程序失败。");
            cnfex.printStackTrace();
        }
        // 捕获连接数据库异常
        catch (SQLException sqlex) {
            System.err.println("无法连接数据库");
            sqlex.printStackTrace();
        }
        return conn;
    }

    // disconnect to MySQL
    public static void deconnSQL() {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.err.println("关闭数据库问题 ：");
            e.printStackTrace();
        }
    }
}
