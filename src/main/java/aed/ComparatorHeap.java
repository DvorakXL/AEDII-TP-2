package aed;

import aed.Comparadores.Comparator;

import java.util.ArrayList;

public class ComparatorHeap<T> {
    ArrayList<Tupla<T, Integer>> arregloHeap;
    Comparator<T> comparador;

    public ComparatorHeap(Comparator<T> comparator) {
        this.arregloHeap = new ArrayList<>(); // O(1)
        this.comparador = comparator; // O(1)
    }

    // Constructor usando heapify
    public ComparatorHeap(T[] elems, Comparator<T> comparator) {    // O(|elems|)
        this.arregloHeap = new ArrayList<>(); // O(1)
        this.comparador = comparator; // O(1)

        // O(|elems|)
        for (int i = 0; i < elems.length; i++) { // La guarda se evalua |elems| + 1 veces
            this.arregloHeap.add(new Tupla<>(elems[i], i)); // Constructor de tupla O(1), add es O(1) amortizado
        }

        // Este bucle es el responsable de hacer el heapify utilizando el algoritmo de Floyd cuya complejidad es O(|elems|)
        for (int i = arregloHeap.size() - 1; i >= 0; i--) { // La guarda se evalua |elems| + 1 veces
            siftDown(i);
        }
    }

    // Heapify enlazado ordena el heapA con el nuevo criterio de orden y lo asocia con handles al heapB
    public ComparatorHeap(ComparatorHeap<T> heap, Comparator<T> comparator) { // O(2*H) = O(H)
        this.arregloHeap = heap.copiarArregloHeap(); // O(H)
        this.comparador = comparator;

        // O(|heap|)
        for (int i = arregloHeap.size() - 1; i >= 0; i--) { // O(H)
            siftDownHandles(i, heap);
        }
    }

    // Funciones Auxiliares

    private ArrayList<Tupla<T, Integer>> copiarArregloHeap() {
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

    private void cambiarHandle(int handle, int nuevoHandle) { // O(1)
        Tupla<T, Integer> tuplaHandle = arregloHeap.get(handle); // O(1)
        tuplaHandle.cambiarSegundo(nuevoHandle);   // O(1)
    }

    private void intercambiarHandles(int handleA, int handleB, int nuevoHandleA, int nuevoHandleB) { // O(1)
        Tupla<T, Integer> tuplaHandleA = arregloHeap.get(nuevoHandleB); // O(1)
        Tupla<T, Integer> tuplaHandleB = arregloHeap.get(nuevoHandleA); // O(1)

        // Intercambio los handles
        tuplaHandleA.cambiarSegundo(handleA); // O(1)
        tuplaHandleB.cambiarSegundo(handleB); // O(1)
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
        int indiceUltimo = arregloHeap.size() - 1; // O(1)

        if (handle == indiceUltimo) { // O(1)
            arregloHeap.remove(indiceUltimo); // O(1)
            return;
        }

        Tupla<T, Integer> tuplaUltimo = arregloHeap.get(indiceUltimo); //O(1)

        // Meti el ultimo elemento en el nodo handle
        arregloHeap.set(handle, tuplaUltimo); // O(1)
        arregloHeap.remove(indiceUltimo);  // O(1)
        heapHandles.cambiarHandle(tuplaUltimo.segundo(), handle); //  O(1) Actualizo el handle en el otro heap para que se mantenga el invariante

        // En este caso si hace SiftDown no va a hacer SiftUp y viceversa (TODO)
        siftDownHandles(handle, heapHandles); // O(log|)
        siftUpHandles(handle, heapHandles);
    }

    private boolean tieneHijoDer(int i) { // O(1)
        return hijoDer(i) < arregloHeap.size();
    }

    private boolean tieneHijoIzq(int i) { // O(1)
        return hijoIzq(i) < arregloHeap.size();
    }

    private int padre(int i) { // O(1)
        return (i - 1) / 2;
    }

    private int hijoIzq(int i) { // O(1)
        return 2 * i + 1;
    }

    private int hijoDer(int i) { // O(1)
        return 2 * i + 2;
    }

    // Interfaz

    public int size() { // O(1)
        return arregloHeap.size();
    }

    // max(O(log H), O(1)) = O(log H)
    public void encolar(T elem) {
        int handle = arregloHeap.size();
        arregloHeap.add(new Tupla<>(elem, handle)); // O(1) costo amortizado

        siftUp(handle); // O(log H)
    }

    // O(2* (log H)) = O(log H)
    public void encolarEnlazado(T elem, ComparatorHeap<T> heapEnlazado) {
        int handle = arregloHeap.size();

        arregloHeap.add(new Tupla<>(elem, handle)); // O(1) costo amortizado
        heapEnlazado.arregloHeap.add(new Tupla<>(elem, handle));

        siftUpHandles(handle, heapEnlazado); // O(log H)
        heapEnlazado.siftUpHandles(handle, this); // O(log H) , |this| = H
    }

    public T tope() { // O(1)
        if (arregloHeap.isEmpty()) return null; // O(1)

        return arregloHeap.get(0).primero(); // O(1)
    }

    public void desencolar() { // O(log H)
        if (arregloHeap.isEmpty()) return; // O(1)

        int indiceUltimo = arregloHeap.size() - 1; // O(1)

        arregloHeap.set(0, arregloHeap.get(indiceUltimo)); // O(1)
        arregloHeap.remove(indiceUltimo); // O(1)

        if (arregloHeap.isEmpty()) return; // O(1)
        siftDown(0); // O(log H)
    }

    public void desencolarEnlazado(ComparatorHeap<T> heapEnlazado) { // O(log H)
        if (arregloHeap.isEmpty()) return;  // O(1)

        int handleAEliminar = arregloHeap.get(0).segundo(); // O(1)
        int indiceUltimo = arregloHeap.size() - 1; // O(1)
        Tupla<T, Integer> tuplaUltimo = arregloHeap.get(indiceUltimo); // O(1)

        // Meti el ultimo elemento en el primer nodo
        arregloHeap.set(0, tuplaUltimo); // O(1)
        arregloHeap.remove(indiceUltimo); // O(1)

        // Actualizo el handle en el otro heap para que se mantenga el invariante
        heapEnlazado.cambiarHandle(tuplaUltimo.segundo(), 0); //  O(1)

        heapEnlazado.borrarConHandle(handleAEliminar, this); // O(log H)

        siftDownHandles(0, heapEnlazado); // O(log H)
    }

    public Tupla<T, Integer> obtenerEnHandle(int handle) { // O(1)
        return new Tupla<>(arregloHeap.get(handle)); // O(1)
    }

    public void actualizar(int handle, T valor, int[] handles) {  // O(log H)
        Tupla<T, Integer> tuplaACambiar = arregloHeap.get(handle); // O(1)
        tuplaACambiar.cambiarPrimero(valor); // O(1)

        //TODO : agregar ifs
        siftDownHandles(handle, handles);
        siftUpHandles(handle, handles);
    }
}