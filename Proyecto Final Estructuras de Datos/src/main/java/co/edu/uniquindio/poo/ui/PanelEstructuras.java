package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.enums.CategoriaGasto;
import co.edu.uniquindio.poo.estructuras.grafo.AristaGrafo;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;
import co.edu.uniquindio.poo.model.Transaccion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class PanelEstructuras extends JPanel {

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

    private static final Color VERDE =
            new Color(35, 145, 95);

    private static final Color ROJO =
            new Color(190, 65, 65);


    private final MonederoVirtual sistema;


    private JComboBox<String> cmbCliente;

    private JComboBox<String> cmbMonedero;


    // INTEGRIDAD
    private JLabel lblIntegridadSistema;

    private JLabel lblIntegridadCliente;

    private JLabel lblTransaccionesValidas;

    private JLabel lblPrimeraInvalida;


    // ÁRBOLES
    private JLabel lblAlturaBST;

    private JLabel lblAlturaAVL;

    private DefaultTableModel modeloBST;

    private DefaultTableModel modeloAVL;


    // GRAFOS
    private JLabel lblTotalTransferido;

    private JLabel lblPrincipalDestino;

    private JLabel lblRecorrido;

    private DefaultTableModel modeloGrafoClientes;

    private DefaultTableModel modeloGrafoMonederos;


    // PATRONES
    private JLabel lblTotalGastos;

    private JLabel lblMayorCategoria;

    private JLabel lblUltimaCategoria;

    private JLabel lblSiguienteCategoria;

    private DefaultTableModel modeloCategorias;


    public PanelEstructuras(
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


    // ---------------------------------------------------------
    // CONSTRUIR
    // ---------------------------------------------------------

    private void construir() {

        add(
                crearSelector(),
                BorderLayout.NORTH
        );


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
                "Integridad",
                crearIntegridad()
        );


        tabs.addTab(
                "BST y AVL",
                crearArboles()
        );


        tabs.addTab(
                "Grafos",
                crearGrafos()
        );


        tabs.addTab(
                "Patrones de gasto",
                crearPatrones()
        );


        add(
                tabs,
                BorderLayout.CENTER
        );
    }


    // ---------------------------------------------------------
    // SELECTOR
    // ---------------------------------------------------------

    private JPanel crearSelector() {

        JPanel panel =
                crearTarjeta();


        panel.setLayout(
                new FlowLayout(
                        FlowLayout.LEFT,
                        15,
                        5
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
                e -> {

                    actualizarMonederos();

                    actualizarAnalisisCliente();
                }
        );


        panel.add(
                cmbCliente
        );


        panel.add(
                new JLabel(
                        "Monedero:"
                )
        );


        cmbMonedero =
                new JComboBox<>();


        cmbMonedero.setPreferredSize(
                new Dimension(
                        160,
                        32
                )
        );


        cmbMonedero.addActionListener(
                e -> actualizarGrafoMonedero()
        );


        panel.add(
                cmbMonedero
        );


        JButton btnActualizar =
                crearBoton(
                        "Actualizar análisis"
                );


        btnActualizar.addActionListener(
                e -> actualizarDatos()
        );


        panel.add(
                btnActualizar
        );


        return panel;
    }


    // ---------------------------------------------------------
    // INTEGRIDAD
    // ---------------------------------------------------------

    private JPanel crearIntegridad() {

        JPanel panel =
                crearTarjeta();


        JPanel datos =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                15,
                                18
                        )
                );


        datos.setOpaque(
                false
        );


        lblIntegridadSistema =
                crearValor();


        lblIntegridadCliente =
                crearValor();


        lblTransaccionesValidas =
                crearValor();


        lblPrimeraInvalida =
                crearValor();


        agregarDato(
                datos,
                "Integridad del sistema",
                lblIntegridadSistema
        );


        agregarDato(
                datos,
                "Integridad del cliente",
                lblIntegridadCliente
        );


        agregarDato(
                datos,
                "Transacciones válidas",
                lblTransaccionesValidas
        );


        agregarDato(
                datos,
                "Primera transacción inválida",
                lblPrimeraInvalida
        );


        panel.add(
                crearTitulo(
                        "Verificación recursiva de integridad",
                        "Comprueba las transacciones del cliente y del sistema."
                ),
                BorderLayout.NORTH
        );


        panel.add(
                datos,
                BorderLayout.CENTER
        );


        return envolver(
                panel
        );
    }


    // ---------------------------------------------------------
    // ÁRBOLES
    // ---------------------------------------------------------

    private JPanel crearArboles() {

        JPanel fondo =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );


        fondo.setBackground(
                FONDO
        );


        JPanel resumen =
                crearTarjeta();


        resumen.setLayout(
                new FlowLayout(
                        FlowLayout.LEFT,
                        30,
                        6
                )
        );


        lblAlturaBST =
                crearValor();


        lblAlturaAVL =
                crearValor();


        resumen.add(
                new JLabel(
                        "Altura BST:"
                )
        );


        resumen.add(
                lblAlturaBST
        );


        resumen.add(
                new JLabel(
                        "Altura AVL:"
                )
        );


        resumen.add(
                lblAlturaAVL
        );


        fondo.add(
                resumen,
                BorderLayout.NORTH
        );


        JPanel tablas =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );


        tablas.setOpaque(
                false
        );


        modeloBST =
                crearModeloClientes();


        modeloAVL =
                crearModeloClientes();


        tablas.add(
                crearPanelTabla(
                        "Recorrido BST por puntos",
                        modeloBST
                )
        );


        tablas.add(
                crearPanelTabla(
                        "Recorrido AVL por puntos",
                        modeloAVL
                )
        );


        fondo.add(
                tablas,
                BorderLayout.CENTER
        );


        return fondo;
    }


    // ---------------------------------------------------------
    // GRAFOS
    // ---------------------------------------------------------

    private JPanel crearGrafos() {

        JPanel fondo =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );


        fondo.setBackground(
                FONDO
        );


        JPanel resumen =
                crearTarjeta();


        resumen.setLayout(
                new GridLayout(
                        3,
                        2,
                        12,
                        10
                )
        );


        lblTotalTransferido =
                crearValor();


        lblPrincipalDestino =
                crearValor();


        lblRecorrido =
                crearValor();


        agregarDato(
                resumen,
                "Total transferido",
                lblTotalTransferido
        );


        agregarDato(
                resumen,
                "Principal destino",
                lblPrincipalDestino
        );


        agregarDato(
                resumen,
                "Recorrido desde cliente",
                lblRecorrido
        );


        fondo.add(
                resumen,
                BorderLayout.NORTH
        );


        JPanel tablas =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );


        tablas.setOpaque(
                false
        );


        modeloGrafoClientes =
                crearModeloGrafo();


        modeloGrafoMonederos =
                crearModeloGrafo();


        tablas.add(
                crearPanelTabla(
                        "Grafo de clientes",
                        modeloGrafoClientes
                )
        );


        tablas.add(
                crearPanelTabla(
                        "Grafo del monedero seleccionado",
                        modeloGrafoMonederos
                )
        );


        fondo.add(
                tablas,
                BorderLayout.CENTER
        );


        return fondo;
    }


    // ---------------------------------------------------------
    // PATRONES
    // ---------------------------------------------------------

    private JPanel crearPatrones() {

        JPanel fondo =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );


        fondo.setBackground(
                FONDO
        );


        JPanel resumen =
                crearTarjeta();


        resumen.setLayout(
                new GridLayout(
                        2,
                        4,
                        12,
                        10
                )
        );


        lblTotalGastos =
                crearValor();


        lblMayorCategoria =
                crearValor();


        lblUltimaCategoria =
                crearValor();


        lblSiguienteCategoria =
                crearValor();


        agregarDato(
                resumen,
                "Total de gastos",
                lblTotalGastos
        );


        agregarDato(
                resumen,
                "Mayor categoría",
                lblMayorCategoria
        );


        agregarDato(
                resumen,
                "Última categoría",
                lblUltimaCategoria
        );


        agregarDato(
                resumen,
                "Siguiente categoría principal",
                lblSiguienteCategoria
        );


        fondo.add(
                resumen,
                BorderLayout.NORTH
        );


        modeloCategorias =
                new DefaultTableModel(
                        new Object[]{
                                "Categoría",
                                "Total gastado"
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


        fondo.add(
                crearPanelTabla(
                        "Distribución de gastos por categoría",
                        modeloCategorias
                ),
                BorderLayout.CENTER
        );


        return fondo;
    }


    // ---------------------------------------------------------
    // ACTUALIZACIÓN GENERAL
    // ---------------------------------------------------------

    public void actualizarDatos() {

        Object seleccionAnterior =
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


        if (seleccionAnterior != null) {

            cmbCliente.setSelectedItem(
                    seleccionAnterior
            );
        }


        actualizarMonederos();

        actualizarArboles();

        actualizarAnalisisCliente();
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    private void actualizarMonederos() {

        Object seleccionAnterior =
                cmbMonedero
                        .getSelectedItem();


        cmbMonedero.removeAllItems();


        String identificacion =
                obtenerCliente();


        if (identificacion == null) {
            return;
        }


        Cliente cliente =
                sistema.buscarCliente(
                        identificacion
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

            cmbMonedero.addItem(
                    monedero.getId()
            );
        }


        if (seleccionAnterior != null) {

            cmbMonedero.setSelectedItem(
                    seleccionAnterior
            );
        }
    }


    // ---------------------------------------------------------
    // CLIENTE
    // ---------------------------------------------------------

    private void actualizarAnalisisCliente() {

        actualizarIntegridad();

        actualizarGrafos();

        actualizarPatrones();
    }


    // ---------------------------------------------------------
    // INTEGRIDAD
    // ---------------------------------------------------------

    private void actualizarIntegridad() {

        boolean integridadSistema =
                sistema
                        .verificarIntegridadSistema();


        mostrarEstado(
                lblIntegridadSistema,
                integridadSistema
        );


        String cliente =
                obtenerCliente();


        if (cliente == null) {

            lblIntegridadCliente.setText("-");
            lblTransaccionesValidas.setText("-");
            lblPrimeraInvalida.setText("-");

            return;
        }


        boolean integridadCliente =
                sistema
                        .verificarIntegridadCliente(
                                cliente
                        );


        mostrarEstado(
                lblIntegridadCliente,
                integridadCliente
        );


        lblTransaccionesValidas.setText(
                String.valueOf(
                        sistema
                                .contarTransaccionesValidas(
                                        cliente
                                )
                )
        );


        Transaccion invalida =
                sistema
                        .buscarPrimeraTransaccionInvalida(
                                cliente
                        );


        lblPrimeraInvalida.setText(
                invalida == null
                        ?
                        "Ninguna"
                        :
                        invalida.getCodigo()
        );
    }


    // ---------------------------------------------------------
    // ÁRBOLES
    // ---------------------------------------------------------

    private void actualizarArboles() {

        lblAlturaBST.setText(
                String.valueOf(
                        sistema.obtenerAlturaBST()
                )
        );


        lblAlturaAVL.setText(
                String.valueOf(
                        sistema.obtenerAlturaAVL()
                )
        );


        modeloBST.setRowCount(
                0
        );


        modeloAVL.setRowCount(
                0
        );


        List<Cliente> bst =
                sistema
                        .obtenerClientesOrdenadosPorPuntosBST();


        for (Cliente cliente :
                bst) {

            agregarClienteArbol(
                    modeloBST,
                    cliente
            );
        }


        List<Cliente> avl =
                sistema
                        .obtenerClientesOrdenadosPorPuntosAVL();


        for (Cliente cliente :
                avl) {

            agregarClienteArbol(
                    modeloAVL,
                    cliente
            );
        }
    }


    private void agregarClienteArbol(
            DefaultTableModel modelo,
            Cliente cliente) {

        modelo.addRow(
                new Object[]{
                        cliente.getIdentificacion(),
                        cliente.getNombre(),
                        cliente.getPuntos(),
                        cliente.getRango()
                }
        );
    }


    // ---------------------------------------------------------
    // GRAFOS
    // ---------------------------------------------------------

    private void actualizarGrafos() {

        modeloGrafoClientes.setRowCount(
                0
        );


        String cliente =
                obtenerCliente();


        if (cliente == null) {

            lblTotalTransferido.setText("-");
            lblPrincipalDestino.setText("-");
            lblRecorrido.setText("-");

            modeloGrafoMonederos.setRowCount(
                    0
            );

            return;
        }


        List<AristaGrafo> relaciones =
                sistema
                        .obtenerRelacionesCliente(
                                cliente
                        );


        for (AristaGrafo arista :
                relaciones) {

            modeloGrafoClientes.addRow(
                    new Object[]{
                            arista.getDestino(),
                            moneda(
                                    arista.getMontoTotal()
                            ),
                            arista.getCantidadTransacciones()
                    }
            );
        }


        lblTotalTransferido.setText(
                moneda(
                        sistema
                                .obtenerTotalTransferidoPorCliente(
                                        cliente
                                )
                )
        );


        String principal =
                sistema
                        .obtenerPrincipalDestinoCliente(
                                cliente
                        );


        lblPrincipalDestino.setText(
                principal == null
                        ?
                        "-"
                        :
                        principal
        );


        List<String> recorrido =
                sistema.recorridoClientes(
                        cliente
                );


        lblRecorrido.setText(
                recorrido.isEmpty()
                        ?
                        "-"
                        :
                        String.join(
                                " → ",
                                recorrido
                        )
        );


        actualizarGrafoMonedero();
    }


    private void actualizarGrafoMonedero() {

        if (modeloGrafoMonederos == null) {
            return;
        }


        modeloGrafoMonederos.setRowCount(
                0
        );


        String cliente =
                obtenerCliente();


        String monedero =
                obtenerMonedero();


        if (cliente == null ||
                monedero == null) {

            return;
        }


        List<AristaGrafo> relaciones =
                sistema
                        .obtenerRelacionesMonedero(
                                cliente,
                                monedero
                        );


        for (AristaGrafo arista :
                relaciones) {

            modeloGrafoMonederos.addRow(
                    new Object[]{
                            arista.getDestino(),
                            moneda(
                                    arista.getMontoTotal()
                            ),
                            arista.getCantidadTransacciones()
                    }
            );
        }
    }


    // ---------------------------------------------------------
    // PATRONES
    // ---------------------------------------------------------

    private void actualizarPatrones() {

        modeloCategorias.setRowCount(
                0
        );


        String cliente =
                obtenerCliente();


        if (cliente == null) {

            lblTotalGastos.setText("-");
            lblMayorCategoria.setText("-");
            lblUltimaCategoria.setText("-");
            lblSiguienteCategoria.setText("-");

            return;
        }


        lblTotalGastos.setText(
                moneda(
                        sistema.obtenerTotalGastos(
                                cliente
                        )
                )
        );


        CategoriaGasto mayor =
                sistema
                        .obtenerCategoriaMayorGasto(
                                cliente
                        );


        lblMayorCategoria.setText(
                mayor == null
                        ?
                        "-"
                        :
                        mayor.toString()
        );


        CategoriaGasto ultima =
                sistema
                        .obtenerUltimaCategoriaGasto(
                                cliente
                        );


        lblUltimaCategoria.setText(
                ultima == null
                        ?
                        "-"
                        :
                        ultima.toString()
        );


        if (ultima == null) {

            lblSiguienteCategoria.setText(
                    "-"
            );

        } else {

            String siguiente =
                    sistema
                            .obtenerSiguienteCategoriaPrincipal(
                                    ultima
                            );


            lblSiguienteCategoria.setText(
                    siguiente == null
                            ?
                            "-"
                            :
                            siguiente
            );
        }


        for (CategoriaGasto categoria :
                CategoriaGasto.values()) {

            modeloCategorias.addRow(
                    new Object[]{
                            categoria,
                            moneda(
                                    sistema
                                            .obtenerTotalCategoria(
                                                    cliente,
                                                    categoria
                                            )
                            )
                    }
            );
        }
    }


    // ---------------------------------------------------------
    // MODELOS
    // ---------------------------------------------------------

    private DefaultTableModel crearModeloClientes() {

        return new DefaultTableModel(
                new Object[]{
                        "Identificación",
                        "Nombre",
                        "Puntos",
                        "Rango"
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


    private DefaultTableModel crearModeloGrafo() {

        return new DefaultTableModel(
                new Object[]{
                        "Destino",
                        "Monto total",
                        "Transferencias"
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


    // ---------------------------------------------------------
    // UI
    // ---------------------------------------------------------

    private JPanel crearPanelTabla(
            String titulo,
            DefaultTableModel modelo) {

        JPanel panel =
                crearTarjeta();


        JLabel label =
                new JLabel(
                        titulo
                );


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );


        label.setForeground(
                TEXTO
        );


        panel.add(
                label,
                BorderLayout.NORTH
        );


        JTable tabla =
                new JTable(
                        modelo
                );


        tabla.setRowHeight(
                28
        );


        tabla.getTableHeader()
                .setReorderingAllowed(
                        false
                );


        panel.add(
                new JScrollPane(
                        tabla
                ),
                BorderLayout.CENTER
        );


        return panel;
    }


    private JPanel crearTitulo(
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
                        20,
                        0
                )
        );


        return panel;
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


    private JPanel envolver(
            JPanel panel) {

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
                panel,
                BorderLayout.CENTER
        );


        return fondo;
    }


    private void agregarDato(
            JPanel panel,
            String nombre,
            JLabel valor) {

        JLabel etiqueta =
                new JLabel(
                        nombre + ":"
                );


        etiqueta.setForeground(
                SECUNDARIO
        );


        panel.add(
                etiqueta
        );


        panel.add(
                valor
        );
    }


    private JLabel crearValor() {

        JLabel label =
                new JLabel(
                        "-"
                );


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        label.setForeground(
                AZUL
        );


        return label;
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


    private void mostrarEstado(
            JLabel label,
            boolean correcto) {

        label.setText(
                correcto
                        ?
                        "VÁLIDA"
                        :
                        "REVISAR"
        );


        label.setForeground(
                correcto
                        ?
                        VERDE
                        :
                        ROJO
        );
    }


    private String obtenerCliente() {

        if (cmbCliente.getSelectedItem()
                == null) {

            return null;
        }


        return cmbCliente
                .getSelectedItem()
                .toString();
    }


    private String obtenerMonedero() {

        if (cmbMonedero.getSelectedItem()
                == null) {

            return null;
        }


        return cmbMonedero
                .getSelectedItem()
                .toString();
    }


    private String moneda(
            double valor) {

        return String.format(
                "$ %,.2f",
                valor
        );
    }
}