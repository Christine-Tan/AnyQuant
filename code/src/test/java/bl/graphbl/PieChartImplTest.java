package bl.graphbl;

import org.junit.Test;
import tool.exception.NotFoundException;
import vo.chart.piechart.PieChartVO;

import java.util.Map;
import java.util.Set;

/**
 * Created by JiachenWang on 2016/4/24.
 */
public class PieChartImplTest {
    PieChartBLImpl pieChartBLImpl;

    @Test
    public void testGetPieVolumnVO() throws NotFoundException {
        pieChartBLImpl = new PieChartBLImpl();
        PieChartVO vo = pieChartBLImpl.getPieVolumnVO("航天机电", "sh600151", "2016-03-01", "2016-04-20");
        System.out.println(vo.getTitle());
        Set<Map.Entry<String, Double>> vo_datas = vo.originMap.entrySet();
        for (Map.Entry<String, Double> vo_data : vo_datas) {
            System.out.println(vo_data.getKey() + " + " + vo_data.getValue());
        }
    }

    @Test
    public void testGetPieAmountVO() throws NotFoundException {
        pieChartBLImpl = new PieChartBLImpl();
        PieChartVO vo = pieChartBLImpl.getPieAmountVO("航天机电", "sh600151", "2016-03-01", "2016-04-20");
        System.out.println(vo.getTitle());
        Set<Map.Entry<String, Double>> vo_datas = vo.originMap.entrySet();
        for (Map.Entry<String, Double> vo_data : vo_datas) {
            System.out.println(vo_data.getKey() + " + " + vo_data.getValue());
        }
    }
}
