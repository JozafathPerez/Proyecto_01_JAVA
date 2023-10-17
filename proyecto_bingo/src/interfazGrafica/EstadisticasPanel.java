package interfazGrafica;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

/**
 * Esta clase representa el panel de estadísticas del juego.
 * Muestra gráficos que representan datos relacionados con el juego.
 */
public class EstadisticasPanel extends JPanel {
  private Juego logica;
  JFreeChart chart;// declaramos un objeto de la clase JFreeChart para construir el grafico
  private ChartPanel panel;
  private ChartPanel panel2;
  private JButton regresarMenuButton;

  /**
   * Constructor de la clase EstadisticasPanel.
   *
   * @param pLogica La instancia de la clase Juego que se utiliza para obtener los
   *                datos de juego.
   */
  public EstadisticasPanel(Juego pLogica) {
    logica = pLogica;

    // Configura el diseño del panel
    setLayout(null);

    // Crear una fuente con el tipo Arial y tamaño 24
    Font fuente = new Font("Arial", Font.PLAIN, 20);

    JLabel EstadisticasLabel = new JLabel("Estadisticas de juego");
    EstadisticasLabel.setBounds(300, 10, 300, 30);
    EstadisticasLabel.setFont(fuente); // Cambiar el tamaño de fuente
    add(EstadisticasLabel);

    crearGrafico();// metodo para cargar los datos y crear el grafico

    panel = new ChartPanel(chart, false);// ChartPanel es una clase del paquete JFreeChart
    panel.setBounds(100, 80, 560, 270);// damos ubicacion y tamano al panel
    add(panel); // anadimos el panel al JFrame

    crearGraficoPie();

    panel2 = new ChartPanel(chart, false);
    panel2.setBounds(100, 360, 560, 270);
    add(panel2);

    setVisible(true);

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
   * Configura y actualiza la interfaz de usuario con los datos actuales.
   */
  public void setGui() {
    crearGrafico();
    panel.setChart(chart);
    crearGraficoPie();
    panel2.setChart(chart);
  }

  /**
   * Crea y actualiza el gráfico de barras que muestra los 10 números más
   * cantados.
   */
  public void crearGrafico() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    // Creamos el Array de numeros cantados
    List<String> numerosCantados = logica.cargarNumerosCantados();

    List<Entry<String, Integer>> topNList = generarTopDiez(numerosCantados);

    for (Entry<String, Integer> entry : topNList) {
      String str = entry.getKey();
      int frequency = entry.getValue();
      dataset.addValue(frequency, "Mazda", str);// ventas de Mazda de Jaime
    }

    chart = ChartFactory.createStackedBarChart(
        "Top 10 numeros cantados",
        "Numero",
        "Apariciones",
        dataset,
        PlotOrientation.VERTICAL,
        false, true, false);
  }

  /**
   * Crea y actualiza el gráfico de pastel que muestra los modos de juego más
   * utilizados.
   */
  public void crearGraficoPie() {
    DefaultPieDataset data = new DefaultPieDataset();

    // Creamos el Array nombres
    List<String> modosDEJuego = logica.cargarModosPartida();

    List<Entry<String, Integer>> topNList = generarTopDiez(modosDEJuego);

    // Añadir los datos al grafico
    for (Entry<String, Integer> entry : topNList) {
      String str = entry.getKey();
      int frequency = entry.getValue();
      // System.out.println("Cadena: " + str + ", Frecuencia: " + frequency);
      data.setValue(str, frequency);
    }

    chart = ChartFactory.createPieChart(
        "Modos de juego más utilizadas",
        data,
        true,
        true,
        false);
  }

    /**
   * Genera la lista de las 10 entradas con mayor frecuencia a partir de una lista de cadenas.
   *
   * @param strings La lista de cadenas de entrada.
   * @return Una lista de las 10 entradas con mayor frecuencia.
   */
  public static List<Entry<String, Integer>> generarTopDiez(List<String> strings) {
    int n = 10; // Obtener las 10 cadenas más repetidas

    Map<String, Integer> frequencyMap = new HashMap<>();
    PriorityQueue<Map.Entry<String, Integer>> topN = new PriorityQueue<>(
        (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));

    for (String str : strings) {
      frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1);
    }

    for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
      topN.offer(entry);
      if (topN.size() > n) {
        topN.poll(); // Elimina la cadena menos frecuente si la cola tiene más de 10 elementos.
      }
    }

    List<Entry<String, Integer>> topNList = new ArrayList<>(topN);
    return topNList;
  }
}