package interfazGrafica;

import javax.swing.*;
import java.awt.*;

public class Gui{
    private static JFrame ventana;
    private static JPanel cards;
    private static CardLayout cardLayout;
    
    public Gui() {
        ventana = new JFrame("Menú de Bingo");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 500);
        
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        // Panel para el menú principal
        MenuPanel menuPanel = new MenuPanel();
        registraEscena(menuPanel, "menu");
        
        // Panel para jugar Bingo
        JPanel jugarPanel = new JPanel();
        jugarPanel.add(new JLabel("¡Bienvenido al juego de Bingo!"));
        jugarPanel.add(new JButton("Iniciar Juego"));
        registraEscena(jugarPanel, "jugar");
        
        // Panel para crear cartones
        GenerarCartonPanel crearCartonesPanel = new GenerarCartonPanel();
        registraEscena(crearCartonesPanel, "crearCartones");
        
        // Panel para enviar cartones
        EnviarCartonesPanel enviarCartonesPanel = new EnviarCartonesPanel();
        registraEscena(enviarCartonesPanel, "enviarCartones");
        
        // Panel para consultar cartones
        ConsultarCartonPanel consultarCartonPanel = new ConsultarCartonPanel();
        registraEscena(consultarCartonPanel, "consultarCartones");

        // Panel para configurar premios
        RegistrarJugadorPanel registrarJugadorPanel = new RegistrarJugadorPanel();
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
