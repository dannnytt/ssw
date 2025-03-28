package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class FileResource {
    private final ReentrantLock lock = new ReentrantLock();
    private final Random random = new Random();
    private final String filename;

    public FileResource(String filename) {
        this.filename = filename;
    }

    public boolean tryAcquire() {
        return lock.tryLock();
    }

    public void write(int threadId) {
        try {
            String fileName = filename + ".txt";
            try (FileWriter writer = new FileWriter(fileName, true)) {
                int randomData = random.nextInt(100);
                String data = String.format("(Поток %d): %d%n", threadId, randomData);
                System.out.printf("(Поток %d) записывает в файл %s%n", threadId, filename);
                writer.write(data);
                writer.flush();
                Thread.sleep(3000);
                System.out.printf("(Поток %d) закончил запись в файл %s%n", threadId, filename);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка при записи в файл " + filename + ": " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
