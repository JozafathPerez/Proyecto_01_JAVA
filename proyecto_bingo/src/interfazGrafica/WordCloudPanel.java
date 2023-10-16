package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.kennycason.kumo.*;
import com.kennycason.kumo.bg.*;
import com.kennycason.kumo.collide.*;
import com.kennycason.kumo.collide.checkers.*;
import com.kennycason.kumo.font.*;
import com.kennycason.kumo.font.scale.*;
import com.kennycason.kumo.image.*;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.padding.*;
import com.kennycason.kumo.palette.*;

import java.util.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import logica.CuentaCorreo;
import javax.mail.Message;

public class WordCloudPanel extends JPanel {
    private Juego logica;
    private JButton regresarMenuButton;

    public WordCloudPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

        // Configura el diseño del panel
        setLayout(new GridLayout(5, 1));

        regresarMenuButton = new JButton("Regresar al Menú Principal");
        regresarMenuButton.addActionListener(e -> {
            Gui.cambiarEscena("menu");
        });
        add(regresarMenuButton);

        // Crear una instancia de la clase CuentaCorreo  bingosocialmold@gmail.com 
        CuentaCorreo cuenta = new CuentaCorreo("bingosocialmold@gmail.com");

        // Obtener los comentarios de los mensajes de correo
        List<String> comentarios = cuenta.obtenerComentariosDeMensajes();
    
        // cargar los mensajes anteriores a comentarios
        List<String> mensajesAnteriores = cargarMensajesAnteriores("mensajes.txt");

        // Agregar los mensajes anteriores a la lista de comentarios
        comentarios.addAll(mensajesAnteriores);

        // Generar WordCloud
        generateWordCloud(comentarios);

        // Sobrescribir los comentarios en el archivo original
        sobrescribirComentarios("mensajes.txt", comentarios);
    }

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

    private static void sobrescribirComentarios(String archivo, List<String> comentarios) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            for (String comentario : comentarios) {
                writer.write(comentario);
                writer.newLine(); // Agregar un salto de línea entre comentarios
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void generateWordCloud(List<String> comentarios) {

        // Crear un analizador de frecuencia de palabras
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300); // Número máximo de palabras a mostrar
        frequencyAnalyzer.setMinWordLength(4); // Longitud mínima de palabras

        //frequencyAnalyzer.addIgnoredWord("juego"); // Palabras ignoradas

        // Analizar los comentarios
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(comentarios);

        // Configurar el WordCloud
        final Dimension dimension = new Dimension(600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("kumo-core/output/wordcloud_rectangle.png");


        // Obtener la imagen del WordCloud
        final BufferedImage wordCloudImage = wordCloud.getBufferedImage();

        // Mostrar el WordCloud en un JLabel
        final JLabel wordCloudLabel = new JLabel(new ImageIcon(wordCloudImage));
            add(wordCloudLabel);
    }
}
