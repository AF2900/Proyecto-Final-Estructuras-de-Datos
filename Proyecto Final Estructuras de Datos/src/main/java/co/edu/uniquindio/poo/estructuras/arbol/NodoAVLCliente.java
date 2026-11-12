package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

public class NodoAVLCliente {

    private Cliente elemento;

    private NodoAVLCliente hijoIzquierdo;

    private NodoAVLCliente hijoDerecho;

    private NodoAVLCliente padre;

    private int altura;

    private int fe;


    public NodoAVLCliente(
            Cliente elemento,
            NodoAVLCliente padre) {

        this.elemento =
                elemento;

        this.padre =
                padre;

        this.hijoIzquierdo =
                null;

        this.hijoDerecho =
                null;

        this.altura =
                1;

        this.fe =
                0;
    }


    public Cliente getElemento() {

        return elemento;
    }


    public void setElemento(
            Cliente elemento) {

        this.elemento =
                elemento;
    }


    public NodoAVLCliente
    getHijoIzquierdo() {

        return hijoIzquierdo;
    }


    public void setHijoIzquierdo(
            NodoAVLCliente hijoIzquierdo) {

        this.hijoIzquierdo =
                hijoIzquierdo;
    }


    public NodoAVLCliente
    getHijoDerecho() {

        return hijoDerecho;
    }


    public void setHijoDerecho(
            NodoAVLCliente hijoDerecho) {

        this.hijoDerecho =
                hijoDerecho;
    }


    public NodoAVLCliente getPadre() {

        return padre;
    }


    public void setPadre(
            NodoAVLCliente padre) {

        this.padre =
                padre;
    }


    public int getAltura() {

        return altura;
    }


    public void setAltura(
            int altura) {

        this.altura =
                altura;
    }


    public int getFe() {

        return fe;
    }


    public void setFe(
            int fe) {

        this.fe =
                fe;
    }
}