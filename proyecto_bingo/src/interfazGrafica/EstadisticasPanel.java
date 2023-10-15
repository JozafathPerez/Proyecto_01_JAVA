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

public class EstadisticasPanel extends JPanel{
  //Varas raras
  private Juego logica;
  JFreeChart chart;//declaramos un objeto de la clase JFreeChart para construir el grafico

  public EstadisticasPanel(Juego pLogica) {
    logica = pLogica;

    // Configura el diseño del panel
    setLayout(new GridLayout(4, 1));

    crearGrafico();//metodo para cargar los datos y crear el grafico

    ChartPanel panel = new ChartPanel(chart, false);//ChartPanel es una clase del paquete JFreeChart
    //es igual que JPanel de swing. Sobre el ChartPanel se crea el grafico
    panel.setBounds(10, 20, 760, 520);//damos ubicacion y tamano al panel
    add(panel); //anadimos el panel al JFrame

    setVisible(true); //mostrar el JFrame  

    crearGraficoPie();

    ChartPanel panel2 = new ChartPanel(chart, false);
    panel2.setBounds(10, 40, 760, 520);
    add(panel2);

    setVisible(true);



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

  public void crearGrafico() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    // Creamos el Array de numeros cantados
    List<String> numerosCantados = logica.cargarNumerosCantados();

    List<Entry<String, Integer>> topNList = generarTopDiez(numerosCantados);

    for (Entry<String, Integer> entry : topNList) {
        String str = entry.getKey();
        int frequency = entry.getValue();
        // System.out.println("Cadena: " + str + ", Frecuencia: " + frequency);
        dataset.addValue(frequency, "Mazda", str);//ventas de Mazda de Jaime
    }

    chart = ChartFactory.createStackedBarChart(
            "Top 10 numeros cantados", 
            "Numero", 
            "Apariciones", 
            dataset,
            PlotOrientation.VERTICAL, 
            false, true, false);

  }

  public static void crearGraficoPie() {
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


  public static List<Entry<String, Integer>> generarTopDiez(List<String> strings) {
    int n = 10; // Obtener las 10 cadenas más repetidas

    Map<String, Integer> frequencyMap = new HashMap<>();
    PriorityQueue<Map.Entry<String, Integer>> topN = new PriorityQueue<>(
        (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue())
    );

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