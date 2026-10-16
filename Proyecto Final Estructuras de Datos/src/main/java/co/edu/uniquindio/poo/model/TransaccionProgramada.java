package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.TipoTransaccion;

import java.time.LocalDateTime;

public class TransaccionProgramada
        implements Comparable<TransaccionProgramada> {

    private String codigo;

    private TipoTransaccion tipo;

    private String identificacionOrigen;

    private String monederoOrigen;

    private String identificacionDestino;

    private String monederoDestino;

    private double monto;

    private LocalDateTime fechaEjecucion;

    /*
     * Si frecuenciaDias es 0,
     * la transacción se ejecuta una sola vez.
     *
     * Si es mayor que 0,
     * la transacción es recurrente.
     */
    private int frecuenciaDias;


    public TransaccionProgramada(
            String codigo,
            TipoTransaccion tipo,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias) {

        this.codigo =
                codigo;

        this.tipo =
                tipo;

        this.identificacionOrigen =
                identificacionOrigen;

        this.monederoOrigen =
                monederoOrigen;

        this.identificacionDestino =
                identificacionDestino;

        this.monederoDestino =
                monederoDestino;

        this.monto =
                monto;

        this.fechaEjecucion =
                fechaEjecucion;

        this.frecuenciaDias =
                frecuenciaDias;
    }


    public String getCodigo() {
        return codigo;
    }


    public TipoTransaccion getTipo() {
        return tipo;
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


    public double getMonto() {
        return monto;
    }


    public LocalDateTime getFechaEjecucion() {
        return fechaEjecucion;
    }


    public int getFrecuenciaDias() {
        return frecuenciaDias;
    }


    public boolean esRecurrente() {

        return frecuenciaDias > 0;
    }


    public void programarSiguienteEjecucion() {

        if (!esRecurrente()) {
            return;
        }

        fechaEjecucion =
                fechaEjecucion.plusDays(
                        frecuenciaDias
                );
    }


    /*
     * Orden natural:
     *
     * 1. Fecha de ejecución más cercana.
     * 2. En caso de empate, código.
     */
    @Override
    public int compareTo(
            TransaccionProgramada otra) {

        int comparacionFecha =
                this.fechaEjecucion
                        .compareTo(
                                otra.fechaEjecucion
                        );


        if (comparacionFecha != 0) {

            return comparacionFecha;
        }


        return this.codigo
                .compareTo(
                        otra.codigo
                );
    }


    @Override
    public String toString() {

        return "TransaccionProgramada{" +
                "codigo='" + codigo + '\'' +
                ", tipo=" + tipo +
                ", monto=" + monto +
                ", fechaEjecucion=" +
                fechaEjecucion +
                ", frecuenciaDias=" +
                frecuenciaDias +
                '}';
    }
}