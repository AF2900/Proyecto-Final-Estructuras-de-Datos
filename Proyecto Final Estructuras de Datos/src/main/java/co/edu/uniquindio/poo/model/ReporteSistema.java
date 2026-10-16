package co.edu.uniquindio.poo.model;

public class ReporteSistema {

    private int cantidadClientes;

    private int cantidadMonederos;

    private double saldoTotalSistema;

    private int cantidadTransacciones;

    private int transaccionesActivas;

    private int transaccionesRevertidas;

    private double totalGastos;

    private int cantidadBeneficios;

    private int cantidadNotificaciones;

    private boolean integridadValida;


    public ReporteSistema(
            int cantidadClientes,
            int cantidadMonederos,
            double saldoTotalSistema,
            int cantidadTransacciones,
            int transaccionesActivas,
            int transaccionesRevertidas,
            double totalGastos,
            int cantidadBeneficios,
            int cantidadNotificaciones,
            boolean integridadValida) {

        this.cantidadClientes =
                cantidadClientes;

        this.cantidadMonederos =
                cantidadMonederos;

        this.saldoTotalSistema =
                saldoTotalSistema;

        this.cantidadTransacciones =
                cantidadTransacciones;

        this.transaccionesActivas =
                transaccionesActivas;

        this.transaccionesRevertidas =
                transaccionesRevertidas;

        this.totalGastos =
                totalGastos;

        this.cantidadBeneficios =
                cantidadBeneficios;

        this.cantidadNotificaciones =
                cantidadNotificaciones;

        this.integridadValida =
                integridadValida;
    }


    public int getCantidadClientes() {
        return cantidadClientes;
    }


    public int getCantidadMonederos() {
        return cantidadMonederos;
    }


    public double getSaldoTotalSistema() {
        return saldoTotalSistema;
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


    public double getTotalGastos() {
        return totalGastos;
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

        return "\n========== REPORTE SISTEMA ==========" +
                "\nClientes: " +
                cantidadClientes +
                "\nMonederos: " +
                cantidadMonederos +
                "\nSaldo total actual: " +
                saldoTotalSistema +
                "\nTransacciones únicas: " +
                cantidadTransacciones +
                "\nTransacciones activas: " +
                transaccionesActivas +
                "\nTransacciones revertidas: " +
                transaccionesRevertidas +
                "\nTotal gastos: " +
                totalGastos +
                "\nBeneficios canjeados: " +
                cantidadBeneficios +
                "\nNotificaciones: " +
                cantidadNotificaciones +
                "\nIntegridad válida: " +
                integridadValida +
                "\n=====================================";
    }
}