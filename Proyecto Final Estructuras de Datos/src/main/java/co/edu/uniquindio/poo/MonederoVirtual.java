package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.service.GestorClientes;
import co.edu.uniquindio.poo.service.GestorReversiones;
import co.edu.uniquindio.poo.service.GestorTransacciones;

import java.util.ArrayList;
import java.util.List;

public class MonederoVirtual {

    private final GestorClientes
            gestorClientes;

    private final GestorReversiones
            gestorReversiones;

    private final GestorTransacciones
            gestorTransacciones;


    public MonederoVirtual() {

        gestorClientes =
                new GestorClientes();


        gestorReversiones =
                new GestorReversiones(
                        gestorClientes
                );


        gestorTransacciones =
                new GestorTransacciones(
                        gestorClientes,
                        gestorReversiones
                );
    }


    // ---------------------------------------------------------
    // CLIENTES
    // ---------------------------------------------------------

    public boolean registrarCliente(
            Cliente cliente) {

        return gestorClientes
                .registrarCliente(
                        cliente
                );
    }


    public Cliente buscarCliente(
            String identificacion) {

        return gestorClientes
                .buscarCliente(
                        identificacion
                );
    }


    public List<Cliente>
    obtenerClientes() {

        return gestorClientes
                .obtenerClientes();
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    public boolean crearMonedero(
            String identificacionCliente,
            Monedero monedero) {

        return gestorClientes
                .crearMonedero(
                        identificacionCliente,
                        monedero
                );
    }


    public Monedero buscarMonedero(
            String identificacionCliente,
            String idMonedero) {

        return gestorClientes
                .buscarMonedero(
                        identificacionCliente,
                        idMonedero
                );
    }


    public Double consultarSaldo(
            String identificacionCliente,
            String idMonedero) {

        Monedero monedero =
                buscarMonedero(
                        identificacionCliente,
                        idMonedero
                );


        if (monedero == null) {
            return null;
        }


        return monedero.getSaldo();
    }


    // ---------------------------------------------------------
    // TRANSACCIONES
    // ---------------------------------------------------------

    public Transaccion depositar(
            String identificacionCliente,
            String idMonedero,
            double monto) {

        return gestorTransacciones
                .depositar(
                        identificacionCliente,
                        idMonedero,
                        monto
                );
    }


    public Transaccion retirar(
            String identificacionCliente,
            String idMonedero,
            double monto) {

        return gestorTransacciones
                .retirar(
                        identificacionCliente,
                        idMonedero,
                        monto
                );
    }


    public Transaccion transferir(
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto) {

        return gestorTransacciones
                .transferir(
                        identificacionOrigen,
                        monederoOrigen,
                        identificacionDestino,
                        monederoDestino,
                        monto
                );
    }


    // ---------------------------------------------------------
    // REVERSIONES
    // ---------------------------------------------------------

    public Transaccion
    verUltimaTransaccionReversible() {

        return gestorReversiones
                .verUltimaTransaccion();
    }


    public Transaccion
    revertirUltimaTransaccion() {

        return gestorReversiones
                .revertirUltimaTransaccion();
    }


    public int
    cantidadTransaccionesReversibles() {

        return gestorReversiones
                .cantidadTransacciones();
    }


    public boolean
    pilaReversionesVacia() {

        return gestorReversiones
                .estaVacia();
    }


    // ---------------------------------------------------------
    // HISTORIAL
    // ---------------------------------------------------------

    public List<Transaccion>
    obtenerHistorial(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {

            return new ArrayList<>();
        }


        return cliente
                .obtenerHistorialTransacciones();
    }


    // ---------------------------------------------------------
    // PUNTOS Y RANGO
    // ---------------------------------------------------------

    public Integer consultarPuntos(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return null;
        }


        return cliente.getPuntos();
    }


    public RangoCliente consultarRango(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return null;
        }


        return cliente.getRango();
    }
}