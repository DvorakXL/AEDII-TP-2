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
    void funcion_despachar_antiguos_y_variables_afectadas() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);


        // Veamos que despacha bien el traslado mas antiguo que es el 18
        int[] res1 = sis.despacharMasAntiguos(1);
        assertEquals(18, res1[0]);

        // Max ganancia está vacío, luego agrega ciudad origen
        assertSetEquals(new ArrayList<>(Arrays.asList(9)), sis.ciudadesConMayorGanancia());
        // Max perdida está vacío, luego agrega ciudad destino
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 900
        assertEquals(900,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 1
        assertEquals(1, sis.cantTraslados);
        // La ciudad con mayor superavit será 9 con 900
        assertEquals(9, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 900
        assertEquals(900, sis.gananciaPromedioPorTraslado());


        //Ahora probemos despachando los siguientes tres mas antiguos
        ArrayList<Integer> res2 = new ArrayList<>();
        for (int elem : sis.despacharMasAntiguos(3)){
            res2.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(21,14,15)), res2);


        // Ahora  la ciudad con Mayor ganancia van a ser 2 y 3 con 2000
        assertSetEquals(new ArrayList<>(Arrays.asList(2,3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida seran 7 y 8 con 2000
        assertSetEquals(new ArrayList<>(Arrays.asList(7,8)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 5300
        assertEquals(5300,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 4
        assertEquals(4, sis.cantTraslados);
        // La ciudad con mayor superavit será 2 con 2000
        assertEquals(2, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 1325
        assertEquals(1325, sis.gananciaPromedioPorTraslado());


        //Ahora despachemos los próximos 5 mas antiguos
        ArrayList<Integer> res3 = new ArrayList<>();
        for (int elem : sis.despacharMasAntiguos(5)){
            res3.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(1,5,9,11,2)), res3);


        // Ahora  la ciudad con Mayor ganancia va a ser  3 con 4000
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida seran 5 con 2400
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 8600
        assertEquals(8600,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 9
        assertEquals(9, sis.cantTraslados);
        // La ciudad con mayor superavit será 3 con 3100
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 955
        assertEquals(955, sis.gananciaPromedioPorTraslado());

        // Veamos que despacha bien el siguiente traslado mas redituable que es el 8
        int[] res4 = sis.despacharMasAntiguos(1);
        assertEquals(8, res4[0]);


        // Ahora  la ciudad con Mayor ganancia va a ser  3 con 4000
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida sera 8 con 2500
        assertSetEquals(new ArrayList<>(Arrays.asList(8)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 9100
        assertEquals(9100,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 10
        assertEquals(10, sis.cantTraslados);
        // La ciudad con mayor superavit será 3 con 3100
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 910
        assertEquals(910, sis.gananciaPromedioPorTraslado());


        //Ahora despachemos los próximos 10 mas antiguos
        ArrayList<Integer> res5 = new ArrayList<>();
        for (int elem : sis.despacharMasAntiguos(10)){
            res5.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(13,17,4,3,16,7,23,24,10,6)), res5);


        // Ahora  la ciudad con Mayor ganancia va a ser  3 con 6000
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida sera 7 con 3510
        assertSetEquals(new ArrayList<>(Arrays.asList(7)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 16611
        assertEquals(16711,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 20
        assertEquals(20, sis.cantTraslados);
        // La ciudad con mayor superavit será 3 con 4900
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 835
        assertEquals(835, sis.gananciaPromedioPorTraslado());

        // Ahora terminemos de despachar todo, pero excediendonos de la cantidad faltante
        ArrayList<Integer> res6 = new ArrayList<>();
        for (int elem : sis.despacharMasAntiguos(100)){
            res6.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(12,22,20,25,19)), res6);


        // Ahora  la ciudad con Mayor ganancia va a ser  3 con 6050
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        // La ciudad con mayor perdida sera 6 con 5501
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 22461
        assertEquals(22461,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 25
        assertEquals(25, sis.cantTraslados);
        // La ciudad con mayor superavit será 3 con 5150
        assertEquals(3, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 898
        assertEquals(898, sis.gananciaPromedioPorTraslado());
    }

    @Test
    void mismas_ganancias_y_perdidas(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
                new Traslado(100, 0, 1, 5000, 2),
                new Traslado(120, 2, 3, 5000, 3),
                new Traslado(130, 4, 5, 5000, 6),
        };

        // Registro traslados
        sis.registrarTraslados(nuevos);

        // Despacho los 3 mas redituables
        ArrayList<Integer> res0 = new ArrayList<>();
        for (int elem : sis.despacharMasRedituables(3)){
            res0.add(elem);
        }
        assertSetEquals(new ArrayList<>(Arrays.asList(100,120,130)), res0);

        // Ahora  las ciudades con mayor ganancia seràn las 0,2 y 4
        assertEquals(new ArrayList<>(Arrays.asList(0,2,4)), sis.maxGanancia);
        // La ciudad con mayor perdida serán 1,3 y 5
        assertSetEquals(new ArrayList<>(Arrays.asList(1,3,5)), sis.ciudadesConMayorPerdida());
        // La variable ganancia pasa a valer 15000
        assertEquals(15000,sis.sumaGanancia);
        // La variable cantTraslados pasa a valer 3
        assertEquals(3, sis.cantTraslados);
        // La ciudad con mayor superavit será 0 con 5000
        assertEquals(0, sis.ciudadConMayorSuperavit());
        // La ganancia promedio será 3000
        assertEquals(5000, sis.gananciaPromedioPorTraslado());

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

    @Test
    void mayor_superavit(){
        Traslado[] nuevos0 = new Traslado[] {
                new Traslado(100,0,1,50000,150),
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos0);

        // Caso una sola ciudad despachada, debe devolverme ciudad de origen, que es 0
        sis.despacharMasRedituables(1);
        assertEquals(0, sis.ciudadConMayorSuperavit());


        Traslado[] nuevos1 = new Traslado[] {
                new Traslado(101,2,3,70000,151),
                new Traslado(102,4,5,20000,152),
                new Traslado(103,6,7,55000,153),

        };
        sis.registrarTraslados(nuevos1);

        // Caso varios despachos, debe devolver aquella ciudad con mayor superavit, es decir, 2
        sis.despacharMasRedituables(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        Traslado[] nuevos2 = new Traslado[] {
                new Traslado(104,8,9,70000,154),
        };
        sis.registrarTraslados(nuevos2);

        // Caso empate, debe devolver aquella ciudad con menor ID, es decir, 2
        sis.despacharMasRedituables(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        Traslado[] nuevos3 = new Traslado[] {
                new Traslado(105,8,2,20000,155),
        };
        sis.registrarTraslados(nuevos3);

        // ahora el superavit de 2 disminuyó en 20000 y el de 8 aumento, luego 8 tendra mayor superavit
        sis.despacharMasRedituables(1);
        assertEquals(8, sis.ciudadConMayorSuperavit());

        Traslado[] nuevos4 = new Traslado[] {
                new Traslado(106,0,1,40000,156),
        };
        sis.registrarTraslados(nuevos4);

        // Caso empate, debe devolver aquella ciudad con menor ID, es decir, 0
        sis.despacharMasRedituables(1);
        assertEquals(0, sis.ciudadConMayorSuperavit());
    }
}