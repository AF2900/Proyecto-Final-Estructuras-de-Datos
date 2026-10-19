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

    private final GestorGrafos
            gestorGrafos;

    private final GestorPatronesGasto
            gestorPatronesGasto;

    private final Deque<Transaccion>
            pilaTransacciones;


    public GestorReversiones(
            GestorClientes gestorClientes,
            GestorPuntos gestorPuntos,
            GestorGrafos gestorGrafos,
            GestorPatronesGasto gestorPatronesGasto) {

        this.gestorClientes =
                gestorClientes;

        this.gestorPuntos =
                gestorPuntos;

        this.gestorGrafos =
                gestorGrafos;

        this.gestorPatronesGasto =
                gestorPatronesGasto;

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


    // ---------------------------------------------------------
    // REVERTIR ÚLTIMA
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

        } else if (transaccion.getTipo() ==
                TipoTransaccion.GASTO) {

            revertida =
                    revertirGasto(
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
         * Se devuelve exactamente lo
         * que se descontó inicialmente:
         *
         * monto + comisión.
         *
         * Si el retiro tenía beneficio,
         * la comisión almacenada será 0.
         */
        double totalADevolver =
                transaccion.getMonto()
                        +
                        transaccion.getComision();


        if (!monedero.depositar(
                totalADevolver
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
         * Del destino únicamente se retira
         * el monto que realmente recibió.
         *
         * La comisión nunca fue enviada
         * al destino.
         */
        if (!monederoDestino.retirar(
                transaccion.getMonto()
        )) {

            return false;
        }


        /*
         * Al origen se devuelve:
         *
         * monto + comisión cobrada.
         */
        double totalADevolver =
                transaccion.getMonto()
                        +
                        transaccion.getComision();


        if (!monederoOrigen.depositar(
                totalADevolver
        )) {

            /*
             * Restauración defensiva:
             * si no se pudiera devolver
             * el dinero al origen,
             * se restaura el destino.
             */
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


        gestorGrafos
                .revertirTransferencia(
                        transaccion
                                .getIdentificacionOrigen(),
                        transaccion
                                .getMonederoOrigen(),
                        transaccion
                                .getIdentificacionDestino(),
                        transaccion
                                .getMonederoDestino(),
                        transaccion
                                .getMonto()
                );


        return true;
    }


    // ---------------------------------------------------------
    // REVERTIR GASTO
    // ---------------------------------------------------------

    private boolean revertirGasto(
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


        boolean patronRevertido =
                gestorPatronesGasto
                        .revertirGasto(
                                transaccion
                                        .getIdentificacionOrigen(),
                                transaccion
                                        .getCategoriaGasto(),
                                transaccion
                                        .getCategoriaAnterior(),
                                transaccion
                                        .getMonto()
                        );


        if (!patronRevertido) {

            monedero.retirar(
                    transaccion.getMonto()
            );

            return false;
        }


        return true;
    }
}