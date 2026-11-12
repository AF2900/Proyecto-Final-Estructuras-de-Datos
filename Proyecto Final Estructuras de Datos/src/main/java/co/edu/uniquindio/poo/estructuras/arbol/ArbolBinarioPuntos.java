package co.edu.uniquindio.poo.estructuras.arbol;

import co.edu.uniquindio.poo.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioPuntos {

    private NodoArbolPuntos raiz;

    private int peso;


    public ArbolBinarioPuntos() {

        raiz =
                null;

        peso =
                0;
    }


    public NodoArbolPuntos getRaiz() {

        return raiz;
    }


    public void setRaiz(
            NodoArbolPuntos raiz) {

        this.raiz =
                raiz;
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
    }


    private NodoArbolPuntos insertar(
            NodoArbolPuntos raizAux,
            Cliente elemento,
            NodoArbolPuntos padre) {

        if (raizAux == null) {

            NodoArbolPuntos nuevoNodo =
                    new NodoArbolPuntos(
                            elemento,
                            padre
                    );


            peso++;


            return nuevoNodo;
        }


        int comparacion =
                comparar(
                        raizAux.getElemento(),
                        elemento
                );


        if (comparacion == 0) {

            throw new RuntimeException(
                    "El cliente ya existe en el arbol"
            );
        }


        if (comparacion > 0) {

            raizAux.setHijoIzquierdo(
                    insertar(
                            raizAux
                                    .getHijoIzquierdo(),
                            elemento,
                            raizAux
                    )
            );

        } else {

            raizAux.setHijoDerecho(
                    insertar(
                            raizAux
                                    .getHijoDerecho(),
                            elemento,
                            raizAux
                    )
            );
        }


        return raizAux;
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


    private NodoArbolPuntos buscarNodo(
            NodoArbolPuntos raizAux,
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


        NodoArbolPuntos encontrado =
                buscarNodo(
                        raiz,
                        elemento
                );


        if (encontrado == null) {

            return;
        }


        /*
         * CASO 1:
         *
         * El nodo no tiene hijos.
         */
        if (encontrado.getHijoIzquierdo()
                == null
                &&
                encontrado.getHijoDerecho()
                        == null) {

            eliminarHoja(
                    encontrado
            );


            peso--;


            return;
        }


        /*
         * CASO 2:
         *
         * El nodo tiene solamente
         * un hijo.
         */
        if (encontrado.getHijoIzquierdo()
                == null
                ||
                encontrado.getHijoDerecho()
                        == null) {

            eliminarConUnHijo(
                    encontrado
            );


            peso--;


            return;
        }


        /*
         * CASO 3:
         *
         * El nodo tiene dos hijos.
         *
         * Como trabaja el profesor,
         * buscamos el elemento mayor
         * del subárbol izquierdo.
         */
        NodoArbolPuntos nodoAuxiliar =
                encontrarMayor(
                        encontrado
                                .getHijoIzquierdo()
                );


        /*
         * Copiamos el elemento del
         * nodo auxiliar en el nodo
         * que deseamos eliminar.
         */
        encontrado.setElemento(
                nodoAuxiliar
                        .getElemento()
        );


        /*
         * El nodo mayor del subárbol
         * izquierdo nunca puede tener
         * un hijo derecho.
         *
         * Puede ser hoja o tener
         * solamente hijo izquierdo.
         */
        if (nodoAuxiliar
                .getHijoIzquierdo()
                == null) {

            eliminarHoja(
                    nodoAuxiliar
            );

        } else {

            eliminarConUnHijo(
                    nodoAuxiliar
            );
        }


        peso--;
    }


    private void eliminarHoja(
            NodoArbolPuntos nodo) {

        /*
         * Si el nodo es la raíz
         * y no tiene hijos.
         */
        if (nodo == raiz) {

            raiz =
                    null;

            return;
        }


        NodoArbolPuntos padre =
                nodo.getPadre();


        if (padre.getHijoIzquierdo()
                == nodo) {

            padre.setHijoIzquierdo(
                    null
            );

        } else {

            padre.setHijoDerecho(
                    null
            );
        }
    }


    private void eliminarConUnHijo(
            NodoArbolPuntos nodo) {

        NodoArbolPuntos hijo;


        if (nodo.getHijoIzquierdo()
                != null) {

            hijo =
                    nodo.getHijoIzquierdo();

        } else {

            hijo =
                    nodo.getHijoDerecho();
        }


        /*
         * Si estamos eliminando
         * la raíz.
         */
        if (nodo == raiz) {

            raiz =
                    hijo;


            hijo.setPadre(
                    null
            );


            return;
        }


        NodoArbolPuntos padre =
                nodo.getPadre();


        /*
         * Determinamos si el nodo
         * estaba a la izquierda
         * o a la derecha del padre.
         */
        if (padre.getHijoIzquierdo()
                == nodo) {

            padre.setHijoIzquierdo(
                    hijo
            );

        } else {

            padre.setHijoDerecho(
                    hijo
            );
        }


        hijo.setPadre(
                padre
        );
    }


    // ---------------------------------------------------------
    // MAYOR DEL SUBÁRBOL IZQUIERDO
    // ---------------------------------------------------------

    private NodoArbolPuntos encontrarMayor(
            NodoArbolPuntos raizAux) {

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
            NodoArbolPuntos raizAux,
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
            NodoArbolPuntos raizAux,
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
            NodoArbolPuntos raizAux,
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
    // ALTURA
    // ---------------------------------------------------------

    public int altura() {

        return calcularAltura(
                raiz
        );
    }


    private int calcularAltura(
            NodoArbolPuntos raizAux) {

        if (raizAux == null) {

            return 0;
        }


        int alturaIzquierda =
                calcularAltura(
                        raizAux
                                .getHijoIzquierdo()
                );


        int alturaDerecha =
                calcularAltura(
                        raizAux
                                .getHijoDerecho()
                );


        return 1 +
                Math.max(
                        alturaIzquierda,
                        alturaDerecha
                );
    }


    // ---------------------------------------------------------
    // PESO
    // ---------------------------------------------------------

    public int obtenerPeso() {

        return peso;
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


        if (comparacionPuntos
                != 0) {

            return comparacionPuntos;
        }


        /*
         * Si dos clientes tienen
         * la misma cantidad de puntos,
         * se utiliza la identificación
         * como criterio de desempate.
         */
        return cliente1
                .getIdentificacion()
                .compareTo(
                        cliente2
                                .getIdentificacion()
                );
    }
}