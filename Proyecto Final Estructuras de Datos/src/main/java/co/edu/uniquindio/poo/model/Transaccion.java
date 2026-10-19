package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
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

    /*
     * Comisión realmente cobrada
     * al ejecutar la operación.
     *
     * Depósito: 0
     * Gasto: 0
     * Retiro: depende del beneficio
     * Transferencia: depende del beneficio
     */
    private double comision;

    private boolean revertida;

    private CategoriaGasto categoriaGasto;

    private CategoriaGasto categoriaAnterior;


    /*
     * Constructor original.
     *
     * Se conserva para mantener compatibilidad
     * con pruebas o código que cree transacciones
     * sin especificar comisión.
     */
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

        this(
                codigo,
                fecha,
                tipo,
                monto,
                identificacionOrigen,
                monederoOrigen,
                identificacionDestino,
                monederoDestino,
                puntosGenerados,
                0,
                null,
                null
        );
    }


    /*
     * Constructor utilizado principalmente
     * por retiros y transferencias.
     */
    public Transaccion(
            String codigo,
            LocalDateTime fecha,
            TipoTransaccion tipo,
            double monto,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            int puntosGenerados,
            double comision) {

        this(
                codigo,
                fecha,
                tipo,
                monto,
                identificacionOrigen,
                monederoOrigen,
                identificacionDestino,
                monederoDestino,
                puntosGenerados,
                comision,
                null,
                null
        );
    }


    /*
     * Constructor anterior utilizado
     * por los gastos.
     *
     * Se conserva para mantener
     * compatibilidad.
     */
    public Transaccion(
            String codigo,
            LocalDateTime fecha,
            TipoTransaccion tipo,
            double monto,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            int puntosGenerados,
            CategoriaGasto categoriaGasto,
            CategoriaGasto categoriaAnterior) {

        this(
                codigo,
                fecha,
                tipo,
                monto,
                identificacionOrigen,
                monederoOrigen,
                identificacionDestino,
                monederoDestino,
                puntosGenerados,
                0,
                categoriaGasto,
                categoriaAnterior
        );
    }


    /*
     * Constructor completo.
     */
    public Transaccion(
            String codigo,
            LocalDateTime fecha,
            TipoTransaccion tipo,
            double monto,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            int puntosGenerados,
            double comision,
            CategoriaGasto categoriaGasto,
            CategoriaGasto categoriaAnterior) {

        this.codigo =
                codigo;

        this.fecha =
                fecha;

        this.tipo =
                tipo;

        this.monto =
                monto;

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

        this.comision =
                comision;

        this.revertida =
                false;

        this.categoriaGasto =
                categoriaGasto;

        this.categoriaAnterior =
                categoriaAnterior;
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


    public double getComision() {
        return comision;
    }


    public boolean isRevertida() {
        return revertida;
    }


    public CategoriaGasto getCategoriaGasto() {
        return categoriaGasto;
    }


    public CategoriaGasto getCategoriaAnterior() {
        return categoriaAnterior;
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
                ", comision=" + comision +
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
                ", categoriaGasto=" +
                categoriaGasto +
                ", revertida=" +
                revertida +
                '}';
    }
}