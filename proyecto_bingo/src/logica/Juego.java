package logica;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Juego {
  private ArrayList<CartonBingo> cartones;
  private ArrayList<Jugador> jugadores;
  private List<String> cartonesEnJuego;
  private ArrayList<Integer> numerosCantados; 
  private List<String> ganadores;
  public int[][] matrizVerificadora;
  private String modo;
  private double premio;

  public Juego() {
    cartones = new ArrayList<>();
    jugadores = new ArrayList<>();
    numerosCantados = new ArrayList<>();
    ganadores = new ArrayList<>();
    cartonesEnJuego = new ArrayList<>();
    matrizVerificadora = new int[5][5];
  }

  public void configurarJuego(String modo, double premio) {
    this.modo = modo;
    this.premio = premio;

    if (modo.equals("Jugar en X")) {
        matrizVerificadora = new int[][] {
            {1, 0, 0, 0, 1},
            {0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0},
            {0, 1, 0, 1, 0},
            {1, 0, 0, 0, 1}
        };
    }

    if (modo.equals("Cuatro esquinas")) {
        matrizVerificadora = new int[][] {
            {1, 0, 0, 0, 1},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {1, 0, 0, 0, 1}
        };
    }

    if (modo.equals("Carton lleno")) {
        matrizVerificadora = new int[][] {
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1}
        };
    }

    if (modo.equals("Jugar en Z")) {
        matrizVerificadora = new int[][] {
            {1, 1, 1, 1, 1},
            {0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0},
            {1, 1, 1, 1, 1}
        };
    }
  }

  public void agregarCarton(CartonBingo carton) {
    cartones.add(carton);
  }

  public void crearCartones(int pCantidad) {
    for (int i = 0; i < pCantidad; i++) {
      CartonBingo carton = new CartonBingo();
      agregarCarton(carton);
    }
  }

    /*
   * Implementacion de la agregar jugador
   */

  public boolean validarJugador(String nombre, String correo, String cedula) {
    for(Jugador objeto : jugadores) {
      if (objeto.getCedula().equals(cedula)) {
        
        return true;
      }
    }
    return false;
  }

  public boolean registrarJugador(String nombre, String correo, String cedula) {
    if ( validarJugador(nombre, correo, cedula) == false) {
      Jugador jugador = new Jugador(nombre, correo, cedula);
      jugador.agregarJugadorACSV();
      jugadores.add(jugador);
      return true;
    } else {
      return false; // Si retorna false significa que la cedula esta duplicada
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

        // Verificar si el archivo CSV está vacío
        if (datos.isEmpty()) {
          return;
        }

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


  public void cantarNumero() {
    // Generar un número aleatorio y agregarlo a la lista de números cantados
    int numero;
    do {
        numero = (int) (Math.random() * (75 - 1 + 1)) + 1; 
      } while (validarNumCantado(numero));
    numerosCantados.add(numero);
    marcarCarton(numero);
  }

  private boolean validarNumCantado(int numero) {
    for(int numCantado : numerosCantados) {
      if (numCantado == numero) {
        return true; // El número ya existe en la columna
      }
    }
    return false; // El número no existe en la columna
  }

  public void marcarCarton (int numero) {
    for (String identificador : cartonesEnJuego) {

      CartonBingo carton = encontrarCartonPorIdentificador(identificador);

      int[][] matriz = carton.getMatriz(); // Obtener el valor de la matriz
      // Recorrer las filas
      for (int fila = 0; fila < 5; fila += 1) {
        // Recorrer por columnas
        for (int columna = 0; columna < 5; columna += 1) {
          // Compara si hay coincidencias con el numero marcado
          if (matriz[fila][columna] == numero) {
            if (matrizVerificadora[fila][columna] == 1) {
              carton.setValorCasilla(fila, columna, 1);
              break;
            }
          }
        }
      }
    }
  } // QUE HORROR VER TANTA VARA ANIDADA.......

  private CartonBingo encontrarCartonPorIdentificador(String identificador) {
    for (CartonBingo carton : cartones) {
        if (carton.getIdentificador().equals(identificador)) {
            return carton;
        }
    }
    return null; // Devuelve null si no se encuentra el cartón con el identificador
  }

  public void verificarCartones() {
    for (String identificador : cartonesEnJuego) {
        // Encuentra el cartón correspondiente por su identificador
        CartonBingo carton = encontrarCartonPorIdentificador(identificador);

        int[][] matrizMarcado = carton.getMatrizMarcado();

        // Compara la matriz de marcado del cartón con la matriz verificadora
        if (matricesSonIguales(matrizMarcado, matrizVerificadora)) {
            ganadores.add(identificador);
            carton.imprimirMatrizMarcado();
        }
    }
  }

  private boolean matricesSonIguales(int[][] matriz1, int[][] matriz2) {

    for (int i = 0; i < matriz1.length; i++) {
        for (int j = 0; j < matriz1[0].length; j++) {
            if (matriz1[i][j] != matriz2[i][j]) {
                return false; // Las matrices no son iguales
            }
        }
    }

    return true; // Las matrices son iguales
  }


  public boolean mostrarGanador() {
    return !ganadores.isEmpty();
  }

  /*
   * Implementacion de la funcion enviar carton
   */

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
    
        // Genera un número aleatorio entre 0 y el total de cartones disponibles
        int numeroAleatorio;
        String identificador;

        do {
            numeroAleatorio = (int) (Math.random() * CartonBingo.totalCartones);
            identificador = CartonBingo.obtenerIdentificadorPorIndice(numeroAleatorio);
        } while (cartonesEnviados.contains(identificador));


        if (agregarCartonAJuego(identificador) == (true)) {
          cartonesEnviados.add(identificador);
          jugador.agregarCartonAjugador(identificador);
        }
    }

    // Fuera del bucle, envía el correo con todos los archivos adjuntos
    String[] archivosAdjuntos = new String[cartonesEnviados.size()];
    int index = 0;
    for (String identificador : cartonesEnviados) {
        archivosAdjuntos[index] = "proyecto_bingo/cartones/" + identificador + ".png";
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

  public boolean agregarCartonAJuego(String identificadorCarton) {
    // Verificar si el cartón ya ha sido asignado
    if (!cartonesEnJuego.contains(identificadorCarton)) {
        cartonesEnJuego.add(identificadorCarton);
        return true;
    } else {
      return false;
    }
  }

  public Jugador obtenerJugadorPorIdentificadorCarton(String identificadorCarton) {
    for (Jugador jugador : jugadores) {
        if (jugador.tieneCarton(identificadorCarton)) {
            return jugador;
        }
    }
    return null; // Devuelve null si no se encuentra el jugador
  }

      // Método para obtener la lista de números cantados
  public List<Integer> getNumerosCantados() {
    return numerosCantados;
  }

    // Método para obtener la lista de ganadores
  public List<String> getGanadores() {
    return ganadores;
  }
}
