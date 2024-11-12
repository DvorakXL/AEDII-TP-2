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

    // Constructor usando heapify. Complejidad 2*|elems| = O(|elems|)
    public ComparatorHeap(T[] elems, Comparator<T> comparator) {
        this.arregloHeap = new ArrayList<>();
        this.comparador = comparator;

        // O(|elems|)
        for (int i = 0; i < elems.length; i++) {
            this.arregloHeap.add(new Tupla<>(elems[i], i)); // Constructor de tupla O(1)
        }

        // O(|elems|) por algoritmo de Floyd
        for (int i = arregloHeap.size() - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    // Heapify enlazado ordena el heapA con el nuevo criterio de orden y lo asocia con handles al heapB
    public ComparatorHeap(ComparatorHeap<T> heap, Comparator<T> comparator) {
        this.arregloHeap = heap.obtenerArregloHeap();
        this.comparador = comparator;

        // O(|elems|) por algoritmo de Floyd
        for (int i = arregloHeap.size() - 1; i >= 0; i--) {
            siftDownHandles(i, heap);
        }
    }

    // Funciones Auxiliares

    public ArrayList<Tupla<T, Integer>> obtenerArregloHeap() {
        return new ArrayList<>(arregloHeap);
    }

    private void siftUpHandles(int handle, ComparatorHeap<T> heapHandles) {
        int indicePadre = padre(handle);

        Tupla<T,Integer> tuplaHijo = arregloHeap.get(handle);
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre);

        // Mientras no sea raiz y padre sea menor al hijo, subimos al hijo
        while ( handle != 0 && comparador.compare(tuplaPadre.primero(), tuplaHijo.primero()) < 0 ) {

            // Intercambiamos los elementos de tupla padre e hijo
            arregloHeap.set(indicePadre, tuplaHijo);
            arregloHeap.set(handle, tuplaPadre);

            // Actualizamos los handles del otro heap
            heapHandles.intercambiarHandles(handle, indicePadre);

            // Actualizamos padres e hijos
            tuplaHijo = arregloHeap.get(indicePadre);

            indicePadre = padre(indicePadre);
            tuplaPadre = arregloHeap.get(indicePadre);
        }
    }

    private void siftDownHandles(int handle, ComparatorHeap<T> heapHandles) {
        while (tieneHijoIzq(handle)) {

            // Inicio el hijo mayor
            int hijoMayor = hijoIzq(handle);
            Tupla<T, Integer> hijoIzq = arregloHeap.get(hijoMayor);

            // Veo si el de la derecha es mas grande usando el comparador
            if (tieneHijoDer(handle) && comparador.compare(arregloHeap.get(hijoDer(handle)).primero(), hijoIzq.primero()) > 0) {
                hijoMayor = hijoDer(handle);
            }

            Tupla<T, Integer> nodoActual = arregloHeap.get(handle);
            Tupla<T, Integer> tuplaHijoMenor = arregloHeap.get(hijoMayor);

            // Si mi hijo mayor es mas grande que mi padre lo reemplazo y cambio los handles del padre.
            if (comparador.compare(tuplaHijoMenor.primero(), nodoActual.primero()) > 0) {
                // Intercambiamos los elementos de tupla padre e hijo
                arregloHeap.set(hijoMayor, nodoActual);
                arregloHeap.set(handle, tuplaHijoMenor);

                // Actualizamos los handles del otro heap
                heapHandles.intercambiarHandles(handle, hijoMayor);

                // Actualizamos padres e hijos
                handle = hijoMayor;
            } else {
                break;
            }
        }
    }

    private void intercambiarHandles(int handleA, int handleB) {
        Tupla<T, Integer> tuplaHandleA = arregloHeap.get(handleA);
        Tupla<T, Integer> tuplaHandleB = arregloHeap.get(handleB);

        // Intercambio los handles
        tuplaHandleA.cambiarSegundo(handleB);
        tuplaHandleB.cambiarSegundo(handleA);
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
        while (tieneHijoIzq(handle)) {

            // Inicio el hijo menor
            int hijoMenor = hijoIzq(handle);
            Tupla<T, Integer> hijoIzq = arregloHeap.get(hijoMenor);

            // Veo si el de la derecha es mas grande usando el comparador
            if (tieneHijoDer(handle) && comparador.compare(arregloHeap.get(hijoDer(handle)).primero(), hijoIzq.primero()) > 0) {
                hijoMenor = hijoDer(handle);
            }

            Tupla<T, Integer> nodoActual = arregloHeap.get(handle);
            Tupla<T, Integer> tuplaHijoMenor = arregloHeap.get(hijoMenor);

            // Si mi hijo menor es mayor lo cambio por el nodo actual.
            if (comparador.compare(tuplaHijoMenor.primero(), nodoActual.primero()) > 0) {

                T elemPadre = nodoActual.primero();
                T elemHijo = tuplaHijoMenor.primero();

                // Intercambiamos los elementos de tupla padre e hijo
                tuplaHijoMenor.cambiarPrimero(elemPadre);
                nodoActual.cambiarPrimero(elemHijo);

                // Actualizamos padres e hijos
                handle = hijoMenor;
            } else {
                break;
            }
        }
    }

    private boolean tieneHijoDer(int i) {
        return hijoDer(i) < arregloHeap.size();
    }

    private boolean tieneHijoIzq(int i) {
        return hijoIzq(i) < arregloHeap.size();
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

    // Interfaz

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

        int indiceUltimo = arregloHeap.size() - 1;

        arregloHeap.set(0, arregloHeap.get(indiceUltimo));
        arregloHeap.remove(indiceUltimo);

        if (arregloHeap.isEmpty()) return;
        siftDown(0);
    }
}