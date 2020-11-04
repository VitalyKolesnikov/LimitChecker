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

public class LimitLoader {

    private static final String LIMITS_PATH = "src/main/resources/limits/";

    public List<Limit> loadLimits() {

        List<Limit> result = new ArrayList<>();
        Gson gson = new Gson();

        List<Class<? extends Limit[]>> classes = new ArrayList<>();
        classes.add(LotsInOrderLimit[].class);
        classes.add(LotsInOrderPerUserLimit[].class);
        classes.add(LotsInOrderPerUserPerSymbolLimit[].class);
        classes.add(SymbolPositionLimit[].class);
        classes.add(SymbolPositionPerUserLimit[].class);
        classes.add(UserOrdersLimit[].class);
        classes.add(UserOrdersPerSymbolLimit[].class);
        classes.add(UserMoneyPositionLimit[].class);

        for (Class<? extends Limit[]> cls : classes) {
            String name = cls.getSimpleName();
            name = name.substring(0, name.length() - 2);
            try (final FileReader fileReader = new FileReader(LIMITS_PATH + name + ".json")) {
                final Limit[] limitArr = gson.fromJson(fileReader, cls);
                result.addAll(Arrays.asList(limitArr));
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

        file = new File(LIMITS_PATH + "LotsInOrderLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderLimit[] limitArr = {new LotsInOrderLimit(70)};
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "LotsInOrderPerUserLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderPerUserLimit[] limitArr = {
                    new LotsInOrderPerUserLimit(50, User.MIKE),
                    new LotsInOrderPerUserLimit(45, User.COREY),
                    new LotsInOrderPerUserLimit(55, User.SARAH),
                    new LotsInOrderPerUserLimit(60, User.JOHN)
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "LotsInOrderPerUserPerSymbolLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderPerUserPerSymbolLimit[] limitArr = {
                    new LotsInOrderPerUserPerSymbolLimit(15, User.MIKE, "KIRK"),
                    new LotsInOrderPerUserPerSymbolLimit(15, User.SAM, "TSLA"),
                    new LotsInOrderPerUserPerSymbolLimit(15, User.CHARLIE, "ETSY"),
                    new LotsInOrderPerUserPerSymbolLimit(15, User.BARBARA, "LAZY"),
                    new LotsInOrderPerUserPerSymbolLimit(20, User.ROBERT, "TRIL")
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "SymbolPositionLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final SymbolPositionLimit[] limitArr = {
                    new SymbolPositionLimit(-150, 150, "KIRK"),
                    new SymbolPositionLimit(-120, 120, "PRPLW"),
                    new SymbolPositionLimit(-130, 140, "ZM"),
                    new SymbolPositionLimit(-100, 140, "FVRR"),
                    new SymbolPositionLimit(-90, 90, "PRTS"),
                    new SymbolPositionLimit(-115, 135, "NIO")
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "SymbolPositionPerUserLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final SymbolPositionPerUserLimit[] limitArr = {
                    new SymbolPositionPerUserLimit(-75, 75, "TRIL", User.MIKE),
                    new SymbolPositionPerUserLimit(-70, 70, "NLS", User.SAM),
                    new SymbolPositionPerUserLimit(-80, 85, "TSLA", User.CHARLIE),
                    new SymbolPositionPerUserLimit(-90, 100, "OSTK", User.BARBARA),
                    new SymbolPositionPerUserLimit(-90, 110, "SE", User.ROBERT),
                    new SymbolPositionPerUserLimit(-95, 90, "PTON", User.MICHELE)
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "UserOrdersLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final UserOrdersLimit[] limitArr = {
                    new UserOrdersLimit(65, User.MIKE),
                    new UserOrdersLimit(60, User.COREY),
                    new UserOrdersLimit(75, User.SARAH),
                    new UserOrdersLimit(55, User.JOHN)
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "UserOrdersPerSymbolLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final UserOrdersPerSymbolLimit[] limitArr = {
                    new UserOrdersPerSymbolLimit(25, User.SAM, "SAVA"),
                    new UserOrdersPerSymbolLimit(30, User.CHARLIE, "BNTX"),
                    new UserOrdersPerSymbolLimit(15, User.BARBARA, "FUTU"),
                    new UserOrdersPerSymbolLimit(33, User.ROBERT, "ZS")
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "UserMoneyPositionLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final UserMoneyPositionLimit[] limitArr = {
                    new UserMoneyPositionLimit(-90000, 90000, User.SAM),
                    new UserMoneyPositionLimit(-100000, 100000, User.MIKE),
                    new UserMoneyPositionLimit(-110000, 110000, User.JOHN),
                    new UserMoneyPositionLimit(-115000, 115000, User.SARAH),
                    new UserMoneyPositionLimit(-120000, 120000, User.ROBERT),
                    new UserMoneyPositionLimit(-125000, 125000, User.BARBARA)
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}