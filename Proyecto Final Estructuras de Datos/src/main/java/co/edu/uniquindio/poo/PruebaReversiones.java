package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

public class PruebaReversiones {

    private static int pruebasCorrectas = 0;


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


        // -----------------------------------------------------
        // CLIENTES
        // -----------------------------------------------------

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


        // -----------------------------------------------------
        // MONEDEROS
        // -----------------------------------------------------

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


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 1000,
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


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 700,
                "Saldo Federico después de transferencia"
        );


        verificar(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ) == 300,
                "Saldo Laura después de transferencia"
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
                "Dos transacciones en la pila"
        );


        // -----------------------------------------------------
        // VER TOPE
        // -----------------------------------------------------

        verificar(
                sistema
                        .verUltimaTransaccionReversible()
                        == transferencia,
                "Transferencia está en el tope"
        );


        // -----------------------------------------------------
        // REVERTIR TRANSFERENCIA
        // -----------------------------------------------------

        Transaccion revertida1 =
                sistema
                        .revertirUltimaTransaccion();


        verificar(
                revertida1 == transferencia,
                "Se revierte la última transacción"
        );


        verificar(
                transferencia.isRevertida(),
                "Transferencia queda marcada como revertida"
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 1000,
                "Dinero regresa al monedero origen"
        );


        verificar(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ) == 0,
                "Dinero se retira del destino"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 10,
                "Puntos de transferencia eliminados"
        );


        verificar(
                sistema
                        .cantidadTransaccionesReversibles()
                        == 1,
                "Transferencia retirada de la pila"
        );


        // -----------------------------------------------------
        // REVERTIR DEPÓSITO
        // -----------------------------------------------------

        Transaccion revertida2 =
                sistema
                        .revertirUltimaTransaccion();


        verificar(
                revertida2 == deposito,
                "Se revierte el depósito"
        );


        verificar(
                deposito.isRevertida(),
                "Depósito queda marcado como revertido"
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 0,
                "Saldo vuelve al estado inicial"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 0,
                "Puntos vuelven al estado inicial"
        );


        // -----------------------------------------------------
        // PILA VACÍA
        // -----------------------------------------------------

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
                ).size() == 2,
                "Historial conserva las transacciones"
        );


        verificar(
                sistema.obtenerHistorial(
                                "C001"
                        ).get(0)
                        .isRevertida(),
                "Historial muestra depósito revertido"
        );


        verificar(
                sistema.obtenerHistorial(
                                "C001"
                        ).get(1)
                        .isRevertida(),
                "Historial muestra transferencia revertida"
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