package com.mains.MemErr;

public class StringBuilderVsConcatenation {
    public static void main(String[] args) {
        int n = 100000;
        long startTime, endTime, duration;
        Boolean printString = false;
        // Using String Concatenation
        startTime = System.nanoTime();
        String concatenated = "";
        for (int i = 0; i < n; i++) {
            concatenated += i;
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        if (printString)
            System.out.println(concatenated);
        System.out.println("String Concatenation Time: " + duration / 1000000.0 + " ms");

        // Using StringBuilder
        startTime = System.nanoTime();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(i);
        }
        String result = stringBuilder.toString();
        endTime = System.nanoTime();
        duration = endTime - startTime;
        if (printString)
            System.out.println(result);
        System.out.println("StringBuilder Time: " + duration / 1000000.0 + " ms");
    }
}
