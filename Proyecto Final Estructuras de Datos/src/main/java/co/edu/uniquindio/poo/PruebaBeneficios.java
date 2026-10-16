package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.model.BeneficioCanjeado;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;

public class PruebaBeneficios {

    private static int pruebasCorrectas = 0;


    public static void main(
            String[] args) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "       PRUEBAS BENEFICIOS"
        );

        System.out.println(
                "======================================"
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        sistema.registrarCliente(
                new Cliente(
                        "C001",
                        "Federico"
                )
        );


        sistema.crearMonedero(
                "C001",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        // -----------------------------------------------------
        // BENEFICIO DE 100 PUNTOS
        // ---------------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                10000
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 100,
                "Cliente acumula 100 puntos"
        );


        BeneficioCanjeado descuento =
                sistema.canjearBeneficio(
                        "C001",
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA,
                        null
                );


        verificar(
                descuento != null,
                "Canjear beneficio de 100 puntos"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 0,
                "Canje descuenta los 100 puntos"
        );


        verificar(
                sistema.tieneBeneficioActivo(
                        "C001",
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA
                ),
                "Descuento de transferencia queda activo"
        );


        verificar(
                sistema.consultarRango(
                        "C001"
                ) == RangoCliente.BRONCE,
                "Rango se recalcula después del canje"
        );


        // -----------------------------------------------------
        // BENEFICIO DE 500 PUNTOS
        // ---------------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                50000
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 500,
                "Cliente acumula 500 puntos"
        );


        BeneficioCanjeado retiros =
                sistema.canjearBeneficio(
                        "C001",
                        TipoBeneficio
                                .RETIROS_SIN_CARGO,
                        null
                );


        verificar(
                retiros != null,
                "Canjear un mes sin cargos por retiros"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 0,
                "Canje descuenta los 500 puntos"
        );


        verificar(
                sistema.tieneBeneficioActivo(
                        "C001",
                        TipoBeneficio
                                .RETIROS_SIN_CARGO
                ),
                "Beneficio de retiros queda activo"
        );


        verificar(
                sistema.obtenerVencimientoBeneficio(
                        "C001",
                        TipoBeneficio
                                .RETIROS_SIN_CARGO
                ) != null,
                "Beneficio de retiros tiene vencimiento"
        );


        // -----------------------------------------------------
        // BENEFICIO DE 1000 PUNTOS
        // ---------------------------------------------------------

        sistema.depositar(
                "C001",
                "M001",
                100000
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 1000,
                "Cliente acumula 1000 puntos"
        );


        double saldoAntes =
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                );


        BeneficioCanjeado bono =
                sistema.canjearBeneficio(
                        "C001",
                        TipoBeneficio
                                .BONO_SALDO,
                        "M001"
                );


        verificar(
                bono != null,
                "Canjear bono de saldo"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 0,
                "Bono consume 1000 puntos"
        );


        verificar(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ) ==
                        saldoAntes + 50,
                "Bono agrega 50 unidades al saldo"
        );


        /*
         * El bono no queda activo porque
         * su efecto se aplica inmediatamente.
         */
        verificar(
                !sistema.tieneBeneficioActivo(
                        "C001",
                        TipoBeneficio
                                .BONO_SALDO
                ),
                "Bono es un beneficio inmediato"
        );


        // -----------------------------------------------------
        // HISTORIAL DE BENEFICIOS
        // ---------------------------------------------------------

        verificar(
                sistema.cantidadBeneficios(
                        "C001"
                ) == 3,
                "Cliente tiene tres beneficios canjeados"
        );


        verificar(
                sistema.obtenerBeneficios(
                                "C001"
                        ).get(0)
                        .getTipo()
                        ==
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA,
                "Historial conserva primer beneficio"
        );


        verificar(
                sistema.obtenerBeneficios(
                                "C001"
                        ).get(2)
                        .getTipo()
                        ==
                        TipoBeneficio
                                .BONO_SALDO,
                "Historial conserva bono de saldo"
        );


        // -----------------------------------------------------
        // PUNTOS INSUFICIENTES
        // ---------------------------------------------------------

        BeneficioCanjeado invalido =
                sistema.canjearBeneficio(
                        "C001",
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA,
                        null
                );


        verificar(
                invalido == null,
                "No se puede canjear sin puntos suficientes"
        );


        verificar(
                sistema.cantidadBeneficios(
                        "C001"
                ) == 3,
                "Canje rechazado no modifica historial"
        );


        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "TODAS LAS PRUEBAS TERMINARON CORRECTAMENTE"
        );

        System.out.println(
                "Pruebas superadas: "
                        + pruebasCorrectas
        );

        System.out.println(
                "======================================"
        );
    }


    private static void verificar(
            boolean condicion,
            String descripcion) {

        if (!condicion) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }
}