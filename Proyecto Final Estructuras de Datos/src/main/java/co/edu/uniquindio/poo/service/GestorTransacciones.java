package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

import java.time.LocalDateTime;

public class GestorTransacciones {

    private final GestorClientes
            gestorClientes;

    private int consecutivo;


    public GestorTransacciones(
            GestorClientes gestorClientes) {

        this.gestorClientes =
                gestorClientes;

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

        boolean depositoRealizado =
                monedero.depositar(
                        monto
                );

        if (!depositoRealizado) {
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


        cliente.agregarPuntos(
                puntos
        );

        cliente.registrarTransaccion(
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

        boolean retiroRealizado =
                monedero.retirar(
                        monto
                );

        if (!retiroRealizado) {
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


        cliente.agregarPuntos(
                puntos
        );

        cliente.registrarTransaccion(
                transaccion
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


        if (origen.getSaldo() < monto) {

            return null;
        }


        boolean retiroRealizado =
                origen.retirar(
                        monto
                );


        if (!retiroRealizado) {
            return null;
        }


        destino.depositar(
                monto
        );


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


        /*
         * Los puntos pertenecen al cliente
         * que realizó la transferencia.
         */
        clienteOrigen.agregarPuntos(
                puntos
        );


        /*
         * Se registra en el historial
         * del cliente origen.
         */
        clienteOrigen.registrarTransaccion(
                transaccion
        );


        /*
         * También se registra en el historial
         * del cliente que recibió el dinero,
         * porque la transferencia afecta
         * uno de sus monederos.
         */
        if (clienteOrigen != clienteDestino) {

            clienteDestino
                    .registrarTransaccion(
                            transaccion
                    );
        }


        return transaccion;
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
    // CÓDIGO DE TRANSACCIÓN
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