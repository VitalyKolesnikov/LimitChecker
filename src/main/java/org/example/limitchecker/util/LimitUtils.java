package org.example.limitchecker.util;

import com.google.gson.Gson;
import org.example.limitchecker.model.User;
import org.example.limitchecker.model.limit.Limit;
import org.example.limitchecker.model.limit.LotsInOrderLimit;
import org.example.limitchecker.model.limit.LotsInOrderPerUserLimit;
import org.example.limitchecker.model.limit.LotsInOrderPerUserPerSymbolLimit;

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

//        List<Class<?>> classes = new ArrayList<>();
//        classes.add(LotsInOrderLimit[].class);
//
//        for (Class<?> cls : classes) {
//            try (final FileReader fileReader = new FileReader("src/main/resources/limits/LotsInOrderLimit.txt")) {
//                result.addAll(Arrays.asList(gson.fromJson(fileReader, cls));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        try (final FileReader fileReader = new FileReader("src/main/resources/limits/LotsInOrderPerUserLimit.txt")) {
            final LotsInOrderPerUserLimit[] after = gson.fromJson(fileReader, LotsInOrderPerUserLimit[].class);
            result.addAll(Arrays.asList(after));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try (final FileReader fileReader = new FileReader("src/main/resources/limits/LotsInOrderPerUserPerSymbolLimit.txt")) {
//            final LotsInOrderPerUserPerSymbolLimit[] after = gson.fromJson(fileReader, LotsInOrderPerUserPerSymbolLimit[].class);
//            result.addAll(Arrays.asList(after));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return result;
    }

    public static void main(String[] args) {

        Gson gson = new Gson();
        File file;

        file = new File("src/main/resources/limits/LotsInOrderLimit.txt");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderLimit[] before = {new LotsInOrderLimit(70)};
            gson.toJson(before, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File("src/main/resources/limits/LotsInOrderPerUserLimit.txt");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderPerUserLimit[] before = {new LotsInOrderPerUserLimit(30, User.MIKE), new LotsInOrderPerUserLimit(40, User.JOHN)};
            gson.toJson(before, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File("src/main/resources/limits/LotsInOrderPerUserPerSymbolLimit.txt");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderPerUserPerSymbolLimit[] before = {
                    new LotsInOrderPerUserPerSymbolLimit(15, User.MIKE, "KIRK"),
                    new LotsInOrderPerUserPerSymbolLimit(20, User.ROBERT, "TRIL")};
            gson.toJson(before, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}