package bl.analyse;

import util.exception.BadInputException;
import util.exception.NotFoundException;

/**
 * Created by JiachenWang on 2016/5/24.
 */
public interface PredictBLService {

    /**
     * 通过MACD预测分析（150日有效数据）
     * @param stock_num 股票编号
     * @return 分析结论
     * @throws BadInputException
     * @throws NotFoundException
     */
    public String getMACDInfo(String stock_num) throws BadInputException, NotFoundException;


    /**
     * 通过方差预测分析（15日有效数据）
     * @param stock_num 股票编号
     * @return 分析结论
     * @throws BadInputException
     * @throws NotFoundException
     */
    public String getVarianceInfo(String stock_num) throws BadInputException, NotFoundException;

    /**
     * 通过方差预测分析
     * @param stock_num 股票编号
     * @return 分析结论
     * @throws BadInputException
     * @throws NotFoundException
     */
    public String getArbrInfo(String stock_num) throws BadInputException, NotFoundException;

}
