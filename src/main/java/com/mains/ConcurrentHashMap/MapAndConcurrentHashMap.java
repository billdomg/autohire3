package com.mains.ConcurrentHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapAndConcurrentHashMap {
    public static void main(String[] args) {
        // Using HashMap
        Map<Integer, String> hashMap = new HashMap<>();

        Runnable hashMapTask = () -> {
            for (int i = 0; i < 1000; i++) {
                hashMap.put(i, "Value " + i);
            }
        };

        Thread thread1 = new Thread(hashMapTask);
        Thread thread2 = new Thread(hashMapTask);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("HashMap Size: " + hashMap.size());

        // Using ConcurrentHashMap
        Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();

        Runnable concurrentHashMapTask = () -> {
            for (int i = 0; i < 1000; i++) {
                concurrentHashMap.put(i, "Value " + i);
            }
        };

        Thread thread3 = new Thread(concurrentHashMapTask);
        Thread thread4 = new Thread(concurrentHashMapTask);

        thread3.start();
        thread4.start();

        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ConcurrentHashMap Size: " + concurrentHashMap.size());
    }
}
