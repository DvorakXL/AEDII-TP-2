package aed.Comparadores;

import aed.Traslado;

public class ComparadorMasRedituable implements Comparator<Traslado> {

    @Override
    public int compare(Traslado t1, Traslado t2) {
        // Si las ganancias son iguales compara usando el id del traslado t1.id < t2.id. Ej: 10, 20
        if (t1.ganancia() == t2.ganancia()) {
            return Integer.compare(t2.id(), t1.id());
        }

        // Si no, lo compara por ganancia t1.ganancia > t2.ganancia. Ej: 1000,50 > 0
        return Integer.compare(t1.ganancia(), t2.ganancia());
    }
}
