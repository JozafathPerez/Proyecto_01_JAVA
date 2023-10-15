package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import logica.Juego;

public class Gui{
    private static JFrame ventana;
    private static JPanel cards;
    private static CardLayout cardLayout;
    private MenuPanel menuPanel;
    private IniciarJuegoPanel jugarPanel;
    private JuegoPanel juegoPanel;
    private CrearCartonesPanel crearCartonesPanel;
    private EnviarCartonesPanel enviarCartonesPanel;
    private ConsultarCartonPanel consultarCartonPanel;
    private RegistrarJugadorPanel registrarJugadorPanel;
    private WordCloudPanel wordCloudPanel;

    public Gui(Juego pLogica) {
        ventana = new JFrame("Menú de Bingo");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 500);
        
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        // Panel para el menú principal
        menuPanel = new MenuPanel(pLogica);
        registraEscena(menuPanel, "menu");
        
        // Panel para jugar Bingo
        jugarPanel = new IniciarJuegoPanel(pLogica);
        registraEscena(jugarPanel, "jugar");

        juegoPanel = new JuegoPanel(pLogica);
        registraEscena(juegoPanel, "juego");
        
        // Panel para crear cartones
        crearCartonesPanel = new CrearCartonesPanel(pLogica);
        registraEscena(crearCartonesPanel, "crearCartones");
        
        // Panel para enviar cartones
        enviarCartonesPanel = new EnviarCartonesPanel(pLogica);
        registraEscena(enviarCartonesPanel, "enviarCartones");
        
        // Panel para consultar cartones
        consultarCartonPanel = new ConsultarCartonPanel(pLogica);
        registraEscena(consultarCartonPanel, "consultarCartones");

        // Panel para configurar premios
        registrarJugadorPanel = new RegistrarJugadorPanel(pLogica);
        registraEscena(registrarJugadorPanel, "registrarJugador");

        // Panel para las estadisticas
        JPanel estadisticasPanel = new JPanel();
        estadisticasPanel.add(new JLabel("En proceso..."));
        registraEscena(estadisticasPanel, "estadisticas");

        // Panel para configurar premios
        wordCloudPanel = new WordCloudPanel(pLogica);
        registraEscena(wordCloudPanel, "wordCloud");

        ventana.add(cards);
        cardLayout.show(cards, "menu");
        ventana.setVisible(true);
    }

    public static void registraEscena(Component comp, Object constraints) {
        cards.add(comp, constraints);
    }

    public static void cambiarEscena(String nombre) {
        cardLayout.show(cards, nombre);
    }
}
