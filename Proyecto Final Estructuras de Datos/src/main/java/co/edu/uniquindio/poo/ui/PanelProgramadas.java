package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;
import co.edu.uniquindio.poo.model.TransaccionProgramada;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PanelProgramadas extends JPanel {

    private static final Color AZUL_PRINCIPAL =
            new Color(20, 65, 120);

    private static final Color FONDO =
            new Color(245, 247, 250);

    private static final Color BLANCO =
            Color.WHITE;

    private static final Color TEXTO_PRINCIPAL =
            new Color(35, 43, 55);

    private static final Color TEXTO_SECUNDARIO =
            new Color(105, 115, 130);


    private final MonederoVirtual sistema;

    private final Runnable alActualizarDatos;


    // DEPÓSITO
    private JTextField txtCodigoDeposito;
    private JComboBox<String> cmbClienteDeposito;
    private JComboBox<String> cmbMonederoDeposito;
    private JTextField txtMontoDeposito;
    private JSpinner spFechaDeposito;
    private JSpinner spFrecuenciaDeposito;


    // TRANSFERENCIA
    private JTextField txtCodigoTransferencia;
    private JComboBox<String> cmbClienteOrigen;
    private JComboBox<String> cmbMonederoOrigen;
    private JComboBox<String> cmbClienteDestino;
    private JComboBox<String> cmbMonederoDestino;
    private JTextField txtMontoTransferencia;
    private JSpinner spFechaTransferencia;
    private JSpinner spFrecuenciaTransferencia;


    // LISTADO
    private DefaultTableModel modeloProgramadas;

    private JTable tablaProgramadas;

    private JLabel lblCantidadProgramadas;

    private JLabel lblSiguienteProgramada;


    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
            );


    public PanelProgramadas(
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
                "Programar depósito",
                crearPanelDeposito()
        );


        tabs.addTab(
                "Programar transferencia",
                crearPanelTransferencia()
        );


        tabs.addTab(
                "Operaciones programadas",
                crearPanelListado()
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


        txtCodigoDeposito =
                crearCampoTexto();


        cmbClienteDeposito =
                crearCombo();


        cmbMonederoDeposito =
                crearCombo();


        txtMontoDeposito =
                crearCampoTexto();


        spFechaDeposito =
                crearSpinnerFecha();


        spFrecuenciaDeposito =
                crearSpinnerFrecuencia();


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
                "Código",
                txtCodigoDeposito
        );


        agregarCampo(
                campos,
                1,
                "Cliente",
                cmbClienteDeposito
        );


        agregarCampo(
                campos,
                2,
                "Monedero",
                cmbMonederoDeposito
        );


        agregarCampo(
                campos,
                3,
                "Monto",
                txtMontoDeposito
        );


        agregarCampo(
                campos,
                4,
                "Fecha y hora",
                spFechaDeposito
        );


        agregarCampo(
                campos,
                5,
                "Frecuencia en días",
                spFrecuenciaDeposito
        );


        JLabel ayuda =
                crearEtiqueta(
                        "Use 0 días para ejecutar una sola vez. "
                                +
                                "Un valor mayor que 0 crea una operación recurrente."
                );


        GridBagConstraints gbc =
                restricciones();


        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;


        campos.add(
                ayuda,
                gbc
        );


        JButton boton =
                crearBotonPrincipal(
                        "Programar depósito"
                );


        boton.addActionListener(
                e -> programarDeposito()
        );


        agregarBoton(
                campos,
                7,
                boton
        );


        return crearFormulario(
                "Programar depósito",
                "La operación quedará almacenada en la cola de prioridad.",
                campos
        );
    }


    // ---------------------------------------------------------
    // TRANSFERENCIA
    // ---------------------------------------------------------

    private JPanel crearPanelTransferencia() {

        JPanel campos =
                crearPanelCampos();


        txtCodigoTransferencia =
                crearCampoTexto();


        cmbClienteOrigen =
                crearCombo();


        cmbMonederoOrigen =
                crearCombo();


        cmbClienteDestino =
                crearCombo();


        cmbMonederoDestino =
                crearCombo();


        txtMontoTransferencia =
                crearCampoTexto();


        spFechaTransferencia =
                crearSpinnerFecha();


        spFrecuenciaTransferencia =
                crearSpinnerFrecuencia();


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
                "Código",
                txtCodigoTransferencia
        );


        agregarCampo(
                campos,
                1,
                "Cliente origen",
                cmbClienteOrigen
        );


        agregarCampo(
                campos,
                2,
                "Monedero origen",
                cmbMonederoOrigen
        );


        agregarCampo(
                campos,
                3,
                "Cliente destino",
                cmbClienteDestino
        );


        agregarCampo(
                campos,
                4,
                "Monedero destino",
                cmbMonederoDestino
        );


        agregarCampo(
                campos,
                5,
                "Monto",
                txtMontoTransferencia
        );


        agregarCampo(
                campos,
                6,
                "Fecha y hora",
                spFechaTransferencia
        );


        agregarCampo(
                campos,
                7,
                "Frecuencia en días",
                spFrecuenciaTransferencia
        );


        JButton boton =
                crearBotonPrincipal(
                        "Programar transferencia"
                );


        boton.addActionListener(
                e -> programarTransferencia()
        );


        agregarBoton(
                campos,
                8,
                boton
        );


        return crearFormulario(
                "Programar transferencia",
                "Las transferencias programadas utilizan la misma lógica de comisiones y beneficios.",
                campos
        );
    }


    // ---------------------------------------------------------
    // LISTADO
    // ---------------------------------------------------------

    private JPanel crearPanelListado() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );


        panel.setBackground(
                FONDO
        );


        JPanel resumen =
                crearTarjeta();


        resumen.setLayout(
                new FlowLayout(
                        FlowLayout.LEFT,
                        20,
                        8
                )
        );


        lblCantidadProgramadas =
                new JLabel(
                        "Programadas: 0"
                );


        lblCantidadProgramadas.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        lblSiguienteProgramada =
                new JLabel(
                        "Siguiente: -"
                );


        lblSiguienteProgramada.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        JButton btnActualizar =
                crearBotonSecundario(
                        "Actualizar"
                );


        btnActualizar.addActionListener(
                e -> actualizarDatos()
        );


        JButton btnProcesar =
                crearBotonPrincipal(
                        "Procesar hasta ahora"
                );


        btnProcesar.addActionListener(
                e -> procesarHastaAhora()
        );


        resumen.add(
                lblCantidadProgramadas
        );


        resumen.add(
                lblSiguienteProgramada
        );


        resumen.add(
                btnActualizar
        );


        resumen.add(
                btnProcesar
        );


        panel.add(
                resumen,
                BorderLayout.NORTH
        );


        modeloProgramadas =
                new DefaultTableModel(
                        new Object[]{
                                "Código",
                                "Tipo",
                                "Origen",
                                "Destino",
                                "Monto",
                                "Fecha",
                                "Frecuencia",
                                "Recurrente"
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


        tablaProgramadas =
                new JTable(
                        modeloProgramadas
                );


        tablaProgramadas.setRowHeight(
                27
        );


        tablaProgramadas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        tablaProgramadas.getTableHeader()
                .setReorderingAllowed(
                        false
                );


        JScrollPane scroll =
                new JScrollPane(
                        tablaProgramadas
                );


        JPanel tablaTarjeta =
                crearTarjeta();


        tablaTarjeta.add(
                scroll,
                BorderLayout.CENTER
        );


        JButton btnCancelar =
                crearBotonPrincipal(
                        "Cancelar seleccionada"
                );


        btnCancelar.addActionListener(
                e -> cancelarSeleccionada()
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
                btnCancelar
        );


        tablaTarjeta.add(
                pie,
                BorderLayout.SOUTH
        );


        panel.add(
                tablaTarjeta,
                BorderLayout.CENTER
        );


        return panel;
    }


    // ---------------------------------------------------------
    // PROGRAMAR DEPÓSITO
    // ---------------------------------------------------------

    private void programarDeposito() {

        String codigo =
                txtCodigoDeposito
                        .getText()
                        .trim();


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


        if (codigo.isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar un código."
            );

            return;
        }


        if (cliente == null ||
                monedero == null ||
                monto == null) {

            return;
        }


        LocalDateTime fecha =
                obtenerFecha(
                        spFechaDeposito
                );


        int frecuencia =
                (Integer)
                        spFrecuenciaDeposito
                                .getValue();


        boolean resultado =
                sistema.programarDeposito(
                        codigo,
                        cliente,
                        monedero,
                        monto,
                        fecha,
                        frecuencia
                );


        if (!resultado) {

            mostrarError(
                    "No fue posible programar el depósito.\n"
                            +
                            "Revise el código, cliente, monedero y demás datos."
            );

            return;
        }


        txtCodigoDeposito.setText(
                ""
        );


        txtMontoDeposito.setText(
                ""
        );


        actualizarDatos();


        notificarCambioGeneral();


        mostrarExito(
                "Depósito programado correctamente."
        );
    }


    // ---------------------------------------------------------
    // PROGRAMAR TRANSFERENCIA
    // ---------------------------------------------------------

    private void programarTransferencia() {

        String codigo =
                txtCodigoTransferencia
                        .getText()
                        .trim();


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


        if (codigo.isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar un código."
            );

            return;
        }


        if (clienteOrigen == null ||
                monederoOrigen == null ||
                clienteDestino == null ||
                monederoDestino == null ||
                monto == null) {

            return;
        }


        LocalDateTime fecha =
                obtenerFecha(
                        spFechaTransferencia
                );


        int frecuencia =
                (Integer)
                        spFrecuenciaTransferencia
                                .getValue();


        boolean resultado =
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


        if (!resultado) {

            mostrarError(
                    "No fue posible programar la transferencia.\n"
                            +
                            "Revise los datos y confirme que origen y destino no sean la misma cuenta."
            );

            return;
        }


        txtCodigoTransferencia.setText(
                ""
        );


        txtMontoTransferencia.setText(
                ""
        );


        actualizarDatos();


        notificarCambioGeneral();


        mostrarExito(
                "Transferencia programada correctamente."
        );
    }


    // ---------------------------------------------------------
    // PROCESAR
    // ---------------------------------------------------------

    private void procesarHastaAhora() {

        List<Transaccion> realizadas =
                sistema
                        .procesarTransaccionesProgramadas(
                                LocalDateTime.now()
                        );


        actualizarDatos();

        notificarCambioGeneral();


        JOptionPane.showMessageDialog(
                this,
                "Transacciones ejecutadas: "
                        +
                        realizadas.size(),
                "Procesamiento terminado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ---------------------------------------------------------
    // CANCELAR
    // ---------------------------------------------------------

    private void cancelarSeleccionada() {

        int fila =
                tablaProgramadas
                        .getSelectedRow();


        if (fila < 0) {

            mostrarAdvertencia(
                    "Seleccione una transacción programada."
            );

            return;
        }


        String codigo =
                modeloProgramadas
                        .getValueAt(
                                fila,
                                0
                        )
                        .toString();


        int confirmacion =
                JOptionPane
                        .showConfirmDialog(
                                this,
                                "¿Desea cancelar la transacción "
                                        +
                                        codigo
                                        +
                                        "?",
                                "Confirmar cancelación",
                                JOptionPane.YES_NO_OPTION
                        );


        if (confirmacion !=
                JOptionPane.YES_OPTION) {

            return;
        }


        if (!sistema.cancelarProgramada(
                codigo
        )) {

            mostrarError(
                    "No fue posible cancelar la transacción."
            );

            return;
        }


        actualizarDatos();

        notificarCambioGeneral();


        mostrarExito(
                "Transacción programada cancelada."
        );
    }


    // ---------------------------------------------------------
    // ACTUALIZAR
    // ---------------------------------------------------------

    public void actualizarDatos() {

        actualizarComboClientes(
                cmbClienteDeposito
        );


        actualizarComboClientes(
                cmbClienteOrigen
        );


        actualizarComboClientes(
                cmbClienteDestino
        );


        actualizarMonederos(
                cmbClienteDeposito,
                cmbMonederoDeposito
        );


        actualizarMonederos(
                cmbClienteOrigen,
                cmbMonederoOrigen
        );


        actualizarMonederos(
                cmbClienteDestino,
                cmbMonederoDestino
        );


        actualizarTabla();
    }


    private void actualizarTabla() {

        if (modeloProgramadas == null) {
            return;
        }


        modeloProgramadas.setRowCount(
                0
        );


        List<TransaccionProgramada> lista =
                sistema
                        .obtenerProgramadasEnOrden();


        for (TransaccionProgramada programada :
                lista) {

            String origen =
                    programada
                            .getIdentificacionOrigen()
                            +
                            " / "
                            +
                            programada
                                    .getMonederoOrigen();


            String destino =
                    "-";


            if (programada
                    .getIdentificacionDestino()
                    != null) {

                destino =
                        programada
                                .getIdentificacionDestino()
                                +
                                " / "
                                +
                                programada
                                        .getMonederoDestino();
            }


            modeloProgramadas.addRow(
                    new Object[]{
                            programada.getCodigo(),
                            programada.getTipo(),
                            origen,
                            destino,
                            String.format(
                                    "%,.2f",
                                    programada.getMonto()
                            ),
                            programada
                                    .getFechaEjecucion()
                                    .format(
                                    formatoFecha
                            ),
                            programada
                                    .getFrecuenciaDias(),
                            programada
                                    .esRecurrente()
                                    ?
                                    "Sí"
                                    :
                                    "No"
                    }
            );
        }


        lblCantidadProgramadas.setText(
                "Programadas: "
                        +
                        sistema
                                .cantidadProgramadas()
        );


        TransaccionProgramada siguiente =
                sistema
                        .verSiguienteProgramada();


        if (siguiente == null) {

            lblSiguienteProgramada.setText(
                    "Siguiente: -"
            );

        } else {

            lblSiguienteProgramada.setText(
                    "Siguiente: "
                            +
                            siguiente.getCodigo()
                            +
                            " - "
                            +
                            siguiente
                                    .getFechaEjecucion()
                                    .format(
                                            formatoFecha
                                    )
            );
        }
    }


    // ---------------------------------------------------------
    // CLIENTES
    // ---------------------------------------------------------

    private void actualizarComboClientes(
            JComboBox<String> combo) {

        if (combo == null) {
            return;
        }


        Object seleccion =
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


        if (seleccion != null) {

            combo.setSelectedItem(
                    seleccion
            );
        }
    }


    private void actualizarMonederos(
            JComboBox<String> comboCliente,
            JComboBox<String> comboMonedero) {

        if (comboCliente == null ||
                comboMonedero == null) {

            return;
        }


        Object seleccion =
                comboMonedero
                        .getSelectedItem();


        comboMonedero
                .removeAllItems();


        String idCliente =
                obtenerSeleccion(
                        comboCliente
                );


        if (idCliente == null) {
            return;
        }


        Cliente cliente =
                sistema.buscarCliente(
                        idCliente
                );


        if (cliente == null) {
            return;
        }


        List<Monedero> monederos =
                cliente.obtenerMonederos();


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


        if (seleccion != null) {

            comboMonedero
                    .setSelectedItem(
                            seleccion
                    );
        }
    }


    // ---------------------------------------------------------
    // AUXILIARES
    // ---------------------------------------------------------

    private LocalDateTime obtenerFecha(
            JSpinner spinner) {

        Date fecha =
                (Date)
                        spinner.getValue();


        return LocalDateTime
                .ofInstant(
                        fecha.toInstant(),
                        ZoneId.systemDefault()
                );
    }


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
                    "El monto debe ser numérico."
            );

            return null;
        }
    }


    private String obtenerSeleccion(
            JComboBox<String> combo) {

        if (combo == null ||
                combo.getSelectedItem()
                        == null) {

            return null;
        }


        return combo
                .getSelectedItem()
                .toString();
    }


    private JSpinner crearSpinnerFecha() {

        Date fechaInicial =
                new Date(
                        System.currentTimeMillis()
                                +
                                5 * 60 * 1000
                );


        SpinnerDateModel modelo =
                new SpinnerDateModel(
                        fechaInicial,
                        null,
                        null,
                        Calendar.MINUTE
                );


        JSpinner spinner =
                new JSpinner(
                        modelo
                );


        spinner.setEditor(
                new JSpinner.DateEditor(
                        spinner,
                        "dd/MM/yyyy HH:mm"
                )
        );


        return spinner;
    }


    private JSpinner crearSpinnerFrecuencia() {

        return new JSpinner(
                new SpinnerNumberModel(
                        0,
                        0,
                        365,
                        1
                )
        );
    }


    // ---------------------------------------------------------
    // UI
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
                        650,
                        500
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
                new JLabel(
                        titulo
                );


        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );


        lblTitulo.setForeground(
                TEXTO_PRINCIPAL
        );


        JLabel lblDescripcion =
                crearEtiqueta(
                        descripcion
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
                        15,
                        0,
                        0,
                        0
                )
        );


        return panel;
    }


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


    private GridBagConstraints restricciones() {

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


        return gbc;
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
                                18,
                                20,
                                18,
                                20
                        )
                )
        );


        return panel;
    }


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
                        12
                )
        );


        label.setForeground(
                TEXTO_SECUNDARIO
        );


        return label;
    }


    private JTextField crearCampoTexto() {

        JTextField campo =
                new JTextField();


        campo.setPreferredSize(
                new Dimension(
                        260,
                        34
                )
        );


        return campo;
    }


    private JComboBox<String> crearCombo() {

        JComboBox<String> combo =
                new JComboBox<>();


        combo.setPreferredSize(
                new Dimension(
                        260,
                        34
                )
        );


        return combo;
    }


    private JButton crearBotonPrincipal(
            String texto) {

        JButton boton =
                new JButton(
                        texto
                );


        boton.setForeground(
                BLANCO
        );


        boton.setBackground(
                AZUL_PRINCIPAL
        );


        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        boton.setFocusPainted(
                false
        );


        return boton;
    }


    private JButton crearBotonSecundario(
            String texto) {

        JButton boton =
                new JButton(
                        texto
                );


        boton.setForeground(
                AZUL_PRINCIPAL
        );


        boton.setBackground(
                Color.WHITE
        );


        return boton;
    }


    private void notificarCambioGeneral() {

        if (alActualizarDatos != null) {

            alActualizarDatos.run();
        }
    }


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