package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.estructuras.cola.Cola;
import co.edu.uniquindio.poo.estructuras.listacircular.ListaCircular;
import co.edu.uniquindio.poo.model.Notificacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorNotificaciones {

    /*
     * Cada cliente conserva todas sus
     * notificaciones en una lista circular.
     */
    private final Map<
            String,
            ListaCircular<Notificacion>
            > notificacionesPorCliente;


    /*
     * Cada cliente también posee una cola
     * con las notificaciones pendientes.
     *
     * La cola trabaja en orden FIFO:
     * la primera notificación recibida
     * será la primera en ser atendida.
     */
    private final Map<
            String,
            Cola<Notificacion>
            > pendientesPorCliente;


    private int consecutivo;


    public GestorNotificaciones() {

        notificacionesPorCliente =
                new HashMap<>();

        pendientesPorCliente =
                new HashMap<>();

        consecutivo = 1;
    }


    // ---------------------------------------------------------
    // CLIENTES
    // ---------------------------------------------------------

    public boolean registrarCliente(
            String identificacionCliente) {

        if (identificacionCliente == null ||
                identificacionCliente
                        .trim()
                        .isEmpty()) {

            return false;
        }


        if (notificacionesPorCliente
                .containsKey(
                        identificacionCliente
                )) {

            return false;
        }


        notificacionesPorCliente.put(
                identificacionCliente,
                new ListaCircular<>()
        );


        pendientesPorCliente.put(
                identificacionCliente,
                new Cola<>(
                        Integer.MAX_VALUE
                )
        );


        return true;
    }


    // ---------------------------------------------------------
    // ENVIAR
    // ---------------------------------------------------------

    public Notificacion enviarNotificacion(
            String identificacionCliente,
            TipoNotificacion tipo,
            String mensaje) {

        if (identificacionCliente == null ||
                tipo == null ||
                mensaje == null ||
                mensaje.trim().isEmpty()) {

            return null;
        }


        ListaCircular<Notificacion> lista =
                notificacionesPorCliente.get(
                        identificacionCliente
                );


        Cola<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (lista == null ||
                cola == null) {

            return null;
        }


        Notificacion notificacion =
                new Notificacion(
                        generarCodigo(),
                        identificacionCliente,
                        tipo,
                        mensaje,
                        LocalDateTime.now()
                );


        /*
         * La misma instancia queda almacenada
         * en las dos estructuras.
         *
         * Lista circular:
         * historial completo.
         *
         * Cola:
         * notificaciones pendientes.
         */
        lista.agregar(
                notificacion
        );


        cola.encolar(
                notificacion
        );


        return notificacion;
    }


    // ---------------------------------------------------------
    // RECORRIDO CIRCULAR
    // ---------------------------------------------------------

    public Notificacion
    verSiguienteNotificacion(
            String identificacionCliente) {

        ListaCircular<Notificacion> lista =
                notificacionesPorCliente.get(
                        identificacionCliente
                );


        if (lista == null) {
            return null;
        }


        return lista.siguiente();
    }


    // ---------------------------------------------------------
    // COLA DE PENDIENTES
    // ---------------------------------------------------------

    public Notificacion
    verSiguientePendiente(
            String identificacionCliente) {

        Cola<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null ||
                cola.estaVacia()) {

            return null;
        }


        /*
         * Consulta el primer elemento
         * sin eliminarlo.
         */
        return cola.verPrimero();
    }


    public Notificacion
    atenderSiguientePendiente(
            String identificacionCliente) {

        Cola<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null ||
                cola.estaVacia()) {

            return null;
        }


        /*
         * Desencolar obtiene y elimina
         * el primer elemento de la cola.
         */
        Notificacion notificacion =
                cola.desencolar();


        notificacion.marcarComoLeida();


        return notificacion;
    }


    public int cantidadPendientes(
            String identificacionCliente) {

        Cola<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null) {
            return 0;
        }


        return cola.getSize();
    }


    public boolean tienePendientes(
            String identificacionCliente) {

        return cantidadPendientes(
                identificacionCliente
        ) > 0;
    }


    public List<Notificacion>
    obtenerPendientes(
            String identificacionCliente) {

        Cola<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null) {

            return new ArrayList<>();
        }


        /*
         * Se devuelve una copia para evitar
         * modificar directamente la cola.
         */
        return cola.obtenerElementos();
    }


    // ---------------------------------------------------------
    // CONSULTAR HISTORIAL
    // ---------------------------------------------------------

    public List<Notificacion>
    obtenerNotificaciones(
            String identificacionCliente) {

        ListaCircular<Notificacion> lista =
                notificacionesPorCliente.get(
                        identificacionCliente
                );


        if (lista == null) {

            return new ArrayList<>();
        }


        return lista.obtenerTodos();
    }


    public int cantidadNotificaciones(
            String identificacionCliente) {

        ListaCircular<Notificacion> lista =
                notificacionesPorCliente.get(
                        identificacionCliente
                );


        if (lista == null) {
            return 0;
        }


        return lista.tamano();
    }


    // ---------------------------------------------------------
    // MARCAR COMO LEÍDA
    // ---------------------------------------------------------

    public boolean marcarComoLeida(
            String identificacionCliente,
            String codigoNotificacion) {

        if (codigoNotificacion == null ||
                codigoNotificacion
                        .trim()
                        .isEmpty()) {

            return false;
        }


        List<Notificacion> notificaciones =
                obtenerNotificaciones(
                        identificacionCliente
                );


        for (Notificacion notificacion :
                notificaciones) {

            if (notificacion
                    .getCodigo()
                    .equals(
                            codigoNotificacion
                    )) {


                notificacion
                        .marcarComoLeida();


                /*
                 * Como la notificación ya fue
                 * leída, deja de estar pendiente.
                 */
                Cola<Notificacion> cola =
                        pendientesPorCliente.get(
                                identificacionCliente
                        );


                if (cola != null) {

                    cola.eliminar(
                            notificacion
                    );
                }


                return true;
            }
        }


        return false;
    }


    // ---------------------------------------------------------
    // CÓDIGO
    // ---------------------------------------------------------

    private String generarCodigo() {

        String codigo =
                String.format(
                        "NT%05d",
                        consecutivo
                );


        consecutivo++;


        return codigo;
    }
}