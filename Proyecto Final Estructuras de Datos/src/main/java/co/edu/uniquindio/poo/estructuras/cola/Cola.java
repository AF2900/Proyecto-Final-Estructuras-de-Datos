package co.edu.uniquindio.poo.estructuras.cola;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Cola<T> {

    private int size;
    private Nodo<T> nodoPrimero;
    private Nodo<T> nodoUltimo;
    private int limite;

    public Cola(int limite) {

        if (limite <= 0) {
            throw new IllegalArgumentException(
                    "El limite de la cola debe ser mayor que cero"
            );
        }

        size = 0;
        nodoPrimero = null;
        nodoUltimo = null;
        this.limite = limite;
    }

    public int getSize() {
        return size;
    }

    public boolean estaVacia() {
        return size == 0;
    }

    public void encolar(T dato) {

        if (size == limite) {
            throw new ColaLlena(
                    "La cola esta llena"
            );
        }

        Nodo<T> nuevoNodo =
                new Nodo<>(dato);

        if (estaVacia()) {

            nodoPrimero = nuevoNodo;
            nodoUltimo = nuevoNodo;

        } else {

            nodoUltimo.setSiguiente(
                    nuevoNodo
            );

            nodoUltimo = nuevoNodo;
        }

        size++;
    }

    public T desencolar() {

        if (estaVacia()) {
            throw new NoSuchElementException(
                    "No hay elementos en la cola"
            );
        }

        T dato =
                nodoPrimero.getDato();

        nodoPrimero =
                nodoPrimero.getSiguiente();

        if (nodoPrimero == null) {
            nodoUltimo = null;
        }

        size--;

        return dato;
    }

    public T verPrimero() {

        if (estaVacia()) {
            return null;
        }

        return nodoPrimero.getDato();
    }

    public boolean eliminar(T dato) {

        if (estaVacia()) {
            return false;
        }

        if (sonIguales(
                nodoPrimero.getDato(),
                dato
        )) {

            desencolar();
            return true;
        }

        Nodo<T> anterior =
                nodoPrimero;

        Nodo<T> actual =
                nodoPrimero.getSiguiente();

        while (actual != null) {

            if (sonIguales(
                    actual.getDato(),
                    dato
            )) {

                anterior.setSiguiente(
                        actual.getSiguiente()
                );

                if (actual == nodoUltimo) {
                    nodoUltimo = anterior;
                }

                size--;

                return true;
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false;
    }

    private boolean sonIguales(
            T dato1,
            T dato2
    ) {

        if (dato1 == null) {
            return dato2 == null;
        }

        return dato1.equals(dato2);
    }

    public List<T> obtenerElementos() {

        List<T> elementos =
                new ArrayList<>();

        Nodo<T> auxiliar =
                nodoPrimero;

        while (auxiliar != null) {

            elementos.add(
                    auxiliar.getDato()
            );

            auxiliar =
                    auxiliar.getSiguiente();
        }

        return elementos;
    }

    public void imprimir() {

        Nodo<T> auxiliar =
                nodoPrimero;

        while (auxiliar != null) {

            System.out.print(
                    auxiliar.getDato()
                            + " -> "
            );

            auxiliar =
                    auxiliar.getSiguiente();
        }
    }
}