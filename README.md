LitespeedStatsLibrary
=====================

Parse LiteSpeed statistics files in Java
Parse and get rtreport stats of litespeed in Java

<h4>How does it work?</h4>
This lib gets the content of rtreport stats (you must specify the path of the file),
and parses this stats using Pattern and Regex.

<h4>Requirements :</h4>
- LiteSpeed Enterprise or VPS Edition (sorry but I only tested with these versions ...)
- The folder of rtreport logs (generally /tmp/lshttpd/)
- The name of rtreport files, generally ".rtreport", but if u have a multicore version,
  files are ".rtreport1", ".rtreport2" etc..
- The total path of files : "/tmp/lshttpd/.rtreport" or "/tmp/lshttpd/.rtreport1" etc..

<h4>Available stats :</h4>
- LiteSpeed stats (version, Uptime in long and in string)
- Bandwidth stats (Input Bps and Output Bps with SSL and without SSL)
- Banned IP stats (a string array)
- Connection stats (MaxConnections, MaxSSLConnections, PlainConnections, AvailableConnections, IdleConnections, SSLConnections, AvailableSSLConnections)
- Requests stats per virtual host (ProcessingRequests, RequestsPerSecond, TotalRequests, CacheHitsPerSecond, TotalCacheHits)
- Requests stats for ALL hosts (global requests stats)

<h4>Exemple using this library :</h4>
`````java
        String filePath = "/tmp/lshttpd/.rtreport";
        StatsFile file = new StatsFile(filePath);
        try {
            Stats stats = file.loadAll();
            System.out.println("Loaded virtualHosts of log.rtreport : ");
            for (HostStats hstats : stats.requests.hosts.values()) {
                System.out.println("- " + hstats.name);
            }
            System.out.println("Litespeed blocked IPS: ");
            for (String addr : stats.bannedIPs.array) {
                System.out.println("- " + addr);
            }
            System.out.println("Litespeed version " + stats.litespeed.Version);
            System.out.println("Uptime(in hours) : " + stats.litespeed.Uptime / (3600 * 1000));
            System.out.println("Uptime(in millis) : " + stats.litespeed.Uptime);
            System.out.println("Uptime : " + stats.litespeed.UptimeStr);
            System.out.println("Total Requests : " + stats.requests.hosts.get("ALL").TotalRequests);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
`````

(If u have a multicore version, u must merge all file Stats on a single Stat instance)
