package org.example;

import java.util.concurrent.*;


public class MultithreadedCalculationSimulation {

    private final static int threadsCount = 2;
    private final static int calculationLength = 30;

    public static void main(String[] args) {

        try (ExecutorService executor = Executors.newFixedThreadPool(threadsCount)) {
            for (int threadNum = 0; threadNum < threadsCount; threadNum++) {
                executor.submit(new CalculationTask(
                        threadNum + 1,
                        calculationLength
                ));
            }
            executor.shutdown();
        }
        moveCursorToEnd();
    }

    private static void moveCursorToEnd() {
        System.out.print("\u001b[" + (threadsCount + 1) + "B");
        System.out.flush();
    }
}