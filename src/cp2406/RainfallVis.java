package cp2406;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RainfallVis extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BOM Rainfall");


        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Months");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Rainfall");

        BarChart     barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data("January", 567));
        dataSeries1.getData().add(new XYChart.Data("February"  , 65));
        dataSeries1.getData().add(new XYChart.Data("March"  , 23));
        dataSeries1.getData().add(new XYChart.Data("April", 567));
        dataSeries1.getData().add(new XYChart.Data("May"  , 65));
        dataSeries1.getData().add(new XYChart.Data("June"  , 23));
        dataSeries1.getData().add(new XYChart.Data("July", 567));
        dataSeries1.getData().add(new XYChart.Data("August"  , 65));
        dataSeries1.getData().add(new XYChart.Data("September"  , 23));
        dataSeries1.getData().add(new XYChart.Data("October", 567));
        dataSeries1.getData().add(new XYChart.Data("November"  , 65));
        dataSeries1.getData().add(new XYChart.Data("December"  , 23));

        barChart.getData().add(dataSeries1);

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("2015");

        dataSeries2.getData().add(new XYChart.Data("January", 540));
        dataSeries2.getData().add(new XYChart.Data("February"  , 120));
        dataSeries2.getData().add(new XYChart.Data("March"  , 36));
        dataSeries2.getData().add(new XYChart.Data("April", 540));
        dataSeries2.getData().add(new XYChart.Data("May"  , 120));
        dataSeries2.getData().add(new XYChart.Data("June"  , 36));
        dataSeries2.getData().add(new XYChart.Data("July", 540));
        dataSeries2.getData().add(new XYChart.Data("August"  , 120));
        dataSeries2.getData().add(new XYChart.Data("September"  , 36));
        dataSeries2.getData().add(new XYChart.Data("October", 540));
        dataSeries2.getData().add(new XYChart.Data("November"  , 120));
        dataSeries2.getData().add(new XYChart.Data("December"  , 36));

        barChart.getData().add(dataSeries2);

        VBox vbox = new VBox(barChart);

        Scene scene = new Scene(vbox, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}