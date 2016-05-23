package data.impl;

import data.dataservice.CacheDataService;
import po.TradeInfoLine;
import po.TradeInfoPO;
import util.constant.FilePath;
import util.enums.TradeEnum;
import util.exception.NotFoundException;
import util.io.FileIOHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kylin on 16/3/7.
 */
public class CacheDataTxtImpl implements CacheDataService {
    private String mainPath = FilePath.CACHE_DIR;

    public CacheDataTxtImpl() throws IOException {

    }

    @Override
    public TradeInfoPO getLatestDayTradeInfo(String number, String date) throws NotFoundException, IOException {
        String file = FilePath.LATEST_INFO_PATH + date + "/" + number + ".txt";
        ArrayList<String> strInfos = FileIOHelper.readTxtFileLines(file);
        HashMap<String, TradeInfoLine> stockTradeInfo = new HashMap<>();

//        System.out.println(strInfos.size());
//        System.out.println(file);
        // check impl
        String headLine = strInfos.get(1);
        if (headLine.contains("当天没有数据")){
            throw new NotFoundException(number +" " + date +" 当天没有数据");
        }

        // get open and close price
        int size = strInfos.size();

        String firstLine = strInfos.get(1);
        String lastLine = strInfos.get(size - 1);

        String[] split = lastLine.split(",");
        double open = Double.parseDouble(split[2]);
        split = firstLine.split(",");
        double close = Double.parseDouble(split[2]);

        for (String line : strInfos) {


            if (line.startsWith(",")) {
            } else {
                //id,time,price,change,volume,amount,type
                split = line.split(",");
                int id = Integer.parseInt(split[0]);
                String time = split[1];
                double price = Double.parseDouble(split[2]);
                double change = 0;
                try {
                    change = Double.parseDouble(split[3]);
                } catch (NumberFormatException ignored) {

                }
                double volume = Double.parseDouble(split[4]);
                double amount = Double.parseDouble(split[5]);
                TradeEnum tradeEnum = TradeEnum.getTradeEnum(split[6]);

                //get one line
                TradeInfoLine tradeInfoLine = new TradeInfoLine(id, time, price,
                        change, volume, amount, tradeEnum);
                stockTradeInfo.put(time, tradeInfoLine);
            }
        }
        TradeInfoPO tradeInfoPO = new TradeInfoPO(stockTradeInfo, number, date);
        tradeInfoPO.setOpen(open);
        tradeInfoPO.setClose(close);
        return tradeInfoPO;
    }

}
