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

    public ComparatorHeap(T[] elems, int[] handles, Comparator<T> comparator) {
        this.arregloHeap = new ArrayList<>();
        this.comparador = comparator;

        // O(|elems|)
        for (int i = 0; i < elems.length; i++) {
            this.arregloHeap.add(new Tupla<>(elems[i], i)); // Constructor de tupla O(1)
        }
    }

    // Funciones Auxiliares

    private ArrayList<Tupla<T, Integer>> obtenerArregloHeap() {
        ArrayList<Tupla<T, Integer>> copiaArregloHeap = new ArrayList<>();

        for (Tupla<T, Integer> tupla: arregloHeap) {
            copiaArregloHeap.add(new Tupla<>(tupla));
        }

        return copiaArregloHeap;
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
            heapHandles.intercambiarHandles(handle, indicePadre, tuplaHijo.segundo(), tuplaPadre.segundo());

            // Actualizamos padres e hijos
            tuplaHijo = arregloHeap.get(indicePadre);
            handle = padre(handle);

            indicePadre = padre(indicePadre);
            tuplaPadre = arregloHeap.get(indicePadre);
        }
    }

    private void siftUpHandles(int handle, int[] handles) {
        int indicePadre = padre(handle);

        Tupla<T,Integer> tuplaHijo = arregloHeap.get(handle);
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre);

        // Mientras no sea raiz y padre sea menor al hijo, subimos al hijo
        while ( handle != 0 && comparador.compare(tuplaPadre.primero(), tuplaHijo.primero()) < 0 ) {

            // Intercambiamos los elementos de tupla padre e hijo
            arregloHeap.set(indicePadre, tuplaHijo);
            arregloHeap.set(handle, tuplaPadre);

            // Actualizamos los handles del array
            handles[indicePadre] = handle;
            handles[handle] = indicePadre;

            // Actualizamos padres e hijos
            tuplaHijo = arregloHeap.get(indicePadre);
            handle = padre(handle);

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
                hijoMayor += 1;
            }

            Tupla<T, Integer> nodoActual = arregloHeap.get(handle);
            Tupla<T, Integer> tuplaHijoMayor = arregloHeap.get(hijoMayor);

            // Si mi hijo mayor es mas grande que mi padre lo reemplazo y cambio los handles del padre.
            if (comparador.compare(tuplaHijoMayor.primero(), nodoActual.primero()) > 0) {
                // Intercambiamos los elementos de tupla padre e hijo
                arregloHeap.set(hijoMayor, nodoActual);
                arregloHeap.set(handle, tuplaHijoMayor);

                // Actualizamos los handles del otro heap
                heapHandles.intercambiarHandles(handle, hijoMayor, nodoActual.segundo(), tuplaHijoMayor.segundo());
                handle = hijoMayor;
            } else {
                break;
            }
        }
    }

    // inout handles
    private void siftDownHandles(int handle, int[] handles) {
        while (tieneHijoIzq(handle)) {

            // Inicio el hijo mayor
            int hijoMayor = hijoIzq(handle);
            Tupla<T, Integer> hijoIzq = arregloHeap.get(hijoMayor);

            // Veo si el de la derecha es mas grande usando el comparador
            if (tieneHijoDer(handle) && comparador.compare(arregloHeap.get(hijoDer(handle)).primero(), hijoIzq.primero()) > 0) {
                hijoMayor += 1;
            }

            Tupla<T, Integer> nodoActual = arregloHeap.get(handle);
            Tupla<T, Integer> tuplaHijoMayor = arregloHeap.get(hijoMayor);

            // Si mi hijo mayor es mas grande que mi padre lo reemplazo y cambio los handles del padre.
            if (comparador.compare(tuplaHijoMayor.primero(), nodoActual.primero()) > 0) {
                // Intercambiamos los elementos de tupla padre e hijo
                arregloHeap.set(hijoMayor, nodoActual);
                arregloHeap.set(handle, tuplaHijoMayor);

                // Actualizamos los handles del otro heap
                handles[hijoMayor] = handle;
                handles[handle] = hijoMayor;

                handle = hijoMayor;
            } else {
                break;
            }
        }
    }

    private void cambiarHandle(int handle, int nuevoHandle) {
        Tupla<T, Integer> tuplaHandle = arregloHeap.get(handle);
        tuplaHandle.cambiarSegundo(nuevoHandle);
    }

    private void intercambiarHandles(int handleA, int handleB, int nuevoHandleA, int nuevoHandleB) {
        Tupla<T, Integer> tuplaHandleA = arregloHeap.get(nuevoHandleB);
        Tupla<T, Integer> tuplaHandleB = arregloHeap.get(nuevoHandleA);

        // Intercambio los handles
        tuplaHandleA.cambiarSegundo(handleA);
        tuplaHandleB.cambiarSegundo(handleB);
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
                hijoMenor += 1;
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
            }

            handle = hijoMenor;
        }
    }

    private void borrarConHandle(int handle, ComparatorHeap<T> heapHandles) {
        int indiceUltimo = arregloHeap.size() - 1;

        if (handle == indiceUltimo) {
            arregloHeap.remove(indiceUltimo);
            return;
        }

        Tupla<T, Integer> tuplaUltimo = arregloHeap.get(indiceUltimo);

        // Meti el ultimo elemento en el nodo handle
        arregloHeap.set(handle, tuplaUltimo);
        arregloHeap.remove(indiceUltimo);
        heapHandles.cambiarHandle(tuplaUltimo.segundo(), handle); // Actualizo el handle en el otro heap para que se mantenga el invariante

        siftDownHandles(handle, heapHandles);
        siftUpHandles(handle, heapHandles);
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

    public int size() {
        return arregloHeap.size();
    }

    // max(O(log |arregloHeap|), O(1)) = O(log |arregloHeap|)
    public void encolar(T elem) {
        int handle = arregloHeap.size();
        arregloHeap.add(new Tupla<>(elem, handle)); // O(1) costo amortizado

        siftUp(handle); // O(log |arregloHeap|)
    }

    public void encolarEnlazado(T elem, ComparatorHeap<T> heapEnlazado) {
        int handle = arregloHeap.size();

        arregloHeap.add(new Tupla<>(elem, handle)); // O(1) costo amortizado
        heapEnlazado.arregloHeap.add(new Tupla<>(elem, handle));

        siftUpHandles(handle, heapEnlazado); // O(log |arregloHeap|)
        heapEnlazado.siftUpHandles(handle, this);
    }

    public T tope() {
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

    public void desencolarEnlazado(ComparatorHeap<T> heapEnlazado) {
        if (arregloHeap.isEmpty()) return;

        int handleAEliminar = arregloHeap.get(0).segundo();
        int indiceUltimo = arregloHeap.size() - 1;
        Tupla<T, Integer> tuplaUltimo = arregloHeap.get(indiceUltimo);

        // Meti el ultimo elemento en el primer nodo
        arregloHeap.set(0, tuplaUltimo);
        arregloHeap.remove(indiceUltimo);

        heapEnlazado.cambiarHandle(tuplaUltimo.segundo(), 0); // Actualizo el handle en el otro heap para que se mantenga el invariante

        heapEnlazado.borrarConHandle(handleAEliminar, this);

        siftDownHandles(0, heapEnlazado);
    }

    public Tupla<T, Integer> obtenerEnHandle(int handle) {
        return new Tupla<>(arregloHeap.get(handle));
    }

    public void actualizar(int handle, T valor, int[] handles) {
        Tupla<T, Integer> tuplaACambiar = arregloHeap.get(handle);
        tuplaACambiar.cambiarPrimero(valor);

        siftDownHandles(handle, handles);
        siftUpHandles(handle, handles);
    }
}