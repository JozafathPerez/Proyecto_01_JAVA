package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnviarCartonesPanel extends JPanel {
    private JTextField cantidadCartonesField;
    private JTextField cedulaField;
    private JButton enviarCartonesButton;
    private Juego logica;

    public EnviarCartonesPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

        // Configura el diseño del panel
        setLayout(null);

        // Crear una fuente con el tipo Arial y tamaño 24
        Font fuente = new Font("Arial", Font.PLAIN, 20);

        // Etiqueta y campo de texto para la cantidad de cartones
        JLabel cantidadLabel = new JLabel("Cantidad de cartones a enviar:");
        cantidadLabel.setBounds(80, 200, 300, 30);
        cantidadLabel.setFont(fuente); // Cambiar el tamaño de fuente
        add(cantidadLabel);
        cantidadCartonesField = new JTextField();
        cantidadCartonesField.setBounds(360, 200, 300, 30);
        add(cantidadCartonesField);

        // Etiqueta y campo de texto para la cédula del jugador
        JLabel cedulaLabel = new JLabel("Cédula del jugador (número):");
        cedulaLabel.setBounds(80, 250, 300, 30);
        cedulaLabel.setFont(fuente); // Cambiar el tamaño de fuente
        add(cedulaLabel);
        cedulaField = new JTextField();
        cedulaField.setBounds(360, 250, 300, 30);
        add(cedulaField);

        // Botón para enviar los cartones
        enviarCartonesButton = new JButton("Enviar Cartones");
        enviarCartonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarCartones(); // Método para enviar los cartones
            }
        });
        enviarCartonesButton.setBounds(510, 300, 150, 40);
        add(enviarCartonesButton);

        // Botón para regresar al menú principal
        JButton regresarMenuButton = new JButton("◀ REGRESAR");
        regresarMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("menu"); // Método para volver al menú principal
            }
        });
        regresarMenuButton.setBounds(10, 10, 110, 25);
        add(regresarMenuButton);

    }

    // Método para enviar los cartones
    private void enviarCartones() {
        String cantidadCartonesStr = cantidadCartonesField.getText();
        String cedulaStr = cedulaField.getText();

        // Validación de la cédula (debe ser un número)
        try {
            Integer.parseInt(cedulaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido.");
            return;
        }

        // Validación de jugador existente
        if (logica.obtenerJugadorPorCedula(cedulaStr) == null) {
            JOptionPane.showMessageDialog(this, "Jugador no encontrado.");
            return;
        }

        // Validación de la cantidad (debe ser un número)
        int cantidadCartones;
        try {
            cantidadCartones = Integer.parseInt(cantidadCartonesStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad de cartones a enviar debe ser un número válido.");
            return;
        }

        // Validación que la cantidad de cartones sea al menos 1

        if (cantidadCartones < 1) {
            JOptionPane.showMessageDialog(this, "Debe de enviar al menos un carton");
            return;
        }

        logica.enviarCartonAJugador(cantidadCartones, cedulaStr);;
        JOptionPane.showMessageDialog(this, "Enviando " + cantidadCartonesStr + " cartones al jugador con cédula: " + cedulaStr);
        setGui();
    }

    public void setGui() {
        cantidadCartonesField.setText("");
        cedulaField.setText("");
    }
}
