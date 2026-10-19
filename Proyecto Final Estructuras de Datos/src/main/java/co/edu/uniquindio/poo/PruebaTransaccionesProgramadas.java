package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;

import java.time.LocalDateTime;
import java.util.List;

public class PruebaTransaccionesProgramadas {

    private static int pruebasCorrectas = 0;

    private static final double TOLERANCIA = 0.0001;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                " PRUEBAS TRANSACCIONES PROGRAMADAS"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


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


        LocalDateTime fechaBase =
                LocalDateTime.of(
                        2026,
                        10,
                        16,
                        12,
                        0
                );


        // -----------------------------------------------------
        // PROGRAMAR
        // -----------------------------------------------------

        verificar(
                sistema.programarTransferencia(
                        "TP002",
                        "C001",
                        "M001",
                        "C002",
                        "M001",
                        300,
                        fechaBase.plusHours(2),
                        0
                ),
                "Programar transferencia"
        );


        verificar(
                sistema.programarDeposito(
                        "TP001",
                        "C001",
                        "M001",
                        1000,
                        fechaBase.plusHours(1),
                        0
                ),
                "Programar depósito"
        );


        verificar(
                sistema.cantidadProgramadas()
                        == 2,
                "Dos transacciones programadas"
        );


        TransaccionProgramada siguiente =
                sistema
                        .verSiguienteProgramada();


        verificar(
                siguiente != null &&
                        siguiente
                                .getCodigo()
                                .equals("TP001"),
                "PriorityQueue deja primero la fecha más cercana"
        );


        List<TransaccionProgramada> orden =
                sistema
                        .obtenerProgramadasEnOrden();


        verificar(
                orden.get(0)
                        .getCodigo()
                        .equals("TP001"),
                "Consulta ordenada comienza con TP001"
        );


        verificar(
                orden.get(1)
                        .getCodigo()
                        .equals("TP002"),
                "TP002 queda después de TP001"
        );


        // -----------------------------------------------------
        // AÚN NO HA LLEGADO LA FECHA
        // -----------------------------------------------------

        List<Transaccion> realizadas =
                sistema
                        .procesarTransaccionesProgramadas(
                                fechaBase
                                        .plusMinutes(30)
                        );


        verificar(
                realizadas.isEmpty(),
                "No se ejecutan operaciones antes de su fecha"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                0,
                "Saldo permanece sin cambios"
        );


        // -----------------------------------------------------
        // EJECUTAR DEPÓSITO
        // -----------------------------------------------------

        realizadas =
                sistema
                        .procesarTransaccionesProgramadas(
                                fechaBase
                                        .plusHours(1)
                                        .plusMinutes(30)
                        );


        verificar(
                realizadas.size() == 1,
                "Se ejecuta únicamente el depósito vencido"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                1000,
                "Depósito programado modifica saldo"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 10,
                "Depósito programado genera puntos"
        );


        verificar(
                sistema.cantidadProgramadas()
                        == 1,
                "Depósito ejecutado sale de cola"
        );


        // -----------------------------------------------------
        // EJECUTAR TRANSFERENCIA
        // -----------------------------------------------------

        realizadas =
                sistema
                        .procesarTransaccionesProgramadas(
                                fechaBase
                                        .plusHours(2)
                                        .plusMinutes(30)
                        );


        verificar(
                realizadas.size() == 1,
                "Se ejecuta transferencia programada"
        );


        Transaccion transferencia =
                realizadas.get(0);


        verificarDouble(
                transferencia.getComision(),
                3,
                "Transferencia programada cobra 1% de comisión"
        );


        /*
         * 1000 - 300 - 3 = 697
         */
        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                697,
                "Transferencia programada descuenta comisión"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ),
                300,
                "Destino recibe monto programado"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 19,
                "Transferencia programada genera puntos"
        );


        verificar(
                sistema.programadasVacia(),
                "Cola queda vacía"
        );


        // -----------------------------------------------------
        // TRANSACCIÓN RECURRENTE
        // -----------------------------------------------------

        verificar(
                sistema.programarDeposito(
                        "TP003",
                        "C002",
                        "M001",
                        200,
                        fechaBase.plusHours(3),
                        2
                ),
                "Programar depósito recurrente"
        );


        realizadas =
                sistema
                        .procesarTransaccionesProgramadas(
                                fechaBase.plusHours(3)
                        );


        verificar(
                realizadas.size() == 1,
                "Primera ejecución recurrente realizada"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ),
                500,
                "Depósito recurrente aumenta saldo"
        );


        verificar(
                sistema.cantidadProgramadas()
                        == 1,
                "Operación recurrente vuelve a cola"
        );


        TransaccionProgramada recurrente =
                sistema
                        .verSiguienteProgramada();


        verificar(
                recurrente != null &&
                        recurrente
                                .getFechaEjecucion()
                                .equals(
                                        fechaBase
                                                .plusHours(3)
                                                .plusDays(2)
                                ),
                "Fecha recurrente avanza dos días"
        );


        verificar(
                !sistema.programarDeposito(
                        "TP003",
                        "C001",
                        "M001",
                        100,
                        fechaBase.plusDays(5),
                        0
                ),
                "Código programado no puede repetirse"
        );


        verificar(
                sistema.cancelarProgramada(
                        "TP003"
                ),
                "Cancelar transacción programada"
        );


        verificar(
                sistema.programadasVacia(),
                "Cola vacía después de cancelar"
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


    private static void verificarDouble(
            Double actual,
            double esperado,
            String descripcion) {

        if (actual == null ||
                Math.abs(
                        actual - esperado
                ) > TOLERANCIA) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
                            + " - esperado: "
                            + esperado
                            + ", actual: "
                            + actual
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }


    private static void verificarDouble(
            double actual,
            double esperado,
            String descripcion) {

        if (Math.abs(
                actual - esperado
        ) > TOLERANCIA) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
                            + " - esperado: "
                            + esperado
                            + ", actual: "
                            + actual
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }
}