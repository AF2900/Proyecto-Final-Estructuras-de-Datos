package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class PanelTransacciones extends JPanel {

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

    private static final Color VERDE =
            new Color(35, 145, 95);


    // ---------------------------------------------------------
    // SISTEMA
    // ---------------------------------------------------------

    private final MonederoVirtual sistema;

    private final Runnable alActualizarDatos;


    // ---------------------------------------------------------
    // DEPÓSITO
    // ---------------------------------------------------------

    private JComboBox<String> cmbClienteDeposito;

    private JComboBox<String> cmbMonederoDeposito;

    private JTextField txtMontoDeposito;


    // ---------------------------------------------------------
    // RETIRO
    // ---------------------------------------------------------

    private JComboBox<String> cmbClienteRetiro;

    private JComboBox<String> cmbMonederoRetiro;

    private JTextField txtMontoRetiro;


    // ---------------------------------------------------------
    // TRANSFERENCIA
    // ---------------------------------------------------------

    private JComboBox<String> cmbClienteOrigen;

    private JComboBox<String> cmbMonederoOrigen;

    private JComboBox<String> cmbClienteDestino;

    private JComboBox<String> cmbMonederoDestino;

    private JTextField txtMontoTransferencia;


    // ---------------------------------------------------------
    // GASTO
    // ---------------------------------------------------------

    private JComboBox<String> cmbClienteGasto;

    private JComboBox<String> cmbMonederoGasto;

    private JComboBox<CategoriaGasto> cmbCategoriaGasto;

    private JTextField txtMontoGasto;


    // ---------------------------------------------------------
    // CONSULTAS
    // ---------------------------------------------------------

    private JComboBox<String> cmbClienteConsulta;

    private JComboBox<String> cmbMonederoConsulta;

    private JLabel lblSaldoConsulta;

    private JComboBox<String> cmbClienteHistorial;

    private DefaultTableModel modeloHistorial;


    // ---------------------------------------------------------
    // FECHAS
    // ---------------------------------------------------------

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
            );


    // ---------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------

    public PanelTransacciones(
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
    // CONFIGURAR
    // ---------------------------------------------------------

    private void configurarPanel() {

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

    private void construirInterfaz() {

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
                "Depósito",
                crearPanelDeposito()
        );


        tabs.addTab(
                "Retiro",
                crearPanelRetiro()
        );


        tabs.addTab(
                "Transferencia",
                crearPanelTransferencia()
        );


        tabs.addTab(
                "Gasto",
                crearPanelGasto()
        );


        tabs.addTab(
                "Consultas",
                crearPanelConsultas()
        );


        add(
                tabs,
                BorderLayout.CENTER
        );
    }


    // ---------------------------------------------------------
    // DEPÓSITO
    // ---------------------------------------------------------

    private JPanel crearPanelDeposito() {

        JPanel campos =
                crearPanelCampos();


        cmbClienteDeposito =
                crearComboTexto();


        cmbMonederoDeposito =
                crearComboTexto();


        txtMontoDeposito =
                crearCampoTexto();


        cmbClienteDeposito
                .addActionListener(
                        e -> actualizarMonederos(
                                cmbClienteDeposito,
                                cmbMonederoDeposito
                        )
                );


        agregarCampo(
                campos,
                0,
                "Cliente",
                cmbClienteDeposito
        );


        agregarCampo(
                campos,
                1,
                "Monedero",
                cmbMonederoDeposito
        );


        agregarCampo(
                campos,
                2,
                "Monto",
                txtMontoDeposito
        );


        JButton boton =
                crearBotonPrincipal(
                        "Realizar depósito"
                );


        boton.addActionListener(
                e -> realizarDeposito()
        );


        agregarBoton(
                campos,
                3,
                boton
        );


        return crearFormulario(
                "Realizar depósito",
                "Agrega dinero al monedero y genera puntos según el monto.",
                campos
        );
    }


    // ---------------------------------------------------------
    // RETIRO
    // ---------------------------------------------------------

    private JPanel crearPanelRetiro() {

        JPanel campos =
                crearPanelCampos();


        cmbClienteRetiro =
                crearComboTexto();


        cmbMonederoRetiro =
                crearComboTexto();


        txtMontoRetiro =
                crearCampoTexto();


        cmbClienteRetiro
                .addActionListener(
                        e -> actualizarMonederos(
                                cmbClienteRetiro,
                                cmbMonederoRetiro
                        )
                );


        agregarCampo(
                campos,
                0,
                "Cliente",
                cmbClienteRetiro
        );


        agregarCampo(
                campos,
                1,
                "Monedero",
                cmbMonederoRetiro
        );


        agregarCampo(
                campos,
                2,
                "Monto",
                txtMontoRetiro
        );


        JLabel aviso =
                new JLabel(
                        "El retiro puede incluir comisión del 1 %."
                );


        aviso.setForeground(
                TEXTO_SECUNDARIO
        );


        aviso.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        GridBagConstraints gbc =
                restricciones();


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;


        campos.add(
                aviso,
                gbc
        );


        JButton boton =
                crearBotonPrincipal(
                        "Realizar retiro"
                );


        boton.addActionListener(
                e -> realizarRetiro()
        );


        agregarBoton(
                campos,
                4,
                boton
        );


        return crearFormulario(
                "Realizar retiro",
                "Retira dinero del monedero siempre que exista saldo suficiente.",
                campos
        );
    }


    // ---------------------------------------------------------
    // TRANSFERENCIA
    // ---------------------------------------------------------

    private JPanel crearPanelTransferencia() {

        JPanel campos =
                crearPanelCampos();


        cmbClienteOrigen =
                crearComboTexto();


        cmbMonederoOrigen =
                crearComboTexto();


        cmbClienteDestino =
                crearComboTexto();


        cmbMonederoDestino =
                crearComboTexto();


        txtMontoTransferencia =
                crearCampoTexto();


        cmbClienteOrigen
                .addActionListener(
                        e -> actualizarMonederos(
                                cmbClienteOrigen,
                                cmbMonederoOrigen
                        )
                );


        cmbClienteDestino
                .addActionListener(
                        e -> actualizarMonederos(
                                cmbClienteDestino,
                                cmbMonederoDestino
                        )
                );


        agregarCampo(
                campos,
                0,
                "Cliente origen",
                cmbClienteOrigen
        );


        agregarCampo(
                campos,
                1,
                "Monedero origen",
                cmbMonederoOrigen
        );


        agregarCampo(
                campos,
                2,
                "Cliente destino",
                cmbClienteDestino
        );


        agregarCampo(
                campos,
                3,
                "Monedero destino",
                cmbMonederoDestino
        );


        agregarCampo(
                campos,
                4,
                "Monto",
                txtMontoTransferencia
        );


        JLabel aviso =
                new JLabel(
                        "La comisión se calcula automáticamente según los beneficios activos."
                );


        aviso.setForeground(
                TEXTO_SECUNDARIO
        );


        aviso.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        GridBagConstraints gbc =
                restricciones();


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;


        campos.add(
                aviso,
                gbc
        );


        JButton boton =
                crearBotonPrincipal(
                        "Transferir"
                );


        boton.addActionListener(
                e -> realizarTransferencia()
        );


        agregarBoton(
                campos,
                6,
                boton
        );


        return crearFormulario(
                "Realizar transferencia",
                "Transfiere dinero entre clientes o entre monederos diferentes.",
                campos
        );
    }


    // ---------------------------------------------------------
    // GASTO
    // ---------------------------------------------------------

    private JPanel crearPanelGasto() {

        JPanel campos =
                crearPanelCampos();


        cmbClienteGasto =
                crearComboTexto();


        cmbMonederoGasto =
                crearComboTexto();


        cmbCategoriaGasto =
                new JComboBox<>(
                        CategoriaGasto.values()
                );


        configurarCombo(
                cmbCategoriaGasto
        );


        txtMontoGasto =
                crearCampoTexto();


        cmbClienteGasto
                .addActionListener(
                        e -> actualizarMonederos(
                                cmbClienteGasto,
                                cmbMonederoGasto
                        )
                );


        agregarCampo(
                campos,
                0,
                "Cliente",
                cmbClienteGasto
        );


        agregarCampo(
                campos,
                1,
                "Monedero",
                cmbMonederoGasto
        );


        agregarCampo(
                campos,
                2,
                "Categoría",
                cmbCategoriaGasto
        );


        agregarCampo(
                campos,
                3,
                "Monto",
                txtMontoGasto
        );


        JButton boton =
                crearBotonPrincipal(
                        "Registrar gasto"
                );


        boton.addActionListener(
                e -> realizarGasto()
        );


        agregarBoton(
                campos,
                4,
                boton
        );


        return crearFormulario(
                "Registrar gasto",
                "Registra un gasto y actualiza automáticamente los patrones por categoría.",
                campos
        );
    }


    // ---------------------------------------------------------
    // CONSULTAS
    // ---------------------------------------------------------

    private JPanel crearPanelConsultas() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );


        panel.setBackground(
                FONDO
        );


        panel.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );


        panel.add(
                crearConsultaSaldo(),
                BorderLayout.NORTH
        );


        panel.add(
                crearHistorial(),
                BorderLayout.CENTER
        );


        return panel;
    }


    // ---------------------------------------------------------
    // CONSULTA SALDO
    // ---------------------------------------------------------

    private JPanel crearConsultaSaldo() {

        JPanel tarjeta =
                crearTarjeta();


        JPanel campos =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                12,
                                10
                        )
                );


        campos.setOpaque(
                false
        );


        cmbClienteConsulta =
                crearComboTexto();


        cmbMonederoConsulta =
                crearComboTexto();


        cmbClienteConsulta
                .addActionListener(
                        e -> {

                            actualizarMonederos(
                                    cmbClienteConsulta,
                                    cmbMonederoConsulta
                            );

                            actualizarSaldoConsulta();
                        }
                );


        cmbMonederoConsulta
                .addActionListener(
                        e -> actualizarSaldoConsulta()
                );


        lblSaldoConsulta =
                new JLabel(
                        "$ 0.00"
                );


        lblSaldoConsulta.setForeground(
                AZUL_PRINCIPAL
        );


        lblSaldoConsulta.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );


        JButton btnConsultar =
                crearBotonPrincipal(
                        "Consultar"
                );


        btnConsultar.addActionListener(
                e -> actualizarSaldoConsulta()
        );


        campos.add(
                crearEtiqueta(
                        "Cliente"
                )
        );


        campos.add(
                cmbClienteConsulta
        );


        campos.add(
                crearEtiqueta(
                        "Monedero"
                )
        );


        campos.add(
                cmbMonederoConsulta
        );


        campos.add(
                btnConsultar
        );


        campos.add(
                Box.createHorizontalStrut(
                        18
                )
        );


        campos.add(
                new JLabel(
                        "Saldo:"
                )
        );


        campos.add(
                lblSaldoConsulta
        );


        tarjeta.add(
                crearTitulo(
                        "Consulta de saldo"
                ),
                BorderLayout.NORTH
        );


        tarjeta.add(
                campos,
                BorderLayout.CENTER
        );


        return tarjeta;
    }


    // ---------------------------------------------------------
    // HISTORIAL
    // ---------------------------------------------------------

    private JPanel crearHistorial() {

        JPanel tarjeta =
                crearTarjeta();


        JPanel superior =
                new JPanel(
                        new BorderLayout()
                );


        superior.setOpaque(
                false
        );


        JLabel titulo =
                crearTitulo(
                        "Historial de transacciones"
                );


        superior.add(
                titulo,
                BorderLayout.WEST
        );


        JPanel filtros =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );


        filtros.setOpaque(
                false
        );


        cmbClienteHistorial =
                crearComboTexto();


        cmbClienteHistorial
                .addActionListener(
                        e -> actualizarHistorial()
                );


        JButton btnActualizar =
                new JButton(
                        "Actualizar"
                );


        btnActualizar.addActionListener(
                e -> actualizarHistorial()
        );


        filtros.add(
                crearEtiqueta(
                        "Cliente"
                )
        );


        filtros.add(
                cmbClienteHistorial
        );


        filtros.add(
                btnActualizar
        );


        superior.add(
                filtros,
                BorderLayout.EAST
        );


        tarjeta.add(
                superior,
                BorderLayout.NORTH
        );


        modeloHistorial =
                new DefaultTableModel(
                        new Object[]{
                                "Código",
                                "Fecha",
                                "Tipo",
                                "Monto",
                                "Comisión",
                                "Origen",
                                "Destino",
                                "Categoría",
                                "Puntos",
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


        JTable tabla =
                new JTable(
                        modeloHistorial
                );


        tabla.setRowHeight(
                27
        );


        tabla.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
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


        tabla.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );


        tabla.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(85);

        tabla.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(125);

        tabla.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(115);

        tabla.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(90);

        tabla.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(85);

        tabla.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(125);

        tabla.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(125);

        tabla.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(110);

        tabla.getColumnModel()
                .getColumn(8)
                .setPreferredWidth(70);

        tabla.getColumnModel()
                .getColumn(9)
                .setPreferredWidth(90);


        JScrollPane scroll =
                new JScrollPane(
                        tabla
                );


        scroll.setBorder(
                new EmptyBorder(
                        12,
                        0,
                        0,
                        0
                )
        );


        tarjeta.add(
                scroll,
                BorderLayout.CENTER
        );


        return tarjeta;
    }


    // ---------------------------------------------------------
    // REALIZAR DEPÓSITO
    // ---------------------------------------------------------

    private void realizarDeposito() {

        String cliente =
                obtenerSeleccion(
                        cmbClienteDeposito
                );


        String monedero =
                obtenerSeleccion(
                        cmbMonederoDeposito
                );


        Double monto =
                leerMonto(
                        txtMontoDeposito
                );


        if (cliente == null ||
                monedero == null ||
                monto == null) {

            return;
        }


        Transaccion transaccion =
                sistema.depositar(
                        cliente,
                        monedero,
                        monto
                );


        if (transaccion == null) {

            mostrarError(
                    "No fue posible realizar el depósito."
            );

            return;
        }


        txtMontoDeposito.setText(
                ""
        );


        finalizarOperacion(
                transaccion,
                cliente
        );
    }


    // ---------------------------------------------------------
    // REALIZAR RETIRO
    // ---------------------------------------------------------

    private void realizarRetiro() {

        String cliente =
                obtenerSeleccion(
                        cmbClienteRetiro
                );


        String monedero =
                obtenerSeleccion(
                        cmbMonederoRetiro
                );


        Double monto =
                leerMonto(
                        txtMontoRetiro
                );


        if (cliente == null ||
                monedero == null ||
                monto == null) {

            return;
        }


        Transaccion transaccion =
                sistema.retirar(
                        cliente,
                        monedero,
                        monto
                );


        if (transaccion == null) {

            mostrarError(
                    "No fue posible realizar el retiro.\n"
                            +
                            "Verifique el saldo disponible para cubrir el monto y la comisión."
            );

            return;
        }


        txtMontoRetiro.setText(
                ""
        );


        finalizarOperacion(
                transaccion,
                cliente
        );
    }


    // ---------------------------------------------------------
    // REALIZAR TRANSFERENCIA
    // ---------------------------------------------------------

    private void realizarTransferencia() {

        String clienteOrigen =
                obtenerSeleccion(
                        cmbClienteOrigen
                );


        String monederoOrigen =
                obtenerSeleccion(
                        cmbMonederoOrigen
                );


        String clienteDestino =
                obtenerSeleccion(
                        cmbClienteDestino
                );


        String monederoDestino =
                obtenerSeleccion(
                        cmbMonederoDestino
                );


        Double monto =
                leerMonto(
                        txtMontoTransferencia
                );


        if (clienteOrigen == null ||
                monederoOrigen == null ||
                clienteDestino == null ||
                monederoDestino == null ||
                monto == null) {

            return;
        }


        Transaccion transaccion =
                sistema.transferir(
                        clienteOrigen,
                        monederoOrigen,
                        clienteDestino,
                        monederoDestino,
                        monto
                );


        if (transaccion == null) {

            mostrarError(
                    "No fue posible realizar la transferencia.\n\n"
                            +
                            "Revise el saldo, los monederos seleccionados "
                            +
                            "y que origen y destino no sean exactamente la misma cuenta."
            );

            return;
        }


        txtMontoTransferencia.setText(
                ""
        );


        finalizarOperacion(
                transaccion,
                clienteOrigen
        );
    }


    // ---------------------------------------------------------
    // REALIZAR GASTO
    // ---------------------------------------------------------

    private void realizarGasto() {

        String cliente =
                obtenerSeleccion(
                        cmbClienteGasto
                );


        String monedero =
                obtenerSeleccion(
                        cmbMonederoGasto
                );


        CategoriaGasto categoria =
                (CategoriaGasto)
                        cmbCategoriaGasto
                                .getSelectedItem();


        Double monto =
                leerMonto(
                        txtMontoGasto
                );


        if (cliente == null ||
                monedero == null ||
                categoria == null ||
                monto == null) {

            return;
        }


        Transaccion transaccion =
                sistema.realizarGasto(
                        cliente,
                        monedero,
                        monto,
                        categoria
                );


        if (transaccion == null) {

            mostrarError(
                    "No fue posible registrar el gasto.\n"
                            +
                            "Verifique que exista saldo suficiente."
            );

            return;
        }


        txtMontoGasto.setText(
                ""
        );


        finalizarOperacion(
                transaccion,
                cliente
        );
    }


    // ---------------------------------------------------------
    // FINALIZAR OPERACIÓN
    // ---------------------------------------------------------

    private void finalizarOperacion(
            Transaccion transaccion,
            String clienteHistorial) {

        actualizarDatos();


        cmbClienteHistorial
                .setSelectedItem(
                        clienteHistorial
                );


        actualizarHistorial();


        if (alActualizarDatos != null) {

            alActualizarDatos.run();
        }


        mostrarResultadoTransaccion(
                transaccion
        );
    }


    // ---------------------------------------------------------
    // MOSTRAR RESULTADO
    // ---------------------------------------------------------

    private void mostrarResultadoTransaccion(
            Transaccion transaccion) {

        StringBuilder mensaje =
                new StringBuilder();


        mensaje.append(
                "Operación realizada correctamente.\n\n"
        );


        mensaje.append(
                "Código: "
        );

        mensaje.append(
                transaccion.getCodigo()
        );


        mensaje.append(
                "\nTipo: "
        );

        mensaje.append(
                transaccion.getTipo()
        );


        mensaje.append(
                "\nMonto: $ "
        );

        mensaje.append(
                String.format(
                        "%,.2f",
                        transaccion.getMonto()
                )
        );


        mensaje.append(
                "\nComisión: $ "
        );

        mensaje.append(
                String.format(
                        "%,.2f",
                        transaccion.getComision()
                )
        );


        mensaje.append(
                "\nPuntos generados: "
        );

        mensaje.append(
                transaccion.getPuntosGenerados()
        );


        if (transaccion.getCategoriaGasto()
                != null) {

            mensaje.append(
                    "\nCategoría: "
            );

            mensaje.append(
                    transaccion.getCategoriaGasto()
            );
        }


        JOptionPane.showMessageDialog(
                this,
                mensaje.toString(),
                "Transacción realizada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ---------------------------------------------------------
    // ACTUALIZAR DATOS
    // ---------------------------------------------------------

    public void actualizarDatos() {

        actualizarComboClientes(
                cmbClienteDeposito
        );

        actualizarComboClientes(
                cmbClienteRetiro
        );

        actualizarComboClientes(
                cmbClienteOrigen
        );

        actualizarComboClientes(
                cmbClienteDestino
        );

        actualizarComboClientes(
                cmbClienteGasto
        );

        actualizarComboClientes(
                cmbClienteConsulta
        );

        actualizarComboClientes(
                cmbClienteHistorial
        );


        actualizarMonederos(
                cmbClienteDeposito,
                cmbMonederoDeposito
        );

        actualizarMonederos(
                cmbClienteRetiro,
                cmbMonederoRetiro
        );

        actualizarMonederos(
                cmbClienteOrigen,
                cmbMonederoOrigen
        );

        actualizarMonederos(
                cmbClienteDestino,
                cmbMonederoDestino
        );

        actualizarMonederos(
                cmbClienteGasto,
                cmbMonederoGasto
        );

        actualizarMonederos(
                cmbClienteConsulta,
                cmbMonederoConsulta
        );


        actualizarSaldoConsulta();

        actualizarHistorial();
    }


    // ---------------------------------------------------------
    // CLIENTES
    // ---------------------------------------------------------

    private void actualizarComboClientes(
            JComboBox<String> combo) {

        if (combo == null) {
            return;
        }


        Object seleccionAnterior =
                combo.getSelectedItem();


        combo.removeAllItems();


        List<Cliente> clientes =
                sistema.obtenerClientes();


        clientes.sort(
                Comparator.comparing(
                        Cliente::getIdentificacion
                )
        );


        for (Cliente cliente :
                clientes) {

            combo.addItem(
                    cliente.getIdentificacion()
            );
        }


        if (seleccionAnterior != null) {

            combo.setSelectedItem(
                    seleccionAnterior
            );
        }
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    private void actualizarMonederos(
            JComboBox<String> comboCliente,
            JComboBox<String> comboMonedero) {

        if (comboCliente == null ||
                comboMonedero == null) {

            return;
        }


        Object seleccionAnterior =
                comboMonedero
                        .getSelectedItem();


        comboMonedero
                .removeAllItems();


        String cliente =
                obtenerSeleccion(
                        comboCliente
                );


        if (cliente == null) {
            return;
        }


        Cliente objetoCliente =
                sistema.buscarCliente(
                        cliente
                );


        if (objetoCliente == null) {
            return;
        }


        List<Monedero> monederos =
                objetoCliente
                        .obtenerMonederos();


        monederos.sort(
                Comparator.comparing(
                        Monedero::getId
                )
        );


        for (Monedero monedero :
                monederos) {

            comboMonedero.addItem(
                    monedero.getId()
            );
        }


        if (seleccionAnterior != null) {

            comboMonedero.setSelectedItem(
                    seleccionAnterior
            );
        }
    }


    // ---------------------------------------------------------
    // CONSULTAR SALDO
    // ---------------------------------------------------------

    private void actualizarSaldoConsulta() {

        if (lblSaldoConsulta == null) {
            return;
        }


        String cliente =
                obtenerSeleccion(
                        cmbClienteConsulta
                );


        String monedero =
                obtenerSeleccion(
                        cmbMonederoConsulta
                );


        if (cliente == null ||
                monedero == null) {

            lblSaldoConsulta.setText(
                    "$ 0.00"
            );

            return;
        }


        Double saldo =
                sistema.consultarSaldo(
                        cliente,
                        monedero
                );


        if (saldo == null) {

            lblSaldoConsulta.setText(
                    "$ 0.00"
            );

            return;
        }


        lblSaldoConsulta.setText(
                String.format(
                        "$ %,.2f",
                        saldo
                )
        );
    }


    // ---------------------------------------------------------
    // HISTORIAL
    // ---------------------------------------------------------

    private void actualizarHistorial() {

        if (modeloHistorial == null) {
            return;
        }


        modeloHistorial.setRowCount(
                0
        );


        String cliente =
                obtenerSeleccion(
                        cmbClienteHistorial
                );


        if (cliente == null) {
            return;
        }


        List<Transaccion> historial =
                sistema.obtenerHistorial(
                        cliente
                );


        for (Transaccion transaccion :
                historial) {

            String origen =
                    transaccion
                            .getIdentificacionOrigen()
                            +
                            " / "
                            +
                            transaccion
                                    .getMonederoOrigen();


            String destino =
                    "-";


            if (transaccion
                    .getIdentificacionDestino()
                    != null) {

                destino =
                        transaccion
                                .getIdentificacionDestino()
                                +
                                " / "
                                +
                                transaccion
                                        .getMonederoDestino();
            }


            String categoria =
                    transaccion
                            .getCategoriaGasto()
                            == null
                            ?
                            "-"
                            :
                            transaccion
                                    .getCategoriaGasto()
                                    .toString();


            modeloHistorial.addRow(
                    new Object[]{
                            transaccion.getCodigo(),
                            transaccion
                                    .getFecha()
                                    .format(
                                    formatoFecha
                            ),
                            transaccion.getTipo(),
                            String.format(
                                    "%,.2f",
                                    transaccion.getMonto()
                            ),
                            String.format(
                                    "%,.2f",
                                    transaccion.getComision()
                            ),
                            origen,
                            destino,
                            categoria,
                            transaccion.getPuntosGenerados(),
                            transaccion.isRevertida()
                                    ? "REVERTIDA"
                                    : "ACTIVA"
                    }
            );
        }
    }


    // ---------------------------------------------------------
    // LEER MONTO
    // ---------------------------------------------------------

    private Double leerMonto(
            JTextField campo) {

        String texto =
                campo.getText()
                        .trim();


        if (texto.isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar un monto."
            );

            return null;
        }


        try {

            double monto =
                    Double.parseDouble(
                            texto.replace(
                                    ",",
                                    "."
                            )
                    );


            if (monto <= 0) {

                mostrarAdvertencia(
                        "El monto debe ser mayor que cero."
                );

                return null;
            }


            return monto;

        } catch (NumberFormatException e) {

            mostrarAdvertencia(
                    "Ingrese un monto numérico válido."
            );

            return null;
        }
    }


    // ---------------------------------------------------------
    // OBTENER SELECCIÓN
    // ---------------------------------------------------------

    private String obtenerSeleccion(
            JComboBox<String> combo) {

        if (combo == null ||
                combo.getSelectedItem() == null) {

            return null;
        }


        return combo
                .getSelectedItem()
                .toString();
    }


    // ---------------------------------------------------------
    // FORMULARIO
    // ---------------------------------------------------------

    private JPanel crearFormulario(
            String titulo,
            String descripcion,
            JPanel campos) {

        JPanel fondo =
                new JPanel(
                        new GridBagLayout()
                );


        fondo.setBackground(
                FONDO
        );


        JPanel tarjeta =
                crearTarjeta();


        tarjeta.setPreferredSize(
                new Dimension(
                        620,
                        430
                )
        );


        JPanel encabezado =
                new JPanel();


        encabezado.setLayout(
                new BoxLayout(
                        encabezado,
                        BoxLayout.Y_AXIS
                )
        );


        encabezado.setOpaque(
                false
        );


        JLabel lblTitulo =
                crearTitulo(
                        titulo
                );


        JLabel lblDescripcion =
                new JLabel(
                        descripcion
                );


        lblDescripcion.setForeground(
                TEXTO_SECUNDARIO
        );


        lblDescripcion.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        encabezado.add(
                lblTitulo
        );


        encabezado.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );


        encabezado.add(
                lblDescripcion
        );


        tarjeta.add(
                encabezado,
                BorderLayout.NORTH
        );


        tarjeta.add(
                campos,
                BorderLayout.CENTER
        );


        fondo.add(
                tarjeta
        );


        return fondo;
    }


    // ---------------------------------------------------------
    // PANEL CAMPOS
    // ---------------------------------------------------------

    private JPanel crearPanelCampos() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );


        panel.setOpaque(
                false
        );


        panel.setBorder(
                new EmptyBorder(
                        20,
                        0,
                        0,
                        0
                )
        );


        return panel;
    }


    // ---------------------------------------------------------
    // AGREGAR CAMPO
    // ---------------------------------------------------------

    private void agregarCampo(
            JPanel panel,
            int fila,
            String texto,
            JComponent componente) {

        GridBagConstraints gbc =
                restricciones();


        gbc.gridx = 0;
        gbc.gridy = fila;


        panel.add(
                crearEtiqueta(
                        texto
                ),
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;


        panel.add(
                componente,
                gbc
        );
    }


    // ---------------------------------------------------------
    // AGREGAR BOTÓN
    // ---------------------------------------------------------

    private void agregarBoton(
            JPanel panel,
            int fila,
            JButton boton) {

        GridBagConstraints gbc =
                restricciones();


        gbc.gridx = 1;
        gbc.gridy = fila;


        gbc.insets =
                new Insets(
                        18,
                        8,
                        5,
                        8
                );


        panel.add(
                boton,
                gbc
        );
    }


    // ---------------------------------------------------------
    // RESTRICCIONES
    // ---------------------------------------------------------

    private GridBagConstraints restricciones() {

        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );


        gbc.anchor =
                GridBagConstraints.WEST;


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        return gbc;
    }


    // ---------------------------------------------------------
    // TARJETA
    // ---------------------------------------------------------

    private JPanel crearTarjeta() {

        JPanel tarjeta =
                new JPanel(
                        new BorderLayout()
                );


        tarjeta.setBackground(
                BLANCO
        );


        tarjeta.setBorder(
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


        return tarjeta;
    }


    // ---------------------------------------------------------
    // TÍTULO
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
    // CAMPO
    // ---------------------------------------------------------

    private JTextField crearCampoTexto() {

        JTextField campo =
                new JTextField();


        campo.setPreferredSize(
                new Dimension(
                        250,
                        34
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

    private JComboBox<String> crearComboTexto() {

        JComboBox<String> combo =
                new JComboBox<>();


        configurarCombo(
                combo
        );


        return combo;
    }


    private void configurarCombo(
            JComboBox<?> combo) {

        combo.setPreferredSize(
                new Dimension(
                        250,
                        34
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
    // BOTÓN
    // ---------------------------------------------------------

    private JButton crearBotonPrincipal(
            String texto) {

        JButton boton =
                new JButton(
                        texto
                );


        boton.setPreferredSize(
                new Dimension(
                        180,
                        38
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


        boton.setOpaque(
                true
        );


        boton.setContentAreaFilled(
                true
        );


        boton.setFocusPainted(
                false
        );


        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return boton;
    }


    // ---------------------------------------------------------
    // MENSAJES
    // ---------------------------------------------------------

    private void mostrarAdvertencia(
            String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Datos inválidos",
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