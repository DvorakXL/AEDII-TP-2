package aed;

import aed.Comparadores.ComparadorMasAntiguo;
import aed.Comparadores.ComparadorMasRedituable;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    ComparatorHeap<Traslado> trasladosMasRedituables;
    ComparatorHeap<Traslado> trasladosMasAntiguos;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        // Implementar
        this.trasladosMasRedituables = new ComparatorHeap<>(traslados, new ComparadorMasRedituable());
        this.trasladosMasAntiguos = new ComparatorHeap<>(trasladosMasRedituables, new ComparadorMasAntiguo());
    }

    public Traslado sacarMasRedituable() {
        Traslado res = trasladosMasRedituables.chusmear();
        trasladosMasRedituables.desencolar();

        return res;
    }

    public Traslado sacarMasAntiguo() {
        Traslado res = this.trasladosMasAntiguos.chusmear();
        this.trasladosMasAntiguos.desencolar();

        return res;
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado t : traslados) {
            this.trasladosMasRedituables.encolar(t);
            this.trasladosMasAntiguos.encolar(t);
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
            despachos[i] = trasladosMasRedituables.chusmear().id();
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
            despachos[i] = trasladosMasAntiguos.chusmear().id();
            trasladosMasAntiguos.desencolarEnlazado(trasladosMasRedituables);
        }

        return despachos;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}
