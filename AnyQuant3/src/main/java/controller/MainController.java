package controller;

import bl.factory.BLFactory;
import bl.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.analyse.RiseAndFallVO;
import model.barchart.MixSingleVolumeVO;
import model.barchart.SingleVolumeVO;
import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.common.PieChartVO;
import model.industry.IndustryVO;
import model.stock.BasicSingleVO;
import model.stock.StockVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import util.constant.StockConstant;
import util.enums.IndustryPeriodEnum;
import util.enums.PeriodEnum;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.json.JsonConverter;
import util.time.DateCount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 16/5/12.
 */
@Controller
public class MainController {

    private GetStockService getStockService;

    private SingleViewService singleViewService;

    private IndustryViewService industryViewService;

    private BarChartService barChartService;

    private PieChartService pieChartService;

    public MainController() throws IOException, NotFoundException {
        getStockService = BLFactory.getInstance().getGetStockService();
        singleViewService = BLFactory.getInstance().getSingleViewService();
        industryViewService = BLFactory.getInstance().getIndustryViewService();
        barChartService = BLFactory.getInstance().getBarChartService();
        pieChartService = BLFactory.getInstance().getPieChartService();
    }

    /**
     * 请求首页请求:显示FrontPage
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "FrontPage";
    }


    /**
     * 界面传入参数获取股票信息的主方法
     *
     * @param httpServletRequest 请求
     * @param response           返回结果
     * @return 股票信息
     * @throws NotFoundException 无数据
     * @throws IOException       IO异常
     * @throws BadInputException 输入参数格式有误
     */
    @RequestMapping(value = "*.stock", method = {RequestMethod.POST})
    public ModelAndView getStock(HttpServletRequest httpServletRequest, HttpServletResponse response)
            throws NotFoundException, IOException, BadInputException {
        //获取URL参数
        String number = httpServletRequest.getParameter("number");

        //TODO 分时图的日期需要界面输入
        String oneDay = httpServletRequest.getParameter("oneDay");

        //将各种数据加入model以返回界面 TODO 如果没有数据,需要界面处理
        Map<String, Object> model = new HashMap<>();
        this.pushDataToModel(model, number);
        this.pushDailyModel(model,number,"2016-05-19");

        return new ModelAndView("singleStock", model);
    }

    /**
     * 将各种数据加入model
     *
     * @param model
     * @param number
     * @throws NotFoundException
     * @throws JsonProcessingException
     * @throws BadInputException
     */
    private void pushDataToModel(Map<String, Object> model, String number) throws NotFoundException, JsonProcessingException, BadInputException {
        //获取日期信息
        Date today = new Date();
        String endDate = DateCount.dateToStr(today);
        String startDate = DateCount.count(endDate, -300);

        //获取StockVO
        StockVO stockVO = getStockService.getStock(
                number, startDate, endDate, StockConstant.AllFields, null);
        //获取股票名称
        String name = getStockService.getStockName(number);
        stockVO.setInfo(name, startDate, endDate);
        model.put("stockVO", stockVO);

        //单只股票价格信息
        this.pushStockModel(model, stockVO);

        //数据分析结果
        this.pushAnalyseModel(model, stockVO);

        //行业数据模型
        String industryName = getStockService.getIndustryName(number);
        IndustryPeriodEnum industryPeriod = IndustryPeriodEnum.FOURTH;
        this.pushIndustryModel(model, industryName, industryPeriod);
    }

    /**
     * 将一日价格分时数据加入model
     *
     * @param model
     * @param number
     * @param oneDate
     * @throws NotFoundException
     * @throws JsonProcessingException
     */
    private void pushDailyModel(Map<String, Object> model, String number, String oneDate) throws NotFoundException, JsonProcessingException {
        //一日的分时价格折线图
        LinearChartVO dailyPrice = getStockService.getLineChartVO(number, "2016-05-19");

        List<String> dailyLine = JsonConverter.jsonOfLinearChartVO(dailyPrice);
        model.put("dailyLine", dailyLine);

        //一日的分时成交量图
        VolumeVO volumeVO = getStockService.getAmountInADayBarchart(number, "2016-05-19");
        String volumeLine = JsonConverter.jsonOfVolumeVO(volumeVO);
        model.put("volumeLine", volumeLine);
    }

    /**
     * 将单只股票基本信息加入model以返回界面
     *
     * @param model
     * @param stockVO
     * @throws JsonProcessingException
     * @throws NotFoundException
     * @throws BadInputException
     */
    private void pushStockModel(Map<String, Object> model, StockVO stockVO) throws JsonProcessingException, NotFoundException, BadInputException {
        //K线图
        String klinear = JsonConverter.jsonKlinearOfVO(stockVO);
        model.put("kLine", klinear);

        //成交量柱状图
        SingleVolumeVO singleVolumeVO = barChartService.getSingleVolumeVO
                (stockVO.getName(), stockVO.getNumber(), PeriodEnum.DAY, stockVO.getStartDate(), stockVO.getEndDate());
        String singleVolumeLine = JsonConverter.jsonOfVolumeVO(singleVolumeVO);
        model.put("singleVolumeLine", singleVolumeLine);

        //成交量与成交金额柱状图
        MixSingleVolumeVO mixSingleVolumeVO = barChartService.getMixSingleVolumeVO
                (stockVO.getName(), stockVO.getNumber(), PeriodEnum.DAY, stockVO.getStartDate(), stockVO.getEndDate());
        String mixSingleVolumeLine = JsonConverter.jsonOfVolumeVO(mixSingleVolumeVO);
        model.put("mixSingleVolumeLine", mixSingleVolumeLine);

        //个股涨跌幅排行
        List<RiseAndFallVO> stockRiseList = getStockService.getRiseAndFallList();
        model.put("stockRiseList", stockRiseList);
    }

    /**
     * 将数据分析结果情况加入model以返回界面
     *
     * @param model
     * @param industryName
     * @param industryPeriod
     * @throws NotFoundException
     * @throws BadInputException
     * @throws JsonProcessingException
     */
    private void pushIndustryModel(Map<String, Object> model, String industryName, IndustryPeriodEnum industryPeriod) throws NotFoundException, BadInputException, JsonProcessingException {
        //行业基本信息VO
        IndustryVO industryVO = industryViewService.getBasicIndustryInfo(industryName, industryPeriod);
        model.put("industryVO", industryVO);

        //行业价格折线图与成交量
        VolumeVO industryVolumeData = industryViewService.getIndustryVolume(industryName, industryPeriod);
        LinearChartVO comparePriceData = industryViewService.getCompareLinearChartVO(industryName, industryPeriod);
        LinearChartVO industryPriceData = industryViewService.getIndustryPrice(industryName, industryPeriod);

        List<String> industryPriceLine = JsonConverter.jsonOfLinearChartVO(industryPriceData);
        List<String> comparePriceLine = JsonConverter.jsonOfLinearChartVO(comparePriceData);
        String volume = JsonConverter.jsonOfVolumeVO(industryVolumeData);

        model.put("industryPriceLine", industryPriceLine);
        model.put("comparePriceLine", comparePriceLine);
        model.put("industryVolume", volume);

        //行业股票涨跌幅排行
        List<RiseAndFallVO> industryRiseList = industryViewService.getRiseAndFallList();
        model.put("industryRiseList", industryRiseList);
    }


    /**
     * 将行业数据模型加入model以返回界面
     *
     * @param model
     * @param stockVO
     * @throws JsonProcessingException
     * @throws NotFoundException
     * @throws BadInputException
     */
    private void pushAnalyseModel(Map<String, Object> model, StockVO stockVO) throws JsonProcessingException, NotFoundException, BadInputException {
        //获取SingleViewService的各种内容
        String startDate = stockVO.getStartDate();
        String endDate = stockVO.getEndDate();
        BasicSingleVO basicSingleVO = singleViewService.getBasicSingleInfo(stockVO, startDate, endDate);
        model.put("basicSingleVO", basicSingleVO);

        //4个数据分析的折线图rsi,ema,macd,arbr
        LinearChartVO rsiData = singleViewService.getStockRSI(stockVO);
        LinearChartVO emaData = singleViewService.getStockEMA(stockVO);
        LinearChartVO macdData = singleViewService.getStockMACD(stockVO);
        LinearChartVO arbrData = singleViewService.getStockARBR(stockVO);

        List<String> rsiLine = JsonConverter.jsonOfLinearChartVO(rsiData);
        List<String> emaLine = JsonConverter.jsonOfLinearChartVO(emaData);
        List<String> macdLine = JsonConverter.jsonOfLinearChartVO(macdData);
        List<String> arbrLine = JsonConverter.jsonOfLinearChartVO(arbrData);

        model.put("rsiLine", rsiLine);
        model.put("emaLine", emaLine);
        model.put("macdLine", macdLine);
        model.put("arbrLine", arbrLine);

        //某时间段内单只股票交易金额对比图(饼图)
        String name = stockVO.getName();
        String number = stockVO.getNumber();
        PieChartVO amountVOPie = pieChartService.getPieAmountVO(name, number, DateCount.count(endDate, -14), endDate);
        List<String> amountPie = JsonConverter.convertPieVO(amountVOPie);
        model.put("amountPie", amountPie);

        //某时间段内单只股票交易量对比图(饼图)
        PieChartVO volumeVOPie = pieChartService.getPieVolumeVO(name, number, DateCount.count(endDate, -14), endDate);
        List<String> volumePie = JsonConverter.convertPieVO(volumeVOPie);
        model.put("volumePie", volumePie);

        //TODO arima模型结果
    }

}
