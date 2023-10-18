package logica;

import java.io.FileReader;
import java.io.IOException;
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

/**
 * Clase que representa la lógica principal del juego de bingo.
 */
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

  /**
   * Constructor de la clase `Juego`.
   */
  public Juego() {
    cartones = new ArrayList<>();
    jugadores = new ArrayList<>();
    numerosCantados = new ArrayList<>();
    ganadores = new ArrayList<>();
    cartonesEnJuego = new ArrayList<>();
    matrizVerificadora = new int[5][5];
  }

  /**
   * Configura el modo de juego y el premio.
   *
   * @param pModo   El modo de juego.
   * @param pPremio El monto del premio en dinero.
   */
  public void configurarJuego(String pModo, double pPremio) {
    this.modo = pModo;
    this.premio = pPremio;

    if (pModo.equals("Jugar en X")) {
      matrizVerificadora = new int[][] {
          { 1, 0, 0, 0, 1 },
          { 0, 1, 0, 1, 0 },
          { 0, 0, 1, 0, 0 },
          { 0, 1, 0, 1, 0 },
          { 1, 0, 0, 0, 1 }
      };
    }

    if (pModo.equals("Cuatro esquinas")) {
      matrizVerificadora = new int[][] {
          { 1, 0, 0, 0, 1 },
          { 0, 0, 0, 0, 0 },
          { 0, 0, 0, 0, 0 },
          { 0, 0, 0, 0, 0 },
          { 1, 0, 0, 0, 1 }
      };
    }

    if (pModo.equals("Carton lleno")) {
      matrizVerificadora = new int[][] {
          { 1, 1, 1, 1, 1 },
          { 1, 1, 1, 1, 1 },
          { 1, 1, 1, 1, 1 },
          { 1, 1, 1, 1, 1 },
          { 1, 1, 1, 1, 1 }
      };
    }

    if (pModo.equals("Jugar en Z")) {
      matrizVerificadora = new int[][] {
          { 1, 1, 1, 1, 1 },
          { 0, 0, 0, 1, 0 },
          { 0, 0, 1, 0, 0 },
          { 0, 1, 0, 0, 0 },
          { 1, 1, 1, 1, 1 }
      };
    }
    // Esto es solo para probar
    setFecha();
  }

  /**
   * Agrega un cartón al juego.
   *
   * @param pCarton El cartón de bingo a agregar al juego.
   */
  public void agregarCarton(CartonBingo pCarton) {
    cartones.add(pCarton);
  }

  /**
   * Crea cartones de bingo y los agrega al juego.
   *
   * @param pCantidad La cantidad de cartones a crear y agregar.
   */
  public void crearCartones(int pCantidad) {
    for (int i = 0; i < pCantidad; i++) {
      CartonBingo carton = new CartonBingo();
      agregarCarton(carton);
    }
  }

  /**
   * Valida si un jugador con la misma cédula ya existe en la lista de jugadores.
   *
   * @param pNombre El nombre del jugador.
   * @param pCorreo El correo del jugador.
   * @param pCedula La cédula del jugador.
   * @return `true` si ya existe un jugador con la misma cédula, `false` en caso
   *         contrario.
   */
  public boolean validarJugador(String pNombre, String pCorreo, String pCedula) {
    for (Jugador objeto : jugadores) {
      if (objeto.getCedula().equals(pCedula)) {

        return true;
      }
    }
    return false;
  }

  /**
   * Registra un nuevo jugador en el juego si no existe previamente.
   *
   * @param pNombre El nombre del jugador.
   * @param pCorreo El correo del jugador.
   * @param pCedula La cédula del jugador.
   * @return `true` si el jugador se registra con éxito, `false` si la cédula está
   *         duplicada.
   */
  public boolean registrarJugador(String pNombre, String pCorreo, String pCedula) {
    if (validarJugador(pNombre, pCorreo, pCedula) == false) {
      Jugador jugador = new Jugador(pNombre, pCorreo, pCedula);
      jugador.agregarJugadorACSV();
      jugadores.add(jugador);
      return true;
    } else {
      return false; // Si retorna false significa que la cedula esta duplicada
    }
  }

  /**
   * Verifica si algún jugador ha ganado en el juego actual.
   *
   * @return `true` si hay ganadores, `false` en caso contrario.
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

  /**
   * Canta un número aleatorio durante el juego.
   *
   * @return El número cantado aleatorio.
   */
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

  /**
   * Valida si un número ya ha sido cantado durante el juego.
   *
   * @param pNumero El número a validar.
   * @return `true` si el número ya ha sido cantado, `false` en caso contrario.
   */
  private boolean validarNumCantado(int pNumero) {
    for (int numCantado : numerosCantados) {
      if (numCantado == pNumero) {
        return true; // El número ya existe en la columna
      }
    }
    return false; // El número no existe en la columna
  }

  /**
   * Marca un cartón de bingo si el número cantado coincide con los valores del
   * cartón.
   *
   * @param pNumero El número cantado que se utiliza para marcar los cartones.
   */
  public void marcarCarton(int pNumero) {
    for (String identificador : cartonesEnJuego) {

      CartonBingo carton = encontrarCartonPorIdentificador(identificador);

      int[][] matriz = carton.getMatriz(); // Obtener el valor de la matriz
      // Recorrer las filas
      for (int fila = 0; fila < 5; fila += 1) {
        // Recorrer por columnas
        for (int columna = 0; columna < 5; columna += 1) {
          // Compara si hay coincidencias con el numero marcado
          if (matriz[fila][columna] == pNumero) {
            if (matrizVerificadora[fila][columna] == 1) {
              carton.setValorCasilla(fila, columna, 1);
              break;
            }
          }
        }
      }
    }
  } // QUE HORROR VER TANTA VARA ANIDADA.......

  /**
   * Encuentra un cartón de bingo por su identificador.
   *
   * @param pIdentificador El identificador del cartón a buscar.
   * @return El cartón de bingo encontrado o `null` si no se encuentra.
   */
  private CartonBingo encontrarCartonPorIdentificador(String pIdentificador) {
    for (CartonBingo carton : cartones) {
      if (carton.getIdentificador().equals(pIdentificador)) {
        return carton;
      }
    }
    return null; // Devuelve null si no se encuentra el cartón con el identificador
  }

  /**
   * Verifica si algún jugador ha ganado en el juego actual.
   *
   * @return `true` si hay ganadores, `false` en caso contrario.
   */
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

  /**
   * Compara dos matrices para verificar si son idénticas.
   *
   * @param pMatriz1 La primera matriz a comparar.
   * @param pMatriz2 La segunda matriz a comparar.
   * @return `true` si las matrices son idénticas, `false` si no lo son.
   */
  private boolean matricesSonIguales(int[][] pMatriz1, int[][] pMatriz2) {
    for (int i = 0; i < pMatriz1.length; i++) {
      for (int j = 0; j < pMatriz1[0].length; j++) {
        if (pMatriz1[i][j] != pMatriz2[i][j]) {
          return false; // Las matrices no son iguales
        }
      }
    }

    return true; // Las matrices son iguales
  }

  /**
   * verifica si existe al menos un jugador en la lista de ganadores
   * 
   * @return `true` existe un Jugador en la lista, `false` en caso contrario
   */
  public boolean mostrarGanador() {
    return !ganadores.isEmpty();
  }

  /**
   * Función para enviar cartones a un jugador por correo electrónico.
   *
   * @param pCantCartones Cantidad de cartones a enviar.
   * @param pCedula       La cédula del jugador al que se le envían los cartones.
   */
  public void enviarCartonAJugador(int pCantCartones, String pCedula) {
    // Reemplaza con tu dirección de correo electrónico
    String remitente = "bingosocialmold@gmail.com";

    // Crea una instancia de CuentaCorreo
    CuentaCorreo cuenta = new CuentaCorreo(remitente);

    // Obtiene la dirección de correo del jugador con la cédula
    String destinatario = obtenerCorreoPorCedula(pCedula);

    // Datos del correo
    String asunto = "Cartones solicitados";
    String cuerpo = "Este es un correo de Bingo Tico desde Java.";

    Jugador jugador = obtenerJugadorPorCedula(pCedula);

    // Tener un registro de cartones enviados
    Set<String> cartonesEnviados = new HashSet<>(jugador.getCartonesAsignados());

    // Contador
    int contador = 0;
    while (contador < pCantCartones) {

      // Genera un número aleatorio entre 0 y el total de cartones disponibles
      String identificador;
      int numeroAleatorio;

      do {
        numeroAleatorio = (int) (Math.random() * CartonBingo.getTotalCartones());
        identificador = CartonBingo.obtenerIdentificadorPorIndice(numeroAleatorio);

      } while (cartonesEnviados.contains(identificador));

      if (agregarCartonAJuego(identificador) == (true)) {
        cartonesEnviados.add(identificador);
        jugador.agregarCartonAjugador(identificador);
        contador++;
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

  /**
   * Obtiene la dirección de correo de un jugador por su cédula.
   *
   * @param pCedula La cédula del jugador.
   * @return La dirección de correo del jugador o una cadena vacía si no se
   *         encuentra.
   */
  private String obtenerCorreoPorCedula(String pCedula) {
    for (Jugador jugador : jugadores) {
      if (jugador.getCedula().equals(pCedula)) {
        return jugador.getCorreo();
      }
    }
    return ""; // Devuelve una cadena vacía si no se encuentra la cédula
    // poner errores de si no se encontro el jugardor en la interfaz
  }

  /**
   * Obtiene un jugador por su cédula.
   *
   * @param pCedula La cédula del jugador.
   * @return El jugador encontrado o `null` si no se encuentra.
   */
  public Jugador obtenerJugadorPorCedula(String pCedula) {
    for (Jugador jugador : jugadores) {
      if (jugador.getCedula().equals(pCedula)) {
        return jugador;
      }
    }
    return null; // Devuelve nulo si no se encuentra la cédula
    // poner errores de si no se encontro el jugardor en la interfaz
  }

  /**
   * Agrega un cartón al juego si aún no ha sido asignado.
   *
   * @param pIdentificadorCarton El identificador del cartón a agregar.
   * @return `true` si se agrega con éxito, `false` si ya ha sido asignado.
   */
  public boolean agregarCartonAJuego(String pIdentificadorCarton) {
    // Verificar si el cartón ya ha sido asignado
    if (!cartonesEnJuego.contains(pIdentificadorCarton)) {
      cartonesEnJuego.add(pIdentificadorCarton);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Obtiene un jugador por el identificador de un cartón.
   *
   * @param pIdentificadorCarton El identificador del cartón.
   * @return El jugador que tiene el cartón o `null` si no se encuentra.
   */
  public Jugador obtenerJugadorPorIdentificadorCarton(String pIdentificadorCarton) {
    for (Jugador jugador : jugadores) {
      if (jugador.tieneCarton(pIdentificadorCarton)) {
        return jugador;
      }
    }
    return null; // Devuelve null si no se encuentra el jugador
  }

  /**
   * Guarda los datos de la partida actual en un archivo XML llamado
   * "partida.xml".
   * Los datos incluyen el tipo de partida, números cantados, ganadores, fecha y
   * hora.
   */
  public void guardarPartida() {
    String tipo = modo;
    String numerosCantadosStr = listaNumAStr();
    String ganadoresStr = listaGanadoresAStr();
    String fecha = fechaPartida;
    String hora = horaPartida;

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

  /**
   * Carga la lista de números cantados desde un archivo XML de partida.
   *
   * @return Una lista de números cantados como cadenas de texto.
   */
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

  /**
   * Carga la lista de modos de partida desde un archivo XML de partida.
   *
   * @return Una lista de modos de partida como cadenas de texto.
   */
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

  /**
   * Convierte la lista de números cantados en una cadena de texto separada por
   * comas.
   *
   * @return Una cadena de texto con los números cantados.
   */
  private String listaNumAStr() {
    StringBuilder numerosCantadosStr = new StringBuilder();
    for (int i = 0; i < numerosCantados.size(); i++) {
      numerosCantadosStr.append(numerosCantados.get(i));
      if (i < numerosCantados.size() - 1) {
        numerosCantadosStr.append(",");
      }
    }
    return numerosCantadosStr.toString();
  }

  /**
   * Convierte la lista de ganadores en una cadena de texto separada por comas.
   *
   * @return Una cadena de texto con las cédulas de los ganadores.
   */
  private String listaGanadoresAStr() {

    ArrayList<String> jugadoresGanadores = new ArrayList<>();

    // Obtener ganadores
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

  /**
   * Establece la fecha y hora actual en el formato "dd/MM/yyyy" y "HH:mm:ss"
   * respectivamente,
   * y guarda estos valores en los atributos "fechaPartida" y "horaPartida".
   */
  public void setFecha() {
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

  /**
   * Notifica a los ganadores por correo electrónico.
   *
   * @param pListaGanadores Una lista de identificadores de ganadores.
   */
  public void notificarGanador(List<String> pListaGanadores) {
    // Reemplaza con tu dirección de correo electrónico
    String remitente = "bingosocialmold@gmail.com";

    // Crea una instancia de CuentaCorreo
    CuentaCorreo cuenta = new CuentaCorreo(remitente);

    for (String identificador : pListaGanadores) {
      Jugador ganador = obtenerJugadorPorIdentificadorCarton(identificador);
      String mensaje = "Felicidades " + ganador.getNombre() + " ha sido el ganador de " + premio;
      cuenta.enviarCorreo(ganador.getCorreo(), "Notificacion de ganador", mensaje);
    }

  }

  /**
   * Restablece los valores del juego a su estado inicial, incluyendo la lista de
   * números cantados y
   * las matrices de marcado de los cartones.
   */
  public void restablecerValoresDeJuego() {
    // Vaciar los elementos de la lista
    numerosCantados.clear();
    ganadores.clear();
    // cartonesEnJuego

    // Vaciar matrices de marcado
    for (CartonBingo carton : cartones) {
      restablecerMatrizMarcado(carton);
    }
  }

  /**
   * Restablece la matriz de marcado de un cartón a su estado inicial, marcando
   * todas las casillas como no marcadas.
   *
   * @param pCarton El cartón de Bingo a restablecer.
   */
  private void restablecerMatrizMarcado(CartonBingo pCarton) {
    for (int i = 0; i < pCarton.getMatrizMarcado().length; i++) {
      for (int j = 0; j < pCarton.getMatrizMarcado()[i].length; j++) {
        pCarton.setValorCasilla(i, j, 0);
      }
    }
  }

  /**
   * Elimina todos los cartones del juego y restablece los identificadores de
   * cartón.
   * También elimina los archivos de cartones guardados en la carpeta
   * "proyecto_bingo/cartones".
   */
  public void eliminarCartones() {
    // Se vacia la lista de cartones creados
    cartones.clear();
    cartonesEnJuego.clear();
    // Restablece los identificadores
    CartonBingo.setCartonId();
    // Restablece el total de cartones
    CartonBingo.setTotalCartones();
    // Restablece los cartones asignados a jugadores
    for (Jugador jugador : jugadores) {
      jugador.resetCartoneAsignados();
    }

    Path carpeta = Paths.get("proyecto_bingo/cartones");
    try {
      Files.walkFileTree(carpeta, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
          new SimpleFileVisitor<Path>() {
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
  public String getModo() {
    return modo;
  }

  public double getPremio() {
    return premio;
  }

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
