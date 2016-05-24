package bl.impl;

import bl.analyse.TechnicalAnalysisStrategy;
import bl.service.GetStockService;
import model.stock.StockVO;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.time.DateCount;

import java.util.Map;
import java.util.Set;

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

        Set<Map.Entry<String, Double>> set14 = rsi14.entrySet();


//        三．RSI指标的使用方法：
// 当n=14时，指数最具代表性。
// 当某证券的RSI升至70时，代表该证券已被超买（Overbought），投资者应考虑出售该证券。
// 相反，当证券RSI跌至30时，代表证券被超卖（Oversold），投资者应购入该证券。


//
//        2，RSI一般选用6日、12日、24日作为参考基期，基期越长越有趋势性(慢速RSI)，基期越短越有敏感性，(快速RSI)。
// 当快速RSI由下往上突破慢速RSI时，为买进时机;当快速RSI由上而下跌破慢速RSI时，为卖出时机。
        return "";
    }

}
