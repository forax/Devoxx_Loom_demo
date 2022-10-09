package org.paumard.loom.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class F3_LockedThread {

  static class Counter {
    private int counter;
    private final ReentrantLock lock = new ReentrantLock();

    public int getAndIncrement() {
      lock.lock();
      try {
        return counter++;
      } finally {
        lock.unlock();
      }
    }

    @Override
    public String toString() {
        lock.lock();
        try {
          return "" + counter;
        } finally {
          lock.unlock();
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
