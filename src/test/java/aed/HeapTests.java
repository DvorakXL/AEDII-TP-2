package aed;

import static org.junit.jupiter.api.Assertions.*;

import aed.Comparadores.ComparadorInteger;
import aed.Comparadores.Comparator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class HeapTests {

    @Test
    void crear_heap_vacio(){
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, null);
    }

    @Test
    void insertar_raiz() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, null);

        heap.encolar(5);
        maximo = heap.tope();
        assertEquals(maximo, 5);
    }

    @Test
    void insertar_sin_sift_up() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, null);

        heap.encolar(8);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(5);
        maximo = heap.tope();
        assertEquals(maximo, 8);
    }

    @Test
    void insertar_con_sift_up() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, null);

        heap.encolar(8);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(5);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(10);
        maximo = heap.tope();
        assertEquals(maximo, 10);

        heap.encolar(6);
        maximo = heap.tope();
        assertEquals(maximo, 10);
    }

    @Test
    void desencolar() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, null);

        heap.encolar(8);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(5);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(10);
        maximo = heap.tope();
        assertEquals(maximo, 10);

        heap.encolar(6);
        maximo = heap.tope();
        assertEquals(maximo, 10);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 6);

    }

    @Test
    void desencolarConRepetidos() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, null);

        heap.encolar(8);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(5);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(10);
        maximo = heap.tope();
        assertEquals(maximo, 10);

        heap.encolar(6);
        maximo = heap.tope();
        assertEquals(maximo, 10);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(7);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(8);
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.encolar(11);
        maximo = heap.tope();
        assertEquals(maximo, 11);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 8);

    }

    @Test
    void heapify_lista_vacia() {
        Integer[] heapRoto = {};
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(heapRoto, comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, null);
    }

    @Test
    void heapify_solo_raiz() {
        Integer[] heapRoto = {10};
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(heapRoto, comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, 10);
    }

    @Test
    void heapify_varios_elementos() {
        Integer[] heapRoto = {10, 11, 10, 8, 5, 2, 11};
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(heapRoto, comparador);

        Integer maximo = heap.tope();
        assertEquals(maximo, 11);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 11);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 10);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 10);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 8);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 5);

        heap.desencolar();
        maximo = heap.tope();
        assertEquals(maximo, 2);
    }
}
