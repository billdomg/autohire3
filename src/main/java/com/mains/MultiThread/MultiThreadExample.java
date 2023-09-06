package com.mains.MultiThread;

public class MultiThreadExample {
    public static void main(String[] args) {
        PrintThread thread1 = new PrintThread(1, 5);
        PrintThread thread2 = new PrintThread(6, 10);

        System.out.println("First thread.");
        thread1.start();
        System.out.println("Second thread.");
        thread2.start();
    }
}
