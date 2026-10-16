package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioPuntos {

    private NodoArbolPuntos raiz;


    public ArbolBinarioPuntos() {

        raiz = null;
    }


    // ---------------------------------------------------------
    // INSERTAR
    // ---------------------------------------------------------

    public void insertar(
            Cliente cliente) {

        if (cliente == null) {
            return;
        }

        raiz =
                insertarRecursivo(
                        raiz,
                        cliente
                );
    }


    private NodoArbolPuntos
    insertarRecursivo(
            NodoArbolPuntos nodo,
            Cliente cliente) {

        if (nodo == null) {

            return new NodoArbolPuntos(
                    cliente
            );
        }


        int comparacion =
                comparar(
                        cliente,
                        nodo.getCliente()
                );


        if (comparacion < 0) {

            nodo.setIzquierdo(
                    insertarRecursivo(
                            nodo.getIzquierdo(),
                            cliente
                    )
            );

        } else if (comparacion > 0) {

            nodo.setDerecho(
                    insertarRecursivo(
                            nodo.getDerecho(),
                            cliente
                    )
            );
        }


        return nodo;
    }


    // ---------------------------------------------------------
    // ELIMINAR
    // ---------------------------------------------------------

    public void eliminar(
            Cliente cliente) {

        if (cliente == null) {
            return;
        }

        raiz =
                eliminarRecursivo(
                        raiz,
                        cliente
                );
    }


    private NodoArbolPuntos
    eliminarRecursivo(
            NodoArbolPuntos nodo,
            Cliente cliente) {

        if (nodo == null) {
            return null;
        }


        int comparacion =
                comparar(
                        cliente,
                        nodo.getCliente()
                );


        if (comparacion < 0) {

            nodo.setIzquierdo(
                    eliminarRecursivo(
                            nodo.getIzquierdo(),
                            cliente
                    )
            );

        } else if (comparacion > 0) {

            nodo.setDerecho(
                    eliminarRecursivo(
                            nodo.getDerecho(),
                            cliente
                    )
            );

        } else {

            // Sin hijo izquierdo.
            if (nodo.getIzquierdo() == null) {

                return nodo.getDerecho();
            }


            // Sin hijo derecho.
            if (nodo.getDerecho() == null) {

                return nodo.getIzquierdo();
            }


            /*
             * Si tiene dos hijos, se busca el
             * menor elemento del subárbol derecho.
             */
            NodoArbolPuntos sucesor =
                    buscarMinimo(
                            nodo.getDerecho()
                    );


            nodo.setCliente(
                    sucesor.getCliente()
            );


            nodo.setDerecho(
                    eliminarRecursivo(
                            nodo.getDerecho(),
                            sucesor.getCliente()
                    )
            );
        }


        return nodo;
    }


    private NodoArbolPuntos buscarMinimo(
            NodoArbolPuntos nodo) {

        NodoArbolPuntos actual =
                nodo;


        while (actual.getIzquierdo()
                != null) {

            actual =
                    actual.getIzquierdo();
        }


        return actual;
    }


    // ---------------------------------------------------------
    // RECORRIDO INORDEN
    // ---------------------------------------------------------

    public List<Cliente>
    obtenerEnOrden() {

        List<Cliente> resultado =
                new ArrayList<>();


        recorridoInOrden(
                raiz,
                resultado
        );


        return resultado;
    }


    private void recorridoInOrden(
            NodoArbolPuntos nodo,
            List<Cliente> resultado) {

        if (nodo == null) {
            return;
        }


        recorridoInOrden(
                nodo.getIzquierdo(),
                resultado
        );


        resultado.add(
                nodo.getCliente()
        );


        recorridoInOrden(
                nodo.getDerecho(),
                resultado
        );
    }


    // ---------------------------------------------------------
    // ALTURA
    // ---------------------------------------------------------

    public int altura() {

        return calcularAltura(
                raiz
        );
    }


    private int calcularAltura(
            NodoArbolPuntos nodo) {

        if (nodo == null) {
            return 0;
        }


        int alturaIzquierda =
                calcularAltura(
                        nodo.getIzquierdo()
                );


        int alturaDerecha =
                calcularAltura(
                        nodo.getDerecho()
                );


        return 1 +
                Math.max(
                        alturaIzquierda,
                        alturaDerecha
                );
    }


    // ---------------------------------------------------------
    // COMPARACIÓN
    // ---------------------------------------------------------

    private int comparar(
            Cliente cliente1,
            Cliente cliente2) {

        int comparacionPuntos =
                Integer.compare(
                        cliente1.getPuntos(),
                        cliente2.getPuntos()
                );


        if (comparacionPuntos != 0) {

            return comparacionPuntos;
        }


        /*
         * Dos clientes pueden tener los mismos puntos.
         *
         * La identificación se utiliza como
         * criterio de desempate.
         */
        return cliente1
                .getIdentificacion()
                .compareTo(
                        cliente2
                                .getIdentificacion()
                );
    }
}