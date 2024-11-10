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

    // Constructor usando heapify. Complejidad 2*n = O(n)
    public ComparatorHeap(T[] elems, Comparator<T> comparator) {
        this.arregloHeap = new ArrayList<>();
        this.comparador = comparator;

        // O(n)
        for (int i = 0; i < elems.length; i++) {
            this.arregloHeap.add(new Tupla<>(elems[i], i));
        }

        // O(n) por algoritmo de Floyd
        for (int i = 0; i < arregloHeap.size(); i++) {
            siftDown(i);
        }
    }

    private void siftUp(int handle) {
        int indicePadre = padre(handle);

        Tupla<T,Integer> tuplaHijo = arregloHeap.get(handle);
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre);

        T hijoElem = tuplaHijo.primero();
        T padreElem = tuplaPadre.primero();

        // Mientras no sea raiz y padre sea menor al hijo, subimos al hijo
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
        int indiceHijoIzq = hijoIzq(handle);
        int indiceHijoDer = hijoDer(handle);

        Tupla<T,Integer> tuplaPadre = arregloHeap.get(handle);
        Tupla<T,Integer> tuplaHijoIzq = null;
        T hijoIzqElem = null;
        Tupla<T,Integer> tuplaHijoDer = null;
        T hijoDerElem = null;

        if ( indiceHijoIzq < arregloHeap.size() ) {
            tuplaHijoIzq = arregloHeap.get(indiceHijoIzq);
            hijoIzqElem = tuplaHijoIzq.primero();
        }
        
        if ( indiceHijoDer < arregloHeap.size()) {
            tuplaHijoDer = arregloHeap.get(indiceHijoDer);
            hijoDerElem = tuplaHijoDer.primero();
        }
        
        T padreElem = tuplaPadre.primero();
        
        // Mientras no sea hoja y padre sea menor al hijo, bajamos al padre
        while ( indiceHijoIzq < arregloHeap.size() && comparador.compare(padreElem, hijoIzqElem) < 0 ) {
            
            // Intercambiamos los elementos de tupla padre e hijo dependiendo de cual hijo sea mayor
            if ( indiceHijoDer < arregloHeap.size() && comparador.compare(hijoIzqElem, hijoDerElem) < 0 ) {
                
                tuplaPadre.cambiarPrimero(tuplaHijoDer.primero());
                tuplaHijoDer.cambiarPrimero(padreElem);
                tuplaPadre = arregloHeap.get(indiceHijoDer);
                handle = indiceHijoDer;
            } else {

                tuplaPadre.cambiarPrimero(tuplaHijoIzq.primero());
                tuplaHijoIzq.cambiarPrimero(padreElem);
                tuplaPadre = arregloHeap.get(indiceHijoIzq);
                handle = indiceHijoIzq;
            }
            
            // Actualizamos padres e hijos
            indiceHijoDer = hijoDer(handle);
            indiceHijoIzq = hijoIzq(handle);
            if ( indiceHijoIzq < arregloHeap.size() ) {
                tuplaHijoIzq = arregloHeap.get(indiceHijoIzq);
                hijoIzqElem = tuplaHijoIzq.primero();
            }
        
            if ( indiceHijoDer < arregloHeap.size()) {
                tuplaHijoDer = arregloHeap.get(indiceHijoDer);
                hijoDerElem = tuplaHijoDer.primero();
            }
            padreElem = tuplaPadre.primero();       
        }
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

    // max(O(log |arregloHeap|), O(1)) = O(log |arregloHeap|)
    public void encolar(T elem) {
        int handle = arregloHeap.size();
        arregloHeap.add(new Tupla<>(elem, handle)); // O(1) costo amortizado

        siftUp(handle); // O(log |arregloHeap|)
    }

    public T chusmear() {
        if (arregloHeap.isEmpty()) return null;

        return arregloHeap.get(0).primero();
    }

    public void desencolar() {
        if (arregloHeap.isEmpty()) return;

        arregloHeap.set(0, arregloHeap.get(arregloHeap.size() - 1));
        arregloHeap.remove(arregloHeap.size() - 1);
        siftDown(0);
    }
}
