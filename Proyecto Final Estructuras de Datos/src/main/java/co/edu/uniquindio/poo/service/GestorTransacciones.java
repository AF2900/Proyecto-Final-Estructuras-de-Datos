package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

import java.time.LocalDateTime;

public class GestorTransacciones {

    /*
     * Valor definido para esta implementación.
     *
     * El documento exige alertas por saldo bajo,
     * pero no establece un límite específico.
     */
    private static final double
            LIMITE_SALDO_BAJO = 100.0;


    private final GestorClientes
            gestorClientes;

    private final GestorReversiones
            gestorReversiones;

    private final GestorPuntos
            gestorPuntos;

    private final GestorNotificaciones
            gestorNotificaciones;

    private int consecutivo;


    public GestorTransacciones(
            GestorClientes gestorClientes,
            GestorReversiones gestorReversiones,
            GestorPuntos gestorPuntos,
            GestorNotificaciones gestorNotificaciones) {

        this.gestorClientes =
                gestorClientes;

        this.gestorReversiones =
                gestorReversiones;

        this.gestorPuntos =
                gestorPuntos;

        this.gestorNotificaciones =
                gestorNotificaciones;

        this.consecutivo = 1;
    }


    // ---------------------------------------------------------
    // DEPÓSITO
    // ---------------------------------------------------------

    public Transaccion depositar(
            String identificacionCliente,
            String idMonedero,
            double monto) {

        if (monto <= 0) {
            return null;
        }


        Cliente cliente =
                gestorClientes.buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return null;
        }


        Monedero monedero =
                cliente.buscarMonedero(
                        idMonedero
                );


        if (monedero == null) {
            return null;
        }


        if (!monedero.depositar(
                monto
        )) {

            return null;
        }


        int puntos =
                calcularPuntos(
                        TipoTransaccion.DEPOSITO,
                        monto
                );


        Transaccion transaccion =
                new Transaccion(
                        generarCodigo(),
                        LocalDateTime.now(),
                        TipoTransaccion.DEPOSITO,
                        monto,
                        identificacionCliente,
                        idMonedero,
                        null,
                        null,
                        puntos
                );


        if (puntos > 0) {

            gestorPuntos.agregarPuntos(
                    cliente,
                    puntos
            );
        }


        cliente.registrarTransaccion(
                transaccion
        );


        gestorReversiones
                .registrarTransaccionReversible(
                        transaccion
                );


        return transaccion;
    }


    // ---------------------------------------------------------
    // RETIRO
    // ---------------------------------------------------------

    public Transaccion retirar(
            String identificacionCliente,
            String idMonedero,
            double monto) {

        if (monto <= 0) {
            return null;
        }


        Cliente cliente =
                gestorClientes.buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return null;
        }


        Monedero monedero =
                cliente.buscarMonedero(
                        idMonedero
                );


        if (monedero == null) {
            return null;
        }


        if (!monedero.retirar(
                monto
        )) {

            return null;
        }


        int puntos =
                calcularPuntos(
                        TipoTransaccion.RETIRO,
                        monto
                );


        Transaccion transaccion =
                new Transaccion(
                        generarCodigo(),
                        LocalDateTime.now(),
                        TipoTransaccion.RETIRO,
                        monto,
                        identificacionCliente,
                        idMonedero,
                        null,
                        null,
                        puntos
                );


        if (puntos > 0) {

            gestorPuntos.agregarPuntos(
                    cliente,
                    puntos
            );
        }


        cliente.registrarTransaccion(
                transaccion
        );


        gestorReversiones
                .registrarTransaccionReversible(
                        transaccion
                );


        verificarSaldoBajo(
                cliente,
                monedero
        );


        return transaccion;
    }


    // ---------------------------------------------------------
    // TRANSFERENCIA
    // ---------------------------------------------------------

    public Transaccion transferir(
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto) {

        if (monto <= 0) {
            return null;
        }


        Cliente clienteOrigen =
                gestorClientes.buscarCliente(
                        identificacionOrigen
                );


        Cliente clienteDestino =
                gestorClientes.buscarCliente(
                        identificacionDestino
                );


        if (clienteOrigen == null ||
                clienteDestino == null) {

            return null;
        }


        Monedero origen =
                clienteOrigen.buscarMonedero(
                        monederoOrigen
                );


        Monedero destino =
                clienteDestino.buscarMonedero(
                        monederoDestino
                );


        if (origen == null ||
                destino == null) {

            return null;
        }


        if (clienteOrigen == clienteDestino &&
                origen == destino) {

            return null;
        }


        if (!origen.retirar(
                monto
        )) {

            return null;
        }


        if (!destino.depositar(
                monto
        )) {

            origen.depositar(
                    monto
            );

            return null;
        }


        int puntos =
                calcularPuntos(
                        TipoTransaccion.TRANSFERENCIA,
                        monto
                );


        Transaccion transaccion =
                new Transaccion(
                        generarCodigo(),
                        LocalDateTime.now(),
                        TipoTransaccion.TRANSFERENCIA,
                        monto,
                        identificacionOrigen,
                        monederoOrigen,
                        identificacionDestino,
                        monederoDestino,
                        puntos
                );


        if (puntos > 0) {

            gestorPuntos.agregarPuntos(
                    clienteOrigen,
                    puntos
            );
        }


        clienteOrigen.registrarTransaccion(
                transaccion
        );


        if (clienteOrigen != clienteDestino) {

            clienteDestino
                    .registrarTransaccion(
                            transaccion
                    );
        }


        gestorReversiones
                .registrarTransaccionReversible(
                        transaccion
                );


        verificarSaldoBajo(
                clienteOrigen,
                origen
        );


        return transaccion;
    }


    // ---------------------------------------------------------
    // ALERTA SALDO BAJO
    // ---------------------------------------------------------

    private void verificarSaldoBajo(
            Cliente cliente,
            Monedero monedero) {

        if (monedero.getSaldo()
                < LIMITE_SALDO_BAJO) {

            gestorNotificaciones
                    .enviarNotificacion(
                            cliente
                                    .getIdentificacion(),
                            TipoNotificacion
                                    .SALDO_BAJO,
                            "El monedero "
                                    + monedero.getNombre()
                                    + " tiene saldo bajo: "
                                    + monedero.getSaldo()
                    );
        }
    }


    // ---------------------------------------------------------
    // PUNTOS
    // ---------------------------------------------------------

    private int calcularPuntos(
            TipoTransaccion tipo,
            double monto) {

        int bloquesDeCien =
                (int) (monto / 100);


        if (tipo ==
                TipoTransaccion.DEPOSITO) {

            return bloquesDeCien;
        }


        if (tipo ==
                TipoTransaccion.RETIRO) {

            return bloquesDeCien * 2;
        }


        if (tipo ==
                TipoTransaccion.TRANSFERENCIA) {

            return bloquesDeCien * 3;
        }


        return 0;
    }


    // ---------------------------------------------------------
    // CÓDIGO
    // ---------------------------------------------------------

    private String generarCodigo() {

        String codigo =
                String.format(
                        "TX%05d",
                        consecutivo
                );


        consecutivo++;


        return codigo;
    }
}