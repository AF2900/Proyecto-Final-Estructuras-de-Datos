package co.edu.uniquindio.poo.enums;

public enum RangoCliente {

    BRONCE,
    PLATA,
    ORO,
    PLATINO;


    public static RangoCliente calcularRango(
            int puntos) {

        if (puntos <= 500) {
            return BRONCE;
        }

        if (puntos <= 1000) {
            return PLATA;
        }

        if (puntos <= 5000) {
            return ORO;
        }

        return PLATINO;
    }
}