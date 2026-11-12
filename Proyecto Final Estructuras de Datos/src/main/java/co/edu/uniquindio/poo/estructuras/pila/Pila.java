package co.edu.uniquindio.poo.estructuras.pila;

public class Pila<T> {

    private int size;

    private Nodo<T> cima;


    public Pila() {

        size = 0;

        cima = null;
    }


    public boolean isEmpty() {

        return cima == null;
    }


    public int getSize() {

        return size;
    }


    /*
     * Agrega un elemento
     * en la cima de la pila.
     */
    public void push(
            T dato) {

        Nodo<T> nuevoNodo =
                new Nodo<>(
                        dato
                );


        if (isEmpty()) {

            cima =
                    nuevoNodo;

        } else {

            nuevoNodo
                    .setSiguiente(
                            cima
                    );


            cima =
                    nuevoNodo;
        }


        size++;
    }


    /*
     * Retira y retorna el
     * elemento de la cima.
     */
    public T pop() {

        if (isEmpty()) {

            throw new RuntimeException(
                    "No hay elementos en la pila"
            );
        }


        T dato =
                cima.getDato();


        cima =
                cima.getSiguiente();


        size--;


        return dato;
    }


    /*
     * Consulta el elemento de
     * la cima sin retirarlo.
     */
    public T peek() {

        if (isEmpty()) {

            return null;
        }


        return cima.getDato();
    }


    /*
     * Elimina todos los elementos
     * de la pila.
     */
    public void limpiarPila() {

        cima = null;

        size = 0;
    }


    public void imprimir() {

        Nodo<T> auxiliar =
                cima;


        while (auxiliar != null) {

            System.out.println(
                    auxiliar.getDato()
            );


            auxiliar =
                    auxiliar.getSiguiente();
        }
    }


    /*
     * Crea una nueva pila
     * conservando el mismo orden.
     */
    public Pila<T> clone() {

        Pila<T> pilaAuxiliar =
                new Pila<>();


        Pila<T> pilaClon =
                new Pila<>();


        Nodo<T> actual =
                cima;


        while (actual != null) {

            pilaAuxiliar.push(
                    actual.getDato()
            );


            actual =
                    actual.getSiguiente();
        }


        while (!pilaAuxiliar.isEmpty()) {

            pilaClon.push(
                    pilaAuxiliar.pop()
            );
        }


        return pilaClon;
    }
}