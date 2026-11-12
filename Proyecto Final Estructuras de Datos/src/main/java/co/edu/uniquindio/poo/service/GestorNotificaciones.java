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

    private Map<
            String,
            ListaCircular<Notificacion>
            > notificacionesPorCliente;

    private Map<
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

    public void registrarCliente(
            String identificacionCliente
    ) {

        if (
                identificacionCliente == null
                        ||
                        identificacionCliente.isBlank()
        ) {
            return;
        }

        if (
                !notificacionesPorCliente
                        .containsKey(
                                identificacionCliente
                        )
        ) {

            notificacionesPorCliente.put(
                    identificacionCliente,
                    new ListaCircular<>()
            );
        }

        if (
                !pendientesPorCliente
                        .containsKey(
                                identificacionCliente
                        )
        ) {

            pendientesPorCliente.put(
                    identificacionCliente,
                    new Cola<>(
                            Integer.MAX_VALUE
                    )
            );
        }
    }

    public Notificacion enviarNotificacion(
            String identificacionCliente,
            TipoNotificacion tipo,
            String mensaje
    ) {

        if (
                identificacionCliente == null
                        ||
                        identificacionCliente.isBlank()
                        ||
                        tipo == null
                        ||
                        mensaje == null
                        ||
                        mensaje.isBlank()
        ) {
            return null;
        }

        registrarCliente(
                identificacionCliente
        );

        String codigo =
                String.format(
                        "NT%05d",
                        consecutivo
                );

        consecutivo++;

        Notificacion notificacion =
                new Notificacion(
                        codigo,
                        identificacionCliente,
                        tipo,
                        mensaje,
                        LocalDateTime.now()
                );

        notificacionesPorCliente
                .get(identificacionCliente)
                .agregar(notificacion);

        pendientesPorCliente
                .get(identificacionCliente)
                .encolar(notificacion);

        return notificacion;
    }

    public Notificacion verSiguienteNotificacion(
            String identificacionCliente
    ) {

        ListaCircular<Notificacion> lista =
                notificacionesPorCliente.get(
                        identificacionCliente
                );

        if (
                lista == null
                        ||
                        lista.estaVacia()
        ) {
            return null;
        }

        return lista.siguiente();
    }

    public Notificacion verSiguientePendiente(
            String identificacionCliente
    ) {

        Cola<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );

        if (
                cola == null
                        ||
                        cola.estaVacia()
        ) {
            return null;
        }

        return cola.verPrimero();
    }

    public List<Notificacion> obtenerNotificaciones(
            String identificacionCliente
    ) {

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
            String identificacionCliente
    ) {

        ListaCircular<Notificacion> lista =
                notificacionesPorCliente.get(
                        identificacionCliente
                );

        if (lista == null) {
            return 0;
        }

        return lista.tamano();
    }

    public int cantidadNotificacionesPendientes(
            String identificacionCliente
    ) {

        Cola<Notificacion> cola =
                pendientesPorCliente.get(
                        identificacionCliente
                );

        if (cola == null) {
            return 0;
        }

        return cola.getSize();
    }

    public boolean marcarComoLeida(
            String identificacionCliente,
            String codigoNotificacion
    ) {

        Cola<Notificacion> pendientes =
                pendientesPorCliente.get(
                        identificacionCliente
                );

        if (
                pendientes == null
                        ||
                        pendientes.estaVacia()
                        ||
                        codigoNotificacion == null
        ) {
            return false;
        }

        int cantidad =
                pendientes.getSize();

        boolean encontrada =
                false;

        for (
                int i = 0;
                i < cantidad;
                i++
        ) {

            Notificacion notificacion =
                    pendientes.desencolar();

            if (
                    !encontrada
                            &&
                            codigoNotificacion.equals(
                                    notificacion.getCodigo()
                            )
            ) {

                notificacion.marcarComoLeida();

                encontrada = true;

            } else {

                pendientes.encolar(
                        notificacion
                );
            }
        }

        return encontrada;
    }
}