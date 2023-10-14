package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsultarCartonPanel extends JPanel {
    private JTextField nombreImagenField;
    private JButton verImagenButton;
    private JLabel imagenLabel;
    private Juego logica;

    public ConsultarCartonPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

        // Configura el diseño del panel
        setLayout(new GridLayout(4, 1));

        // Etiqueta para indicar la entrada
        JLabel nombreImagenLabel = new JLabel("Nombre de la imagen (sin la extensión .png):");
        nombreImagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(nombreImagenLabel);

        // Campo de texto para el nombre de la imagen
        nombreImagenField = new JTextField();
        add(nombreImagenField);

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

    // Método para ver la imagen
    private void consultar() {
        String nombreImagen = nombreImagenField.getText();
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
        }
    }
}
