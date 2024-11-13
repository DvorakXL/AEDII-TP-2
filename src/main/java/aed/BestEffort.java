package aed;

import aed.Comparadores.ComparadorMasAntiguo;
import aed.Comparadores.ComparadorMasRedituable;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    ComparatorHeap<Traslado> trasladosMasRedituables;
    ComparatorHeap<Traslado> trasladosMasAntiguos;
    ComparatorHeap<Tupla<Ciudad, Integer>> ciudades;
    int maxSuperavit;
    ArrayList<Integer> maxGanancia;
    ArrayList<Integer> maxPerdida;
    int sumaGanancia;
    int cantTraslados;


    public BestEffort(int cantCiudades, Traslado[] traslados) {
        // Implementar
        this.trasladosMasRedituables = new ComparatorHeap<>(traslados, new ComparadorMasRedituable());
        this.trasladosMasAntiguos = new ComparatorHeap<>(trasladosMasRedituables, new ComparadorMasAntiguo());
        this.ciudades = new ComparatorHeap<>();
        this.maxGanancia = new ArrayList<>();
        this.maxPerdida = new ArrayList<>();
        this.maxGanancia.add(0);
        this.maxGanancia.add(0);
        this.maxSuperavit = 0;
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
            sumaGanancia += trasladosMasRedituables.chusmear().ganancia();
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
