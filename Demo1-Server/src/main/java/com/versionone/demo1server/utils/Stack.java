package com.versionone.demo1server.utils;


import java.util.Iterator;

/**
 * 自定义栈，链表栈
 * @param <T>
 */
public class Stack<T> implements Iterable<T>{
    private final Node<T> head;
    private int N;

    @Override
    public String toString() {
        return "Stack{" +
                  head.next +
                '}';
    }

    public Stack(){
        head = new Node<>(null,null);
        N = 0;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public void push(T t){
        Node<T> oldNext = head.next;
        head.next = new Node<>(t,oldNext);
        N++;
    }

    public T pop(){
        Node<T> oldNext = head.next;
        if (oldNext == null){
            return null;
        }
        head.next=head.next.next;
        N--;
        return oldNext.item;
    }

    public int size(){
        return N;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private class SIterator implements Iterator<T>{

        private Node<T> n = head;
        @Override
        public boolean hasNext() {
            return n.next!=null;
        }

        @Override
        public T next() {
            Node<T> node = n.next;
            n = n.next;
            return node.item;
        }
    }
}
