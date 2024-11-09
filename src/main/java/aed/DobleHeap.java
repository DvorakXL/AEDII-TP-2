package aed;

import aed.ComparatorHeap;

import aed.Comparadores.Comparator;

public class DobleHeap<T> {
    private ComparatorHeap<T> heapRedituables;
    private ComparatorHeap<T> heapAntiguos;
    
    public DobleHeap(Comparator<T> comparator1, Comparator<T> comparator2) {
        this.heapRedituables = new ComparatorHeap<>(comparator1);
        this.heapAntiguos = new ComparatorHeap<>(comparator2);
    }
    
    // constructor con heapify
    // public DobleHeap(T[] elems, Comparator<T> comparator1, Comparator<T> comparator2) {}
    
    public void dobleEncolar(T elem) {
    }
}
