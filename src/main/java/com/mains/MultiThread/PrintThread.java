package com.mains.MultiThread;

class PrintThread extends Thread {
    private final int start;
    private final int end;

    public PrintThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.println("Thread " + Thread.currentThread().getId() + ": " + i);
        }
    }
}
