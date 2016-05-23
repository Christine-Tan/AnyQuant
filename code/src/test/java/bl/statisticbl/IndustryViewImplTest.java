package bl.statisticbl;

import bl.blfactory.BLFactory;
import org.junit.Test;
import tool.enums.IndustryPeriodEnum;
import tool.enums.LinearChartType;
import tool.exception.NotFoundException;
import vo.analyse.industry.IndustryVO;
import vo.analyse.industry.IndustryVolumeVO;
import vo.chart.barchart.VolumeChartVO;
import vo.chart.barchart.VolumeVO;
import vo.chart.linearchart.LinearChartVO;

import java.io.IOException;

/**
 * Created by Seven on 16/4/11.
 */
public class IndustryViewImplTest {
//    IndustryViewImpl industryView;
//
//    public IndustryViewImplTest() throws IOException, NotFoundException {
//        industryView = new IndustryViewImpl(BLFactory.getInstance().getStockViewService());
//    }
//
//    @Test
//    public void testGetBasicIndustryInfo() throws Exception {
//        IndustryVO industryVO1=industryView.getBasicIndustryInfo("酒业", IndustryPeriodEnum.FIRST);
//        System.out.println(industryVO1.getIndustryBasicInfo().getAverageChange());
//        IndustryVO industryVO2=industryView.getBasicIndustryInfo("酒业", IndustryPeriodEnum.SECOND);
//        System.out.println(industryVO2.getIndustryBasicInfo().getAverageChange());
//        IndustryVO industryVO3=industryView.getBasicIndustryInfo("酒业", IndustryPeriodEnum.THIRD);
//        System.out.println(industryVO3.getIndustryBasicInfo().getAverageChange());
//        IndustryVO industryVO4=industryView.getBasicIndustryInfo("酒业", IndustryPeriodEnum.FOURTH);
//        System.out.println(industryVO4.getIndustryBasicInfo().getAverageChange());
//    }
//
//    @Test
//    public void testGetIndustryPrice() throws Exception{
//        LinearChartVO linearChartVO=industryView.getIndustryPrice("酒业", IndustryPeriodEnum.FIRST);
//        System.out.println(linearChartVO.getChartType());
//    }
//
//    @Test
//    public void testGetCompareLinearChartVO() throws Exception {
//        LinearChartVO linearChartVO=industryView.getCompareLinearChartVO("酒业", IndustryPeriodEnum.FIRST);
//        System.out.println(linearChartVO.getChartType());
//    }
//
//    @Test
//    public void testGetIndustryVolume() throws Exception {
//        IndustryVolumeVO industryVolumeVO=industryView.getIndustryVolume("酒业", IndustryPeriodEnum.FIRST);
//        for(VolumeChartVO volumeVO:industryVolumeVO.getList()){
//            System.out.println(volumeVO.getTime()+" volume:"+volumeVO.getVolumn());
//        }
//    }
}