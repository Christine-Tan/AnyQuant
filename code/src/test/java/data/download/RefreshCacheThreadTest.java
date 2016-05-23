package data.download;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by kylin on 16/5/7.
 */
public class RefreshCacheThreadTest {

    RefreshCacheThread cacheThread = new RefreshCacheThread();

    public RefreshCacheThreadTest() throws IOException {

    }

//    @Test
//    public void refreshOrNot() throws Exception {
//        cacheThread.refreshOrNot();
//    }

    @Test
    public void refreshFields() throws Exception {
        cacheThread.refreshFields();;
    }

//    @Test
//    public void refreshStock() throws Exception {
//        cacheThread.refreshStock();
//    }

    @Test
    public void refreshBenchmark() throws Exception {
        cacheThread.refreshBenchmark();
    }

//    @Test
//    public void refresh() throws Exception {
//        cacheThread.refresh();
//    }

}