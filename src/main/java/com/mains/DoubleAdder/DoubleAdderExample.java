package com.mains.DoubleAdder;
import java.util.concurrent.atomic.DoubleAdder;

public class DoubleAdderExample {
    public static void main(String[] args) {

        DoubleAdder doubleAdder = new DoubleAdder();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                doubleAdder.add(0.1); // Add 0.1 to the total
            }
        }; 

        // Create multiple threads to execute the task concurrently
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total: " + doubleAdder.doubleValue());
    }
}
