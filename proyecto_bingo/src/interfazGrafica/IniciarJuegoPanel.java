package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa el panel de configuración para iniciar un juego de
 * bingo.
 */
public class IniciarJuegoPanel extends JPanel {
  private JComboBox<String> configuracionJuegoComboBox;
  private JTextField montoPremioField;
  private JButton iniciarJuegoButton;
  private Juego logica;
  private Gui gui;

  /**
   * Constructor de la clase `IniciarJuegoPanel`.
   * 
   * @param pGui    La instancia de la clase `Gui` que maneja la interfaz.
   * @param pLogica La instancia de la clase `Juego` que contiene la lógica.
   */
  public IniciarJuegoPanel(Gui pGui, Juego pLogica) {
    // asignar la clase logica de la interfaz
    logica = pLogica;
    gui = pGui;

    // Configurar el diseño del panel
    setLayout(null);

    // Crear una fuente con el tipo Arial y tamaño 24
    Font fuente = new Font("Arial", Font.PLAIN, 20);

    // Etiqueta y ComboBox para la configuración del juego
    JLabel configuracionLabel = new JLabel("Configuración del juego:");
    configuracionLabel.setBounds(130, 150, 300, 30);
    configuracionLabel.setFont(fuente); // Cambiar el tamaño de fuente
    add(configuracionLabel);
    String[] opcionesConfiguracion = { "Jugar en X", "Cuatro esquinas",
                                       "Cartón lleno", "Jugar en Z" };
    configuracionJuegoComboBox = new JComboBox<>(opcionesConfiguracion);
    configuracionJuegoComboBox.setBounds(410, 150, 300, 30);
    add(configuracionJuegoComboBox);

    // Etiqueta y campo de texto para el monto en dinero del premio
    JLabel montoPremioLabel = new JLabel("Monto en dinero del premio:");
    montoPremioLabel.setBounds(130, 270, 300, 30);
    montoPremioLabel.setFont(fuente); // Cambiar el tamaño de fuente
    add(montoPremioLabel);
    montoPremioField = new JTextField();
    montoPremioField.setBounds(410, 270, 300, 30);
    add(montoPremioField);

    // Botón para iniciar el juego
    iniciarJuegoButton = new JButton("Iniciar Juego");
    iniciarJuegoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        iniciarJuego(); // Método para iniciar el juego
      }
    });
    iniciarJuegoButton.setBounds(300, 340, 200, 40);
    add(iniciarJuegoButton);

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

  /**
   * Método para iniciar un juego de bingo con la configuración seleccionada y el
   * monto del premio.
   * Valida la configuración, el monto del premio y que existan cartones creados y
   * asignados antes
   * de iniciar el juego.
   */
  private void iniciarJuego() {
    String configuracionJuego = (String) configuracionJuegoComboBox.getSelectedItem();
    String montoPremioStr = montoPremioField.getText();

    // Validacion de que existan cartones creado

    if (logica.getCartonesCreados().isEmpty()) {
      JOptionPane.showMessageDialog(this, "No se han creado cartones");
      return;
    }

    // Validacion de que existan cartones en juego

    if (logica.getCartonesEnJuego().isEmpty()) {
      JOptionPane.showMessageDialog(this, "No se han asignado cartones a jugadores");
      return;
    }

    if (montoPremioStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Ingresa el monto en dinero del premio.");
      return;
    }

    try {
      // Validacion del monto (que sea numero)
      double montoPremio = Double.parseDouble(montoPremioStr);

      // Validar que no sea mayor que 0

      if (montoPremio <= 0) {
        JOptionPane.showMessageDialog(this, "El monto del premio debe ser mayor a 0");
        return;
      }

      // inicio de juego
      logica.configurarJuego(configuracionJuego, montoPremio);
      JOptionPane.showMessageDialog(this, "Iniciando juego con configuración: " +
          configuracionJuego + " y premio de $" + montoPremio);

      gui.getJuegoPanel().setGui();
      Gui.cambiarEscena("juego");
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Ingresa un monto de premio válido.");
    }
  }

  /**
   * Reinicia la interfaz gráfica, limpiando el campo del monto del premio.
   */
  public void setGui() {
    montoPremioField.setText("");
  }
}
