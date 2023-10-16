package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

public class JuegoPanel extends JPanel {
    private JLabel configuracionLabel;
    private JLabel cartonesLabel;
    private JLabel montoPremioLabel;
    private JLabel totalJugadoresLabel;
    private JLabel numerosCantadosLabel;
    private JTextArea numerosCantadosArea;
    private JButton cantarNumeroButton;
    private Juego logica;

    public JuegoPanel(Juego pLogica) {
        // asignar la clase logica de la interfaz
        logica = pLogica;

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
                cantarNumeroAleatorio();
            }
        });
        add(cantarNumeroButton);
    }

    // Método para cantar un número aleatorio
    private void cantarNumeroAleatorio() {
        // cantar el numero
				int numeroCantado = logica.cantarNumero();
        String numerosCantadosString = logica.getNumerosCantados().stream().map(String::valueOf)
            .collect(Collectors.joining(", "));
        numerosCantadosArea.setText(numerosCantadosString);
				// rellenar cartones y verificar ganadores
				logica.marcarCarton(numeroCantado);
				if (logica.verificarCartones()) {
					JOptionPane.showMessageDialog(this, "felicidades: " + logica.getGanadores().stream().collect(Collectors.joining(", ")));
                    logica.guardarPartida(); // Guarda los datos de la partida en el xml
                    //Restablecer valores
                    logica.restablecerValoresDeJuego();
					Gui.cambiarEscena("menu");
				}
    }

    /**
		 * Establece todos los elementos para mostrar la información del nuevo juego
     */
    public void setGui() {
			numerosCantadosArea.setText("");
			configuracionLabel.setText("Configuración del juego: " + logica.getModo());
			cartonesLabel.setText("Cartones en juego: " + logica.getCartonesEnJuego().size());
			montoPremioLabel.setText("Monto del premio: ₵" + logica.getPremio());
			totalJugadoresLabel.setText("Total de jugadores: " + logica.getJugadores().size());
    }
}
