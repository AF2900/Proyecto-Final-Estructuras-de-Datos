package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.estructuras.cola.ColaPrioridad;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorProgramadas {

    private ColaPrioridad<
            TransaccionProgramada
            > colaProgramadas;

    private Map<
            String,
            TransaccionProgramada
            > programadasPorCodigo;

    private GestorClientes gestorClientes;

    private GestorTransacciones gestorTransacciones;

    public GestorProgramadas(
            GestorClientes gestorClientes,
            GestorTransacciones gestorTransacciones
    ) {

        this.gestorClientes =
                gestorClientes;

        this.gestorTransacciones =
                gestorTransacciones;

        colaProgramadas =
                new ColaPrioridad<>(
                        Integer.MAX_VALUE
                );

        programadasPorCodigo =
                new HashMap<>();
    }

    public boolean programarDeposito(
            String codigo,
            String identificacionCliente,
            String idMonedero,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias
    ) {

        if (
                codigo == null
                        ||
                        codigo.isBlank()
                        ||
                        programadasPorCodigo
                                .containsKey(codigo)
        ) {
            return false;
        }

        if (
                identificacionCliente == null
                        ||
                        identificacionCliente.isBlank()
                        ||
                        idMonedero == null
                        ||
                        idMonedero.isBlank()
        ) {
            return false;
        }

        if (
                monto <= 0
                        ||
                        fechaEjecucion == null
                        ||
                        frecuenciaDias < 0
        ) {
            return false;
        }

        if (
                gestorClientes.buscarCliente(
                        identificacionCliente
                ) == null
        ) {
            return false;
        }

        if (
                gestorClientes.buscarMonedero(
                        identificacionCliente,
                        idMonedero
                ) == null
        ) {
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

        colaProgramadas.encolar(
                programada
        );

        programadasPorCodigo.put(
                codigo,
                programada
        );

        return true;
    }

    public boolean programarTransferencia(
            String codigo,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias
    ) {

        if (
                codigo == null
                        ||
                        codigo.isBlank()
                        ||
                        programadasPorCodigo
                                .containsKey(codigo)
        ) {
            return false;
        }

        if (
                identificacionOrigen == null
                        ||
                        identificacionDestino == null
                        ||
                        monederoOrigen == null
                        ||
                        monederoDestino == null
        ) {
            return false;
        }

        if (
                monto <= 0
                        ||
                        fechaEjecucion == null
                        ||
                        frecuenciaDias < 0
        ) {
            return false;
        }

        if (
                gestorClientes.buscarCliente(
                        identificacionOrigen
                ) == null
                        ||
                        gestorClientes.buscarCliente(
                                identificacionDestino
                        ) == null
        ) {
            return false;
        }

        if (
                gestorClientes.buscarMonedero(
                        identificacionOrigen,
                        monederoOrigen
                ) == null
                        ||
                        gestorClientes.buscarMonedero(
                                identificacionDestino,
                                monederoDestino
                        ) == null
        ) {
            return false;
        }

        if (
                identificacionOrigen.equals(
                        identificacionDestino
                )
                        &&
                        monederoOrigen.equals(
                                monederoDestino
                        )
        ) {
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

        colaProgramadas.encolar(
                programada
        );

        programadasPorCodigo.put(
                codigo,
                programada
        );

        return true;
    }

    public TransaccionProgramada
    verSiguienteProgramada() {

        return colaProgramadas
                .verPrimero();
    }

    public TransaccionProgramada buscarProgramada(
            String codigo
    ) {

        return programadasPorCodigo
                .get(codigo);
    }

    public int cantidadProgramadas() {

        return colaProgramadas
                .getSize();
    }

    public boolean estaVacia() {

        return colaProgramadas
                .estaVacia();
    }

    public List<TransaccionProgramada>
    obtenerProgramadasEnOrden() {

        return colaProgramadas
                .obtenerElementos();
    }

    public boolean cancelarProgramada(
            String codigo
    ) {

        if (codigo == null) {
            return false;
        }

        TransaccionProgramada programada =
                programadasPorCodigo
                        .get(codigo);

        if (programada == null) {
            return false;
        }

        boolean eliminada =
                colaProgramadas
                        .eliminar(programada);

        if (eliminada) {

            programadasPorCodigo
                    .remove(codigo);
        }

        return eliminada;
    }

    public List<Transaccion> procesarHasta(
            LocalDateTime fechaActual
    ) {

        List<Transaccion> procesadas =
                new ArrayList<>();

        if (fechaActual == null) {
            return procesadas;
        }

        while (
                !colaProgramadas
                        .estaVacia()
        ) {

            TransaccionProgramada siguiente =
                    colaProgramadas
                            .verPrimero();

            if (
                    siguiente
                            .getFechaEjecucion()
                            .isAfter(
                                    fechaActual
                            )
            ) {
                break;
            }

            TransaccionProgramada programada =
                    colaProgramadas
                            .desencolar();

            Transaccion resultado =
                    null;

            if (
                    programada.getTipo()
                            ==
                            TipoTransaccion.DEPOSITO
            ) {

                resultado =
                        gestorTransacciones
                                .depositar(
                                        programada
                                                .getIdentificacionOrigen(),
                                        programada
                                                .getMonederoOrigen(),
                                        programada
                                                .getMonto()
                                );

            } else if (
                    programada.getTipo()
                            ==
                            TipoTransaccion.TRANSFERENCIA
            ) {

                resultado =
                        gestorTransacciones
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

            if (resultado != null) {

                procesadas.add(
                        resultado
                );
            }

            if (
                    programada.esRecurrente()
            ) {

                programada
                        .programarSiguienteEjecucion();

                colaProgramadas.encolar(
                        programada
                );

            } else {

                programadasPorCodigo.remove(
                        programada.getCodigo()
                );
            }
        }

        return procesadas;
    }
}