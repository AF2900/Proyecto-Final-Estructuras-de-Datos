package co.edu.uniquindio.poo.estructuras.grafo;

public class AristaGrafo {

    private String destino;

    private double montoTotal;

    private int cantidadTransacciones;


    public AristaGrafo(
            String destino,
            double montoInicial) {

        this.destino =
                destino;

        this.montoTotal =
                montoInicial;

        this.cantidadTransacciones =
                1;
    }


    public String getDestino() {
        return destino;
    }


    public double getMontoTotal() {
        return montoTotal;
    }


    public int getCantidadTransacciones() {
        return cantidadTransacciones;
    }


    public void agregarTransferencia(
            double monto) {

        if (monto <= 0) {
            return;
        }


        montoTotal += monto;

        cantidadTransacciones++;
    }


    public boolean retirarTransferencia(
            double monto) {

        if (monto <= 0) {
            return false;
        }


        if (cantidadTransacciones <= 0) {
            return false;
        }


        montoTotal -= monto;

        cantidadTransacciones--;


        if (montoTotal < 0) {

            montoTotal = 0;
        }


        return true;
    }


    @Override
    public String toString() {

        return "AristaGrafo{" +
                "destino='" +
                destino + '\'' +
                ", montoTotal=" +
                montoTotal +
                ", cantidadTransacciones=" +
                cantidadTransacciones +
                '}';
    }
}