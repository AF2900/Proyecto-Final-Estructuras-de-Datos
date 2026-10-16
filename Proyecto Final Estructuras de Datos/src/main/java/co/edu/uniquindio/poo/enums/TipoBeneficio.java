package co.edu.uniquindio.poo.enums;

public enum TipoBeneficio {

    DESCUENTO_TRANSFERENCIA(
            100,
            "Reducción del 10% en la comisión por transferencias"
    ),

    RETIROS_SIN_CARGO(
            500,
            "Un mes sin cargos por retiros"
    ),

    BONO_SALDO(
            1000,
            "Bono de 50 unidades en el monedero"
    );


    private final int costoPuntos;

    private final String descripcion;


    TipoBeneficio(
            int costoPuntos,
            String descripcion) {

        this.costoPuntos =
                costoPuntos;

        this.descripcion =
                descripcion;
    }


    public int getCostoPuntos() {
        return costoPuntos;
    }


    public String getDescripcion() {
        return descripcion;
    }
}