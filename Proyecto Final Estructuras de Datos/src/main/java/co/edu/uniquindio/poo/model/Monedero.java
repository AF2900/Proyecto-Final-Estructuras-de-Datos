package co.edu.uniquindio.poo.model;

public class Monedero {

    private String id;
    private String nombre;
    private double saldo;


    public Monedero(
            String id,
            String nombre,
            double saldoInicial) {

        this.id = id;
        this.nombre = nombre;

        if (saldoInicial >= 0) {
            this.saldo = saldoInicial;
        } else {
            this.saldo = 0;
        }
    }


    public String getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }


    public double getSaldo() {
        return saldo;
    }


    public boolean depositar(
            double monto) {

        if (monto <= 0) {
            return false;
        }

        saldo += monto;

        return true;
    }


    public boolean retirar(
            double monto) {

        if (monto <= 0) {
            return false;
        }

        if (monto > saldo) {
            return false;
        }

        saldo -= monto;

        return true;
    }


    @Override
    public String toString() {

        return "Monedero{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}