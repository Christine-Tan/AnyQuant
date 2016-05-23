package bl.service;


import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.stock.StockVO;
import model.stock.ConditionSelect;
import util.exception.BadInputException;
import util.exception.NotFoundException;

import java.util.List;

/**
 * Created by kylin on 16/3/4.
 */
public interface GetStockService {

    /**
     * 通过股票编号获得所属行业名称
     *
     * @param num 股票编号
     * @return 所属行业名称
     * @throws NotFoundException
     */
    String getIndustryName(String num) throws NotFoundException;

    /**
     * 通过股票编号获得股票名称
     *
     * @param num
     * @return
     * @throws NotFoundException
     */
    String getStockName(String num) throws NotFoundException;

    /**
     * 返回指定股票代码的指定时间字段的信息
     *
     * @param name   股票代码
     * @param start  起点日期
     * @param end    终点日期
     * @param fields 股票信息字段
     * @return 股票代码的指定时间字段的信息
     */
    StockVO getStock(String name, String start, String end, String fields, List<ConditionSelect> ranges) throws NotFoundException, BadInputException;

    /**
     * 获取一直股票单日价格的折线图数据,包括两条数据线,即时价格与累计均价
     *
     * @param number 股票代码
     * @param date   日期
     * @return
     * @throws NotFoundException
     */
    LinearChartVO getLineChartVO(String number, String date) throws NotFoundException;

    /**
     * 获取一直股票单日价格的成交量的数据
     *
     * @param number 股票代码
     * @param date   日期
     * @return
     * @throws NotFoundException
     */
    VolumeVO getAmountInADayBarchart(String number, String date) throws NotFoundException;

}
