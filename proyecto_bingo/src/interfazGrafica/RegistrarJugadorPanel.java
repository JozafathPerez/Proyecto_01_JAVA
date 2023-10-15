package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarJugadorPanel extends JPanel {
    private JTextField nombreCompletoField;
    private JTextField correoField;
    private JTextField cedulaField;
    private JButton registrarButton;
    private Juego logica;

    public RegistrarJugadorPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

        // Configura el diseño del panel
        setLayout(new GridLayout(5, 1));

        // Etiqueta y campo de texto para el nombre completo
        JLabel nombreLabel = new JLabel("Nombre completo:");
        add(nombreLabel);
        nombreCompletoField = new JTextField();
        add(nombreCompletoField);

        // Etiqueta y campo de texto para el correo electrónico
        JLabel correoLabel = new JLabel("Correo electrónico:");
        add(correoLabel);
        correoField = new JTextField();
        add(correoField);

        // Etiqueta y campo de texto para la cédula
        JLabel cedulaLabel = new JLabel("Cédula (número):");
        add(cedulaLabel);
        cedulaField = new JTextField();
        add(cedulaField);

        // Botón para registrar
        registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarJugador(); // Método para registrar al jugador
            }
        });
        add(registrarButton);

        // Botón para regresar al menú principal
        JButton regresarMenuButton = new JButton("Regresar al Menú Principal");
        regresarMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("menu"); // Método para volver al menú principal
            }
        });
        add(regresarMenuButton);
    }

    // Método para registrar al jugador
    private void registrarJugador() {
        String nombreCompleto = nombreCompletoField.getText();
        String correoElectronico = correoField.getText();
        String cedulaStr = cedulaField.getText();

        // Validación del correo electrónico (utiliza regex)
        if (!correoElectronico.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.");
            return;
        }

        // Validación de la cédula (try/catch para eso)
        try {
            Integer.parseInt(cedulaStr);
            
            logica.registrarJugador(nombreCompleto, correoElectronico, cedulaStr);
            JOptionPane.showMessageDialog(this, "Registrando jugador: " + nombreCompleto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido.");
        }
    }
}