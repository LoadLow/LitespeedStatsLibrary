package com.litespeed.stats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LoadLow
 */
public class BannedIPsStats {

    private final static String REGEX = "BLOCKED_IP: ([0-9., ]+)";
    private final static Pattern PATTERN = Pattern.compile(REGEX);
    public String[] array = new String[0];

    public BannedIPsStats(String fileContent) {
        Matcher m = PATTERN.matcher(fileContent);

        if (m.find()) {//Just the first line :'>
            try {
                array = m.group(1).replaceAll(" ", "").split(",");
            } catch (Exception e) {
            }
        }
    }

    public synchronized void merge(BannedIPsStats stats) {
        array = mergeArray(array, stats.array);
    }

    public static String[] mergeArray(String[] a1, String[] a2) {
        String[] toReturn = new String[a1.length + a2.length];
        System.arraycopy(a1, 0, toReturn, 0, a1.length);
        System.arraycopy(a2, 0, toReturn, a1.length, a2.length);
        return toReturn;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BLOCKED_IP: ");
        for (String ip : array) {
            if (builder.length() > "BLOCKED_IP: ".length()) {
                builder.append(", ");
            }
            builder.append(ip);
        }
        return builder.toString();
    }
}
