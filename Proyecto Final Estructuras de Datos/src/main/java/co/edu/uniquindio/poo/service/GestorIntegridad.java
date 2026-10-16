package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Transaccion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorIntegridad {

    // ---------------------------------------------------------
    // VERIFICAR UN CLIENTE
    // ---------------------------------------------------------

    public boolean verificarIntegridadCliente(
            Cliente cliente) {

        if (cliente == null) {
            return false;
        }


        List<Transaccion> historial =
                cliente
                        .obtenerHistorialTransacciones();


        Set<String> codigos =
                new HashSet<>();


        return verificarHistorialRecursivo(
                cliente.getIdentificacion(),
                historial,
                0,
                codigos
        );
    }


    // ---------------------------------------------------------
    // VERIFICAR TODO EL SISTEMA
    // ---------------------------------------------------------

    public boolean verificarIntegridadSistema(
            List<Cliente> clientes) {

        if (clientes == null) {
            return false;
        }


        return verificarClientesRecursivo(
                clientes,
                0
        );
    }


    private boolean verificarClientesRecursivo(
            List<Cliente> clientes,
            int indice) {

        /*
         * Caso base:
         * todos los clientes fueron revisados.
         */
        if (indice ==
                clientes.size()) {

            return true;
        }


        Cliente cliente =
                clientes.get(
                        indice
                );


        if (!verificarIntegridadCliente(
                cliente
        )) {

            return false;
        }


        /*
         * Llamada recursiva:
         * revisar el siguiente cliente.
         */
        return verificarClientesRecursivo(
                clientes,
                indice + 1
        );
    }


    // ---------------------------------------------------------
    // VERIFICAR HISTORIAL
    // ---------------------------------------------------------

    private boolean verificarHistorialRecursivo(
            String identificacionCliente,
            List<Transaccion> historial,
            int indice,
            Set<String> codigos) {

        /*
         * Caso base:
         * se llegó al final del historial.
         */
        if (indice ==
                historial.size()) {

            return true;
        }


        Transaccion transaccion =
                historial.get(
                        indice
                );


        /*
         * Validar la transacción actual.
         */
        if (!transaccionValidaParaCliente(
                identificacionCliente,
                transaccion
        )) {

            return false;
        }


        /*
         * El código no puede repetirse
         * dentro del historial del cliente.
         */
        if (codigos.contains(
                transaccion.getCodigo()
        )) {

            return false;
        }


        codigos.add(
                transaccion.getCodigo()
        );


        /*
         * Llamada recursiva:
         * validar la siguiente transacción.
         */
        return verificarHistorialRecursivo(
                identificacionCliente,
                historial,
                indice + 1,
                codigos
        );
    }


    // ---------------------------------------------------------
    // VALIDACIÓN SEGÚN EL TIPO
    // ---------------------------------------------------------

    private boolean transaccionValidaParaCliente(
            String identificacionCliente,
            Transaccion transaccion) {

        if (transaccion == null) {
            return false;
        }


        if (transaccion.getCodigo() == null ||
                transaccion
                        .getCodigo()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        if (transaccion.getFecha() == null) {
            return false;
        }


        if (transaccion.getTipo() == null) {
            return false;
        }


        if (transaccion.getMonto() <= 0) {
            return false;
        }


        if (transaccion.getPuntosGenerados()
                < 0) {

            return false;
        }


        if (transaccion
                .getIdentificacionOrigen()
                == null ||
                transaccion
                        .getIdentificacionOrigen()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        if (transaccion
                .getMonederoOrigen()
                == null ||
                transaccion
                        .getMonederoOrigen()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        if (transaccion.getTipo() ==
                TipoTransaccion.DEPOSITO) {

            return validarDeposito(
                    identificacionCliente,
                    transaccion
            );
        }


        if (transaccion.getTipo() ==
                TipoTransaccion.RETIRO) {

            return validarRetiro(
                    identificacionCliente,
                    transaccion
            );
        }


        if (transaccion.getTipo() ==
                TipoTransaccion.TRANSFERENCIA) {

            return validarTransferencia(
                    identificacionCliente,
                    transaccion
            );
        }


        if (transaccion.getTipo() ==
                TipoTransaccion.GASTO) {

            return validarGasto(
                    identificacionCliente,
                    transaccion
            );
        }


        return false;
    }


    // ---------------------------------------------------------
    // DEPÓSITO
    // ---------------------------------------------------------

    private boolean validarDeposito(
            String identificacionCliente,
            Transaccion transaccion) {

        if (!transaccion
                .getIdentificacionOrigen()
                .equals(
                        identificacionCliente
                )) {

            return false;
        }


        if (transaccion
                .getIdentificacionDestino()
                != null) {

            return false;
        }


        if (transaccion
                .getMonederoDestino()
                != null) {

            return false;
        }


        if (transaccion
                .getCategoriaGasto()
                != null) {

            return false;
        }


        int puntosEsperados =
                calcularPuntosEsperados(
                        TipoTransaccion.DEPOSITO,
                        transaccion.getMonto()
                );


        return transaccion
                .getPuntosGenerados()
                == puntosEsperados;
    }


    // ---------------------------------------------------------
    // RETIRO
    // ---------------------------------------------------------

    private boolean validarRetiro(
            String identificacionCliente,
            Transaccion transaccion) {

        if (!transaccion
                .getIdentificacionOrigen()
                .equals(
                        identificacionCliente
                )) {

            return false;
        }


        if (transaccion
                .getIdentificacionDestino()
                != null) {

            return false;
        }


        if (transaccion
                .getMonederoDestino()
                != null) {

            return false;
        }


        if (transaccion
                .getCategoriaGasto()
                != null) {

            return false;
        }


        int puntosEsperados =
                calcularPuntosEsperados(
                        TipoTransaccion.RETIRO,
                        transaccion.getMonto()
                );


        return transaccion
                .getPuntosGenerados()
                == puntosEsperados;
    }


    // ---------------------------------------------------------
    // TRANSFERENCIA
    // ---------------------------------------------------------

    private boolean validarTransferencia(
            String identificacionCliente,
            Transaccion transaccion) {

        if (transaccion
                .getIdentificacionDestino()
                == null ||
                transaccion
                        .getIdentificacionDestino()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        if (transaccion
                .getMonederoDestino()
                == null ||
                transaccion
                        .getMonederoDestino()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        /*
         * La transferencia debe pertenecer
         * al historial del origen o del destino.
         */
        boolean perteneceAlCliente =
                identificacionCliente.equals(
                        transaccion
                                .getIdentificacionOrigen()
                ) ||
                        identificacionCliente.equals(
                                transaccion
                                        .getIdentificacionDestino()
                        );


        if (!perteneceAlCliente) {
            return false;
        }


        /*
         * No se puede transferir exactamente
         * al mismo monedero de origen.
         */
        boolean mismaCuenta =
                transaccion
                        .getIdentificacionOrigen()
                        .equals(
                                transaccion
                                        .getIdentificacionDestino()
                        ) &&
                        transaccion
                                .getMonederoOrigen()
                                .equals(
                                        transaccion
                                                .getMonederoDestino()
                                );


        if (mismaCuenta) {
            return false;
        }


        if (transaccion
                .getCategoriaGasto()
                != null) {

            return false;
        }


        int puntosEsperados =
                calcularPuntosEsperados(
                        TipoTransaccion.TRANSFERENCIA,
                        transaccion.getMonto()
                );


        return transaccion
                .getPuntosGenerados()
                == puntosEsperados;
    }


    // ---------------------------------------------------------
    // GASTO
    // ---------------------------------------------------------

    private boolean validarGasto(
            String identificacionCliente,
            Transaccion transaccion) {

        if (!transaccion
                .getIdentificacionOrigen()
                .equals(
                        identificacionCliente
                )) {

            return false;
        }


        if (transaccion
                .getIdentificacionDestino()
                != null) {

            return false;
        }


        if (transaccion
                .getMonederoDestino()
                != null) {

            return false;
        }


        if (transaccion
                .getCategoriaGasto()
                == null) {

            return false;
        }


        /*
         * El enunciado no establece
         * puntos para gastos.
         */
        return transaccion
                .getPuntosGenerados()
                == 0;
    }


    // ---------------------------------------------------------
    // CALCULAR PUNTOS ESPERADOS
    // ---------------------------------------------------------

    private int calcularPuntosEsperados(
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
    // CONTAR TRANSACCIONES VÁLIDAS
    // ---------------------------------------------------------

    public int contarTransaccionesValidas(
            Cliente cliente) {

        if (cliente == null) {
            return 0;
        }


        return contarValidasRecursivo(
                cliente.getIdentificacion(),
                cliente
                        .obtenerHistorialTransacciones(),
                0
        );
    }


    private int contarValidasRecursivo(
            String identificacionCliente,
            List<Transaccion> historial,
            int indice) {

        /*
         * Caso base.
         */
        if (indice ==
                historial.size()) {

            return 0;
        }


        Transaccion actual =
                historial.get(
                        indice
                );


        int valorActual = 0;


        if (transaccionValidaParaCliente(
                identificacionCliente,
                actual
        )) {

            valorActual = 1;
        }


        /*
         * Resultado actual +
         * resultado del resto del historial.
         */
        return valorActual +
                contarValidasRecursivo(
                        identificacionCliente,
                        historial,
                        indice + 1
                );
    }


    // ---------------------------------------------------------
    // BUSCAR PRIMERA TRANSACCIÓN INVÁLIDA
    // ---------------------------------------------------------

    public Transaccion
    buscarPrimeraTransaccionInvalida(
            Cliente cliente) {

        if (cliente == null) {
            return null;
        }


        return buscarInvalidaRecursivo(
                cliente.getIdentificacion(),
                cliente
                        .obtenerHistorialTransacciones(),
                0
        );
    }


    private Transaccion
    buscarInvalidaRecursivo(
            String identificacionCliente,
            List<Transaccion> historial,
            int indice) {

        /*
         * Caso base:
         * no se encontró ninguna inválida.
         */
        if (indice ==
                historial.size()) {

            return null;
        }


        Transaccion actual =
                historial.get(
                        indice
                );


        if (!transaccionValidaParaCliente(
                identificacionCliente,
                actual
        )) {

            return actual;
        }


        return buscarInvalidaRecursivo(
                identificacionCliente,
                historial,
                indice + 1
        );
    }
}