package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IniciarJuegoPanel extends JPanel {
    private JComboBox<String> configuracionJuegoComboBox;
    private JTextField montoPremioField;
    private JButton iniciarJuegoButton;
    private Juego logica;
    private Gui gui;

    public IniciarJuegoPanel(Gui pGui, Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;
        gui = pGui;

        // Configura el diseño del panel
        setLayout(new GridLayout(5, 1));

        // Etiqueta y ComboBox para la configuración del juego
        JLabel configuracionLabel = new JLabel("Configuración del juego:");
        add(configuracionLabel);
        String[] opcionesConfiguracion = {"Jugar en X", "Cuatro esquinas", "Carton lleno", "Jugar en Z"};
        configuracionJuegoComboBox = new JComboBox<>(opcionesConfiguracion);
        add(configuracionJuegoComboBox);

        // Etiqueta y campo de texto para el monto en dinero del premio
        JLabel montoPremioLabel = new JLabel("Monto en dinero del premio:");
        add(montoPremioLabel);
        montoPremioField = new JTextField();
        add(montoPremioField);

        // Botón para iniciar el juego
        iniciarJuegoButton = new JButton("Iniciar Juego");
        iniciarJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego(); // Método para iniciar el juego
            }
        });
        add(iniciarJuegoButton);
    }

    // Método para iniciar el juego
    private void iniciarJuego() {
        String configuracionJuego = (String) configuracionJuegoComboBox.getSelectedItem();
        String montoPremioStr = montoPremioField.getText();

        if (montoPremioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el monto en dinero del premio.");
            return;
        }

        try {
            // Validacion del monto (que sea numero)
            double montoPremio = Double.parseDouble(montoPremioStr);
            
            // inicio de juego
            logica.configurarJuego(configuracionJuego, montoPremio);
            JOptionPane.showMessageDialog(this, "Iniciando juego con configuración: " + configuracionJuego + " y premio de $" + montoPremio);
            
            gui.getJuegoPanel().setGui();
            Gui.cambiarEscena("juego");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un monto de premio válido.");
        }
    }

    public void setGui() {
        montoPremioField.setText("");
    }
}
