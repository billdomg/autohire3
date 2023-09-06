package com.mains.MultiThreadSafety;

public class ConcurrencyWithThreadSafety {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Boolean isSafe = true;
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                counter.increment();
                System.out.println("Thread " + Thread.currentThread().getId() + ": " + i);
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        try {
            if (isSafe) {
                thread1.join();
                thread2.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 

        System.out.println("Final Count: " + counter.getCount());
    }
}
