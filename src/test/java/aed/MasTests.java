package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class MasTests {
    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;
    Traslado[] listaTrasladosPrimerTercio;
    Traslado[] listaTrasladosSegundoTercio;
    Traslado[] listaTrasladosTercerTercio;

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

        listaTrasladosPrimerTercio = new Traslado[] {
                new Traslado(1, 0, 1, 200, 10),
                new Traslado(2,1,2,200,15),
                new Traslado(3,1,3,200,21),
                new Traslado(4,4,5,200,20),
                new Traslado(5,2,4,200,11),
                new Traslado(6,6,7,10,45),
                new Traslado(7, 9,2,300,23),
                new Traslado(8,5,8,500,16)
        };

        listaTrasladosSegundoTercio = new Traslado[] {
                new Traslado(9,9,2,700,12),
                new Traslado(10,4,7,1000,30),
                new Traslado(11,3,5,2000,13),
                new Traslado(12,7,8,2000,50),
                new Traslado(13,3,9,2000,17),
                new Traslado(14,2,8,2000,5),
                new Traslado(15,3,7,2000,9),
                new Traslado(16,4,6,1,22)
        };

        listaTrasladosTercerTercio = new Traslado[] {
                new Traslado (17,5,6,3000,18),
                new Traslado(18,9,3,900,1),
                new Traslado(19,7,4,500,100),
                new Traslado(20,7,4,700,70),
                new Traslado(21,6,5,400,4),
                new Traslado(22,3,8,50,51),
                new Traslado(23,7,2,400,24),
                new Traslado(24,8,7,500,29),
                new Traslado(25,3,6,2500,99)
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
    void funcion_despachar_reditaubles_y_variables_afectadas() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);


        // Veamos que despacha bien el traslado mas redituable que es el 17
        int[] res1 = sis.despacharMasRedituables(1);
        assertEquals(17, res1[0]);


        // Max ganancia está vacío, luego agrega ciudad origen
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorGanancia());
        // Max perdida está vacío, luego agrega ciudad destino
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 3000
        assertEquals(3000,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 1
        assertEquals(1, sis.cantTraslados);
        // La ciudad con mayor superavit será 5 con 3000
        assertEquals(5, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 3000
        assertEquals(3000, sis.gananciaPromedioPorTraslado());


        //Ahora probemos despachando los siguientes tres mas redituables
        ArrayList<Integer> res2 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(3)){
            res2.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(25,11,12)), res2);


        // Ahora  la ciudad con Mayor ganancia va a ser la 3 con 5500
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida seguirá siendo la ciudaad 6, ahora con 5500
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 9500
        assertEquals(9500,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 4
        assertEquals(4, sis.cantTraslados);
        // La ciudad con mayor superavit será 3 con 4500
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 2375
        assertEquals(2375, sis.gananciaPromedioPorTraslado());



        //Ahora despachemos los próximos 5 mas redituables
        ArrayList<Integer> res3 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(5)){
            res3.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(13,14,15,10,18)), res3);


        // Ahora  la ciudad con Mayor ganancia va a seguir siendo la 3 con 8500
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida seguirá siendo la ciudaad 6, ahora con 5500
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 17400
        assertEquals(17400,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 9
        assertEquals(9, sis.cantTraslados);
        // La ciudad con mayor superavit será 3  con 7600
        assertEquals(3, sis.ciudadConMayorSuperavit());


        // Veamos que despacha bien el siguiente traslado mas redituable que es el 9
        int[] res4 = sis.despacharMasRedituables(1);
        assertEquals(9, res4[0]);


        // Ahora  la ciudad con Mayor ganancia va a seguir siendo la 3 con 8500
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida seguirá siendo la ciudaad 6, ahora con 5500
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 18100
        assertEquals(18100,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 10
        assertEquals(10, sis.cantTraslados);
        // La ciudad con mayor superavit será 3 con 7600
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 1810
        assertEquals(1810, sis.gananciaPromedioPorTraslado());


        //Ahora despachemos los próximos 10 mas redituables
        ArrayList<Integer> res5 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(10)){
            res5.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(20,8,19,24,21,23,7,1,2,3)), res5);


         // La ciudad con Mayor ganancia va a seguir siendo la 3 con 8500
         assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
         // La ciudad con mayor perdida seguirá siendo la ciudaad 6, ahora con 5500
         assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
         // La variable ganancia pasa a valer 22000
         assertEquals(22000,sis.sumaGanancia);
         // La variable cantTraslados pasa a valer 20
         assertEquals(20, sis.cantTraslados);
         // La ciudad con mayor superavit será 3 con 7400
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 1100
        assertEquals(1100, sis.gananciaPromedioPorTraslado());

         // Ahora terminemos de despachar todo, pero excediendonos de la cantidad faltante
         ArrayList<Integer> res6 = new ArrayList<>();
         for (int elem : sis.despacharMasRedituables(100)){
             res6.add(elem);
         }
         assertSetEquals(new ArrayList<>(Arrays.asList(4,5,22,6,16)), res6);

         // La ciudad con Mayor ganancia va a seguir siendo la 3 con 8550
         assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
         // La ciudad con mayor perdida seguirá siendo la ciudaad 6, ahora con 5501
         assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
         // La variable ganancia pasa a valer 22461
         assertEquals(22461,sis.sumaGanancia);
         // La variable cantTraslados pasa a valer 25
         assertEquals(25, sis.cantTraslados);
         // La ciudad con mayor superavit será 3 con 7650
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 898
        assertEquals(898, sis.gananciaPromedioPorTraslado());

    }

    @Test
    void despachar_con_mas_ganancia(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        assertEquals(17, sis.despacharMasRedituables(1)[0]);

        ArrayList<Integer> arreglo1 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(2)) {
            arreglo1.add(elem);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(25,11)), arreglo1);

        ArrayList<Integer> arreglo2 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(5)) {
            arreglo2.add(elem);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(12,13,14,15,10)), arreglo2);
        assertEquals(18, sis.despacharMasRedituables(1)[0]);
        assertEquals(9, sis.despacharMasRedituables(1)[0]);

        ArrayList<Integer> arreglo3 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(6)) {
            arreglo3.add(elem);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(20,8,19,24,21,23)), arreglo3);
    }

    @Test
    void registrar_traslados_despachando_mixto() {
        BestEffort sis = new BestEffort(cantCiudades, listaTrasladosSegundoTercio);

        ArrayList<Integer> arreglo1 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(4)) {
            arreglo1.add(elem);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(11, 12, 13, 14)), arreglo1);

        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(8)), sis.ciudadesConMayorPerdida());
        assertEquals(3, sis.ciudadConMayorSuperavit());

        ArrayList<Integer> arreglo2 = new ArrayList<>();
        for (int elem : sis.despacharMasAntiguos(2)) {
            arreglo2.add(elem);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(15, 9)), arreglo2);

        sis.registrarTraslados(listaTrasladosTercerTercio);

        ArrayList<Integer> arreglo3 = new ArrayList<>();
        for (int elem : sis.despacharMasAntiguos(4)) {
            arreglo3.add(elem);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(18, 21, 17, 16)), arreglo3);

        ArrayList<Integer> arreglo4 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(2)) {
            arreglo4.add(elem);
        }

        assertSetEquals(new ArrayList<>(Arrays.asList(25, 10)), arreglo4);
    }
}


