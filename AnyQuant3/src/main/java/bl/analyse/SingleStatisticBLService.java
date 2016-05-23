package bl.analyse;


import model.stock.StockVO;
import util.exception.BadInputException;
import util.exception.NotFoundException;

import java.io.IOException;

/**
 * Created by JiachenWang on 2016/4/10.
 */
public interface SingleStatisticBLService {

    /**
     * 求某时间段股票日价格方差
     * @param stock 股票信息集合
     * @return
     */
     double getVarianceOfPrice(StockVO stock);

    /**
     * 求某时间段股票价格平均值
     * @param stock 股票信息集合
     * @return
     */
    double getAvgPrice(StockVO stock);

    /**
     * 求某时间段所有股票日价格方差集合的1/4,1/2,3/4分位点
     * @return double + "-" + double + "-" + double
     */
    String getAllVarianceOfPrice(String startDate, String endDate) throws NotFoundException;

    /**
     * 计算近90天两只股票的相关系数
     * @param stockname1
     * @param stockname2
     * @return
     * @throws NotFoundException
     * @throws BadInputException
     */
     String cal90CC(String stockname1, String stockname2) throws NotFoundException, BadInputException, IOException;

}
