package com.mains.MemErr;
import java.io.FileWriter;
import java.io.IOException;

public class CloseWriterExample {
    public static void main(String[] args) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("somefile.txt");
            writer.write("Hello, world!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();         // close writer
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
