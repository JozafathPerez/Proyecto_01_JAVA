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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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

        // Obtener comentarios (reemplaza con tus comentarios reales)
        List<String> comentarios = obtenerComentarios();

        // Generar WordCloud
        generateWordCloud(comentarios);
    }

    private List<String> obtenerComentarios() {
        // Reemplaza este ejemplo con tu propia lógica para obtener los comentarios reales
        List<String> comentarios = new ArrayList<>();
        comentarios.add("PENE");
        comentarios.add("PENE");
        comentarios.add("PENE");
        comentarios.add("PENE");
        comentarios.add("Excelente juego de bingo");
        comentarios.add("Me divertí mucho jugando");
        comentarios.add("Recomiendo este juego a mis amigos");
        comentarios.add("Bingo social es genial");
        return comentarios;
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
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("kumo-core/output/datarank_wordcloud_circle_sqrt_font.png");

        // Obtener la imagen del WordCloud
        final BufferedImage wordCloudImage = wordCloud.getBufferedImage();

        // Mostrar el WordCloud en un JLabel
        final JLabel wordCloudLabel = new JLabel(new ImageIcon(wordCloudImage));
            add(wordCloudLabel);
    }
}
