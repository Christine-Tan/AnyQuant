package data;

import java.io.IOException;

/**
 * Created by JiachenWang on 2016/5/18.
 */
public class Python {

    public static void main(String[] args) throws InterruptedException, IOException {
        Process proc = Runtime.getRuntime().exec("python  D:\\mysql.py");
        proc.waitFor();
    }

}
