package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.TipoBeneficio;

import java.time.LocalDateTime;

public class BeneficioCanjeado {

    private String codigo;

    private String identificacionCliente;

    private TipoBeneficio tipo;

    private int puntosUtilizados;

    private LocalDateTime fechaCanje;

    private LocalDateTime fechaVencimiento;

    private String idMonederoBeneficiado;


    public BeneficioCanjeado(
            String codigo,
            String identificacionCliente,
            TipoBeneficio tipo,
            int puntosUtilizados,
            LocalDateTime fechaCanje,
            LocalDateTime fechaVencimiento,
            String idMonederoBeneficiado) {

        this.codigo =
                codigo;

        this.identificacionCliente =
                identificacionCliente;

        this.tipo =
                tipo;

        this.puntosUtilizados =
                puntosUtilizados;

        this.fechaCanje =
                fechaCanje;

        this.fechaVencimiento =
                fechaVencimiento;

        this.idMonederoBeneficiado =
                idMonederoBeneficiado;
    }


    public String getCodigo() {
        return codigo;
    }


    public String getIdentificacionCliente() {
        return identificacionCliente;
    }


    public TipoBeneficio getTipo() {
        return tipo;
    }


    public int getPuntosUtilizados() {
        return puntosUtilizados;
    }


    public LocalDateTime getFechaCanje() {
        return fechaCanje;
    }


    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }


    public String getIdMonederoBeneficiado() {
        return idMonederoBeneficiado;
    }


    public boolean estaVigente() {

        /*
         * El bono de saldo se aplica
         * inmediatamente y no es un
         * beneficio temporal.
         */
        if (tipo ==
                TipoBeneficio.BONO_SALDO) {

            return false;
        }


        /*
         * El descuento de transferencia
         * no tiene duración especificada
         * por el enunciado.
         */
        if (fechaVencimiento == null) {

            return true;
        }


        return !LocalDateTime
                .now()
                .isAfter(
                        fechaVencimiento
                );
    }


    @Override
    public String toString() {

        return "BeneficioCanjeado{" +
                "codigo='" +
                codigo + '\'' +
                ", cliente='" +
                identificacionCliente + '\'' +
                ", tipo=" +
                tipo +
                ", puntosUtilizados=" +
                puntosUtilizados +
                ", fechaCanje=" +
                fechaCanje +
                ", fechaVencimiento=" +
                fechaVencimiento +
                ", monedero='" +
                idMonederoBeneficiado + '\'' +
                '}';
    }
}