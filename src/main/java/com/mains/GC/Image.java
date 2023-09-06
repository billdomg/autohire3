package com.mains.GC;
//import java.lang.ref.WeakReference;
//import java.util.Map;
//import java.util.WeakHashMap;

class Image {
    // Simulate some image data
    private byte[] data;

    public Image(byte[] d) {
        this.data = d;
    }

    public void display() {
        System.out.println("Displaying image");
    }

    public void printd() {
        System.out.println(data);
    }
}
