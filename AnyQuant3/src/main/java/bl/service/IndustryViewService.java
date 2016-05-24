package bl.service;


import model.analyse.RiseAndFallVO;
import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.industry.IndustryVO;
import util.enums.IndustryPeriodEnum;
import util.exception.BadInputException;
import util.exception.NotFoundException;

import java.util.List;

/**
 * Created by kylin on 16/3/31.
 */
public interface IndustryViewService {

    /**
     * 获取一个行业的基本数据统计信息
     *
     * @param industryName 行业名称
     * @param period 时间间隔
     * @return 行业基本数据VO包
     */
    IndustryVO getBasicIndustryInfo(String industryName, IndustryPeriodEnum period) throws NotFoundException;

    /**
     * 获取一个行业一段时间内的价格统计信息(折线图)
     *
     * @param industryName 行业名称
     * @param period 时间间隔
     * @return 行业基本数据VO包
     */
    LinearChartVO getIndustryPrice(String industryName, IndustryPeriodEnum period) throws NotFoundException;

    /**
     * 获取一个行业一段时间内的涨跌幅与大盘涨跌幅对比(折线图)
     *
     * @param industryName 行业名称
     * @param period 时间
     * @return
     * @throws BadInputException
     * @throws NotFoundException
     */
    LinearChartVO getCompareLinearChartVO(String industryName, IndustryPeriodEnum period) throws BadInputException, NotFoundException;

    /**
     * 获取一个行业一段时间内的资金流向统计信息(柱状图)
     *
     * @param industryName 行业名称
     * @param period 时间间隔
     * @return 行业基本数据VO包
     */
    VolumeVO getIndustryVolume(String industryName, IndustryPeriodEnum period) throws NotFoundException;

    /**
     * 获取所有行业涨跌幅排行榜
     *
     * @return 按照涨到跌排序的行业
     */
    List<RiseAndFallVO> getRiseAndFallList();
}
