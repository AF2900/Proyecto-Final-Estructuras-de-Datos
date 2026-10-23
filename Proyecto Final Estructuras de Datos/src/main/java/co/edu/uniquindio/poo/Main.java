package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.ui.InterfazGrafica;

import javax.swing.*;

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

            UIManager.setLookAndFeel(
                    UIManager
                            .getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            System.out.println(
                    "No fue posible aplicar la apariencia del sistema."
            );
        }
    }
}