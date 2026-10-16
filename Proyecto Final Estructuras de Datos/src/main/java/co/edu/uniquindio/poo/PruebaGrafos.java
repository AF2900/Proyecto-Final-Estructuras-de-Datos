package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;

import java.util.List;

public class PruebaGrafos {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "          PRUEBAS GRAFOS"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        // -----------------------------------------------------
        // CLIENTES
        // ---------------------------------------------------------

        sistema.registrarCliente(
                new Cliente(
                        "C001",
                        "Federico"
                )
        );


        sistema.registrarCliente(
                new Cliente(
                        "C002",
                        "Laura"
                )
        );


        sistema.registrarCliente(
                new Cliente(
                        "C003",
                        "Johan"
                )
        );


        // -----------------------------------------------------
        // MONEDEROS
        // ---------------------------------------------------------

        sistema.crearMonedero(
                "C001",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        sistema.crearMonedero(
                "C001",
                new Monedero(
                        "M002",
                        "Ahorros",
                        0
                )
        );


        sistema.crearMonedero(
                "C002",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        sistema.crearMonedero(
                "C003",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        // -----------------------------------------------------
        // SALDO INICIAL
        // ---------------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                2000
        );


        // -----------------------------------------------------
        // TRANSFERENCIAS
        // ---------------------------------------------------------

        sistema.transferir(
                "C001",
                "M001",
                "C002",
                "M001",
                500
        );


        sistema.transferir(
                "C001",
                "M001",
                "C002",
                "M001",
                200
        );


        sistema.transferir(
                "C001",
                "M001",
                "C003",
                "M001",
                300
        );


        verificar(
                sistema.obtenerMontoTransferido(
                        "C001",
                        "C002"
                ) == 700,
                "Grafo acumula 700 transferidos a C002"
        );


        verificar(
                sistema.obtenerCantidadTransferencias(
                        "C001",
                        "C002"
                ) == 2,
                "Grafo registra dos transferencias a C002"
        );


        verificar(
                sistema.obtenerMontoTransferido(
                        "C001",
                        "C003"
                ) == 300,
                "Grafo registra transferencia a C003"
        );


        verificar(
                sistema.obtenerTotalTransferidoPorCliente(
                        "C001"
                ) == 1000,
                "C001 ha transferido 1000 a otros clientes"
        );


        verificar(
                sistema.obtenerPrincipalDestinoCliente(
                        "C001"
                ).equals(
                        "C002"
                ),
                "C002 es el principal destino de C001"
        );


        // -----------------------------------------------------
        // DIRECCIÓN DEL GRAFO
        // ---------------------------------------------------------

        verificar(
                sistema.obtenerMontoTransferido(
                        "C002",
                        "C001"
                ) == 0,
                "El grafo es dirigido"
        );


        // -----------------------------------------------------
        // BFS
        // ---------------------------------------------------------

        List<String> recorrido =
                sistema.recorridoClientes(
                        "C001"
                );


        verificar(
                recorrido.contains(
                        "C001"
                ),
                "BFS contiene el origen"
        );


        verificar(
                recorrido.contains(
                        "C002"
                ) &&
                        recorrido.contains(
                                "C003"
                        ),
                "BFS alcanza los clientes relacionados"
        );


        verificar(
                sistema.existeRelacionEntreClientes(
                        "C001",
                        "C003"
                ),
                "Existe camino desde C001 hasta C003"
        );


        verificar(
                !sistema.existeRelacionEntreClientes(
                        "C003",
                        "C001"
                ),
                "No existe camino inverso automáticamente"
        );


        // -----------------------------------------------------
        // GRAFO DE MONEDEROS
        // ---------------------------------------------------------

        sistema.transferir(
                "C001",
                "M001",
                "C001",
                "M002",
                100
        );


        verificar(
                sistema.obtenerMontoEntreMonederos(
                        "C001",
                        "M001",
                        "C001",
                        "M002"
                ) == 100,
                "Grafo relaciona monederos del mismo cliente"
        );


        /*
         * Una transferencia entre monederos
         * propios no debe aumentar el grafo
         * de transferencias entre clientes.
         */
        verificar(
                sistema.obtenerTotalTransferidoPorCliente(
                        "C001"
                ) == 1000,
                "Transferencia interna no altera grafo de clientes"
        );


        // -----------------------------------------------------
        // REVERTIR TRANSFERENCIA INTERNA
        // ---------------------------------------------------------

        sistema.revertirUltimaTransaccion();


        verificar(
                sistema.obtenerMontoEntreMonederos(
                        "C001",
                        "M001",
                        "C001",
                        "M002"
                ) == 0,
                "Reversión elimina relación entre monederos"
        );


        // -----------------------------------------------------
        // REVERTIR TRANSFERENCIA A C003
        // ---------------------------------------------------------

        sistema.revertirUltimaTransaccion();


        verificar(
                sistema.obtenerMontoTransferido(
                        "C001",
                        "C003"
                ) == 0,
                "Reversión actualiza grafo de clientes"
        );


        verificar(
                sistema.obtenerTotalTransferidoPorCliente(
                        "C001"
                ) == 700,
                "Total transferido se recalcula tras reversión"
        );


        verificar(
                sistema.obtenerCantidadTransferencias(
                        "C001",
                        "C002"
                ) == 2,
                "Transferencias a C002 permanecen intactas"
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