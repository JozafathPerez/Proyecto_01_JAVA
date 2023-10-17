package interfazGrafica;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Clase que representa el panel del menú principal de la aplicación.
 */
public class MenuPanel extends JPanel {

  /**
   * Constructor de la clase `MenuPanel`.
   * 
   * @param gui La instancia de la clase `Gui` que gestiona la interfaz gráfica.
   */
  public MenuPanel(Gui gui) {
    // Configurar el diseño como nulo para posicionar manualmente los botones
    setLayout(null);

    int buttonWidth = 300; // Ancho de los botones
    int buttonHeight = 80; // Alto de los botones

    int x = 250; // Centrar horizontalmente

    // Crear botones y agregar acciones
    JButton botonJugar = new JButton("Iniciar Juego");
    botonJugar.setBounds(x, 20, buttonWidth, buttonHeight);
    botonJugar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        gui.getJugarPanel().setGui();
        Gui.cambiarEscena("jugar");
      }
    });
    add(botonJugar);

    JButton botonCrearCartones = new JButton("Crear Cartones");
    botonCrearCartones.setBounds(x, 110, buttonWidth, buttonHeight);
    botonCrearCartones.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Gui.cambiarEscena("crearCartones");
      }
    });
    add(botonCrearCartones);

    JButton botonEnviarCartones = new JButton("Enviar Cartones");
    botonEnviarCartones.setBounds(x, 200, buttonWidth, buttonHeight);
    botonEnviarCartones.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Gui.cambiarEscena("enviarCartones");
      }
    });
    add(botonEnviarCartones);

    JButton consultarCartonesButton = new JButton("Consultar Cartones");
    consultarCartonesButton.setBounds(x, 290, buttonWidth, buttonHeight);
    consultarCartonesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Gui.cambiarEscena("consultarCartones");
      }
    });
    add(consultarCartonesButton);

    JButton botonRegistrarJugador = new JButton("Registrar Jugador");
    botonRegistrarJugador.setBounds(x, 380, buttonWidth, buttonHeight);
    botonRegistrarJugador.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Gui.cambiarEscena("registrarJugador");
      }
    });
    add(botonRegistrarJugador);

    JButton botonEstadisticas = new JButton("Estadísticas");
    botonEstadisticas.setBounds(x, 470, buttonWidth, buttonHeight);
    botonEstadisticas.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        gui.getEstadisticasPanel().setGui();
        Gui.cambiarEscena("estadisticas");
      }
    });
    add(botonEstadisticas);

    JButton botonWordCloud = new JButton("WordCloud");
    botonWordCloud.setBounds(x, 560, buttonWidth, buttonHeight);
    botonWordCloud.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Gui.cambiarEscena("wordCloud");
        gui.getWordCloudPanel().setCursor(new Cursor(Cursor.WAIT_CURSOR));
        gui.getWordCloudPanel().generateWordCloud();
        gui.getWordCloudPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    });
    add(botonWordCloud);
  }
}
