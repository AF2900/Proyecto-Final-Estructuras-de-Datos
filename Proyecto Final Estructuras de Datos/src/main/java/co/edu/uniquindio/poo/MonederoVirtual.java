package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.enums.TipoNotificacion;
import co.edu.uniquindio.poo.estructuras.grafo.AristaGrafo;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Notificacion;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;
import co.edu.uniquindio.poo.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonederoVirtual {

    private final GestorClientes
            gestorClientes;

    private final GestorPuntos
            gestorPuntos;

    private final GestorNotificaciones
            gestorNotificaciones;

    private final GestorGrafos
            gestorGrafos;

    private final GestorPatronesGasto
            gestorPatronesGasto;

    private final GestorIntegridad
            gestorIntegridad;

    private final GestorReversiones
            gestorReversiones;

    private final GestorTransacciones
            gestorTransacciones;

    private final GestorProgramadas
            gestorProgramadas;


    public MonederoVirtual() {

        gestorClientes =
                new GestorClientes();


        gestorPuntos =
                new GestorPuntos();


        gestorNotificaciones =
                new GestorNotificaciones();


        gestorGrafos =
                new GestorGrafos();


        gestorPatronesGasto =
                new GestorPatronesGasto();


        gestorIntegridad =
                new GestorIntegridad();


        gestorReversiones =
                new GestorReversiones(
                        gestorClientes,
                        gestorPuntos,
                        gestorGrafos,
                        gestorPatronesGasto
                );


        gestorTransacciones =
                new GestorTransacciones(
                        gestorClientes,
                        gestorReversiones,
                        gestorPuntos,
                        gestorNotificaciones,
                        gestorGrafos,
                        gestorPatronesGasto
                );


        gestorProgramadas =
                new GestorProgramadas(
                        gestorClientes,
                        gestorTransacciones
                );
    }


    // ---------------------------------------------------------
    // CLIENTES
    // ---------------------------------------------------------

    public boolean registrarCliente(
            Cliente cliente) {

        boolean registrado =
                gestorClientes
                        .registrarCliente(
                                cliente
                        );


        if (!registrado) {
            return false;
        }


        gestorPuntos.registrarCliente(
                cliente
        );


        gestorNotificaciones
                .registrarCliente(
                        cliente.getIdentificacion()
                );


        gestorGrafos
                .registrarCliente(
                        cliente.getIdentificacion()
                );


        gestorPatronesGasto
                .registrarCliente(
                        cliente.getIdentificacion()
                );


        return true;
    }


    public Cliente buscarCliente(
            String identificacion) {

        return gestorClientes
                .buscarCliente(
                        identificacion
                );
    }


    public List<Cliente>
    obtenerClientes() {

        return gestorClientes
                .obtenerClientes();
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    public boolean crearMonedero(
            String identificacionCliente,
            Monedero monedero) {

        boolean creado =
                gestorClientes
                        .crearMonedero(
                                identificacionCliente,
                                monedero
                        );


        if (creado) {

            gestorGrafos
                    .registrarMonedero(
                            identificacionCliente,
                            monedero.getId()
                    );
        }


        return creado;
    }


    public Monedero buscarMonedero(
            String identificacionCliente,
            String idMonedero) {

        return gestorClientes
                .buscarMonedero(
                        identificacionCliente,
                        idMonedero
                );
    }


    public Double consultarSaldo(
            String identificacionCliente,
            String idMonedero) {

        Monedero monedero =
                buscarMonedero(
                        identificacionCliente,
                        idMonedero
                );


        if (monedero == null) {
            return null;
        }


        return monedero.getSaldo();
    }


    // ---------------------------------------------------------
    // TRANSACCIONES
    // ---------------------------------------------------------

    public Transaccion depositar(
            String identificacionCliente,
            String idMonedero,
            double monto) {

        return gestorTransacciones
                .depositar(
                        identificacionCliente,
                        idMonedero,
                        monto
                );
    }


    public Transaccion retirar(
            String identificacionCliente,
            String idMonedero,
            double monto) {

        return gestorTransacciones
                .retirar(
                        identificacionCliente,
                        idMonedero,
                        monto
                );
    }


    public Transaccion transferir(
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto) {

        return gestorTransacciones
                .transferir(
                        identificacionOrigen,
                        monederoOrigen,
                        identificacionDestino,
                        monederoDestino,
                        monto
                );
    }


    public Transaccion realizarGasto(
            String identificacionCliente,
            String idMonedero,
            double monto,
            CategoriaGasto categoria) {

        return gestorTransacciones
                .realizarGasto(
                        identificacionCliente,
                        idMonedero,
                        monto,
                        categoria
                );
    }


    // ---------------------------------------------------------
    // TRANSACCIONES PROGRAMADAS
    // ---------------------------------------------------------

    public boolean programarDeposito(
            String codigo,
            String identificacionCliente,
            String idMonedero,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias) {

        boolean programada =
                gestorProgramadas
                        .programarDeposito(
                                codigo,
                                identificacionCliente,
                                idMonedero,
                                monto,
                                fechaEjecucion,
                                frecuenciaDias
                        );


        if (programada) {

            gestorNotificaciones
                    .enviarNotificacion(
                            identificacionCliente,
                            TipoNotificacion
                                    .TRANSACCION_PROGRAMADA,
                            "Depósito programado para "
                                    + fechaEjecucion
                    );
        }


        return programada;
    }


    public boolean programarTransferencia(
            String codigo,
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino,
            double monto,
            LocalDateTime fechaEjecucion,
            int frecuenciaDias) {

        boolean programada =
                gestorProgramadas
                        .programarTransferencia(
                                codigo,
                                identificacionOrigen,
                                monederoOrigen,
                                identificacionDestino,
                                monederoDestino,
                                monto,
                                fechaEjecucion,
                                frecuenciaDias
                        );


        if (programada) {

            gestorNotificaciones
                    .enviarNotificacion(
                            identificacionOrigen,
                            TipoNotificacion
                                    .TRANSACCION_PROGRAMADA,
                            "Transferencia programada para "
                                    + fechaEjecucion
                    );
        }


        return programada;
    }


    public TransaccionProgramada
    verSiguienteProgramada() {

        return gestorProgramadas
                .verSiguienteProgramada();
    }


    public TransaccionProgramada buscarProgramada(
            String codigo) {

        return gestorProgramadas
                .buscarProgramada(
                        codigo
                );
    }


    public List<TransaccionProgramada>
    obtenerProgramadasEnOrden() {

        return gestorProgramadas
                .obtenerProgramadasEnOrden();
    }


    public int cantidadProgramadas() {

        return gestorProgramadas
                .cantidadProgramadas();
    }


    public boolean programadasVacia() {

        return gestorProgramadas
                .estaVacia();
    }


    public boolean cancelarProgramada(
            String codigo) {

        return gestorProgramadas
                .cancelarProgramada(
                        codigo
                );
    }


    public List<Transaccion>
    procesarTransaccionesProgramadas(
            LocalDateTime fechaActual) {

        return gestorProgramadas
                .procesarHasta(
                        fechaActual
                );
    }


    // ---------------------------------------------------------
    // NOTIFICACIONES
    // ---------------------------------------------------------

    public Notificacion enviarNotificacion(
            String identificacionCliente,
            TipoNotificacion tipo,
            String mensaje) {

        return gestorNotificaciones
                .enviarNotificacion(
                        identificacionCliente,
                        tipo,
                        mensaje
                );
    }


    public Notificacion verSiguienteNotificacion(
            String identificacionCliente) {

        return gestorNotificaciones
                .verSiguienteNotificacion(
                        identificacionCliente
                );
    }


    public List<Notificacion>
    obtenerNotificaciones(
            String identificacionCliente) {

        return gestorNotificaciones
                .obtenerNotificaciones(
                        identificacionCliente
                );
    }


    public int cantidadNotificaciones(
            String identificacionCliente) {

        return gestorNotificaciones
                .cantidadNotificaciones(
                        identificacionCliente
                );
    }


    public boolean marcarNotificacionComoLeida(
            String identificacionCliente,
            String codigoNotificacion) {

        return gestorNotificaciones
                .marcarComoLeida(
                        identificacionCliente,
                        codigoNotificacion
                );
    }


    // ---------------------------------------------------------
    // GASTOS Y PATRONES
    // ---------------------------------------------------------

    public double obtenerTotalGastos(
            String identificacionCliente) {

        return gestorPatronesGasto
                .obtenerTotalGastos(
                        identificacionCliente
                );
    }


    public double obtenerTotalCategoria(
            String identificacionCliente,
            CategoriaGasto categoria) {

        return gestorPatronesGasto
                .obtenerTotalCategoria(
                        identificacionCliente,
                        categoria
                );
    }


    public CategoriaGasto obtenerCategoriaMayorGasto(
            String identificacionCliente) {

        return gestorPatronesGasto
                .obtenerCategoriaMayorGasto(
                        identificacionCliente
                );
    }


    public CategoriaGasto obtenerUltimaCategoriaGasto(
            String identificacionCliente) {

        return gestorPatronesGasto
                .obtenerUltimaCategoria(
                        identificacionCliente
                );
    }


    public double obtenerMontoTransicionCategorias(
            CategoriaGasto origen,
            CategoriaGasto destino) {

        return gestorPatronesGasto
                .obtenerMontoTransicion(
                        origen,
                        destino
                );
    }


    public int obtenerCantidadTransicionesCategorias(
            CategoriaGasto origen,
            CategoriaGasto destino) {

        return gestorPatronesGasto
                .obtenerCantidadTransiciones(
                        origen,
                        destino
                );
    }


    public String obtenerSiguienteCategoriaPrincipal(
            CategoriaGasto categoria) {

        return gestorPatronesGasto
                .obtenerSiguienteCategoriaPrincipal(
                        categoria
                );
    }


    // ---------------------------------------------------------
    // GRAFO DE CLIENTES
    // ---------------------------------------------------------

    public List<AristaGrafo>
    obtenerRelacionesCliente(
            String identificacionCliente) {

        return gestorGrafos
                .obtenerRelacionesCliente(
                        identificacionCliente
                );
    }


    public double obtenerMontoTransferido(
            String origen,
            String destino) {

        return gestorGrafos
                .obtenerMontoTransferido(
                        origen,
                        destino
                );
    }


    public int obtenerCantidadTransferencias(
            String origen,
            String destino) {

        return gestorGrafos
                .obtenerCantidadTransferencias(
                        origen,
                        destino
                );
    }


    public double obtenerTotalTransferidoPorCliente(
            String identificacionCliente) {

        return gestorGrafos
                .obtenerTotalTransferidoPorCliente(
                        identificacionCliente
                );
    }


    public String obtenerPrincipalDestinoCliente(
            String identificacionCliente) {

        return gestorGrafos
                .obtenerPrincipalDestinoCliente(
                        identificacionCliente
                );
    }


    public List<String> recorridoClientes(
            String identificacionInicial) {

        return gestorGrafos
                .recorridoClientes(
                        identificacionInicial
                );
    }


    public boolean existeRelacionEntreClientes(
            String origen,
            String destino) {

        return gestorGrafos
                .existeRelacionEntreClientes(
                        origen,
                        destino
                );
    }


    // ---------------------------------------------------------
    // GRAFO DE MONEDEROS
    // ---------------------------------------------------------

    public List<AristaGrafo>
    obtenerRelacionesMonedero(
            String identificacionCliente,
            String idMonedero) {

        return gestorGrafos
                .obtenerRelacionesMonedero(
                        identificacionCliente,
                        idMonedero
                );
    }


    public double obtenerMontoEntreMonederos(
            String identificacionOrigen,
            String monederoOrigen,
            String identificacionDestino,
            String monederoDestino) {

        return gestorGrafos
                .obtenerMontoEntreMonederos(
                        identificacionOrigen,
                        monederoOrigen,
                        identificacionDestino,
                        monederoDestino
                );
    }


    // ---------------------------------------------------------
    // REVERSIONES
    // ---------------------------------------------------------

    public Transaccion
    verUltimaTransaccionReversible() {

        return gestorReversiones
                .verUltimaTransaccion();
    }


    public Transaccion
    revertirUltimaTransaccion() {

        return gestorReversiones
                .revertirUltimaTransaccion();
    }


    public int cantidadTransaccionesReversibles() {

        return gestorReversiones
                .cantidadTransacciones();
    }


    public boolean pilaReversionesVacia() {

        return gestorReversiones
                .estaVacia();
    }


    // ---------------------------------------------------------
    // HISTORIAL
    // ---------------------------------------------------------

    public List<Transaccion> obtenerHistorial(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {

            return new ArrayList<>();
        }


        return cliente
                .obtenerHistorialTransacciones();
    }


    // ---------------------------------------------------------
    // INTEGRIDAD RECURSIVA
    // ---------------------------------------------------------

    public boolean verificarIntegridadCliente(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        return gestorIntegridad
                .verificarIntegridadCliente(
                        cliente
                );
    }


    public boolean verificarIntegridadSistema() {

        return gestorIntegridad
                .verificarIntegridadSistema(
                        obtenerClientes()
                );
    }


    public int contarTransaccionesValidas(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        return gestorIntegridad
                .contarTransaccionesValidas(
                        cliente
                );
    }


    public Transaccion
    buscarPrimeraTransaccionInvalida(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        return gestorIntegridad
                .buscarPrimeraTransaccionInvalida(
                        cliente
                );
    }


    // ---------------------------------------------------------
    // PUNTOS Y RANGOS
    // ---------------------------------------------------------

    public Integer consultarPuntos(
            String identificacionCliente) {

        return gestorPuntos
                .consultarPuntos(
                        identificacionCliente
                );
    }


    public RangoCliente consultarRango(
            String identificacionCliente) {

        Cliente cliente =
                buscarCliente(
                        identificacionCliente
                );


        if (cliente == null) {
            return null;
        }


        return cliente.getRango();
    }


    public List<Cliente>
    obtenerClientesOrdenadosPorPuntosBST() {

        return gestorPuntos
                .obtenerClientesOrdenadosPorPuntosBST();
    }


    public List<Cliente>
    obtenerClientesOrdenadosPorPuntosAVL() {

        return gestorPuntos
                .obtenerClientesOrdenadosPorPuntosAVL();
    }


    public int obtenerAlturaBST() {

        return gestorPuntos
                .obtenerAlturaBST();
    }


    public int obtenerAlturaAVL() {

        return gestorPuntos
                .obtenerAlturaAVL();
    }
}