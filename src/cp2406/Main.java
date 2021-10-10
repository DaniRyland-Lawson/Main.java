package cp2406;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import org.apache.commons.collections.iterators.AbstractOrderedMapIteratorDecorator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    //[Product code,
// Bureau of Meteorology station number,
// Year,
// Month,
// Day,
// Rainfall amount (millimetres),
// Period over which rainfall was measured (days),
// Quality]
    public static final int MONTH_IDX = 3;
    public static final int DAY_IDX = 4;
    public static final int RAIN_IDX = 5;

    public static void main(String[] args) {
        System.out.println("Hello Assign 2 Let's do this");
// create map
        HashMap<String, Double> monthMap = new HashMap<>();
        HashMap<String, Double> dayMin = new HashMap<>();
        HashMap<String, Double> dayMax = new HashMap<>();
// open file
        try {
            FileReader reader = new FileReader(
                    "./src/cp2406/MountSheridanStationCNS.csv");
//            CSVReaderHeaderAware csv = new CSVReaderHeaderAware(reader);

// read file
            CSVReader csv = new CSVReader(reader);
            List<String[]> arr = csv.readAll();
            String[] header = arr.remove(0);
            System.out.println(Arrays.toString(arr.get(0)));
            for (int i = 0; i < arr.size(); i++) {
                String[] row = arr.get(i);
                System.out.println(Arrays.toString(row));
                collectMonth(monthMap, row);
                collectDay(dayMin, row, true);
                collectDay(dayMax, row, false);
//                collectMaxDay(dayMax, row);
            }
            System.out.println("OK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(monthMap);
        System.out.println(dayMin);
        System.out.println(dayMax);
        // write your code here
    }

    private static void collectMonth(HashMap<String, Double> map, String[] row) {
        String str = row[RAIN_IDX];
        String key = row[MONTH_IDX];
        double rain = 0;
        try {
            rain = Double.parseDouble(str);
        } catch (Exception e) {}

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
        } catch (Exception e) {}

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