import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Juego {
  private ArrayList<CartonBingo> cartones;
  private ArrayList<Jugador> jugadores;
  private ArrayList<Integer> numerosCantados; 
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

  public void registrarJugador(String nombre, String correo, String cedula) {
    Jugador jugador = new Jugador(nombre, correo, cedula);
    jugador.agregarJugadorACSV();
    jugadores.add(jugador);
  }

  /*
   * Cargar jugadores
   */
  public void cargarJugadoresDesdeCSV() {
        String csvFile = "Jugadores.csv";
        
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            try {
                List<String[]> datos = reader.readAll(); // Leer todos los datos

                for (String[] fila : datos) {
                    String nombre = fila[0];
                    String correo = fila[1];
                    String cedula = fila[2];

                    Jugador jugador = new Jugador(nombre, correo, cedula);
                    jugadores.add(jugador);
                }
            } catch (CsvException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imprimir() {
      for(Jugador ob : jugadores) {
        ob.imprimir();
      }
    }

  // public int cantarNumero() {
  //   // Generar un número aleatorio y agregarlo a la lista de números cantados
  // }

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
