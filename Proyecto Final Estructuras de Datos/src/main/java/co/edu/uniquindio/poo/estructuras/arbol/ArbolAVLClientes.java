package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ArbolAVLClientes {

    private NodoAVLCliente raiz;

    private int peso;


    public ArbolAVLClientes() {

        raiz =
                null;

        peso =
                0;
    }


    public NodoAVLCliente getRaiz() {

        return raiz;
    }


    public int getPeso() {

        return peso;
    }


    public boolean estaVacio() {

        return raiz == null;
    }


    // ---------------------------------------------------------
    // INSERTAR
    // ---------------------------------------------------------

    public void insertar(
            Cliente elemento) {

        if (elemento == null) {

            return;
        }


        raiz =
                insertar(
                        raiz,
                        elemento,
                        null
                );


        if (raiz != null) {

            raiz.setPadre(
                    null
            );
        }
    }


    private NodoAVLCliente insertar(
            NodoAVLCliente raizAux,
            Cliente elemento,
            NodoAVLCliente padre) {

        if (raizAux == null) {

            peso++;


            return new NodoAVLCliente(
                    elemento,
                    padre
            );
        }


        int comparacion =
                comparar(
                        raizAux.getElemento(),
                        elemento
                );


        if (comparacion > 0) {

            NodoAVLCliente nuevoIzquierdo =
                    insertar(
                            raizAux
                                    .getHijoIzquierdo(),
                            elemento,
                            raizAux
                    );


            raizAux.setHijoIzquierdo(
                    nuevoIzquierdo
            );


            if (nuevoIzquierdo != null) {

                nuevoIzquierdo.setPadre(
                        raizAux
                );
            }

        } else if (comparacion < 0) {

            NodoAVLCliente nuevoDerecho =
                    insertar(
                            raizAux
                                    .getHijoDerecho(),
                            elemento,
                            raizAux
                    );


            raizAux.setHijoDerecho(
                    nuevoDerecho
            );


            if (nuevoDerecho != null) {

                nuevoDerecho.setPadre(
                        raizAux
                );
            }

        } else {

            return raizAux;
        }


        actualizarDatos(
                raizAux
        );


        return balancear(
                raizAux
        );
    }


    // ---------------------------------------------------------
    // BUSCAR
    // ---------------------------------------------------------

    public boolean buscarNodo(
            Cliente buscado) {

        if (buscado == null) {

            return false;
        }


        return buscarNodo(
                raiz,
                buscado
        ) != null;
    }


    private NodoAVLCliente buscarNodo(
            NodoAVLCliente raizAux,
            Cliente buscado) {

        if (raizAux == null) {

            return null;
        }


        int comparacion =
                comparar(
                        raizAux.getElemento(),
                        buscado
                );


        if (comparacion == 0) {

            return raizAux;
        }


        if (comparacion > 0) {

            return buscarNodo(
                    raizAux
                            .getHijoIzquierdo(),
                    buscado
            );
        }


        return buscarNodo(
                raizAux
                        .getHijoDerecho(),
                buscado
        );
    }


    // ---------------------------------------------------------
    // ELIMINAR
    // ---------------------------------------------------------

    public void eliminar(
            Cliente elemento) {

        if (elemento == null) {

            return;
        }


        if (!buscarNodo(
                elemento
        )) {

            return;
        }


        raiz =
                eliminar(
                        raiz,
                        elemento
                );


        peso--;


        if (raiz != null) {

            raiz.setPadre(
                    null
            );
        }
    }


    private NodoAVLCliente eliminar(
            NodoAVLCliente raizAux,
            Cliente elemento) {

        if (raizAux == null) {

            return null;
        }


        int comparacion =
                comparar(
                        raizAux.getElemento(),
                        elemento
                );


        if (comparacion > 0) {

            NodoAVLCliente nuevoIzquierdo =
                    eliminar(
                            raizAux
                                    .getHijoIzquierdo(),
                            elemento
                    );


            raizAux.setHijoIzquierdo(
                    nuevoIzquierdo
            );


            if (nuevoIzquierdo != null) {

                nuevoIzquierdo.setPadre(
                        raizAux
                );
            }

        } else if (comparacion < 0) {

            NodoAVLCliente nuevoDerecho =
                    eliminar(
                            raizAux
                                    .getHijoDerecho(),
                            elemento
                    );


            raizAux.setHijoDerecho(
                    nuevoDerecho
            );


            if (nuevoDerecho != null) {

                nuevoDerecho.setPadre(
                        raizAux
                );
            }

        } else {

            /*
             * CASO 1:
             *
             * No tiene hijo izquierdo.
             */
            if (raizAux
                    .getHijoIzquierdo()
                    == null) {

                NodoAVLCliente derecho =
                        raizAux
                                .getHijoDerecho();


                if (derecho != null) {

                    derecho.setPadre(
                            raizAux.getPadre()
                    );
                }


                return derecho;
            }


            /*
             * CASO 2:
             *
             * No tiene hijo derecho.
             */
            if (raizAux
                    .getHijoDerecho()
                    == null) {

                NodoAVLCliente izquierdo =
                        raizAux
                                .getHijoIzquierdo();


                if (izquierdo != null) {

                    izquierdo.setPadre(
                            raizAux.getPadre()
                    );
                }


                return izquierdo;
            }


            /*
             * CASO 3:
             *
             * Tiene dos hijos.
             *
             * Se utiliza el mayor elemento
             * del subárbol izquierdo,
             * como trabaja el profesor.
             */
            NodoAVLCliente mayorIzquierda =
                    encontrarMayor(
                            raizAux
                                    .getHijoIzquierdo()
                    );


            raizAux.setElemento(
                    mayorIzquierda
                            .getElemento()
            );


            NodoAVLCliente nuevoIzquierdo =
                    eliminar(
                            raizAux
                                    .getHijoIzquierdo(),
                            mayorIzquierda
                                    .getElemento()
                    );


            raizAux.setHijoIzquierdo(
                    nuevoIzquierdo
            );


            if (nuevoIzquierdo != null) {

                nuevoIzquierdo.setPadre(
                        raizAux
                );
            }
        }


        /*
         * Después de eliminar,
         * actualizamos altura y FE.
         */
        actualizarDatos(
                raizAux
        );


        /*
         * Finalmente balanceamos
         * el subárbol.
         */
        return balancear(
                raizAux
        );
    }


    // ---------------------------------------------------------
    // BALANCEAR
    // ---------------------------------------------------------

    private NodoAVLCliente balancear(
            NodoAVLCliente nodo) {

        if (nodo == null) {

            return null;
        }


        actualizarDatos(
                nodo
        );


        int factorEquilibrio =
                nodo.getFe();


        /*
         * IZQUIERDA - IZQUIERDA
         *
         * FE < -1 significa que el
         * árbol está cargado hacia
         * la izquierda.
         */
        if (factorEquilibrio < -1 &&
                obtenerFactorEquilibrio(
                        nodo.getHijoIzquierdo()
                ) <= 0) {

            return rotacionDerecha(
                    nodo
            );
        }


        /*
         * IZQUIERDA - DERECHA
         */
        if (factorEquilibrio < -1 &&
                obtenerFactorEquilibrio(
                        nodo.getHijoIzquierdo()
                ) > 0) {

            NodoAVLCliente nuevoIzquierdo =
                    rotacionIzquierda(
                            nodo
                                    .getHijoIzquierdo()
                    );


            nodo.setHijoIzquierdo(
                    nuevoIzquierdo
            );


            if (nuevoIzquierdo != null) {

                nuevoIzquierdo.setPadre(
                        nodo
                );
            }


            return rotacionDerecha(
                    nodo
            );
        }


        /*
         * DERECHA - DERECHA
         *
         * FE > 1 significa que el
         * árbol está cargado hacia
         * la derecha.
         */
        if (factorEquilibrio > 1 &&
                obtenerFactorEquilibrio(
                        nodo.getHijoDerecho()
                ) >= 0) {

            return rotacionIzquierda(
                    nodo
            );
        }


        /*
         * DERECHA - IZQUIERDA
         */
        if (factorEquilibrio > 1 &&
                obtenerFactorEquilibrio(
                        nodo.getHijoDerecho()
                ) < 0) {

            NodoAVLCliente nuevoDerecho =
                    rotacionDerecha(
                            nodo
                                    .getHijoDerecho()
                    );


            nodo.setHijoDerecho(
                    nuevoDerecho
            );


            if (nuevoDerecho != null) {

                nuevoDerecho.setPadre(
                        nodo
                );
            }


            return rotacionIzquierda(
                    nodo
            );
        }


        return nodo;
    }


    // ---------------------------------------------------------
    // ROTACIÓN DERECHA
    // ---------------------------------------------------------

    private NodoAVLCliente rotacionDerecha(
            NodoAVLCliente nodo) {

        NodoAVLCliente nuevaRaiz =
                nodo
                        .getHijoIzquierdo();


        NodoAVLCliente temporal =
                nuevaRaiz
                        .getHijoDerecho();


        NodoAVLCliente padreOriginal =
                nodo.getPadre();


        /*
         * La nueva raíz ocupa
         * la posición del nodo.
         */
        nuevaRaiz.setPadre(
                padreOriginal
        );


        nuevaRaiz.setHijoDerecho(
                nodo
        );


        nodo.setPadre(
                nuevaRaiz
        );


        nodo.setHijoIzquierdo(
                temporal
        );


        if (temporal != null) {

            temporal.setPadre(
                    nodo
            );
        }


        actualizarDatos(
                nodo
        );


        actualizarDatos(
                nuevaRaiz
        );


        return nuevaRaiz;
    }


    // ---------------------------------------------------------
    // ROTACIÓN IZQUIERDA
    // ---------------------------------------------------------

    private NodoAVLCliente rotacionIzquierda(
            NodoAVLCliente nodo) {

        NodoAVLCliente nuevaRaiz =
                nodo
                        .getHijoDerecho();


        NodoAVLCliente temporal =
                nuevaRaiz
                        .getHijoIzquierdo();


        NodoAVLCliente padreOriginal =
                nodo.getPadre();


        /*
         * La nueva raíz ocupa
         * la posición del nodo.
         */
        nuevaRaiz.setPadre(
                padreOriginal
        );


        nuevaRaiz.setHijoIzquierdo(
                nodo
        );


        nodo.setPadre(
                nuevaRaiz
        );


        nodo.setHijoDerecho(
                temporal
        );


        if (temporal != null) {

            temporal.setPadre(
                    nodo
            );
        }


        actualizarDatos(
                nodo
        );


        actualizarDatos(
                nuevaRaiz
        );


        return nuevaRaiz;
    }


    // ---------------------------------------------------------
    // ALTURA Y FACTOR DE EQUILIBRIO
    // ---------------------------------------------------------

    private int obtenerAltura(
            NodoAVLCliente nodo) {

        if (nodo == null) {

            return 0;
        }


        return nodo.getAltura();
    }


    /*
     * Convención utilizada por
     * el profesor:
     *
     * FE = altura derecha
     *      - altura izquierda
     */
    private int obtenerFactorEquilibrio(
            NodoAVLCliente nodo) {

        if (nodo == null) {

            return 0;
        }


        return obtenerAltura(
                nodo.getHijoDerecho()
        )
                -
                obtenerAltura(
                        nodo.getHijoIzquierdo()
                );
    }


    private void actualizarDatos(
            NodoAVLCliente nodo) {

        if (nodo == null) {

            return;
        }


        int alturaIzquierda =
                obtenerAltura(
                        nodo
                                .getHijoIzquierdo()
                );


        int alturaDerecha =
                obtenerAltura(
                        nodo
                                .getHijoDerecho()
                );


        nodo.setAltura(
                1 +
                        Math.max(
                                alturaIzquierda,
                                alturaDerecha
                        )
        );


        nodo.setFe(
                alturaDerecha
                        -
                        alturaIzquierda
        );
    }


    public int altura() {

        return obtenerAltura(
                raiz
        );
    }


    // ---------------------------------------------------------
    // MAYOR DEL SUBÁRBOL IZQUIERDO
    // ---------------------------------------------------------

    private NodoAVLCliente encontrarMayor(
            NodoAVLCliente raizAux) {

        if (raizAux
                .getHijoDerecho()
                == null) {

            return raizAux;
        }


        return encontrarMayor(
                raizAux
                        .getHijoDerecho()
        );
    }


    // ---------------------------------------------------------
    // RECORRIDO INORDEN
    // ---------------------------------------------------------

    public List<Cliente>
    obtenerEnOrden() {

        List<Cliente> resultado =
                new ArrayList<>();


        inorden(
                raiz,
                resultado
        );


        return resultado;
    }


    private void inorden(
            NodoAVLCliente raizAux,
            List<Cliente> resultado) {

        if (raizAux == null) {

            return;
        }


        inorden(
                raizAux
                        .getHijoIzquierdo(),
                resultado
        );


        resultado.add(
                raizAux
                        .getElemento()
        );


        inorden(
                raizAux
                        .getHijoDerecho(),
                resultado
        );
    }


    // ---------------------------------------------------------
    // PREORDEN
    // ---------------------------------------------------------

    public List<Cliente>
    obtenerPreOrden() {

        List<Cliente> resultado =
                new ArrayList<>();


        preorden(
                raiz,
                resultado
        );


        return resultado;
    }


    private void preorden(
            NodoAVLCliente raizAux,
            List<Cliente> resultado) {

        if (raizAux == null) {

            return;
        }


        resultado.add(
                raizAux
                        .getElemento()
        );


        preorden(
                raizAux
                        .getHijoIzquierdo(),
                resultado
        );


        preorden(
                raizAux
                        .getHijoDerecho(),
                resultado
        );
    }


    // ---------------------------------------------------------
    // POSTORDEN
    // ---------------------------------------------------------

    public List<Cliente>
    obtenerPostOrden() {

        List<Cliente> resultado =
                new ArrayList<>();


        postorden(
                raiz,
                resultado
        );


        return resultado;
    }


    private void postorden(
            NodoAVLCliente raizAux,
            List<Cliente> resultado) {

        if (raizAux == null) {

            return;
        }


        postorden(
                raizAux
                        .getHijoIzquierdo(),
                resultado
        );


        postorden(
                raizAux
                        .getHijoDerecho(),
                resultado
        );


        resultado.add(
                raizAux
                        .getElemento()
        );
    }


    // ---------------------------------------------------------
    // BORRAR ÁRBOL
    // ---------------------------------------------------------

    public void borrarArbol() {

        raiz =
                null;

        peso =
                0;
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
         * Cuando dos clientes tienen
         * los mismos puntos, usamos
         * la identificación como
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