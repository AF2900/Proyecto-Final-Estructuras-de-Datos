package co.edu.uniquindio.poo.estructuras.grafo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GrafoDirigido {

    /*
     * Lista de adyacencia.
     *
     * Cada vértice contiene las aristas
     * que salen desde él.
     */
    private final Map<String, List<AristaGrafo>>
            adyacencias;


    public GrafoDirigido() {

        adyacencias =
                new HashMap<>();
    }


    // ---------------------------------------------------------
    // VÉRTICES
    // ---------------------------------------------------------

    public boolean agregarVertice(
            String vertice) {

        if (vertice == null ||
                vertice.trim().isEmpty()) {

            return false;
        }


        if (adyacencias.containsKey(
                vertice
        )) {

            return false;
        }


        adyacencias.put(
                vertice,
                new ArrayList<>()
        );


        return true;
    }


    public boolean contieneVertice(
            String vertice) {

        return vertice != null &&
                adyacencias.containsKey(
                        vertice
                );
    }


    public int cantidadVertices() {

        return adyacencias.size();
    }


    // ---------------------------------------------------------
    // CONEXIONES
    // ---------------------------------------------------------

    public boolean registrarConexion(
            String origen,
            String destino,
            double monto) {

        if (origen == null ||
                destino == null ||
                monto <= 0) {

            return false;
        }


        if (!adyacencias.containsKey(
                origen
        )) {

            agregarVertice(
                    origen
            );
        }


        if (!adyacencias.containsKey(
                destino
        )) {

            agregarVertice(
                    destino
            );
        }


        AristaGrafo arista =
                buscarArista(
                        origen,
                        destino
                );


        /*
         * Si ya existe una relación entre
         * los dos vértices, se acumulan
         * el monto y la cantidad.
         */
        if (arista != null) {

            arista.agregarTransferencia(
                    monto
            );

            return true;
        }


        adyacencias
                .get(origen)
                .add(
                        new AristaGrafo(
                                destino,
                                monto
                        )
                );


        return true;
    }


    // ---------------------------------------------------------
    // REVERTIR CONEXIÓN
    // ---------------------------------------------------------

    public boolean revertirConexion(
            String origen,
            String destino,
            double monto) {

        AristaGrafo arista =
                buscarArista(
                        origen,
                        destino
                );


        if (arista == null) {
            return false;
        }


        if (!arista.retirarTransferencia(
                monto
        )) {

            return false;
        }


        /*
         * Si ya no existen transferencias
         * entre los vértices, se elimina
         * completamente la arista.
         */
        if (arista
                .getCantidadTransacciones()
                == 0) {

            adyacencias
                    .get(origen)
                    .remove(
                            arista
                    );
        }


        return true;
    }


    // ---------------------------------------------------------
    // CONSULTAR ARISTA
    // ---------------------------------------------------------

    private AristaGrafo buscarArista(
            String origen,
            String destino) {

        List<AristaGrafo> aristas =
                adyacencias.get(
                        origen
                );


        if (aristas == null) {
            return null;
        }


        for (AristaGrafo arista :
                aristas) {

            if (arista
                    .getDestino()
                    .equals(
                            destino
                    )) {

                return arista;
            }
        }


        return null;
    }


    public double obtenerMonto(
            String origen,
            String destino) {

        AristaGrafo arista =
                buscarArista(
                        origen,
                        destino
                );


        if (arista == null) {
            return 0;
        }


        return arista
                .getMontoTotal();
    }


    public int obtenerCantidadTransacciones(
            String origen,
            String destino) {

        AristaGrafo arista =
                buscarArista(
                        origen,
                        destino
                );


        if (arista == null) {
            return 0;
        }


        return arista
                .getCantidadTransacciones();
    }


    // ---------------------------------------------------------
    // ADYACENTES
    // ---------------------------------------------------------

    public List<AristaGrafo>
    obtenerAdyacentes(
            String origen) {

        List<AristaGrafo> aristas =
                adyacencias.get(
                        origen
                );


        if (aristas == null) {

            return new ArrayList<>();
        }


        return new ArrayList<>(
                aristas
        );
    }


    // ---------------------------------------------------------
    // MONTO TOTAL DESDE UN VÉRTICE
    // ---------------------------------------------------------

    public double obtenerMontoTotalDesde(
            String origen) {

        List<AristaGrafo> aristas =
                adyacencias.get(
                        origen
                );


        if (aristas == null) {
            return 0;
        }


        double total = 0;


        for (AristaGrafo arista :
                aristas) {

            total +=
                    arista.getMontoTotal();
        }


        return total;
    }


    // ---------------------------------------------------------
    // DESTINO PRINCIPAL
    // ---------------------------------------------------------

    public String obtenerDestinoMayorMonto(
            String origen) {

        List<AristaGrafo> aristas =
                adyacencias.get(
                        origen
                );


        if (aristas == null ||
                aristas.isEmpty()) {

            return null;
        }


        AristaGrafo mayor =
                aristas.get(0);


        for (AristaGrafo arista :
                aristas) {

            if (arista.getMontoTotal()
                    > mayor.getMontoTotal()) {

                mayor =
                        arista;
            }
        }


        return mayor.getDestino();
    }


    // ---------------------------------------------------------
    // BFS
    // ---------------------------------------------------------

    public List<String>
    recorridoAnchura(
            String inicio) {

        List<String> resultado =
                new ArrayList<>();


        if (!adyacencias.containsKey(
                inicio
        )) {

            return resultado;
        }


        Queue<String> cola =
                new ArrayDeque<>();


        Set<String> visitados =
                new HashSet<>();


        cola.offer(
                inicio
        );


        visitados.add(
                inicio
        );


        while (!cola.isEmpty()) {

            String actual =
                    cola.poll();


            resultado.add(
                    actual
            );


            for (AristaGrafo arista :
                    obtenerAdyacentes(
                            actual
                    )) {

                String destino =
                        arista.getDestino();


                if (!visitados.contains(
                        destino
                )) {

                    visitados.add(
                            destino
                    );


                    cola.offer(
                            destino
                    );
                }
            }
        }


        return resultado;
    }


    // ---------------------------------------------------------
    // CAMINO ENTRE VÉRTICES
    // ---------------------------------------------------------

    public boolean existeCamino(
            String origen,
            String destino) {

        if (origen == null ||
                destino == null) {

            return false;
        }


        List<String> recorrido =
                recorridoAnchura(
                        origen
                );


        return recorrido.contains(
                destino
        );
    }
}