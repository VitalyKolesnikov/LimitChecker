package org.example.limitchecker.util;

import com.google.gson.Gson;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LimitUtils {

    private static final String LIMITS_PATH = "src/main/resources/limits/";

    public static List<Limit> loadLimits() {

        List<Limit> result = new ArrayList<>();
        Gson gson = new Gson();

        List<Class<? extends Limit[]>> classes = new ArrayList<>();
        classes.add(LotsInOrderLimit[].class);
        classes.add(LotsInOrderPerUserLimit[].class);
        classes.add(LotsInOrderPerUserPerSymbolLimit[].class);
        classes.add(StockPositionLimit[].class);

        for (Class<? extends Limit[]> cls : classes) {
            String name = cls.getSimpleName();
            name = name.substring(0, name.length() - 2);
            try (final FileReader fileReader = new FileReader(LIMITS_PATH + name + ".txt")) {
                final Limit[] after = gson.fromJson(fileReader, cls);
                result.addAll(Arrays.asList(after));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // this main method creates limit files
    public static void main(String[] args) {

        Gson gson = new Gson();
        File file;

        file = new File(LIMITS_PATH + "LotsInOrderLimit.txt");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderLimit[] limitArr = {new LotsInOrderLimit(70)};
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "LotsInOrderPerUserLimit.txt");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderPerUserLimit[] limitArr = {
                    new LotsInOrderPerUserLimit(30, User.MIKE),
                    new LotsInOrderPerUserLimit(40, User.JOHN)};
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "LotsInOrderPerUserPerSymbolLimit.txt");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderPerUserPerSymbolLimit[] limitArr = {
                    new LotsInOrderPerUserPerSymbolLimit(15, User.MIKE, "KIRK"),
                    new LotsInOrderPerUserPerSymbolLimit(20, User.ROBERT, "TRIL")};
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "StockPositionLimit.txt");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final StockPositionLimit[] limitArr = {new StockPositionLimit(-150, 150)};
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}