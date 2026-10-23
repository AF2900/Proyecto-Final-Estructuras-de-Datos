package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.ReporteSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class InterfazGrafica extends JFrame {

    private static final Color AZUL_PRINCIPAL =
            new Color(20, 65, 120);

    private static final Color AZUL_OSCURO =
            new Color(13, 46, 88);

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


    private final MonederoVirtual sistema;


    private JPanel panelContenido;

    private CardLayout cardLayout;

    private JLabel lblTituloPagina;

    private JLabel lblSubtituloPagina;


    private PanelClientesMonederos
            panelClientesMonederos;

    private PanelTransacciones
            panelTransacciones;

    private PanelPuntosBeneficios
            panelPuntosBeneficios;

    private PanelProgramadas
            panelProgramadas;

    private PanelNotificaciones
            panelNotificaciones;

    private PanelReversiones
            panelReversiones;


    private JLabel lblCantidadClientes;

    private JLabel lblCantidadMonederos;

    private JLabel lblSaldoSistema;

    private JLabel lblIntegridad;


    public InterfazGrafica() {

        sistema =
                new MonederoVirtual();


        configurarVentana();

        inicializarInterfaz();


        mostrarPantalla(
                "INICIO",
                "Inicio",
                "Resumen general del monedero virtual"
        );


        actualizarDashboard();
    }


    // ---------------------------------------------------------
    // VENTANA
    // ---------------------------------------------------------

    private void configurarVentana() {

        setTitle(
                "Monedero Virtual - Universidad del Quindío"
        );


        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );


        setSize(
                1250,
                760
        );


        setMinimumSize(
                new Dimension(
                        1050,
                        650
                )
        );


        setLocationRelativeTo(
                null
        );


        setLayout(
                new BorderLayout()
        );


        getContentPane()
                .setBackground(
                        FONDO
                );
    }


    // ---------------------------------------------------------
    // INTERFAZ
    // ---------------------------------------------------------

    private void inicializarInterfaz() {

        JPanel principal =
                new JPanel(
                        new BorderLayout()
                );


        principal.setBackground(
                FONDO
        );


        principal.add(
                crearMenuLateral(),
                BorderLayout.WEST
        );


        JPanel derecha =
                new JPanel(
                        new BorderLayout()
                );


        derecha.setBackground(
                FONDO
        );


        derecha.add(
                crearEncabezado(),
                BorderLayout.NORTH
        );


        cardLayout =
                new CardLayout();


        panelContenido =
                new JPanel(
                        cardLayout
                );


        panelContenido.setBackground(
                FONDO
        );


        // INICIO
        panelContenido.add(
                crearPanelInicio(),
                "INICIO"
        );


        // CLIENTES
        panelClientesMonederos =
                new PanelClientesMonederos(
                        sistema,
                        this::actualizarDatosGenerales
                );


        panelContenido.add(
                panelClientesMonederos,
                "CLIENTES"
        );


        // TRANSACCIONES
        panelTransacciones =
                new PanelTransacciones(
                        sistema,
                        this::actualizarDatosGenerales
                );


        panelContenido.add(
                panelTransacciones,
                "TRANSACCIONES"
        );


        // REVERSIONES
        panelReversiones =
                new PanelReversiones(
                        sistema,
                        this::actualizarDatosGenerales
                );


        panelContenido.add(
                panelReversiones,
                "REVERSIONES"
        );


        // PUNTOS
        panelPuntosBeneficios =
                new PanelPuntosBeneficios(
                        sistema,
                        this::actualizarDatosGenerales
                );


        panelContenido.add(
                panelPuntosBeneficios,
                "PUNTOS"
        );


        // PROGRAMADAS
        panelProgramadas =
                new PanelProgramadas(
                        sistema,
                        this::actualizarDatosGenerales
                );


        panelContenido.add(
                panelProgramadas,
                "PROGRAMADAS"
        );


        // NOTIFICACIONES
        panelNotificaciones =
                new PanelNotificaciones(
                        sistema,
                        this::actualizarDatosGenerales
                );


        panelContenido.add(
                panelNotificaciones,
                "NOTIFICACIONES"
        );


        // REPORTES
        panelContenido.add(
                crearPanelProvisional(
                        "Reportes",
                        "Reportes de clientes y estado general del sistema."
                ),
                "REPORTES"
        );


        // ESTRUCTURAS
        panelContenido.add(
                crearPanelProvisional(
                        "Estructuras y análisis",
                        "Integridad, grafos, patrones de gasto, BST y AVL."
                ),
                "ESTRUCTURAS"
        );


        derecha.add(
                panelContenido,
                BorderLayout.CENTER
        );


        principal.add(
                derecha,
                BorderLayout.CENTER
        );


        add(
                principal,
                BorderLayout.CENTER
        );
    }


    // ---------------------------------------------------------
    // MENÚ
    // ---------------------------------------------------------

    private JPanel crearMenuLateral() {

        JPanel menu =
                new JPanel();


        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );


        menu.setPreferredSize(
                new Dimension(
                        245,
                        0
                )
        );


        menu.setBackground(
                AZUL_OSCURO
        );


        menu.setBorder(
                new EmptyBorder(
                        25,
                        18,
                        20,
                        18
                )
        );


        JLabel logo =
                new JLabel(
                        "MONEDERO"
                );


        logo.setForeground(
                BLANCO
        );


        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );


        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel subtitulo =
                new JLabel(
                        "VIRTUAL"
                );


        subtitulo.setForeground(
                new Color(
                        150,
                        190,
                        230
                )
        );


        subtitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        subtitulo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        menu.add(
                logo
        );


        menu.add(
                subtitulo
        );


        menu.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                28
                        )
                )
        );


        agregarBotonMenu(
                menu,
                "Inicio",
                "INICIO",
                "Inicio",
                "Resumen general del monedero virtual"
        );


        agregarBotonMenu(
                menu,
                "Clientes",
                "CLIENTES",
                "Clientes y monederos",
                "Gestión de clientes y sus cuentas virtuales"
        );


        agregarBotonMenu(
                menu,
                "Transacciones",
                "TRANSACCIONES",
                "Transacciones",
                "Depósitos, retiros, transferencias y gastos"
        );


        agregarBotonMenu(
                menu,
                "Reversiones",
                "REVERSIONES",
                "Reversiones",
                "Pila LIFO de transacciones reversibles"
        );


        agregarBotonMenu(
                menu,
                "Puntos y beneficios",
                "PUNTOS",
                "Puntos y beneficios",
                "Consulta de puntos, rangos y beneficios"
        );


        agregarBotonMenu(
                menu,
                "Programadas",
                "PROGRAMADAS",
                "Transacciones programadas",
                "Operaciones automáticas y recurrentes"
        );


        agregarBotonMenu(
                menu,
                "Notificaciones",
                "NOTIFICACIONES",
                "Notificaciones",
                "Historial circular y cola FIFO de pendientes"
        );


        agregarBotonMenu(
                menu,
                "Reportes",
                "REPORTES",
                "Reportes",
                "Información consolidada del sistema"
        );


        agregarBotonMenu(
                menu,
                "Estructuras",
                "ESTRUCTURAS",
                "Estructuras y análisis",
                "Grafos, árboles, patrones e integridad"
        );


        menu.add(
                Box.createVerticalGlue()
        );


        JLabel universidad =
                new JLabel(
                        "<html>Universidad del Quindío<br>Estructuras de Datos</html>"
                );


        universidad.setForeground(
                new Color(
                        155,
                        180,
                        205
                )
        );


        universidad.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );


        universidad.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        menu.add(
                universidad
        );


        return menu;
    }


    private void agregarBotonMenu(
            JPanel menu,
            String texto,
            String pantalla,
            String titulo,
            String subtitulo) {

        JButton boton =
                crearBotonMenu(
                        texto
                );


        boton.addActionListener(
                e -> mostrarPantalla(
                        pantalla,
                        titulo,
                        subtitulo
                )
        );


        menu.add(
                boton
        );


        menu.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );
    }


    private JButton crearBotonMenu(
            String texto) {

        JButton boton =
                new JButton(
                        texto
                );


        boton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );


        boton.setPreferredSize(
                new Dimension(
                        210,
                        40
                )
        );


        boton.setHorizontalAlignment(
                SwingConstants.LEFT
        );


        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        boton.setForeground(
                new Color(
                        225,
                        235,
                        245
                )
        );


        boton.setBackground(
                AZUL_OSCURO
        );


        boton.setOpaque(
                true
        );


        boton.setContentAreaFilled(
                true
        );


        boton.setBorderPainted(
                false
        );


        boton.setBorder(
                new EmptyBorder(
                        0,
                        14,
                        0,
                        10
                )
        );


        boton.setFocusPainted(
                false
        );


        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        boton.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e) {

                        boton.setBackground(
                                AZUL_PRINCIPAL
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e) {

                        boton.setBackground(
                                AZUL_OSCURO
                        );
                    }
                }
        );


        return boton;
    }


    // ---------------------------------------------------------
    // ENCABEZADO
    // ---------------------------------------------------------

    private JPanel crearEncabezado() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );


        panel.setBackground(
                BLANCO
        );


        panel.setPreferredSize(
                new Dimension(
                        0,
                        92
                )
        );


        panel.setBorder(
                new EmptyBorder(
                        18,
                        32,
                        15,
                        32
                )
        );


        JPanel textos =
                new JPanel();


        textos.setLayout(
                new BoxLayout(
                        textos,
                        BoxLayout.Y_AXIS
                )
        );


        textos.setOpaque(
                false
        );


        lblTituloPagina =
                new JLabel(
                        "Inicio"
                );


        lblTituloPagina.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );


        lblTituloPagina.setForeground(
                TEXTO_PRINCIPAL
        );


        lblSubtituloPagina =
                new JLabel(
                        "Resumen general del monedero virtual"
                );


        lblSubtituloPagina.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        lblSubtituloPagina.setForeground(
                TEXTO_SECUNDARIO
        );


        textos.add(
                lblTituloPagina
        );


        textos.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );


        textos.add(
                lblSubtituloPagina
        );


        panel.add(
                textos,
                BorderLayout.WEST
        );


        JLabel estado =
                new JLabel(
                        "Sistema activo"
                );


        estado.setForeground(
                VERDE
        );


        estado.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        panel.add(
                estado,
                BorderLayout.EAST
        );


        return panel;
    }


    // ---------------------------------------------------------
    // INICIO
    // ---------------------------------------------------------

    private JPanel crearPanelInicio() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                25
                        )
                );


        panel.setBackground(
                FONDO
        );


        panel.setBorder(
                new EmptyBorder(
                        28,
                        32,
                        30,
                        32
                )
        );


        JPanel tarjetas =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                18,
                                0
                        )
                );


        tarjetas.setOpaque(
                false
        );


        lblCantidadClientes =
                new JLabel(
                        "0"
                );


        lblCantidadMonederos =
                new JLabel(
                        "0"
                );


        lblSaldoSistema =
                new JLabel(
                        "$ 0.00"
                );


        lblIntegridad =
                new JLabel(
                        "Correcta"
                );


        tarjetas.add(
                crearTarjetaResumen(
                        "Clientes",
                        lblCantidadClientes,
                        "Registrados en el sistema"
                )
        );


        tarjetas.add(
                crearTarjetaResumen(
                        "Monederos",
                        lblCantidadMonederos,
                        "Cuentas virtuales creadas"
                )
        );


        tarjetas.add(
                crearTarjetaResumen(
                        "Saldo total",
                        lblSaldoSistema,
                        "Dinero actual del sistema"
                )
        );


        tarjetas.add(
                crearTarjetaResumen(
                        "Integridad",
                        lblIntegridad,
                        "Estado de las transacciones"
                )
        );


        panel.add(
                tarjetas,
                BorderLayout.NORTH
        );


        JPanel centro =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                22,
                                0
                        )
                );


        centro.setOpaque(
                false
        );


        centro.add(
                crearPanelBienvenida()
        );


        centro.add(
                crearPanelAccesosRapidos()
        );


        panel.add(
                centro,
                BorderLayout.CENTER
        );


        return panel;
    }


    private JPanel crearTarjetaResumen(
            String titulo,
            JLabel valor,
            String descripcion) {

        JPanel tarjeta =
                new JPanel();


        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS
                )
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
                                20,
                                20,
                                20
                        )
                )
        );


        JLabel lblTitulo =
                new JLabel(
                        titulo
                );


        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        lblTitulo.setForeground(
                TEXTO_SECUNDARIO
        );


        valor.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );


        valor.setForeground(
                AZUL_PRINCIPAL
        );


        JLabel lblDescripcion =
                new JLabel(
                        descripcion
                );


        lblDescripcion.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );


        lblDescripcion.setForeground(
                new Color(
                        135,
                        145,
                        158
                )
        );


        tarjeta.add(
                lblTitulo
        );


        tarjeta.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                10
                        )
                )
        );


        tarjeta.add(
                valor
        );


        tarjeta.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                8
                        )
                )
        );


        tarjeta.add(
                lblDescripcion
        );


        return tarjeta;
    }


    private JPanel crearPanelBienvenida() {

        JPanel panel =
                new JPanel();


        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );


        panel.setBackground(
                AZUL_PRINCIPAL
        );


        panel.setBorder(
                new EmptyBorder(
                        32,
                        32,
                        32,
                        32
                )
        );


        JLabel titulo =
                new JLabel(
                        "Bienvenido al Monedero Virtual"
                );


        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );


        titulo.setForeground(
                BLANCO
        );


        JLabel texto =
                new JLabel(
                        "<html><div style='width:390px'>"
                                +
                                "Administra clientes, monederos, transacciones, "
                                +
                                "puntos, beneficios y estructuras de datos "
                                +
                                "desde una sola aplicación."
                                +
                                "</div></html>"
                );


        texto.setForeground(
                new Color(
                        225,
                        235,
                        245
                )
        );


        texto.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );


        JLabel proyecto =
                new JLabel(
                        "Proyecto Final - Estructuras de Datos"
                );


        proyecto.setForeground(
                new Color(
                        180,
                        210,
                        238
                )
        );


        proyecto.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        panel.add(
                titulo
        );


        panel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                20
                        )
                )
        );


        panel.add(
                texto
        );


        panel.add(
                Box.createVerticalGlue()
        );


        panel.add(
                proyecto
        );


        return panel;
    }


    private JPanel crearPanelAccesosRapidos() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
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
                                25,
                                25,
                                25,
                                25
                        )
                )
        );


        JLabel titulo =
                new JLabel(
                        "Accesos rápidos"
                );


        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );


        titulo.setForeground(
                TEXTO_PRINCIPAL
        );


        panel.add(
                titulo,
                BorderLayout.NORTH
        );


        JPanel botones =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                12,
                                12
                        )
                );


        botones.setOpaque(
                false
        );


        botones.add(
                crearBotonAcceso(
                        "Registrar cliente",
                        "CLIENTES",
                        "Clientes y monederos",
                        "Gestión de clientes y sus cuentas virtuales"
                )
        );


        botones.add(
                crearBotonAcceso(
                        "Nueva transacción",
                        "TRANSACCIONES",
                        "Transacciones",
                        "Depósitos, retiros, transferencias y gastos"
                )
        );


        botones.add(
                crearBotonAcceso(
                        "Puntos",
                        "PUNTOS",
                        "Puntos y beneficios",
                        "Consulta de puntos, rangos y beneficios"
                )
        );


        botones.add(
                crearBotonAcceso(
                        "Programar",
                        "PROGRAMADAS",
                        "Transacciones programadas",
                        "Operaciones automáticas y recurrentes"
                )
        );


        botones.add(
                crearBotonAcceso(
                        "Notificaciones",
                        "NOTIFICACIONES",
                        "Notificaciones",
                        "Historial circular y pendientes FIFO"
                )
        );


        botones.add(
                crearBotonAcceso(
                        "Reversiones",
                        "REVERSIONES",
                        "Reversiones",
                        "Pila de transacciones reversibles"
                )
        );


        panel.add(
                botones,
                BorderLayout.CENTER
        );


        return panel;
    }


    private JButton crearBotonAcceso(
            String texto,
            String pantalla,
            String titulo,
            String subtitulo) {

        JButton boton =
                new JButton(
                        texto
                );


        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        boton.setForeground(
                AZUL_PRINCIPAL
        );


        boton.setBackground(
                AZUL_CLARO
        );


        boton.setFocusPainted(
                false
        );


        boton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        boton.addActionListener(
                e -> mostrarPantalla(
                        pantalla,
                        titulo,
                        subtitulo
                )
        );


        return boton;
    }


    // ---------------------------------------------------------
    // PROVISIONALES
    // ---------------------------------------------------------

    private JPanel crearPanelProvisional(
            String titulo,
            String descripcion) {

        JPanel fondo =
                new JPanel(
                        new GridBagLayout()
                );


        fondo.setBackground(
                FONDO
        );


        JPanel tarjeta =
                new JPanel();


        tarjeta.setLayout(
                new BoxLayout(
                        tarjeta,
                        BoxLayout.Y_AXIS
                )
        );


        tarjeta.setPreferredSize(
                new Dimension(
                        650,
                        300
                )
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
                                45,
                                45,
                                45,
                                45
                        )
                )
        );


        JLabel lblTitulo =
                new JLabel(
                        titulo
                );


        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );


        JLabel lblDescripcion =
                new JLabel(
                        descripcion
                );


        lblDescripcion.setForeground(
                TEXTO_SECUNDARIO
        );


        JLabel lblEstado =
                new JLabel(
                        "La funcionalidad será conectada en la siguiente fase."
                );


        lblEstado.setForeground(
                AZUL_PRINCIPAL
        );


        tarjeta.add(
                lblTitulo
        );


        tarjeta.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                18
                        )
                )
        );


        tarjeta.add(
                lblDescripcion
        );


        tarjeta.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                35
                        )
                )
        );


        tarjeta.add(
                lblEstado
        );


        fondo.add(
                tarjeta
        );


        return fondo;
    }


    // ---------------------------------------------------------
    // NAVEGACIÓN
    // ---------------------------------------------------------

    private void mostrarPantalla(
            String pantalla,
            String titulo,
            String subtitulo) {

        cardLayout.show(
                panelContenido,
                pantalla
        );


        lblTituloPagina.setText(
                titulo
        );


        lblSubtituloPagina.setText(
                subtitulo
        );


        if (pantalla.equals("INICIO")) {
            actualizarDashboard();
        }


        if (pantalla.equals("CLIENTES")) {
            panelClientesMonederos.actualizarDatos();
        }


        if (pantalla.equals("TRANSACCIONES")) {
            panelTransacciones.actualizarDatos();
        }


        if (pantalla.equals("REVERSIONES")) {
            panelReversiones.actualizarDatos();
        }


        if (pantalla.equals("PUNTOS")) {
            panelPuntosBeneficios.actualizarDatos();
        }


        if (pantalla.equals("PROGRAMADAS")) {
            panelProgramadas.actualizarDatos();
        }


        if (pantalla.equals("NOTIFICACIONES")) {
            panelNotificaciones.actualizarDatos();
        }
    }


    // ---------------------------------------------------------
    // ACTUALIZAR TODO
    // ---------------------------------------------------------

    private void actualizarDatosGenerales() {

        actualizarDashboard();


        if (panelClientesMonederos != null) {
            panelClientesMonederos.actualizarDatos();
        }


        if (panelTransacciones != null) {
            panelTransacciones.actualizarDatos();
        }


        if (panelPuntosBeneficios != null) {
            panelPuntosBeneficios.actualizarDatos();
        }


        if (panelProgramadas != null) {
            panelProgramadas.actualizarDatos();
        }


        if (panelNotificaciones != null) {
            panelNotificaciones.actualizarDatos();
        }


        if (panelReversiones != null) {
            panelReversiones.actualizarDatos();
        }
    }


    // ---------------------------------------------------------
    // DASHBOARD
    // ---------------------------------------------------------

    private void actualizarDashboard() {

        List<Cliente> clientes =
                sistema.obtenerClientes();


        int cantidadClientes =
                clientes.size();


        int cantidadMonederos =
                0;


        for (Cliente cliente :
                clientes) {

            cantidadMonederos +=
                    cliente
                            .obtenerMonederos()
                            .size();
        }


        ReporteSistema reporte =
                sistema.generarReporteSistema();


        lblCantidadClientes.setText(
                String.valueOf(
                        cantidadClientes
                )
        );


        lblCantidadMonederos.setText(
                String.valueOf(
                        cantidadMonederos
                )
        );


        lblSaldoSistema.setText(
                String.format(
                        "$ %,.2f",
                        reporte.getSaldoTotalSistema()
                )
        );


        if (reporte.isIntegridadValida()) {

            lblIntegridad.setText(
                    "Correcta"
            );


            lblIntegridad.setForeground(
                    VERDE
            );

        } else {

            lblIntegridad.setText(
                    "Revisar"
            );


            lblIntegridad.setForeground(
                    new Color(
                            190,
                            65,
                            65
                    )
            );
        }
    }
}