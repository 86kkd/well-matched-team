package com.versionone.demo1server.utils;

import java.util.Iterator;

/**
 * 自定义队列，双链表队列
 * @param <T>
 */
public class Queue<T> implements Iterable<T> {
    private final Node<T> head;
    private Node<T> last;
    private int  N;

    public Queue(){
        head = new Node<>(null,null);
        last = null;
        N = 0;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public void enqueue(T t){
        if (last == null){
            last = new Node<>(t,null);
            head.next = last;
        }else {
            Node<T> oldLast = last;
            last = new Node<>(t,null);
            oldLast.next=last;
        }
        N++;
    }

    public T dequeue(){
        if (isEmpty()){
            return null;
        }

        Node<T> oldFirst = head.next;
        head.next = oldFirst.next;
        N--;
        if (isEmpty()){
            last = null;
        }
        return oldFirst.item;
    }

    @Override
    public String toString() {
        return "Queue{" + head.next +
                '}';
    }

    @Override
    public Iterator<T> iterator() {
        return new QIterator();
    }

    private class QIterator implements Iterator<T>{

        private Node<T> n = head;
        @Override
        public boolean hasNext() {
            return n.next!=null;
        }

        @Override
        public T next() {
            return null;
        }
    }
}
