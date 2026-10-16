package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;

import java.util.List;

public class PruebaArbolesPuntos {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "     PRUEBAS ÁRBOLES Y PUNTOS"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        // -----------------------------------------------------
        // CLIENTES
        // -----------------------------------------------------

        for (int i = 1; i <= 5; i++) {

            String codigo =
                    "C00" + i;


            sistema.registrarCliente(
                    new Cliente(
                            codigo,
                            "Cliente " + i
                    )
            );


            sistema.crearMonedero(
                    codigo,
                    new Monedero(
                            "M001",
                            "Principal",
                            0
                    )
            );
        }


        verificar(
                sistema.obtenerClientes().size()
                        == 5,
                "Cinco clientes registrados"
        );


        // -----------------------------------------------------
        // GENERAR DIFERENTES PUNTOS
        // -----------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                1000
        );

        sistema.depositar(
                "C002",
                "M001",
                2000
        );

        sistema.depositar(
                "C003",
                "M001",
                3000
        );

        sistema.depositar(
                "C004",
                "M001",
                4000
        );

        sistema.depositar(
                "C005",
                "M001",
                60000
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 10,
                "C001 tiene 10 puntos"
        );


        verificar(
                sistema.consultarPuntos(
                        "C003"
                ) == 30,
                "HashMap consulta C003 con 30 puntos"
        );


        verificar(
                sistema.consultarPuntos(
                        "C005"
                ) == 600,
                "C005 tiene 600 puntos"
        );


        verificar(
                sistema.consultarRango(
                        "C005"
                ) == RangoCliente.PLATA,
                "600 puntos clasifican al cliente como Plata"
        );


        // -----------------------------------------------------
        // BST
        // -----------------------------------------------------

        List<Cliente> ordenBST =
                sistema
                        .obtenerClientesOrdenadosPorPuntosBST();


        verificar(
                ordenBST.size() == 5,
                "BST contiene los cinco clientes"
        );


        verificar(
                estaOrdenado(
                        ordenBST
                ),
                "Recorrido inorden del BST está ordenado por puntos"
        );


        // -----------------------------------------------------
        // AVL
        // -----------------------------------------------------

        List<Cliente> ordenAVL =
                sistema
                        .obtenerClientesOrdenadosPorPuntosAVL();


        verificar(
                ordenAVL.size() == 5,
                "AVL contiene los cinco clientes"
        );


        verificar(
                estaOrdenado(
                        ordenAVL
                ),
                "Recorrido inorden del AVL está ordenado por puntos"
        );


        verificar(
                sistema.obtenerAlturaAVL()
                        <= 3,
                "AVL mantiene una altura balanceada"
        );


        // -----------------------------------------------------
        // MOSTRAR ÁRBOLES
        // -----------------------------------------------------

        System.out.println(
                "\n=== ORDEN BST ==="
        );


        for (Cliente cliente :
                ordenBST) {

            System.out.println(
                    cliente.getIdentificacion()
                            + " -> "
                            + cliente.getPuntos()
                            + " puntos"
            );
        }


        System.out.println(
                "\nAltura BST: "
                        + sistema
                        .obtenerAlturaBST()
        );


        System.out.println(
                "Altura AVL: "
                        + sistema
                        .obtenerAlturaAVL()
        );


        // -----------------------------------------------------
        // REVERTIR Y ACTUALIZAR ÁRBOLES
        // -----------------------------------------------------

        sistema.revertirUltimaTransaccion();


        verificar(
                sistema.consultarPuntos(
                        "C005"
                ) == 0,
                "Reversión recalcula puntos en HashMap"
        );


        verificar(
                sistema.consultarRango(
                        "C005"
                ) == RangoCliente.BRONCE,
                "Reversión recalcula el rango"
        );


        ordenAVL =
                sistema
                        .obtenerClientesOrdenadosPorPuntosAVL();


        verificar(
                estaOrdenado(
                        ordenAVL
                ),
                "AVL sigue ordenado después de actualizar puntos"
        );


        verificar(
                ordenAVL
                        .get(0)
                        .getIdentificacion()
                        .equals("C005"),
                "C005 vuelve al inicio con cero puntos"
        );


        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "TODAS LAS PRUEBAS TERMINARON CORRECTAMENTE"
        );

        System.out.println(
                "Pruebas superadas: "
                        + pruebasCorrectas
        );

        System.out.println(
                "======================================"
        );
    }


    private static boolean estaOrdenado(
            List<Cliente> clientes) {

        for (int i = 1;
             i < clientes.size();
             i++) {

            Cliente anterior =
                    clientes.get(
                            i - 1
                    );


            Cliente actual =
                    clientes.get(
                            i
                    );


            if (anterior.getPuntos()
                    > actual.getPuntos()) {

                return false;
            }
        }


        return true;
    }


    private static void verificar(
            boolean condicion,
            String descripcion) {

        if (!condicion) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }
}