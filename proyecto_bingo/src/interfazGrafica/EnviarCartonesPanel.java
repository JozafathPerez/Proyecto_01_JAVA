package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnviarCartonesPanel extends JPanel {
    private JTextField cantidadCartonesField;
    private JTextField cedulaField;
    private JButton enviarCartonesButton;

    public EnviarCartonesPanel() {
        // Configura el diseño del panel
        setLayout(new GridLayout(4, 1));

        // Etiqueta y campo de texto para la cantidad de cartones
        JLabel cantidadLabel = new JLabel("Cantidad de cartones a enviar:");
        add(cantidadLabel);
        cantidadCartonesField = new JTextField();
        add(cantidadCartonesField);

        // Etiqueta y campo de texto para la cédula del jugador
        JLabel cedulaLabel = new JLabel("Cédula del jugador (número):");
        add(cedulaLabel);
        cedulaField = new JTextField();
        add(cedulaField);

        // Botón para enviar los cartones
        enviarCartonesButton = new JButton("Enviar Cartones");
        enviarCartonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarCartones(); // Método para enviar los cartones
            }
        });
        add(enviarCartonesButton);
    }

    // Método para enviar los cartones
    private void enviarCartones() {
        String cantidadCartonesStr = cantidadCartonesField.getText();
        String cedulaStr = cedulaField.getText();

        // Validación de la cédula (debe ser un número)
        try {
            int cedula = Integer.parseInt(cedulaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido.");
            return;
        }

        // la funcion para eso va a aquiquiquiquiquiquiq
        JOptionPane.showMessageDialog(this, "Enviando " + cantidadCartonesStr + " cartones al jugador con cédula: " + cedulaStr);
    }
}
