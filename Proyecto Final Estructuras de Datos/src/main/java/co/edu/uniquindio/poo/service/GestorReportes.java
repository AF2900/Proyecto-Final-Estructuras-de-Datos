package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.ReporteCliente;
import co.edu.uniquindio.poo.model.ReporteSistema;
import co.edu.uniquindio.poo.model.Transaccion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorReportes {

    private final GestorClientes
            gestorClientes;

    private final GestorPuntos
            gestorPuntos;

    private final GestorPatronesGasto
            gestorPatronesGasto;

    private final GestorGrafos
            gestorGrafos;

    private final GestorBeneficios
            gestorBeneficios;

    private final GestorNotificaciones
            gestorNotificaciones;

    private final GestorIntegridad
            gestorIntegridad;


    public GestorReportes(
            GestorClientes gestorClientes,
            GestorPuntos gestorPuntos,
            GestorPatronesGasto gestorPatronesGasto,
            GestorGrafos gestorGrafos,
            GestorBeneficios gestorBeneficios,
            GestorNotificaciones gestorNotificaciones,
            GestorIntegridad gestorIntegridad) {

        this.gestorClientes =
                gestorClientes;

        this.gestorPuntos =
                gestorPuntos;

        this.gestorPatronesGasto =
                gestorPatronesGasto;

        this.gestorGrafos =
                gestorGrafos;

        this.gestorBeneficios =
                gestorBeneficios;

        this.gestorNotificaciones =
                gestorNotificaciones;

        this.gestorIntegridad =
                gestorIntegridad;
    }


    // ---------------------------------------------------------
    // REPORTE DE UN CLIENTE
    // ---------------------------------------------------------

    public ReporteCliente generarReporteCliente(
            String identificacionCliente) {

        Cliente cliente =
                gestorClientes.buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return null;
        }


        List<Monedero> monederos =
                cliente.obtenerMonederos();


        List<Transaccion> historial =
                cliente
                        .obtenerHistorialTransacciones();


        double saldoTotal =
                calcularSaldoTotal(
                        monederos
                );


        int revertidas =
                contarRevertidas(
                        historial
                );


        int activas =
                historial.size()
                        - revertidas;


        Integer puntos =
                gestorPuntos
                        .consultarPuntos(
                                identificacionCliente
                        );


        double totalGastos =
                gestorPatronesGasto
                        .obtenerTotalGastos(
                                identificacionCliente
                        );


        CategoriaGasto categoriaMayor =
                gestorPatronesGasto
                        .obtenerCategoriaMayorGasto(
                                identificacionCliente
                        );


        double totalTransferido =
                gestorGrafos
                        .obtenerTotalTransferidoPorCliente(
                                identificacionCliente
                        );


        int cantidadBeneficios =
                gestorBeneficios
                        .cantidadBeneficios(
                                identificacionCliente
                        );


        int cantidadNotificaciones =
                gestorNotificaciones
                        .cantidadNotificaciones(
                                identificacionCliente
                        );


        boolean integridad =
                gestorIntegridad
                        .verificarIntegridadCliente(
                                cliente
                        );


        return new ReporteCliente(
                cliente.getIdentificacion(),
                cliente.getNombre(),
                monederos.size(),
                saldoTotal,
                historial.size(),
                activas,
                revertidas,
                puntos == null
                        ? 0
                        : puntos,
                cliente.getRango(),
                totalGastos,
                categoriaMayor,
                totalTransferido,
                cantidadBeneficios,
                cantidadNotificaciones,
                integridad
        );
    }


    // ---------------------------------------------------------
    // REPORTE GENERAL
    // ---------------------------------------------------------

    public ReporteSistema generarReporteSistema() {

        List<Cliente> clientes =
                gestorClientes
                        .obtenerClientes();


        int cantidadMonederos =
                0;


        double saldoTotal =
                0;


        double totalGastos =
                0;


        int cantidadBeneficios =
                0;


        int cantidadNotificaciones =
                0;


        /*
         * Se utiliza un HashMap por código
         * porque una transferencia aparece
         * tanto en el historial del origen
         * como en el historial del destino.
         *
         * Así se cuenta una sola vez.
         */
        Map<String, Transaccion>
                transaccionesUnicas =
                new HashMap<>();


        for (Cliente cliente :
                clientes) {


            List<Monedero> monederos =
                    cliente
                            .obtenerMonederos();


            cantidadMonederos +=
                    monederos.size();


            saldoTotal +=
                    calcularSaldoTotal(
                            monederos
                    );


            totalGastos +=
                    gestorPatronesGasto
                            .obtenerTotalGastos(
                                    cliente
                                            .getIdentificacion()
                            );


            cantidadBeneficios +=
                    gestorBeneficios
                            .cantidadBeneficios(
                                    cliente
                                            .getIdentificacion()
                            );


            cantidadNotificaciones +=
                    gestorNotificaciones
                            .cantidadNotificaciones(
                                    cliente
                                            .getIdentificacion()
                            );


            for (Transaccion transaccion :
                    cliente
                            .obtenerHistorialTransacciones()) {

                transaccionesUnicas
                        .putIfAbsent(
                                transaccion.getCodigo(),
                                transaccion
                        );
            }
        }


        int transaccionesRevertidas =
                0;


        for (Transaccion transaccion :
                transaccionesUnicas.values()) {

            if (transaccion.isRevertida()) {

                transaccionesRevertidas++;
            }
        }


        int transaccionesActivas =
                transaccionesUnicas.size()
                        - transaccionesRevertidas;


        boolean integridad =
                gestorIntegridad
                        .verificarIntegridadSistema(
                                clientes
                        );


        return new ReporteSistema(
                clientes.size(),
                cantidadMonederos,
                saldoTotal,
                transaccionesUnicas.size(),
                transaccionesActivas,
                transaccionesRevertidas,
                totalGastos,
                cantidadBeneficios,
                cantidadNotificaciones,
                integridad
        );
    }


    // ---------------------------------------------------------
    // SALDO TOTAL
    // ---------------------------------------------------------

    private double calcularSaldoTotal(
            List<Monedero> monederos) {

        double total =
                0;


        for (Monedero monedero :
                monederos) {

            total +=
                    monedero.getSaldo();
        }


        return total;
    }


    // ---------------------------------------------------------
    // TRANSACCIONES REVERTIDAS
    // ---------------------------------------------------------

    private int contarRevertidas(
            List<Transaccion> historial) {

        int cantidad =
                0;


        for (Transaccion transaccion :
                historial) {

            if (transaccion.isRevertida()) {

                cantidad++;
            }
        }


        return cantidad;
    }
}