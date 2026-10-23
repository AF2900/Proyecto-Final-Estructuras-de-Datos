package co.edu.uniquindio.poo.ui;

import co.edu.uniquindio.poo.MonederoVirtual;
import co.edu.uniquindio.poo.model.Transaccion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class PanelReversiones extends JPanel {

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

    private final Runnable alActualizarDatos;


    private JLabel lblCantidad;

    private JLabel lblCodigo;

    private JLabel lblTipo;

    private JLabel lblFecha;

    private JLabel lblMonto;

    private JLabel lblComision;

    private JLabel lblOrigen;

    private JLabel lblDestino;

    private JLabel lblPuntos;


    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
            );


    public PanelReversiones(
            MonederoVirtual sistema,
            Runnable alActualizarDatos) {

        this.sistema =
                sistema;

        this.alActualizarDatos =
                alActualizarDatos;


        construir();

        actualizarDatos();
    }


    private void construir() {

        setLayout(
                new GridBagLayout()
        );


        setBackground(
                FONDO
        );


        JPanel tarjeta =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );


        tarjeta.setPreferredSize(
                new Dimension(
                        680,
                        470
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
                                28,
                                32,
                                28,
                                32
                        )
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


        JLabel titulo =
                new JLabel(
                        "Pila de reversiones"
                );


        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );


        titulo.setForeground(
                TEXTO
        );


        JLabel descripcion =
                new JLabel(
                        "La última transacción registrada es la primera que puede revertirse (LIFO)."
                );


        descripcion.setForeground(
                SECUNDARIO
        );


        lblCantidad =
                new JLabel(
                        "Transacciones reversibles: 0"
                );


        lblCantidad.setForeground(
                AZUL
        );


        lblCantidad.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        encabezado.add(
                titulo
        );


        encabezado.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                7
                        )
                )
        );


        encabezado.add(
                descripcion
        );


        encabezado.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                12
                        )
                )
        );


        encabezado.add(
                lblCantidad
        );


        tarjeta.add(
                encabezado,
                BorderLayout.NORTH
        );


        JPanel datos =
                new JPanel(
                        new GridLayout(
                                9,
                                2,
                                10,
                                10
                        )
                );


        datos.setOpaque(
                false
        );


        lblCodigo = new JLabel("-");
        lblTipo = new JLabel("-");
        lblFecha = new JLabel("-");
        lblMonto = new JLabel("-");
        lblComision = new JLabel("-");
        lblOrigen = new JLabel("-");
        lblDestino = new JLabel("-");
        lblPuntos = new JLabel("-");


        agregarDato(
                datos,
                "Código",
                lblCodigo
        );


        agregarDato(
                datos,
                "Tipo",
                lblTipo
        );


        agregarDato(
                datos,
                "Fecha",
                lblFecha
        );


        agregarDato(
                datos,
                "Monto",
                lblMonto
        );


        agregarDato(
                datos,
                "Comisión",
                lblComision
        );


        agregarDato(
                datos,
                "Origen",
                lblOrigen
        );


        agregarDato(
                datos,
                "Destino",
                lblDestino
        );


        agregarDato(
                datos,
                "Puntos generados",
                lblPuntos
        );


        JLabel advertencia =
                new JLabel(
                        "La reversión también restaura la comisión cuando corresponde."
                );


        advertencia.setForeground(
                SECUNDARIO
        );


        datos.add(
                advertencia
        );


        datos.add(
                new JLabel()
        );


        tarjeta.add(
                datos,
                BorderLayout.CENTER
        );


        JButton btnRevertir =
                new JButton(
                        "Revertir última transacción"
                );


        btnRevertir.setBackground(
                AZUL
        );


        btnRevertir.setForeground(
                BLANCO
        );


        btnRevertir.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        btnRevertir.setFocusPainted(
                false
        );


        btnRevertir.addActionListener(
                e -> revertir()
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
                btnRevertir
        );


        tarjeta.add(
                pie,
                BorderLayout.SOUTH
        );


        add(
                tarjeta
        );
    }


    private void agregarDato(
            JPanel panel,
            String titulo,
            JLabel valor) {

        JLabel etiqueta =
                new JLabel(
                        titulo + ":"
                );


        etiqueta.setForeground(
                SECUNDARIO
        );


        valor.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        valor.setForeground(
                TEXTO
        );


        panel.add(
                etiqueta
        );


        panel.add(
                valor
        );
    }


    public void actualizarDatos() {

        lblCantidad.setText(
                "Transacciones reversibles: "
                        +
                        sistema
                                .cantidadTransaccionesReversibles()
        );


        Transaccion transaccion =
                sistema
                        .verUltimaTransaccionReversible();


        if (transaccion == null) {

            limpiar();

            return;
        }


        lblCodigo.setText(
                transaccion.getCodigo()
        );


        lblTipo.setText(
                transaccion
                        .getTipo()
                        .toString()
        );


        lblFecha.setText(
                transaccion
                        .getFecha()
                        .format(
                                formatoFecha
                        )
        );


        lblMonto.setText(
                String.format(
                        "$ %,.2f",
                        transaccion.getMonto()
                )
        );


        lblComision.setText(
                String.format(
                        "$ %,.2f",
                        transaccion.getComision()
                )
        );


        lblOrigen.setText(
                transaccion
                        .getIdentificacionOrigen()
                        +
                        " / "
                        +
                        transaccion
                                .getMonederoOrigen()
        );


        if (transaccion
                .getIdentificacionDestino()
                == null) {

            lblDestino.setText(
                    "-"
            );

        } else {

            lblDestino.setText(
                    transaccion
                            .getIdentificacionDestino()
                            +
                            " / "
                            +
                            transaccion
                                    .getMonederoDestino()
            );
        }


        lblPuntos.setText(
                String.valueOf(
                        transaccion
                                .getPuntosGenerados()
                )
        );
    }


    private void revertir() {

        Transaccion actual =
                sistema
                        .verUltimaTransaccionReversible();


        if (actual == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen transacciones para revertir.",
                    "Reversiones",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        "Se revertirá la última transacción:\n\n"
                                +
                                actual.getCodigo()
                                +
                                " - "
                                +
                                actual.getTipo()
                                +
                                "\nMonto: $ "
                                +
                                String.format(
                                        "%,.2f",
                                        actual.getMonto()
                                )
                                +
                                "\n\n¿Desea continuar?",
                        "Confirmar reversión",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (confirmacion !=
                JOptionPane.YES_OPTION) {

            return;
        }


        Transaccion revertida =
                sistema
                        .revertirUltimaTransaccion();


        if (revertida == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible realizar la reversión.\n"
                            +
                            "La operación puede no cumplir las condiciones necesarias.",
                    "Reversión no realizada",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        actualizarDatos();


        if (alActualizarDatos != null) {

            alActualizarDatos.run();
        }


        JOptionPane.showMessageDialog(
                this,
                "Transacción "
                        +
                        revertida.getCodigo()
                        +
                        " revertida correctamente.",
                "Reversión realizada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    private void limpiar() {

        lblCodigo.setText("-");
        lblTipo.setText("-");
        lblFecha.setText("-");
        lblMonto.setText("-");
        lblComision.setText("-");
        lblOrigen.setText("-");
        lblDestino.setText("-");
        lblPuntos.setText("-");
    }
}