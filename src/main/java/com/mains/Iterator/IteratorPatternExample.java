package com.mains.Iterator;


public class IteratorPatternExample {
    public static void main(String[] args) {
        MyCollection<Boolean> collection = new MyCollection<>();
        collection.addItem(true);
        collection.addItem(true);
        collection.addItem(false);

        Iterator<Boolean> iterator = collection.createIterator();
        while (iterator.hasNext()) {
            Boolean item = iterator.next();
            System.out.println("Item: " + item); 
        }
    }
}
