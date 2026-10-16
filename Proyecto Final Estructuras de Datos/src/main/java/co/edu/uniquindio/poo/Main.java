package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

public class Main {

    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "       MONEDERO VIRTUAL"
        );

        System.out.println(
                "======================================"
        );


        // -----------------------------------------------------
        // CREAR SISTEMA
        // -----------------------------------------------------

        MonederoVirtual sistema =
                new MonederoVirtual();


        // -----------------------------------------------------
        // CREAR CLIENTES
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
        // CREAR MONEDEROS
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


        // -----------------------------------------------------
        // DEPÓSITO
        // -----------------------------------------------------

        System.out.println(
                "\n=== DEPÓSITO ==="
        );


        Transaccion deposito =
                sistema.depositar(
                        "C001",
                        "M001",
                        1000
                );


        System.out.println(
                deposito
        );


        System.out.println(
                "Saldo Federico: "
                        + sistema.consultarSaldo(
                        "C001",
                        "M001"
                )
        );


        System.out.println(
                "Puntos Federico: "
                        + sistema.consultarPuntos(
                        "C001"
                )
        );


        // -----------------------------------------------------
        // RETIRO
        // -----------------------------------------------------

        System.out.println(
                "\n=== RETIRO ==="
        );


        Transaccion retiro =
                sistema.retirar(
                        "C001",
                        "M001",
                        200
                );


        System.out.println(
                retiro
        );


        System.out.println(
                "Saldo Federico: "
                        + sistema.consultarSaldo(
                        "C001",
                        "M001"
                )
        );


        System.out.println(
                "Puntos Federico: "
                        + sistema.consultarPuntos(
                        "C001"
                )
        );


        // -----------------------------------------------------
        // TRANSFERENCIA
        // -----------------------------------------------------

        System.out.println(
                "\n=== TRANSFERENCIA ==="
        );


        Transaccion transferencia =
                sistema.transferir(
                        "C001",
                        "M001",
                        "C002",
                        "M001",
                        300
                );


        System.out.println(
                transferencia
        );


        System.out.println(
                "Saldo Federico: "
                        + sistema.consultarSaldo(
                        "C001",
                        "M001"
                )
        );


        System.out.println(
                "Saldo Laura: "
                        + sistema.consultarSaldo(
                        "C002",
                        "M001"
                )
        );


        // -----------------------------------------------------
        // PUNTOS
        // -----------------------------------------------------

        System.out.println(
                "\n=== SISTEMA DE PUNTOS ==="
        );


        System.out.println(
                "Puntos Federico: "
                        + sistema.consultarPuntos(
                        "C001"
                )
        );


        System.out.println(
                "Rango Federico: "
                        + sistema.consultarRango(
                        "C001"
                )
        );


        // -----------------------------------------------------
        // HISTORIAL
        // -----------------------------------------------------

        System.out.println(
                "\n=== HISTORIAL FEDERICO ==="
        );


        for (Transaccion transaccion :
                sistema.obtenerHistorial(
                        "C001"
                )) {

            System.out.println(
                    transaccion
            );
        }


        System.out.println(
                "\n=== HISTORIAL LAURA ==="
        );


        for (Transaccion transaccion :
                sistema.obtenerHistorial(
                        "C002"
                )) {

            System.out.println(
                    transaccion
            );
        }


        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "   DEMOSTRACIÓN FINALIZADA"
        );

        System.out.println(
                "======================================"
        );
    }
}