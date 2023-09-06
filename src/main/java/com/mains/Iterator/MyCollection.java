package com.mains.Iterator;
import java.util.ArrayList;
import java.util.List;

// holds collection, add remove items to the collection, also build and return iterator
class MyCollection<T> {
    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public Iterator<T> createIterator() {
        return new ListIterator<>(items);
    }
}

