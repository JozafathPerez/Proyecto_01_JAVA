package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import logica.Juego;

/**
 * La clase `Gui` representa la interfaz gráfica principal de la aplicación de
 * Bingo.
 * Contiene paneles (escenas) para diferentes funciones y permite la
 * comunicación entre ellos.
 */
public class Gui {
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
	private WordCloudPanel wordCloudPanel;

	/**
	 * Constructor de la clase `Gui`.
	 * 
	 * @param pLogica La instancia de la clase `Juego` que contiene la lógica
	 */
	public Gui(Juego pLogica) {
		ventana = new JFrame("Menú de Bingo");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 700);
		ventana.setResizable(false);

		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);

		// Panel para el menú principal #1
		menuPanel = new MenuPanel(this);
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

		// Panel para las estadísticas
		estadisticasPanel = new EstadisticasPanel(pLogica);
		// estadisticasPanel.add(new JLabel("En proceso..."));
		registraEscena(estadisticasPanel, "estadisticas");

		// Panel para configurar premios
		wordCloudPanel = new WordCloudPanel(pLogica);
		registraEscena(wordCloudPanel, "wordCloud");

		ventana.add(cards);
		cardLayout.show(cards, "menu");
		ventana.setVisible(true);
	}

	/**
	 * Registra una escena (panel) en el contenedor de escenas.
	 * 
	 * @param pComponent El componente que representa la escena.
	 * @param pNombre Nombre de la escena a registrar.
	 */
	public static void registraEscena(Component pComponent, String pNombre) {
		cards.add(pComponent, pNombre);
	}

	/**
	 * Cambia la escena actual a la escena con el nombre especificado.
	 * 
	 * @param pNombre El nombre de la escena a la que se debe cambiar.
	 */
	public static void cambiarEscena(String pNombre) {
		cardLayout.show(cards, pNombre);
	}

	
	/** 
	 * @return JFrame
	 */
	// Getters para acceder a los paneles de la interfaz gráfica

	public static JFrame getVentana() {
		return ventana;
	}

	
	/** 
	 * @return MenuPanel
	 */
	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	
	/** 
	 * @return IniciarJuegoPanel
	 */
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

	public EstadisticasPanel getEstadisticasPanel() {
		return estadisticasPanel;
	}

	public WordCloudPanel getWordCloudPanel() {
		return wordCloudPanel;
	}
}
