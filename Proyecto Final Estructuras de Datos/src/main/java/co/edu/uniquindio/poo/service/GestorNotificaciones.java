package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.estructuras.listacircular.ListaCircular;
import co.edu.uniquindio.poo.model.Notificacion;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GestorNotificaciones {

    /*
     * Cada cliente conserva todas sus
     * notificaciones en una lista circular.
     */
    private final Map<String,
            ListaCircular<Notificacion>>
            notificacionesPorCliente;


    /*
     * Cada cliente también posee una cola
     * con las notificaciones pendientes.
     *
     * La cola trabaja en orden FIFO:
     * la primera notificación recibida
     * será la primera en ser atendida.
     */
    private final Map<String,
            Queue<Notificacion>>
            pendientesPorCliente;


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
                new ArrayDeque<>()
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


        Queue<Notificacion> cola =
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
         * Queue:
         * notificaciones pendientes.
         */
        lista.agregar(
                notificacion
        );


        cola.offer(
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

        Queue<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null) {
            return null;
        }


        /*
         * peek() consulta el primer elemento
         * sin eliminarlo.
         */
        return cola.peek();
    }


    public Notificacion
    atenderSiguientePendiente(
            String identificacionCliente) {

        Queue<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null) {
            return null;
        }


        /*
         * poll() obtiene y elimina
         * el primer elemento de la cola.
         */
        Notificacion notificacion =
                cola.poll();


        if (notificacion == null) {
            return null;
        }


        notificacion.marcarComoLeida();


        return notificacion;
    }


    public int cantidadPendientes(
            String identificacionCliente) {

        Queue<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null) {
            return 0;
        }


        return cola.size();
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

        Queue<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );


        if (cola == null) {

            return new ArrayList<>();
        }


        /*
         * Se devuelve una copia para evitar
         * que desde afuera se modifique
         * directamente la cola real.
         */
        return new ArrayList<>(
                cola
        );
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
                Queue<Notificacion> cola =
                        pendientesPorCliente.get(
                                identificacionCliente
                        );


                if (cola != null) {

                    cola.remove(
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