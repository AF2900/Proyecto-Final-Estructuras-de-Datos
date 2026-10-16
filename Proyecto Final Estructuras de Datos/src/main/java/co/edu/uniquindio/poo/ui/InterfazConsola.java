package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.model.BeneficioCanjeado;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Notificacion;
import co.edu.uniquindio.poo.model.ReporteCliente;
import co.edu.uniquindio.poo.model.ReporteSistema;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class InterfazConsola {

    private final MonederoVirtual sistema;

    private final Scanner scanner;

    private final DateTimeFormatter formatoFecha;


    public InterfazConsola() {

        sistema =
                new MonederoVirtual();

        scanner =
                new Scanner(
                        System.in
                );

        formatoFecha =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm"
                );
    }


    // ---------------------------------------------------------
    // INICIAR
    // ---------------------------------------------------------

    public void iniciar() {

        boolean continuar =
                true;


        while (continuar) {

            mostrarMenuPrincipal();


            int opcion =
                    leerEntero(
                            "Seleccione una opción: "
                    );


            switch (opcion) {

                case 1:
                    registrarCliente();
                    break;

                case 2:
                    crearMonedero();
                    break;

                case 3:
                    depositar();
                    break;

                case 4:
                    retirar();
                    break;

                case 5:
                    transferir();
                    break;

                case 6:
                    realizarGasto();
                    break;

                case 7:
                    consultarSaldo();
                    break;

                case 8:
                    consultarHistorial();
                    break;

                case 9:
                    consultarPuntosYRango();
                    break;

                case 10:
                    canjearBeneficio();
                    break;

                case 11:
                    programarDeposito();
                    break;

                case 12:
                    programarTransferencia();
                    break;

                case 13:
                    consultarProgramadas();
                    break;

                case 14:
                    procesarProgramadas();
                    break;

                case 15:
                    consultarNotificaciones();
                    break;

                case 16:
                    revertirUltimaTransaccion();
                    break;

                case 17:
                    generarReporteCliente();
                    break;

                case 18:
                    generarReporteSistema();
                    break;

                case 19:
                    consultarPatronesGasto();
                    break;

                case 20:
                    verificarIntegridad();
                    break;

                case 21:
                    consultarGrafos();
                    break;

                case 0:

                    continuar =
                            false;

                    System.out.println(
                            "\nSistema finalizado."
                    );

                    break;

                default:

                    System.out.println(
                            "\nOpción no válida."
                    );
            }


            if (continuar) {

                pausar();
            }
        }


        scanner.close();
    }


    // ---------------------------------------------------------
    // MENÚ
    // ---------------------------------------------------------

    private void mostrarMenuPrincipal() {

        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "          MONEDERO VIRTUAL"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "1.  Registrar cliente"
        );

        System.out.println(
                "2.  Crear monedero"
        );

        System.out.println(
                "3.  Realizar depósito"
        );

        System.out.println(
                "4.  Realizar retiro"
        );

        System.out.println(
                "5.  Realizar transferencia"
        );

        System.out.println(
                "6.  Registrar gasto"
        );

        System.out.println(
                "7.  Consultar saldo"
        );

        System.out.println(
                "8.  Consultar historial"
        );

        System.out.println(
                "9.  Consultar puntos y rango"
        );

        System.out.println(
                "10. Canjear beneficio"
        );

        System.out.println(
                "11. Programar depósito"
        );

        System.out.println(
                "12. Programar transferencia"
        );

        System.out.println(
                "13. Consultar programadas"
        );

        System.out.println(
                "14. Procesar programadas"
        );

        System.out.println(
                "15. Consultar notificaciones"
        );

        System.out.println(
                "16. Revertir última transacción"
        );

        System.out.println(
                "17. Reporte de cliente"
        );

        System.out.println(
                "18. Reporte general"
        );

        System.out.println(
                "19. Patrones de gasto"
        );

        System.out.println(
                "20. Verificar integridad"
        );

        System.out.println(
                "21. Consultar relaciones del grafo"
        );

        System.out.println(
                "0.  Salir"
        );

        System.out.println(
                "=========================================="
        );
    }


    // ---------------------------------------------------------
    // CLIENTE
    // ---------------------------------------------------------

    private void registrarCliente() {

        System.out.println(
                "\n=== REGISTRAR CLIENTE ==="
        );


        String identificacion =
                leerTexto(
                        "Identificación: "
                );


        String nombre =
                leerTexto(
                        "Nombre: "
                );


        Cliente cliente =
                new Cliente(
                        identificacion,
                        nombre
                );


        boolean registrado =
                sistema.registrarCliente(
                        cliente
                );


        if (registrado) {

            System.out.println(
                    "Cliente registrado correctamente."
            );

        } else {

            System.out.println(
                    "No fue posible registrar el cliente."
            );
        }
    }


    // ---------------------------------------------------------
    // MONEDERO
    // ---------------------------------------------------------

    private void crearMonedero() {

        System.out.println(
                "\n=== CREAR MONEDERO ==="
        );


        String identificacion =
                leerTexto(
                        "Identificación del cliente: "
                );


        String idMonedero =
                leerTexto(
                        "Código del monedero: "
                );


        String nombre =
                leerTexto(
                        "Nombre del monedero: "
                );


        double saldoInicial =
                leerDouble(
                        "Saldo inicial: "
                );


        Monedero monedero =
                new Monedero(
                        idMonedero,
                        nombre,
                        saldoInicial
                );


        boolean creado =
                sistema.crearMonedero(
                        identificacion,
                        monedero
                );


        if (creado) {

            System.out.println(
                    "Monedero creado correctamente."
            );

        } else {

            System.out.println(
                    "No fue posible crear el monedero."
            );
        }
    }


    // ---------------------------------------------------------
    // DEPÓSITO
    // ---------------------------------------------------------

    private void depositar() {

        System.out.println(
                "\n=== DEPÓSITO ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        String monedero =
                leerTexto(
                        "Código del monedero: "
                );


        double monto =
                leerDouble(
                        "Monto: "
                );


        Transaccion transaccion =
                sistema.depositar(
                        cliente,
                        monedero,
                        monto
                );


        mostrarResultadoTransaccion(
                transaccion
        );
    }


    // ---------------------------------------------------------
    // RETIRO
    // ---------------------------------------------------------

    private void retirar() {

        System.out.println(
                "\n=== RETIRO ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        String monedero =
                leerTexto(
                        "Código del monedero: "
                );


        double monto =
                leerDouble(
                        "Monto: "
                );


        Transaccion transaccion =
                sistema.retirar(
                        cliente,
                        monedero,
                        monto
                );


        mostrarResultadoTransaccion(
                transaccion
        );
    }


    // ---------------------------------------------------------
    // TRANSFERENCIA
    // ---------------------------------------------------------

    private void transferir() {

        System.out.println(
                "\n=== TRANSFERENCIA ==="
        );


        String clienteOrigen =
                leerTexto(
                        "Cliente origen: "
                );


        String monederoOrigen =
                leerTexto(
                        "Monedero origen: "
                );


        String clienteDestino =
                leerTexto(
                        "Cliente destino: "
                );


        String monederoDestino =
                leerTexto(
                        "Monedero destino: "
                );


        double monto =
                leerDouble(
                        "Monto: "
                );


        Transaccion transaccion =
                sistema.transferir(
                        clienteOrigen,
                        monederoOrigen,
                        clienteDestino,
                        monederoDestino,
                        monto
                );


        mostrarResultadoTransaccion(
                transaccion
        );
    }


    // ---------------------------------------------------------
    // GASTO
    // ---------------------------------------------------------

    private void realizarGasto() {

        System.out.println(
                "\n=== REGISTRAR GASTO ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        String monedero =
                leerTexto(
                        "Código del monedero: "
                );


        double monto =
                leerDouble(
                        "Monto: "
                );


        CategoriaGasto categoria =
                seleccionarCategoriaGasto();


        if (categoria == null) {

            System.out.println(
                    "Categoría no válida."
            );

            return;
        }


        Transaccion transaccion =
                sistema.realizarGasto(
                        cliente,
                        monedero,
                        monto,
                        categoria
                );


        mostrarResultadoTransaccion(
                transaccion
        );
    }


    // ---------------------------------------------------------
    // SALDO
    // ---------------------------------------------------------

    private void consultarSaldo() {

        System.out.println(
                "\n=== CONSULTAR SALDO ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        String monedero =
                leerTexto(
                        "Código del monedero: "
                );


        Double saldo =
                sistema.consultarSaldo(
                        cliente,
                        monedero
                );


        if (saldo == null) {

            System.out.println(
                    "Cliente o monedero no encontrado."
            );

            return;
        }


        System.out.println(
                "Saldo actual: "
                        + saldo
        );
    }


    // ---------------------------------------------------------
    // HISTORIAL
    // ---------------------------------------------------------

    private void consultarHistorial() {

        System.out.println(
                "\n=== HISTORIAL ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        List<Transaccion> historial =
                sistema.obtenerHistorial(
                        cliente
                );


        if (historial.isEmpty()) {

            System.out.println(
                    "No existen transacciones."
            );

            return;
        }


        for (Transaccion transaccion :
                historial) {

            System.out.println(
                    transaccion
            );
        }
    }


    // ---------------------------------------------------------
    // PUNTOS Y RANGO
    // ---------------------------------------------------------

    private void consultarPuntosYRango() {

        System.out.println(
                "\n=== PUNTOS Y RANGO ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        Integer puntos =
                sistema.consultarPuntos(
                        cliente
                );


        if (puntos == null) {

            System.out.println(
                    "Cliente no encontrado."
            );

            return;
        }


        System.out.println(
                "Puntos: "
                        + puntos
        );


        System.out.println(
                "Rango: "
                        + sistema.consultarRango(
                        cliente
                )
        );
    }


    // ---------------------------------------------------------
    // BENEFICIOS
    // ---------------------------------------------------------

    private void canjearBeneficio() {

        System.out.println(
                "\n=== CANJEAR BENEFICIO ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        TipoBeneficio tipo =
                seleccionarBeneficio();


        if (tipo == null) {

            System.out.println(
                    "Beneficio no válido."
            );

            return;
        }


        String idMonedero =
                null;


        if (tipo ==
                TipoBeneficio.BONO_SALDO) {

            idMonedero =
                    leerTexto(
                            "Monedero donde se aplicará el bono: "
                    );
        }


        BeneficioCanjeado beneficio =
                sistema.canjearBeneficio(
                        cliente,
                        tipo,
                        idMonedero
                );


        if (beneficio == null) {

            System.out.println(
                    "No fue posible realizar el canje."
            );

            return;
        }


        System.out.println(
                "Beneficio canjeado correctamente:"
        );


        System.out.println(
                beneficio
        );
    }


    // ---------------------------------------------------------
    // PROGRAMAR DEPÓSITO
    // ---------------------------------------------------------

    private void programarDeposito() {

        System.out.println(
                "\n=== PROGRAMAR DEPÓSITO ==="
        );


        String codigo =
                leerTexto(
                        "Código de programación: "
                );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        String monedero =
                leerTexto(
                        "Código del monedero: "
                );


        double monto =
                leerDouble(
                        "Monto: "
                );


        LocalDateTime fecha =
                leerFecha();


        int frecuencia =
                leerEntero(
                        "Frecuencia en días (0 si no es recurrente): "
                );


        boolean programado =
                sistema.programarDeposito(
                        codigo,
                        cliente,
                        monedero,
                        monto,
                        fecha,
                        frecuencia
                );


        mostrarResultadoProgramacion(
                programado
        );
    }


    // ---------------------------------------------------------
    // PROGRAMAR TRANSFERENCIA
    // ---------------------------------------------------------

    private void programarTransferencia() {

        System.out.println(
                "\n=== PROGRAMAR TRANSFERENCIA ==="
        );


        String codigo =
                leerTexto(
                        "Código de programación: "
                );


        String clienteOrigen =
                leerTexto(
                        "Cliente origen: "
                );


        String monederoOrigen =
                leerTexto(
                        "Monedero origen: "
                );


        String clienteDestino =
                leerTexto(
                        "Cliente destino: "
                );


        String monederoDestino =
                leerTexto(
                        "Monedero destino: "
                );


        double monto =
                leerDouble(
                        "Monto: "
                );


        LocalDateTime fecha =
                leerFecha();


        int frecuencia =
                leerEntero(
                        "Frecuencia en días (0 si no es recurrente): "
                );


        boolean programado =
                sistema.programarTransferencia(
                        codigo,
                        clienteOrigen,
                        monederoOrigen,
                        clienteDestino,
                        monederoDestino,
                        monto,
                        fecha,
                        frecuencia
                );


        mostrarResultadoProgramacion(
                programado
        );
    }


    // ---------------------------------------------------------
    // CONSULTAR PROGRAMADAS
    // ---------------------------------------------------------

    private void consultarProgramadas() {

        System.out.println(
                "\n=== TRANSACCIONES PROGRAMADAS ==="
        );


        List<TransaccionProgramada> programadas =
                sistema.obtenerProgramadasEnOrden();


        if (programadas.isEmpty()) {

            System.out.println(
                    "No existen transacciones programadas."
            );

            return;
        }


        for (TransaccionProgramada programada :
                programadas) {

            System.out.println(
                    programada
            );
        }


        System.out.println(
                "\nSiguiente:"
        );


        System.out.println(
                sistema.verSiguienteProgramada()
        );
    }


    // ---------------------------------------------------------
    // PROCESAR PROGRAMADAS
    // ---------------------------------------------------------

    private void procesarProgramadas() {

        System.out.println(
                "\n=== PROCESAR PROGRAMADAS ==="
        );


        LocalDateTime ahora =
                LocalDateTime.now();


        List<Transaccion> realizadas =
                sistema
                        .procesarTransaccionesProgramadas(
                                ahora
                        );


        if (realizadas.isEmpty()) {

            System.out.println(
                    "No hay transacciones pendientes para ejecutar."
            );

            return;
        }


        System.out.println(
                "Transacciones ejecutadas: "
                        + realizadas.size()
        );


        for (Transaccion transaccion :
                realizadas) {

            System.out.println(
                    transaccion
            );
        }
    }


    // ---------------------------------------------------------
    // NOTIFICACIONES
    // ---------------------------------------------------------

    private void consultarNotificaciones() {

        System.out.println(
                "\n=== NOTIFICACIONES ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        List<Notificacion> notificaciones =
                sistema.obtenerNotificaciones(
                        cliente
                );


        if (notificaciones.isEmpty()) {

            System.out.println(
                    "No existen notificaciones."
            );

            return;
        }


        for (Notificacion notificacion :
                notificaciones) {

            System.out.println(
                    notificacion
            );
        }


        String respuesta =
                leerTexto(
                        "¿Desea marcar una como leída? (S/N): "
                );


        if (!respuesta.equalsIgnoreCase(
                "S"
        )) {

            return;
        }


        String codigo =
                leerTexto(
                        "Código de la notificación: "
                );


        boolean actualizada =
                sistema
                        .marcarNotificacionComoLeida(
                                cliente,
                                codigo
                        );


        if (actualizada) {

            System.out.println(
                    "Notificación marcada como leída."
            );

        } else {

            System.out.println(
                    "Notificación no encontrada."
            );
        }
    }


    // ---------------------------------------------------------
    // REVERTIR
    // ---------------------------------------------------------

    private void revertirUltimaTransaccion() {

        System.out.println(
                "\n=== REVERTIR ÚLTIMA TRANSACCIÓN ==="
        );


        Transaccion ultima =
                sistema
                        .verUltimaTransaccionReversible();


        if (ultima == null) {

            System.out.println(
                    "No existen transacciones reversibles."
            );

            return;
        }


        System.out.println(
                "Transacción a revertir:"
        );


        System.out.println(
                ultima
        );


        String respuesta =
                leerTexto(
                        "¿Confirmar reversión? (S/N): "
                );


        if (!respuesta.equalsIgnoreCase(
                "S"
        )) {

            System.out.println(
                    "Operación cancelada."
            );

            return;
        }


        Transaccion revertida =
                sistema
                        .revertirUltimaTransaccion();


        if (revertida == null) {

            System.out.println(
                    "No fue posible revertir la transacción."
            );

        } else {

            System.out.println(
                    "Transacción revertida:"
            );

            System.out.println(
                    revertida
            );
        }
    }


    // ---------------------------------------------------------
    // REPORTE CLIENTE
    // ---------------------------------------------------------

    private void generarReporteCliente() {

        System.out.println(
                "\n=== REPORTE DE CLIENTE ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        ReporteCliente reporte =
                sistema.generarReporteCliente(
                        cliente
                );


        if (reporte == null) {

            System.out.println(
                    "Cliente no encontrado."
            );

            return;
        }


        System.out.println(
                reporte
        );
    }


    // ---------------------------------------------------------
    // REPORTE GENERAL
    // ---------------------------------------------------------

    private void generarReporteSistema() {

        System.out.println(
                sistema.generarReporteSistema()
        );
    }


    // ---------------------------------------------------------
    // PATRONES
    // ---------------------------------------------------------

    private void consultarPatronesGasto() {

        System.out.println(
                "\n=== PATRONES DE GASTO ==="
        );


        String cliente =
                leerTexto(
                        "Identificación del cliente: "
                );


        System.out.println(
                "Total gastado: "
                        + sistema.obtenerTotalGastos(
                        cliente
                )
        );


        System.out.println(
                "Categoría de mayor gasto: "
                        + sistema
                        .obtenerCategoriaMayorGasto(
                                cliente
                        )
        );


        System.out.println(
                "Última categoría: "
                        + sistema
                        .obtenerUltimaCategoriaGasto(
                                cliente
                        )
        );


        CategoriaGasto categoria =
                seleccionarCategoriaGasto();


        if (categoria != null) {

            System.out.println(
                    "Total en "
                            + categoria
                            + ": "
                            + sistema
                            .obtenerTotalCategoria(
                                    cliente,
                                    categoria
                            )
            );


            System.out.println(
                    "Siguiente categoría principal desde "
                            + categoria
                            + ": "
                            + sistema
                            .obtenerSiguienteCategoriaPrincipal(
                                    categoria
                            )
            );
        }
    }


    // ---------------------------------------------------------
    // INTEGRIDAD
    // ---------------------------------------------------------

    private void verificarIntegridad() {

        System.out.println(
                "\n=== VERIFICACIÓN DE INTEGRIDAD ==="
        );


        System.out.println(
                "1. Verificar cliente"
        );


        System.out.println(
                "2. Verificar sistema completo"
        );


        int opcion =
                leerEntero(
                        "Seleccione: "
                );


        if (opcion == 1) {

            String cliente =
                    leerTexto(
                            "Identificación: "
                    );


            boolean valida =
                    sistema
                            .verificarIntegridadCliente(
                                    cliente
                            );


            System.out.println(
                    "Integridad válida: "
                            + valida
            );


            System.out.println(
                    "Transacciones válidas: "
                            + sistema
                            .contarTransaccionesValidas(
                                    cliente
                            )
            );


            Transaccion invalida =
                    sistema
                            .buscarPrimeraTransaccionInvalida(
                                    cliente
                            );


            if (invalida != null) {

                System.out.println(
                        "Primera transacción inválida:"
                );

                System.out.println(
                        invalida
                );
            }

        } else if (opcion == 2) {

            System.out.println(
                    "Integridad del sistema: "
                            + sistema
                            .verificarIntegridadSistema()
            );

        } else {

            System.out.println(
                    "Opción no válida."
            );
        }
    }


    // ---------------------------------------------------------
    // GRAFOS
    // ---------------------------------------------------------

    private void consultarGrafos() {

        System.out.println(
                "\n=== RELACIONES ENTRE CLIENTES ==="
        );


        String origen =
                leerTexto(
                        "Cliente origen: "
                );


        String destino =
                leerTexto(
                        "Cliente destino: "
                );


        System.out.println(
                "Monto transferido: "
                        + sistema
                        .obtenerMontoTransferido(
                                origen,
                                destino
                        )
        );


        System.out.println(
                "Cantidad de transferencias: "
                        + sistema
                        .obtenerCantidadTransferencias(
                                origen,
                                destino
                        )
        );


        System.out.println(
                "Total transferido por "
                        + origen
                        + ": "
                        + sistema
                        .obtenerTotalTransferidoPorCliente(
                                origen
                        )
        );


        System.out.println(
                "Destino principal: "
                        + sistema
                        .obtenerPrincipalDestinoCliente(
                                origen
                        )
        );


        System.out.println(
                "Existe camino entre clientes: "
                        + sistema
                        .existeRelacionEntreClientes(
                                origen,
                                destino
                        )
        );


        System.out.println(
                "Recorrido BFS desde "
                        + origen
                        + ": "
                        + sistema
                        .recorridoClientes(
                                origen
                        )
        );
    }


    // ---------------------------------------------------------
    // SELECCIONAR CATEGORÍA
    // ---------------------------------------------------------

    private CategoriaGasto
    seleccionarCategoriaGasto() {

        System.out.println(
                "\nCategorías:"
        );


        CategoriaGasto[] categorias =
                CategoriaGasto.values();


        for (int i = 0;
             i < categorias.length;
             i++) {

            System.out.println(
                    (i + 1)
                            + ". "
                            + categorias[i]
            );
        }


        int opcion =
                leerEntero(
                        "Seleccione categoría: "
                );


        if (opcion < 1 ||
                opcion > categorias.length) {

            return null;
        }


        return categorias[
                opcion - 1
                ];
    }


    // ---------------------------------------------------------
    // SELECCIONAR BENEFICIO
    // ---------------------------------------------------------

    private TipoBeneficio
    seleccionarBeneficio() {

        System.out.println(
                "\nBeneficios:"
        );


        TipoBeneficio[] beneficios =
                TipoBeneficio.values();


        for (int i = 0;
             i < beneficios.length;
             i++) {

            TipoBeneficio beneficio =
                    beneficios[i];


            System.out.println(
                    (i + 1)
                            + ". "
                            + beneficio
                            + " - "
                            + beneficio
                            .getCostoPuntos()
                            + " puntos"
            );


            System.out.println(
                    "   "
                            + beneficio
                            .getDescripcion()
            );
        }


        int opcion =
                leerEntero(
                        "Seleccione beneficio: "
                );


        if (opcion < 1 ||
                opcion > beneficios.length) {

            return null;
        }


        return beneficios[
                opcion - 1
                ];
    }


    // ---------------------------------------------------------
    // FECHA
    // ---------------------------------------------------------

    private LocalDateTime leerFecha() {

        while (true) {

            String texto =
                    leerTexto(
                            "Fecha (yyyy-MM-dd HH:mm): "
                    );


            try {

                return LocalDateTime.parse(
                        texto,
                        formatoFecha
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Formato de fecha incorrecto."
                );
            }
        }
    }


    // ---------------------------------------------------------
    // UTILIDADES DE ENTRADA
    // ---------------------------------------------------------

    private String leerTexto(
            String mensaje) {

        System.out.print(
                mensaje
        );


        return scanner
                .nextLine()
                .trim();
    }


    private int leerEntero(
            String mensaje) {

        while (true) {

            String valor =
                    leerTexto(
                            mensaje
                    );


            try {

                return Integer.parseInt(
                        valor
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un número entero."
                );
            }
        }
    }


    private double leerDouble(
            String mensaje) {

        while (true) {

            String valor =
                    leerTexto(
                            mensaje
                    );


            /*
             * Permite escribir:
             *
             * 1500.50
             * o
             * 1500,50
             */
            valor =
                    valor.replace(
                            ',',
                            '.'
                    );


            try {

                return Double.parseDouble(
                        valor
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un número válido."
                );
            }
        }
    }


    // ---------------------------------------------------------
    // RESULTADOS
    // ---------------------------------------------------------

    private void mostrarResultadoTransaccion(
            Transaccion transaccion) {

        if (transaccion == null) {

            System.out.println(
                    "No fue posible realizar la transacción."
            );

            return;
        }


        System.out.println(
                "Transacción realizada correctamente:"
        );


        System.out.println(
                transaccion
        );
    }


    private void mostrarResultadoProgramacion(
            boolean programado) {

        if (programado) {

            System.out.println(
                    "Transacción programada correctamente."
            );

        } else {

            System.out.println(
                    "No fue posible programar la transacción."
            );
        }
    }


    private void pausar() {

        System.out.println(
                "\nPresione ENTER para continuar..."
        );


        scanner.nextLine();
    }
}