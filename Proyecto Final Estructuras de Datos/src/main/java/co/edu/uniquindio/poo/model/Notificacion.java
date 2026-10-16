package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.TipoNotificacion;

import java.time.LocalDateTime;

public class Notificacion {

    private String codigo;

    private String identificacionCliente;

    private TipoNotificacion tipo;

    private String mensaje;

    private LocalDateTime fecha;

    private boolean leida;


    public Notificacion(
            String codigo,
            String identificacionCliente,
            TipoNotificacion tipo,
            String mensaje,
            LocalDateTime fecha) {

        this.codigo =
                codigo;

        this.identificacionCliente =
                identificacionCliente;

        this.tipo =
                tipo;

        this.mensaje =
                mensaje;

        this.fecha =
                fecha;

        this.leida =
                false;
    }


    public String getCodigo() {
        return codigo;
    }


    public String getIdentificacionCliente() {
        return identificacionCliente;
    }


    public TipoNotificacion getTipo() {
        return tipo;
    }


    public String getMensaje() {
        return mensaje;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }


    public boolean isLeida() {
        return leida;
    }


    public void marcarComoLeida() {

        leida = true;
    }


    @Override
    public String toString() {

        return "Notificacion{" +
                "codigo='" + codigo + '\'' +
                ", tipo=" + tipo +
                ", mensaje='" + mensaje + '\'' +
                ", fecha=" + fecha +
                ", leida=" + leida +
                '}';
    }
}