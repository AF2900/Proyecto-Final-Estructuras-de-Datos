package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.estructuras.grafo.AristaGrafo;
import co.edu.uniquindio.poo.estructuras.grafo.GrafoDirigido;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorPatronesGasto {

    /*
     * Guarda los gastos acumulados
     * por cliente y por categoría.
     */
    private final Map<String,
            Map<CategoriaGasto, Double>>
            gastosPorCliente;


    /*
     * Guarda cuál fue la última categoría
     * utilizada por cada cliente.
     */
    private final Map<String, CategoriaGasto>
            ultimaCategoriaPorCliente;


    /*
     * Grafo dirigido de categorías.
     *
     * Ejemplo:
     *
     * ALIMENTACION -> TRANSPORTE
     * TRANSPORTE   -> ENTRETENIMIENTO
     */
    private final GrafoDirigido
            grafoCategorias;


    public GestorPatronesGasto() {

        gastosPorCliente =
                new HashMap<>();


        ultimaCategoriaPorCliente =
                new HashMap<>();


        grafoCategorias =
                new GrafoDirigido();


        registrarCategorias();
    }


    // ---------------------------------------------------------
    // REGISTRAR CATEGORÍAS
    // ---------------------------------------------------------

    private void registrarCategorias() {

        for (CategoriaGasto categoria :
                CategoriaGasto.values()) {

            grafoCategorias
                    .agregarVertice(
                            categoria.name()
                    );
        }
    }


    // ---------------------------------------------------------
    // REGISTRAR CLIENTE
    // ---------------------------------------------------------

    public void registrarCliente(
            String identificacionCliente) {

        if (identificacionCliente == null ||
                identificacionCliente
                        .trim()
                        .isEmpty()) {

            return;
        }


        gastosPorCliente.putIfAbsent(
                identificacionCliente,
                new EnumMap<>(
                        CategoriaGasto.class
                )
        );
    }


    // ---------------------------------------------------------
    // REGISTRAR GASTO
    // ---------------------------------------------------------

    public CategoriaGasto registrarGasto(
            String identificacionCliente,
            CategoriaGasto categoria,
            double monto) {

        if (identificacionCliente == null ||
                categoria == null ||
                monto <= 0) {

            return null;
        }


        registrarCliente(
                identificacionCliente
        );


        Map<CategoriaGasto, Double>
                gastosCliente =
                gastosPorCliente.get(
                        identificacionCliente
                );


        double acumulado =
                gastosCliente.getOrDefault(
                        categoria,
                        0.0
                );


        gastosCliente.put(
                categoria,
                acumulado + monto
        );


        CategoriaGasto anterior =
                ultimaCategoriaPorCliente.get(
                        identificacionCliente
                );


        /*
         * Si ya existía una categoría anterior,
         * se registra la transición en el grafo.
         */
        if (anterior != null) {

            grafoCategorias
                    .registrarConexion(
                            anterior.name(),
                            categoria.name(),
                            monto
                    );
        }


        ultimaCategoriaPorCliente.put(
                identificacionCliente,
                categoria
        );


        return anterior;
    }


    // ---------------------------------------------------------
    // REVERTIR GASTO
    // ---------------------------------------------------------

    public boolean revertirGasto(
            String identificacionCliente,
            CategoriaGasto categoria,
            CategoriaGasto categoriaAnterior,
            double monto) {

        if (identificacionCliente == null ||
                categoria == null ||
                monto <= 0) {

            return false;
        }


        Map<CategoriaGasto, Double>
                gastosCliente =
                gastosPorCliente.get(
                        identificacionCliente
                );


        if (gastosCliente == null) {
            return false;
        }


        double acumulado =
                gastosCliente.getOrDefault(
                        categoria,
                        0.0
                );


        if (acumulado < monto) {
            return false;
        }


        double nuevoTotal =
                acumulado - monto;


        if (nuevoTotal == 0) {

            gastosCliente.remove(
                    categoria
            );

        } else {

            gastosCliente.put(
                    categoria,
                    nuevoTotal
            );
        }


        /*
         * Si existió transición desde una
         * categoría anterior, se revierte.
         */
        if (categoriaAnterior != null) {

            grafoCategorias
                    .revertirConexion(
                            categoriaAnterior.name(),
                            categoria.name(),
                            monto
                    );
        }


        /*
         * Como las reversiones funcionan LIFO,
         * la última categoría vuelve a ser
         * la categoría anterior.
         */
        if (categoriaAnterior == null) {

            ultimaCategoriaPorCliente.remove(
                    identificacionCliente
            );

        } else {

            ultimaCategoriaPorCliente.put(
                    identificacionCliente,
                    categoriaAnterior
            );
        }


        return true;
    }


    // ---------------------------------------------------------
    // TOTAL POR CATEGORÍA
    // ---------------------------------------------------------

    public double obtenerTotalCategoria(
            String identificacionCliente,
            CategoriaGasto categoria) {

        Map<CategoriaGasto, Double>
                gastosCliente =
                gastosPorCliente.get(
                        identificacionCliente
                );


        if (gastosCliente == null ||
                categoria == null) {

            return 0;
        }


        return gastosCliente
                .getOrDefault(
                        categoria,
                        0.0
                );
    }


    // ---------------------------------------------------------
    // TOTAL GENERAL
    // ---------------------------------------------------------

    public double obtenerTotalGastos(
            String identificacionCliente) {

        Map<CategoriaGasto, Double>
                gastosCliente =
                gastosPorCliente.get(
                        identificacionCliente
                );


        if (gastosCliente == null) {
            return 0;
        }


        double total = 0;


        for (double monto :
                gastosCliente.values()) {

            total += monto;
        }


        return total;
    }


    // ---------------------------------------------------------
    // CATEGORÍA PRINCIPAL
    // ---------------------------------------------------------

    public CategoriaGasto
    obtenerCategoriaMayorGasto(
            String identificacionCliente) {

        Map<CategoriaGasto, Double>
                gastosCliente =
                gastosPorCliente.get(
                        identificacionCliente
                );


        if (gastosCliente == null ||
                gastosCliente.isEmpty()) {

            return null;
        }


        CategoriaGasto mayorCategoria =
                null;


        double mayorMonto =
                -1;


        for (Map.Entry<CategoriaGasto, Double>
                entrada :
                gastosCliente.entrySet()) {

            if (entrada.getValue()
                    > mayorMonto) {

                mayorMonto =
                        entrada.getValue();


                mayorCategoria =
                        entrada.getKey();
            }
        }


        return mayorCategoria;
    }


    // ---------------------------------------------------------
    // ÚLTIMA CATEGORÍA
    // ---------------------------------------------------------

    public CategoriaGasto obtenerUltimaCategoria(
            String identificacionCliente) {

        return ultimaCategoriaPorCliente
                .get(
                        identificacionCliente
                );
    }


    // ---------------------------------------------------------
    // GRAFO DE CATEGORÍAS
    // ---------------------------------------------------------

    public double obtenerMontoTransicion(
            CategoriaGasto origen,
            CategoriaGasto destino) {

        if (origen == null ||
                destino == null) {

            return 0;
        }


        return grafoCategorias
                .obtenerMonto(
                        origen.name(),
                        destino.name()
                );
    }


    public int obtenerCantidadTransiciones(
            CategoriaGasto origen,
            CategoriaGasto destino) {

        if (origen == null ||
                destino == null) {

            return 0;
        }


        return grafoCategorias
                .obtenerCantidadTransacciones(
                        origen.name(),
                        destino.name()
                );
    }


    public List<AristaGrafo>
    obtenerRelacionesCategoria(
            CategoriaGasto categoria) {

        if (categoria == null) {

            return List.of();
        }


        return grafoCategorias
                .obtenerAdyacentes(
                        categoria.name()
                );
    }


    public String obtenerSiguienteCategoriaPrincipal(
            CategoriaGasto categoria) {

        if (categoria == null) {
            return null;
        }


        return grafoCategorias
                .obtenerDestinoMayorMonto(
                        categoria.name()
                );
    }
}