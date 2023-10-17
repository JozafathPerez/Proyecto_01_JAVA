package logica;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileWriter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Juego {
  private ArrayList<CartonBingo> cartones;
  private ArrayList<Jugador> jugadores;
  private List<String> cartonesEnJuego;
  private ArrayList<Integer> numerosCantados; 
  private List<String> ganadores;
  public int[][] matrizVerificadora;
  private String modo;
  private double premio;
  private String fechaPartida;
  private String horaPartida;

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
    // Esto es solo para probar
    setFecha();
  }

  public String getModo() {
    return modo;
  }

  public double getPremio() {
    return premio;
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


  public int cantarNumero() {
    // Generar un número aleatorio y agregarlo a la lista de números cantados
    int numero;
    do {
        numero = (int) (Math.random() * (75 - 1 + 1)) + 1; 
      } while (validarNumCantado(numero));
    numerosCantados.add(numero);
    marcarCarton(numero);
    
    return numero;
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

  public boolean verificarCartones() {
    boolean hayGanadores = false;
    for (String identificador : cartonesEnJuego) {
        // Encuentra el cartón correspondiente por su identificador
        CartonBingo carton = encontrarCartonPorIdentificador(identificador);

        int[][] matrizMarcado = carton.getMatrizMarcado();

        // Compara la matriz de marcado del cartón con la matriz verificadora
        if (matricesSonIguales(matrizMarcado, matrizVerificadora)) {
            ganadores.add(identificador);
            carton.imprimirMatrizMarcado();
            hayGanadores = true;
        }
    }
    return hayGanadores;
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

  public Jugador obtenerJugadorPorCedula(String cedula) {
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
  
  public void guardarPartida() {
    String tipo = modo;
    
    String numerosCantadosStr = listaNumAStr();

    String ganadoresStr = listaGanadoresAStr();

    String fecha = fechaPartida;

    String hora  = horaPartida;

    try {
        SAXBuilder builder = new SAXBuilder();
        Document doc;

        try {
            doc = builder.build("partida.xml");
        } catch (Exception e) {
            // Si el archivo no existe, creamos un nuevo documento
            doc = new Document(new Element("partidasJuego"));
        }

        Element partidaRoot = doc.getRootElement();

        Element partidaElement = new Element("partida");
        partidaRoot.addContent(partidaElement);

        Element tipoElement = new Element("tipo");
        tipoElement.setText(tipo);

        Element numsCantadosElement = new Element("numerosCantados");
        numsCantadosElement.setText(numerosCantadosStr.toString());

        Element ganadoresElement = new Element("ganadores");
        ganadoresElement.setText(ganadoresStr.toString());

        Element fechaElement = new Element("fecha");
        fechaElement.setText(fecha);

        Element horaElement = new Element("hora");
        horaElement.setText(hora);

        partidaElement.addContent(tipoElement);
        partidaElement.addContent(numsCantadosElement);
        partidaElement.addContent(ganadoresElement);
        partidaElement.addContent(fechaElement);
        partidaElement.addContent(horaElement);

        XMLOutputter xml = new XMLOutputter();
        xml.setFormat(Format.getPrettyFormat());
        xml.output(doc, new FileWriter("partida.xml"));
    } catch (IOException ex) {
        System.out.println("Fatal error");
    }
  }

  public List<String> cargarNumerosCantados() {
    // Crear una lista para almacenar todos los números cantados
    List<String> numerosCantadosList = new ArrayList<>();
    try {
      // Crear un analizador SAX para cargar el documento XML
      SAXBuilder saxBuilder = new SAXBuilder();
      Document document = saxBuilder.build("partida.xml");

      // Obtener el elemento raíz del documento
      Element rootElement = document.getRootElement();

      // Obtener todas las etiquetas "partida"
      List<Element> partidas = rootElement.getChildren("partida");

      // Iterar a través de las etiquetas "partida"
      for (Element partida : partidas) {
          // Obtener el elemento "numerosCantados" de cada "partida"
          Element numerosCantadosElement = partida.getChild("numerosCantados");

          // Obtener el texto del elemento "numerosCantados"
          String numerosCantados = numerosCantadosElement.getText();

          // Dividir la cadena de números en una lista
          String[] numerosArray = numerosCantados.split(",");
          for (String numero : numerosArray) {
              numerosCantadosList.add(numero);
          }
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return numerosCantadosList;
  }

  public List<String> cargarModosPartida() {
    // Crear una lista para almacenar todos los tipos
    List<String> modosDeJuego = new ArrayList<>();
    try {
      // Crear un analizador SAX para cargar el documento XML
      SAXBuilder saxBuilder = new SAXBuilder();
      Document document = saxBuilder.build("partida.xml");

      // Obtener el elemento raíz del documento
      Element rootElement = document.getRootElement();

      // Obtener todas las etiquetas "partida"
      List<Element> partidas = rootElement.getChildren("partida");

      // Iterar a través de las etiquetas "partida"
      for (Element partida : partidas) {
          // Obtener el elemento "tipo" de cada "partida"
          Element tipoElement = partida.getChild("tipo");

          // Obtener el texto del elemento "tipo"
          String tipoValue = tipoElement.getText();
          modosDeJuego.add(tipoValue);
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println(modosDeJuego);
    return modosDeJuego;    
  }

  private  String listaNumAStr() {
    StringBuilder numerosCantadosStr = new StringBuilder();
    for (int i = 0; i < numerosCantados.size(); i++) {
      numerosCantadosStr.append(numerosCantados.get(i));
      if (i < numerosCantados.size() - 1) {
          numerosCantadosStr.append(",");
      }
    }
    return numerosCantadosStr.toString();
  }

  private String listaGanadoresAStr() {

    ArrayList<String> jugadoresGanadores = new ArrayList<>();

    //Obtener ganadores
    for (String identificador : ganadores) {
      String ganador = obtenerJugadorPorIdentificadorCarton(identificador).getCedula();
      if (ganador != null) {
        jugadoresGanadores.add(ganador);
      }
  }
    StringBuilder ganadoresStr = new StringBuilder();

    for (int i = 0; i < jugadoresGanadores.size(); i++) {
        ganadoresStr.append(jugadoresGanadores.get(i));

        if (i < ganadores.size() - 1) {
            ganadoresStr.append(",");
        }
    }

    return ganadoresStr.toString();
  }

  public void setFecha () {
  // Obtener la fecha y hora actual
    LocalDateTime fechaHoraActual = LocalDateTime.now();

    // Separar la fecha y la hora en variables distintas
    LocalDate fechaActual = fechaHoraActual.toLocalDate();
    LocalTime horaActual = fechaHoraActual.toLocalTime();

    // Crear formatos para la fecha y la hora
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Formatear la fecha y la hora como cadenas
    String fechaComoString = fechaActual.format(formatoFecha);
    String horaComoString = horaActual.format(formatoHora);

    // Setear los valores de los atributos
    fechaPartida = fechaComoString;
    horaPartida = horaComoString;
  }

  public void notificarGanador(List<String> listaGanadores) {
    // Reemplaza con tu dirección de correo electrónico
    String remitente = "bingosocialmold@gmail.com";

    // Crea una instancia de CuentaCorreo
    CuentaCorreo cuenta = new CuentaCorreo(remitente);

    for (String identificador: listaGanadores) {
      Jugador ganador = obtenerJugadorPorIdentificadorCarton(identificador);
      String mensaje = "Felicidades " + ganador.getNombre() + " ha sido el ganador de " + premio ;
      cuenta.enviarCorreo(ganador.getCorreo(),"Notificacion de ganador", mensaje);
    }
    
  }

  public void restablecerValoresDeJuego() {
    //Vaciar los elementos de la lista
    numerosCantados.clear();
    ganadores.clear();

    //Vaciar matrices de marcado
    for (CartonBingo carton: cartones) {
      restablecerMatrizMarcado(carton);
    }
  }

  private void restablecerMatrizMarcado(CartonBingo carton) {
    for (int i = 0; i < carton.getMatrizMarcado().length; i++) {
      for (int j = 0; j < carton.getMatrizMarcado()[i].length; j++) {
          carton.setValorCasilla(i, j, 0);
      }
   }
  }

  public void eliminarCartones() {
        // Se vacia la lista de cartones creados
        cartones.clear();
        cartonesEnJuego.clear();
        //Restablece los identificadores
        CartonBingo.setCartonId();
        Path carpeta = Paths.get("proyecto_bingo/cartones");
        try {
            Files.walkFileTree(carpeta, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }    
  }

  // Método para obtener la lista de números cantados
  public List<Integer> getNumerosCantados() {
    return numerosCantados;
  }

    // Método para obtener la lista de ganadores
  public List<String> getGanadores() {
    return ganadores;
  }

  public List<CartonBingo> getCartonesCreados() {
    return cartones;
  }

  public List<String> getCartonesEnJuego() {
    return cartonesEnJuego;
  }

  public List<Jugador> getJugadores() {
    return jugadores;
  }

}
