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

        Integer maximo = heap.chusmear();
        assertEquals(maximo, null);
    }

    @Test
    void insertar_raiz() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.chusmear();
        assertEquals(maximo, null);

        heap.encolar(5);
        maximo = heap.chusmear();
        assertEquals(maximo, 5);
    }

    @Test
    void insertar_sin_sift_up() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.chusmear();
        assertEquals(maximo, null);

        heap.encolar(8);
        maximo = heap.chusmear();
        assertEquals(maximo, 8);

        heap.encolar(5);
        maximo = heap.chusmear();
        assertEquals(maximo, 8);
    }

    @Test
    void insertar_con_sift_up() {
        Comparator<Integer> comparador = new ComparadorInteger();
        ComparatorHeap<Integer> heap = new ComparatorHeap<>(comparador);

        Integer maximo = heap.chusmear();
        assertEquals(maximo, null);

        heap.encolar(8);
        maximo = heap.chusmear();
        assertEquals(maximo, 8);

        heap.encolar(5);
        maximo = heap.chusmear();
        assertEquals(maximo, 8);

        heap.encolar(10);
        maximo = heap.chusmear();
        assertEquals(maximo, 10);

        heap.encolar(6);
        maximo = heap.chusmear();
        assertEquals(maximo, 10);
    }
}
