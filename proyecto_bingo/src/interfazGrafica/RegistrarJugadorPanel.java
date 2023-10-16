package interfazGrafica;

import javax.swing.*;
import logica.Juego;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarJugadorPanel extends JPanel {
	private JTextField nombreCompletoField;
	private JTextField correoField;
	private JTextField cedulaField;
	private JButton registrarButton;
	private Juego logica;

	public RegistrarJugadorPanel(Juego pLogica) {
		// asignar la clase logica de la interfaz
		logica = pLogica;

		// Configura el diseño del panel
		setLayout(null);

		// Crear una fuente con el tipo Arial y tamaño 24
		Font fuente = new Font("Arial", Font.PLAIN, 24);

		// Etiqueta y campo de texto para el nombre completo
		JLabel nombreLabel = new JLabel("Nombre completo:");
		nombreLabel.setBounds(100, 200, 300, 30);
		nombreLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(nombreLabel);
		nombreCompletoField = new JTextField();
		nombreCompletoField.setBounds(330, 200, 300, 30);
		add(nombreCompletoField);

		// Etiqueta y campo de texto para el correo electrónico
		JLabel correoLabel = new JLabel("Correo electrónico:");
		correoLabel.setBounds(100, 250, 250, 30);
		correoLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(correoLabel);
		correoField = new JTextField();
		correoField.setBounds(330, 250, 300, 30);
		add(correoField);

		// Etiqueta y campo de texto para la cédula
		JLabel cedulaLabel = new JLabel("Cédula (número):");
		cedulaLabel.setBounds(100, 300, 200, 30);
		cedulaLabel.setFont(fuente); // Cambiar el tamaño de fuente
		add(cedulaLabel);
		cedulaField = new JTextField();
		cedulaField.setBounds(330, 300, 300, 30);
		add(cedulaField);

		// Botón para registrar
		registrarButton = new JButton("Registrar");
		registrarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarJugador(); // Método para registrar al jugador
			}
		});
		registrarButton.setBounds(480, 350, 150, 40);
		add(registrarButton);

		// Botón para regresar al menú principal
		JButton regresarMenuButton = new JButton("◀ REGRESAR");
		regresarMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui.cambiarEscena("menu"); // Método para volver al menú principal
			}
		});
		regresarMenuButton.setBounds(10, 10, 110, 25);
		add(regresarMenuButton);
	}

	// Método para registrar al jugador
	private void registrarJugador() {
		String nombreCompleto = nombreCompletoField.getText();
		String correoElectronico = correoField.getText();
		String cedulaStr = cedulaField.getText();

		// Validación del correo electrónico (utiliza regex)
		if (!correoElectronico.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
			JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.");
			return;
		}

		// Validación de la cédula (try/catch para eso)
		try {
			Integer.parseInt(cedulaStr);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido.");
		}

		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		boolean registroValido = logica.registrarJugador(nombreCompleto, correoElectronico, cedulaStr);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		// Mostrar mensaje de error si la cedula esta duplicada
		if (registroValido == false) {
			JOptionPane.showMessageDialog(this, "La cédula ingresada ya se encuentra registrada");
			return;
		} else {
			JOptionPane.showMessageDialog(this, "Registrando jugador: " + nombreCompleto);
			setGui();
		}
	}

	public void setGui() {
		cedulaField.setText("");
		correoField.setText("");
		nombreCompletoField.setText("");
	}
}