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
    regresarMenuButtoConstraints.insets = new Insets(10, 10, 10, 10);
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
    identificadorField = new JTextField(8);
    GridBagConstraints identificadorFieldConstraints = new GridBagConstraints();
    identificadorFieldConstraints.gridx = 1;
    identificadorFieldConstraints.gridy = 1;
    identificadorFieldConstraints.anchor = GridBagConstraints.LINE_START;
    identificadorFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
    add(identificadorField, identificadorFieldConstraints);

    // Botón para ver la imagen
    verImagenButton = new JButton("Ver Imagen");    
    verImagenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        consultar(); // Método para ver la imagen
      }
    });
    GridBagConstraints verImagenButtonConstraints = new GridBagConstraints();
    verImagenButtonConstraints.gridx = 0;
    verImagenButtonConstraints.gridy = 2;
    verImagenButtonConstraints.gridwidth = 2;
    add(verImagenButton, verImagenButtonConstraints);

    // Etiqueta para mostrar la imagen
    imagenLabel = new JLabel();
    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
    GridBagConstraints imagenLabelConstraints = new GridBagConstraints();
    imagenLabelConstraints.gridx = 0;
    imagenLabelConstraints.gridy = 3;
    imagenLabelConstraints.gridwidth = 2;
    imagenLabelConstraints.fill = GridBagConstraints.BOTH;
    add(imagenLabel, imagenLabelConstraints);

    asignacionLabel = new JLabel();
    asignacionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    GridBagConstraints asignacionLabelConstraint = new GridBagConstraints();
    asignacionLabelConstraint.gridx = 0;
    asignacionLabelConstraint.gridy = 4;
    asignacionLabelConstraint.gridwidth = 2;
    asignacionLabelConstraint.fill = GridBagConstraints.BOTH;
    add(asignacionLabel, asignacionLabelConstraint);
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
