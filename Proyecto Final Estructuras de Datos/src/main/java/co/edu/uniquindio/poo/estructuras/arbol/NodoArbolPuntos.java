package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

public class NodoArbolPuntos {

    private Cliente cliente;

    private NodoArbolPuntos izquierdo;

    private NodoArbolPuntos derecho;


    public NodoArbolPuntos(
            Cliente cliente) {

        this.cliente = cliente;
    }


    public Cliente getCliente() {
        return cliente;
    }


    public void setCliente(
            Cliente cliente) {

        this.cliente = cliente;
    }


    public NodoArbolPuntos getIzquierdo() {
        return izquierdo;
    }


    public void setIzquierdo(
            NodoArbolPuntos izquierdo) {

        this.izquierdo = izquierdo;
    }


    public NodoArbolPuntos getDerecho() {
        return derecho;
    }


    public void setDerecho(
            NodoArbolPuntos derecho) {

        this.derecho = derecho;
    }
}