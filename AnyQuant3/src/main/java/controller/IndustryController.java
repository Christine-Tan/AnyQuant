package controller;

import bl.factory.BLFactory;
import bl.service.GetStockService;
import bl.service.IndustryViewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.analyse.RiseAndFallVO;
import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.industry.IndustryVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import util.enums.IndustryPeriodEnum;
import util.exception.BadInputException;
import util.exception.NotFoundException;
import util.json.JsonConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 16/5/24.
 * All rights reserved.
 */
@Controller
public class IndustryController {

    private IndustryViewService industryViewService;

    private GetStockService getStockService;

    public IndustryController() throws NotFoundException {
        industryViewService = BLFactory.getInstance().getIndustryViewService();
        getStockService = BLFactory.getInstance().getGetStockService();
    }

    @RequestMapping(value = "/industry", method = RequestMethod.GET)
    public ModelAndView getIndustry(HttpServletRequest httpServletRequest)
            throws NotFoundException, IOException, BadInputException {

        //行业数据模型
//        String industryName = getStockService.getIndustryName(number);
        String industryName = "酒业";
        IndustryPeriodEnum industryPeriod = IndustryPeriodEnum.FOURTH;

        Map<String, Object> model = new HashMap<>();
        this.pushIndustryModel(model, industryName, industryPeriod);
        return new ModelAndView("industry", model);
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


}
