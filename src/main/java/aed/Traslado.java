package aed;

public class Traslado {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public int id() {
        return this.id;
    }

    public int ganancia() {
        return this.gananciaNeta;
    }

    public int destino() {
        return this.destino;
    }

    public int origen() {
        return this.origen;
    }

    public int timestamp() {
        return this.timestamp;
    }
}
