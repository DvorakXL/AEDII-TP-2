package aed;

import aed.Comparadores.ComparadorInteger;
import aed.Comparadores.ComparadorMasAntiguo;
import aed.Comparadores.ComparadorMasRedituable;

import java.util.ArrayList;

//Usamos las variables C = conjunto de ciudades, T = conjunto de traslados
//Aclaracion; si a la derecha de una linea de codigo no hay un comentario acerca de la complejidad, es O(1).
//Aclaracion; si hacemos un llamado a una funcion auxiliar y no ponemos la complejidad es porque esta comentada en la definicion de la funcion.

public class BestEffort {
    ComparatorHeap<Traslado> trasladosMasRedituables;
    ComparatorHeap<Traslado> trasladosMasAntiguos;
    ComparatorHeap<Integer> heapSuperavit;
    ArrayList<Integer> maxGanancia;
    ArrayList<Integer> maxPerdida;
    ArrayList<Integer> gananciasCiudades;
    ArrayList<Integer> perdidasCiudades;
    int[] arregloDeHandlesSuperavit;
    int sumaGanancia = 0;
    int cantTraslados = 0;

    public BestEffort(int cantCiudades, Traslado[] traslados) {  // O(|C| + |T|)
        this.trasladosMasRedituables = new ComparatorHeap<>(traslados, new ComparadorMasRedituable());  // O(|T|)
        this.trasladosMasAntiguos = new ComparatorHeap<>(trasladosMasRedituables, new ComparadorMasAntiguo());  // O(|T|)

        this.arregloDeHandlesSuperavit = new int[cantCiudades]; // O(1)
        this.gananciasCiudades = new ArrayList<>(); // O(1)
        this.perdidasCiudades = new ArrayList<>(); // O(1)

        // O(|C|)
        Integer[] initSuperavit = new Integer[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {        // La guarda se evalua |C| veces
            this.arregloDeHandlesSuperavit[i] = i;      // O(1)
            this.gananciasCiudades.add(0);              // Metodo de ArrayList .add es O(1) amortizado
            this.perdidasCiudades.add(0);               // O(1)
            initSuperavit[i] = 0;                       // O(1)
        }

        this.heapSuperavit = new ComparatorHeap<>(initSuperavit, new ComparadorInteger());  // O(|C|)

        this.maxGanancia = new ArrayList<>();  // O(1)
        this.maxPerdida = new ArrayList<>();   // O(1)
    }
    
    // Funciones Auxiliares

    private void actualizarEstadisticasCiudades(ArrayList<Integer> maxGanancia, int ciudadOrigen, ArrayList<Integer> gananciasCiudades) {
        //La compejidad de if then else es O(max{complejidad if, complejidad else}) = O(max{O(1),O(1)}) = O(1)
        if (maxGanancia.isEmpty()) {              // O(1)
            maxGanancia.add(ciudadOrigen);        // O(1)
        } else {  // O(1)
            Integer indiceCiudadMaximaGanancia = maxGanancia.get(0); // O(1)

            //La compejidad del if then else  es O(max{complejidad if, complejidad else}) = O(max{O(1),O(1)}) = O(1)
            if (gananciasCiudades.get(indiceCiudadMaximaGanancia) < gananciasCiudades.get(ciudadOrigen) ||
                    indiceCiudadMaximaGanancia.equals(ciudadOrigen)) {  // O(1 )
                maxGanancia.clear();                // O(1)
                maxGanancia.add(ciudadOrigen);      // O(1)
            } else if (gananciasCiudades.get(indiceCiudadMaximaGanancia).equals(gananciasCiudades.get(ciudadOrigen))) {  // O(1)
                maxGanancia.add(ciudadOrigen);   // O(1)
            }
        }
    }

    private int[] despacharTrasladosHeap(int n, ComparatorHeap<Traslado> heapA, ComparatorHeap<Traslado> heapB) {
        // Me fijo que el n no supere la longitud de mi heap
        int capacidad = n;  // O(1)
        int longitudHeap = heapA.size(); // O(1)
        if (capacidad > longitudHeap) { // O(1)
            capacidad = longitudHeap; // O(1)
        }

        int[] despachos = new int[capacidad]; // O(1)

        // Notemos que las complejidades dentro del bucle son : O(1), O(log |C|) y O(log |T|)
        // Por lo tanto, la complejidad del bucle será O(max{ O(1), O(log |C|) , O(log |T|)}) = O(log |T| + log |C|)
        // Finalmente, el bucle se ejecuta n veces, por lo tanto la complejidad de todo el bucle es O(n (log |T| + log |C|))
        for (int i = 0; i < capacidad; i++) { // la guarda se evalúa n veces
            Traslado trasladoRedit = heapA.tope(); // O(1)
            despachos[i] = trasladoRedit.id();                       // O(1)

            int ciudadOrigen = trasladoRedit.origen;              // O(1) (obtenemos atributos)
            int ciudadDestino = trasladoRedit.destino;            // O(1)
            int gananciaDelDespacho = trasladoRedit.ganancia();   // O(1)

            actualizarSuperavit(ciudadOrigen, gananciaDelDespacho); // O(log |C|)
            actualizarSuperavit(ciudadDestino, -gananciaDelDespacho); // O(log |C|)

            gananciasCiudades.set(ciudadOrigen, gananciasCiudades.get(ciudadOrigen) + gananciaDelDespacho);  // O(1)
            perdidasCiudades.set(ciudadDestino, perdidasCiudades.get(ciudadDestino) + gananciaDelDespacho);  // O(1)

            actualizarEstadisticasCiudades(maxGanancia, ciudadOrigen, gananciasCiudades);

            //La compejidad de if then else es O(max{complejidad if, complejidad else}) = O(max{O(1),O(1)}) = O(1)
            actualizarEstadisticasCiudades(maxPerdida, ciudadDestino, perdidasCiudades);

            // Esto es para el promedio por despacho
            sumaGanancia += trasladoRedit.ganancia();  // O(1)
            cantTraslados += 1;  // O(1)

            heapA.desencolarEnlazado(heapB);  // O(log |T|) (desencolar en heap traslados Redituables)
        }

        return despachos;
    }

    private void actualizarSuperavit(int ciudad, int gananciaDelDespacho) {
        int handleCiudad = arregloDeHandlesSuperavit[ciudad]; // O(1) (accedemos a posiciones)
        int nuevoSuperavitOrigen = heapSuperavit.obtenerEnHandle(handleCiudad).primero() + gananciaDelDespacho; // O(1)

        heapSuperavit.actualizar(handleCiudad, nuevoSuperavitOrigen, arregloDeHandlesSuperavit); // O(log |C|) (heapSuperavit se construye sobre las ciudades)
    }

    // Interfaz

    public void registrarTraslados(Traslado[] traslados){ //  O(|traslados| log(|T|))
        // la complejidad del ciclo es O(|traslados| log(|T|))
        for (Traslado t : traslados) { // la guarda se evalua |traslados|
            this.trasladosMasRedituables.encolarEnlazado(t, trasladosMasAntiguos);  // O(log |T|)
        }
    }

    public int[] despacharMasRedituables(int n) {  // O(n (log |T| + log |C|))
        return despacharTrasladosHeap(n, trasladosMasRedituables, trasladosMasAntiguos);
    }

    public int[] despacharMasAntiguos(int n){  // O(n (log |T| + log |C|))
        return despacharTrasladosHeap(n, trasladosMasAntiguos, trasladosMasRedituables);
    }

    public int ciudadConMayorSuperavit(){  // O(1)
        return heapSuperavit.obtenerEnHandle(0).segundo(); // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){  // O(1)
        return maxGanancia; // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){   // O(1)
        return maxPerdida;  // O(1)
    }

    public int gananciaPromedioPorTraslado(){ // O(1)
        return sumaGanancia / cantTraslados;  // O(1)
    }
}