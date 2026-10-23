package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class PanelClientesMonederos extends JPanel {

    // ---------------------------------------------------------
    // COLORES
    // ---------------------------------------------------------

    private static final Color AZUL_PRINCIPAL =
            new Color(20, 65, 120);

    private static final Color AZUL_CLARO =
            new Color(230, 240, 250);

    private static final Color FONDO =
            new Color(245, 247, 250);

    private static final Color BLANCO =
            Color.WHITE;

    private static final Color TEXTO_PRINCIPAL =
            new Color(35, 43, 55);

    private static final Color TEXTO_SECUNDARIO =
            new Color(105, 115, 130);


    // ---------------------------------------------------------
    // SISTEMA
    // ---------------------------------------------------------

    private final MonederoVirtual sistema;

    private final Runnable alActualizarDatos;


    // ---------------------------------------------------------
    // REGISTRO CLIENTE
    // ---------------------------------------------------------

    private JTextField txtIdentificacion;

    private JTextField txtNombre;


    // ---------------------------------------------------------
    // CREAR MONEDERO
    // ---------------------------------------------------------

    private JComboBox<String> cmbClienteMonedero;

    private JTextField txtIdMonedero;

    private JTextField txtNombreMonedero;

    private JTextField txtSaldoInicial;


    // ---------------------------------------------------------
    // TABLAS
    // ---------------------------------------------------------

    private DefaultTableModel modeloClientes;

    private DefaultTableModel modeloMonederos;


    // ---------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------

    public PanelClientesMonederos(
            MonederoVirtual sistema,
            Runnable alActualizarDatos) {

        this.sistema =
                sistema;

        this.alActualizarDatos =
                alActualizarDatos;

        configurarPanel();

        construirInterfaz();

        actualizarDatos();
    }


    // ---------------------------------------------------------
    // CONFIGURAR PANEL
    // ---------------------------------------------------------

    private void configurarPanel() {

        setLayout(
                new BorderLayout(
                        0,
                        22
                )
        );

        setBackground(
                FONDO
        );

        setBorder(
                new EmptyBorder(
                        25,
                        32,
                        30,
                        32
                )
        );
    }


    // ---------------------------------------------------------
    // CONSTRUIR INTERFAZ
    // ---------------------------------------------------------

    private void construirInterfaz() {

        JPanel formularios =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );

        formularios.setOpaque(
                false
        );


        formularios.add(
                crearFormularioCliente()
        );


        formularios.add(
                crearFormularioMonedero()
        );


        add(
                formularios,
                BorderLayout.NORTH
        );


        JPanel tablas =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );

        tablas.setOpaque(
                false
        );


        tablas.add(
                crearTablaClientes()
        );


        tablas.add(
                crearTablaMonederos()
        );


        add(
                tablas,
                BorderLayout.CENTER
        );
    }


    // ---------------------------------------------------------
    // FORMULARIO CLIENTE
    // ---------------------------------------------------------

    private JPanel crearFormularioCliente() {

        JPanel tarjeta =
                crearTarjeta();


        JPanel contenido =
                new JPanel(
                        new GridBagLayout()
                );

        contenido.setOpaque(
                false
        );


        GridBagConstraints gbc =
                crearRestricciones();


        JLabel titulo =
                crearTitulo(
                        "Registrar cliente"
                );


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        contenido.add(
                titulo,
                gbc
        );


        gbc.gridwidth = 1;


        // -----------------------------------------------------
        // IDENTIFICACIÓN
        // -----------------------------------------------------

        gbc.gridy++;
        gbc.gridx = 0;

        contenido.add(
                crearEtiqueta(
                        "Identificación"
                ),
                gbc
        );


        txtIdentificacion =
                crearCampoTexto();


        gbc.gridx = 1;

        contenido.add(
                txtIdentificacion,
                gbc
        );


        // -----------------------------------------------------
        // NOMBRE
        // -----------------------------------------------------

        gbc.gridy++;
        gbc.gridx = 0;

        contenido.add(
                crearEtiqueta(
                        "Nombre"
                ),
                gbc
        );


        txtNombre =
                crearCampoTexto();


        gbc.gridx = 1;

        contenido.add(
                txtNombre,
                gbc
        );


        // -----------------------------------------------------
        // BOTÓN
        // -----------------------------------------------------

        JButton btnRegistrar =
                crearBotonPrincipal(
                        "Registrar cliente"
                );


        btnRegistrar.addActionListener(
                e -> registrarCliente()
        );


        gbc.gridy++;
        gbc.gridx = 1;
        gbc.insets =
                new Insets(
                        16,
                        8,
                        5,
                        0
                );

        contenido.add(
                btnRegistrar,
                gbc
        );


        tarjeta.add(
                contenido,
                BorderLayout.CENTER
        );


        return tarjeta;
    }


    // ---------------------------------------------------------
    // FORMULARIO MONEDERO
    // ---------------------------------------------------------

    private JPanel crearFormularioMonedero() {

        JPanel tarjeta =
                crearTarjeta();


        JPanel contenido =
                new JPanel(
                        new GridBagLayout()
                );

        contenido.setOpaque(
                false
        );


        GridBagConstraints gbc =
                crearRestricciones();


        JLabel titulo =
                crearTitulo(
                        "Crear monedero"
                );


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        contenido.add(
                titulo,
                gbc
        );


        gbc.gridwidth = 1;


        // -----------------------------------------------------
        // CLIENTE
        // -----------------------------------------------------

        gbc.gridy++;
        gbc.gridx = 0;

        contenido.add(
                crearEtiqueta(
                        "Cliente"
                ),
                gbc
        );


        cmbClienteMonedero =
                new JComboBox<>();

        configurarCombo(
                cmbClienteMonedero
        );


        gbc.gridx = 1;

        contenido.add(
                cmbClienteMonedero,
                gbc
        );


        // -----------------------------------------------------
        // ID MONEDERO
        // -----------------------------------------------------

        gbc.gridy++;
        gbc.gridx = 0;

        contenido.add(
                crearEtiqueta(
                        "Código"
                ),
                gbc
        );


        txtIdMonedero =
                crearCampoTexto();


        gbc.gridx = 1;

        contenido.add(
                txtIdMonedero,
                gbc
        );


        // -----------------------------------------------------
        // NOMBRE MONEDERO
        // -----------------------------------------------------

        gbc.gridy++;
        gbc.gridx = 0;

        contenido.add(
                crearEtiqueta(
                        "Nombre"
                ),
                gbc
        );


        txtNombreMonedero =
                crearCampoTexto();


        gbc.gridx = 1;

        contenido.add(
                txtNombreMonedero,
                gbc
        );


        // -----------------------------------------------------
        // SALDO
        // -----------------------------------------------------

        gbc.gridy++;
        gbc.gridx = 0;

        contenido.add(
                crearEtiqueta(
                        "Saldo inicial"
                ),
                gbc
        );


        txtSaldoInicial =
                crearCampoTexto();

        txtSaldoInicial.setText(
                "0"
        );


        gbc.gridx = 1;

        contenido.add(
                txtSaldoInicial,
                gbc
        );


        // -----------------------------------------------------
        // BOTÓN
        // -----------------------------------------------------

        JButton btnCrear =
                crearBotonPrincipal(
                        "Crear monedero"
                );


        btnCrear.addActionListener(
                e -> crearMonedero()
        );


        gbc.gridy++;
        gbc.gridx = 1;
        gbc.insets =
                new Insets(
                        16,
                        8,
                        5,
                        0
                );

        contenido.add(
                btnCrear,
                gbc
        );


        tarjeta.add(
                contenido,
                BorderLayout.CENTER
        );


        return tarjeta;
    }


    // ---------------------------------------------------------
    // TABLA CLIENTES
    // ---------------------------------------------------------

    private JPanel crearTablaClientes() {

        modeloClientes =
                new DefaultTableModel(
                        new Object[]{
                                "Identificación",
                                "Nombre",
                                "Puntos",
                                "Rango",
                                "Monederos"
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


        JTable tabla =
                crearTabla(
                        modeloClientes
                );


        return crearPanelTabla(
                "Clientes registrados",
                tabla
        );
    }


    // ---------------------------------------------------------
    // TABLA MONEDEROS
    // ---------------------------------------------------------

    private JPanel crearTablaMonederos() {

        modeloMonederos =
                new DefaultTableModel(
                        new Object[]{
                                "Cliente",
                                "Código",
                                "Nombre",
                                "Saldo"
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


        JTable tabla =
                crearTabla(
                        modeloMonederos
                );


        return crearPanelTabla(
                "Monederos registrados",
                tabla
        );
    }


    // ---------------------------------------------------------
    // REGISTRAR CLIENTE
    // ---------------------------------------------------------

    private void registrarCliente() {

        String identificacion =
                txtIdentificacion
                        .getText()
                        .trim();


        String nombre =
                txtNombre
                        .getText()
                        .trim();


        if (identificacion.isEmpty() ||
                nombre.isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar la identificación y el nombre."
            );

            return;
        }


        Cliente cliente =
                new Cliente(
                        identificacion,
                        nombre
                );


        boolean registrado =
                sistema.registrarCliente(
                        cliente
                );


        if (!registrado) {

            mostrarError(
                    "No fue posible registrar el cliente.\n"
                            +
                            "Verifique que la identificación no esté repetida."
            );

            return;
        }


        txtIdentificacion.setText(
                ""
        );

        txtNombre.setText(
                ""
        );


        actualizarDatos();

        notificarCambioGeneral();


        mostrarExito(
                "Cliente registrado correctamente."
        );
    }


    // ---------------------------------------------------------
    // CREAR MONEDERO
    // ---------------------------------------------------------

    private void crearMonedero() {

        Object clienteSeleccionado =
                cmbClienteMonedero
                        .getSelectedItem();


        if (clienteSeleccionado == null) {

            mostrarAdvertencia(
                    "Primero debe registrar un cliente."
            );

            return;
        }


        String identificacionCliente =
                clienteSeleccionado
                        .toString();


        String idMonedero =
                txtIdMonedero
                        .getText()
                        .trim();


        String nombre =
                txtNombreMonedero
                        .getText()
                        .trim();


        String textoSaldo =
                txtSaldoInicial
                        .getText()
                        .trim();


        if (idMonedero.isEmpty() ||
                nombre.isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar el código y el nombre del monedero."
            );

            return;
        }


        double saldoInicial;


        try {

            if (textoSaldo.isEmpty()) {

                saldoInicial = 0;

            } else {

                saldoInicial =
                        Double.parseDouble(
                                textoSaldo.replace(
                                        ",",
                                        "."
                                )
                        );
            }

        } catch (NumberFormatException e) {

            mostrarAdvertencia(
                    "El saldo inicial debe ser un número válido."
            );

            return;
        }


        if (saldoInicial < 0) {

            mostrarAdvertencia(
                    "El saldo inicial no puede ser negativo."
            );

            return;
        }


        Monedero monedero =
                new Monedero(
                        idMonedero,
                        nombre,
                        saldoInicial
                );


        boolean creado =
                sistema.crearMonedero(
                        identificacionCliente,
                        monedero
                );


        if (!creado) {

            mostrarError(
                    "No fue posible crear el monedero.\n"
                            +
                            "Verifique que el código no esté repetido para este cliente."
            );

            return;
        }


        txtIdMonedero.setText(
                ""
        );

        txtNombreMonedero.setText(
                ""
        );

        txtSaldoInicial.setText(
                "0"
        );


        actualizarDatos();

        notificarCambioGeneral();


        mostrarExito(
                "Monedero creado correctamente."
        );
    }


    // ---------------------------------------------------------
    // ACTUALIZAR INFORMACIÓN
    // ---------------------------------------------------------

    public void actualizarDatos() {

        actualizarComboClientes();

        actualizarTablaClientes();

        actualizarTablaMonederos();
    }


    // ---------------------------------------------------------
    // COMBO CLIENTES
    // ---------------------------------------------------------

    private void actualizarComboClientes() {

        Object seleccionAnterior =
                cmbClienteMonedero
                        .getSelectedItem();


        cmbClienteMonedero
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

            cmbClienteMonedero
                    .addItem(
                            cliente.getIdentificacion()
                    );
        }


        if (seleccionAnterior != null) {

            cmbClienteMonedero
                    .setSelectedItem(
                            seleccionAnterior
                    );
        }
    }


    // ---------------------------------------------------------
    // ACTUALIZAR TABLA CLIENTES
    // ---------------------------------------------------------

    private void actualizarTablaClientes() {

        modeloClientes.setRowCount(
                0
        );


        List<Cliente> clientes =
                sistema.obtenerClientes();


        clientes.sort(
                Comparator.comparing(
                        Cliente::getIdentificacion
                )
        );


        for (Cliente cliente :
                clientes) {

            modeloClientes.addRow(
                    new Object[]{
                            cliente.getIdentificacion(),
                            cliente.getNombre(),
                            cliente.getPuntos(),
                            cliente.getRango(),
                            cliente
                                    .obtenerMonederos()
                                    .size()
                    }
            );
        }
    }


    // ---------------------------------------------------------
    // ACTUALIZAR TABLA MONEDEROS
    // ---------------------------------------------------------

    private void actualizarTablaMonederos() {

        modeloMonederos.setRowCount(
                0
        );


        List<Cliente> clientes =
                sistema.obtenerClientes();


        clientes.sort(
                Comparator.comparing(
                        Cliente::getIdentificacion
                )
        );


        for (Cliente cliente :
                clientes) {

            List<Monedero> monederos =
                    cliente.obtenerMonederos();


            monederos.sort(
                    Comparator.comparing(
                            Monedero::getId
                    )
            );


            for (Monedero monedero :
                    monederos) {

                modeloMonederos.addRow(
                        new Object[]{
                                cliente.getIdentificacion(),
                                monedero.getId(),
                                monedero.getNombre(),
                                String.format(
                                        "%,.2f",
                                        monedero.getSaldo()
                                )
                        }
                );
            }
        }
    }


    // ---------------------------------------------------------
    // NOTIFICAR CAMBIO GENERAL
    // ---------------------------------------------------------

    private void notificarCambioGeneral() {

        if (alActualizarDatos != null) {

            alActualizarDatos.run();
        }
    }


    // ---------------------------------------------------------
    // TARJETA
    // ---------------------------------------------------------

    private JPanel crearTarjeta() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
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
                                20,
                                24,
                                20,
                                24
                        )
                )
        );


        return panel;
    }


    // ---------------------------------------------------------
    // TITULO
    // ---------------------------------------------------------

    private JLabel crearTitulo(
            String texto) {

        JLabel label =
                new JLabel(
                        texto
                );


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );


        label.setForeground(
                TEXTO_PRINCIPAL
        );


        return label;
    }


    // ---------------------------------------------------------
    // ETIQUETA
    // ---------------------------------------------------------

    private JLabel crearEtiqueta(
            String texto) {

        JLabel label =
                new JLabel(
                        texto
                );


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        label.setForeground(
                TEXTO_SECUNDARIO
        );


        return label;
    }


    // ---------------------------------------------------------
    // CAMPO DE TEXTO
    // ---------------------------------------------------------

    private JTextField crearCampoTexto() {

        JTextField campo =
                new JTextField();


        campo.setPreferredSize(
                new Dimension(
                        210,
                        32
                )
        );


        campo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        return campo;
    }


    // ---------------------------------------------------------
    // COMBO
    // ---------------------------------------------------------

    private void configurarCombo(
            JComboBox<String> combo) {

        combo.setPreferredSize(
                new Dimension(
                        210,
                        32
                )
        );


        combo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );
    }


    // ---------------------------------------------------------
    // BOTÓN PRINCIPAL
    // ---------------------------------------------------------

    private JButton crearBotonPrincipal(
            String texto) {

        JButton boton =
                new JButton(
                        texto
                );


        boton.setPreferredSize(
                new Dimension(
                        170,
                        36
                )
        );


        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        boton.setForeground(
                BLANCO
        );


        boton.setBackground(
                AZUL_PRINCIPAL
        );


        boton.setFocusPainted(
                false
        );


        boton.setOpaque(
                true
        );


        boton.setContentAreaFilled(
                true
        );


        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return boton;
    }


    // ---------------------------------------------------------
    // TABLA
    // ---------------------------------------------------------

    private JTable crearTabla(
            DefaultTableModel modelo) {

        JTable tabla =
                new JTable(
                        modelo
                );


        tabla.setRowHeight(
                28
        );


        tabla.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        tabla.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        tabla.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                12
                        )
                );


        tabla.getTableHeader()
                .setReorderingAllowed(
                        false
                );


        return tabla;
    }


    // ---------------------------------------------------------
    // PANEL TABLA
    // ---------------------------------------------------------

    private JPanel crearPanelTabla(
            String titulo,
            JTable tabla) {

        JPanel panel =
                crearTarjeta();


        JLabel label =
                crearTitulo(
                        titulo
                );


        label.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );


        panel.add(
                label,
                BorderLayout.NORTH
        );


        JScrollPane scroll =
                new JScrollPane(
                        tabla
                );


        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                225,
                                230,
                                236
                        )
                )
        );


        panel.add(
                scroll,
                BorderLayout.CENTER
        );


        return panel;
    }


    // ---------------------------------------------------------
    // RESTRICCIONES GRIDBAG
    // ---------------------------------------------------------

    private GridBagConstraints
    crearRestricciones() {

        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        7,
                        8,
                        7,
                        8
                );


        gbc.anchor =
                GridBagConstraints.WEST;


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        gbc.weightx =
                1;


        return gbc;
    }


    // ---------------------------------------------------------
    // MENSAJES
    // ---------------------------------------------------------

    private void mostrarExito(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Operación realizada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    private void mostrarAdvertencia(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Datos incompletos",
                JOptionPane.WARNING_MESSAGE
        );
    }


    private void mostrarError(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Operación no realizada",
                JOptionPane.ERROR_MESSAGE
        );
    }
}