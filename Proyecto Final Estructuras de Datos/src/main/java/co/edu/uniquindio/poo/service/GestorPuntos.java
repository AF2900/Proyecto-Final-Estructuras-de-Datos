package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.estructuras.arbol.ArbolAVLClientes;
import co.edu.uniquindio.poo.estructuras.arbol.ArbolBinarioPuntos;
import co.edu.uniquindio.poo.model.Cliente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorPuntos {

    /*
     * Permite consultar rápidamente los puntos
     * de un cliente mediante su identificación.
     */
    private final Map<String, Integer>
            puntosPorCliente;


    /*
     * Árbol binario de búsqueda sin balancear.
     */
    private final ArbolBinarioPuntos
            arbolPuntos;


    /*
     * Árbol AVL que mantiene los clientes
     * balanceados según sus puntos.
     */
    private final ArbolAVLClientes
            arbolRangos;


    public GestorPuntos() {

        puntosPorCliente =
                new HashMap<>();


        arbolPuntos =
                new ArbolBinarioPuntos();


        arbolRangos =
                new ArbolAVLClientes();
    }


    // ---------------------------------------------------------
    // REGISTRAR CLIENTE
    // ---------------------------------------------------------

    public boolean registrarCliente(
            Cliente cliente) {

        if (cliente == null) {
            return false;
        }


        if (cliente.getIdentificacion() == null ||
                cliente
                        .getIdentificacion()
                        .trim()
                        .isEmpty()) {

            return false;
        }


        if (puntosPorCliente.containsKey(
                cliente.getIdentificacion())) {

            return false;
        }


        puntosPorCliente.put(
                cliente.getIdentificacion(),
                cliente.getPuntos()
        );


        arbolPuntos.insertar(
                cliente
        );


        arbolRangos.insertar(
                cliente
        );


        return true;
    }


    // ---------------------------------------------------------
    // AGREGAR PUNTOS
    // ---------------------------------------------------------

    public boolean agregarPuntos(
            Cliente cliente,
            int puntos) {

        if (!clienteValido(
                cliente
        )) {

            return false;
        }


        if (puntos <= 0) {
            return false;
        }


        /*
         * Primero se elimina usando los
         * puntos anteriores como clave.
         */
        arbolPuntos.eliminar(
                cliente
        );


        arbolRangos.eliminar(
                cliente
        );


        /*
         * Después se modifica el cliente.
         */
        cliente.agregarPuntos(
                puntos
        );


        puntosPorCliente.put(
                cliente.getIdentificacion(),
                cliente.getPuntos()
        );


        /*
         * Finalmente se vuelve a insertar
         * con su nueva cantidad de puntos.
         */
        arbolPuntos.insertar(
                cliente
        );


        arbolRangos.insertar(
                cliente
        );


        return true;
    }


    // ---------------------------------------------------------
    // RESTAR PUNTOS
    // ---------------------------------------------------------

    public boolean restarPuntos(
            Cliente cliente,
            int puntos) {

        if (!clienteValido(
                cliente
        )) {

            return false;
        }


        if (puntos <= 0) {
            return false;
        }


        arbolPuntos.eliminar(
                cliente
        );


        arbolRangos.eliminar(
                cliente
        );


        cliente.restarPuntos(
                puntos
        );


        puntosPorCliente.put(
                cliente.getIdentificacion(),
                cliente.getPuntos()
        );


        arbolPuntos.insertar(
                cliente
        );


        arbolRangos.insertar(
                cliente
        );


        return true;
    }


    // ---------------------------------------------------------
    // CONSULTAS
    // ---------------------------------------------------------

    public Integer consultarPuntos(
            String identificacion) {

        if (identificacion == null ||
                identificacion
                        .trim()
                        .isEmpty()) {

            return null;
        }


        return puntosPorCliente.get(
                identificacion
        );
    }


    public List<Cliente>
    obtenerClientesOrdenadosPorPuntosBST() {

        return arbolPuntos
                .obtenerEnOrden();
    }


    public List<Cliente>
    obtenerClientesOrdenadosPorPuntosAVL() {

        return arbolRangos
                .obtenerEnOrden();
    }


    public int obtenerAlturaBST() {

        return arbolPuntos.altura();
    }


    public int obtenerAlturaAVL() {

        return arbolRangos.altura();
    }


    // ---------------------------------------------------------
    // VALIDACIÓN
    // ---------------------------------------------------------

    private boolean clienteValido(
            Cliente cliente) {

        if (cliente == null) {
            return false;
        }


        return puntosPorCliente
                .containsKey(
                        cliente.getIdentificacion()
                );
    }
}