package org.paumard.loom.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class E_HowManyVirtualThreads {

    // --enable-preview

    public static void main(String[] args) throws InterruptedException {

        var pools = ConcurrentHashMap.newKeySet();
        var pThreads = ConcurrentHashMap.newKeySet();
        var pool = Pattern.compile("ForkJoinPool-[\\d?]");
        var worker = Pattern.compile("worker-[\\d?]");

        var threads = IntStream.range(0, 10_000)
              .mapToObj(i -> Thread.ofVirtual()
                    .unstarted(() -> {
                        try {
                          Thread.sleep(2_000);
                        } catch (InterruptedException e) {
                          throw new AssertionError(e);
                        }
                        var name = Thread.currentThread().toString();
                        var poolMatcher = pool.matcher(name);
                        if (poolMatcher.find()) {
                            pools.add(poolMatcher.group());
                        }
                        var workerMatcher = worker.matcher(name);
                        if (workerMatcher.find()) {
                            pThreads.add(workerMatcher.group());
                        }
                    }))
              .toList();

        var start = System.currentTimeMillis();
        for (var thread : threads) {
            thread.start();
        }
        for (var thread : threads) {
            thread.join();
        }
        var end = System.currentTimeMillis();
        System.out.println("# cores = " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time = " + (end - start));
        System.out.println("Pools");
        pools.forEach(System.out::println);
        System.out.println("Platform threads (" + pThreads.size() + ")");
        new LinkedHashSet<>(pThreads).forEach(System.out::println);
    }
}
