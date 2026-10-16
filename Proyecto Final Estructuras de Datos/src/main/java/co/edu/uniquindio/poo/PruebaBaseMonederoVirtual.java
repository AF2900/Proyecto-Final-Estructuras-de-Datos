package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

public class PruebaBaseMonederoVirtual {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                " PRUEBAS BASE MONEDERO VIRTUAL"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        // -----------------------------------------------------
        // 1. REGISTRO DE CLIENTES
        // -----------------------------------------------------

        Cliente cliente1 =
                new Cliente(
                        "C001",
                        "Federico"
                );


        Cliente cliente2 =
                new Cliente(
                        "C002",
                        "Laura"
                );


        verificar(
                sistema.registrarCliente(
                        cliente1
                ),
                "Registrar primer cliente"
        );


        verificar(
                sistema.registrarCliente(
                        cliente2
                ),
                "Registrar segundo cliente"
        );


        verificar(
                !sistema.registrarCliente(
                        new Cliente(
                                "C001",
                                "Otro cliente"
                        )
                ),
                "Rechazar identificación duplicada"
        );


        // -----------------------------------------------------
        // 2. MONEDEROS
        // -----------------------------------------------------

        verificar(
                sistema.crearMonedero(
                        "C001",
                        new Monedero(
                                "M001",
                                "Principal",
                                0
                        )
                ),
                "Crear monedero principal"
        );


        verificar(
                sistema.crearMonedero(
                        "C001",
                        new Monedero(
                                "M002",
                                "Ahorros",
                                0
                        )
                ),
                "Cliente puede tener varios monederos"
        );


        verificar(
                sistema.crearMonedero(
                        "C002",
                        new Monedero(
                                "M001",
                                "Principal",
                                0
                        )
                ),
                "Crear monedero cliente destino"
        );


        // -----------------------------------------------------
        // 3. DEPÓSITO
        // -----------------------------------------------------

        Transaccion deposito =
                sistema.depositar(
                        "C001",
                        "M001",
                        1000
                );


        verificar(
                deposito != null,
                "Depósito válido"
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
                "Depósito genera 10 puntos"
        );


        // -----------------------------------------------------
        // 4. RETIRO INVÁLIDO
        // -----------------------------------------------------

        Transaccion retiroInvalido =
                sistema.retirar(
                        "C001",
                        "M001",
                        1500
                );


        verificar(
                retiroInvalido == null,
                "No retirar más del saldo"
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 1000,
                "Retiro inválido no modifica saldo"
        );


        // -----------------------------------------------------
        // 5. RETIRO VÁLIDO
        // -----------------------------------------------------

        Transaccion retiro =
                sistema.retirar(
                        "C001",
                        "M001",
                        200
                );


        verificar(
                retiro != null,
                "Retiro válido"
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 800,
                "Saldo después del retiro"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 14,
                "Retiro genera puntos"
        );


        // -----------------------------------------------------
        // 6. TRANSFERENCIA
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
                "Transferencia válida"
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 500,
                "Saldo origen después de transferencia"
        );


        verificar(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ) == 300,
                "Saldo destino después de transferencia"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 23,
                "Transferencia genera puntos"
        );


        // -----------------------------------------------------
        // 7. HISTORIAL
        // -----------------------------------------------------

        verificar(
                sistema.obtenerHistorial(
                        "C001"
                ).size() == 3,
                "Historial del cliente origen"
        );


        verificar(
                sistema.obtenerHistorial(
                        "C002"
                ).size() == 1,
                "Historial del cliente destino"
        );


        // -----------------------------------------------------
        // 8. RANGO
        // -----------------------------------------------------

        verificar(
                sistema.consultarRango(
                        "C001"
                ) == RangoCliente.BRONCE,
                "Cliente comienza en rango Bronce"
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