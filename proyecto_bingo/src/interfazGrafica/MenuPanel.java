package interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.Juego;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
    private Juego logica;

    /**
     * 
     */
    public MenuPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

        JButton botonJugar = new JButton("Iniciar Juego");
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("jugar");
            }
        });
        this.add(botonJugar);

        JButton botonCrearCartones = new JButton("Crear Cartones");
        botonCrearCartones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("crearCartones");
            }
        });
        this.add(botonCrearCartones);

        JButton botonEnviarCartones = new JButton("Enviar Cartones");
        botonEnviarCartones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("enviarCartones");
            }
        });
        this.add(botonEnviarCartones);

        JButton consultarCartonesButton = new JButton("Consultar Cartones");
        consultarCartonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("consultarCartones");
            }
        });
        this.add(consultarCartonesButton);

        JButton botonRegistrarJugador = new JButton("Registrar Jugador");
        botonRegistrarJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("registrarJugador");
            }
        });
        this.add(botonRegistrarJugador);

        JButton botonEstadisticas = new JButton("Estadisticas");
        botonEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.cambiarEscena("estadisticas");
            }
        });
        this.add(botonEstadisticas);        
    }
}
