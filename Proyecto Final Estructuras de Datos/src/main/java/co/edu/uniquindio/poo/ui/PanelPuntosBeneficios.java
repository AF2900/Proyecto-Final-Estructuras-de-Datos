package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.enums.RangoCliente;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.model.BeneficioCanjeado;
import co.edu.uniquindio.poo.model.Cliente;
import co.edu.uniquindio.poo.model.Monedero;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class PanelPuntosBeneficios extends JPanel {

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

    private static final Color ROJO =
            new Color(190, 65, 65);


    private final MonederoVirtual sistema;

    private final Runnable alActualizarDatos;


    private JComboBox<String> cmbCliente;

    private JComboBox<String> cmbMonederoBono;


    private JLabel lblNombre;

    private JLabel lblPuntos;

    private JLabel lblRango;


    private JLabel lblEstadoDescuento;

    private JLabel lblEstadoRetiros;

    private JLabel lblEstadoBono;


    private DefaultTableModel modeloBeneficios;


    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
            );


    public PanelPuntosBeneficios(
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
    // CONFIGURACIÓN
    // ---------------------------------------------------------

    private void configurarPanel() {

        setLayout(
                new BorderLayout(
                        0,
                        18
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

    private void construirInterfaz() {

        add(
                crearPanelResumen(),
                BorderLayout.NORTH
        );


        JPanel centro =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );


        centro.setOpaque(
                false
        );


        centro.add(
                crearPanelBeneficios(),
                BorderLayout.NORTH
        );


        centro.add(
                crearPanelHistorial(),
                BorderLayout.CENTER
        );


        add(
                centro,
                BorderLayout.CENTER
        );
    }


    // ---------------------------------------------------------
    // RESUMEN
    // ---------------------------------------------------------

    private JPanel crearPanelResumen() {

        JPanel tarjeta =
                crearTarjeta();


        tarjeta.setLayout(
                new BorderLayout(
                        20,
                        0
                )
        );


        JPanel seleccion =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                4
                        )
                );


        seleccion.setOpaque(
                false
        );


        seleccion.add(
                crearEtiqueta(
                        "Cliente"
                )
        );


        cmbCliente =
                new JComboBox<>();


        cmbCliente.setPreferredSize(
                new Dimension(
                        180,
                        34
                )
        );


        cmbCliente.addActionListener(
                e -> actualizarResumenCliente()
        );


        seleccion.add(
                cmbCliente
        );


        tarjeta.add(
                seleccion,
                BorderLayout.WEST
        );


        JPanel datos =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );


        datos.setOpaque(
                false
        );


        lblNombre =
                new JLabel(
                        "-"
                );


        lblPuntos =
                new JLabel(
                        "0"
                );


        lblRango =
                new JLabel(
                        "-"
                );


        datos.add(
                crearDatoResumen(
                        "Cliente",
                        lblNombre
                )
        );


        datos.add(
                crearDatoResumen(
                        "Puntos",
                        lblPuntos
                )
        );


        datos.add(
                crearDatoResumen(
                        "Rango",
                        lblRango
                )
        );


        tarjeta.add(
                datos,
                BorderLayout.CENTER
        );


        return tarjeta;
    }


    private JPanel crearDatoResumen(
            String titulo,
            JLabel valor) {

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
                crearEtiqueta(
                        titulo
                );


        valor.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        valor.setForeground(
                AZUL_PRINCIPAL
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
                valor
        );


        return panel;
    }


    // ---------------------------------------------------------
    // BENEFICIOS
    // ---------------------------------------------------------

    private JPanel crearPanelBeneficios() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );


        panel.setOpaque(
                false
        );


        lblEstadoDescuento =
                new JLabel(
                        "No activo"
                );


        lblEstadoRetiros =
                new JLabel(
                        "No activo"
                );


        lblEstadoBono =
                new JLabel(
                        "Sin canjes"
                );


        panel.add(
                crearTarjetaDescuento()
        );


        panel.add(
                crearTarjetaRetiros()
        );


        panel.add(
                crearTarjetaBono()
        );


        return panel;
    }


    // ---------------------------------------------------------
    // DESCUENTO TRANSFERENCIA
    // ---------------------------------------------------------

    private JPanel crearTarjetaDescuento() {

        JPanel tarjeta =
                crearTarjetaBeneficioBase(
                        "100 puntos",
                        "10 % menos comisión",
                        "Reduce en un 10 % la comisión calculada para las transferencias.",
                        lblEstadoDescuento
                );


        JButton boton =
                crearBotonPrincipal(
                        "Canjear"
                );


        boton.addActionListener(
                e -> canjearBeneficio(
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA
                )
        );


        tarjeta.add(
                boton,
                BorderLayout.SOUTH
        );


        return tarjeta;
    }


    // ---------------------------------------------------------
    // RETIROS SIN CARGO
    // ---------------------------------------------------------

    private JPanel crearTarjetaRetiros() {

        JPanel tarjeta =
                crearTarjetaBeneficioBase(
                        "500 puntos",
                        "Retiros sin comisión",
                        "Durante un mes los retiros no generan comisión.",
                        lblEstadoRetiros
                );


        JButton boton =
                crearBotonPrincipal(
                        "Canjear"
                );


        boton.addActionListener(
                e -> canjearBeneficio(
                        TipoBeneficio
                                .RETIROS_SIN_CARGO
                )
        );


        tarjeta.add(
                boton,
                BorderLayout.SOUTH
        );


        return tarjeta;
    }


    // ---------------------------------------------------------
    // BONO SALDO
    // ---------------------------------------------------------

    private JPanel crearTarjetaBono() {

        JPanel tarjeta =
                crearTarjetaBeneficioBase(
                        "1000 puntos",
                        "Bono de 50 unidades",
                        "Agrega 50 unidades directamente al monedero seleccionado.",
                        lblEstadoBono
                );


        JPanel inferior =
                new JPanel();


        inferior.setLayout(
                new BoxLayout(
                        inferior,
                        BoxLayout.Y_AXIS
                )
        );


        inferior.setOpaque(
                false
        );


        JLabel lblMonedero =
                crearEtiqueta(
                        "Monedero"
                );


        cmbMonederoBono =
                new JComboBox<>();


        cmbMonederoBono.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        32
                )
        );


        JButton boton =
                crearBotonPrincipal(
                        "Canjear bono"
                );


        boton.addActionListener(
                e -> canjearBeneficio(
                        TipoBeneficio.BONO_SALDO
                )
        );


        inferior.add(
                lblMonedero
        );


        inferior.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );


        inferior.add(
                cmbMonederoBono
        );


        inferior.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                10
                        )
                )
        );


        inferior.add(
                boton
        );


        tarjeta.add(
                inferior,
                BorderLayout.SOUTH
        );


        return tarjeta;
    }


    private JPanel crearTarjetaBeneficioBase(
            String puntos,
            String titulo,
            String descripcion,
            JLabel estado) {

        JPanel tarjeta =
                crearTarjeta();


        tarjeta.setPreferredSize(
                new Dimension(
                        250,
                        245
                )
        );


        JPanel contenido =
                new JPanel();


        contenido.setLayout(
                new BoxLayout(
                        contenido,
                        BoxLayout.Y_AXIS
                )
        );


        contenido.setOpaque(
                false
        );


        JLabel lblPuntosBeneficio =
                new JLabel(
                        puntos
                );


        lblPuntosBeneficio.setForeground(
                AZUL_PRINCIPAL
        );


        lblPuntosBeneficio.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        JLabel lblTitulo =
                new JLabel(
                        titulo
                );


        lblTitulo.setForeground(
                TEXTO_PRINCIPAL
        );


        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );


        JLabel lblDescripcion =
                new JLabel(
                        "<html><div style='width:200px'>"
                                +
                                descripcion
                                +
                                "</div></html>"
                );


        lblDescripcion.setForeground(
                TEXTO_SECUNDARIO
        );


        lblDescripcion.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        estado.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        contenido.add(
                lblPuntosBeneficio
        );


        contenido.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                7
                        )
                )
        );


        contenido.add(
                lblTitulo
        );


        contenido.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                10
                        )
                )
        );


        contenido.add(
                lblDescripcion
        );


        contenido.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                12
                        )
                )
        );


        contenido.add(
                estado
        );


        tarjeta.add(
                contenido,
                BorderLayout.CENTER
        );


        return tarjeta;
    }


    // ---------------------------------------------------------
    // HISTORIAL
    // ---------------------------------------------------------

    private JPanel crearPanelHistorial() {

        JPanel tarjeta =
                crearTarjeta();


        JLabel titulo =
                new JLabel(
                        "Historial de beneficios"
                );


        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );


        titulo.setForeground(
                TEXTO_PRINCIPAL
        );


        tarjeta.add(
                titulo,
                BorderLayout.NORTH
        );


        modeloBeneficios =
                new DefaultTableModel(
                        new Object[]{
                                "Código",
                                "Beneficio",
                                "Puntos",
                                "Fecha canje",
                                "Vencimiento",
                                "Monedero",
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
                        modeloBeneficios
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
    // CANJEAR
    // ---------------------------------------------------------

    private void canjearBeneficio(
            TipoBeneficio tipo) {

        String cliente =
                obtenerClienteSeleccionado();


        if (cliente == null) {

            mostrarAdvertencia(
                    "Primero debe seleccionar un cliente."
            );

            return;
        }


        String idMonedero =
                null;


        if (tipo ==
                TipoBeneficio.BONO_SALDO) {

            if (cmbMonederoBono
                    .getSelectedItem()
                    == null) {

                mostrarAdvertencia(
                        "Seleccione el monedero donde se aplicará el bono."
                );

                return;
            }


            idMonedero =
                    cmbMonederoBono
                            .getSelectedItem()
                            .toString();
        }


        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea canjear este beneficio?\n\n"
                                +
                                tipo.getDescripcion()
                                +
                                "\nCosto: "
                                +
                                tipo.getCostoPuntos()
                                +
                                " puntos",
                        "Confirmar canje",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


        if (respuesta !=
                JOptionPane.YES_OPTION) {

            return;
        }


        BeneficioCanjeado beneficio =
                sistema.canjearBeneficio(
                        cliente,
                        tipo,
                        idMonedero
                );


        if (beneficio == null) {

            mostrarError(
                    "No fue posible canjear el beneficio.\n\n"
                            +
                            "Verifique que el cliente tenga suficientes puntos "
                            +
                            "y que el monedero seleccionado sea válido."
            );

            return;
        }


        actualizarDatos();


        if (alActualizarDatos != null) {

            alActualizarDatos.run();
        }


        StringBuilder mensaje =
                new StringBuilder();


        mensaje.append(
                "Beneficio canjeado correctamente.\n\n"
        );


        mensaje.append(
                "Código: "
        );

        mensaje.append(
                beneficio.getCodigo()
        );


        mensaje.append(
                "\nBeneficio: "
        );

        mensaje.append(
                tipo.getDescripcion()
        );


        mensaje.append(
                "\nPuntos utilizados: "
        );

        mensaje.append(
                beneficio.getPuntosUtilizados()
        );


        if (beneficio.getFechaVencimiento()
                != null) {

            mensaje.append(
                    "\nVigente hasta: "
            );

            mensaje.append(
                    beneficio
                            .getFechaVencimiento()
                            .format(
                                    formatoFecha
                            )
            );
        }


        if (beneficio
                .getIdMonederoBeneficiado()
                != null) {

            mensaje.append(
                    "\nMonedero: "
            );

            mensaje.append(
                    beneficio
                            .getIdMonederoBeneficiado()
            );
        }


        JOptionPane.showMessageDialog(
                this,
                mensaje.toString(),
                "Canje realizado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // ---------------------------------------------------------
    // ACTUALIZAR
    // ---------------------------------------------------------

    public void actualizarDatos() {

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


        actualizarResumenCliente();
    }


    // ---------------------------------------------------------
    // RESUMEN CLIENTE
    // ---------------------------------------------------------

    private void actualizarResumenCliente() {

        String identificacion =
                obtenerClienteSeleccionado();


        if (identificacion == null) {

            lblNombre.setText(
                    "-"
            );


            lblPuntos.setText(
                    "0"
            );


            lblRango.setText(
                    "-"
            );


            cmbMonederoBono
                    .removeAllItems();


            modeloBeneficios.setRowCount(
                    0
            );


            actualizarEstado(
                    lblEstadoDescuento,
                    "No activo",
                    false
            );


            actualizarEstado(
                    lblEstadoRetiros,
                    "No activo",
                    false
            );


            actualizarEstado(
                    lblEstadoBono,
                    "Sin canjes",
                    false
            );


            return;
        }


        Cliente cliente =
                sistema.buscarCliente(
                        identificacion
                );


        if (cliente == null) {
            return;
        }


        lblNombre.setText(
                cliente.getNombre()
        );


        Integer puntos =
                sistema.consultarPuntos(
                        identificacion
                );


        lblPuntos.setText(
                String.valueOf(
                        puntos == null
                                ? 0
                                : puntos
                )
        );


        RangoCliente rango =
                sistema.consultarRango(
                        identificacion
                );


        lblRango.setText(
                rango == null
                        ?
                        "-"
                        :
                        rango.toString()
        );


        actualizarMonederos(
                cliente
        );


        actualizarEstadosBeneficios(
                identificacion
        );


        actualizarTablaBeneficios(
                identificacion
        );
    }


    // ---------------------------------------------------------
    // MONEDEROS
    // ---------------------------------------------------------

    private void actualizarMonederos(
            Cliente cliente) {

        Object seleccionAnterior =
                cmbMonederoBono
                        .getSelectedItem();


        cmbMonederoBono
                .removeAllItems();


        List<Monedero> monederos =
                cliente.obtenerMonederos();


        monederos.sort(
                Comparator.comparing(
                        Monedero::getId
                )
        );


        for (Monedero monedero :
                monederos) {

            cmbMonederoBono.addItem(
                    monedero.getId()
            );
        }


        if (seleccionAnterior != null) {

            cmbMonederoBono
                    .setSelectedItem(
                            seleccionAnterior
                    );
        }
    }


    // ---------------------------------------------------------
    // ESTADOS
    // ---------------------------------------------------------

    private void actualizarEstadosBeneficios(
            String identificacion) {

        boolean descuentoActivo =
                sistema.tieneBeneficioActivo(
                        identificacion,
                        TipoBeneficio
                                .DESCUENTO_TRANSFERENCIA
                );


        actualizarEstado(
                lblEstadoDescuento,
                descuentoActivo
                        ?
                        "ACTIVO"
                        :
                        "No activo",
                descuentoActivo
        );


        boolean retirosActivo =
                sistema.tieneBeneficioActivo(
                        identificacion,
                        TipoBeneficio
                                .RETIROS_SIN_CARGO
                );


        if (retirosActivo) {

            LocalDateTime vencimiento =
                    sistema.obtenerVencimientoBeneficio(
                            identificacion,
                            TipoBeneficio
                                    .RETIROS_SIN_CARGO
                    );


            String texto =
                    "ACTIVO";


            if (vencimiento != null) {

                texto +=
                        " hasta "
                                +
                                vencimiento.format(
                                        DateTimeFormatter
                                                .ofPattern(
                                                        "dd/MM/yyyy"
                                                )
                                );
            }


            actualizarEstado(
                    lblEstadoRetiros,
                    texto,
                    true
            );

        } else {

            actualizarEstado(
                    lblEstadoRetiros,
                    "No activo",
                    false
            );
        }


        long bonos =
                sistema.obtenerBeneficios(
                                identificacion
                        )
                        .stream()
                        .filter(
                                beneficio ->
                                        beneficio.getTipo()
                                                ==
                                                TipoBeneficio.BONO_SALDO
                        )
                        .count();


        actualizarEstado(
                lblEstadoBono,
                "Canjes realizados: "
                        + bonos,
                bonos > 0
        );
    }


    private void actualizarEstado(
            JLabel label,
            String texto,
            boolean activo) {

        label.setText(
                texto
        );


        label.setForeground(
                activo
                        ?
                        VERDE
                        :
                        ROJO
        );
    }


    // ---------------------------------------------------------
    // TABLA
    // ---------------------------------------------------------

    private void actualizarTablaBeneficios(
            String identificacion) {

        modeloBeneficios.setRowCount(
                0
        );


        List<BeneficioCanjeado> beneficios =
                sistema.obtenerBeneficios(
                        identificacion
                );


        for (BeneficioCanjeado beneficio :
                beneficios) {

            String vencimiento =
                    beneficio.getFechaVencimiento()
                            == null
                            ?
                            "-"
                            :
                            beneficio
                                    .getFechaVencimiento()
                                    .format(
                                            formatoFecha
                                    );


            String monedero =
                    beneficio
                            .getIdMonederoBeneficiado()
                            == null
                            ?
                            "-"
                            :
                            beneficio
                                    .getIdMonederoBeneficiado();


            String estado;


            if (beneficio.getTipo()
                    == TipoBeneficio.BONO_SALDO) {

                estado =
                        "APLICADO";

            } else {

                estado =
                        beneficio.estaVigente()
                                ?
                                "ACTIVO"
                                :
                                "VENCIDO";
            }


            modeloBeneficios.addRow(
                    new Object[]{
                            beneficio.getCodigo(),
                            beneficio
                                    .getTipo()
                                    .getDescripcion(),
                            beneficio
                                    .getPuntosUtilizados(),
                            beneficio
                                    .getFechaCanje()
                                    .format(
                                    formatoFecha
                            ),
                            vencimiento,
                            monedero,
                            estado
                    }
            );
        }
    }


    // ---------------------------------------------------------
    // CLIENTE SELECCIONADO
    // ---------------------------------------------------------

    private String obtenerClienteSeleccionado() {

        if (cmbCliente == null ||
                cmbCliente.getSelectedItem()
                        == null) {

            return null;
        }


        return cmbCliente
                .getSelectedItem()
                .toString();
    }


    // ---------------------------------------------------------
    // TARJETAS
    // ---------------------------------------------------------

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
                        12
                )
        );


        label.setForeground(
                TEXTO_SECUNDARIO
        );


        return label;
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


        boton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
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
    // MENSAJES
    // ---------------------------------------------------------

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
                "Canje no realizado",
                JOptionPane.ERROR_MESSAGE
        );
    }
}