package interfazGrafica;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    dataset.addValue(1.0, "Mazda", "Jaime");//ventas de Mazda de Jaime
    dataset.addValue(4.0, "Mazda", "Valeria");//ventas de Mazda de Valeria
    dataset.addValue(3.0, "Mazda", "Sebastian");//ventas de Mazda de Sebastian
    dataset.addValue(5.0, "Mazda", "Valentina");//ventas de Mazda de Valentina
    dataset.addValue(5.0, "Mazda", "Carlos");//ventas de Mazda de Carlos
    
    chart = ChartFactory.createStackedBarChart(
            "Top 10 numeros cantados", 
            "Numero", 
            "Apariciones", 
            dataset,
            PlotOrientation.VERTICAL, 
            false, true, false);
}
}