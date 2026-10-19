package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

public class PruebaReversiones {

    private static int pruebasCorrectas = 0;

    private static final double TOLERANCIA = 0.0001;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "       PRUEBAS DE REVERSIONES"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        Cliente federico =
                new Cliente(
                        "C001",
                        "Federico"
                );


        Cliente laura =
                new Cliente(
                        "C002",
                        "Laura"
                );


        sistema.registrarCliente(
                federico
        );


        sistema.registrarCliente(
                laura
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
        // DEPÓSITO
        // -----------------------------------------------------

        Transaccion deposito =
                sistema.depositar(
                        "C001",
                        "M001",
                        1000
                );


        verificar(
                deposito != null,
                "Depósito registrado"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                1000,
                "Saldo después del depósito"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 10,
                "Puntos después del depósito"
        );


        verificar(
                sistema
                        .cantidadTransaccionesReversibles()
                        == 1,
                "Depósito agregado a la pila"
        );


        // -----------------------------------------------------
        // RETIRO
        // -----------------------------------------------------

        Transaccion retiro =
                sistema.retirar(
                        "C001",
                        "M001",
                        100
                );


        verificar(
                retiro != null,
                "Retiro registrado"
        );


        verificarDouble(
                retiro.getComision(),
                1,
                "Retiro cobra comisión de 1"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                899,
                "Retiro descuenta monto más comisión"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 12,
                "Retiro genera dos puntos"
        );


        verificar(
                sistema
                        .cantidadTransaccionesReversibles()
                        == 2,
                "Retiro agregado a la pila"
        );


        // -----------------------------------------------------
        // REVERTIR RETIRO
        // -----------------------------------------------------

        Transaccion retiroRevertido =
                sistema
                        .revertirUltimaTransaccion();


        verificar(
                retiroRevertido == retiro,
                "Se revierte retiro"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                1000,
                "Reversión devuelve monto y comisión del retiro"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 10,
                "Reversión retira puntos del retiro"
        );


        // -----------------------------------------------------
        // TRANSFERENCIA
        // -----------------------------------------------------

        Transaccion transferencia =
                sistema.transferir(
                        "C001",
                        "M001",
                        "C002",
                        "M001",
                        300
                );


        verificar(
                transferencia != null,
                "Transferencia registrada"
        );


        verificarDouble(
                transferencia.getComision(),
                3,
                "Transferencia cobra comisión de 3"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                697,
                "Origen paga transferencia y comisión"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ),
                300,
                "Destino recibe transferencia"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 19,
                "Puntos acumulados antes de revertir"
        );


        verificar(
                sistema
                        .cantidadTransaccionesReversibles()
                        == 2,
                "Depósito y transferencia quedan en pila"
        );


        // -----------------------------------------------------
        // REVERTIR TRANSFERENCIA
        // -----------------------------------------------------

        verificar(
                sistema
                        .verUltimaTransaccionReversible()
                        == transferencia,
                "Transferencia está en el tope"
        );


        Transaccion transferenciaRevertida =
                sistema
                        .revertirUltimaTransaccion();


        verificar(
                transferenciaRevertida
                        == transferencia,
                "Se revierte transferencia"
        );


        verificar(
                transferencia.isRevertida(),
                "Transferencia queda marcada como revertida"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                1000,
                "Reversión devuelve monto y comisión al origen"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ),
                0,
                "Reversión retira monto del destino"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 10,
                "Puntos de transferencia eliminados"
        );


        // -----------------------------------------------------
        // REVERTIR DEPÓSITO
        // -----------------------------------------------------

        Transaccion depositoRevertido =
                sistema
                        .revertirUltimaTransaccion();


        verificar(
                depositoRevertido == deposito,
                "Se revierte depósito"
        );


        verificar(
                deposito.isRevertida(),
                "Depósito queda marcado como revertido"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                0,
                "Saldo vuelve al estado inicial"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 0,
                "Puntos vuelven al estado inicial"
        );


        verificar(
                sistema.pilaReversionesVacia(),
                "Pila queda vacía"
        );


        verificar(
                sistema
                        .revertirUltimaTransaccion()
                        == null,
                "No se puede revertir una pila vacía"
        );


        // -----------------------------------------------------
        // HISTORIAL
        // -----------------------------------------------------

        verificar(
                sistema.obtenerHistorial(
                        "C001"
                ).size() == 3,
                "Historial conserva las tres transacciones"
        );


        verificar(
                sistema.obtenerHistorial(
                                "C001"
                        ).get(0)
                        .isRevertida(),
                "Depósito aparece revertido"
        );


        verificar(
                sistema.obtenerHistorial(
                                "C001"
                        ).get(1)
                        .isRevertida(),
                "Retiro aparece revertido"
        );


        verificar(
                sistema.obtenerHistorial(
                                "C001"
                        ).get(2)
                        .isRevertida(),
                "Transferencia aparece revertida"
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