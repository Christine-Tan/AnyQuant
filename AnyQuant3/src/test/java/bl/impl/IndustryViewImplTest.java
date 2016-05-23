package bl.impl;

import model.barchart.VolumeVO;
import model.common.LinearChartVO;
import model.industry.IndustryVO;
import org.junit.Test;
import util.enums.IndustryPeriodEnum;
import util.exception.NotFoundException;

import java.io.IOException;

/**
 * Created by kylin on 16/5/19.
 */
public class IndustryViewImplTest {

    IndustryViewImpl industryView;

    public IndustryViewImplTest() throws IOException, NotFoundException {
        industryView = new IndustryViewImpl(new GetStockStub());
    }
    @Test
    public void getBasicIndustryInfo() throws Exception {
        IndustryVO industryVO1=industryView.getBasicIndustryInfo("酒业", IndustryPeriodEnum.FIRST);
        System.out.println(industryVO1.getIndustryBasicInfo().getAverageChange());
        IndustryVO industryVO4=industryView.getBasicIndustryInfo("酒业", IndustryPeriodEnum.FOURTH);
        System.out.println(industryVO4.getIndustryBasicInfo().getAverageChange());
    }

    @Test
    public void getIndustryPrice() throws Exception {
        LinearChartVO linearChartVO=industryView.getIndustryPrice("酒业", IndustryPeriodEnum.FIRST);
        System.out.println(linearChartVO.getChartType());
    }

    @Test
    public void getCompareLinearChartVO() throws Exception {
        LinearChartVO linearChartVO=industryView.getCompareLinearChartVO("酒业", IndustryPeriodEnum.FIRST);
        System.out.println(linearChartVO.getChartType());
    }

    @Test
    public void getIndustryVolume() throws Exception {
        VolumeVO industryVolumeVO=industryView.getIndustryVolume("酒业", IndustryPeriodEnum.FIRST);
        System.out.println(industryVolumeVO.getList().size());
    }

}