package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Notificacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class PanelNotificaciones extends JPanel {

    private static final Color AZUL =
            new Color(20, 65, 120);

    private static final Color FONDO =
            new Color(245, 247, 250);

    private static final Color BLANCO =
            Color.WHITE;


    private final MonederoVirtual sistema;

    private final Runnable alActualizarDatos;


    private JComboBox<String> cmbCliente;

    private JLabel lblTotal;

    private JLabel lblPendientes;


    private DefaultTableModel modeloPendientes;

    private DefaultTableModel modeloHistorial;

    private JTable tablaHistorial;


    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
            );


    public PanelNotificaciones(
            MonederoVirtual sistema,
            Runnable alActualizarDatos) {

        this.sistema =
                sistema;

        this.alActualizarDatos =
                alActualizarDatos;


        configurar();

        construir();

        actualizarDatos();
    }


    private void configurar() {

        setLayout(
                new BorderLayout(
                        0,
                        15
                )
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


    private void construir() {

        add(
                crearResumen(),
                BorderLayout.NORTH
        );


        JTabbedPane tabs =
                new JTabbedPane();


        tabs.addTab(
                "Pendientes FIFO",
                crearPendientes()
        );


        tabs.addTab(
                "Historial circular",
                crearHistorial()
        );


        add(
                tabs,
                BorderLayout.CENTER
        );
    }


    private JPanel crearResumen() {

        JPanel panel =
                crearTarjeta();


        panel.setLayout(
                new FlowLayout(
                        FlowLayout.LEFT,
                        18,
                        8
                )
        );


        panel.add(
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
                e -> actualizarTablas()
        );


        panel.add(
                cmbCliente
        );


        lblTotal =
                new JLabel(
                        "Total: 0"
                );


        lblPendientes =
                new JLabel(
                        "Pendientes: 0"
                );


        lblTotal.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        lblPendientes.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        panel.add(
                lblTotal
        );


        panel.add(
                lblPendientes
        );


        JButton btnCircular =
                crearBoton(
                        "Ver siguiente circular"
                );


        btnCircular.addActionListener(
                e -> verSiguienteCircular()
        );


        panel.add(
                btnCircular
        );


        return panel;
    }


    private JPanel crearPendientes() {

        JPanel panel =
                crearTarjeta();


        modeloPendientes =
                crearModelo();


        JTable tabla =
                crearTabla(
                        modeloPendientes
                );


        panel.add(
                new JScrollPane(
                        tabla
                ),
                BorderLayout.CENTER
        );


        JButton btnAtender =
                crearBoton(
                        "Atender siguiente"
                );


        btnAtender.addActionListener(
                e -> atenderSiguiente()
        );


        JPanel pie =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        pie.setOpaque(
                false
        );


        pie.add(
                btnAtender
        );


        panel.add(
                pie,
                BorderLayout.SOUTH
        );


        return panel;
    }


    private JPanel crearHistorial() {

        JPanel panel =
                crearTarjeta();


        modeloHistorial =
                crearModelo();


        tablaHistorial =
                crearTabla(
                        modeloHistorial
                );


        panel.add(
                new JScrollPane(
                        tablaHistorial
                ),
                BorderLayout.CENTER
        );


        JButton btnLeida =
                crearBoton(
                        "Marcar seleccionada como leída"
                );


        btnLeida.addActionListener(
                e -> marcarSeleccionada()
        );


        JPanel pie =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        pie.setOpaque(
                false
        );


        pie.add(
                btnLeida
        );


        panel.add(
                pie,
                BorderLayout.SOUTH
        );


        return panel;
    }


    private DefaultTableModel crearModelo() {

        return new DefaultTableModel(
                new Object[]{
                        "Código",
                        "Fecha",
                        "Tipo",
                        "Mensaje",
                        "Estado"
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
                28
        );


        tabla.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        tabla.getTableHeader()
                .setReorderingAllowed(
                        false
                );


        tabla.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        90
                );


        tabla.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        130
                );


        tabla.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        160
                );


        tabla.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        450
                );


        return tabla;
    }


    private void atenderSiguiente() {

        String cliente =
                obtenerCliente();


        if (cliente == null) {

            mostrarAdvertencia(
                    "Seleccione un cliente."
            );

            return;
        }


        Notificacion notificacion =
                sistema
                        .atenderSiguienteNotificacion(
                                cliente
                        );


        if (notificacion == null) {

            mostrarAdvertencia(
                    "El cliente no tiene notificaciones pendientes."
            );

            return;
        }


        actualizarDatos();


        JOptionPane.showMessageDialog(
                this,
                notificacion.getMensaje(),
                "Notificación atendida",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    private void marcarSeleccionada() {

        String cliente =
                obtenerCliente();


        int fila =
                tablaHistorial
                        .getSelectedRow();


        if (cliente == null ||
                fila < 0) {

            mostrarAdvertencia(
                    "Seleccione una notificación del historial."
            );

            return;
        }


        String codigo =
                modeloHistorial
                        .getValueAt(
                                fila,
                                0
                        )
                        .toString();


        boolean resultado =
                sistema
                        .marcarNotificacionComoLeida(
                                cliente,
                                codigo
                        );


        if (!resultado) {

            mostrarAdvertencia(
                    "No fue posible marcar la notificación."
            );

            return;
        }


        actualizarDatos();
    }


    private void verSiguienteCircular() {

        String cliente =
                obtenerCliente();


        if (cliente == null) {

            mostrarAdvertencia(
                    "Seleccione un cliente."
            );

            return;
        }


        Notificacion notificacion =
                sistema
                        .verSiguienteNotificacion(
                                cliente
                        );


        if (notificacion == null) {

            mostrarAdvertencia(
                    "No existen notificaciones."
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Código: "
                        +
                        notificacion.getCodigo()
                        +
                        "\nTipo: "
                        +
                        notificacion.getTipo()
                        +
                        "\nFecha: "
                        +
                        notificacion
                                .getFecha()
                                .format(
                                        formatoFecha
                                )
                        +
                        "\n\n"
                        +
                        notificacion.getMensaje(),
                "Lista circular",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    public void actualizarDatos() {

        Object seleccion =
                cmbCliente
                        .getSelectedItem();


        cmbCliente.removeAllItems();


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


        if (seleccion != null) {

            cmbCliente.setSelectedItem(
                    seleccion
            );
        }


        actualizarTablas();
    }


    private void actualizarTablas() {

        if (modeloPendientes == null ||
                modeloHistorial == null) {

            return;
        }


        modeloPendientes.setRowCount(
                0
        );


        modeloHistorial.setRowCount(
                0
        );


        String cliente =
                obtenerCliente();


        if (cliente == null) {

            lblTotal.setText(
                    "Total: 0"
            );


            lblPendientes.setText(
                    "Pendientes: 0"
            );


            return;
        }


        List<Notificacion> pendientes =
                sistema
                        .obtenerNotificacionesPendientes(
                                cliente
                        );


        for (Notificacion notificacion :
                pendientes) {

            modeloPendientes.addRow(
                    crearFila(
                            notificacion
                    )
            );
        }


        List<Notificacion> historial =
                sistema
                        .obtenerNotificaciones(
                                cliente
                        );


        for (Notificacion notificacion :
                historial) {

            modeloHistorial.addRow(
                    crearFila(
                            notificacion
                    )
            );
        }


        lblTotal.setText(
                "Total: "
                        +
                        sistema
                                .cantidadNotificaciones(
                                        cliente
                                )
        );


        lblPendientes.setText(
                "Pendientes: "
                        +
                        sistema
                                .cantidadNotificacionesPendientes(
                                        cliente
                                )
        );
    }


    private Object[] crearFila(
            Notificacion notificacion) {

        return new Object[]{
                notificacion.getCodigo(),
                notificacion
                        .getFecha()
                        .format(
                        formatoFecha
                ),
                notificacion.getTipo(),
                notificacion.getMensaje(),
                notificacion.isLeida()
                        ?
                        "LEÍDA"
                        :
                        "PENDIENTE"
        };
    }


    private String obtenerCliente() {

        if (cmbCliente
                .getSelectedItem()
                == null) {

            return null;
        }


        return cmbCliente
                .getSelectedItem()
                .toString();
    }


    private JPanel crearTarjeta() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
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
                                16,
                                18,
                                16,
                                18
                        )
                )
        );


        return panel;
    }


    private JButton crearBoton(
            String texto) {

        JButton boton =
                new JButton(
                        texto
                );


        boton.setBackground(
                AZUL
        );


        boton.setForeground(
                BLANCO
        );


        boton.setFocusPainted(
                false
        );


        return boton;
    }


    private void mostrarAdvertencia(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Notificaciones",
                JOptionPane.WARNING_MESSAGE
        );
    }
}