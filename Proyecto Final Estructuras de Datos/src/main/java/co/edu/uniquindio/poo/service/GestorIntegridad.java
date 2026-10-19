package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Transaccion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorIntegridad {

    private static final double
            PORCENTAJE_COMISION = 0.01;

    private static final double
            DESCUENTO_TRANSFERENCIA = 0.10;

    private static final double
            TOLERANCIA = 0.0001;


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

        if (indice ==
                historial.size()) {

            return true;
        }


        Transaccion transaccion =
                historial.get(
                        indice
                );


        if (!transaccionValidaParaCliente(
                identificacionCliente,
                transaccion
        )) {

            return false;
        }


        if (codigos.contains(
                transaccion.getCodigo()
        )) {

            return false;
        }


        codigos.add(
                transaccion.getCodigo()
        );


        return verificarHistorialRecursivo(
                identificacionCliente,
                historial,
                indice + 1,
                codigos
        );
    }


    // ---------------------------------------------------------
    // VALIDACIÓN GENERAL
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


        if (transaccion
                .getPuntosGenerados()
                < 0) {

            return false;
        }


        /*
         * Una comisión nunca puede
         * ser negativa.
         */
        if (transaccion.getComision()
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


        /*
         * Los depósitos no cobran comisión.
         */
        if (!sonIguales(
                transaccion.getComision(),
                0
        )) {

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


        /*
         * Para un retiro existen dos
         * valores válidos de comisión:
         *
         * 1. Comisión normal del 1%.
         * 2. Cero si tiene el beneficio
         *    de retiros sin cargo.
         */
        double comisionNormal =
                calcularComisionBase(
                        transaccion.getMonto()
                );


        boolean comisionValida =
                sonIguales(
                        transaccion.getComision(),
                        comisionNormal
                ) ||
                        sonIguales(
                                transaccion.getComision(),
                                0
                        );


        if (!comisionValida) {
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


        /*
         * Una transferencia puede tener:
         *
         * Comisión normal del 1%.
         *
         * o
         *
         * Comisión reducida un 10%
         * por el beneficio.
         */
        double comisionNormal =
                calcularComisionBase(
                        transaccion.getMonto()
                );


        double comisionConDescuento =
                redondearDosDecimales(
                        comisionNormal *
                                (1 -
                                        DESCUENTO_TRANSFERENCIA)
                );


        boolean comisionValida =
                sonIguales(
                        transaccion.getComision(),
                        comisionNormal
                ) ||
                        sonIguales(
                                transaccion.getComision(),
                                comisionConDescuento
                        );


        if (!comisionValida) {
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


        if (!sonIguales(
                transaccion.getComision(),
                0
        )) {

            return false;
        }


        return transaccion
                .getPuntosGenerados()
                == 0;
    }


    // ---------------------------------------------------------
    // CALCULAR COMISIÓN BASE
    // ---------------------------------------------------------

    private double calcularComisionBase(
            double monto) {

        return redondearDosDecimales(
                monto *
                        PORCENTAJE_COMISION
        );
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


        Set<String> codigos =
                new HashSet<>();


        return contarValidasRecursivo(
                cliente.getIdentificacion(),
                cliente
                        .obtenerHistorialTransacciones(),
                0,
                codigos
        );
    }


    private int contarValidasRecursivo(
            String identificacionCliente,
            List<Transaccion> historial,
            int indice,
            Set<String> codigos) {

        if (indice ==
                historial.size()) {

            return 0;
        }


        Transaccion actual =
                historial.get(
                        indice
                );


        boolean valida =
                transaccionValidaParaCliente(
                        identificacionCliente,
                        actual
                );


        boolean codigoRepetido =
                valida &&
                        codigos.contains(
                                actual.getCodigo()
                        );


        int valorActual =
                valida &&
                        !codigoRepetido
                        ? 1
                        : 0;


        if (valida &&
                !codigoRepetido) {

            codigos.add(
                    actual.getCodigo()
            );
        }


        return valorActual +
                contarValidasRecursivo(
                        identificacionCliente,
                        historial,
                        indice + 1,
                        codigos
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


        Set<String> codigos =
                new HashSet<>();


        return buscarInvalidaRecursivo(
                cliente.getIdentificacion(),
                cliente
                        .obtenerHistorialTransacciones(),
                0,
                codigos
        );
    }


    private Transaccion
    buscarInvalidaRecursivo(
            String identificacionCliente,
            List<Transaccion> historial,
            int indice,
            Set<String> codigos) {

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


        if (codigos.contains(
                actual.getCodigo()
        )) {

            return actual;
        }


        codigos.add(
                actual.getCodigo()
        );


        return buscarInvalidaRecursivo(
                identificacionCliente,
                historial,
                indice + 1,
                codigos
        );
    }


    // ---------------------------------------------------------
    // COMPARAR DOUBLE
    // ---------------------------------------------------------

    private boolean sonIguales(
            double valor1,
            double valor2) {

        return Math.abs(
                valor1 - valor2
        ) <= TOLERANCIA;
    }


    // ---------------------------------------------------------
    // REDONDEAR
    // ---------------------------------------------------------

    private double redondearDosDecimales(
            double valor) {

        return Math.round(
                valor * 100.0
        ) / 100.0;
    }
}