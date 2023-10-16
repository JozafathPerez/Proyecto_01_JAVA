package interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.Juego;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CrearCartonesPanel extends JPanel {
	private JTextField cantidadCartonesField;
	private JButton crearCartonesButton;
	private JButton regresarMenuButton;
	private Juego logica;

	public CrearCartonesPanel(Juego pLogica) {
		// asignar la clase logica de la interfaz
		logica = pLogica;

		// Configura el diseño del panel
		setLayout(null);

		// Etiqueta para indicar la entrada
		JLabel cantidadLabel = new JLabel("Cantidad de cartones a crear (1-500):");
		cantidadLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(cantidadLabel);
		cantidadLabel.setBounds(100, 240, 600, 30);

		// Campo de texto para la cantidad de cartones
		cantidadCartonesField = new JTextField();
		add(cantidadCartonesField);
		cantidadCartonesField.setBounds(350, 290, 100, 30);

		// Botón para crear los cartones
		crearCartonesButton = new JButton("Crear Cartones");
		crearCartonesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accionCrearCartones();
			}
		});
		add(crearCartonesButton);
		crearCartonesButton.setBounds(250, 350, 300, 60);

		// Botón para regresar al menú principal
		regresarMenuButton = new JButton("◀ REGRESAR");
		regresarMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui.cambiarEscena("menu"); // Método para volver al menú principal
			}
		});
		add(regresarMenuButton);
		regresarMenuButton.setBounds(10, 10, 110, 25);
	}

	private void accionCrearCartones() {
		String cantidadCartonesStr = cantidadCartonesField.getText();
		try {
			int cantidadCartones = Integer.parseInt(cantidadCartonesStr);

			if (cantidadCartones >= 1 && cantidadCartones <= 500) {
				if (cantidadCartones + logica.getCartonesCreados().size() > 500) {
					JOptionPane.showMessageDialog(this, "No queda esa cantidad de cartones disponibles para crear");
					return;
				}
				// aqui se llama la funcion para crearlos
				logica.crearCartones(cantidadCartones);
				JOptionPane.showMessageDialog(this, "Creando " + cantidadCartones + " cartones de Bingo.");
				setGui();
			} else {
				JOptionPane.showMessageDialog(this, "La cantidad debe estar entre 1 y 500.");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Ingresa un número válido.");
		}
	}

	public void setGui() {
		cantidadCartonesField.setText("");
	}
}
