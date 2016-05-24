package bl.impl;

import bl.analyse.TechnicalAnalysisStrategy;
import bl.service.GetStockService;
import model.stock.StockVO;
import util.calculate.MyHashItem;
import util.calculate.MySort;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.time.DateCount;

import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 16/5/24.
 * All rights reserved.
 */
public class NewAnalysis {

    private TechnicalAnalysisStrategy analysisStrategy;

    private GetStockService getStockService;

    public String analyseRSI(String number) throws NotFoundException, BadInputException {
        String today = DateCount.getToday();
        String startDate = DateCount.count(today,-100);
        StockVO stockVO = getStockService.getStock(number,startDate,today);
        Map<String, Double> rsi6 =  analysisStrategy.calculateRSI(stockVO,6);
        Map<String, Double> rsi14 =  analysisStrategy.calculateRSI(stockVO,14);
        Map<String, Double> rsi24 =  analysisStrategy.calculateRSI(stockVO,24);

        List<MyHashItem> list6 = MySort.sortHashmapByKey(rsi6);
        List<MyHashItem> list14 = MySort.sortHashmapByKey(rsi14);
        List<MyHashItem> list24 = MySort.sortHashmapByKey(rsi24);

        int size = list14.size();
        double lastest6 = (double) list6.get(size-1).getValue();
        double lastest14 = (double) list14.get(size-1).getValue();
        double lastest24 = (double) list24.get(size-1).getValue();

        StringBuilder result = new StringBuilder();
        result.append("从RSI指数分析来看,短期内");
        if(lastest14 >= 70){
            result.append("股票14日RSI数值升至70,代表该证券已被超买,投资者应考虑出售该证券.");
        } else if(lastest14 <= 30){
            result.append("股票14日RSI数值跌至30，代表证券被超卖（Oversold），投资者应购入该证券。");
        } else {
            result.append("股票14日RSI数值在50左右,无明显买卖信号");
        }
//
//        2，RSI一般选用6日、12日、24日作为参考基期，基期越长越有趋势性(慢速RSI)，基期越短越有敏感性，(快速RSI)。
// 当快速RSI由下往上突破慢速RSI时，为买进时机;当快速RSI由上而下跌破慢速RSI时，为卖出时机。
        return result.toString();
    }

}
