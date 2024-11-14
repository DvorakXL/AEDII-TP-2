package aed;

import aed.Comparadores.ComparadorInteger;
import aed.Comparadores.ComparadorMasAntiguo;
import aed.Comparadores.ComparadorMasRedituable;

import java.util.ArrayList;

//Usamos las variables C = conjunto de ciudades, T = conjunto de traslados
//Aclaracion; si a la derecha de una linea de codigo no hay un comentario acerca de la complejidad, es O(1)

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

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        this.trasladosMasRedituables = new ComparatorHeap<>(traslados, new ComparadorMasRedituable());  // O(|T|)
        this.trasladosMasAntiguos = new ComparatorHeap<>(trasladosMasRedituables, new ComparadorMasAntiguo());  // O(|T|)
        
        this.arregloDeHandlesSuperavit = new int[cantCiudades];     // O(1)
        this.gananciasCiudades = new ArrayList<>();
        this.perdidasCiudades = new ArrayList<>();

        // O(|C|)
        Integer[] initSuperavit = new Integer[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {        // La guarda se evalua |C| veces
            this.arregloDeHandlesSuperavit[i] = i;
            this.gananciasCiudades.add(0);      // Metodo de ArrayList .add es O(1) amortizado
            this.perdidasCiudades.add(0);
            initSuperavit[i] = 0;
        }

        this.heapSuperavit = new ComparatorHeap<>(initSuperavit, arregloDeHandlesSuperavit, new ComparadorInteger());

        this.maxGanancia = new ArrayList<>();
        this.maxPerdida = new ArrayList<>();
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado t : traslados) {
            this.trasladosMasRedituables.encolarEnlazado(t, trasladosMasAntiguos);

            gananciasCiudades.add(0);
            perdidasCiudades.add(0);
        }
    }

    public int[] despacharMasRedituables(int n) {

        // Me fijo que el n no supere la longitud de mi heap
        int capacidad = n;
        int longitudHeap = trasladosMasRedituables.size();
        if (capacidad > longitudHeap) {
            capacidad = longitudHeap;
        }

        int[] despachos = new int[capacidad];

        for (int i = 0; i < capacidad; i++) {
            Traslado trasladoRedit = trasladosMasRedituables.tope();
            despachos[i] = trasladoRedit.id();

            int ciudadOrigen = trasladoRedit.origen;
            int ciudadDestino = trasladoRedit.destino;
            int gananciaDelDespacho = trasladoRedit.ganancia();

            int handleCiudadOrigen = arregloDeHandlesSuperavit[ciudadOrigen];
            int handleCiudadDestino = arregloDeHandlesSuperavit[ciudadDestino];

            int nuevoSuperavitOrigen = heapSuperavit.obtenerEnHandle(handleCiudadOrigen).primero() + gananciaDelDespacho;
            int nuevoSuperavitDestino = heapSuperavit.obtenerEnHandle(handleCiudadDestino).primero() - gananciaDelDespacho;

            heapSuperavit.actualizar(handleCiudadOrigen, nuevoSuperavitOrigen, arregloDeHandlesSuperavit);
            heapSuperavit.actualizar(handleCiudadDestino, nuevoSuperavitDestino, arregloDeHandlesSuperavit);

            gananciasCiudades.set(ciudadOrigen, gananciasCiudades.get(ciudadOrigen) + gananciaDelDespacho);
            perdidasCiudades.set(ciudadDestino, perdidasCiudades.get(ciudadDestino) + gananciaDelDespacho);

            if (maxGanancia.isEmpty()) {
                maxGanancia.add(ciudadOrigen);
            } else {
                Integer indiceCiudadMaximaGanancia = maxGanancia.get(0);

                if (gananciasCiudades.get(indiceCiudadMaximaGanancia) < gananciasCiudades.get(ciudadOrigen) || indiceCiudadMaximaGanancia.equals(ciudadOrigen)) {
                    maxGanancia.clear();
                    maxGanancia.add(ciudadOrigen);
                } else if (gananciasCiudades.get(indiceCiudadMaximaGanancia).equals(gananciasCiudades.get(ciudadOrigen))) {
                    maxGanancia.add(ciudadOrigen);
                }
            }

            if (maxPerdida.isEmpty()) {
                maxPerdida.add(ciudadDestino);
            } else {
                Integer indiceCiudadMaximaPerdida = maxPerdida.get(0);

                if (perdidasCiudades.get(indiceCiudadMaximaPerdida) < perdidasCiudades.get(ciudadDestino) || indiceCiudadMaximaPerdida.equals(ciudadDestino)) {
                    maxPerdida.clear();
                    maxPerdida.add(ciudadDestino);
                } else if (perdidasCiudades.get(indiceCiudadMaximaPerdida).equals(perdidasCiudades.get(ciudadDestino))) {
                    maxPerdida.add(ciudadDestino);
                }
            }

            // Esto es para el promedio por despacho
            sumaGanancia += trasladoRedit.ganancia();
            cantTraslados += 1;

            trasladosMasRedituables.desencolarEnlazado(trasladosMasAntiguos);
        }

        return despachos;
    }

    public int[] despacharMasAntiguos(int n){
        // Me fijo que el n no supere la longitud de mi heap
        int capacidad = n;
        int longitudHeap = trasladosMasAntiguos.size();
        if (capacidad > longitudHeap) {
            capacidad = longitudHeap;
        }

        int[] despachos = new int[capacidad];

        for (int i = 0; i < capacidad; i++) {
            Traslado trasladoAntiguos = trasladosMasAntiguos.tope();
            despachos[i] = trasladoAntiguos.id();

            int ciudadOrigen = trasladoAntiguos.origen;
            int ciudadDestino = trasladoAntiguos.destino;
            int gananciaDelDespacho = trasladoAntiguos.ganancia();

            int handleCiudadOrigen = arregloDeHandlesSuperavit[ciudadOrigen];
            int handleCiudadDestino = arregloDeHandlesSuperavit[ciudadDestino];

            int nuevoSuperavitOrigen = heapSuperavit.obtenerEnHandle(handleCiudadOrigen).primero() + gananciaDelDespacho;
            int nuevoSuperavitDestino = heapSuperavit.obtenerEnHandle(handleCiudadDestino).primero() - gananciaDelDespacho;

            heapSuperavit.actualizar(handleCiudadOrigen, nuevoSuperavitOrigen, arregloDeHandlesSuperavit);
            heapSuperavit.actualizar(handleCiudadDestino, nuevoSuperavitDestino, arregloDeHandlesSuperavit);

            gananciasCiudades.set(ciudadOrigen, gananciasCiudades.get(ciudadOrigen) + gananciaDelDespacho);
            perdidasCiudades.set(ciudadDestino, perdidasCiudades.get(ciudadDestino) + gananciaDelDespacho);

            if (maxGanancia.isEmpty()) {
                maxGanancia.add(ciudadOrigen);
            } else {
                Integer indiceCiudadMaximaGanancia = maxGanancia.get(0);

                if (gananciasCiudades.get(indiceCiudadMaximaGanancia) < gananciasCiudades.get(ciudadOrigen) || indiceCiudadMaximaGanancia.equals(ciudadOrigen)) {
                    maxGanancia.clear();
                    maxGanancia.add(ciudadOrigen);
                } else if (gananciasCiudades.get(indiceCiudadMaximaGanancia).equals(gananciasCiudades.get(ciudadOrigen))) {
                    maxGanancia.add(ciudadOrigen);
                }
            }

            if (maxPerdida.isEmpty()) {
                maxPerdida.add(ciudadDestino);
            } else {
                Integer indiceCiudadMaximaPerdida = maxPerdida.get(0);

                if (perdidasCiudades.get(indiceCiudadMaximaPerdida) < perdidasCiudades.get(ciudadDestino) || indiceCiudadMaximaPerdida.equals(ciudadDestino)) {
                    maxPerdida.clear();
                    maxPerdida.add(ciudadDestino);
                } else if (perdidasCiudades.get(indiceCiudadMaximaPerdida).equals(perdidasCiudades.get(ciudadDestino))) {
                    maxPerdida.add(ciudadDestino);
                }
            }

            // Esto es para el promedio por despacho
            sumaGanancia += trasladoAntiguos.ganancia();
            cantTraslados += 1;

            trasladosMasAntiguos.desencolarEnlazado(trasladosMasRedituables);
        }

        return despachos;
    }

    public int ciudadConMayorSuperavit(){
        return heapSuperavit.obtenerEnHandle(0).segundo();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return maxGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return maxPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return sumaGanancia / cantTraslados;
    }
    
}
