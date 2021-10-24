package cp2406;

/*
 CP2406 Programming III Assignment Alpha-Version
 Danielle Lawson
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import textio.TextIO;


// Draw a chart that effectively represents rainfall data.

public class RainfallVisualiser extends Application {

   // Draws a picture. The parameters width and height give the size of the drawing area, in pixels.

     public void drawPicture(GraphicsContext g, int width, int height) {
         //Defining the x an y axis
         CategoryAxis xAxis = new CategoryAxis();
         NumberAxis yAxis = new NumberAxis(200, 600, 100);
         //Setting labels for the axes
         xAxis.setLabel("Months");
         yAxis.setLabel("Rainfall (MM)");

         //Creating a Barchart
         var barChart = new BarChart(xAxis, yAxis);

         //Preparing the data for BarChart
         XYChart.Series series = new XYChart.Series();

         series.setName("BOM Rainfall Data");

         //Creating a stack pane to hold the chart
         StackPane pane = new StackPane(barChart);
         pane.setPadding(new Insets(15, 15, 15, 15));
         pane.setStyle("-fx-background-color: #8ce791");
         //Setting the Scene
         Scene scene = new Scene(pane, 595, 300);
     }
// end drawPicture()

    //------ Implementation details: DO NOT EDIT THIS ------
    public void start(Stage stage) {
        int width = 218 * 6 + 40;
        int height = 500;
        Canvas canvas = new Canvas(width, height);
        drawPicture(canvas.getGraphicsContext2D(), width, height);
        BorderPane root = new BorderPane(canvas);
        root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Rainfall Visualiser");
        stage.show();
        stage.setResizable(false);
    }
    //------ End of Implementation details ------
    public static void main(String[] args) {

//        System.out.print("Enter path: ");
//        var path = TextIO.getln();

        var path = "BOMRainFallFiles/MyMaxDay.csv";
        TextIO.readFile(path);
        launch(args);
    }

} // end RainfallVisualiser