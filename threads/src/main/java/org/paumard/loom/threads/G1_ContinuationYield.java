package org.paumard.loom.threads;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class G1_ContinuationYield {

    // --enable-preview --add-exports java.base/jdk.internal.vm=ALL-UNNAMED

    public static void main(String[] args) throws InterruptedException {
        var scope = new ContinuationScope("HelloLoom");

        var continuations =
              IntStream.range(10, 20)
                    .mapToObj(index -> new Continuation(scope,
                          () -> {
                              System.out.println("A-" + index + " [" + Thread.currentThread() + "]");
                              Continuation.yield(scope);
                              System.out.println("B-" + index + " [" + Thread.currentThread() + "]");
                              Continuation.yield(scope);
                              System.out.println("C-" + index + " [" + Thread.currentThread() + "]");
                          }))
                    .toList();

        var threads = continuations.stream().map(c -> Thread.ofVirtual().start(c::run)).toList();
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Step 1");

        threads = continuations.stream().map(c -> Thread.ofVirtual().start(c::run)).toList();
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Step 2");

        threads = continuations.stream().map(c -> Thread.ofVirtual().start(c::run)).toList();
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Step 3");
    }
}
