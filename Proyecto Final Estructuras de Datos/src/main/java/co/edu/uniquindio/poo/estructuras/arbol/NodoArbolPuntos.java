package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

public class NodoArbolPuntos {

    private Cliente elemento;

    private NodoArbolPuntos hijoIzquierdo;

    private NodoArbolPuntos hijoDerecho;

    private NodoArbolPuntos padre;


    public NodoArbolPuntos(
            Cliente elemento,
            NodoArbolPuntos padre) {

        this.elemento =
                elemento;

        this.padre =
                padre;

        hijoIzquierdo =
                null;

        hijoDerecho =
                null;
    }


    public Cliente getElemento() {

        return elemento;
    }


    public void setElemento(
            Cliente elemento) {

        this.elemento =
                elemento;
    }


    public NodoArbolPuntos
    getHijoIzquierdo() {

        return hijoIzquierdo;
    }


    public void setHijoIzquierdo(
            NodoArbolPuntos hijoIzquierdo) {

        this.hijoIzquierdo =
                hijoIzquierdo;
    }


    public NodoArbolPuntos
    getHijoDerecho() {

        return hijoDerecho;
    }


    public void setHijoDerecho(
            NodoArbolPuntos hijoDerecho) {

        this.hijoDerecho =
                hijoDerecho;
    }


    public NodoArbolPuntos getPadre() {

        return padre;
    }


    public void setPadre(
            NodoArbolPuntos padre) {

        this.padre =
                padre;
    }
}