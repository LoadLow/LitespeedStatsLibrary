package com.litespeed.stats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LoadLow
 */
public class LitespeedStats {

    private final static String VERSION_REGEX = "VERSION: ([A-Za-z /0-9.]+)";
    private final static Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);
    private final static String UPTIME_REGEX_DAYS = "UPTIME: ([0-9]+) days ([0-9]{2}):([0-9]{2}):([0-9]{2})";
    private final static Pattern UPTIME_PATTERN_DAYS = Pattern.compile(UPTIME_REGEX_DAYS);
    private final static String UPTIME_REGEX = "UPTIME: ([0-9]{2}):([0-9]{2}):([0-9]{2})";
    private final static Pattern UPTIME_PATTERN = Pattern.compile(UPTIME_REGEX);
    public String Version = null;
    public long Uptime = 0;
    public String UptimeStr = "0 days 00:00:00";

    public LitespeedStats(String fileContent) {
        Matcher m = VERSION_PATTERN.matcher(fileContent);

        if (m.find()) {//Just the first line :'>
            try {
                Version = m.group(1);
            } catch (Exception e) {
            }
        }

        m = UPTIME_PATTERN_DAYS.matcher(fileContent);

        if (m.find()) {//Just the first line :'>
            try {
                int days = Integer.parseInt(m.group(1));
                int hours = Integer.parseInt(m.group(2));
                int minutes = Integer.parseInt(m.group(3));
                int seconds = Integer.parseInt(m.group(4));

                Uptime = (days * 3600 * 24) + (hours * 3600) + (minutes * 60) + (seconds);
                Uptime *= 1000;
                UptimeStr = days + " days " + (hours < 10 ? ("0" + hours) : (hours)) + ":" + (minutes < 10 ? ("0" + minutes) : (minutes)) + ":" + (seconds < 10 ? ("0" + seconds) : (seconds));

            } catch (Exception e) {
            }
        } else {
            m = UPTIME_PATTERN.matcher(fileContent);

            if (m.find()) {//Just the first line :'>
                try {
                    int hours = Integer.parseInt(m.group(1));
                    int minutes = Integer.parseInt(m.group(2));
                    int seconds = Integer.parseInt(m.group(3));

                    Uptime = (hours * 3600) + (minutes * 60) + (seconds);
                    Uptime *= 1000;
                    UptimeStr = (hours < 10 ? ("0" + hours) : (hours)) + ":" + (minutes < 10 ? ("0" + minutes) : (minutes)) + ":" + (seconds < 10 ? ("0" + seconds) : (seconds));
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VERSION: ").append(Version).append(StatsFile.LINE_SEPARATOR);
        builder.append("UPTIME: ").append(UptimeStr);
        return builder.toString();
    }
}
