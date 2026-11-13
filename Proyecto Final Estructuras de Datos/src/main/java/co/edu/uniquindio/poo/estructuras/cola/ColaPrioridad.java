package co.edu.uniquindio.poo.estructuras.cola;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Cola de prioridad implementada con un monticulo binario minimo (min-heap)
 * guardado en una lista: el elemento de menor valor (segun compareTo) siempre
 * esta en la posicion 0.
 *
 * Para el nodo en la posicion i:
 *   - su padre esta en (i - 1) / 2
 *   - su hijo izquierdo esta en 2 * i + 1
 *   - su hijo derecho esta en 2 * i + 2
 *
 * Los elementos con la misma prioridad salen en el orden en que llegaron
 * (FIFO), porque cada entrada guarda un numero de llegada.
 */
public class ColaPrioridad<T extends Comparable<T>> {

    // Entrada del monticulo: el dato y su orden de llegada (para desempatar)
    private static class Entrada<T> {

        private final T dato;
        private final long orden;

        private Entrada(T dato, long orden) {
            this.dato = dato;
            this.orden = orden;
        }
    }

    private final List<Entrada<T>> monticulo;
    private final int limite;
    private long contadorLlegada;

    public ColaPrioridad(int limite) {

        if (limite <= 0) {
            throw new IllegalArgumentException(
                    "El limite de la cola debe ser mayor que cero"
            );
        }

        this.monticulo = new ArrayList<>();
        this.limite = limite;
        this.contadorLlegada = 0;
    }

    public int getSize() {
        return monticulo.size();
    }

    public boolean estaVacia() {
        return monticulo.isEmpty();
    }

    // O(log n)
    public void encolar(T dato) {

        if (monticulo.size() == limite) {
            throw new ColaLlena(
                    "La cola de prioridad esta llena"
            );
        }

        if (dato == null) {
            throw new IllegalArgumentException(
                    "No se puede encolar un dato nulo"
            );
        }

        monticulo.add(new Entrada<>(dato, contadorLlegada++));

        subir(monticulo.size() - 1);
    }

    // O(log n)
    public T desencolar() {

        if (estaVacia()) {
            throw new NoSuchElementException(
                    "No hay elementos en la cola"
            );
        }

        T primero = monticulo.get(0).dato;

        quitarEnPosicion(0);

        return primero;
    }

    // O(1)
    public T verPrimero() {

        if (estaVacia()) {
            return null;
        }

        return monticulo.get(0).dato;
    }

    // O(n): hay que buscar el elemento dentro del monticulo
    public boolean eliminar(T dato) {

        if (dato == null) {
            return false;
        }

        for (int i = 0; i < monticulo.size(); i++) {

            if (dato.equals(monticulo.get(i).dato)) {
                quitarEnPosicion(i);
                return true;
            }
        }

        return false;
    }

    // Devuelve los elementos ordenados de menor a mayor, O(n log n).
    // El orden interno del monticulo no es el de salida, por eso se ordena
    // una copia antes de devolverla.
    public List<T> obtenerElementos() {

        List<Entrada<T>> copia = new ArrayList<>(monticulo);

        copia.sort(this::comparar);

        List<T> elementos = new ArrayList<>();

        for (Entrada<T> entrada : copia) {
            elementos.add(entrada.dato);
        }

        return elementos;
    }

    public void imprimir() {

        for (T dato : obtenerElementos()) {
            System.out.println(dato);
        }
    }

    // ---------- operaciones internas del monticulo ----------

    // Quita la entrada de la posicion indicada y reacomoda el monticulo
    private void quitarEnPosicion(int posicion) {

        int ultima = monticulo.size() - 1;

        Entrada<T> entradaUltima = monticulo.remove(ultima);

        if (posicion == ultima) {
            return;
        }

        // La ultima entrada ocupa el hueco y se acomoda hacia arriba o abajo
        monticulo.set(posicion, entradaUltima);

        subir(posicion);
        bajar(posicion);
    }

    // Sube la entrada mientras sea menor que su padre
    private void subir(int posicion) {

        while (posicion > 0) {

            int padre = (posicion - 1) / 2;

            if (comparar(monticulo.get(posicion), monticulo.get(padre)) >= 0) {
                break;
            }

            intercambiar(posicion, padre);
            posicion = padre;
        }
    }

    // Baja la entrada mientras sea mayor que alguno de sus hijos
    private void bajar(int posicion) {

        int tamano = monticulo.size();

        while (true) {

            int izquierdo = 2 * posicion + 1;
            int derecho = 2 * posicion + 2;
            int menor = posicion;

            if (izquierdo < tamano
                    && comparar(monticulo.get(izquierdo), monticulo.get(menor)) < 0) {
                menor = izquierdo;
            }

            if (derecho < tamano
                    && comparar(monticulo.get(derecho), monticulo.get(menor)) < 0) {
                menor = derecho;
            }

            if (menor == posicion) {
                break;
            }

            intercambiar(posicion, menor);
            posicion = menor;
        }
    }

    private void intercambiar(int i, int j) {

        Entrada<T> auxiliar = monticulo.get(i);

        monticulo.set(i, monticulo.get(j));
        monticulo.set(j, auxiliar);
    }

    // Compara por valor y, si empatan, por orden de llegada
    private int comparar(Entrada<T> a, Entrada<T> b) {

        int resultado = a.dato.compareTo(b.dato);

        if (resultado != 0) {
            return resultado;
        }

        return Long.compare(a.orden, b.orden);
    }
}