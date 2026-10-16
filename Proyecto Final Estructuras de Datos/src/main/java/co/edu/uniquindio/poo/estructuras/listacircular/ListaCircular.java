package co.edu.uniquindio.poo.estructuras.listacircular;

import java.util.ArrayList;
import java.util.List;

public class ListaCircular<T> {

    private NodoCircular<T> primero;

    private NodoCircular<T> ultimo;

    /*
     * Guarda la posición actual
     * para recorrer circularmente.
     */
    private NodoCircular<T> actual;

    private int tamano;


    public ListaCircular() {

        primero = null;

        ultimo = null;

        actual = null;

        tamano = 0;
    }


    // ---------------------------------------------------------
    // AGREGAR
    // ---------------------------------------------------------

    public void agregar(
            T dato) {

        if (dato == null) {
            return;
        }


        NodoCircular<T> nuevo =
                new NodoCircular<>(
                        dato
                );


        /*
         * Primer elemento.
         */
        if (primero == null) {

            primero =
                    nuevo;

            ultimo =
                    nuevo;

            /*
             * El único nodo apunta
             * hacia sí mismo.
             */
            nuevo.setSiguiente(
                    nuevo
            );

            actual =
                    primero;

        } else {

            /*
             * El nuevo nodo apunta
             * nuevamente al primero.
             */
            nuevo.setSiguiente(
                    primero
            );


            /*
             * El antiguo último
             * apunta al nuevo.
             */
            ultimo.setSiguiente(
                    nuevo
            );


            ultimo =
                    nuevo;
        }


        tamano++;
    }


    // ---------------------------------------------------------
    // RECORRIDO CIRCULAR
    // ---------------------------------------------------------

    public T siguiente() {

        if (actual == null) {
            return null;
        }


        T dato =
                actual.getDato();


        /*
         * Al llegar al último nodo,
         * siguiente vuelve automáticamente
         * al primero.
         */
        actual =
                actual.getSiguiente();


        return dato;
    }


    // ---------------------------------------------------------
    // CONSULTAS
    // ---------------------------------------------------------

    public boolean estaVacia() {

        return tamano == 0;
    }


    public int tamano() {

        return tamano;
    }


    /*
     * Devuelve los elementos una sola vez,
     * aunque internamente la lista sea circular.
     */
    public List<T> obtenerTodos() {

        List<T> resultado =
                new ArrayList<>();


        if (primero == null) {
            return resultado;
        }


        NodoCircular<T> nodo =
                primero;


        for (int i = 0;
             i < tamano;
             i++) {

            resultado.add(
                    nodo.getDato()
            );


            nodo =
                    nodo.getSiguiente();
        }


        return resultado;
    }
}