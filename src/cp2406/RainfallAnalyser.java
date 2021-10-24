package cp2406;

/*
 CP2406 Programming III Assignment Alpha-Version
 Danielle Lawson
 */


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RainfallAnalyser {
    /**
     Product code [0]
     Bureau of Meteorology station number [1]
     Year [2]
     Month [3]
     Day [4]
     Rainfall amount (millimetres) [5]
     Period over which rainfall was measured (days) [6]
     Quality [7]
     */

    public static final int MONTH_IDX = 3;
    public static final int DAY_IDX = 4;
    public static final int RAIN_IDX = 5;


    public static void main(String[] args) {
//        System.out.println("Hello Assign 2");

// create map
        HashMap<String, Double> monthMap = new HashMap<>();
        HashMap<String, Double> dayMin = new HashMap<>();
        HashMap<String, Double> dayMax = new HashMap<>();

// open file
        try {
            FileReader reader = new FileReader(
                    "BOMRainFallFiles/MountSheridanStationCNS.csv");

// read file
            CSVReader csv = new CSVReader(reader);
            List<String[]> arr = csv.readAll();
//            System.out.println(Arrays.toString(arr.get(0)));
            for (String[] row : arr) {
//                System.out.println(Arrays.toString(row));
                collectMonth(monthMap, row);
                collectDay(dayMin, row, true);
                collectDay(dayMax, row, false);
            }
//            System.out.println("OK");

        } catch (IOException e) {
            e.printStackTrace();
        }

// testing output
//        System.out.println(yearMap);
//        System.out.println(monthMap);
//        System.out.println(dayMin);
//        System.out.println(dayMax);

        //create output file called MountSheridanStation_Analysed.csv
        String outPath = "BOMRainFallFiles/MountSheridanStation_Analysed.csv";
        String[] header ={"Month", "Total rain", "Month-day", "Min rain", "Max rain"};
        myWriteToCSV(monthMap, dayMin, dayMax, outPath, header );



/*   Testing of data values into csv files
        outPath = "BOMRainFallFiles/MyMonthMap.csv";
        header ={"Month", "Total rain"};
        myWriteToCSV(monthMap, dayMin, monthMap, outPath, header );

        outPath = "BOMRainFallFiles/MyMinDay.csv";
        header = new String[]{"Month-day", "Min rain"};
        myWriteToCSV(monthMap, dayMin, dayMin, outPath, header );

        outPath = "BOMRainFallFiles/MyMaxDay.csv";
        header = new String[]{"Month-day", "Max rain"};
        myWriteToCSV(monthMap, dayMin, dayMax, outPath, header );

 */
        }
        private static void myWriteToCSV(HashMap<String, Double> monthMap, HashMap<String, Double> dayMin, HashMap<String, Double> map, String outPath, String[] header) {
        CSVWriter csv;
        try {
            FileWriter writer = new FileWriter(outPath);
            csv = new CSVWriter(writer);
//            String[] header = {"Month", "Total rain"};
            csv.writeNext(header);
            for (Map.Entry<String, Double> entry: map.entrySet()) {
                String key = entry.getKey();
                Double v = entry.getValue();
                String[] row = {key, v.toString() };
//                System.out.println(Arrays.toString(row));
                csv.writeNext(row);
            }
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void collectMonth(HashMap<String, Double> map, String[] row) {
        String str = row[RAIN_IDX];
        String key = row[MONTH_IDX];
        double rain = 0;
        try {
            rain = Double.parseDouble(str);
        } catch (Exception ignored) {}

        if (map.containsKey(key)){
            double oldRain = map.get(key);
            double newRain = oldRain + rain;
            map.put(key, newRain);
        }
        else {
            map.put(key, rain);
        }
    }
    private static void collectDay(HashMap<String, Double> map, String[] row, boolean isMin) {
        String str = row[RAIN_IDX];
        String month = row[MONTH_IDX];
        String day = row[DAY_IDX];
        String key = month + "-day-" + day;
        double rain = 0;
        try {
            rain = Double.parseDouble(str);
        } catch (Exception ignored) {

        }
        if (map.containsKey(key)){
            double oldRain = map.get(key);
            double newRain = 0;
            if (isMin) {
                newRain = Math.min(oldRain, rain);
            } else {
                newRain = Math.max(oldRain, rain);
            }
            map.put(key, newRain);
        }
        else {
            map.put(key, rain);


        }

    }



}
