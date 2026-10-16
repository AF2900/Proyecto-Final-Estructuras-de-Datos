package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Notificacion;

import java.time.LocalDateTime;
import java.util.List;

public class PruebaNotificaciones {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "      PRUEBAS NOTIFICACIONES"
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


        // -----------------------------------------------------
        // NOTIFICACIÓN GENERAL
        // -----------------------------------------------------

        Notificacion general =
                sistema.enviarNotificacion(
                        "C001",
                        TipoNotificacion.GENERAL,
                        "Bienvenido al monedero virtual"
                );


        verificar(
                general != null,
                "Crear notificación general"
        );


        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 1,
                "Cliente tiene una notificación"
        );


        // -----------------------------------------------------
        // TRANSACCIÓN PROGRAMADA
        // -----------------------------------------------------

        LocalDateTime fecha =
                LocalDateTime.of(
                        2026,
                        10,
                        20,
                        10,
                        0
                );


        verificar(
                sistema.programarDeposito(
                        "TP001",
                        "C001",
                        "M001",
                        1000,
                        fecha,
                        0
                ),
                "Crear transacción programada"
        );


        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 2,
                "Programación genera notificación automática"
        );


        // -----------------------------------------------------
        // SALDO BAJO
        // -----------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                500
        );


        sistema.retirar(
                "C001",
                "M001",
                450
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 50,
                "Saldo queda en 50"
        );


        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 3,
                "Saldo bajo genera alerta automática"
        );


        List<Notificacion> notificaciones =
                sistema.obtenerNotificaciones(
                        "C001"
                );


        verificar(
                notificaciones
                        .get(2)
                        .getTipo()
                        == TipoNotificacion.SALDO_BAJO,
                "Última notificación es saldo bajo"
        );


        // -----------------------------------------------------
        // COMPROBAR CIRCULARIDAD
        // -----------------------------------------------------

        Notificacion primera =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        Notificacion segunda =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        Notificacion tercera =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        Notificacion vuelvePrimera =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        verificar(
                primera == vuelvePrimera,
                "Lista vuelve al primer nodo"
        );


        verificar(
                primera != segunda &&
                        segunda != tercera,
                "Recorrido avanza entre notificaciones"
        );


        // -----------------------------------------------------
        // MARCAR COMO LEÍDA
        // -----------------------------------------------------

        verificar(
                !primera.isLeida(),
                "Notificación inicia como no leída"
        );


        verificar(
                sistema
                        .marcarNotificacionComoLeida(
                                "C001",
                                primera.getCodigo()
                        ),
                "Marcar notificación como leída"
        );


        verificar(
                primera.isLeida(),
                "Notificación queda leída"
        );


        // -----------------------------------------------------
        // CLIENTE SIN NOTIFICACIONES
        // -----------------------------------------------------

        verificar(
                sistema.cantidadNotificaciones(
                        "C002"
                ) == 0,
                "Segundo cliente inicia sin notificaciones"
        );


        verificar(
                sistema.verSiguienteNotificacion(
                        "C002"
                ) == null,
                "Lista circular vacía retorna null"
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