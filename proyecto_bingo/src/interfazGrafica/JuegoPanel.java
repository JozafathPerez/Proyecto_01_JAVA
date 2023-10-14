package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JuegoPanel extends JPanel {
    private JLabel configuracionLabel;
    private JLabel cartonesLabel;
    private JLabel montoPremioLabel;
    private JLabel totalJugadoresLabel;
    private JLabel numerosCantadosLabel;
    private JTextArea numerosCantadosArea;
    private JButton cantarNumeroButton;

    public JuegoPanel() {
        // Configura el diseño del panel
        setLayout(new GridLayout(6, 1));

        // Etiqueta para mostrar la configuración del juego
        configuracionLabel = new JLabel("Configuración del juego: ");
        add(configuracionLabel);

        // Etiqueta para mostrar los cartones actuales
        cartonesLabel = new JLabel("Cantidad de cartones: ");
        add(cartonesLabel);

        // Etiqueta para mostrar el monto del premio
        montoPremioLabel = new JLabel("Monto del premio: ");
        add(montoPremioLabel);

        // Etiqueta para mostrar el total de jugadores
        totalJugadoresLabel = new JLabel("Total de jugadores: ");
        add(totalJugadoresLabel);

        // Etiqueta para mostrar los números cantados
        numerosCantadosLabel = new JLabel("Números cantados: \n");
        add(numerosCantadosLabel);

        numerosCantadosArea = new JTextArea();
        numerosCantadosArea.setEditable(false);
        numerosCantadosArea.setWrapStyleWord(true);
        numerosCantadosArea.setLineWrap(true);
        add(numerosCantadosArea);

        // Botón para cantar un número aleatorio
        cantarNumeroButton = new JButton("Cantar Número Aleatorio");
        cantarNumeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cantarNumeroAleatorio(); // Método para cantar un número aleatorio
            }
        });
        add(cantarNumeroButton);
    }

    // Método para cantar un número aleatorio
    private void cantarNumeroAleatorio() {
        Random random = new Random();
        int numeroCantado = random.nextInt(75) + 1; // random temporal
        
        // Agrega el número cantado al espacio de números cantados
        numerosCantadosArea.append(Integer.toString(numeroCantado) + " ");
    }

    // Métodos para actualizar la información del juego
    public void actualizarConfiguracion(String configuracion) {
        configuracionLabel.setText("Configuración del juego: " + configuracion);
    }

    public void actualizarCartones(String cartones) {
        cartonesLabel.setText("Cartones actuales: " + cartones);
    }

    public void actualizarMontoPremio(double montoPremio) {
        montoPremioLabel.setText("Monto del premio: ₵" + montoPremio);
    }

    public void actualizarTotalJugadores(int totalJugadores) {
        totalJugadoresLabel.setText("Total de jugadores: " + totalJugadores);
    }
}
