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
		setLayout(null);

		// Crear una fuente con el tipo Arial y tamaño 24
		Font fuente = new Font("Arial", Font.PLAIN, 20);

		// Etiqueta para mostrar la configuración del juego
		configuracionLabel = new JLabel("Configuración del juego: ");
		configuracionLabel.setBounds(80, 50, 400, 30);
		configuracionLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(configuracionLabel);

		// Etiqueta para mostrar los cartones actuales
		cartonesLabel = new JLabel("Cartones en juego: ");
		cartonesLabel.setBounds(470, 50, 400, 30);
		cartonesLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(cartonesLabel);

		// Etiqueta para mostrar el monto del premio
		montoPremioLabel = new JLabel("Monto del premio: ");
		montoPremioLabel.setBounds(80, 100, 400, 30);
		montoPremioLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(montoPremioLabel);

		// Etiqueta para mostrar el total de jugadores
		totalJugadoresLabel = new JLabel("Total de jugadores: ");
		totalJugadoresLabel.setBounds(470, 100, 400, 30);
		totalJugadoresLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(totalJugadoresLabel);

		// Etiqueta para mostrar los números cantados
		numerosCantadosLabel = new JLabel("Números cantados: \n");
		numerosCantadosLabel.setBounds(80, 170, 400, 30);
		numerosCantadosLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(numerosCantadosLabel);

		numerosCantadosArea = new JTextArea();
		numerosCantadosArea.setEditable(false);
		numerosCantadosArea.setFont(new Font("Arial", Font.PLAIN, 18));
		numerosCantadosArea.setWrapStyleWord(true);
		numerosCantadosArea.setLineWrap(true);
		numerosCantadosArea.setBounds(80, 230, 630, 200);
		add(numerosCantadosArea);

		// Botón para cantar un número aleatorio
		cantarNumeroButton = new JButton("Cantar Número Aleatorio");
		cantarNumeroButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						cantarNumeroAleatorio();
				}
		});
		cantarNumeroButton.setBounds(450, 450, 250, 50);
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
                    //Notificar al ganador
                    logica.notificarGanador(logica.getGanadores());
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
