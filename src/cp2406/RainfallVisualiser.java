//package cp2406;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.geometry.Insets;
//import javafx.geometry.Point2D;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.effect.BlendMode;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import textio.TextIO;
//
//import java.io.File;
//import java.io.FileReader;
//import java.time.Year;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class RainfallVisualiser extends Application {
//
//    // utility constants
//    private static final int GUI_WIDTH = 1200;
//    private static final int GUI_HEIGHT = 600;
//    private static final int CHART_WIDTH = 1000; // width in pixels of the chart
//    private static final int CHART_HEIGHT = 500; // height in pixels of the chart
//    private static final int RECORD_LIST_WIDTH = 180; // width in pixels of the record list view
//    private static final int MONTHS_IN_YEAR = 12;
//    private static final int CHART_PADDING = 75; // padding in pixels of the chart area
//    private static final int CHART_AXIS_WEIGHT = 4; // width in pixels of axis lines
//    private static final int CHART_GUIDE_WEIGHT = 1; // height and width in pixels of chart guides
//    private static final int TICK_STEP_SIZE = 10; // step size for y axis ticks
//    private static final int TICK_MARGIN = 35; // margin in pixels for y axis ticks
//    private static final double CHART_SCALE_ACCURACY = 0.01; // accuracy for chart data scaling
//    private static final String STATUS_PREFIX = "STATUS: "; // prefix for status label messages
//    private static final String X_AXIS_TEXT = "YEAR"; // text for x axis
//    private static final String Y_AXIS_TEXT = "RAINFALL (MM):"; // text for y axis
//
//    // web colours
//    private static final Color COLOUR_DARK_BLUE = Color.web("#004c6d");
//    private static final Color COLOUR_BLUE = Color.web("#008fb1");
//    private static final Color COLOUR_LIGHT_BLUE = Color.web("#b1ffff");
//    private static final Color COLOUR_WHITE_BLUE = Color.web("#d8ffff");
//    private static final Color COLOUR_GREY = Color.web("#dcdcdc");
//    private static final Color COLOUR_GREEN = Color.web("#99cc00");
//    private static final Color COLOUR_RED = Color.web("#cc3300");
//
//    // fonts
//    public static final Font FONT_CHART_TITLE = Font.font("Arial", FontWeight.NORMAL, 35);
//    public static final Font FONT_INTERFACE_NORMAL = Font.font("Arial", FontWeight.NORMAL, 11);
//
//    // javafx components
//    private Pane chartPane; // node for adding chart elements
//    private Label statusLabel; // label to display interface status
//    private Menu stationMenu; // menu to display stations
//    private Circle statusCircle; // visual aid to display interface status
//    private TextField directoryTextField; // directory user input text field
//    private TextField stationTextField; // station user input text field
//    private ListView<String> recordsListView; // list for for displaying station records
////    public ChoiceBox stationLoaderChoiceBox;
//
//
//    // chart themes
//    private final HashMap<String, BlendMode> chartThemes = new HashMap<>() {{
//        put("Dark", BlendMode.DIFFERENCE);
//        put("Light", BlendMode.MULTIPLY);
//    }};
//
//    private System FileLoader;
//
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("BOM Rainfall");
//
//
//        BorderPane root = new BorderPane();
//        Scene scene = new Scene(root);
//
//        // BorderPane center components
//        chartPane = new Pane();
//
//        // BorderPane bottom components
//        HBox statusBox = new HBox();
//        statusCircle = new Circle(5);
//        statusLabel = new Label();
//        statusLabel.setPadding(new Insets(0, 0, 0, 4));
//        statusLabel.setFont(FONT_INTERFACE_NORMAL);
//
//        statusBox.setPadding(new Insets(6));
//        statusBox.getChildren().addAll(statusCircle, statusLabel);
//
//        // BorderPane top components
//        VBox topPanelVBox = new VBox(); // top panel VBox contains the navigation bar and station loader HBox
//
//        // menu bar
//        MenuBar menuBar = new MenuBar();
//        Menu fileMenu = new Menu("File");
//        stationMenu = new Menu("Loaded Stations");
//        Menu themeMenu = new Menu("Themes");
//
//        MenuItem quitMenuItem = new MenuItem("Quit");
//        quitMenuItem.setOnAction(action -> Platform.exit());
//
//        MenuItem resetMenuItem = new MenuItem("Clear Interface");
//        resetMenuItem.setOnAction(action -> clearLoadedInterface());
//
//        fileMenu.getItems().addAll(resetMenuItem, quitMenuItem);
//
//        //add menu item for each theme
//        for (Map.Entry<String, BlendMode> theme : chartThemes.entrySet()) {
//            MenuItem themeMenuItem = new MenuItem(theme.getKey());
//            themeMenuItem.setOnAction(action -> chartPane.setBlendMode(theme.getValue()));
//            themeMenu.getItems().add(themeMenuItem);
//        }
//
//        menuBar.getMenus().addAll(fileMenu, themeMenu, stationMenu);
//
//        //Need to introduce a choice box for each of the stations
//        ChoiceBox stationLoaderChoiceBox = new ChoiceBox();
//
//        stationLoaderChoiceBox.getItems().add("CopperlodeDam");
//        stationLoaderChoiceBox.getItems().add("KurandaRailwayStation");
//        stationLoaderChoiceBox.getItems().add("TinarooFallsStation");
//        stationLoaderChoiceBox.getItems().add("MountSheridanStation");
//
//        stationLoaderChoiceBox.setOnAction((event) -> {
//            int selectedIndex = stationLoaderChoiceBox.getSelectionModel().getSelectedIndex();
//            Object selectedItem = stationLoaderChoiceBox.getSelectionModel().getSelectedItem();
//
////            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
////            System.out.println("   ChoiceBox.getValue(): " + stationLoaderChoiceBox.getValue());
//        });
//
//
//        // station loader HBox
//        HBox stationLoaderHBox = new HBox(20);
//        stationLoaderHBox.setPadding(new Insets(6));
//
//        directoryTextField = new TextField();
//        directoryTextField.setPromptText("BOMRainfallFiles");
//
//        stationTextField = new TextField();
//        stationTextField.setPromptText("Load Station from...KurandaRailwayStation, TinarooFallsDam or CopperloadDam ");
//
//        Button loadButton = new Button("Load");
//        loadButton.setOnMouseClicked(mouseEvent -> loadStation());
//
//        stationLoaderHBox.getChildren().addAll(directoryTextField, stationTextField, loadButton);
//        HBox.setHgrow(directoryTextField, Priority.ALWAYS);
//        HBox.setHgrow(stationTextField, Priority.ALWAYS);
//
//        topPanelVBox.getChildren().addAll(menuBar, stationLoaderHBox, stationLoaderChoiceBox);
//
//        // BorderPane right components
//        recordsListView = new ListView<>();
//        recordsListView.setPrefSize(RECORD_LIST_WIDTH, GUI_HEIGHT);
//
//        // add components to root
//        root.setTop(topPanelVBox);
//        root.setBottom(statusBox);
//        root.setRight(recordsListView);
//        root.setCenter(chartPane);
//
//        stage stage;
//        stage.setScene(scene);
//        stage.setTitle("BOM RAINFALL VISUALISER");
//        stage.setWidth(GUI_WIDTH);
//        stage.setHeight(GUI_HEIGHT);
//        stage.setResizable(false);
//        stage.show();
//
//        updateStatus("READY...", true);
//    }
//
//    private void loadStation() {
//        String directoryName = directoryTextField.getText();
//        String stationName = stationTextField.getText();
//
//        try {
//
//            // attempt to load the station data
//            StationFiles station = FileReader.load(directoryName, stationName);
//
//            // add station to library
//            loadedStations.put(station.getName().toLowerCase(), station);
//
//            refreshStationsMenu();
//            drawLoadedInterface(station);
//        } catch (FileLoader.LoaderException err) {
//            updateStatus(err.getMessage(), false);
//        }
//    }
//
//    private void refreshStationsMenu() {
//
//        // clear existing menu items
//        stationMenu.getItems().clear();
//
//        // add stations to menu
//        for (StationFiles station : loadedStations.values()) {
//
//            MenuItem stationMenuItem = new MenuItem(station.getName());
//            stationMenuItem.setOnAction(event -> drawLoadedInterface(station));
//
//            stationMenu.getItems().add(stationMenuItem);
//        }
//    }
//
//    private void drawLoadedInterface(StationFiles station) {
//
//        // clear loaded interface to remove existing charts
//        clearLoadedInterface();
//
//        Point2D chartTopLeft = new Point2D(CHART_PADDING, CHART_PADDING);
//        Point2D chartTopRight = new Point2D(CHART_WIDTH - CHART_PADDING, CHART_PADDING);
//        Point2D chartBtmLeft = new Point2D(CHART_PADDING, CHART_HEIGHT - CHART_PADDING);
//        //Point2D chartBtmRight = new Point2D(CHART_WIDTH - CHART_PADDING, CHART_HEIGHT - CHART_PADDING);
//
//        double pixelsPerYear; // how wide a year should be in the chart area
//        double pixelsPerMonth; // how wide a month should be in the chart area
//        double chartWidth = chartTopRight.getX() - chartTopLeft.getX(); // the width of the chart
//        double chartHeight = chartBtmLeft.getY() - chartTopLeft.getY(); // the height of the chart
//
//        ArrayList<Integer> distinctYears = station.getDistinctYears();
//
//        pixelsPerYear = chartWidth / distinctYears.size();
//        pixelsPerMonth = pixelsPerYear / MONTHS_IN_YEAR;
//
//        Rectangle background = new Rectangle(0, 0, chartPane.getWidth(), chartPane.getHeight());
//        background.setFill(COLOUR_GREY);
//
//        // draw station title
//        Text stationText = new Text(station.getName());
//        stationText.setFont(FONT_CHART_TITLE);
//        stationText.setX(chartTopLeft.getX());
//        stationText.setY(chartTopLeft.getY() - 30);
//
//        // draw axis titles
//        Text xAxisText = new Text(X_AXIS_TEXT);
////        xAxisText.setFill(COLOUR_DARK_BLUE);
//        xAxisText.setFont(FONT_INTERFACE_NORMAL);
//        xAxisText.setX(chartBtmLeft.getX() + (chartWidth / 2));
//        xAxisText.setY(chartBtmLeft.getY() + 46);
//
//        Text yAxisText = new Text(Y_AXIS_TEXT);
//        yAxisText.setFont(FONT_INTERFACE_NORMAL);
//        yAxisText.setFill(COLOUR_DARK_BLUE);
//        yAxisText.setRotate(270);
//        yAxisText.setX(chartBtmLeft.getX() - 90);
//        yAxisText.setY(chartBtmLeft.getY() - (chartHeight / 2));
//
//        chartPane.getChildren().addAll(background, stationText, xAxisText, yAxisText);
//
//        // draw distinct year backgrounds and X Axis titles on the chart
//        for (int i = 0; i < distinctYears.size(); i++) {
//
//            Point2D xAxisPos = new Point2D(chartBtmLeft.getX() + (pixelsPerYear * i), chartBtmLeft.getY() + 20);
//
//            Color switchColour = i % 2 == 0 ? COLOUR_LIGHT_BLUE : COLOUR_WHITE_BLUE;
//            Rectangle distinctYearRectangle = new Rectangle(xAxisPos.getX(),
//                    chartTopLeft.getY(), pixelsPerYear, chartHeight);
//            distinctYearRectangle.setFill(switchColour);
//
//            Rectangle tickIndicator = new Rectangle(xAxisPos.getX() + (pixelsPerYear / 2), chartBtmLeft.getY(),
//                    1, 10);
//            tickIndicator.setFill(COLOUR_DARK_BLUE);
//
//            // conditional year ticks, adjust text in accordance to amount of records
//            Text tickText = new Text(String.valueOf(distinctYears.get(i)));
//            tickText.setFill(COLOUR_DARK_BLUE);
//            tickText.setFont(FONT_INTERFACE_NORMAL);
//            tickText.setRotate(45);
//            tickText.setX(xAxisPos.getX() + (pixelsPerYear / 2));
//            tickText.setY(xAxisPos.getY() + 5);
//
//            if (pixelsPerYear >= 30) {
//                chartPane.getChildren().addAll(tickText, tickIndicator);
//            } else if (pixelsPerYear < 30 && i % 3 == 0) {
//                chartPane.getChildren().addAll(tickText, tickIndicator);
//            }
//
//            chartPane.getChildren().add(distinctYearRectangle);
//        }
//
//        // draw chart axis lines
//        Rectangle yAxisLine = new Rectangle(chartTopLeft.getX() - CHART_AXIS_WEIGHT,
//                chartTopLeft.getY(), CHART_AXIS_WEIGHT, chartHeight);
//        yAxisLine.setFill(COLOUR_DARK_BLUE);
//
//        Rectangle xAxisLine = new Rectangle(chartBtmLeft.getX(), chartBtmLeft.getY(), chartWidth, CHART_AXIS_WEIGHT);
//        xAxisLine.setFill(COLOUR_DARK_BLUE);
//
//        chartPane.getChildren().addAll(xAxisLine, yAxisLine);
//
//        // give the chart Y axis titles
//        for (int i = 0; i <= 100; i += TICK_STEP_SIZE) {
//
//            // rainfall value to be displayed on the axis
//            int tickInteger = (int) Math.round((station.getRainfallMax() / 100) * i);
//            String tickValue = String.valueOf(tickInteger);
//
//            Point2D tickPos = new Point2D(chartTopLeft.getX() - TICK_MARGIN,
//                    chartBtmLeft.getY() - ((chartHeight / 100) * i));
//
//            Rectangle tickIndicator = new Rectangle(chartTopLeft.getX() - 6, tickPos.getY(), 6, 1);
//            tickIndicator.setFill(COLOUR_DARK_BLUE);
//
//            Text tickText = new Text(tickValue);
//            tickText.setFill(COLOUR_DARK_BLUE);
//            tickText.setFont(FONT_INTERFACE_NORMAL);
//            tickText.setX(tickPos.getX());
//            tickText.setY(tickPos.getY());
//
//            chartPane.getChildren().addAll(tickText, tickIndicator);
//
//            // draw guide lines across the chart
//            if (tickInteger != 0) {
//                for (int j = 0; j < chartWidth; j++) {
//                    if (j % 2 == 0) {
//                        Point2D guidePosition = new Point2D(chartBtmLeft.getX() + j, tickPos.getY());
//
//                        Rectangle guideRectangle = new Rectangle(guidePosition.getX(), guidePosition.getY(),
//                                CHART_GUIDE_WEIGHT, CHART_GUIDE_WEIGHT);
//                        chartPane.getChildren().add(guideRectangle);
//                    }
//                }
//            }
//        }
//
//        // apply scaling division to contain data within the chart
//        double applyScalingDivision = 0;
//        while ((station.getRainfallMax() / applyScalingDivision) > chartHeight) {
//            applyScalingDivision += CHART_SCALE_ACCURACY;
//        }
//
//        // draw rainfall bars and add records to list view
//        for (RecordFiles record : station.values()) {
//
//            recordsListView.getItems().add(record.getCSVString()); // add record to list view
//
//            int yearIndex = distinctYears.indexOf(record.getYear());
//
//            // apply scaling division to record total rainfall
//            double recordScaledTotalRain = record.getRainfallTotal() / applyScalingDivision;
//
//            double barXPos = chartBtmLeft.getX() + (pixelsPerYear * yearIndex) +
//                    (pixelsPerMonth * (record.getMonth() - 1));
//
//            Point2D barPos = new Point2D(barXPos, chartBtmLeft.getY() - recordScaledTotalRain);
//
//            Rectangle rainfallBar = new Rectangle(barPos.getX(), barPos.getY(), pixelsPerMonth, recordScaledTotalRain);
//            rainfallBar.setFill(COLOUR_BLUE);
//
//            StatisticsBox recordStatBox = new StatisticsBox(chartPane, record);
//
//            rainfallBar.setOnMouseEntered(mouseEvent -> {
//
//                rainfallBar.setFill(COLOUR_DARK_BLUE);
//                recordStatBox.show(mouseEvent.getX(), mouseEvent.getY());
//            });
//
//            rainfallBar.setOnMouseExited(mouseEvent -> {
//
//                recordStatBox.hide();
//                rainfallBar.setFill(COLOUR_BLUE);
//            });
//
//            chartPane.getChildren().add(rainfallBar);
//        }
//
//
////    private void clearLoadedInterface() {
////
////        chartPane.getChildren().clear();
////        recordsListView.getItems().clear();
////    }
//
////    private void updateStatus(String status, boolean isGraceful) {
////
////        // update status label text
////        statusLabel.setText(STATUS_PREFIX + status);
////
////        // update status circle colour
////        Color statusColour = isGraceful ? COLOUR_GREEN : COLOUR_RED;
////        statusCircle.setFill(statusColour);
////    }
//
//
////    private void getChoice(ChoiceBox<String> choiceBox){
////        String bom = choiceBox.getValue();
////        System.out.println(bom);
////    }
//
//
//        class StationFiles extends LinkedHashMap<String, RecordFiles> {
//
//            private final String name;
//
//            public StationFiles(String name) {
//                this.name = name;
//            }
//
//
//            public ArrayList<Integer> getDistinctYears() {
//
//                ArrayList<Integer> distinctYears = new ArrayList<>();
//
//                for (RecordFiles record : this.values()) {
//                    int year = record.getYear();
//                    if (!distinctYears.contains(year)) {
//                        distinctYears.add(year);
//                    }
//                }
//
//                return distinctYears;
//            }
//
//            public double getRainfallMax() {
//
//                double stationRainfallMax = -1; // rainfall max sentinel
//                for (RecordFiles record : this.values()) {
//
//                    if (record.getRainfallTotal() > stationRainfallMax) {
//                        stationRainfallMax = record.getRainfallTotal();
//                    }
//                }
//
//                return stationRainfallMax;
//            }
//
//            public String getName() {
//                return name;
//            }
//        }
//
//        class RecordFiles {
//
//            private final int month; // month of the record
//            private final int year; // year of the record
//            private double rainfallMin; // minimum daily rainfall amount in the record
//            private double rainfallMax; // maximum daily rainfall amount in the record
//            private double rainfallTotal; // total cumulative rainfall amount in the record
//            public static final String CSV_HEADER = "year,month,total,min,max"; // header row for analysed csv files
//
//            public RecordFiles(int year, int month, double rainfallTotal, double rainfallMin, double rainfallMax) {
//
//                this.year = year;
//                this.month = month;
//                this.rainfallTotal = rainfallTotal;
//                this.rainfallMin = rainfallMin;
//                this.rainfallMax = rainfallMax;
//            }
//
//            public static String makeKey(int year, int month) {
//                return String.format("_Y:%d_M:%d", year, month);
//            }
//
//
//            public String getCSVString() {
//
//                return String.format("%d,%d,%1.2f,%1.2f,%1.2f", year, month, rainfallTotal,
//                        rainfallMin, rainfallMax);
//            }
//
//
//            public String getHumanMonth() {
//                return switch (month) {
//                    case 1 -> "January";
//                    case 2 -> "February";
//                    case 3 -> "March";
//                    case 4 -> "April";
//                    case 5 -> "May";
//                    case 6 -> "June";
//                    case 7 -> "July";
//                    case 8 -> "August";
//                    case 9 -> "September";
//                    case 10 -> "October";
//                    case 11 -> "November";
//                    case 12 -> "December";
//                    default -> "Unknown";
//                };
//            }
//
//
//            public int getMonth() {
//                return month;
//            }
//
//
//            public int getYear() {
//                return year;
//            }
//
//
//            public double getRainfallMin() {
//                return rainfallMin;
//            }
//
//
//            public RecordFiles setRainfallMin(double rainfallMin) {
//                this.rainfallMin = rainfallMin;
//                return this;
//            }
//
//            public double getRainfallMax() {
//                return rainfallMax;
//            }
//
//
//            public RecordFiles setRainfallMax(double rainfallMax) {
//                this.rainfallMax = rainfallMax;
//                return this;
//            }
//
//            public double getRainfallTotal() {
//                return rainfallTotal;
//            }
//
//
//            public RecordFiles setRainfallTotal(double rainfallTotal) {
//                this.rainfallTotal = rainfallTotal;
//                return this;
//            }
//
//            public static StationFiles load(String directoryName, String stationName) throws LoaderException {
//                if (directoryName.length() < 1) {
//                    throw new LoaderException("Please enter directory name: BOMRainfallFiles ");
//                } else if (stationName.length() < 1) {
//                    throw new LoaderException("What Station would you like you analyse?");
//                }
//                String analysedFilePath = "./" + directoryName + "/" + stationName + "_analysed.csv";
//                String rawFilePath = "./" + directoryName + "/" + stationName + ".csv";
//
//                File analysedCSVFile = new File(analysedFilePath);
//                File rawCSVFile = new File(rawFilePath);
//
//                StationFiles station = new StationFiles(stationName);
//
//                if (!analysedCSVFile.exists() && !rawCSVFile.exists()) {
//                    throw new LoaderException("The File does not exist");
//                }
//
//
//                String filePath = analysedCSVFile.exists() ? analysedFilePath : rawFilePath;
//
//                TextIO.readFile(filePath);
//
//                boolean isHeaderRow = true;
//
//                while ((!TextIO.eof())) {
//
//                    String rawRow = TextIO.getln();
//                    String[] rowColumns = rawRow.split(",", -1);
//
//
//                    if (isHeaderRow) {
//                        isHeaderRow = false;
//                        continue;
//                    }
//
//                    int yearIndex = analysedCSVFile.exists() ? YEAR_IDX_ANALYSED : YEAR_IDX;
//                    int monthIndex = analysedCSVFile.exists() ? MONTH_IDX_ANALYSED : MONTH_IDX;
//
//                    try {
//
//                        int recordYear = Integer.parseInt(rowColumns[yearIndex]);
//                        int recordMonth = Integer.parseInt(rowColumns[monthIndex]);
//
//                        if (!isYearValid(recordYear)) {
//                            throw new LoaderException("Invalid record Year value!");
//                        } else if (!isMonthValid(recordMonth)) {
//                            throw new LoaderException("Invalid record Month value");
//
//                        }
//
//
//                        String yearMonthKey = RecordFiles.makeKey(recordYear, recordMonth);
//
//
//                        if (analysedCSVFile.exists()) {
//
//                            double recordTotalRainfall = Double.parseDouble(rowColumns[TOTAL_IDX_ANALYSED]);
//                            double recordMinRainfall = Double.parseDouble(rowColumns[MIN_IDX_ANALYSED]);
//                            double recordMaxRainfall = Double.parseDouble(rowColumns[MAX_IDX_ANALYSED]);
//
//                            RecordFiles record = new RecordFiles(recordYear, recordMonth, recordTotalRainfall, recordMinRainfall, recordMaxRainfall);
//
//                            station.put(yearMonthKey, record);
//
//                        } else {
//
//                            double recordRainfall = (rowColumns[RAIN_IDX].length() > 0) ?
//                                    Double.parseDouble(rowColumns[RAIN_IDX]) : 0;
//
//                            if (station.containsKey(yearMonthKey)) {
//
//                                RecordFiles existingRecord = station.get(yearMonthKey);
//                                double totalRainfall = existingRecord.getRainfallTotal();
//
//
//                                if (recordRainfall < existingRecord.getRainfallMin()) {
//                                    existingRecord.setRainfallMin(recordRainfall);
//                                }
//
//                                if (recordRainfall > existingRecord.getRainfallMax()) {
//                                    existingRecord.setRainfallMax(recordRainfall);
//                                }
//
//                                totalRainfall += recordRainfall;
//                                existingRecord.setRainfallTotal(totalRainfall);
//                            } else {
//
//                                RecordFiles newRecord = new RecordFiles(recordYear, recordMonth,
//                                        recordRainfall, recordRainfall, recordRainfall);
//
//                                station.put(yearMonthKey, newRecord);
//                            }
//                        }
//                    } catch (NumberFormatException err) {
//                        throw new LoaderException("nonnumerical value encountered!");
//                    }
//                }
//                if (station.values().isEmpty()) {
//                    throw new LoaderException("No data found!");
//                }
//
//                if (!analysedCSVFile.exists() && !station.isEmpty()) {
//                    writeAnalysedCSVFile(station, analysedFilePath);
//                }
//
//                return station;
//
//            }
//
//            //
//
//            private static void writeAnalysedCSVFile(StationFiles station, String filePath) {
//
//                // write records to analysed csv file
//                TextIO.writeFile(filePath);
//                TextIO.putln(RecordFiles.CSV_HEADER);
//                for (RecordFiles record : station.values()) {
//                    TextIO.putln(record.getCSVString());
//                }
//
//                TextIO.writeStandardOutput();
//            }
//
//
//            private static boolean isYearValid(int year) {
//                return year > 0 && year <= Year.now().getValue();
//            }
//
//            private static boolean isMonthValid(int month) {
//                return month >= 1 && month <= 12;
//            }
//
//
//            static class LoaderException extends Exception {
//                LoaderException(String message) {
//                    super(message);
//                }
//            }
//        }
//
//
//
//        }
//
//    private void clearLoadedInterface() {
//    }
//
//    private class StationFiles {
//    }
//}
//
