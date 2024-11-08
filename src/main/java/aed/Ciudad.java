package aed;

public class Ciudad {

    int ganancia;
    int perdida;

    public Ciudad(int ganancia, int perdida) {
        this.ganancia = ganancia;
        this.perdida = perdida;
    }

    public int ganancia() {
        return ganancia;
    }

    public int perdida() {
        return perdida;
    }

    public void agregarGanancia(int ganancia) {
        this.ganancia += ganancia;
    }

    public void agregarPerdida(int perdida) {
        this.perdida += perdida;
    }

    public int superavit() {
        return this.ganancia - this.perdida;
    }
}
