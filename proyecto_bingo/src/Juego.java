import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.stream.StreamFilter;

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
  public Set<String> cartonesEnviados;

  public Juego() {
    cartones = new ArrayList<>();
    jugadores = new ArrayList<>();
    numerosCantados = new ArrayList<>();
    cartonesEnviados = new HashSet<>();
  }

  public void configurarJuego(String modo, double premio) {
    this.modo = modo;
    this.premio = premio;
  }

  public void agregarCarton(CartonBingo carton) {
    cartones.add(carton);
  }

  public boolean validarJugador(String nombre, String correo, String cedula) {
    System.out.println(cedula);
    for(Jugador objeto : jugadores) {
      System.out.println(objeto.getCedula());
      if (objeto.getCedula().equals(cedula)) {
        
        return true;
      }
    }
    return false;
  }

  public void registrarJugador(String nombre, String correo, String cedula) {
    if ( validarJugador(nombre, correo, cedula) == false) {
      Jugador jugador = new Jugador(nombre, correo, cedula);
      jugador.agregarJugadorACSV();
      jugadores.add(jugador);
      System.out.println("Jugador agregado al registro de jugadores");
    } else {
      System.out.println("Error, la cedula esta duplicada");
    }
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

  // public void consultarCarton(String identificadorCarton) {
  //   String rutaCarton = "cartones/" + identificadorCarton + ".png";

  //   // Cargar y mostrar la imagen del cartón
  //   try {
  //     BufferedImage cartonImage = ImageIO.read(new File(rutaCarton));
  //        // Ver el carton en el panel que estamos
  //   } catch (IOException e) {
  //     System.err.println("Error al cargar el cartón: " + e.getMessage());
  // }

  public void enviarCartonAJugador(int cantCartones, String cedula) {
    // Reemplaza con tu dirección de correo electrónico
    String remitente = "bingosocialmold@gmail.com";

    // Crea una instancia de CuentaCorreo
    CuentaCorreo cuenta = new CuentaCorreo(remitente);

    // Obtiene la dirección de correo del jugador con la cédula
    String destinatario = obtenerCorreoPorCedula(cedula);

    // Datos del correo
    String asunto = "Cartones solicitados";
    String cuerpo = "Este es un correo de Bingo Tico desde Java.";

    Jugador jugador = obtenerJugadorPorCedula(cedula);
    
    // Tener un registro de cartones enviados
    Set<String> cartonesEnviados = new HashSet<>(jugador.getCartonesAsignados());

    while (cartonesEnviados.size() < cantCartones) { 
        // hacer validacion que si cartonesEnviados.size() es > 5... ERROR
    
        // Genera un número aleatorio entre 0 y el total de cartones disponibles
        int numeroAleatorio;
        String identificador;

        do {
            numeroAleatorio = (int) (Math.random() * CartonBingo.totalCartones);
            identificador = CartonBingo.obtenerIdentificadorPorIndice(numeroAleatorio);
        } while (cartonesEnviados.contains(identificador));


        cartonesEnviados.add(identificador);
        jugador.agregarCarton(identificador);
    }

    // Fuera del bucle, envía el correo con todos los archivos adjuntos
    String[] archivosAdjuntos = new String[cartonesEnviados.size()];
    int index = 0;
    for (String identificador : cartonesEnviados) {
        archivosAdjuntos[index] = "cartones/" + identificador + ".png";
        index++;
    }

    cuenta.enviarCorreo(destinatario, asunto, cuerpo, archivosAdjuntos);
  }


  private String obtenerCorreoPorCedula(String cedula) {
    for (Jugador jugador : jugadores) {
        if (jugador.getCedula().equals(cedula)) {
            return jugador.getCorreo();
        }
    }
    return ""; // Devuelve una cadena vacía si no se encuentra la cédula
    // poner errores de si no se encontro el jugardor en la interfaz
  }

  private Jugador obtenerJugadorPorCedula(String cedula) {
    for (Jugador jugador : jugadores) {
        if (jugador.getCedula().equals(cedula)) {
            return jugador;
        }
    }
    return null; // Devuelve nulo si no se encuentra la cédula
    // poner errores de si no se encontro el jugardor en la interfaz
  }


}
