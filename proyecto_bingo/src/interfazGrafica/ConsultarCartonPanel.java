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
    setLayout(null);

    // Botón para regresar al menú principal
    JButton regresarMenuButton = new JButton("◀ REGRESAR");
    regresarMenuButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Gui.cambiarEscena("menu"); // Método para volver al menú principal
      }
    });
    add(regresarMenuButton);
    regresarMenuButton.setBounds(10, 10, 110, 25);

    // Etiqueta para indicar la entrada
    JLabel nombreImagenLabel = new JLabel("Identificador:");
    nombreImagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(nombreImagenLabel);
    nombreImagenLabel.setBounds(200, 40, 200, 30);

    // Campo de texto para el nombre de la imagen
    identificadorField = new JTextField(8);
    add(identificadorField);
    identificadorField.setBounds(400, 40, 200, 30);

    // Botón para ver la imagen
    verImagenButton = new JButton("Ver Imagen");
    verImagenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        consultar(); // Método para ver la imagen
      }
    });
    add(verImagenButton);
    verImagenButton.setBounds(300, 80, 200, 30);

    // Etiqueta para mostrar la imagen
    imagenLabel = new JLabel();
    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(imagenLabel);
    imagenLabel.setBounds(200, 120, 400, 500);

    asignacionLabel = new JLabel();
    asignacionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(asignacionLabel);
    asignacionLabel.setBounds(200, 630, 400, 25);
  }

  // Método para ver la imagen
  /**
   * 
   */
  private void consultar() {
    String nombreImagen = identificadorField.getText();
    if (nombreImagen.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Ingresa un nombre de imagen válido.");
      return;
    }

    // Cargar la imagen desde la carpeta "/cartones"
    ImageIcon imagenIcon = new ImageIcon("proyecto_bingo/cartones/" + nombreImagen + ".png");
    Image imagen = imagenIcon.getImage();
    Image imagenMod = imagen.getScaledInstance(400, -1, Image.SCALE_SMOOTH);
    imagenIcon = new ImageIcon(imagenMod);
    if (imagenIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
      JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen.");
      return;
    } else {
      // Mostrar la imagen en la etiqueta
      imagenLabel.setIcon(imagenIcon);
      Jugador jugadorAsignado = logica.obtenerJugadorPorIdentificadorCarton(
          identificadorField.getText());
      if (jugadorAsignado == null) {
        asignacionLabel.setText("No está asignado a un jugador");
      } else {
        asignacionLabel.setText("Pertenece al jugador " + jugadorAsignado.getNombre() +
            ", cedula " + jugadorAsignado.getCedula());
      }
    }
  }
}
