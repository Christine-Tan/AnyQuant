package bl.impl;

import bl.analyse.TechnicalAnalysisStrategy;
import bl.service.GetStockService;
import model.stock.StockVO;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.time.DateCount;

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
        analysisStrategy.calculateRSI(stockVO,6);
        return "";
    }

}
