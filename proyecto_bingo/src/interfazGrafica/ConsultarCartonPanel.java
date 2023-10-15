package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import logica.Jugador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsultarCartonPanel extends JPanel {
    private JTextField identificadorField;
    private JButton verImagenButton;
    private JLabel imagenLabel;
    private JLabel asignacionLabel;
    private Juego logica;

    public ConsultarCartonPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

        // Configura el diseño del panel
        setLayout(new GridBagLayout());

        // Botón para regresar al menú principal
        JButton regresarMenuButton = new JButton("Regresar al Menú Principal");
        regresarMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("menu"); // Método para volver al menú principal
            }
        });
        GridBagConstraints regresarMenuButtoConstraints = new GridBagConstraints();
        regresarMenuButtoConstraints.gridx = 0;
        regresarMenuButtoConstraints.gridy = 0;
        regresarMenuButtoConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(regresarMenuButton, regresarMenuButtoConstraints);

        // Etiqueta para indicar la entrada
        JLabel nombreImagenLabel = new JLabel("Identificador:");
        nombreImagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints nombreImagenLabelConstraints = new GridBagConstraints();
        nombreImagenLabelConstraints.gridx = 0;
        nombreImagenLabelConstraints.gridy = 1;
        nombreImagenLabelConstraints.anchor = GridBagConstraints.LINE_END;
        add(nombreImagenLabel, nombreImagenLabelConstraints);

        // Campo de texto para el nombre de la imagen
        identificadorField = new JTextField();
        GridBagConstraints identificadorFieldConstraints = new GridBagConstraints();
        identificadorFieldConstraints.gridx = 1;
        identificadorFieldConstraints.gridy = 1;
        identificadorFieldConstraints.anchor = GridBagConstraints.LINE_START;
        add(identificadorField, identificadorFieldConstraints);

        // Botón para ver la imagen
        verImagenButton = new JButton("Ver Imagen");
        verImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultar(); // Método para ver la imagen
            }
        });
        add(verImagenButton);

        // Etiqueta para mostrar la imagen
        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imagenLabel);

        asignacionLabel = new JLabel();
        asignacionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(asignacionLabel);
    }

    // Método para ver la imagen
    private void consultar() {
        String nombreImagen = identificadorField.getText();
        if (nombreImagen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un nombre de imagen válido.");
            return;
        }

        // Cargar la imagen desde la carpeta "/cartones"
        ImageIcon imagen = new ImageIcon("proyecto_bingo/cartones/" + nombreImagen + ".png");
        if (imagen.getImageLoadStatus() == MediaTracker.ERRORED) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen.");
        } else {
            // Mostrar la imagen en la etiqueta
            imagenLabel.setIcon(imagen);
            Jugador jugadorAsignado = logica.obtenerJugadorPorIdentificadorCarton(
                identificadorField.getText());
            if (jugadorAsignado == null) {
              
            }
        }
    }
}
