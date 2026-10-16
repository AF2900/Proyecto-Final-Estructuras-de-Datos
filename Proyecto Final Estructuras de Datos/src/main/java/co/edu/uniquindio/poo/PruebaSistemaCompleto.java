package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.model.BeneficioCanjeado;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Notificacion;
import co.edu.uniquindio.poo.model.ReporteCliente;
import co.edu.uniquindio.poo.model.ReporteSistema;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;

import java.time.LocalDateTime;
import java.util.List;

public class PruebaSistemaCompleto {

    private static int pruebasCorrectas = 0;

    private static final double TOLERANCIA =
            0.0001;


    public static void main(
            String[] args) {

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "      PRUEBA INTEGRAL DEL SISTEMA"
        );

        System.out.println(
                "=========================================="
        );


        MonederoVirtual sistema =
                new MonederoVirtual();


        // -----------------------------------------------------
        // 1. CLIENTES
        // -----------------------------------------------------

        verificar(
                sistema.registrarCliente(
                        new Cliente(
                                "C001",
                                "Federico"
                        )
                ),
                "Registrar cliente C001"
        );


        verificar(
                sistema.registrarCliente(
                        new Cliente(
                                "C002",
                                "Laura"
                        )
                ),
                "Registrar cliente C002"
        );


        verificar(
                sistema.registrarCliente(
                        new Cliente(
                                "C003",
                                "Johan"
                        )
                ),
                "Registrar cliente C003"
        );


        verificar(
                !sistema.registrarCliente(
                        new Cliente(
                                "C001",
                                "Cliente repetido"
                        )
                ),
                "Rechazar cliente duplicado"
        );


        verificar(
                sistema.obtenerClientes()
                        .size()
                        == 3,
                "Sistema contiene tres clientes"
        );


        // -----------------------------------------------------
        // 2. MULTIMONEDERO
        // -----------------------------------------------------

        verificar(
                sistema.crearMonedero(
                        "C001",
                        new Monedero(
                                "M001",
                                "Principal",
                                0
                        )
                ),
                "Crear monedero principal de C001"
        );


        verificar(
                sistema.crearMonedero(
                        "C001",
                        new Monedero(
                                "M002",
                                "Ahorros",
                                0
                        )
                ),
                "Crear segundo monedero de C001"
        );


        verificar(
                sistema.crearMonedero(
                        "C002",
                        new Monedero(
                                "M001",
                                "Principal",
                                0
                        )
                ),
                "Crear monedero de C002"
        );


        verificar(
                sistema.crearMonedero(
                        "C003",
                        new Monedero(
                                "M001",
                                "Principal",
                                0
                        )
                ),
                "Crear monedero de C003"
        );


        verificar(
                sistema.buscarCliente(
                                "C001"
                        )
                        .obtenerMonederos()
                        .size()
                        == 2,
                "C001 tiene dos monederos"
        );


        // -----------------------------------------------------
        // 3. DEPÓSITOS
        // -----------------------------------------------------

        Transaccion depositoC001 =
                sistema.depositar(
                        "C001",
                        "M001",
                        20000
                );


        verificar(
                depositoC001 != null,
                "Depósito de C001 realizado"
        );


        Transaccion depositoC002 =
                sistema.depositar(
                        "C002",
                        "M001",
                        1000
                );


        verificar(
                depositoC002 != null,
                "Depósito de C002 realizado"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                20000,
                "Saldo inicial C001"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 200,
                "Depósito genera 200 puntos"
        );


        verificar(
                sistema.consultarPuntos(
                        "C002"
                ) == 10,
                "Depósito genera 10 puntos a C002"
        );


        // -----------------------------------------------------
        // 4. TRANSFERENCIA ENTRE CLIENTES
        // -----------------------------------------------------

        Transaccion transferenciaC002 =
                sistema.transferir(
                        "C001",
                        "M001",
                        "C002",
                        "M001",
                        1000
                );


        verificar(
                transferenciaC002 != null,
                "Transferencia C001 hacia C002"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                19000,
                "Transferencia descuenta saldo origen"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C002",
                        "M001"
                ),
                2000,
                "Transferencia aumenta saldo destino"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 230,
                "Transferencia genera 30 puntos"
        );


        // -----------------------------------------------------
        // 5. TRANSFERENCIA ENTRE MONEDEROS PROPIOS
        // -----------------------------------------------------

        Transaccion transferenciaInterna =
                sistema.transferir(
                        "C001",
                        "M001",
                        "C001",
                        "M002",
                        2000
                );


        verificar(
                transferenciaInterna != null,
                "Transferencia entre monederos propios"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M002"
                ),
                2000,
                "Monedero de ahorros recibe 2000"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 290,
                "Transferencia interna genera puntos"
        );


        // -----------------------------------------------------
        // 6. GASTOS
        // -----------------------------------------------------

        Transaccion gastoAlimentacion =
                sistema.realizarGasto(
                        "C001",
                        "M001",
                        500,
                        CategoriaGasto.ALIMENTACION
                );


        verificar(
                gastoAlimentacion != null,
                "Registrar gasto de alimentación"
        );


        Transaccion gastoTransporte =
                sistema.realizarGasto(
                        "C001",
                        "M001",
                        300,
                        CategoriaGasto.TRANSPORTE
                );


        verificar(
                gastoTransporte != null,
                "Registrar gasto de transporte"
        );


        verificarDouble(
                sistema.obtenerTotalGastos(
                        "C001"
                ),
                800,
                "Total de gastos es 800"
        );


        verificarDouble(
                sistema.obtenerTotalCategoria(
                        "C001",
                        CategoriaGasto.ALIMENTACION
                ),
                500,
                "Alimentación acumula 500"
        );


        verificar(
                sistema.obtenerCategoriaMayorGasto(
                        "C001"
                )
                        == CategoriaGasto.ALIMENTACION,
                "Alimentación es categoría principal"
        );


        verificarDouble(
                sistema.obtenerMontoTransicionCategorias(
                        CategoriaGasto.ALIMENTACION,
                        CategoriaGasto.TRANSPORTE
                ),
                300,
                "Grafo registra patrón Alimentación a Transporte"
        );


        // -----------------------------------------------------
        // 7. RETIRO
        // -----------------------------------------------------

        Transaccion retiro =
                sistema.retirar(
                        "C001",
                        "M001",
                        200
                );


        verificar(
                retiro != null,
                "Realizar retiro"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                16000,
                "Saldo después del retiro"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 294,
                "Retiro genera cuatro puntos"
        );


        // -----------------------------------------------------
        // 8. GRAFOS
        // -----------------------------------------------------

        verificarDouble(
                sistema.obtenerMontoTransferido(
                        "C001",
                        "C002"
                ),
                1000,
                "Grafo registra transferencia C001 a C002"
        );


        verificar(
                sistema.obtenerCantidadTransferencias(
                        "C001",
                        "C002"
                ) == 1,
                "Grafo registra una transferencia"
        );


        verificarDouble(
                sistema.obtenerMontoEntreMonederos(
                        "C001",
                        "M001",
                        "C001",
                        "M002"
                ),
                2000,
                "Grafo relaciona monederos propios"
        );


        verificar(
                sistema.existeRelacionEntreClientes(
                        "C001",
                        "C002"
                ),
                "Existe camino dirigido de C001 a C002"
        );


        verificar(
                !sistema.existeRelacionEntreClientes(
                        "C002",
                        "C001"
                ),
                "No existe camino inverso automáticamente"
        );


        // -----------------------------------------------------
        // 9. BENEFICIO
        // -----------------------------------------------------

        BeneficioCanjeado beneficio =
                sistema.canjearBeneficio(
                        "C001",
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA,
                        null
                );


        verificar(
                beneficio != null,
                "Canjear beneficio de 100 puntos"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 194,
                "Canje descuenta los puntos"
        );


        verificar(
                sistema.tieneBeneficioActivo(
                        "C001",
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA
                ),
                "Beneficio queda activo"
        );


        verificar(
                sistema.cantidadBeneficios(
                        "C001"
                ) == 1,
                "Historial contiene un beneficio"
        );


        verificar(
                sistema.consultarRango(
                        "C001"
                ) == RangoCliente.BRONCE,
                "Rango se mantiene en Bronce"
        );


        // -----------------------------------------------------
        // 10. TRANSACCIONES PROGRAMADAS
        // -----------------------------------------------------

        LocalDateTime fechaBase =
                LocalDateTime.of(
                        2026,
                        10,
                        20,
                        8,
                        0
                );


        /*
         * Se agrega primero la operación
         * que tiene fecha posterior.
         *
         * Así demostramos que PriorityQueue
         * no depende del orden de inserción.
         */
        verificar(
                sistema.programarTransferencia(
                        "TP002",
                        "C001",
                        "M001",
                        "C003",
                        "M001",
                        400,
                        fechaBase.plusHours(
                                3
                        ),
                        7
                ),
                "Programar transferencia recurrente"
        );


        verificar(
                sistema.programarDeposito(
                        "TP001",
                        "C001",
                        "M001",
                        1000,
                        fechaBase.plusHours(
                                1
                        ),
                        0
                ),
                "Programar depósito"
        );


        verificar(
                sistema.cantidadProgramadas()
                        == 2,
                "PriorityQueue contiene dos programadas"
        );


        verificar(
                sistema.verSiguienteProgramada()
                        .getCodigo()
                        .equals(
                                "TP001"
                        ),
                "PriorityQueue prioriza la fecha más cercana"
        );


        // -----------------------------------------------------
        // 11. PROCESAR DEPÓSITO PROGRAMADO
        // -----------------------------------------------------

        List<Transaccion> ejecutadasDeposito =
                sistema
                        .procesarTransaccionesProgramadas(
                                fechaBase.plusHours(
                                        1
                                )
                        );


        verificar(
                ejecutadasDeposito.size()
                        == 1,
                "Se ejecuta únicamente TP001"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                17000,
                "Depósito programado aumenta saldo"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 204,
                "Depósito programado aumenta puntos"
        );


        verificar(
                sistema.cantidadProgramadas()
                        == 1,
                "TP001 sale de PriorityQueue"
        );


        // -----------------------------------------------------
        // 12. PROCESAR TRANSFERENCIA RECURRENTE
        // -----------------------------------------------------

        List<Transaccion> ejecutadasTransferencia =
                sistema
                        .procesarTransaccionesProgramadas(
                                fechaBase.plusHours(
                                        3
                                )
                        );


        verificar(
                ejecutadasTransferencia.size()
                        == 1,
                "Se ejecuta TP002"
        );


        Transaccion transferenciaProgramada =
                ejecutadasTransferencia.get(
                        0
                );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                16600,
                "Transferencia programada descuenta origen"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C003",
                        "M001"
                ),
                400,
                "C003 recibe transferencia programada"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 216,
                "Transferencia programada genera puntos"
        );


        verificar(
                sistema.cantidadProgramadas()
                        == 1,
                "Transacción recurrente vuelve a PriorityQueue"
        );


        TransaccionProgramada recurrente =
                sistema
                        .verSiguienteProgramada();


        verificar(
                recurrente != null &&
                        recurrente
                                .getCodigo()
                                .equals(
                                        "TP002"
                                ),
                "TP002 continúa programada"
        );


        verificar(
                recurrente
                        .getFechaEjecucion()
                        .equals(
                                fechaBase
                                        .plusHours(3)
                                        .plusDays(7)
                        ),
                "Fecha recurrente avanza siete días"
        );


        // -----------------------------------------------------
        // 13. NOTIFICACIONES
        // -----------------------------------------------------

        /*
         * Se generaron:
         *
         * 1. Programación TP002
         * 2. Programación TP001
         * 3. Ejecución TP001
         * 4. Ejecución TP002
         */
        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 4,
                "Historial contiene cuatro notificaciones"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 4,
                "Queue contiene cuatro pendientes"
        );


        Notificacion primeraPendiente =
                sistema
                        .verSiguienteNotificacionPendiente(
                                "C001"
                        );


        verificar(
                primeraPendiente != null &&
                        primeraPendiente.getTipo()
                                ==
                                TipoNotificacion
                                        .TRANSACCION_PROGRAMADA,
                "Queue expone primera pendiente"
        );


        // -----------------------------------------------------
        // 14. LISTA CIRCULAR
        // -----------------------------------------------------

        Notificacion circular1 =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        Notificacion circular2 =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        Notificacion circular3 =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        Notificacion circular4 =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        Notificacion circular5 =
                sistema.verSiguienteNotificacion(
                        "C001"
                );


        verificar(
                circular1 != null &&
                        circular2 != null &&
                        circular3 != null &&
                        circular4 != null,
                "Lista circular recorre las cuatro notificaciones"
        );


        verificar(
                circular1 == circular5,
                "Lista circular vuelve al primer nodo"
        );


        // -----------------------------------------------------
        // 15. COLA FIFO DE NOTIFICACIONES
        // -----------------------------------------------------

        Notificacion atendida =
                sistema
                        .atenderSiguienteNotificacion(
                                "C001"
                        );


        verificar(
                atendida == primeraPendiente,
                "Queue atiende primero la más antigua"
        );


        verificar(
                atendida.isLeida(),
                "Notificación atendida queda leída"
        );


        verificar(
                sistema.cantidadNotificacionesPendientes(
                        "C001"
                ) == 3,
                "Queue queda con tres pendientes"
        );


        verificar(
                sistema.cantidadNotificaciones(
                        "C001"
                ) == 4,
                "Lista circular conserva historial completo"
        );


        // -----------------------------------------------------
        // 16. GRAFO DESPUÉS DE TP002
        // -----------------------------------------------------

        verificarDouble(
                sistema.obtenerMontoTransferido(
                        "C001",
                        "C003"
                ),
                400,
                "Grafo registra transferencia programada"
        );


        verificarDouble(
                sistema.obtenerTotalTransferidoPorCliente(
                        "C001"
                ),
                1400,
                "C001 ha transferido 1400 a otros clientes"
        );


        verificar(
                sistema.obtenerPrincipalDestinoCliente(
                        "C001"
                ).equals(
                        "C002"
                ),
                "C002 continúa como principal destino"
        );


        verificar(
                sistema.existeRelacionEntreClientes(
                        "C001",
                        "C003"
                ),
                "Grafo alcanza C003"
        );


        // -----------------------------------------------------
        // 17. HASHMAP + BST + AVL
        // -----------------------------------------------------

        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 216,
                "HashMap consulta puntos de C001"
        );


        List<Cliente> ordenBST =
                sistema
                        .obtenerClientesOrdenadosPorPuntosBST();


        List<Cliente> ordenAVL =
                sistema
                        .obtenerClientesOrdenadosPorPuntosAVL();


        verificar(
                ordenBST.size()
                        == 3,
                "BST contiene los tres clientes"
        );


        verificar(
                ordenAVL.size()
                        == 3,
                "AVL contiene los tres clientes"
        );


        verificar(
                ordenBST.get(0)
                        .getIdentificacion()
                        .equals(
                                "C003"
                        ),
                "BST comienza con cliente de menor puntuación"
        );


        verificar(
                ordenBST.get(2)
                        .getIdentificacion()
                        .equals(
                                "C001"
                        ),
                "BST termina con cliente de mayor puntuación"
        );


        verificar(
                ordenAVL.get(0)
                        .getIdentificacion()
                        .equals(
                                "C003"
                        ) &&
                        ordenAVL.get(2)
                                .getIdentificacion()
                                .equals(
                                        "C001"
                                ),
                "AVL conserva orden por puntos"
        );


        verificar(
                sistema.obtenerAlturaAVL()
                        <= sistema.obtenerAlturaBST(),
                "AVL mantiene altura controlada"
        );


        // -----------------------------------------------------
        // 18. INTEGRIDAD RECURSIVA
        // -----------------------------------------------------

        verificar(
                sistema.verificarIntegridadCliente(
                        "C001"
                ),
                "Integridad recursiva de C001"
        );


        verificar(
                sistema.verificarIntegridadCliente(
                        "C002"
                ),
                "Integridad recursiva de C002"
        );


        verificar(
                sistema.verificarIntegridadCliente(
                        "C003"
                ),
                "Integridad recursiva de C003"
        );


        verificar(
                sistema.verificarIntegridadSistema(),
                "Integridad recursiva del sistema completo"
        );


        verificar(
                sistema.contarTransaccionesValidas(
                        "C001"
                ) == 8,
                "C001 tiene ocho transacciones válidas"
        );


        // -----------------------------------------------------
        // 19. REPORTES ANTES DE REVERTIR
        // -----------------------------------------------------

        ReporteCliente reporteAntes =
                sistema.generarReporteCliente(
                        "C001"
                );


        verificar(
                reporteAntes != null,
                "Generar reporte de C001"
        );


        verificarDouble(
                reporteAntes.getSaldoTotal(),
                18600,
                "Reporte calcula saldo total de los dos monederos"
        );


        verificar(
                reporteAntes.getCantidadTransacciones()
                        == 8,
                "Reporte registra ocho transacciones de C001"
        );


        verificar(
                reporteAntes.getTransaccionesActivas()
                        == 8,
                "Todas están activas antes de revertir"
        );


        verificar(
                reporteAntes.getPuntos()
                        == 216,
                "Reporte refleja puntos actuales"
        );


        verificarDouble(
                reporteAntes.getTotalGastos(),
                800,
                "Reporte refleja gastos"
        );


        verificarDouble(
                reporteAntes.getTotalTransferido(),
                1400,
                "Reporte refleja transferencias externas"
        );


        verificar(
                reporteAntes.getCantidadBeneficios()
                        == 1,
                "Reporte refleja beneficio canjeado"
        );


        verificar(
                reporteAntes.getCantidadNotificaciones()
                        == 4,
                "Reporte refleja historial de notificaciones"
        );


        ReporteSistema reporteSistemaAntes =
                sistema.generarReporteSistema();


        verificar(
                reporteSistemaAntes
                        .getCantidadClientes()
                        == 3,
                "Reporte general contiene tres clientes"
        );


        verificar(
                reporteSistemaAntes
                        .getCantidadMonederos()
                        == 4,
                "Reporte general contiene cuatro monederos"
        );


        /*
         * Dinero que entró:
         *
         * C001: 20.000
         * C002:  1.000
         * Programado: 1.000
         *
         * Total: 22.000
         *
         * Salidas reales:
         *
         * Gastos: 800
         * Retiro: 200
         *
         * Saldo global: 21.000
         *
         * Las transferencias no modifican
         * el dinero total del sistema.
         */
        verificarDouble(
                reporteSistemaAntes
                        .getSaldoTotalSistema(),
                21000,
                "Saldo global del sistema es 21000"
        );


        verificar(
                reporteSistemaAntes
                        .getCantidadTransacciones()
                        == 9,
                "Reporte general identifica nueve transacciones únicas"
        );


        verificar(
                reporteSistemaAntes
                        .getTransaccionesRevertidas()
                        == 0,
                "Aún no existen transacciones revertidas"
        );


        verificarDouble(
                reporteSistemaAntes
                        .getTotalGastos(),
                800,
                "Reporte general acumula los gastos"
        );


        verificar(
                reporteSistemaAntes
                        .getCantidadBeneficios()
                        == 1,
                "Reporte general contiene un beneficio"
        );


        verificar(
                reporteSistemaAntes
                        .getCantidadNotificaciones()
                        == 4,
                "Reporte general contiene cuatro notificaciones"
        );


        verificar(
                reporteSistemaAntes
                        .isIntegridadValida(),
                "Reporte indica integridad válida"
        );


        // -----------------------------------------------------
        // 20. PILA Y REVERSIÓN LIFO
        // -----------------------------------------------------

        Transaccion ultima =
                sistema
                        .verUltimaTransaccionReversible();


        verificar(
                ultima ==
                        transferenciaProgramada,
                "Pila tiene TP002 en el tope"
        );


        Transaccion revertida =
                sistema
                        .revertirUltimaTransaccion();


        verificar(
                revertida ==
                        transferenciaProgramada,
                "LIFO revierte la última transacción"
        );


        verificar(
                revertida.isRevertida(),
                "Transacción queda marcada como revertida"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C001",
                        "M001"
                ),
                17000,
                "Reversión devuelve dinero a C001"
        );


        verificarDouble(
                sistema.consultarSaldo(
                        "C003",
                        "M001"
                ),
                0,
                "Reversión retira dinero de C003"
        );


        verificar(
                sistema.consultarPuntos(
                        "C001"
                ) == 204,
                "Reversión recalcula puntos"
        );


        // -----------------------------------------------------
        // 21. GRAFOS DESPUÉS DE REVERTIR
        // -----------------------------------------------------

        verificarDouble(
                sistema.obtenerMontoTransferido(
                        "C001",
                        "C003"
                ),
                0,
                "Reversión elimina relación C001 a C003"
        );


        verificarDouble(
                sistema.obtenerMontoEntreMonederos(
                        "C001",
                        "M001",
                        "C003",
                        "M001"
                ),
                0,
                "Reversión actualiza grafo de monederos"
        );


        verificarDouble(
                sistema.obtenerTotalTransferidoPorCliente(
                        "C001"
                ),
                1000,
                "Solo permanece transferencia hacia C002"
        );


        verificarDouble(
                sistema.obtenerMontoEntreMonederos(
                        "C001",
                        "M001",
                        "C001",
                        "M002"
                ),
                2000,
                "Transferencia interna permanece intacta"
        );


        // -----------------------------------------------------
        // 22. RECURRENCIA Y CANCELACIÓN
        // -----------------------------------------------------

        verificar(
                sistema.cantidadProgramadas()
                        == 1,
                "Revertir no cancela recurrencia"
        );


        verificar(
                sistema.cancelarProgramada(
                        "TP002"
                ),
                "Cancelar transacción recurrente"
        );


        verificar(
                sistema.programadasVacia(),
                "PriorityQueue queda vacía"
        );


        // -----------------------------------------------------
        // 23. INTEGRIDAD DESPUÉS DE REVERTIR
        // -----------------------------------------------------

        verificar(
                sistema.verificarIntegridadSistema(),
                "Revertir no rompe la integridad"
        );


        verificar(
                sistema.buscarPrimeraTransaccionInvalida(
                        "C001"
                ) == null,
                "No existen transacciones inválidas"
        );


        // -----------------------------------------------------
        // 24. ÁRBOLES DESPUÉS DE ACTUALIZAR PUNTOS
        // -----------------------------------------------------

        ordenBST =
                sistema
                        .obtenerClientesOrdenadosPorPuntosBST();


        ordenAVL =
                sistema
                        .obtenerClientesOrdenadosPorPuntosAVL();


        verificar(
                ordenBST.get(2)
                        .getPuntos()
                        == 204,
                "BST se actualiza después de reversión"
        );


        verificar(
                ordenAVL.get(2)
                        .getPuntos()
                        == 204,
                "AVL se actualiza después de reversión"
        );


        // -----------------------------------------------------
        // 25. REPORTES DESPUÉS DE REVERTIR
        // -----------------------------------------------------

        ReporteCliente reporteDespues =
                sistema.generarReporteCliente(
                        "C001"
                );


        verificarDouble(
                reporteDespues.getSaldoTotal(),
                19000,
                "Reporte actualiza saldo después de reversión"
        );


        verificar(
                reporteDespues
                        .getCantidadTransacciones()
                        == 8,
                "Historial conserva ocho transacciones"
        );


        verificar(
                reporteDespues
                        .getTransaccionesActivas()
                        == 7,
                "Siete transacciones quedan activas"
        );


        verificar(
                reporteDespues
                        .getTransaccionesRevertidas()
                        == 1,
                "Reporte detecta una reversión"
        );


        verificar(
                reporteDespues
                        .getPuntos()
                        == 204,
                "Reporte actualiza puntos"
        );


        verificarDouble(
                reporteDespues
                        .getTotalTransferido(),
                1000,
                "Reporte actualiza grafo de transferencias"
        );


        verificarDouble(
                reporteDespues
                        .getTotalGastos(),
                800,
                "Gastos permanecen intactos"
        );


        ReporteSistema reporteSistemaDespues =
                sistema.generarReporteSistema();


        verificar(
                reporteSistemaDespues
                        .getCantidadTransacciones()
                        == 9,
                "Reporte general conserva historial completo"
        );


        verificar(
                reporteSistemaDespues
                        .getTransaccionesActivas()
                        == 8,
                "Ocho transacciones únicas siguen activas"
        );


        verificar(
                reporteSistemaDespues
                        .getTransaccionesRevertidas()
                        == 1,
                "Reporte general detecta una reversión"
        );


        verificarDouble(
                reporteSistemaDespues
                        .getSaldoTotalSistema(),
                21000,
                "Reversión de transferencia no cambia saldo global"
        );


        verificar(
                reporteSistemaDespues
                        .isIntegridadValida(),
                "Sistema continúa íntegro"
        );


        // -----------------------------------------------------
        // 26. RESULTADO FINAL
        // -----------------------------------------------------

        System.out.println(
                "\n========== REPORTE FINAL C001 =========="
        );


        System.out.println(
                reporteDespues
        );


        System.out.println(
                "\n========== REPORTE FINAL SISTEMA =========="
        );


        System.out.println(
                reporteSistemaDespues
        );


        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "PRUEBA INTEGRAL TERMINADA CORRECTAMENTE"
        );

        System.out.println(
                "Pruebas superadas: "
                        + pruebasCorrectas
        );

        System.out.println(
                "=========================================="
        );
    }


    // ---------------------------------------------------------
    // VERIFICAR BOOLEAN
    // ---------------------------------------------------------

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


    // ---------------------------------------------------------
    // VERIFICAR DOUBLE
    // ---------------------------------------------------------

    private static void verificarDouble(
            Double actual,
            double esperado,
            String descripcion) {

        if (actual == null) {

            throw new AssertionError(
                    "PRUEBA FALLIDA: "
                            + descripcion
                            + " - valor null"
            );
        }


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