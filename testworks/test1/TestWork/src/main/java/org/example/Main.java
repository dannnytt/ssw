package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        final int threadsCount = 10;
        final int filesCount = 5;
        final int iterations = 5;

        Semaphore semaphore = new Semaphore(filesCount);

        List<FileResource> files = new ArrayList<>();
        for (int i = 0; i < filesCount; i++)
            files.add(new FileResource("file" + i));

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            threads.add(new Thread(new FileWorker(i, files, semaphore, iterations)));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}