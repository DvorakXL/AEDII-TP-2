package aed;

import aed.Comparadores.Comparator;

import java.util.ArrayList;

// Para calcular las complejidades definimos H = |arregloHeap|

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

        // O(|arregloHeap| en el que se invoco|)
        for (Tupla<T, Integer> tupla: arregloHeap) {    // La guarda se evalua longitud del arreglo en el que se invoco + 1 veces
            copiaArregloHeap.add(new Tupla<>(tupla));   // O(1)
        }

        return copiaArregloHeap;
    }

    private int compararTuplasPrimerComponente(Tupla<T, Integer> tuplaPadre, Tupla<T, Integer> tuplaHijo) {
        return comparador.compare(tuplaPadre.primero(), tuplaHijo.primero());
    }

    private void siftUpHandles(int handle, ComparatorHeap<T> heapHandles) { // O(log H)
        int indicePadre = padre(handle); // O(1)

        Tupla<T,Integer> tuplaHijo = arregloHeap.get(handle);           // O(1)
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre);     // O(1)

        // Mientras no sea raiz y padre sea menor al hijo, subimos al hijo
        while ( handle != 0 && compararTuplasPrimerComponente(tuplaPadre, tuplaHijo) < 0 ) {  // O(log H) (Altura del árbol)

            // Intercambiamos las tuplas padre e hijo
            arregloHeap.set(indicePadre, tuplaHijo);     // O(1)
            arregloHeap.set(handle, tuplaPadre);         // O(1)

            // Actualizamos los handles del otro heap
            heapHandles.intercambiarHandles(handle, indicePadre, tuplaHijo.segundo(), tuplaPadre.segundo()); // O(1)

            // Actualizamos padres e hijos
            tuplaHijo = arregloHeap.get(indicePadre);     // O(1)
            handle = padre(handle);                       // O(1)
            indicePadre = padre(indicePadre);             // O(1)
            tuplaPadre = arregloHeap.get(indicePadre);    // O(1)
        }
    }

    private void siftUpHandles(int handle, int[] handles) { // O(log H)
        int indicePadre = padre(handle); // O(1)

        Tupla<T,Integer> tuplaHijo = arregloHeap.get(handle); // O(1)
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre); // O(1)

        // Mientras no sea raiz y padre sea menor al hijo, subimos al hijo
        while ( handle != 0 && compararTuplasPrimerComponente(tuplaPadre, tuplaHijo) < 0 ) { // O(log H)

            // Intercambiamos las tuplas padre e hijo
            arregloHeap.set(indicePadre, tuplaHijo); // O(1)
            arregloHeap.set(handle, tuplaPadre); // O(1)

            // Actualizamos los handles del array
            handles[tuplaHijo.segundo()] = indicePadre; // O(1)
            handles[tuplaPadre.segundo()] = handle; // O(1)

            // Actualizamos padres e hijos
            tuplaHijo = arregloHeap.get(indicePadre); // O(1)
            handle = padre(handle); // O(1)
            indicePadre = padre(indicePadre); // O(1)
            tuplaPadre = arregloHeap.get(indicePadre); // O(1)
        }
    }

    private void siftDownHandles(int handle, ComparatorHeap<T> heapHandles) {  // O(log H)
        while (tieneHijoIzq(handle)) { // O(log H)  (peor caso la altura del árbol)

            // Inicio el hijo mayor
            int hijoMayor = hijoIzq(handle);                         // O(1)
            Tupla<T, Integer> hijoIzq = arregloHeap.get(hijoMayor);  // O(1)

            // Notemos que la complejidad de ambos ifs y del else es O(1)

            // Veo si el de la derecha es mas grande usando el comparador
            if (tieneHijoDer(handle) && compararTuplasPrimerComponente(arregloHeap.get(hijoDer(handle)), hijoIzq) > 0) {
                hijoMayor += 1;
            }  // O(1)

            Tupla<T, Integer> nodoActual = arregloHeap.get(handle);                  // O(1)
            Tupla<T, Integer> tuplaHijoMayor = arregloHeap.get(hijoMayor);           // O(1)

            // Si mi hijo mayor es mas grande que mi padre lo reemplazo y cambio los handles del padre.
            if (compararTuplasPrimerComponente(tuplaHijoMayor, nodoActual) > 0) {  // O(1)
                // Intercambiamos las tuplas padre e hijo
                arregloHeap.set(hijoMayor, nodoActual);                  // O(1)
                arregloHeap.set(handle, tuplaHijoMayor);                 // O(1)

                // Actualizamos los handles del otro heap
                heapHandles.intercambiarHandles(handle, hijoMayor, nodoActual.segundo(), tuplaHijoMayor.segundo());  // O(1)
                handle = hijoMayor;                                                                                  // O(1)
            } else { // O(1)
                break;  // O(1)
            }
        }
    }

    // inout handles
    private void siftDownHandles(int handle, int[] handles) { // O(log H)
        while (tieneHijoIzq(handle)) { // O(log H )    ( peor caso alrura del árbol)

            // Inicio el hijo mayor
            int hijoMayor = hijoIzq(handle);                        // O(1)
            Tupla<T, Integer> hijoIzq = arregloHeap.get(hijoMayor); // O(1)

            // Veo si el de la derecha es mas grande usando el comparador
            if (tieneHijoDer(handle) && compararTuplasPrimerComponente(arregloHeap.get(hijoDer(handle)), hijoIzq) > 0) { // O(1)
                hijoMayor += 1; // O(1)
            }

            Tupla<T, Integer> nodoActual = arregloHeap.get(handle);        // O(1)
            Tupla<T, Integer> tuplaHijoMayor = arregloHeap.get(hijoMayor); // O(1)

            // Si mi hijo mayor es mas grande que mi padre lo reemplazo y cambio los handles del padre.
            if (compararTuplasPrimerComponente(tuplaHijoMayor, nodoActual) > 0) { // O(1)
                // Intercambiamos los elementos de tupla padre e hijo
                arregloHeap.set(hijoMayor, nodoActual);  // O(1)
                arregloHeap.set(handle, tuplaHijoMayor); // O(1)

                // Actualizamos los handles del otro heap
                handles[tuplaHijoMayor.segundo()] = handle; // O(1)
                handles[nodoActual.segundo()] = hijoMayor;  // O(1)

                handle = hijoMayor; // O(1)
            } else {
                break;
            }
        }
    }

    private void cambiarHandle(int handle, int nuevoHandle) { // O(1)
        Tupla<T, Integer> tuplaHandle = arregloHeap.get(handle); // O(1)
        tuplaHandle.cambiarSegundo(nuevoHandle);   // O(1)
    }

    private void intercambiarHandles(int nuevoHandleA, int nuevoHandleB, int handleACambiarA, int handleACambiarB) { // O(1)
        Tupla<T, Integer> tuplaHandleA = arregloHeap.get(handleACambiarB); // O(1)
        Tupla<T, Integer> tuplaHandleB = arregloHeap.get(handleACambiarA); // O(1)

        // Intercambio los handles
        tuplaHandleA.cambiarSegundo(nuevoHandleA); // O(1)
        tuplaHandleB.cambiarSegundo(nuevoHandleB); // O(1)
    }

    private void siftUp(int handle) {  // O(log H)
        int indicePadre = padre(handle);                    // O(1)

        Tupla<T,Integer> tuplaHijo = arregloHeap.get(handle);         // O(1)
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre);   // O(1)

        T hijoElem = tuplaHijo.primero();    // O(1)
        T padreElem = tuplaPadre.primero();  // O(1)

        // Mientras no sea raiz y padre sea menor al hijo, subimos al hijo
        while ( handle != 0 && comparador.compare(padreElem, hijoElem) < 0 ) { // peor caso O(log H), es decir altura del árbol

            // Intercambiamos los elementos de tupla padre e hijo
            tuplaPadre.cambiarPrimero(tuplaHijo.primero());   // O(1)
            tuplaHijo.cambiarPrimero(padreElem);              // O(1)

            // Actualizamos padres e hijos
            tuplaHijo = arregloHeap.get(indicePadre);   // O(1)
            hijoElem = tuplaHijo.primero();             // O(1)

            indicePadre = padre(indicePadre);           // O(1)
            tuplaPadre = arregloHeap.get(indicePadre);  // O(1)
            padreElem = tuplaPadre.primero();           // O(1)
        }
    }

    private void siftDown(int handle) { // O(log H)
        while (tieneHijoIzq(handle)) {  // O(log H)

            // Inicio el hijo menor
            int hijoMenor = hijoIzq(handle);                             // O(1)
            Tupla<T, Integer> hijoIzq = arregloHeap.get(hijoMenor);      // O(1)

            // Notemos que la complejidad de ambos ifs es O(1)

            // Veo si el de la derecha es mas grande usando el comparador
            if (tieneHijoDer(handle) && compararTuplasPrimerComponente(arregloHeap.get(hijoDer(handle)), hijoIzq) > 0) { // O(1)
                hijoMenor += 1;
            }

            Tupla<T, Integer> nodoActual = arregloHeap.get(handle);           // O(1)
            Tupla<T, Integer> tuplaHijoMenor = arregloHeap.get(hijoMenor);    // O(1)

            // Si mi hijo menor es mayor lo cambio por el nodo actual.
            if (compararTuplasPrimerComponente(tuplaHijoMenor, nodoActual) > 0) {  // O(1)

                T elemPadre = nodoActual.primero();       // O(1)
                T elemHijo = tuplaHijoMenor.primero();    // O(1)

                // Intercambiamos los elementos de tupla padre e hijo
                tuplaHijoMenor.cambiarPrimero(elemPadre); // O(1)
                nodoActual.cambiarPrimero(elemHijo);      // O(1)
            }

            handle = hijoMenor;  // O(1)
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

        // Nos fijamos si hay que hacer sift up o sift down
        int indicePadre = padre(handle); // O(1)
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre); // O(1)

        //La compejidad de if then else es O(max{complejidad if, complejidad else}) = O(max{O(log H),O(log H)}) = O(log H)
        if (handle != 0 && compararTuplasPrimerComponente(tuplaPadre, tuplaUltimo) < 0) { // O(1)
            siftUpHandles(handle, heapHandles); // O(log H)
        } else {
            siftDownHandles(handle, heapHandles); // O(log H)
        }
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

        // Nos fijamos si hay que hacer sift up o sift down
        int indicePadre = padre(handle);                               // O(1)
        Tupla<T,Integer> tuplaPadre = arregloHeap.get(indicePadre);    // O(1)

        //La compejidad de if then else es O(max{complejidad if, complejidad else}) = O(max{O(log H),O(log H)}) = O(log H)
        if (handle != 0 && compararTuplasPrimerComponente(tuplaPadre, tuplaACambiar) < 0) { // O(1)
            siftUpHandles(handle, handles);    // O(log H)
        } else {
            siftDownHandles(handle, handles);  // O(log H)
        }
    }
}