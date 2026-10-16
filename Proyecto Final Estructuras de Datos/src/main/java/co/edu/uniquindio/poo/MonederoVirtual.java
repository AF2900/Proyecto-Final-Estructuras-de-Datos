package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;
import co.edu.uniquindio.poo.service.GestorClientes;
import co.edu.uniquindio.poo.service.GestorProgramadas;
import co.edu.uniquindio.poo.service.GestorPuntos;
import co.edu.uniquindio.poo.service.GestorReversiones;
import co.edu.uniquindio.poo.service.GestorTransacciones;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonederoVirtual {

    private final GestorClientes
            gestorClientes;

    private final GestorPuntos
            gestorPuntos;

    private final GestorReversiones
            gestorReversiones;

    private final GestorTransacciones
            gestorTransacciones;

    private final GestorProgramadas
            gestorProgramadas;


    public MonederoVirtual() {

        gestorClientes =
                new GestorClientes();


        gestorPuntos =
                new GestorPuntos();


        gestorReversiones =
                new GestorReversiones(
                        gestorClientes,
                        gestorPuntos
                );


        gestorTransacciones =
                new GestorTransacciones(
                        gestorClientes,
                        gestorReversiones,
                        gestorPuntos
                );


        gestorProgramadas =
                new GestorProgramadas(
                        gestorClientes,
                        gestorTransacciones
                );
    }


    // ---------------------------------------------------------
    // CLIENTES
    // ---------------------------------------------------------

    public boolean registrarCliente(
            Cliente cliente) {

        boolean registrado =
                gestorClientes
                        .registrarCliente(
                                cliente
                        );


        if (!registrado) {
            return false;
        }


        gestorPuntos.registrarCliente(
                cliente
        );


        return true;
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
    // TRANSACCIONES PROGRAMADAS
    // ---------------------------------------------------------

    public boolean programarDeposito(
            String codigo,
            String identificacionCliente,
            String idMonedero,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias) {

        return gestorProgramadas
                .programarDeposito(
                        codigo,
                        identificacionCliente,
                        idMonedero,
                        monto,
                        fechaEjecucion,
                        frecuenciaDias
                );
    }


    public boolean programarTransferencia(
            String codigo,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias) {

        return gestorProgramadas
                .programarTransferencia(
                        codigo,
                        identificacionOrigen,
                        monederoOrigen,
                        identificacionDestino,
                        monederoDestino,
                        monto,
                        fechaEjecucion,
                        frecuenciaDias
                );
    }


    public TransaccionProgramada
    verSiguienteProgramada() {

        return gestorProgramadas
                .verSiguienteProgramada();
    }


    public TransaccionProgramada
    buscarProgramada(
            String codigo) {

        return gestorProgramadas
                .buscarProgramada(
                        codigo
                );
    }


    public List<TransaccionProgramada>
    obtenerProgramadasEnOrden() {

        return gestorProgramadas
                .obtenerProgramadasEnOrden();
    }


    public int cantidadProgramadas() {

        return gestorProgramadas
                .cantidadProgramadas();
    }


    public boolean
    programadasVacia() {

        return gestorProgramadas
                .estaVacia();
    }


    public boolean cancelarProgramada(
            String codigo) {

        return gestorProgramadas
                .cancelarProgramada(
                        codigo
                );
    }


    public List<Transaccion>
    procesarTransaccionesProgramadas(
            LocalDateTime fechaActual) {

        return gestorProgramadas
                .procesarHasta(
                        fechaActual
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
    // PUNTOS Y RANGOS
    // ---------------------------------------------------------

    public Integer consultarPuntos(
            String identificacionCliente) {

        return gestorPuntos
                .consultarPuntos(
                        identificacionCliente
                );
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


    public List<Cliente>
    obtenerClientesOrdenadosPorPuntosBST() {

        return gestorPuntos
                .obtenerClientesOrdenadosPorPuntosBST();
    }


    public List<Cliente>
    obtenerClientesOrdenadosPorPuntosAVL() {

        return gestorPuntos
                .obtenerClientesOrdenadosPorPuntosAVL();
    }


    public int obtenerAlturaBST() {

        return gestorPuntos
                .obtenerAlturaBST();
    }


    public int obtenerAlturaAVL() {

        return gestorPuntos
                .obtenerAlturaAVL();
    }
}