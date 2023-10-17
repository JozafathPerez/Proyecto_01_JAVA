package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.kennycason.kumo.*;
import com.kennycason.kumo.bg.*;
import com.kennycason.kumo.font.scale.*;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import logica.CuentaCorreo;

/**
 * Esta clase representa un panel que muestra un WordCloud generado a partir de
 * comentarios de mensajes de correo.
 * Los comentarios se obtienen de una cuenta de correo y se generan en forma de
 * nube de palabras.
 */
public class WordCloudPanel extends JPanel {
  private Juego logica;
  private JButton regresarMenuButton;

  /**
   * Constructor de la clase WordCloudPanel.
   *
   * @param pLogica La instancia de la clase Juego que se utiliza para obtener los
   *                datos del juego.
   */
  public WordCloudPanel(Juego pLogica) {
    this.logica = pLogica;

    // Configurar el diseño del panel a null para posicionamiento manual
    setLayout(null);

    CuentaCorreo cuenta = new CuentaCorreo("bingosocialmold@gmail.com");
    List<String> comentarios = cuenta.obtenerComentariosDeMensajes();
    List<String> mensajesAnteriores = cargarMensajesAnteriores("mensajes.txt");
    comentarios.addAll(mensajesAnteriores);

    generateWordCloud(comentarios);

    sobrescribirComentarios("mensajes.txt", comentarios);

    // Botón para regresar al menú principal
    regresarMenuButton = new JButton("◀ REGRESAR");
    regresarMenuButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Gui.cambiarEscena("menu"); // Método para volver al menú principal
      }
    });
    add(regresarMenuButton);
    regresarMenuButton.setBounds(10, 10, 110, 25);
  }

  /**
   * Carga mensajes anteriores desde un archivo de texto.
   *
   * @param archivo El nombre del archivo de texto.
   * @return Una lista de mensajes anteriores.
   */
  private static List<String> cargarMensajesAnteriores(String archivo) {
    List<String> mensajesAnteriores = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(archivo));
      String linea;
      while ((linea = reader.readLine()) != null) {
        mensajesAnteriores.add(linea);
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return mensajesAnteriores;
  }

  /**
   * Sobrescribe comentarios en un archivo de texto.
   *
   * @param archivo     El nombre del archivo de texto.
   * @param comentarios La lista de comentarios a sobrescribir.
   */
  private static void sobrescribirComentarios(String pArchivo, List<String> pComentarios) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(pArchivo));
      for (String comentario : pComentarios) {
        writer.write(comentario);
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Genera un WordCloud a partir de los comentarios proporcionados y lo muestra
   * en el panel.
   *
   * @param comentarios La lista de comentarios para generar el WordCloud.
   */
  private void generateWordCloud(List<String> comentarios) {
    // Ejemplo del word cloud circular pero con mas palabras de frecuencia
    final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
    frequencyAnalyzer.setWordFrequenciesToReturn(500);
    frequencyAnalyzer.setMinWordLength(4);
    final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(comentarios);
    final Dimension dimension = new Dimension(600, 600);
    final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
    wordCloud.setPadding(2);
    wordCloud.setBackground(new CircleBackground(300));
    // colors followed by and steps between
    wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
    wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
    wordCloud.build(wordFrequencies);
    wordCloud.writeToFile("kumo-core/output/wordcloud_gradient_redbluegreen.png");

    // Obtener el word cloud y hacerlo imagen
    final BufferedImage wordCloudImage = wordCloud.getBufferedImage();

    // Establecer posición y tamaño del JLabel que muestra el WordCloud
    JLabel wordCloudLabel = new JLabel(new ImageIcon(wordCloudImage));
    wordCloudLabel.setBounds(100, 50, dimension.width, dimension.height);

    add(wordCloudLabel);
  }
}
