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
    private EstadisticasPanel estadisticasPanel;

    public Gui(Juego pLogica) {
        ventana = new JFrame("Menú de Bingo");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 500);
        
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        // Panel para el menú principal #1
        menuPanel = new MenuPanel(this, pLogica);
        registraEscena(menuPanel, "menu");
        
        // Panel para configurar el juego #2
        jugarPanel = new IniciarJuegoPanel(this, pLogica);
        registraEscena(jugarPanel, "jugar");

        // Panel para jugar #3
        juegoPanel = new JuegoPanel(pLogica);
        registraEscena(juegoPanel, "juego");
        
        // Panel para crear cartones #4
        crearCartonesPanel = new CrearCartonesPanel(pLogica);
        registraEscena(crearCartonesPanel, "crearCartones");
        
        // Panel para enviar cartones #5
        enviarCartonesPanel = new EnviarCartonesPanel(pLogica);
        registraEscena(enviarCartonesPanel, "enviarCartones");
        
        // Panel para consultar cartones #6
        consultarCartonPanel = new ConsultarCartonPanel(pLogica);
        registraEscena(consultarCartonPanel, "consultarCartones");

        // Panel para configurar premios #7
        registrarJugadorPanel = new RegistrarJugadorPanel(pLogica);
        registraEscena(registrarJugadorPanel, "registrarJugador");

        // Panel para las estadisticas
        estadisticasPanel = new EstadisticasPanel(pLogica);
        // estadisticasPanel.add(new JLabel("En proceso..."));
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

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public IniciarJuegoPanel getJugarPanel() {
        return jugarPanel;
    }

    public JuegoPanel getJuegoPanel() {
        return juegoPanel;
    }

    public CrearCartonesPanel getCrearCartonesPanel() {
        return crearCartonesPanel;
    }

    public EnviarCartonesPanel getEnviarCartonesPanel() {
        return enviarCartonesPanel;
    }

    public ConsultarCartonPanel getConsultarCartonPanel() {
        return consultarCartonPanel;
    }

    public RegistrarJugadorPanel getRegistrarJugadorPanel() {
        return registrarJugadorPanel;
    }

}
