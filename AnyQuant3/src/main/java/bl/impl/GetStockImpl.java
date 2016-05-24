package bl.impl;


import bl.service.GetStockService;
import bl.tool.FormatCheck;
import data.dataservice.StockDataService;
import data.factory.DataFactory;
import model.analyse.RiseAndFallVO;
import model.barchart.VolumeChartVO;
import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.common.MyChartSeries;
import model.common.StockPriceInfo;
import model.stock.ConditionSelect;
import model.stock.StockAttribute;
import model.stock.StockVO;
import po.StockPO;
import po.TradeInfoPO;
import util.constant.StockConstant;
import util.enums.LinearChartType;
import util.enums.PeriodEnum;
import util.enums.TypeOfVolumn;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.time.DateCount;
import util.time.TimeConvert;

import java.util.*;

/**
 * Created by kylin on 16/3/4.
 */
public class GetStockImpl implements GetStockService {

    private StockDataService stockDataService;

    private HashMap<String, StockPO> cachePO;

    private List<RiseAndFallVO> riseAndFallVOs;

    private List<HashMap<String, String>> numAndName;
    private List<String> exchangeNames;
    private HashMap<String, String> numberAndName;

    public GetStockImpl() throws NotFoundException {
        stockDataService = DataFactory.getInstance().getStockDataService();
        cachePO = new HashMap<>();
        numAndName = new ArrayList<>();
        exchangeNames = new ArrayList<>();
        numberAndName = new HashMap<>();
        initial();
    }

    public void initial() throws NotFoundException {
        numberAndName = stockDataService.getStockNumAndName();

        Iterator<Map.Entry<String, String>> iter = numberAndName.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String number = entry.getKey();
            String exchangeName = number.substring(0, 2);
            int i = exchangeNames.indexOf(exchangeName);
            if (i < 0) {
                exchangeNames.add(exchangeName);
                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put(number, entry.getValue());
                numAndName.add(temp);
            } else {
                numAndName.get(i).put(number, entry.getValue());
            }
        }

    }


    @Override
    public String getIndustryName(String num) throws NotFoundException {
        for (Map.Entry<String, HashMap<String, String>> industry :
                this.stockDataService.getNamesAndNames().entrySet()) {
            for (Map.Entry<String, String> entry : industry.getValue().entrySet()) {
                if (entry.getValue().equalsIgnoreCase(num))
                    return industry.getKey();
            }
        }
        throw new NotFoundException("没有对应信息");
    }

    @Override
    public String getStockName(String num) throws NotFoundException {
        for (Map.Entry<String, String> entry : stockDataService.getStockNumAndName().entrySet()) {
            if (entry.getValue().equalsIgnoreCase(num))
                return entry.getKey();
        }
        throw new NotFoundException("股票不存在");
    }

    public StockVO getStock(String number, String start, String end, String fields, List<ConditionSelect> ranges) throws NotFoundException, BadInputException {
        FormatCheck.isDateBefore(start, end);
        //从数据层调取数据包
        StockPO po = this.getAndCachePO(number);
        //变为VO
        StockVO vo = new StockVO(po, start, end, fields);
        //过滤数据域
        vo = filter(vo, ranges);
        //获取股票名称
        String name = this.getStockName(number);
        vo.setInfo(name, start, end);
        return vo;
    }

    @Override
    public StockVO getStock(String name, String start, String end) throws NotFoundException, BadInputException {
        return this.getStock(name,start,end,StockConstant.AllFields,null);
    }

    /**
     * 先在缓存中找某只股票的PO,没有再从数据层获得
     *
     * @param number 股票
     * @return
     * @throws NotFoundException
     */
    private StockPO getAndCachePO(String number) throws NotFoundException {
        StockPO po = cachePO.get(number) ;
        if (po == null) {
            if (number.equals("hs300")) {
                po = stockDataService.getBenchmark(number);
            } else {
                po = stockDataService.getStock(number);
            }
            cachePO.put(number, po);
        }
        return po;
    }

    public VolumeVO getAmountInADayBarchart(String number, String date) throws NotFoundException {
        //获取每一秒的时间与金额
        TradeInfoPO tradeInfoPO = this.stockDataService.getTradeInfo(number, date);
        StockPriceInfo stockPriceInfo = tradeInfoPO.getPriceInfoVOFromTradeInfoPO(tradeInfoPO);
        HashMap<String, Double> timeAndAmount = stockPriceInfo.getTimeAndVolume();

        //添加间隔一定秒数的数据
        ArrayList<VolumeChartVO> volumeChartVOs = new ArrayList<>();

        List<String> validSeconds = TimeConvert.getSecondsInTrade(1);

        int stepIndex = 0;
        int step = 20;

        for (String oneSecond : validSeconds) {
            // 如果这一秒存在数据
            if (stockPriceInfo.timeGotInfo(oneSecond)) {
                stepIndex++;
                // 间隔获取数据,不显示所有秒的所有数据
                if (stepIndex % step == 0) {
                    double amount = timeAndAmount.get(oneSecond);
                    VolumeChartVO vo = new VolumeChartVO(PeriodEnum.SECOND, oneSecond, (int) amount);
                    volumeChartVOs.add(vo);
                }
            }
        }

        VolumeVO volumeVO = new VolumeVO(volumeChartVOs, TypeOfVolumn.INDUSTRY);
        volumeVO.setLabelInfo("分时成交量统计图", "时间", "成交量/手");

        return volumeVO;
    }

    @Override
    public List<RiseAndFallVO> getRiseAndFallList() {
        String endDate = DateCount.getToday();
        String startDate = DateCount.count(endDate, -30);

        List<RiseAndFallVO> result = new ArrayList<>();
        numberAndName = stockDataService.getStockNumAndName();

        Iterator<Map.Entry<String, String>> iter = numberAndName.entrySet().iterator();
        while (iter.hasNext()) {
            //对于每一只股票
            Map.Entry<String, String> entry = iter.next();
            String stockNumber = entry.getKey();
            String stockName = entry.getValue();
            try {
                StockVO stockVO = this.getStock(stockNumber,startDate,endDate,StockConstant.AllFields,null);
                int numberOfData = stockVO.numberOfDays();
                //获取昨日(今天不会有数据,数据包倒数第1个日期)涨跌幅
                int dateIndex = numberOfData-1;
                double change = stockVO.changeAtDay(dateIndex);
                List<Double> prices = stockVO.pricelistAtDay(dateIndex);
                double open = prices.get(0);
                double risePercent = change/open * 100;
                RiseAndFallVO riseAndFallVO = new RiseAndFallVO(stockName,risePercent);
                result.add(riseAndFallVO);
            } catch (NotFoundException | BadInputException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public LinearChartVO getLineChartVO(String number, String date) throws NotFoundException {

        StockPriceInfo stockPriceInfo = this.getTradeInfoVO(number, date);

        // get info form vo
        HashMap<String, Double> timeAndPrice = stockPriceInfo.getTimeAndPrice();
        HashMap<String, Double> timeAndAccumulatePrice = stockPriceInfo.getTimeAndAccumlatePrice();

        List<String> validSeconds = TimeConvert.getSecondsInTrade(1);

        HashMap<String, Double> xyItem1 = new HashMap<>();
        HashMap<String, Double> xyItem2 = new HashMap<>();

        int stepIndex = 0;
        int step = 20;

        for (String oneSecond : validSeconds) {
            // 如果这一秒存在数据
            if (stockPriceInfo.timeGotInfo(oneSecond)) {
                stepIndex++;
                // 间隔获取数据,不显示所有秒的所有数据
                if (stepIndex % step == 0) {
                    double price = timeAndPrice.get(oneSecond);
                    double accuPrice = timeAndAccumulatePrice.get(oneSecond);
                    xyItem1.put(oneSecond, price);
                    xyItem2.put(oneSecond, accuPrice);
                }
            }
        }

        double high = stockPriceInfo.getHighest();
        double low = stockPriceInfo.getLowest();
        double stepx = (high - low);

        double lowBound = low - stepx * 0.15;
        double upBound = high + stepx * 0.15;

        MyChartSeries series1 = new MyChartSeries("即时价格", xyItem1);
        MyChartSeries series2 = new MyChartSeries("累计均价", xyItem2);

        List<MyChartSeries> myChartSeries = new ArrayList<>();
        myChartSeries.add(series1);
        myChartSeries.add(series2);

        return new LinearChartVO(LinearChartType.STOCK, myChartSeries, upBound, lowBound);
    }

    private StockPriceInfo getTradeInfoVO(String number, String date) throws NotFoundException {
        TradeInfoPO tradeInfoPO = this.stockDataService.getTradeInfo(number, date);
        return tradeInfoPO.getPriceInfoVOFromTradeInfoPO(tradeInfoPO);
    }

    /**
     * vo筛选器
     *
     * @param vo
     * @param ranges
     * @return
     */
    private StockVO filter(StockVO vo, List<ConditionSelect> ranges) throws BadInputException, NotFoundException {
        //若筛选条件为空直接返回vo
        if (ranges != null && !ranges.isEmpty()) {
            List<StockAttribute> atts = new ArrayList<StockAttribute>();
            List<StockAttribute> rawAtts = vo.getAttributes();

            //遍历每一天，选择有效的几天
            for (StockAttribute sAtt : rawAtts) {
                if (isValid(sAtt, ranges)) {
                    atts.add(sAtt);
                }
            }
            if (atts == null || atts.isEmpty()) {
                throw new NotFoundException("亲，您选的数据域无数据^_^");
            } else {
                vo.setAttributes(atts);
            }

        }
        return vo;

    }

    /**
     * 判断某一天的数据是否满足条件
     *
     * @param att
     * @param ranges
     * @return
     */
    private boolean isValid(StockAttribute att, List<ConditionSelect> ranges) throws BadInputException, NotFoundException {

        for (ConditionSelect con : ranges) {
            FormatCheck.isNumber(con.getFromNum());
            Double bottom = new Double(con.getFromNum());

            FormatCheck.isNumber(con.getToNum());
            Double top = new Double(con.getToNum());

            if (top < bottom) {
                throw new BadInputException("亲，请从小到大输入^_^");
            }

            String field = con.getField();

            String data = att.getAttribute(field);

            if (data == null) {
                throw new NotFoundException("亲，该筛选条件无数据^_^");
            } else {
                FormatCheck.isNumber(att.getAttribute(field));
                Double bd = new Double(att.getAttribute(field));

                if (bd.compareTo(top) > 0 || bd.compareTo(bottom) < 0) {
                    return false;
                }
            }

        }
        return true;

    }

}
