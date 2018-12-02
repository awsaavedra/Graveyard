package com.company;

public class RememberLast<T> {
    private T lastElement;
    private int numElements;

    public RememberLast() {
        numElements = 0;
        lastElement = null;
    }

    public T add( T element){ //checked and unchecked exceptions
        //basically in the method line above is checked, unchecked are
        //in the method body
        if( element == null){
            throw new NullPointerException("Remember object cannot store null" +
                    "pointers.");
        }
        T prevLast = lastElement;
        lastElement = element;
        numElements++;
        return prevLast;
    }
}
