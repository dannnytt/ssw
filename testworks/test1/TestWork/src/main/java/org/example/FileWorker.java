package org.example;

import java.util.List;
import java.util.concurrent.Semaphore;

class FileWorker implements Runnable {
    private final int threadId;
    private final List<FileResource> files;
    private final Semaphore semaphore;
    private final int iterations;

    public FileWorker(int threadId, List<FileResource> files, Semaphore semaphore, int iterations) {
        this.threadId = threadId;
        this.files = files;
        this.semaphore = semaphore;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                semaphore.acquire();

                FileResource file = getAvailableFile();

                if (file != null) {
                    file.write(threadId);
                }

                semaphore.release();

                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private FileResource getAvailableFile() {
        for (FileResource file : files) {
            if (file.tryAcquire()) {
                return file;
            }
        }
        return null;
    }
}
