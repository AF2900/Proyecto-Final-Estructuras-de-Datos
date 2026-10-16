package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.enums.TipoTransaccion;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

public class PruebaGastos {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "        PRUEBAS DE GASTOS"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        sistema.registrarCliente(
                new Cliente(
                        "C001",
                        "Federico"
                )
        );


        sistema.crearMonedero(
                "C001",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        // -----------------------------------------------------
        // SALDO INICIAL
        // ---------------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                2000
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 2000,
                "Saldo inicial de 2000"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 20,
                "Depósito genera 20 puntos"
        );


        // -----------------------------------------------------
        // PRIMER GASTO
        // ---------------------------------------------------------

        Transaccion gasto1 =
                sistema.realizarGasto(
                        "C001",
                        "M001",
                        300,
                        CategoriaGasto.ALIMENTACION
                );


        verificar(
                gasto1 != null,
                "Registrar gasto de alimentación"
        );


        verificar(
                gasto1.getTipo()
                        == TipoTransaccion.GASTO,
                "Transacción queda como GASTO"
        );


        verificar(
                gasto1.getCategoriaGasto()
                        == CategoriaGasto.ALIMENTACION,
                "Gasto conserva su categoría"
        );


        verificar(
                gasto1.getPuntosGenerados()
                        == 0,
                "Gasto no genera puntos"
        );


        // -----------------------------------------------------
        // MÁS GASTOS
        // ---------------------------------------------------------

        sistema.realizarGasto(
                "C001",
                "M001",
                200,
                CategoriaGasto.TRANSPORTE
        );


        sistema.realizarGasto(
                "C001",
                "M001",
                100,
                CategoriaGasto.ALIMENTACION
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 1400,
                "Los gastos descuentan saldo"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 20,
                "Los gastos no cambian los puntos"
        );


        // -----------------------------------------------------
        // REPORTES
        // ---------------------------------------------------------

        verificar(
                sistema.obtenerTotalGastos(
                        "C001"
                ) == 600,
                "Total gastado es 600"
        );


        verificar(
                sistema.obtenerTotalCategoria(
                        "C001",
                        CategoriaGasto.ALIMENTACION
                ) == 400,
                "Alimentación acumula 400"
        );


        verificar(
                sistema.obtenerTotalCategoria(
                        "C001",
                        CategoriaGasto.TRANSPORTE
                ) == 200,
                "Transporte acumula 200"
        );


        verificar(
                sistema.obtenerCategoriaMayorGasto(
                        "C001"
                ) == CategoriaGasto.ALIMENTACION,
                "Alimentación es la categoría principal"
        );


        // -----------------------------------------------------
        // GRAFO DE CATEGORÍAS
        // ---------------------------------------------------------

        verificar(
                sistema.obtenerMontoTransicionCategorias(
                        CategoriaGasto.ALIMENTACION,
                        CategoriaGasto.TRANSPORTE
                ) == 200,
                "Existe transición Alimentación a Transporte"
        );


        verificar(
                sistema.obtenerCantidadTransicionesCategorias(
                        CategoriaGasto.ALIMENTACION,
                        CategoriaGasto.TRANSPORTE
                ) == 1,
                "Transición Alimentación a Transporte ocurre una vez"
        );


        verificar(
                sistema.obtenerMontoTransicionCategorias(
                        CategoriaGasto.TRANSPORTE,
                        CategoriaGasto.ALIMENTACION
                ) == 100,
                "Existe transición Transporte a Alimentación"
        );


        verificar(
                sistema.obtenerUltimaCategoriaGasto(
                        "C001"
                ) == CategoriaGasto.ALIMENTACION,
                "Última categoría es Alimentación"
        );


        // -----------------------------------------------------
        // REVERSIÓN DEL ÚLTIMO GASTO
        // ---------------------------------------------------------

        Transaccion revertida =
                sistema.revertirUltimaTransaccion();


        verificar(
                revertida != null &&
                        revertida.getTipo()
                                == TipoTransaccion.GASTO,
                "Se revierte el último gasto"
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) == 1500,
                "Reversión devuelve el dinero"
        );


        verificar(
                sistema.obtenerTotalGastos(
                        "C001"
                ) == 500,
                "Total de gastos baja a 500"
        );


        verificar(
                sistema.obtenerTotalCategoria(
                        "C001",
                        CategoriaGasto.ALIMENTACION
                ) == 300,
                "Alimentación vuelve a 300"
        );


        verificar(
                sistema.obtenerMontoTransicionCategorias(
                        CategoriaGasto.TRANSPORTE,
                        CategoriaGasto.ALIMENTACION
                ) == 0,
                "Reversión elimina la última transición"
        );


        verificar(
                sistema.obtenerUltimaCategoriaGasto(
                        "C001"
                ) == CategoriaGasto.TRANSPORTE,
                "Última categoría vuelve a Transporte"
        );


        // -----------------------------------------------------
        // HISTORIAL
        // ---------------------------------------------------------

        verificar(
                sistema.obtenerHistorial(
                        "C001"
                ).size() == 4,
                "Historial conserva depósito y tres gastos"
        );


        verificar(
                sistema.obtenerHistorial(
                                "C001"
                        )
                        .get(3)
                        .isRevertida(),
                "Gasto revertido permanece en historial"
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