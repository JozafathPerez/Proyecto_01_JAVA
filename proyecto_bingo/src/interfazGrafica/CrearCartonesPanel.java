package interfazGrafica;

import java.awt.Cursor;
import java.awt.Font;
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
    cantidadLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		cantidadLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(cantidadLabel);
		cantidadLabel.setBounds(100, 230, 600, 50);

		// Campo de texto para la cantidad de cartones
		cantidadCartonesField = new JTextField();
    cantidadCartonesField.setFont(new Font("Arial", Font.PLAIN, 20));
		add(cantidadCartonesField);
		cantidadCartonesField.setBounds(350, 285, 100, 50);

		// Botón para crear los cartones
		crearCartonesButton = new JButton("Crear Cartones");
    crearCartonesButton.setFont(new Font("Arial", Font.PLAIN, 20));
		crearCartonesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accionCrearCartones();
			}
		});
		add(crearCartonesButton);
		crearCartonesButton.setBounds(250, 350, 300, 50);

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
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				logica.crearCartones(cantidadCartones);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				JOptionPane.showMessageDialog(this, "Se han creado " + cantidadCartones + " cartones de Bingo.");
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
