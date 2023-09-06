package com.mains.Iterator;
import java.util.List;

// Class represent iterator and determine current and next location
class ListIterator<T> implements Iterator<T> {
    private List<T> list;
    private int index = 0;

    public ListIterator(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public T next() {
        if (hasNext()) {
            return list.get(index++);
        }
        return null;
    }
}
