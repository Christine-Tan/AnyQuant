package bl.impl;

import bl.factory.BLFactory;
import bl.service.BarChartService;
import bl.service.GetStockService;
import bl.service.PieChartService;
import bl.service.SingleViewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.analyse.RiseAndFallVO;
import model.barchart.MixSingleVolumeVO;
import model.barchart.SingleVolumeVO;
import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.common.PieChartVO;
import model.stock.BasicSingleVO;
import model.stock.StockVO;
import util.enums.PeriodEnum;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.json.JsonConverter;
import util.time.DateCount;

import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 16/5/24.
 * All rights reserved.
 */
public class PushData {

    private BarChartService barChartService;

    private SingleViewService singleViewService;

    private PieChartService pieChartService;

    private GetStockService getStockService;

    public PushData(GetStockService getStockService) throws NotFoundException {
        this.getStockService = getStockService;
        this.singleViewService = BLFactory.getInstance().getSingleViewService();
        this.pieChartService = BLFactory.getInstance().getPieChartService();
        this.barChartService = BLFactory.getInstance().getBarChartService();
    }

    /**
     * 将一日价格分时数据加入model
     *
     */
    public void pushDailyModel(Map<String, Object> model, String number, String oneDate) throws NotFoundException, JsonProcessingException {
        //一日的分时价格折线图
        LinearChartVO dailyPrice = getStockService.getLineChartVO(number, oneDate);

        List<String> dailyLine = JsonConverter.jsonOfLinearChartVO(dailyPrice);
        model.put("dailyLine", dailyLine);

        //一日的分时成交量图
        VolumeVO volumeVO = getStockService.getAmountInADayBarchart(number, oneDate);
        String volumeLine = JsonConverter.jsonOfVolumeVO(volumeVO);
        model.put("volumeLine", volumeLine);
    }

    /**
     * 将单只股票基本信息加入model以返回界面
     *
     */
    public void pushStockModel(Map<String, Object> model, StockVO stockVO) throws JsonProcessingException, NotFoundException, BadInputException {
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
     * 将统计模型模型加入model以返回界面
     *
     */
    public void pushAnalyseModel(Map<String, Object> model, StockVO stockVO) throws JsonProcessingException, NotFoundException, BadInputException {
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

    }

}
