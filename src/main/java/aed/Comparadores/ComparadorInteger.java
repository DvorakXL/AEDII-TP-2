package aed.Comparadores;

// Esto lo usamos para los tests de heap pero no es necesario
public class ComparadorInteger implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return Integer.compare(o1, o2);
    }
}
