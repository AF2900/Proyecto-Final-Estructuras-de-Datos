package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.TipoTransaccion;

import java.time.LocalDateTime;

public class Transaccion {

    private String codigo;

    private LocalDateTime fecha;

    private TipoTransaccion tipo;

    private double monto;

    private String identificacionOrigen;

    private String monederoOrigen;

    private String identificacionDestino;

    private String monederoDestino;

    private int puntosGenerados;

    private boolean revertida;


    public Transaccion(
            String codigo,
            LocalDateTime fecha,
            TipoTransaccion tipo,
            double monto,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            int puntosGenerados) {

        this.codigo = codigo;

        this.fecha = fecha;

        this.tipo = tipo;

        this.monto = monto;

        this.identificacionOrigen =
                identificacionOrigen;

        this.monederoOrigen =
                monederoOrigen;

        this.identificacionDestino =
                identificacionDestino;

        this.monederoDestino =
                monederoDestino;

        this.puntosGenerados =
                puntosGenerados;

        this.revertida = false;
    }


    public String getCodigo() {
        return codigo;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }


    public TipoTransaccion getTipo() {
        return tipo;
    }


    public double getMonto() {
        return monto;
    }


    public String getIdentificacionOrigen() {
        return identificacionOrigen;
    }


    public String getMonederoOrigen() {
        return monederoOrigen;
    }


    public String getIdentificacionDestino() {
        return identificacionDestino;
    }


    public String getMonederoDestino() {
        return monederoDestino;
    }


    public int getPuntosGenerados() {
        return puntosGenerados;
    }


    public boolean isRevertida() {
        return revertida;
    }


    public void marcarRevertida() {

        this.revertida = true;
    }


    @Override
    public String toString() {

        return "Transaccion{" +
                "codigo='" + codigo + '\'' +
                ", fecha=" + fecha +
                ", tipo=" + tipo +
                ", monto=" + monto +
                ", origen='" +
                identificacionOrigen + '\'' +
                ", monederoOrigen='" +
                monederoOrigen + '\'' +
                ", destino='" +
                identificacionDestino + '\'' +
                ", monederoDestino='" +
                monederoDestino + '\'' +
                ", puntosGenerados=" +
                puntosGenerados +
                ", revertida=" +
                revertida +
                '}';
    }
}