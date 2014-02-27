package com.litespeed.stats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LoadLow
 */
public class ConnectionsStats {

    private final static String REGEX = "MAXCONN: ([0-9]+), MAXSSL_CONN: ([0-9]+), PLAINCONN: ([0-9]+), AVAILCONN: ([0-9]+), IDLECONN: ([0-9]+), SSLCONN: ([0-9]+), AVAILSSL: ([0-9]+)";
    private final static Pattern PATTERN = Pattern.compile(REGEX);
    public int MaxConnections = 0;
    public int MaxSSLConnections = 0;
    public int PlainConnections = 0;
    public int AvailableConnections = 0;
    public int IdleConnections = 0;
    public int SSLConnections = 0;
    public int AvailableSSLConnections = 0;

    public ConnectionsStats(String fileContent) {
        Matcher m = PATTERN.matcher(fileContent);

        if (m.find()) {//Just the first line :'>
            try {
                MaxConnections = Integer.parseInt(m.group(1));
                MaxSSLConnections = Integer.parseInt(m.group(2));
                PlainConnections = Integer.parseInt(m.group(3));
                AvailableConnections = Integer.parseInt(m.group(4));
                IdleConnections = Integer.parseInt(m.group(5));
                SSLConnections = Integer.parseInt(m.group(6));
                AvailableSSLConnections = Integer.parseInt(m.group(7));
            } catch (Exception e) {
            }
        }
    }

    public synchronized void merge(ConnectionsStats stats) {
        this.MaxConnections += stats.MaxConnections;
        this.MaxSSLConnections += stats.MaxSSLConnections;
        this.PlainConnections += stats.PlainConnections;
        this.AvailableConnections += stats.AvailableConnections;
        this.IdleConnections += stats.IdleConnections;
        this.SSLConnections += stats.SSLConnections;
        this.AvailableSSLConnections += stats.AvailableSSLConnections;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MAXCONN: ").append(MaxConnections).append(", ");
        builder.append("MAXSSL_CONN: ").append(MaxSSLConnections).append(", ");
        builder.append("PLAINCONN: ").append(PlainConnections).append(", ");
        builder.append("AVAILCONN: ").append(AvailableConnections).append(", ");
        builder.append("IDLECONN: ").append(IdleConnections).append(", ");
        builder.append("AVAILCONN: ").append(AvailableSSLConnections).append(", ");
        builder.append("SSLCONN: ").append(SSLConnections);
        return builder.toString();
    }
}
