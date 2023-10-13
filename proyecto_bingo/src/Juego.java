import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Juego {
  private ArrayList<CartonBingo> cartones;
  private ArrayList<Jugador> jugadores;
  private String modo;
  private double premio;
  // private List<N> numerosCantados; NO SE QUE TIPO PONER 

  public Juego() {
    cartones = new ArrayList<>();
    jugadores = new ArrayList<>();
    numerosCantados = new ArrayList<>();
  }

  public void configurarJuego(String modo, double premio) {
    this.modo = modo;
    this.premio = premio;
  }

  public void agregarCarton(CartonBingo carton) {
    cartones.add(carton);
  }

  public void agregarJugador(Jugador jugador) {
    jugadores.add(jugador);
  }

  public int cantarNumero() {
    // Generar un número aleatorio y agregarlo a la lista de números cantados
  }

  public void verificarCartones() {
    // Implementa la lógica para verificar los cartones y marcar los números
  }

  public void mostrarGanador() {
    // Implementa la lógica para mostrar al ganador y entregar el premio
  }
  public void consultarCarton(String identificadorCarton) {
    String rutaCarton = "cartones/" + identificadorCarton + ".png";

    // Cargar y mostrar la imagen del cartón
    try {
      BufferedImage cartonImage = ImageIO.read(new File(rutaCarton));
         // Ver el carton en el panel que estamos
    } catch (IOException e) {
      System.err.println("Error al cargar el cartón: " + e.getMessage());
  }
}
