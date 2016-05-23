package ui;

import bl.graphbl.BarChartBLImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tool.enums.PeriodEnum;
import ui.chartui.barchart.BarChartController;
import vo.chart.barchart.SingleVolumeVO;

/**
 * Created by JiachenWang on 2016/4/24.
 */
public class TestBarChart  extends Application {

    BarChartController controller;
    BarChartBLImpl barChartBLImpl;
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        TestBarChart.primaryStage = primaryStage;

        barChartBLImpl = new BarChartBLImpl();
        SingleVolumeVO vo = barChartBLImpl.getSingleVolumeVO("航天机电", "sh600151", PeriodEnum.DAY,"2016-03-01","2016-04-20");
        controller = new BarChartController();

        Scene scene = new Scene(controller.createBarChart(vo));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
