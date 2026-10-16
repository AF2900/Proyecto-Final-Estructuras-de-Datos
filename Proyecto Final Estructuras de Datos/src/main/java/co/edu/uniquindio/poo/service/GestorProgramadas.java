package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GestorProgramadas {

    /*
     * PriorityQueue mantiene primero
     * la transacción cuya fecha de
     * ejecución sea más cercana.
     */
    private final PriorityQueue<TransaccionProgramada>
            colaProgramadas;


    /*
     * Permite buscar rápidamente una
     * transacción programada por código.
     */
    private final Map<String, TransaccionProgramada>
            programadasPorCodigo;


    private final GestorClientes
            gestorClientes;


    private final GestorTransacciones
            gestorTransacciones;


    public GestorProgramadas(
            GestorClientes gestorClientes,
            GestorTransacciones gestorTransacciones) {

        this.gestorClientes =
                gestorClientes;

        this.gestorTransacciones =
                gestorTransacciones;


        colaProgramadas =
                new PriorityQueue<>();


        programadasPorCodigo =
                new HashMap<>();
    }


    // ---------------------------------------------------------
    // PROGRAMAR DEPÓSITO
    // ---------------------------------------------------------

    public boolean programarDeposito(
            String codigo,
            String identificacionCliente,
            String idMonedero,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias) {

        if (!codigoValido(
                codigo
        )) {

            return false;
        }


        if (monto <= 0 ||
                fechaEjecucion == null ||
                frecuenciaDias < 0) {

            return false;
        }


        Cliente cliente =
                gestorClientes.buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return false;
        }


        Monedero monedero =
                cliente.buscarMonedero(
                        idMonedero
                );


        if (monedero == null) {
            return false;
        }


        TransaccionProgramada programada =
                new TransaccionProgramada(
                        codigo,
                        TipoTransaccion.DEPOSITO,
                        identificacionCliente,
                        idMonedero,
                        null,
                        null,
                        monto,
                        fechaEjecucion,
                        frecuenciaDias
                );


        agregarProgramada(
                programada
        );


        return true;
    }


    // ---------------------------------------------------------
    // PROGRAMAR TRANSFERENCIA
    // ---------------------------------------------------------

    public boolean programarTransferencia(
            String codigo,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias) {

        if (!codigoValido(
                codigo
        )) {

            return false;
        }


        if (monto <= 0 ||
                fechaEjecucion == null ||
                frecuenciaDias < 0) {

            return false;
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

            return false;
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

            return false;
        }


        if (clienteOrigen == clienteDestino &&
                origen == destino) {

            return false;
        }


        TransaccionProgramada programada =
                new TransaccionProgramada(
                        codigo,
                        TipoTransaccion.TRANSFERENCIA,
                        identificacionOrigen,
                        monederoOrigen,
                        identificacionDestino,
                        monederoDestino,
                        monto,
                        fechaEjecucion,
                        frecuenciaDias
                );


        agregarProgramada(
                programada
        );


        return true;
    }


    // ---------------------------------------------------------
    // AGREGAR
    // ---------------------------------------------------------

    private void agregarProgramada(
            TransaccionProgramada programada) {

        colaProgramadas.offer(
                programada
        );


        programadasPorCodigo.put(
                programada.getCodigo(),
                programada
        );
    }


    // ---------------------------------------------------------
    // CONSULTAS
    // ---------------------------------------------------------

    public TransaccionProgramada
    verSiguienteProgramada() {

        return colaProgramadas.peek();
    }


    public TransaccionProgramada
    buscarProgramada(
            String codigo) {

        if (codigo == null ||
                codigo.trim().isEmpty()) {

            return null;
        }


        return programadasPorCodigo.get(
                codigo
        );
    }


    public int cantidadProgramadas() {

        return colaProgramadas.size();
    }


    public boolean estaVacia() {

        return colaProgramadas.isEmpty();
    }


    /*
     * Se crea una copia para consultar
     * el orden sin modificar la cola real.
     */
    public List<TransaccionProgramada>
    obtenerProgramadasEnOrden() {

        PriorityQueue<TransaccionProgramada>
                copia =
                new PriorityQueue<>(
                        colaProgramadas
                );


        List<TransaccionProgramada>
                resultado =
                new ArrayList<>();


        while (!copia.isEmpty()) {

            resultado.add(
                    copia.poll()
            );
        }


        return resultado;
    }


    // ---------------------------------------------------------
    // CANCELAR
    // ---------------------------------------------------------

    public boolean cancelarProgramada(
            String codigo) {

        TransaccionProgramada programada =
                buscarProgramada(
                        codigo
                );


        if (programada == null) {
            return false;
        }


        /*
         * PriorityQueue.remove(Object)
         * realiza una búsqueda lineal.
         */
        boolean eliminada =
                colaProgramadas.remove(
                        programada
                );


        if (!eliminada) {
            return false;
        }


        programadasPorCodigo.remove(
                codigo
        );


        return true;
    }


    // ---------------------------------------------------------
    // PROCESAR
    // ---------------------------------------------------------

    public List<Transaccion>
    procesarHasta(
            LocalDateTime fechaActual) {

        List<Transaccion> realizadas =
                new ArrayList<>();


        if (fechaActual == null) {

            return realizadas;
        }


        /*
         * Se procesan todas las operaciones
         * cuya fecha ya haya llegado.
         */
        while (!colaProgramadas.isEmpty() &&
                !colaProgramadas
                        .peek()
                        .getFechaEjecucion()
                        .isAfter(
                                fechaActual
                        )) {


            TransaccionProgramada programada =
                    colaProgramadas.poll();


            Transaccion transaccion =
                    ejecutarProgramada(
                            programada
                    );


            if (transaccion != null) {

                realizadas.add(
                        transaccion
                );
            }


            /*
             * Una operación recurrente vuelve
             * a insertarse con su nueva fecha.
             *
             * Primero salió de PriorityQueue,
             * por lo que es seguro modificar
             * la fecha antes de volverla a insertar.
             */
            if (programada.esRecurrente()) {

                programada
                        .programarSiguienteEjecucion();


                colaProgramadas.offer(
                        programada
                );

            } else {

                programadasPorCodigo.remove(
                        programada.getCodigo()
                );
            }
        }


        return realizadas;
    }


    // ---------------------------------------------------------
    // EJECUTAR OPERACIÓN
    // ---------------------------------------------------------

    private Transaccion ejecutarProgramada(
            TransaccionProgramada programada) {

        if (programada.getTipo() ==
                TipoTransaccion.DEPOSITO) {

            return gestorTransacciones
                    .depositar(
                            programada
                                    .getIdentificacionOrigen(),
                            programada
                                    .getMonederoOrigen(),
                            programada
                                    .getMonto()
                    );
        }


        if (programada.getTipo() ==
                TipoTransaccion.TRANSFERENCIA) {

            return gestorTransacciones
                    .transferir(
                            programada
                                    .getIdentificacionOrigen(),
                            programada
                                    .getMonederoOrigen(),
                            programada
                                    .getIdentificacionDestino(),
                            programada
                                    .getMonederoDestino(),
                            programada
                                    .getMonto()
                    );
        }


        return null;
    }


    // ---------------------------------------------------------
    // VALIDACIÓN
    // ---------------------------------------------------------

    private boolean codigoValido(
            String codigo) {

        if (codigo == null ||
                codigo.trim().isEmpty()) {

            return false;
        }


        return !programadasPorCodigo
                .containsKey(
                        codigo
                );
    }
}