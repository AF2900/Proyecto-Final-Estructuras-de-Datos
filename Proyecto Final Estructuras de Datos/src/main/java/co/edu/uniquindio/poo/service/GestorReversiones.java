package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

import java.util.ArrayDeque;
import java.util.Deque;

public class GestorReversiones {

    private final GestorClientes
            gestorClientes;

    /*
     * La estructura Deque se utiliza como una pila.
     *
     * push -> agrega al tope.
     * peek -> consulta el tope.
     * pop  -> retira el tope.
     *
     * Su comportamiento es LIFO:
     * Last In, First Out.
     */
    private final Deque<Transaccion>
            pilaTransacciones;


    public GestorReversiones(
            GestorClientes gestorClientes) {

        this.gestorClientes =
                gestorClientes;

        this.pilaTransacciones =
                new ArrayDeque<>();
    }


    // ---------------------------------------------------------
    // PILA
    // ---------------------------------------------------------

    public void registrarTransaccionReversible(
            Transaccion transaccion) {

        if (transaccion == null) {
            return;
        }

        if (transaccion.isRevertida()) {
            return;
        }

        pilaTransacciones.push(
                transaccion
        );
    }


    public Transaccion
    verUltimaTransaccion() {

        return pilaTransacciones.peek();
    }


    public boolean estaVacia() {

        return pilaTransacciones.isEmpty();
    }


    public int cantidadTransacciones() {

        return pilaTransacciones.size();
    }


    // ---------------------------------------------------------
    // REVERTIR
    // ---------------------------------------------------------

    public Transaccion
    revertirUltimaTransaccion() {

        Transaccion transaccion =
                pilaTransacciones.peek();

        if (transaccion == null) {
            return null;
        }


        boolean revertida = false;


        if (transaccion.getTipo() ==
                TipoTransaccion.DEPOSITO) {

            revertida =
                    revertirDeposito(
                            transaccion
                    );
        }


        else if (transaccion.getTipo() ==
                TipoTransaccion.RETIRO) {

            revertida =
                    revertirRetiro(
                            transaccion
                    );
        }


        else if (transaccion.getTipo() ==
                TipoTransaccion.TRANSFERENCIA) {

            revertida =
                    revertirTransferencia(
                            transaccion
                    );
        }


        if (!revertida) {
            return null;
        }


        /*
         * Solo se elimina del tope
         * cuando la reversión fue exitosa.
         */
        pilaTransacciones.pop();

        transaccion.marcarRevertida();

        return transaccion;
    }


    // ---------------------------------------------------------
    // REVERTIR DEPÓSITO
    // ---------------------------------------------------------

    private boolean revertirDeposito(
            Transaccion transaccion) {

        Cliente cliente =
                gestorClientes.buscarCliente(
                        transaccion
                                .getIdentificacionOrigen()
                );


        if (cliente == null) {
            return false;
        }


        Monedero monedero =
                cliente.buscarMonedero(
                        transaccion
                                .getMonederoOrigen()
                );


        if (monedero == null) {
            return false;
        }


        /*
         * Para revertir el depósito hay que
         * retirar nuevamente el dinero que
         * había sido depositado.
         */
        boolean retiroRealizado =
                monedero.retirar(
                        transaccion.getMonto()
                );


        if (!retiroRealizado) {
            return false;
        }


        cliente.restarPuntos(
                transaccion
                        .getPuntosGenerados()
        );


        return true;
    }


    // ---------------------------------------------------------
    // REVERTIR RETIRO
    // ---------------------------------------------------------

    private boolean revertirRetiro(
            Transaccion transaccion) {

        Cliente cliente =
                gestorClientes.buscarCliente(
                        transaccion
                                .getIdentificacionOrigen()
                );


        if (cliente == null) {
            return false;
        }


        Monedero monedero =
                cliente.buscarMonedero(
                        transaccion
                                .getMonederoOrigen()
                );


        if (monedero == null) {
            return false;
        }


        /*
         * Un retiro se revierte devolviendo
         * el dinero al monedero.
         */
        boolean depositoRealizado =
                monedero.depositar(
                        transaccion.getMonto()
                );


        if (!depositoRealizado) {
            return false;
        }


        cliente.restarPuntos(
                transaccion
                        .getPuntosGenerados()
        );


        return true;
    }


    // ---------------------------------------------------------
    // REVERTIR TRANSFERENCIA
    // ---------------------------------------------------------

    private boolean revertirTransferencia(
            Transaccion transaccion) {

        Cliente clienteOrigen =
                gestorClientes.buscarCliente(
                        transaccion
                                .getIdentificacionOrigen()
                );


        Cliente clienteDestino =
                gestorClientes.buscarCliente(
                        transaccion
                                .getIdentificacionDestino()
                );


        if (clienteOrigen == null ||
                clienteDestino == null) {

            return false;
        }


        Monedero monederoOrigen =
                clienteOrigen.buscarMonedero(
                        transaccion
                                .getMonederoOrigen()
                );


        Monedero monederoDestino =
                clienteDestino.buscarMonedero(
                        transaccion
                                .getMonederoDestino()
                );


        if (monederoOrigen == null ||
                monederoDestino == null) {

            return false;
        }


        /*
         * Para revertir la transferencia:
         *
         * 1. Se retira el dinero del destino.
         * 2. Se devuelve al origen.
         */
        boolean retiroDestino =
                monederoDestino.retirar(
                        transaccion.getMonto()
                );


        if (!retiroDestino) {
            return false;
        }


        boolean depositoOrigen =
                monederoOrigen.depositar(
                        transaccion.getMonto()
                );


        if (!depositoOrigen) {

            /*
             * Restauración defensiva:
             * si ocurriera un error al devolver
             * el dinero al origen, se regresa
             * nuevamente al destino.
             */
            monederoDestino.depositar(
                    transaccion.getMonto()
            );

            return false;
        }


        /*
         * Los puntos de una transferencia
         * pertenecen al cliente que la realizó.
         */
        clienteOrigen.restarPuntos(
                transaccion
                        .getPuntosGenerados()
        );


        return true;
    }
}