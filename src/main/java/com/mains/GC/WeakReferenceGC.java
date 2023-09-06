package com.mains.GC;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakReferenceGC {
        public static void main(String[] args) {
        Map<String, WeakReference<Image>> imageCache = new WeakHashMap<>();

        // Create and store images in the cache
        byte[] imageData1 = new byte[1024 * 1024]; // 1 MB
        byte[] imageData2 = new byte[1024 * 1024]; // 1 MB
        byte[] imageData3 = new byte[1024 * 1024]; // 1 MB

        imageCache.put("image1", new WeakReference<>(new Image(imageData1)));
        imageCache.put("image2", new WeakReference<>(new Image(imageData2)));
        imageCache.put("image3", new WeakReference<>(new Image(imageData3)));

        // Retrieve images from the cache and and place them in separate objects to display them
        Image image1 = imageCache.get("image1").get();
        Image image2 = imageCache.get("image2").get();
        Image image3 = imageCache.get("image3").get();

        // Allways check for null on objects weak referenced to avoid NullPointerException
        if (image1 != null) {
            image1.display();
        } else {
            System.out.println("Image1 has been garbage collected");
        }

        if (image2 != null) {
            image2.display();
        } else {
            System.out.println("Image2 has been garbage collected");
        }

        if (image3 != null) {
            image3.display();
        } else {
            System.out.println("Image3 has been garbage collected");
        }
    }
}
