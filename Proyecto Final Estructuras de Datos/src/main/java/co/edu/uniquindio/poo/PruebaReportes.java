package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.ReporteCliente;
import co.edu.uniquindio.poo.model.ReporteSistema;

public class PruebaReportes {

    private static int pruebasCorrectas = 0;

    private static final double TOLERANCIA = 0.0001;


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

        /*
         * C001 deposita 1000.
         *
         * Saldo C001 = 1000.
         */
        sistema.depositar(
                "C001",
                "M001",
                1000
        );


        /*
         * C001 transfiere 300 a C002.
         *
         * Comisión:
         * 300 * 1% = 3.
         *
         * C001:
         * 1000 - 300 - 3 = 697.
         *
         * C002:
         * 300.
         */
        sistema.transferir(
                "C001",
                "M001",
                "C002",
                "M001",
                300
        );


        /*
         * C001 realiza un gasto de 100.
         *
         * 697 - 100 = 597.
         */
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


        verificarDouble(
                reporteCliente
                        .getSaldoTotal(),
                597,
                "Saldo total del cliente es 597 incluyendo comisión"
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


        /*
         * Puntos:
         *
         * Depósito 1000:
         * 10 puntos.
         *
         * Transferencia 300:
         * 9 puntos.
         *
         * Gasto:
         * 0 puntos.
         *
         * Total:
         * 19 puntos.
         */
        verificar(
                reporteCliente
                        .getPuntos()
                        == 19,
                "Reporte muestra 19 puntos"
        );


        verificarDouble(
                reporteCliente
                        .getTotalGastos(),
                100,
                "Reporte muestra 100 en gastos"
        );


        verificar(
                reporteCliente
                        .getCategoriaMayorGasto()
                        ==
                        CategoriaGasto.ALIMENTACION,
                "Alimentación es categoría principal"
        );


        verificarDouble(
                reporteCliente
                        .getTotalTransferido(),
                300,
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
         * Dinero ingresado:
         *
         * Depósito:
         * +1000.
         *
         * Transferencia:
         * no cambia el monto transferido
         * dentro del sistema, pero sí sale
         * la comisión de 3.
         *
         * Gasto:
         * -100.
         *
         * Total:
         *
         * 1000 - 3 - 100
         * = 897.
         */
        verificarDouble(
                reporteSistema
                        .getSaldoTotalSistema(),
                897,
                "Saldo total del sistema es 897"
        );


        /*
         * La transferencia aparece en los
         * historiales de origen y destino,
         * pero el reporte general debe
         * contarla una sola vez.
         */
        verificar(
                reporteSistema
                        .getCantidadTransacciones()
                        == 3,
                "Reporte evita duplicar transferencias"
        );


        verificarDouble(
                reporteSistema
                        .getTotalGastos(),
                100,
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


        /*
         * Antes de revertir:
         * 597.
         *
         * Se devuelve el gasto:
         * +100.
         *
         * Resultado:
         * 697.
         *
         * La comisión de transferencia
         * continúa cobrada porque esa
         * transferencia no fue revertida.
         */
        verificarDouble(
                actualizado
                        .getSaldoTotal(),
                697,
                "Reversión actualiza saldo del reporte"
        );


        verificarDouble(
                actualizado
                        .getTotalGastos(),
                0,
                "Reversión actualiza gastos del reporte"
        );


        // -----------------------------------------------------
        // REPORTE SISTEMA DESPUÉS DE REVERTIR
        // -----------------------------------------------------

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


        /*
         * Después de revertir el gasto:
         *
         * Depósito:
         * +1000.
         *
         * Comisión de transferencia:
         * -3.
         *
         * El gasto ya fue revertido.
         *
         * Total:
         * 997.
         */
        verificarDouble(
                reporteSistema
                        .getSaldoTotalSistema(),
                997,
                "Saldo global queda en 997 después de revertir gasto"
        );


        verificarDouble(
                reporteSistema
                        .getTotalGastos(),
                0,
                "Reporte general elimina gasto revertido"
        );


        verificar(
                reporteSistema
                        .isIntegridadValida(),
                "Sistema continúa con integridad válida"
        );


        // -----------------------------------------------------
        // MOSTRAR REPORTES
        // -----------------------------------------------------

        System.out.println(
                "\n========== REPORTE CLIENTE =========="
        );


        System.out.println(
                sistema.generarReporteCliente(
                        "C001"
                )
        );


        System.out.println(
                "\n========== REPORTE SISTEMA =========="
        );


        System.out.println(
                sistema.generarReporteSistema()
        );


        // -----------------------------------------------------
        // RESULTADO FINAL
        // -----------------------------------------------------

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


    // ---------------------------------------------------------
    // VERIFICAR BOOLEAN
    // ---------------------------------------------------------

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


    // ---------------------------------------------------------
    // VERIFICAR DOUBLE
    // ---------------------------------------------------------

    private static void verificarDouble(
            double actual,
            double esperado,
            String descripcion) {

        if (Math.abs(
                actual - esperado
        ) > TOLERANCIA) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
                            + " - esperado: "
                            + esperado
                            + ", actual: "
                            + actual
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }
}