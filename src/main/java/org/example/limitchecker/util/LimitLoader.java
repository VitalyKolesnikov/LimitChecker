package org.example.limitchecker.util;

import com.google.gson.Gson;
import org.example.limitchecker.model.limit.*;
import org.example.limitchecker.repository.UserStorage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.limitchecker.repository.Database.LIMITS_PATH;

public class LimitLoader {

    private final String path;

    public LimitLoader(String path) {
        this.path = path;
    }

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
            try (final FileReader fileReader = new FileReader(path + name + ".json")) {
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
        UserStorage userStorage = new UserStorage();

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
                    new LotsInOrderPerUserLimit(50, userStorage.getByName("Mike")),
                    new LotsInOrderPerUserLimit(45, userStorage.getByName("Corey")),
                    new LotsInOrderPerUserLimit(55, userStorage.getByName("Sarah")),
                    new LotsInOrderPerUserLimit(60, userStorage.getByName("John"))
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "LotsInOrderPerUserPerSymbolLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final LotsInOrderPerUserPerSymbolLimit[] limitArr = {
                    new LotsInOrderPerUserPerSymbolLimit(15, userStorage.getByName("Mike"), "KIRK"),
                    new LotsInOrderPerUserPerSymbolLimit(15, userStorage.getByName("Sam"), "TSLA"),
                    new LotsInOrderPerUserPerSymbolLimit(15, userStorage.getByName("Charlie"), "ETSY"),
                    new LotsInOrderPerUserPerSymbolLimit(15, userStorage.getByName("Barbara"), "LAZY"),
                    new LotsInOrderPerUserPerSymbolLimit(20, userStorage.getByName("Robert"), "TRIL")
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
                    new SymbolPositionPerUserLimit(-75, 75, "TRIL", userStorage.getByName("Mike")),
                    new SymbolPositionPerUserLimit(-70, 70, "NLS", userStorage.getByName("Sam")),
                    new SymbolPositionPerUserLimit(-80, 85, "TSLA", userStorage.getByName("Charlie")),
                    new SymbolPositionPerUserLimit(-90, 100, "OSTK", userStorage.getByName("Barbara")),
                    new SymbolPositionPerUserLimit(-90, 110, "SE", userStorage.getByName("Robert")),
                    new SymbolPositionPerUserLimit(-95, 90, "PTON", userStorage.getByName("Michele"))
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "UserOrdersLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final UserOrdersLimit[] limitArr = {
                    new UserOrdersLimit(65, userStorage.getByName("Mike")),
                    new UserOrdersLimit(60, userStorage.getByName("Corey")),
                    new UserOrdersLimit(75, userStorage.getByName("Sarah")),
                    new UserOrdersLimit(55, userStorage.getByName("John"))
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "UserOrdersPerSymbolLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final UserOrdersPerSymbolLimit[] limitArr = {
                    new UserOrdersPerSymbolLimit(25, userStorage.getByName("Sam"), "SAVA"),
                    new UserOrdersPerSymbolLimit(30, userStorage.getByName("Charlie"), "BNTX"),
                    new UserOrdersPerSymbolLimit(15, userStorage.getByName("Barbara"), "FUTU"),
                    new UserOrdersPerSymbolLimit(33, userStorage.getByName("Robert"), "ZS")
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(LIMITS_PATH + "UserMoneyPositionLimit.json");
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final UserMoneyPositionLimit[] limitArr = {
                    new UserMoneyPositionLimit(-90000, 90000, userStorage.getByName("Sam")),
                    new UserMoneyPositionLimit(-100000, 100000, userStorage.getByName("Mike")),
                    new UserMoneyPositionLimit(-110000, 110000, userStorage.getByName("John")),
                    new UserMoneyPositionLimit(-115000, 115000, userStorage.getByName("Sarah")),
                    new UserMoneyPositionLimit(-120000, 120000, userStorage.getByName("Robert")),
                    new UserMoneyPositionLimit(-125000, 125000, userStorage.getByName("Barbara"))
            };
            gson.toJson(limitArr, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}