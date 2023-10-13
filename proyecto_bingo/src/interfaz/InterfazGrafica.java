import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazGrafica{
    private JFrame ventana;
    private JPanel cards;
    private CardLayout cardLayout;

    public InterfazGrafica() {
        ventana = new JFrame("Menú de Bingo");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(300, 200);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Panel para el menú principal
        JPanel menuPanel = new JPanel();
        JButton botonJugar = new JButton("Iniciar Juego");
        JButton botonCrearCartones = new JButton("Crear Cartones");
        JButton botonEnviarCartones = new JButton("Enviar Cartones");
        JButton botonRegistrarJugador = new JButton("Registrar Jugador");
        JButton botonEstadisticas = new JButton("Estadisticas");

        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "jugar");
            }
        });

        botonCrearCartones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "crearCartones");
            }
        });

        botonEnviarCartones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "enviarCartones");
            }
        });

        botonRegistrarJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "registrarJugador");
            }
        });

        botonEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "estadisticas");
            }
        });
        
        menuPanel.add(botonJugar);
        menuPanel.add(botonCrearCartones);
        menuPanel.add(botonEnviarCartones);
        menuPanel.add(botonRegistrarJugador);
        menuPanel.add(botonEstadisticas);

        // Panel para jugar Bingo
        JPanel jugarPanel = new JPanel();
        jugarPanel.add(new JLabel("¡Bienvenido al juego de Bingo!"));
        jugarPanel.add(new JButton("Iniciar Juego"));

        // Panel para administrar cartones
        JPanel crearCartonesPanel = new JPanel();
        crearCartonesPanel.add(new JLabel("Crear Cartones de Bingo"));
        crearCartonesPanel.add(new JButton("Crear Nuevo Cartón"));

        // Panel para administrar cartones
        JPanel enviarCartonesPanel = new JPanel();
        enviarCartonesPanel.add(new JLabel("Enviar Cartones de Bingo"));
        enviarCartonesPanel.add(new JButton("Enviar Nuevo Cartón"));

        // Panel para configurar premios
        JPanel registrarJugadorPanel = new JPanel();
        registrarJugadorPanel.add(new JLabel("Regsitrar Jugador"));
        registrarJugadorPanel.add(new JButton("Registrar"));

        // Panel para las estadisticas
        JPanel estadisticasPanel = new JPanel();
        estadisticasPanel.add(new JLabel("En proceso..."));

        cards.add(menuPanel, "menu");
        cards.add(jugarPanel, "jugar");
        cards.add(crearCartonesPanel, "crearCartones");
        cards.add(enviarCartonesPanel, "enviarCartones");
        cards.add(registrarJugadorPanel, "registrarJugador");
        cards.add(estadisticasPanel, "estadisticas");

        ventana.add(cards);
        cardLayout.show(cards, "menu");
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazGrafica();
            }
        });
    }
}
