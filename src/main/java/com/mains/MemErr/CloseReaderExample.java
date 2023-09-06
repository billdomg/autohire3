package com.mains.MemErr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CloseReaderExample {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("somefile.txt"))) {    // close automatically after exiting bracket
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
