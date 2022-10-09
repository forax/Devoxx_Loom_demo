package org.paumard.loom.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class F0_SleepingThreads {

    public static void main(String[] args) throws InterruptedException {

        var counter = new AtomicInteger();
        var threads = IntStream.range(0, 4_000)
              .mapToObj(index -> Thread.ofVirtual()
                    .unstarted(() -> {
                        try {
                            if (index == 10) {
                                System.out.println(Thread.currentThread());
                            }
                            Thread.sleep(1_000);
                            if (index == 10) {
                                System.out.println(Thread.currentThread());
                            }
                            counter.incrementAndGet();
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
        System.out.println("# counter = " + counter);
        System.out.println("Time = " + (end - start));

    }
}
