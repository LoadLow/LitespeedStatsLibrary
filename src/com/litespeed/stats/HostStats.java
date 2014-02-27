package com.litespeed.stats;

/**
 *
 * @author LoadLow
 */
public class HostStats {

    public String name = "Undefined";
    public int ProcessingRequests = 0;
    public float RequestsPerSecond = 0;
    public long TotalRequests = 0;
    public float CacheHitsPerSecond = 0;
    public long TotalCacheHits = 0;

    public synchronized void merge(HostStats stats) {
        this.ProcessingRequests += stats.ProcessingRequests;
        this.RequestsPerSecond += stats.RequestsPerSecond;
        this.TotalRequests += stats.TotalRequests;
        this.CacheHitsPerSecond += stats.CacheHitsPerSecond;
        this.TotalCacheHits += stats.TotalCacheHits;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("REQ_RATE [").append(name).append("]: ");
        builder.append("REQ_PROCESSING: ").append(ProcessingRequests).append(", ");
        builder.append("REQ_PER_SEC: ").append(RequestsPerSecond).append(", ");
        builder.append("TOT_REQS: ").append(TotalRequests).append(", ");
        builder.append("CACHE_HITS_PER_SEC: ").append(CacheHitsPerSecond).append(", ");
        builder.append("TOTAL_CACHE_HITS: ").append(TotalCacheHits);
        return builder.toString();
    }
}
