package com.company;

public class ListNode<E> {
    ListNode<E> next;
    ListNode<E> prev;
    E data;

    // list node object
    public ListNode(E theData) {
        this.data = theData;
    }
}
