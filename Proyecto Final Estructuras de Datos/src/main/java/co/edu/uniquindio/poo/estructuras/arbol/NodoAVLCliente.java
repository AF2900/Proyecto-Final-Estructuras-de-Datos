package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

public class NodoAVLCliente {

    private Cliente cliente;

    private NodoAVLCliente izquierdo;

    private NodoAVLCliente derecho;

    private int altura;


    public NodoAVLCliente(
            Cliente cliente) {

        this.cliente =
                cliente;

        this.altura = 1;
    }


    public Cliente getCliente() {
        return cliente;
    }


    public void setCliente(
            Cliente cliente) {

        this.cliente = cliente;
    }


    public NodoAVLCliente getIzquierdo() {
        return izquierdo;
    }


    public void setIzquierdo(
            NodoAVLCliente izquierdo) {

        this.izquierdo = izquierdo;
    }


    public NodoAVLCliente getDerecho() {
        return derecho;
    }


    public void setDerecho(
            NodoAVLCliente derecho) {

        this.derecho = derecho;
    }


    public int getAltura() {
        return altura;
    }


    public void setAltura(
            int altura) {

        this.altura = altura;
    }
}