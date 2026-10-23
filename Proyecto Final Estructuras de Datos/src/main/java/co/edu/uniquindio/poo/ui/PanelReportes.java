package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.ReporteCliente;
import co.edu.uniquindio.poo.model.ReporteSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class PanelReportes extends JPanel {

    private static final Color AZUL =
            new Color(20, 65, 120);

    private static final Color FONDO =
            new Color(245, 247, 250);

    private static final Color BLANCO =
            Color.WHITE;

    private static final Color TEXTO =
            new Color(35, 43, 55);

    private static final Color SECUNDARIO =
            new Color(105, 115, 130);


    private final MonederoVirtual sistema;


    private JComboBox<String> cmbCliente;

    private DefaultTableModel modeloSistema;

    private DefaultTableModel modeloCliente;


    public PanelReportes(
            MonederoVirtual sistema) {

        this.sistema =
                sistema;

        configurar();

        construir();

        actualizarDatos();
    }


    // ---------------------------------------------------------
    // CONFIGURACIÓN
    // ---------------------------------------------------------

    private void configurar() {

        setLayout(
                new BorderLayout()
        );

        setBackground(
                FONDO
        );

        setBorder(
                new EmptyBorder(
                        22,
                        32,
                        28,
                        32
                )
        );
    }


    // ---------------------------------------------------------
    // CONSTRUIR
    // ---------------------------------------------------------

    private void construir() {

        JTabbedPane tabs =
                new JTabbedPane();

        tabs.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        tabs.addTab(
                "Reporte del sistema",
                crearReporteSistema()
        );


        tabs.addTab(
                "Reporte por cliente",
                crearReporteCliente()
        );


        add(
                tabs,
                BorderLayout.CENTER
        );
    }


    // ---------------------------------------------------------
    // REPORTE SISTEMA
    // ---------------------------------------------------------

    private JPanel crearReporteSistema() {

        JPanel panel =
                crearTarjeta();


        JPanel encabezado =
                crearEncabezado(
                        "Reporte general del sistema",
                        "Resumen consolidado de clientes, saldos, transacciones y estado de integridad."
                );


        panel.add(
                encabezado,
                BorderLayout.NORTH
        );


        modeloSistema =
                crearModelo();


        JTable tabla =
                crearTabla(
                        modeloSistema
                );


        panel.add(
                new JScrollPane(
                        tabla
                ),
                BorderLayout.CENTER
        );


        return envolver(
                panel
        );
    }


    // ---------------------------------------------------------
    // REPORTE CLIENTE
    // ---------------------------------------------------------

    private JPanel crearReporteCliente() {

        JPanel panel =
                crearTarjeta();


        JPanel superior =
                new JPanel(
                        new BorderLayout()
                );


        superior.setOpaque(
                false
        );


        superior.add(
                crearEncabezado(
                        "Reporte individual",
                        "Consulta la información consolidada de un cliente."
                ),
                BorderLayout.WEST
        );


        JPanel seleccion =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        seleccion.setOpaque(
                false
        );


        seleccion.add(
                new JLabel(
                        "Cliente:"
                )
        );


        cmbCliente =
                new JComboBox<>();


        cmbCliente.setPreferredSize(
                new Dimension(
                        180,
                        32
                )
        );


        cmbCliente.addActionListener(
                e -> actualizarReporteCliente()
        );


        seleccion.add(
                cmbCliente
        );


        superior.add(
                seleccion,
                BorderLayout.EAST
        );


        panel.add(
                superior,
                BorderLayout.NORTH
        );


        modeloCliente =
                crearModelo();


        JTable tabla =
                crearTabla(
                        modeloCliente
                );


        panel.add(
                new JScrollPane(
                        tabla
                ),
                BorderLayout.CENTER
        );


        return envolver(
                panel
        );
    }


    // ---------------------------------------------------------
    // ACTUALIZAR
    // ---------------------------------------------------------

    public void actualizarDatos() {

        actualizarClientes();

        actualizarReporteSistema();

        actualizarReporteCliente();
    }


    private void actualizarClientes() {

        Object seleccionAnterior =
                cmbCliente
                        .getSelectedItem();


        cmbCliente
                .removeAllItems();


        List<Cliente> clientes =
                sistema.obtenerClientes();


        clientes.sort(
                Comparator.comparing(
                        Cliente::getIdentificacion
                )
        );


        for (Cliente cliente :
                clientes) {

            cmbCliente.addItem(
                    cliente.getIdentificacion()
            );
        }


        if (seleccionAnterior != null) {

            cmbCliente.setSelectedItem(
                    seleccionAnterior
            );
        }
    }


    // ---------------------------------------------------------
    // SISTEMA
    // ---------------------------------------------------------

    private void actualizarReporteSistema() {

        modeloSistema.setRowCount(
                0
        );


        ReporteSistema reporte =
                sistema.generarReporteSistema();


        agregarFila(
                modeloSistema,
                "Clientes",
                reporte.getCantidadClientes()
        );


        agregarFila(
                modeloSistema,
                "Monederos",
                reporte.getCantidadMonederos()
        );


        agregarFila(
                modeloSistema,
                "Saldo total actual",
                moneda(
                        reporte.getSaldoTotalSistema()
                )
        );


        agregarFila(
                modeloSistema,
                "Transacciones únicas",
                reporte.getCantidadTransacciones()
        );


        agregarFila(
                modeloSistema,
                "Transacciones activas",
                reporte.getTransaccionesActivas()
        );


        agregarFila(
                modeloSistema,
                "Transacciones revertidas",
                reporte.getTransaccionesRevertidas()
        );


        agregarFila(
                modeloSistema,
                "Total de gastos",
                moneda(
                        reporte.getTotalGastos()
                )
        );


        agregarFila(
                modeloSistema,
                "Beneficios canjeados",
                reporte.getCantidadBeneficios()
        );


        agregarFila(
                modeloSistema,
                "Notificaciones",
                reporte.getCantidadNotificaciones()
        );


        agregarFila(
                modeloSistema,
                "Integridad",
                reporte.isIntegridadValida()
                        ?
                        "VÁLIDA"
                        :
                        "REVISAR"
        );
    }


    // ---------------------------------------------------------
    // CLIENTE
    // ---------------------------------------------------------

    private void actualizarReporteCliente() {

        modeloCliente.setRowCount(
                0
        );


        if (cmbCliente
                .getSelectedItem()
                == null) {

            return;
        }


        String identificacion =
                cmbCliente
                        .getSelectedItem()
                        .toString();


        ReporteCliente reporte =
                sistema.generarReporteCliente(
                        identificacion
                );


        if (reporte == null) {
            return;
        }


        agregarFila(
                modeloCliente,
                "Identificación",
                reporte.getIdentificacion()
        );


        agregarFila(
                modeloCliente,
                "Nombre",
                reporte.getNombre()
        );


        agregarFila(
                modeloCliente,
                "Monederos",
                reporte.getCantidadMonederos()
        );


        agregarFila(
                modeloCliente,
                "Saldo total",
                moneda(
                        reporte.getSaldoTotal()
                )
        );


        agregarFila(
                modeloCliente,
                "Transacciones",
                reporte.getCantidadTransacciones()
        );


        agregarFila(
                modeloCliente,
                "Transacciones activas",
                reporte.getTransaccionesActivas()
        );


        agregarFila(
                modeloCliente,
                "Transacciones revertidas",
                reporte.getTransaccionesRevertidas()
        );


        agregarFila(
                modeloCliente,
                "Puntos",
                reporte.getPuntos()
        );


        agregarFila(
                modeloCliente,
                "Rango",
                reporte.getRango()
        );


        agregarFila(
                modeloCliente,
                "Total de gastos",
                moneda(
                        reporte.getTotalGastos()
                )
        );


        agregarFila(
                modeloCliente,
                "Categoría de mayor gasto",
                reporte.getCategoriaMayorGasto()
                        == null
                        ?
                        "-"
                        :
                        reporte.getCategoriaMayorGasto()
        );


        agregarFila(
                modeloCliente,
                "Total transferido",
                moneda(
                        reporte.getTotalTransferido()
                )
        );


        agregarFila(
                modeloCliente,
                "Beneficios canjeados",
                reporte.getCantidadBeneficios()
        );


        agregarFila(
                modeloCliente,
                "Notificaciones",
                reporte.getCantidadNotificaciones()
        );


        agregarFila(
                modeloCliente,
                "Integridad",
                reporte.isIntegridadValida()
                        ?
                        "VÁLIDA"
                        :
                        "REVISAR"
        );
    }


    // ---------------------------------------------------------
    // COMPONENTES
    // ---------------------------------------------------------

    private DefaultTableModel crearModelo() {

        return new DefaultTableModel(
                new Object[]{
                        "Indicador",
                        "Valor"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };
    }


    private JTable crearTabla(
            DefaultTableModel modelo) {

        JTable tabla =
                new JTable(
                        modelo
                );


        tabla.setRowHeight(
                31
        );


        tabla.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        tabla.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );


        tabla.getTableHeader()
                .setReorderingAllowed(
                        false
                );


        tabla.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        320
                );


        tabla.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        320
                );


        return tabla;
    }


    private JPanel crearEncabezado(
            String titulo,
            String descripcion) {

        JPanel panel =
                new JPanel();


        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );


        panel.setOpaque(
                false
        );


        JLabel lblTitulo =
                new JLabel(
                        titulo
                );


        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        lblTitulo.setForeground(
                TEXTO
        );


        JLabel lblDescripcion =
                new JLabel(
                        descripcion
                );


        lblDescripcion.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        lblDescripcion.setForeground(
                SECUNDARIO
        );


        panel.add(
                lblTitulo
        );


        panel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );


        panel.add(
                lblDescripcion
        );


        panel.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );


        return panel;
    }


    private JPanel crearTarjeta() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );


        panel.setBackground(
                BLANCO
        );


        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        236
                                )
                        ),
                        new EmptyBorder(
                                22,
                                24,
                                22,
                                24
                        )
                )
        );


        return panel;
    }


    private JPanel envolver(
            JPanel tarjeta) {

        JPanel fondo =
                new JPanel(
                        new BorderLayout()
                );


        fondo.setBackground(
                FONDO
        );


        fondo.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );


        fondo.add(
                tarjeta,
                BorderLayout.CENTER
        );


        return fondo;
    }


    private void agregarFila(
            DefaultTableModel modelo,
            String indicador,
            Object valor) {

        modelo.addRow(
                new Object[]{
                        indicador,
                        valor
                }
        );
    }


    private String moneda(
            double valor) {

        return String.format(
                "$ %,.2f",
                valor
        );
    }
}