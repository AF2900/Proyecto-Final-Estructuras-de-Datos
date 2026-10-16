package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorClientes {

    private final Map<String, Cliente>
            clientes;


    public GestorClientes() {

        clientes =
                new HashMap<>();
    }


    // ---------------------------------------------------------
    // CLIENTES
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

        if (cliente.getNombre() == null ||
                cliente
                        .getNombre()
                        .trim()
                        .isEmpty()) {

            return false;
        }

        if (clientes.containsKey(
                cliente.getIdentificacion())) {

            return false;
        }

        clientes.put(
                cliente.getIdentificacion(),
                cliente
        );

        return true;
    }


    public Cliente buscarCliente(
            String identificacion) {

        if (identificacion == null ||
                identificacion
                        .trim()
                        .isEmpty()) {

            return null;
        }

        return clientes.get(
                identificacion
        );
    }


    public List<Cliente>
    obtenerClientes() {

        return new ArrayList<>(
                clientes.values()
        );
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    public boolean crearMonedero(
            String identificacionCliente,
            Monedero monedero) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );

        if (cliente == null) {
            return false;
        }

        return cliente.agregarMonedero(
                monedero
        );
    }


    public Monedero buscarMonedero(
            String identificacionCliente,
            String idMonedero) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );

        if (cliente == null) {
            return null;
        }

        return cliente.buscarMonedero(
                idMonedero
        );
    }
}