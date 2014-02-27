package com.litespeed.stats;

/**
 *
 * @author LoadLow
 */
public class Stats {

    public final LitespeedStats litespeed;
    public final BandwidthStats bandwidth;
    public final ConnectionsStats connections;
    public final RequestsStats requests;
    public final BannedIPsStats bannedIPs;

    public Stats(String fileContent) {
        this.litespeed = new LitespeedStats(fileContent);
        this.bandwidth = new BandwidthStats(fileContent);
        this.connections = new ConnectionsStats(fileContent);
        this.requests = new RequestsStats(fileContent);
        this.bannedIPs = new BannedIPsStats(fileContent);
    }

    public synchronized void merge(Stats stats) {
        requests.merge(stats.requests);
        bannedIPs.merge(stats.bannedIPs);
        connections.merge(stats.connections);
        bandwidth.merge(stats.bandwidth);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(litespeed.toString()).append(StatsFile.LINE_SEPARATOR);
        builder.append(bandwidth.toString()).append(StatsFile.LINE_SEPARATOR);
        builder.append(connections.toString()).append(StatsFile.LINE_SEPARATOR);
        builder.append(requests.toString()).append(StatsFile.LINE_SEPARATOR);
        builder.append(bannedIPs.toString()).append(StatsFile.LINE_SEPARATOR);
        builder.append("EOF").append(StatsFile.LINE_SEPARATOR);
        return builder.toString();
    }
}
