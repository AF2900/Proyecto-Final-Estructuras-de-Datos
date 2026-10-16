package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.enums.RangoCliente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {

    private String identificacion;

    private String nombre;

    private int puntos;

    private RangoCliente rango;

    private final Map<String, Monedero>
            monederos;

    private final List<Transaccion>
            historialTransacciones;


    public Cliente(
            String identificacion,
            String nombre) {

        this.identificacion =
                identificacion;

        this.nombre =
                nombre;

        this.puntos = 0;

        this.rango =
                RangoCliente.BRONCE;

        this.monederos =
                new HashMap<>();

        this.historialTransacciones =
                new ArrayList<>();
    }


    public String getIdentificacion() {
        return identificacion;
    }


    public String getNombre() {
        return nombre;
    }


    public int getPuntos() {
        return puntos;
    }


    public RangoCliente getRango() {
        return rango;
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    public boolean agregarMonedero(
            Monedero monedero) {

        if (monedero == null) {
            return false;
        }

        if (monedero.getId() == null ||
                monedero.getId()
                        .trim()
                        .isEmpty()) {

            return false;
        }

        if (monederos.containsKey(
                monedero.getId())) {

            return false;
        }

        monederos.put(
                monedero.getId(),
                monedero
        );

        return true;
    }


    public Monedero buscarMonedero(
            String idMonedero) {

        if (idMonedero == null ||
                idMonedero
                        .trim()
                        .isEmpty()) {

            return null;
        }

        return monederos.get(
                idMonedero
        );
    }


    public List<Monedero>
    obtenerMonederos() {

        return new ArrayList<>(
                monederos.values()
        );
    }


    // ---------------------------------------------------------
    // HISTORIAL
    // ---------------------------------------------------------

    public void registrarTransaccion(
            Transaccion transaccion) {

        if (transaccion != null) {

            historialTransacciones.add(
                    transaccion
            );
        }
    }


    public List<Transaccion>
    obtenerHistorialTransacciones() {

        return new ArrayList<>(
                historialTransacciones
        );
    }


    // ---------------------------------------------------------
    // PUNTOS
    // ---------------------------------------------------------

    public void agregarPuntos(
            int puntosGenerados) {

        if (puntosGenerados <= 0) {
            return;
        }

        puntos += puntosGenerados;

        actualizarRango();
    }


    public void restarPuntos(
            int puntosARestar) {

        if (puntosARestar <= 0) {
            return;
        }

        puntos -= puntosARestar;

        if (puntos < 0) {
            puntos = 0;
        }

        actualizarRango();
    }


    private void actualizarRango() {

        rango =
                RangoCliente
                        .calcularRango(
                                puntos
                        );
    }


    @Override
    public String toString() {

        return "Cliente{" +
                "identificacion='" +
                identificacion + '\'' +
                ", nombre='" +
                nombre + '\'' +
                ", puntos=" +
                puntos +
                ", rango=" +
                rango +
                ", cantidadMonederos=" +
                monederos.size() +
                '}';
    }
}