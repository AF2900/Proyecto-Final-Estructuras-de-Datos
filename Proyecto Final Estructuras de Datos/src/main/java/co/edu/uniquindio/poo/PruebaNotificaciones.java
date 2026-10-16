package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Notificacion;
import co.edu.uniquindio.poo.model.Transaccion;

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
        // ---------------------------------------------------------

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
                "Historial contiene una notificación"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 1,
                "Cola contiene una notificación pendiente"
        );


        verificar(
                sistema
                        .verSiguienteNotificacionPendiente(
                                "C001"
                        )
                        == general,
                "Primera pendiente es la primera recibida"
        );


        // -----------------------------------------------------
        // TRANSACCIÓN PROGRAMADA
        // ---------------------------------------------------------

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
                "Programación genera notificación"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 2,
                "Programación entra en cola de pendientes"
        );


        // -----------------------------------------------------
        // SALDO BAJO
        // ---------------------------------------------------------

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
                "Saldo bajo genera notificación"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 3,
                "Las tres notificaciones están pendientes"
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
        // LISTA CIRCULAR
        // ---------------------------------------------------------

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
                "Lista circular vuelve al primer nodo"
        );


        verificar(
                primera != segunda &&
                        segunda != tercera,
                "Lista circular avanza entre nodos"
        );


        // -----------------------------------------------------
        // MARCAR COMO LEÍDA
        // ---------------------------------------------------------

        verificar(
                !general.isLeida(),
                "Notificación inicia como no leída"
        );


        verificar(
                sistema
                        .marcarNotificacionComoLeida(
                                "C001",
                                general.getCodigo()
                        ),
                "Marcar primera notificación como leída"
        );


        verificar(
                general.isLeida(),
                "Primera notificación queda leída"
        );


        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 3,
                "Historial conserva la notificación leída"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 2,
                "Notificación leída sale de la Queue"
        );


        // -----------------------------------------------------
        // FIFO
        // ---------------------------------------------------------

        Notificacion siguientePendiente =
                sistema
                        .verSiguienteNotificacionPendiente(
                                "C001"
                        );


        verificar(
                siguientePendiente
                        == segunda,
                "Queue conserva orden FIFO"
        );


        Notificacion atendida =
                sistema
                        .atenderSiguienteNotificacion(
                                "C001"
                        );


        verificar(
                atendida == segunda,
                "Se atiende la primera pendiente"
        );


        verificar(
                atendida.isLeida(),
                "Notificación atendida queda leída"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 1,
                "Queue queda con una pendiente"
        );


        verificar(
                sistema
                        .verSiguienteNotificacionPendiente(
                                "C001"
                        )
                        == tercera,
                "Saldo bajo queda al frente de la Queue"
        );


        // -----------------------------------------------------
        // EJECUTAR TRANSACCIÓN PROGRAMADA
        // ---------------------------------------------------------

        List<Transaccion> ejecutadas =
                sistema
                        .procesarTransaccionesProgramadas(
                                fecha.plusMinutes(
                                        1
                                )
                        );


        verificar(
                ejecutadas.size()
                        == 1,
                "Se ejecuta depósito programado"
        );


        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 4,
                "Ejecución genera recordatorio automático"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 2,
                "Recordatorio entra al final de la Queue"
        );


        List<Notificacion> pendientes =
                sistema
                        .obtenerNotificacionesPendientes(
                                "C001"
                        );


        verificar(
                pendientes.get(0)
                        == tercera,
                "Primera pendiente sigue siendo saldo bajo"
        );


        verificar(
                pendientes.get(1)
                        .getTipo()
                        ==
                        TipoNotificacion
                                .TRANSACCION_PROGRAMADA,
                "Recordatorio ejecutado queda después en la cola"
        );


        // -----------------------------------------------------
        // ATENDER TODA LA COLA
        // ---------------------------------------------------------

        Notificacion pendiente1 =
                sistema
                        .atenderSiguienteNotificacion(
                                "C001"
                        );


        Notificacion pendiente2 =
                sistema
                        .atenderSiguienteNotificacion(
                                "C001"
                        );


        verificar(
                pendiente1 != null &&
                        pendiente2 != null,
                "Se atienden las dos pendientes"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 0,
                "Queue queda vacía"
        );


        verificar(
                !sistema
                        .tieneNotificacionesPendientes(
                                "C001"
                        ),
                "Cliente ya no tiene pendientes"
        );


        verificar(
                sistema
                        .atenderSiguienteNotificacion(
                                "C001"
                        )
                        == null,
                "poll sobre Queue vacía retorna null"
        );


        /*
         * Aunque la cola quedó vacía,
         * la lista circular conserva
         * las cuatro notificaciones.
         */
        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 4,
                "Historial circular conserva todas"
        );


        // -----------------------------------------------------
        // SEGUNDO CLIENTE
        // ---------------------------------------------------------

        verificar(
                sistema.cantidadNotificaciones(
                        "C002"
                ) == 0,
                "Segundo cliente no tiene historial"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C002"
                ) == 0,
                "Segundo cliente no tiene pendientes"
        );


        verificar(
                sistema
                        .verSiguienteNotificacionPendiente(
                                "C002"
                        )
                        == null,
                "Queue vacía retorna null"
        );


        // -----------------------------------------------------
        // RESULTADO
        // ---------------------------------------------------------

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