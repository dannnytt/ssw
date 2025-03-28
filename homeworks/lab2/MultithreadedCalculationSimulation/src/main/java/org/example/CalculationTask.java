package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class CalculationTask implements Runnable {

    private final int threadNum;
    private final int calculationLength;
    private int progress = 0;
    private long startTime;
    private boolean isCompleted = false;
    private final int speed;

    public CalculationTask(int threadNum, int calculationLength) {
        this.threadNum = threadNum;
        this.calculationLength = calculationLength;
        this.speed = ThreadLocalRandom.current().nextInt(   200, 300);
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        if (threadNum == 1) {
            System.out.print("\u001b[2J");
            System.out.flush();
        }

        for (int i = 0; i <= calculationLength; i++) {
            if (isCompleted) break;
            progress = i;
            printProgress();

            try { Thread.sleep(speed); }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.currentTimeMillis();
        isCompleted = true;
        printCompletion(endTime - startTime);
    }

    private synchronized void printProgress() {
        if (isCompleted) return;

        final int barLength = 40;
        int progressPercent = (int) ((double) progress / calculationLength * 100);
        int position = barLength * progressPercent / 100;

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < barLength; i++) {
            if (i < position) sb.append("=");
            else if (i == position) sb.append(">");
            else sb.append(" ");
        }
        sb.append(']').append(progressPercent).append("%");

        System.out.printf("\u001b[%d;0H", threadNum);
        System.out.printf("Thread %d (ID %d) %s\n",
                threadNum, Thread.currentThread().threadId(), sb);
        System.out.flush();
    }

    private synchronized void printCompletion(long duration) {
        System.out.printf("\u001b[%d;0H", threadNum);
        System.out.printf("Thread %d (ID %d) completed in %d ms.\u001b[K\n",
                threadNum, Thread.currentThread().threadId(), duration);
        System.out.print("\u001b[B");
        System.out.flush();
    }

}