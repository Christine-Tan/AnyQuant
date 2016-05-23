package data.download;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by kylin on 16/5/7.
 */
public class DownloadDataTxtTest {

    DownloadDataTxt downloadDataTxt = new DownloadDataTxt();

    public DownloadDataTxtTest() throws IOException {

    }

    @Test
    public void downloadStockNumber() throws Exception {

        downloadDataTxt.downloadStockNumber("2016","sh");
        downloadDataTxt.downloadStockNumber("2016","sz");
    }

    @Test
    public void downloadStock() throws Exception {
        downloadDataTxt.downloadStock("sh600000","2009-04-01","2016-04-06");
    }

    @Test
    public void downloadBenchmarkNumber() throws Exception {
        downloadDataTxt.downloadBenchmarkNumber();
    }

    @Test
    public void downloadBenchmark() throws Exception {
        downloadDataTxt.downloadBenchmark("sh","2009-04-01","2016-04-06");
    }

    @Test
    public void downloadFields() throws Exception {
        downloadDataTxt.downloadFields();
    }

    @Test
    public void getJsonReader() throws Exception {
        downloadDataTxt.getJsonReader();
    }

}