package org.example.limitchecker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> getFileContents(String fileName) throws IOException {
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