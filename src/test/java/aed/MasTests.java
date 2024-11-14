package aed;

import static org.junit.jupiter.api.Assertions.*;

import aed.Comparadores.ComparadorInteger;
import aed.Comparadores.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MasTests {
    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 10;
        listaTraslados = new Traslado[] {
                                            //Traslados con mismas ganancias, distintos timestamps
                                            new Traslado(1, 0, 1, 200, 10),
                                            new Traslado(2,1,2,200,15),
                                            new Traslado(3,1,3,200,21),
                                            new Traslado(4,4,5,200,20),
                                            new Traslado(5,2,4,200,11),
                                            //Traslados con ganancias y timestamps variados
                                            new Traslado(6,6,7,10,45),
                                            new Traslado(7, 9,2,300,23),
                                            new Traslado(8,5,8,500,16),
                                            new Traslado(9,9,2,700,12),
                                            new Traslado(10,4,7,1000,30),
                                            //Traslados con mismas ganancias pero valores altos
                                            new Traslado(11,3,5,2000,13),
                                            new Traslado(12,7,8,2000,50),
                                            new Traslado(13,3,9,2000,17),
                                            new Traslado(14,2,8,2000,5),
                                            new Traslado(15,3,7,2000,9),
                                            //Traslado con muy poca gananciaNeta
                                            new Traslado(16,4,6,1,22),
                                            //Traslado con mucha gananciaNeta
                                            new Traslado (17,5,6,3000,18),
                                            //Traslado con muy poco timestamp
                                            new Traslado(18,9,3,900,1),
                                            //Traslado con mucho timestamp
                                            new Traslado(19,7,4,500,100),
                                            //Mas Traslados
                                            new Traslado(20,7,4,700,70),
                                            new Traslado(21,6,5,400,4),
                                            new Traslado(22,3,8,50,51),
                                            new Traslado(23,7,2,400,24),
                                            new Traslado(24,8,7,500,29),
                                            new Traslado(25,3,6,2500,99),
                                        };
    }
    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void sacar_primero_mas_redituable(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

//        assertEquals(17,sis.sacarMasRedituable().id());
//        assertEquals(25,sis.sacarMasRedituable().id());
//        assertEquals(11,sis.sacarMasRedituable().id());
//        assertEquals(12,sis.sacarMasRedituable().id());
//        assertEquals(13,sis.sacarMasRedituable().id());
//        assertEquals(14,sis.sacarMasRedituable().id());
//        assertEquals(15,sis.sacarMasRedituable().id());
//        assertEquals(10,sis.sacarMasRedituable().id());
//        assertEquals(18,sis.sacarMasRedituable().id());
//        assertEquals(9,sis.sacarMasRedituable().id());
//        assertEquals(20,sis.sacarMasRedituable().id());
//        assertEquals(8,sis.sacarMasRedituable().id());
//        assertEquals(19,sis.sacarMasRedituable().id());
//        assertEquals(24,sis.sacarMasRedituable().id());
//        assertEquals(21,sis.sacarMasRedituable().id());
//        assertEquals(23,sis.sacarMasRedituable().id());
//        assertEquals(7,sis.sacarMasRedituable().id());
//        assertEquals(1,sis.sacarMasRedituable().id());
//        assertEquals(2,sis.sacarMasRedituable().id());
//        assertEquals(3,sis.sacarMasRedituable().id());
//        assertEquals(4,sis.sacarMasRedituable().id());
//        assertEquals(5,sis.sacarMasRedituable().id());
//        assertEquals(22,sis.sacarMasRedituable().id());
//        assertEquals(6,sis.sacarMasRedituable().id());
//        assertEquals(16,sis.sacarMasRedituable().id());

    }

    @Test
    void sacar_primero_mas_antiguo(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

//        assertEquals(18,sis.sacarMasAntiguo().id());
//        assertEquals(21,sis.sacarMasAntiguo().id());
//        assertEquals(14,sis.sacarMasAntiguo().id());
//        assertEquals(15,sis.sacarMasAntiguo().id());
//        assertEquals(1,sis.sacarMasAntiguo().id());
//        assertEquals(5,sis.sacarMasAntiguo().id());
//        assertEquals(9,sis.sacarMasAntiguo().id());
//        assertEquals(11,sis.sacarMasAntiguo().id());
//        assertEquals(2,sis.sacarMasAntiguo().id());
//        assertEquals(8,sis.sacarMasAntiguo().id());
//        assertEquals(13,sis.sacarMasAntiguo().id());
//        assertEquals(17,sis.sacarMasAntiguo().id());
//        assertEquals(4,sis.sacarMasAntiguo().id());
//        assertEquals(3,sis.sacarMasAntiguo().id());
//        assertEquals(16,sis.sacarMasAntiguo().id());
//        assertEquals(7,sis.sacarMasAntiguo().id());
//        assertEquals(23,sis.sacarMasAntiguo().id());
//        assertEquals(24,sis.sacarMasAntiguo().id());
//        assertEquals(10,sis.sacarMasAntiguo().id());
//        assertEquals(6,sis.sacarMasAntiguo().id());
//        assertEquals(12,sis.sacarMasAntiguo().id());
//        assertEquals(22,sis.sacarMasAntiguo().id());
//        assertEquals(20,sis.sacarMasAntiguo().id());
//        assertEquals(25,sis.sacarMasAntiguo().id());
//        assertEquals(19,sis.sacarMasAntiguo().id());
        
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
    }

//    @Test
//    void despachar_con_mas_ganancia(){
//        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
//
//        //Aca no me deja usar el assert pero la idea es decir cuales son los traslados de las ciudades que debe devolver y
//        //compararlos con los que devuelve despacharMasRedituables e ir sacando
//        assertSetEquals(new ArrayList<>(Arrays.asList(17,25,11)), sis.despacharMasRedituables(3));
//        assertSetEquals(new ArrayList<>(Arrays.asList(12,13,14,15,10)), sis.despacharMasRedituables(5));
//        assertSetEquals(new  ArrayList<>(Arrays.asList(9,20,8,19,24,21,23)), sis.despacharMasRedituables(7));
//    }
}


