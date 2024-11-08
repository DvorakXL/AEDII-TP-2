package aed;

import aed.Comparadores.Comparator;

import java.util.ArrayList;

public class ComparatorHeap<T> {
    ArrayList<Tupla<T, Integer>> arregloHeap;
    Comparator<T> comparador;

    public ComparatorHeap(Comparator<T> comparator) {
        this.arregloHeap = new ArrayList<>();
        this.comparador = comparator;
    }

//    public ComparatorHeap(T[] elems, Comparator<T> comparator, int[] handles) {
//
//    }

    private void siftUp(int handle) {
        int indicePadre = padre(handle);

        Tupla<T,Integer> tuplaHijo = arregloHeap.get(handle);
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre);

        T hijoElem = tuplaHijo.primero();
        T padreElem = tuplaPadre.primero();

        // Mientras no sea raiz y padre sea menor al hijo lo subimos
        while ( handle != 0 && comparador.compare(padreElem, hijoElem) < 0 ) {

            // Intercambiamos los elementos de tupla padre e hijo
            tuplaPadre.cambiarPrimero(tuplaHijo.primero());
            tuplaHijo.cambiarPrimero(padreElem);

            // Actualizamos padres e hijos
            tuplaHijo = arregloHeap.get(indicePadre);
            hijoElem = tuplaHijo.primero();

            indicePadre = padre(indicePadre);
            tuplaPadre = arregloHeap.get(indicePadre);
            padreElem = tuplaPadre.primero();
        }
    }

    private void siftDown(int handle) {

    }

    private int padre(int i) {
        return (i - 1) / 2;
    }

    private int hijoIzq(int i) {
        return 2 * i + 1;
    }

    private int hijoDer(int i) {
        return 2 * i + 2;
    }

    public void encolar(T elem) {
        int handle = arregloHeap.size();
        arregloHeap.add(new Tupla<T,Integer>(elem, handle));

        siftUp(handle);
    }

    public T chusmear() {
        if (arregloHeap.isEmpty()) return null;

        return arregloHeap.get(0).primero();
    }
}
