package interfazGrafica;

import javax.swing.*;
import java.awt.*;

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
    
    public Gui() {
        ventana = new JFrame("Menú de Bingo");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 500);
        
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        // Panel para el menú principal
        menuPanel = new MenuPanel();
        registraEscena(menuPanel, "menu");
        
        // Panel para jugar Bingo
        jugarPanel = new IniciarJuegoPanel();
        registraEscena(jugarPanel, "jugar");

        juegoPanel = new JuegoPanel();
        registraEscena(juegoPanel, "juego");
        
        // Panel para crear cartones
        crearCartonesPanel = new CrearCartonesPanel();
        registraEscena(crearCartonesPanel, "crearCartones");
        
        // Panel para enviar cartones
        enviarCartonesPanel = new EnviarCartonesPanel();
        registraEscena(enviarCartonesPanel, "enviarCartones");
        
        // Panel para consultar cartones
        consultarCartonPanel = new ConsultarCartonPanel();
        registraEscena(consultarCartonPanel, "consultarCartones");

        // Panel para configurar premios
        registrarJugadorPanel = new RegistrarJugadorPanel();
        registraEscena(registrarJugadorPanel, "registrarJugador");

        // Panel para las estadisticas
        JPanel estadisticasPanel = new JPanel();
        estadisticasPanel.add(new JLabel("En proceso..."));
        registraEscena(estadisticasPanel, "estadisticas");

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui();
            }
        });
    }
}
