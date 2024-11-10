package aed.Comparadores;

import aed.Traslado;

public class ComparadorMasAntiguo implements Comparator<Traslado> {

    @Override
    public int compare(Traslado t1, Traslado t2) {
        return Integer.compare(t2.timestamp(), t1.timestamp());
    }
}
