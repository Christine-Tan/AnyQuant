package bl.graphbl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.chartui.piechart.PieChartController;
import vo.chart.piechart.PieChartVO;

/**
 * Created by JiachenWang on 2016/4/25.
 */
public class TestPieChart extends Application {

    PieChartController controller;
    PieChartBLImpl pieChartBLImpl;
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        TestPieChart.primaryStage = primaryStage;

        pieChartBLImpl = new PieChartBLImpl();
        PieChartVO vo = pieChartBLImpl.getPieVolumnVO("航天机电", "sh600151", "2016-03-01", "2016-04-20");
        controller = new PieChartController();

        Scene scene = new Scene(controller.createPieChart(vo));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
