package aed.Comparadores;

import aed.Traslado;

public class ComparadorMasRedituable implements Comparator<Traslado> {

    @Override
    public int compare(Traslado t1, Traslado t2) {
        // Si las ganancias son iguales compara usando el id del traslado
        if (t1.ganancia() == t2.ganancia()) {
            return Integer.compare(t1.id(), t2.id());
        }

        // Si no, lo compara por ganancia
        return Integer.compare(t1.ganancia(), t2.ganancia());
    }
}
