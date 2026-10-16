package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.estructuras.grafo.AristaGrafo;
import co.edu.uniquindio.poo.estructuras.grafo.GrafoDirigido;

import java.util.List;

public class GestorGrafos {

    /*
     * Grafo para analizar transferencias
     * entre diferentes clientes.
     */
    private final GrafoDirigido
            grafoClientes;


    /*
     * Grafo para analizar relaciones
     * entre monederos.
     */
    private final GrafoDirigido
            grafoMonederos;


    public GestorGrafos() {

        grafoClientes =
                new GrafoDirigido();


        grafoMonederos =
                new GrafoDirigido();
    }


    // ---------------------------------------------------------
    // CLIENTES
    // ---------------------------------------------------------

    public void registrarCliente(
            String identificacion) {

        grafoClientes
                .agregarVertice(
                        identificacion
                );
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    public void registrarMonedero(
            String identificacionCliente,
            String idMonedero) {

        String vertice =
                crearVerticeMonedero(
                        identificacionCliente,
                        idMonedero
                );


        grafoMonederos
                .agregarVertice(
                        vertice
                );
    }


    // ---------------------------------------------------------
    // REGISTRAR TRANSFERENCIA
    // ---------------------------------------------------------

    public void registrarTransferencia(
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto) {

        /*
         * Si son clientes diferentes,
         * se registra la relación en
         * el grafo de clientes.
         */
        if (!identificacionOrigen
                .equals(
                        identificacionDestino
                )) {

            grafoClientes
                    .registrarConexion(
                            identificacionOrigen,
                            identificacionDestino,
                            monto
                    );
        }


        /*
         * Toda transferencia genera
         * una relación entre monederos,
         * incluso si pertenecen al
         * mismo cliente.
         */
        grafoMonederos
                .registrarConexion(
                        crearVerticeMonedero(
                                identificacionOrigen,
                                monederoOrigen
                        ),
                        crearVerticeMonedero(
                                identificacionDestino,
                                monederoDestino
                        ),
                        monto
                );
    }


    // ---------------------------------------------------------
    // REVERTIR TRANSFERENCIA
    // ---------------------------------------------------------

    public void revertirTransferencia(
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto) {

        if (!identificacionOrigen
                .equals(
                        identificacionDestino
                )) {

            grafoClientes
                    .revertirConexion(
                            identificacionOrigen,
                            identificacionDestino,
                            monto
                    );
        }


        grafoMonederos
                .revertirConexion(
                        crearVerticeMonedero(
                                identificacionOrigen,
                                monederoOrigen
                        ),
                        crearVerticeMonedero(
                                identificacionDestino,
                                monederoDestino
                        ),
                        monto
                );
    }


    // ---------------------------------------------------------
    // CONSULTAS CLIENTES
    // ---------------------------------------------------------

    public List<AristaGrafo>
    obtenerRelacionesCliente(
            String identificacionCliente) {

        return grafoClientes
                .obtenerAdyacentes(
                        identificacionCliente
                );
    }


    public double obtenerMontoTransferido(
            String origen,
            String destino) {

        return grafoClientes
                .obtenerMonto(
                        origen,
                        destino
                );
    }


    public int obtenerCantidadTransferencias(
            String origen,
            String destino) {

        return grafoClientes
                .obtenerCantidadTransacciones(
                        origen,
                        destino
                );
    }


    public double obtenerTotalTransferidoPorCliente(
            String identificacionCliente) {

        return grafoClientes
                .obtenerMontoTotalDesde(
                        identificacionCliente
                );
    }


    public String obtenerPrincipalDestinoCliente(
            String identificacionCliente) {

        return grafoClientes
                .obtenerDestinoMayorMonto(
                        identificacionCliente
                );
    }


    public List<String>
    recorridoClientes(
            String identificacionInicial) {

        return grafoClientes
                .recorridoAnchura(
                        identificacionInicial
                );
    }


    public boolean existeRelacionEntreClientes(
            String origen,
            String destino) {

        return grafoClientes
                .existeCamino(
                        origen,
                        destino
                );
    }


    // ---------------------------------------------------------
    // CONSULTAS MONEDEROS
    // ---------------------------------------------------------

    public List<AristaGrafo>
    obtenerRelacionesMonedero(
            String identificacionCliente,
            String idMonedero) {

        return grafoMonederos
                .obtenerAdyacentes(
                        crearVerticeMonedero(
                                identificacionCliente,
                                idMonedero
                        )
                );
    }


    public double obtenerMontoEntreMonederos(
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino) {

        return grafoMonederos
                .obtenerMonto(
                        crearVerticeMonedero(
                                identificacionOrigen,
                                monederoOrigen
                        ),
                        crearVerticeMonedero(
                                identificacionDestino,
                                monederoDestino
                        )
                );
    }


    // ---------------------------------------------------------
    // IDENTIFICADOR MONEDERO
    // ---------------------------------------------------------

    private String crearVerticeMonedero(
            String identificacionCliente,
            String idMonedero) {

        return identificacionCliente
                + ":"
                + idMonedero;
    }
}