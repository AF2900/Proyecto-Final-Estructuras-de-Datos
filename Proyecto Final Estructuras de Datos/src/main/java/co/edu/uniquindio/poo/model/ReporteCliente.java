package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.enums.RangoCliente;

public class ReporteCliente {

    private String identificacion;

    private String nombre;

    private int cantidadMonederos;

    private double saldoTotal;

    private int cantidadTransacciones;

    private int transaccionesActivas;

    private int transaccionesRevertidas;

    private int puntos;

    private RangoCliente rango;

    private double totalGastos;

    private CategoriaGasto categoriaMayorGasto;

    private double totalTransferido;

    private int cantidadBeneficios;

    private int cantidadNotificaciones;

    private boolean integridadValida;


    public ReporteCliente(
            String identificacion,
            String nombre,
            int cantidadMonederos,
            double saldoTotal,
            int cantidadTransacciones,
            int transaccionesActivas,
            int transaccionesRevertidas,
            int puntos,
            RangoCliente rango,
            double totalGastos,
            CategoriaGasto categoriaMayorGasto,
            double totalTransferido,
            int cantidadBeneficios,
            int cantidadNotificaciones,
            boolean integridadValida) {

        this.identificacion =
                identificacion;

        this.nombre =
                nombre;

        this.cantidadMonederos =
                cantidadMonederos;

        this.saldoTotal =
                saldoTotal;

        this.cantidadTransacciones =
                cantidadTransacciones;

        this.transaccionesActivas =
                transaccionesActivas;

        this.transaccionesRevertidas =
                transaccionesRevertidas;

        this.puntos =
                puntos;

        this.rango =
                rango;

        this.totalGastos =
                totalGastos;

        this.categoriaMayorGasto =
                categoriaMayorGasto;

        this.totalTransferido =
                totalTransferido;

        this.cantidadBeneficios =
                cantidadBeneficios;

        this.cantidadNotificaciones =
                cantidadNotificaciones;

        this.integridadValida =
                integridadValida;
    }


    public String getIdentificacion() {
        return identificacion;
    }


    public String getNombre() {
        return nombre;
    }


    public int getCantidadMonederos() {
        return cantidadMonederos;
    }


    public double getSaldoTotal() {
        return saldoTotal;
    }


    public int getCantidadTransacciones() {
        return cantidadTransacciones;
    }


    public int getTransaccionesActivas() {
        return transaccionesActivas;
    }


    public int getTransaccionesRevertidas() {
        return transaccionesRevertidas;
    }


    public int getPuntos() {
        return puntos;
    }


    public RangoCliente getRango() {
        return rango;
    }


    public double getTotalGastos() {
        return totalGastos;
    }


    public CategoriaGasto getCategoriaMayorGasto() {
        return categoriaMayorGasto;
    }


    public double getTotalTransferido() {
        return totalTransferido;
    }


    public int getCantidadBeneficios() {
        return cantidadBeneficios;
    }


    public int getCantidadNotificaciones() {
        return cantidadNotificaciones;
    }


    public boolean isIntegridadValida() {
        return integridadValida;
    }


    @Override
    public String toString() {

        return "\n========== REPORTE CLIENTE ==========" +
                "\nIdentificación: " +
                identificacion +
                "\nNombre: " +
                nombre +
                "\nMonederos: " +
                cantidadMonederos +
                "\nSaldo total: " +
                saldoTotal +
                "\nTransacciones: " +
                cantidadTransacciones +
                "\nTransacciones activas: " +
                transaccionesActivas +
                "\nTransacciones revertidas: " +
                transaccionesRevertidas +
                "\nPuntos: " +
                puntos +
                "\nRango: " +
                rango +
                "\nTotal gastos: " +
                totalGastos +
                "\nCategoría de mayor gasto: " +
                categoriaMayorGasto +
                "\nTotal transferido: " +
                totalTransferido +
                "\nBeneficios canjeados: " +
                cantidadBeneficios +
                "\nNotificaciones: " +
                cantidadNotificaciones +
                "\nIntegridad válida: " +
                integridadValida +
                "\n=====================================";
    }
}