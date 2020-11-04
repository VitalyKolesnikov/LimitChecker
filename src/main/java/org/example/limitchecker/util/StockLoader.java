package org.example.limitchecker.util;

import org.example.limitchecker.model.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StockLoader {

    private static final String STOCKS = "src/main/resources/stocks.csv";

    public List<Stock> loadStocks() {
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

    public Stock getRandomStock() {
        List<Stock> list = loadStocks();
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public List<String> getFileContents(String fileName) throws IOException {
        List<String> result = new ArrayList<>();
        Path path = Paths.get(fileName);
        BufferedReader reader = Files.newBufferedReader(path);
        String str;
        while ((str = reader.readLine()) != null) {
            result.add(str);
        }
        return result;
    }

}