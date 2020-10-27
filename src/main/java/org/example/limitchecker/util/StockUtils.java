package org.example.limitchecker.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.limitchecker.util.FileUtils.getFileContents;

public class StockUtils {

    private static final String STOCKS = "src/main/resources/top100.csv";

    public static List<String> getStocks() {
        List<String> result = new ArrayList<>();
        try {
            for (String line : getFileContents(STOCKS)) {
                result.add(line.split(",")[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getRandomStock() {
        List<String> list = getStocks();
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}