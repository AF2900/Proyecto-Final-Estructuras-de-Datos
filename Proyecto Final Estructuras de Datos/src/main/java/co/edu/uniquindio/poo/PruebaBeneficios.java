package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.model.BeneficioCanjeado;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

public class PruebaBeneficios {

    private static int pruebasCorrectas = 0;

    private static final double TOLERANCIA = 0.0001;


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


        sistema.registrarCliente(
                new Cliente(
                        "C002",
                        "Laura"
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


        sistema.crearMonedero(
                "C002",
                new Monedero(
                        "M001",
                        "Principal",
                        0
                )
        );


        // -----------------------------------------------------
        // BENEFICIO DE 100 PUNTOS
        // -----------------------------------------------------

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


        /*
         * Una transferencia de 1000 tendría
         * normalmente una comisión de 10.
         *
         * Con el beneficio se reduce
         * esa comisión un 10%:
         *
         * 10 - 10% = 9.
         */
        Transaccion transferencia =
                sistema.transferir(
                        "C001",
                        "M001",
                        "C002",
                        "M001",
                        1000
                );


        verificar(
                transferencia != null,
                "Realizar transferencia con beneficio"
        );


        verificarDouble(
                transferencia.getComision(),
                9,
                "Beneficio reduce comisión de 10 a 9"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                8991,
                "Origen paga monto más comisión reducida"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ),
                1000,
                "Destino recibe únicamente 1000"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 30,
                "Transferencia genera 30 puntos"
        );


        // -----------------------------------------------------
        // BENEFICIO DE 500 PUNTOS
        // -----------------------------------------------------

        /*
         * Ya existen 30 puntos.
         *
         * Se depositan 47.000 para generar
         * 470 puntos adicionales.
         *
         * Total = 500.
         */
        sistema.depositar(
                "C001",
                "M001",
                47000
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 500,
                "Cliente acumula exactamente 500 puntos"
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


        double saldoAntesRetiro =
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                );


        Transaccion retiro =
                sistema.retirar(
                        "C001",
                        "M001",
                        1000
                );


        verificar(
                retiro != null,
                "Realizar retiro con beneficio"
        );


        verificarDouble(
                retiro.getComision(),
                0,
                "Beneficio elimina comisión de retiro"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                saldoAntesRetiro - 1000,
                "Retiro descuenta únicamente el monto"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 20,
                "Retiro continúa generando puntos"
        );


        // -----------------------------------------------------
        // BENEFICIO DE 1000 PUNTOS
        // -----------------------------------------------------

        /*
         * El cliente tiene 20 puntos.
         *
         * Un depósito de 98.000 genera
         * otros 980 puntos.
         */
        sistema.depositar(
                "C001",
                "M001",
                98000
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 1000,
                "Cliente acumula exactamente 1000 puntos"
        );


        double saldoAntesBono =
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


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                saldoAntesBono + 50,
                "Bono agrega 50 unidades al saldo"
        );


        verificar(
                !sistema.tieneBeneficioActivo(
                        "C001",
                        TipoBeneficio
                                .BONO_SALDO
                ),
                "Bono es un beneficio inmediato"
        );


        // -----------------------------------------------------
        // HISTORIAL
        // -----------------------------------------------------

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
        // BENEFICIO DE TRANSFERENCIA SIGUE ACTIVO
        // -----------------------------------------------------

        verificar(
                sistema.tieneBeneficioActivo(
                        "C001",
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA
                ),
                "Descuento de transferencia permanece activo"
        );


        // -----------------------------------------------------
        // PUNTOS INSUFICIENTES
        // -----------------------------------------------------

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


    private static void verificarDouble(
            Double actual,
            double esperado,
            String descripcion) {

        if (actual == null ||
                Math.abs(
                        actual - esperado
                ) > TOLERANCIA) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
                            + " - esperado: "
                            + esperado
                            + ", actual: "
                            + actual
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }


    private static void verificarDouble(
            double actual,
            double esperado,
            String descripcion) {

        if (Math.abs(
                actual - esperado
        ) > TOLERANCIA) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
                            + " - esperado: "
                            + esperado
                            + ", actual: "
                            + actual
            );
        }


        pruebasCorrectas++;


        System.out.println(
                "OK - "
                        + descripcion
        );
    }
}