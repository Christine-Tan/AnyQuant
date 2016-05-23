package bl.graphbl;

import org.junit.Test;
import tool.enums.PeriodEnum;
import tool.exception.BadInputException;
import tool.exception.NotFoundException;
import vo.chart.barchart.MixSingleVolumeVO;
import vo.chart.barchart.SingleVolumeVO;
import vo.chart.barchart.VolumeChartVO;

/**
 * Created by JiachenWang on 2016/4/24.
 */
public class BarChartImplTest {

//    BarChartBLImpl barChartBLImpl;
//
//    @Test
//    public void testGetSingleVolumeVO() throws NotFoundException, BadInputException {
//        barChartBLImpl = new BarChartBLImpl();
//        SingleVolumeVO vo = barChartBLImpl.getSingleVolumeVO("航天机电", "sh600151", PeriodEnum.DAY,"2016-03-01","2016-04-20");
//        System.out.println(vo.getStock_name());
//        System.out.println(vo.getStock_num());
//        for (VolumeChartVO tmp:vo.getList()){
//            System.out.println(tmp.getTime()+" -------  "+tmp.getVolumn());
//        }
//    }
//
//    @Test
//    public void testGetMixVolumeVO() throws NotFoundException, BadInputException {
//        barChartBLImpl = new BarChartBLImpl();
//        MixSingleVolumeVO vo = barChartBLImpl.getMixSingleVolumeVO("航天机电", "sh600151", PeriodEnum.DAY,"2016-03-01","2016-04-20");
//        System.out.println(vo.getStock_name());
//        System.out.println(vo.getStock_num());
//        for (VolumeChartVO tmp:vo.getList()){
//            System.out.println(tmp.getTime() + " -------  " + tmp.getVolumn() + " -------  " + tmp.getSumPrice());
//        }
//    }
}
