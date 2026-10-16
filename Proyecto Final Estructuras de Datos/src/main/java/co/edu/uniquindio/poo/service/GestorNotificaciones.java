package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.estructuras.listacircular.ListaCircular;
import co.edu.uniquindio.poo.model.Notificacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorNotificaciones {

    /*
     * Cada cliente tiene su propia
     * lista circular de notificaciones.
     */
    private final Map<String,
            ListaCircular<Notificacion>>
            notificacionesPorCliente;


    private int consecutivo;


    public GestorNotificaciones() {

        notificacionesPorCliente =
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


        if (lista == null) {
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


        lista.agregar(
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
    // CONSULTAS
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