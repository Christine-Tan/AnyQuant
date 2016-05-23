package ui.chartui.linearchart;

import tool.exception.NotFoundException;
import vo.chart.linearchart.LinearChartVO;

/**
 * Created by kylin on 16/5/19.
 */
public interface LinearChartBLService {

    LinearChartVO getLineChartVO(String number, String date) throws NotFoundException;
}
