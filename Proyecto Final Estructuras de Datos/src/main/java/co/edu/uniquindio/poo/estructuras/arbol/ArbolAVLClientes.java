package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ArbolAVLClientes {

    private NodoAVLCliente raiz;


    public ArbolAVLClientes() {

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


    private NodoAVLCliente
    insertarRecursivo(
            NodoAVLCliente nodo,
            Cliente cliente) {

        if (nodo == null) {

            return new NodoAVLCliente(
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

        } else {

            return nodo;
        }


        actualizarAltura(
                nodo
        );


        return balancear(
                nodo
        );
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


    private NodoAVLCliente
    eliminarRecursivo(
            NodoAVLCliente nodo,
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

            if (nodo.getIzquierdo() == null) {

                return nodo.getDerecho();
            }


            if (nodo.getDerecho() == null) {

                return nodo.getIzquierdo();
            }


            NodoAVLCliente sucesor =
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


        actualizarAltura(
                nodo
        );


        return balancear(
                nodo
        );
    }


    // ---------------------------------------------------------
    // BALANCE
    // ---------------------------------------------------------

    private NodoAVLCliente balancear(
            NodoAVLCliente nodo) {

        int factorBalance =
                obtenerFactorBalance(
                        nodo
                );


        // Izquierda - izquierda.
        if (factorBalance > 1 &&
                obtenerFactorBalance(
                        nodo.getIzquierdo()
                ) >= 0) {

            return rotacionDerecha(
                    nodo
            );
        }


        // Derecha - derecha.
        if (factorBalance < -1 &&
                obtenerFactorBalance(
                        nodo.getDerecho()
                ) <= 0) {

            return rotacionIzquierda(
                    nodo
            );
        }


        // Izquierda - derecha.
        if (factorBalance > 1 &&
                obtenerFactorBalance(
                        nodo.getIzquierdo()
                ) < 0) {

            nodo.setIzquierdo(
                    rotacionIzquierda(
                            nodo.getIzquierdo()
                    )
            );


            return rotacionDerecha(
                    nodo
            );
        }


        // Derecha - izquierda.
        if (factorBalance < -1 &&
                obtenerFactorBalance(
                        nodo.getDerecho()
                ) > 0) {

            nodo.setDerecho(
                    rotacionDerecha(
                            nodo.getDerecho()
                    )
            );


            return rotacionIzquierda(
                    nodo
            );
        }


        return nodo;
    }


    private NodoAVLCliente rotacionDerecha(
            NodoAVLCliente nodo) {

        NodoAVLCliente nuevaRaiz =
                nodo.getIzquierdo();


        NodoAVLCliente temporal =
                nuevaRaiz.getDerecho();


        nuevaRaiz.setDerecho(
                nodo
        );


        nodo.setIzquierdo(
                temporal
        );


        actualizarAltura(
                nodo
        );


        actualizarAltura(
                nuevaRaiz
        );


        return nuevaRaiz;
    }


    private NodoAVLCliente rotacionIzquierda(
            NodoAVLCliente nodo) {

        NodoAVLCliente nuevaRaiz =
                nodo.getDerecho();


        NodoAVLCliente temporal =
                nuevaRaiz.getIzquierdo();


        nuevaRaiz.setIzquierdo(
                nodo
        );


        nodo.setDerecho(
                temporal
        );


        actualizarAltura(
                nodo
        );


        actualizarAltura(
                nuevaRaiz
        );


        return nuevaRaiz;
    }


    // ---------------------------------------------------------
    // ALTURA
    // ---------------------------------------------------------

    private int obtenerAltura(
            NodoAVLCliente nodo) {

        if (nodo == null) {
            return 0;
        }


        return nodo.getAltura();
    }


    private void actualizarAltura(
            NodoAVLCliente nodo) {

        if (nodo == null) {
            return;
        }


        nodo.setAltura(
                1 +
                        Math.max(
                                obtenerAltura(
                                        nodo.getIzquierdo()
                                ),
                                obtenerAltura(
                                        nodo.getDerecho()
                                )
                        )
        );
    }


    private int obtenerFactorBalance(
            NodoAVLCliente nodo) {

        if (nodo == null) {
            return 0;
        }


        return obtenerAltura(
                nodo.getIzquierdo()
        ) -
                obtenerAltura(
                        nodo.getDerecho()
                );
    }


    public int altura() {

        return obtenerAltura(
                raiz
        );
    }


    // ---------------------------------------------------------
    // MÍNIMO
    // ---------------------------------------------------------

    private NodoAVLCliente buscarMinimo(
            NodoAVLCliente nodo) {

        NodoAVLCliente actual =
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
            NodoAVLCliente nodo,
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


        return cliente1
                .getIdentificacion()
                .compareTo(
                        cliente2
                                .getIdentificacion()
                );
    }
}