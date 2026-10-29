package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.ui.InterfazGrafica;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    configurarApariencia();

                    InterfazGrafica interfaz =
                            new InterfazGrafica();

                    interfaz.setVisible(
                            true
                    );
                }
        );
    }


    private static void configurarApariencia() {

        try {

            /*
             * Usamos el Look & Feel estándar de Java.
             *
             * El Look & Feel nativo de Windows estaba
             * sobrescribiendo algunos colores de los
             * botones y provocaba que el texto se viera
             * demasiado claro o casi invisible.
             */
            UIManager.setLookAndFeel(
                    UIManager
                            .getCrossPlatformLookAndFeelClassName()
            );


            // -------------------------------------------------
            // ESTILO GENERAL DE BOTONES
            // -------------------------------------------------

            UIManager.put(
                    "Button.font",
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13
                    )
            );


            /*
             * Los botones que no tengan colores personalizados
             * mostrarán texto azul oscuro sobre fondo claro.
             */
            UIManager.put(
                    "Button.foreground",
                    new Color(
                            20,
                            65,
                            120
                    )
            );


            UIManager.put(
                    "Button.background",
                    new Color(
                            235,
                            240,
                            246
                    )
            );


            /*
             * Evita que el texto de un botón deshabilitado
             * quede prácticamente invisible.
             */
            UIManager.put(
                    "Button.disabledText",
                    new Color(
                            100,
                            110,
                            125
                    )
            );


            // -------------------------------------------------
            // OTROS COMPONENTES
            // -------------------------------------------------

            UIManager.put(
                    "TabbedPane.font",
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13
                    )
            );


            UIManager.put(
                    "Label.font",
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            13
                    )
            );


            UIManager.put(
                    "ComboBox.font",
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            13
                    )
            );


            UIManager.put(
                    "TextField.font",
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            13
                    )
            );


            UIManager.put(
                    "Table.font",
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            12
                    )
            );


            UIManager.put(
                    "TableHeader.font",
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            12
                    )
            );


        } catch (Exception e) {

            System.out.println(
                    "No fue posible aplicar la apariencia gráfica."
            );
        }
    }
}