package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

import java.time.LocalDateTime;

public class GestorTransacciones {

    private static final double
            LIMITE_SALDO_BAJO = 100.0;


    /*
     * Decisión de implementación:
     *
     * tanto los retiros como las
     * transferencias tienen una
     * comisión base del 1%.
     */
    private static final double
            PORCENTAJE_COMISION_RETIRO = 0.01;


    private static final double
            PORCENTAJE_COMISION_TRANSFERENCIA = 0.01;


    /*
     * El beneficio de 100 puntos
     * reduce un 10% la comisión
     * calculada para transferencias.
     */
    private static final double
            DESCUENTO_COMISION_TRANSFERENCIA = 0.10;


    private final GestorClientes
            gestorClientes;

    private final GestorReversiones
            gestorReversiones;

    private final GestorPuntos
            gestorPuntos;

    private final GestorNotificaciones
            gestorNotificaciones;

    private final GestorGrafos
            gestorGrafos;

    private final GestorPatronesGasto
            gestorPatronesGasto;

    private final GestorBeneficios
            gestorBeneficios;

    private int consecutivo;


    public GestorTransacciones(
            GestorClientes gestorClientes,
            GestorReversiones gestorReversiones,
            GestorPuntos gestorPuntos,
            GestorNotificaciones gestorNotificaciones,
            GestorGrafos gestorGrafos,
            GestorPatronesGasto gestorPatronesGasto,
            GestorBeneficios gestorBeneficios) {

        this.gestorClientes =
                gestorClientes;

        this.gestorReversiones =
                gestorReversiones;

        this.gestorPuntos =
                gestorPuntos;

        this.gestorNotificaciones =
                gestorNotificaciones;

        this.gestorGrafos =
                gestorGrafos;

        this.gestorPatronesGasto =
                gestorPatronesGasto;

        this.gestorBeneficios =
                gestorBeneficios;

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
                        puntos,
                        0
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


        /*
         * Primero se determina la comisión.
         *
         * Si el cliente tiene vigente
         * RETIROS_SIN_CARGO, será cero.
         */
        double comision =
                calcularComisionRetiro(
                        identificacionCliente,
                        monto
                );


        double totalADescontar =
                redondearDosDecimales(
                        monto + comision
                );


        /*
         * La operación solo se realiza
         * si existe saldo suficiente para:
         *
         * monto + comisión.
         */
        if (!monedero.retirar(
                totalADescontar
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
                        puntos,
                        comision
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


        /*
         * Calcular comisión de transferencia.
         *
         * Si el beneficio de 100 puntos
         * está activo, la comisión se
         * reduce un 10%.
         */
        double comision =
                calcularComisionTransferencia(
                        identificacionOrigen,
                        monto
                );


        double totalADescontar =
                redondearDosDecimales(
                        monto + comision
                );


        /*
         * El origen debe poder pagar
         * tanto el monto enviado como
         * la comisión.
         */
        if (!origen.retirar(
                totalADescontar
        )) {

            return null;
        }


        /*
         * El destino recibe únicamente
         * el monto de la transferencia.
         *
         * La comisión no se transfiere.
         */
        if (!destino.depositar(
                monto
        )) {

            /*
             * Restauración defensiva.
             *
             * Si falla el depósito en
             * destino, se devuelve
             * monto + comisión al origen.
             */
            origen.depositar(
                    totalADescontar
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
                        puntos,
                        comision
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


        /*
         * Si los clientes son distintos,
         * la misma transacción también
         * queda en el historial del destino.
         */
        if (clienteOrigen != clienteDestino) {

            clienteDestino
                    .registrarTransaccion(
                            transaccion
                    );
        }


        /*
         * El grafo registra el monto
         * realmente transferido.
         *
         * La comisión no representa una
         * transferencia hacia el destino.
         */
        gestorGrafos
                .registrarTransferencia(
                        identificacionOrigen,
                        monederoOrigen,
                        identificacionDestino,
                        monederoDestino,
                        monto
                );


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
    // GASTO
    // ---------------------------------------------------------

    public Transaccion realizarGasto(
            String identificacionCliente,
            String idMonedero,
            double monto,
            CategoriaGasto categoria) {

        if (monto <= 0 ||
                categoria == null) {

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


        CategoriaGasto categoriaAnterior =
                gestorPatronesGasto
                        .registrarGasto(
                                identificacionCliente,
                                categoria,
                                monto
                        );


        /*
         * El enunciado no define puntos
         * para los gastos.
         *
         * Tampoco se definió una comisión
         * para esta operación.
         */
        Transaccion transaccion =
                new Transaccion(
                        generarCodigo(),
                        LocalDateTime.now(),
                        TipoTransaccion.GASTO,
                        monto,
                        identificacionCliente,
                        idMonedero,
                        null,
                        null,
                        0,
                        0,
                        categoria,
                        categoriaAnterior
                );


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
    // COMISIÓN DE RETIRO
    // ---------------------------------------------------------

    private double calcularComisionRetiro(
            String identificacionCliente,
            double monto) {

        /*
         * Beneficio de 500 puntos:
         *
         * un mes sin cargos por retiros.
         */
        if (gestorBeneficios
                .tieneBeneficioActivo(
                        identificacionCliente,
                        TipoBeneficio
                                .RETIROS_SIN_CARGO
                )) {

            return 0;
        }


        return redondearDosDecimales(
                monto *
                        PORCENTAJE_COMISION_RETIRO
        );
    }


    // ---------------------------------------------------------
    // COMISIÓN DE TRANSFERENCIA
    // ---------------------------------------------------------

    private double calcularComisionTransferencia(
            String identificacionCliente,
            double monto) {

        double comisionBase =
                redondearDosDecimales(
                        monto *
                                PORCENTAJE_COMISION_TRANSFERENCIA
                );


        /*
         * Beneficio de 100 puntos:
         *
         * reducción del 10% sobre
         * la comisión calculada.
         */
        if (gestorBeneficios
                .tieneBeneficioActivo(
                        identificacionCliente,
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA
                )) {

            return redondearDosDecimales(
                    comisionBase *
                            (1 -
                                    DESCUENTO_COMISION_TRANSFERENCIA)
            );
        }


        return comisionBase;
    }


    // ---------------------------------------------------------
    // SALDO BAJO
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

        /*
         * Los puntos se calculan sobre
         * el monto de la operación,
         * no sobre la comisión.
         */
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
    // REDONDEO MONETARIO
    // ---------------------------------------------------------

    private double redondearDosDecimales(
            double valor) {

        return Math.round(
                valor * 100.0
        ) / 100.0;
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