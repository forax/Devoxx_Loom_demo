package org.paumard.loom.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

public class F1_SyncedThread {

    static class Counter {
      private int counter;
      private final Object lock = new Object();

      public int getAndIncrement() {
        synchronized (lock) {
          return counter++;
        }
      }

      @Override
      public String toString() {
        synchronized (lock) {
          return "" + counter;
        }
      }
    }

    public static void main(String[] args) throws InterruptedException {
        var counter = new Counter();
        var threads = IntStream.range(0, 4_000)
              .mapToObj(index -> Thread.ofVirtual()
                    .unstarted(() -> {
                        if (index == 10) {
                            System.out.println(Thread.currentThread());
                        }
                        counter.getAndIncrement();
                        if (index == 10) {
                            System.out.println(Thread.currentThread());
                        }
                        counter.getAndIncrement();
                        if (index == 10) {
                            System.out.println(Thread.currentThread());
                        }
                        counter.getAndIncrement();
                        if (index == 10) {
                            System.out.println(Thread.currentThread());
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
