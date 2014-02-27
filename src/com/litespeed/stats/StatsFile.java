package com.litespeed.stats;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author LoadLow
 */
public class StatsFile {

    public static String LINE_SEPARATOR = System.getProperty("line.separator");
    private String path;

    public StatsFile(String path) {
        this.path = path;
    }

    public String load() throws Exception {
        File file = new File(path);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine()).append(LINE_SEPARATOR);
            }
            return fileContents.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            scanner = null;
        }
    }

    public Stats loadAll() throws Exception {
        return new Stats(load());
    }
}
