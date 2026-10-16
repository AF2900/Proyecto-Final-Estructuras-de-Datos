package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

import java.time.LocalDateTime;

public class PruebaIntegridad {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "     PRUEBAS DE INTEGRIDAD"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        // -----------------------------------------------------
        // CLIENTES
        // -----------------------------------------------------

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


        sistema.crearMonedero(
                "C001",
                new Monedero(
                        "M001",
                        "Principal",
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


        // -----------------------------------------------------
        // TRANSACCIONES VÁLIDAS
        // -----------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                2000
        );


        sistema.retirar(
                "C001",
                "M001",
                200
        );


        sistema.transferir(
                "C001",
                "M001",
                "C002",
                "M001",
                300
        );


        sistema.realizarGasto(
                "C001",
                "M001",
                100,
                CategoriaGasto.ALIMENTACION
        );


        verificar(
                sistema.verificarIntegridadCliente(
                        "C001"
                ),
                "Historial válido de C001"
        );


        verificar(
                sistema.verificarIntegridadCliente(
                        "C002"
                ),
                "Historial válido de C002"
        );


        verificar(
                sistema.verificarIntegridadSistema(),
                "Todo el sistema tiene integridad"
        );


        verificar(
                sistema.contarTransaccionesValidas(
                        "C001"
                ) == 4,
                "C001 tiene cuatro transacciones válidas"
        );


        verificar(
                sistema.buscarPrimeraTransaccionInvalida(
                        "C001"
                ) == null,
                "No existe transacción inválida inicialmente"
        );


        // -----------------------------------------------------
        // TRANSACCIÓN REVERTIDA
        // -----------------------------------------------------

        sistema.revertirUltimaTransaccion();


        verificar(
                sistema.verificarIntegridadCliente(
                        "C001"
                ),
                "Una transacción revertida sigue siendo válida"
        );


        /*
         * Revertida no significa inválida.
         * Sigue formando parte del historial
         * pero está marcada como revertida.
         */


        // -----------------------------------------------------
        // INSERTAR UNA TRANSACCIÓN INCORRECTA
        // -----------------------------------------------------

        Cliente cliente =
                sistema.buscarCliente(
                        "C001"
                );


        /*
         * Esta transacción es incorrecta
         * porque tiene un monto negativo.
         *
         * Se inserta manualmente únicamente
         * para demostrar que el algoritmo
         * recursivo puede detectar corrupción.
         */
        Transaccion invalida =
                new Transaccion(
                        "TX_INVALIDA",
                        LocalDateTime.now(),
                        TipoTransaccion.RETIRO,
                        -500,
                        "C001",
                        "M001",
                        null,
                        null,
                        10
                );


        cliente.registrarTransaccion(
                invalida
        );


        verificar(
                !sistema.verificarIntegridadCliente(
                        "C001"
                ),
                "Algoritmo detecta monto negativo"
        );


        verificar(
                !sistema.verificarIntegridadSistema(),
                "Sistema completo queda marcado como inválido"
        );


        verificar(
                sistema.contarTransaccionesValidas(
                        "C001"
                ) == 4,
                "Conteo recursivo encuentra cuatro válidas"
        );


        verificar(
                sistema.buscarPrimeraTransaccionInvalida(
                        "C001"
                ) == invalida,
                "Se encuentra la transacción inválida"
        );


        // -----------------------------------------------------
        // MOSTRAR RESULTADO
        // -----------------------------------------------------

        Transaccion encontrada =
                sistema
                        .buscarPrimeraTransaccionInvalida(
                                "C001"
                        );


        System.out.println(
                "\nTransacción inválida encontrada:"
        );


        System.out.println(
                encontrada
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