package org.paumard.loom.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

public class C_MaxPlatformThreads {

    // --enable-preview

    public static void main(String[] args) throws InterruptedException {

        // platform thread
        var threads =
          IntStream.range(0, 10_000)
              .mapToObj(index ->
                    Thread.ofPlatform()
                          .name("platform-", index)
                          .unstarted(() -> {
                            System.out.println(index);
                            try {
                              Thread.sleep(2_000);
                            } catch (InterruptedException e) {
                              throw new AssertionError(e);
                            }
                          }))
                    .toList();

        var start = System.currentTimeMillis();
        threads.forEach(Thread::start);
        for (var thread : threads) {
            thread.join();
        }
        var end = System.currentTimeMillis();
        System.out.println("Time = " + (end - start));
    }
}

