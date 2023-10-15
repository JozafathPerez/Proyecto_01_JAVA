package interfazGrafica;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.Juego;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CrearCartonesPanel extends JPanel{
    private JTextField cantidadCartonesField;
    private JButton crearCartonesButton;
    private JButton regresarMenuButton;
    private Juego logica;

    public CrearCartonesPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

        // Configura el diseño del panel
        setLayout(new GridLayout(4, 1));

        // Etiqueta para indicar la entrada
        JLabel cantidadLabel = new JLabel("Cantidad de cartones a crear (1-500):");
        cantidadLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(cantidadLabel);

        // Campo de texto para la cantidad de cartones
        cantidadCartonesField = new JTextField();
        add(cantidadCartonesField);

        // Botón para crear los cartones
        crearCartonesButton = new JButton("Crear Cartones");
        crearCartonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionCrearCartones();
            }
        });
        add(crearCartonesButton);

        // Botón para regresar al menú principal
        regresarMenuButton = new JButton("Regresar al Menú Principal");
        regresarMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("menu"); // Método para volver al menú principal
            }
        });
        add(regresarMenuButton);
    }

    private void accionCrearCartones() {
        String cantidadCartonesStr = cantidadCartonesField.getText();
        try {
            int cantidadCartones = Integer.parseInt(cantidadCartonesStr);
            if (cantidadCartones >= 1 && cantidadCartones <= 500) {
                
                // aqui se llama la funcion para crearlos
                logica.crearCartones(cantidadCartones);
                JOptionPane.showMessageDialog(this, "Creando " + cantidadCartones + " cartones de Bingo.");
            } else {
                JOptionPane.showMessageDialog(this, "La cantidad debe estar entre 1 y 500.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.");
        }
    }
}
