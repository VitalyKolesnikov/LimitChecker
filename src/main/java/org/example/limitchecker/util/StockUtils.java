package org.example.limitchecker.util;

import org.example.limitchecker.model.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.limitchecker.util.FileUtils.getFileContents;

public class StockUtils {

    private static final String STOCKS = "src/main/resources/top100.csv";

    public static List<Stock> getStocks() {
        List<Stock> result = new ArrayList<>();
        try {
            for (String line : getFileContents(STOCKS)) {
                String[] stockArr = line.split(",");
                result.add(new Stock(stockArr[0], Double.parseDouble(stockArr[5])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Stock getRandomStock() {
        List<Stock> list = getStocks();
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}