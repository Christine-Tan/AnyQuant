package bl.service;

import bl.impl.GetStockStub;
import bl.impl.IndustryViewImpl;
import model.industry.IndustryVO;
import org.junit.Test;
import util.enums.IndustryPeriodEnum;
import util.exception.NotFoundException;

/**
 * Created by kylin on 16/5/16.
 */
public class IndustryViewServiceTest {
    private GetStockService getStockService = new GetStockStub();

    private String number = "sh600519";
    private IndustryViewService industryViewService = new IndustryViewImpl(getStockService);

    public IndustryViewServiceTest() throws NotFoundException {
    }

    @Test
    public void getBasicIndustryInfo() throws Exception {
        String industryName = getStockService.getIndustryName(number);
        IndustryPeriodEnum industryPeriod = IndustryPeriodEnum.SECOND;

        IndustryVO industryVO = industryViewService.getBasicIndustryInfo(industryName,industryPeriod);
    }

    @Test
    public void getIndustryPrice() throws Exception {

    }

    @Test
    public void getCompareLinearChartVO() throws Exception {

    }

    @Test
    public void getIndustryVolume() throws Exception {

    }

}