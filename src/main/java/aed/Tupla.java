package aed;

public class Tupla<U, V> {
    U t1;
    V t2;

    public Tupla(U t1, V t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public U primero() {
        return t1;
    }

    public V segundo() {
        return t2;
    }

    public void cambiarPrimero(U t1) {
        this.t1 = t1;
    }

    public void cambiarSegundo(V t2) {
        this.t2 = t2;
    }
}
