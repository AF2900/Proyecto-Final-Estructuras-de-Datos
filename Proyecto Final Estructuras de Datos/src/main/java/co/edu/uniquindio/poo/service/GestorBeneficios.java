package co.edu.uniquindio.poo.service;

import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.model.BeneficioCanjeado;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorBeneficios {

    private final GestorClientes
            gestorClientes;

    private final GestorPuntos
            gestorPuntos;


    /*
     * Cada cliente conserva un historial
     * de los beneficios que ha canjeado.
     */
    private final Map<String,
            List<BeneficioCanjeado>>
            beneficiosPorCliente;


    private int consecutivo;


    public GestorBeneficios(
            GestorClientes gestorClientes,
            GestorPuntos gestorPuntos) {

        this.gestorClientes =
                gestorClientes;

        this.gestorPuntos =
                gestorPuntos;

        this.beneficiosPorCliente =
                new HashMap<>();

        this.consecutivo = 1;
    }


    // ---------------------------------------------------------
    // REGISTRAR CLIENTE
    // ---------------------------------------------------------

    public void registrarCliente(
            String identificacionCliente) {

        if (identificacionCliente == null ||
                identificacionCliente
                        .trim()
                        .isEmpty()) {

            return;
        }


        beneficiosPorCliente
                .putIfAbsent(
                        identificacionCliente,
                        new ArrayList<>()
                );
    }


    // ---------------------------------------------------------
    // CANJEAR BENEFICIO
    // ---------------------------------------------------------

    public BeneficioCanjeado canjearBeneficio(
            String identificacionCliente,
            TipoBeneficio tipo,
            String idMonedero) {

        if (identificacionCliente == null ||
                tipo == null) {

            return null;
        }


        Cliente cliente =
                gestorClientes.buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return null;
        }


        int costo =
                tipo.getCostoPuntos();


        /*
         * El cliente debe tener suficientes
         * puntos antes de realizar el canje.
         */
        if (cliente.getPuntos()
                < costo) {

            return null;
        }


        Monedero monedero =
                null;


        /*
         * El bono de saldo necesita saber
         * en qué monedero se aplicarán
         * las 50 unidades.
         */
        if (tipo ==
                TipoBeneficio.BONO_SALDO) {

            monedero =
                    cliente.buscarMonedero(
                            idMonedero
                    );


            if (monedero == null) {
                return null;
            }
        }


        /*
         * Se descuentan los puntos utilizando
         * GestorPuntos para mantener sincronizados:
         *
         * HashMap
         * BST
         * AVL
         * Cliente
         */
        boolean puntosRestados =
                gestorPuntos.restarPuntos(
                        cliente,
                        costo
                );


        if (!puntosRestados) {
            return null;
        }


        LocalDateTime fechaCanje =
                LocalDateTime.now();


        LocalDateTime fechaVencimiento =
                null;


        /*
         * El beneficio de retiros tiene
         * una duración específica de un mes.
         */
        if (tipo ==
                TipoBeneficio.RETIROS_SIN_CARGO) {

            fechaVencimiento =
                    fechaCanje.plusMonths(
                            1
                    );
        }


        /*
         * El bono se aplica directamente
         * al saldo.
         *
         * No se utiliza depositar() del
         * GestorTransacciones porque eso
         * generaría puntos nuevamente.
         */
        if (tipo ==
                TipoBeneficio.BONO_SALDO) {

            boolean bonoAplicado =
                    monedero.depositar(
                            50
                    );


            if (!bonoAplicado) {

                /*
                 * Restauración defensiva:
                 * si no se pudiera aplicar
                 * el bono, se devuelven
                 * los puntos.
                 */
                gestorPuntos.agregarPuntos(
                        cliente,
                        costo
                );


                return null;
            }
        }


        BeneficioCanjeado beneficio =
                new BeneficioCanjeado(
                        generarCodigo(),
                        identificacionCliente,
                        tipo,
                        costo,
                        fechaCanje,
                        fechaVencimiento,
                        tipo ==
                                TipoBeneficio.BONO_SALDO
                                ? idMonedero
                                : null
                );


        registrarCliente(
                identificacionCliente
        );


        beneficiosPorCliente
                .get(
                        identificacionCliente
                )
                .add(
                        beneficio
                );


        return beneficio;
    }


    // ---------------------------------------------------------
    // CONSULTAR BENEFICIOS
    // ---------------------------------------------------------

    public List<BeneficioCanjeado>
    obtenerBeneficios(
            String identificacionCliente) {

        List<BeneficioCanjeado> lista =
                beneficiosPorCliente.get(
                        identificacionCliente
                );


        if (lista == null) {

            return new ArrayList<>();
        }


        return new ArrayList<>(
                lista
        );
    }


    public int cantidadBeneficios(
            String identificacionCliente) {

        return obtenerBeneficios(
                identificacionCliente
        ).size();
    }


    // ---------------------------------------------------------
    // BENEFICIO ACTIVO
    // ---------------------------------------------------------

    public boolean tieneBeneficioActivo(
            String identificacionCliente,
            TipoBeneficio tipo) {

        if (tipo == null) {
            return false;
        }


        List<BeneficioCanjeado> beneficios =
                beneficiosPorCliente.get(
                        identificacionCliente
                );


        if (beneficios == null) {
            return false;
        }


        for (BeneficioCanjeado beneficio :
                beneficios) {

            if (beneficio.getTipo()
                    == tipo &&
                    beneficio.estaVigente()) {

                return true;
            }
        }


        return false;
    }


    // ---------------------------------------------------------
    // FECHA DE VENCIMIENTO
    // ---------------------------------------------------------

    public LocalDateTime
    obtenerVencimientoBeneficio(
            String identificacionCliente,
            TipoBeneficio tipo) {

        List<BeneficioCanjeado> beneficios =
                beneficiosPorCliente.get(
                        identificacionCliente
                );


        if (beneficios == null ||
                tipo == null) {

            return null;
        }


        /*
         * Se recorre desde el beneficio
         * más reciente hacia atrás.
         */
        for (int i =
             beneficios.size() - 1;
             i >= 0;
             i--) {

            BeneficioCanjeado beneficio =
                    beneficios.get(
                            i
                    );


            if (beneficio.getTipo()
                    == tipo) {

                return beneficio
                        .getFechaVencimiento();
            }
        }


        return null;
    }


    // ---------------------------------------------------------
    // CÓDIGO
    // ---------------------------------------------------------

    private String generarCodigo() {

        String codigo =
                String.format(
                        "BF%05d",
                        consecutivo
                );


        consecutivo++;


        return codigo;
    }
}