package com.mains.GC;
import java.lang.ref.WeakReference;

public class WeakReferenceCompGC {
    public static void main(String[] args) {
        Object strongRefObject = new Object();
        WeakReference<Object> weakRef = new WeakReference<>(strongRefObject);

        strongRefObject = null; // Removing the strong reference

        // Attempt to access the object through the weak reference
        Object retrievedObject = weakRef.get();

        // Allways check for null on objects weak referenced to avoid NullPointerException
        if (retrievedObject == null) {
            System.out.println("Object has been garbage collected");
        } else {
            System.out.println("Object is still accessible: " + retrievedObject);
        }
    }
}
