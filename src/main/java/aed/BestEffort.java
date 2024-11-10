package aed;

import aed.Comparadores.ComparadorMasAntiguo;
import aed.Comparadores.ComparadorMasRedituable;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    ComparatorHeap<Traslado> trasladosMasRedituables;
    ComparatorHeap<Traslado> trasladosMasAntiguos;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        // Implementar
        this.trasladosMasRedituables = new ComparatorHeap<>(traslados, new ComparadorMasRedituable());
        this.trasladosMasAntiguos = new ComparatorHeap<>(traslados, new ComparadorMasAntiguo());
    }

    public Traslado sacarMasRedituable() {
        Traslado res = this.trasladosMasRedituables.chusmear();
        this.trasladosMasRedituables.desencolar();

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

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
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
