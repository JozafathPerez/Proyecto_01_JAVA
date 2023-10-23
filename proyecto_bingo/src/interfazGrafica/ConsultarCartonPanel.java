package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import logica.Jugador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La clase `ConsultarCartonPanel` representa un panel de la interfaz gráfica
 * para consultar un cartón de bingo.
 */
public class ConsultarCartonPanel extends JPanel {
  private JTextField identificadorField;
  private JButton verImagenButton;
  private JLabel imagenLabel;
  private JLabel asignacionLabel;
  private Juego logica;

  /**
   * Constructor de la clase `ConsultarCartonPanel`.
   * 
   * @param pLogica La instancia de la clase `Juego` que contiene la lógica
   */
  public ConsultarCartonPanel(Juego pLogica) {
    // Asignar la clase lógica de la interfaz
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
    nombreImagenLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    add(nombreImagenLabel);
    nombreImagenLabel.setBounds(200, 40, 200, 50);

    // Campo de texto para el nombre de la imagen
    identificadorField = new JTextField(8);
    identificadorField.setFont(new Font("Arial", Font.PLAIN, 20));
    add(identificadorField);
    identificadorField.setBounds(400, 40, 200, 50);

    // Botón para ver la imagen
    verImagenButton = new JButton("Ver Imagen");
    verImagenButton.setFont(new Font("Arial", Font.PLAIN, 20));
    verImagenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        consultar(); // Método para ver la imagen
      }
    });
    add(verImagenButton);
    verImagenButton.setBounds(300, 100, 200, 30);

    // Etiqueta para mostrar la imagen
    imagenLabel = new JLabel();
    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(imagenLabel);
    imagenLabel.setBounds(200, 130, 400, 500);

    // Etiqueta para mostrar la asignación del cartón
    asignacionLabel = new JLabel();
    asignacionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    asignacionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(asignacionLabel);
    asignacionLabel.setBounds(150, 620, 500, 30);
  }

  /**
   * Consulta y muestra un cartón de bingo basado en el identificador ingresado.
   */
  private void consultar() {
    String nombreImagen = identificadorField.getText();
    nombreImagen.toUpperCase();
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