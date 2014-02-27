package com.litespeed.stats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LoadLow
 */
public class BandwidthStats {

    private final static String REGEX = "BPS_IN: ([0-9]+), BPS_OUT: ([0-9]+), SSL_BPS_IN: ([0-9]+), SSL_BPS_OUT: ([0-9]+)";
    private final static Pattern PATTERN = Pattern.compile(REGEX);
    public int InputBitsPerSecond = 0;
    public int OutputBitsPerSecond = 0;
    public int InputSSLBitsPerSecond = 0;
    public int OutputSSLBitsPerSecond = 0;

    public BandwidthStats(String fileContent) {
        Matcher m = PATTERN.matcher(fileContent);

        if (m.find()) {//Just the first line :'>
            try {
                InputBitsPerSecond = Integer.parseInt(m.group(1));
                OutputBitsPerSecond = Integer.parseInt(m.group(2));
                InputSSLBitsPerSecond = Integer.parseInt(m.group(3));
                OutputSSLBitsPerSecond = Integer.parseInt(m.group(4));
            } catch (Exception e) {
            }
        }
    }

    public synchronized void merge(BandwidthStats stats) {
        this.InputBitsPerSecond += stats.InputBitsPerSecond;
        this.OutputBitsPerSecond += stats.OutputBitsPerSecond;
        this.InputSSLBitsPerSecond += stats.InputSSLBitsPerSecond;
        this.OutputSSLBitsPerSecond += stats.OutputSSLBitsPerSecond;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BPS_IN: ").append(InputBitsPerSecond).append(", ");
        builder.append("BPS_OUT: ").append(OutputBitsPerSecond).append(", ");
        builder.append("SSL_BPS_IN: ").append(InputSSLBitsPerSecond).append(", ");
        builder.append("SSL_BPS_OUT: ").append(OutputSSLBitsPerSecond);
        return builder.toString();
    }
}
