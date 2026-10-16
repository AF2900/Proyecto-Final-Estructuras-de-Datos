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

    private final GestorPuntos
            gestorPuntos;

    private final Deque<Transaccion>
            pilaTransacciones;


    public GestorReversiones(
            GestorClientes gestorClientes,
            GestorPuntos gestorPuntos) {

        this.gestorClientes =
                gestorClientes;

        this.gestorPuntos =
                gestorPuntos;

        pilaTransacciones =
                new ArrayDeque<>();
    }


    public void registrarTransaccionReversible(
            Transaccion transaccion) {

        if (transaccion == null ||
                transaccion.isRevertida()) {

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

        } else if (transaccion.getTipo() ==
                TipoTransaccion.RETIRO) {

            revertida =
                    revertirRetiro(
                            transaccion
                    );

        } else if (transaccion.getTipo() ==
                TipoTransaccion.TRANSFERENCIA) {

            revertida =
                    revertirTransferencia(
                            transaccion
                    );
        }


        if (!revertida) {
            return null;
        }


        pilaTransacciones.pop();

        transaccion.marcarRevertida();

        return transaccion;
    }


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


        if (!monedero.retirar(
                transaccion.getMonto()
        )) {

            return false;
        }


        if (transaccion.getPuntosGenerados()
                > 0) {

            gestorPuntos.restarPuntos(
                    cliente,
                    transaccion
                            .getPuntosGenerados()
            );
        }


        return true;
    }


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


        if (!monedero.depositar(
                transaccion.getMonto()
        )) {

            return false;
        }


        if (transaccion.getPuntosGenerados()
                > 0) {

            gestorPuntos.restarPuntos(
                    cliente,
                    transaccion
                            .getPuntosGenerados()
            );
        }


        return true;
    }


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


        if (!monederoDestino.retirar(
                transaccion.getMonto()
        )) {

            return false;
        }


        if (!monederoOrigen.depositar(
                transaccion.getMonto()
        )) {

            monederoDestino.depositar(
                    transaccion.getMonto()
            );

            return false;
        }


        if (transaccion.getPuntosGenerados()
                > 0) {

            gestorPuntos.restarPuntos(
                    clienteOrigen,
                    transaccion
                            .getPuntosGenerados()
            );
        }


        return true;
    }
}