package com.litespeed.stats;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LoadLow
 */
public class RequestsStats {

    private final static String REGEX = "REQ_RATE \\[([A-Z_a-z0-9-,.]{0,255})\\]: REQ_PROCESSING: ([0-9]+), REQ_PER_SEC: ([0-9.]+), TOT_REQS: ([0-9]+), CACHE_HITS_PER_SEC: ([0-9.]+), TOTAL_CACHE_HITS: ([0-9]+)";
    private final static Pattern PATTERN = Pattern.compile(REGEX);
    public Map<String, HostStats> hosts = new ConcurrentHashMap<String, HostStats>();

    public synchronized void merge(RequestsStats stats) {
        for (HostStats hStats : stats.hosts.values()) {
            if (hosts.containsKey(hStats.name)) {
                hosts.get(hStats.name).merge(hStats);
            } else {
                hosts.put(hStats.name, hStats);
            }
        }
    }

    public RequestsStats(String fileContent) {
        Matcher m = PATTERN.matcher(fileContent);

        while (m.find()) {
            try {
                HostStats host = new HostStats();
                host.name = m.group(1);
                if ("".equals(host.name)) {
                    host.name = "ALL";
                }
                host.ProcessingRequests = Integer.parseInt(m.group(2));
                host.RequestsPerSecond = Float.parseFloat(m.group(3));
                host.TotalRequests = Long.parseLong(m.group(4));
                host.CacheHitsPerSecond = Float.parseFloat(m.group(5));
                host.TotalCacheHits = Long.parseLong(m.group(6));
                hosts.put(host.name, host);

            } catch (Exception e) {
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (HostStats host : hosts.values()) {
            if (builder.length() > 0) {
                builder.append(StatsFile.LINE_SEPARATOR);
            }
            builder.append(host.toString());
        }
        return builder.toString();
    }
}
