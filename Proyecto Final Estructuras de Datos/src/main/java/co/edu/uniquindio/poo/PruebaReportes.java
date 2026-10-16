package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.ReporteCliente;
import co.edu.uniquindio.poo.model.ReporteSistema;

public class PruebaReportes {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "        PRUEBAS DE REPORTES"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        // -----------------------------------------------------
        // CLIENTES
        // -----------------------------------------------------

        sistema.registrarCliente(
                new Cliente(
                        "C001",
                        "Federico"
                )
        );


        sistema.registrarCliente(
                new Cliente(
                        "C002",
                        "Laura"
                )
        );


        // -----------------------------------------------------
        // MONEDEROS
        // -----------------------------------------------------

        sistema.crearMonedero(
                "C001",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        sistema.crearMonedero(
                "C002",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        // -----------------------------------------------------
        // TRANSACCIONES
        // -----------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                1000
        );


        sistema.transferir(
                "C001",
                "M001",
                "C002",
                "M001",
                300
        );


        sistema.realizarGasto(
                "C001",
                "M001",
                100,
                CategoriaGasto.ALIMENTACION
        );


        // -----------------------------------------------------
        // REPORTE CLIENTE
        // -----------------------------------------------------

        ReporteCliente reporteCliente =
                sistema.generarReporteCliente(
                        "C001"
                );


        verificar(
                reporteCliente != null,
                "Generar reporte del cliente"
        );


        verificar(
                reporteCliente
                        .getCantidadMonederos()
                        == 1,
                "Reporte muestra un monedero"
        );


        verificar(
                reporteCliente
                        .getSaldoTotal()
                        == 600,
                "Saldo total del cliente es 600"
        );


        verificar(
                reporteCliente
                        .getCantidadTransacciones()
                        == 3,
                "Cliente tiene tres transacciones"
        );


        verificar(
                reporteCliente
                        .getTransaccionesActivas()
                        == 3,
                "Tres transacciones están activas"
        );


        verificar(
                reporteCliente
                        .getTransaccionesRevertidas()
                        == 0,
                "No existen transacciones revertidas"
        );


        verificar(
                reporteCliente
                        .getPuntos()
                        == 19,
                "Reporte muestra 19 puntos"
        );


        verificar(
                reporteCliente
                        .getTotalGastos()
                        == 100,
                "Reporte muestra 100 en gastos"
        );


        verificar(
                reporteCliente
                        .getCategoriaMayorGasto()
                        ==
                        CategoriaGasto.ALIMENTACION,
                "Alimentación es categoría principal"
        );


        verificar(
                reporteCliente
                        .getTotalTransferido()
                        == 300,
                "Reporte muestra 300 transferidos"
        );


        verificar(
                reporteCliente
                        .isIntegridadValida(),
                "Reporte indica integridad válida"
        );


        // -----------------------------------------------------
        // REPORTE SISTEMA
        // -----------------------------------------------------

        ReporteSistema reporteSistema =
                sistema.generarReporteSistema();


        verificar(
                reporteSistema
                        .getCantidadClientes()
                        == 2,
                "Sistema tiene dos clientes"
        );


        verificar(
                reporteSistema
                        .getCantidadMonederos()
                        == 2,
                "Sistema tiene dos monederos"
        );


        /*
         * Depósito: +1000
         * Transferencia: no cambia saldo global
         * Gasto: -100
         *
         * Total = 900
         */
        verificar(
                reporteSistema
                        .getSaldoTotalSistema()
                        == 900,
                "Saldo total del sistema es 900"
        );


        /*
         * La transferencia aparece en dos
         * historiales pero debe contarse
         * como una sola transacción.
         */
        verificar(
                reporteSistema
                        .getCantidadTransacciones()
                        == 3,
                "Reporte evita duplicar transferencias"
        );


        verificar(
                reporteSistema
                        .getTotalGastos()
                        == 100,
                "Sistema acumula 100 en gastos"
        );


        verificar(
                reporteSistema
                        .isIntegridadValida(),
                "Sistema tiene integridad válida"
        );


        // -----------------------------------------------------
        // REVERTIR EL GASTO
        // -----------------------------------------------------

        sistema.revertirUltimaTransaccion();


        ReporteCliente actualizado =
                sistema.generarReporteCliente(
                        "C001"
                );


        verificar(
                actualizado
                        .getTransaccionesRevertidas()
                        == 1,
                "Reporte detecta una transacción revertida"
        );


        verificar(
                actualizado
                        .getTransaccionesActivas()
                        == 2,
                "Quedan dos transacciones activas"
        );


        verificar(
                actualizado
                        .getSaldoTotal()
                        == 700,
                "Reversión actualiza saldo del reporte"
        );


        verificar(
                actualizado
                        .getTotalGastos()
                        == 0,
                "Reversión actualiza gastos del reporte"
        );


        reporteSistema =
                sistema.generarReporteSistema();


        verificar(
                reporteSistema
                        .getCantidadTransacciones()
                        == 3,
                "Historial conserva tres transacciones"
        );


        verificar(
                reporteSistema
                        .getTransaccionesRevertidas()
                        == 1,
                "Reporte general detecta reversión"
        );


        verificar(
                reporteSistema
                        .getSaldoTotalSistema()
                        == 1000,
                "Saldo global vuelve a 1000"
        );


        // -----------------------------------------------------
        // MOSTRAR REPORTES
        // -----------------------------------------------------

        System.out.println(
                sistema.generarReporteCliente(
                        "C001"
                )
        );


        System.out.println(
                sistema.generarReporteSistema()
        );


        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "TODAS LAS PRUEBAS TERMINARON CORRECTAMENTE"
        );

        System.out.println(
                "Pruebas superadas: "
                        + pruebasCorrectas
        );

        System.out.println(
                "======================================"
        );
    }


    private static void verificar(
            boolean condicion,
            String descripcion) {

        if (!condicion) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }
}