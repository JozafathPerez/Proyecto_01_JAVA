package logica;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;

/**
 * Clase que representa a un jugador en el juego de bingo.
 */
public class Jugador {
  private String nombre; // Nombre del jugador
  private String correo; // Correo electrónico del jugador
  private String cedula; // Número de cédula del jugador
  private List<String> cartonesAsignados; // Lista de identificadores de cartones asignados

  /**
   * Constructor de la clase `Jugador`.
   *
   * @param pNombre El nombre del jugador.
   * @param pCorreo El correo electrónico del jugador.
   * @param pCedula El número de cédula del jugador.
   */
  public Jugador(String pNombre, String pCorreo, String pCedula) {
    this.nombre = pNombre;
    this.correo = pCorreo;
    this.cedula = pCedula;
    cartonesAsignados = new ArrayList<>();
  }

  /**
   * Agrega la información del jugador a un archivo CSV llamado "Jugadores.csv".
   */
  public void agregarJugadorACSV() {
    String csvFile = "Jugadores.csv"; // Nombre del archivo csv
    try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
      String[] datos = {
          this.nombre,
          this.correo,
          this.cedula
      };
      writer.writeNext(datos);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Obtiene el nombre del jugador.
   *
   * @return El nombre del jugador.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del jugador.
   *
   * @param nombre El nombre del jugador.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el correo electrónico del jugador.
   *
   * @return El correo electrónico del jugador.
   */
  public String getCorreo() {
    return correo;
  }

  /**
   * Establece el correo electrónico del jugador.
   *
   * @param correo El correo electrónico del jugador.
   */
  public void setCorreo(String correo) {
    this.correo = correo;
  }

  /**
   * Obtiene el número de cédula del jugador.
   *
   * @return El número de cédula del jugador.
   */
  public String getCedula() {
    return cedula;
  }

  /**
   * Establece el número de cédula del jugador.
   *
   * @param pCedula El número de cédula del jugador.
   */
  public void setCedula(String pCedula) {
    cedula = pCedula;
  }

  /**
   * Agrega un identificador de cartón a la lista de cartones asignados al
   * jugador.
   *
   * @param pIdentificadorCarton El identificador del cartón a agregar.
   */
  public void agregarCartonAjugador(String pIdentificadorCarton) {
    // Verifica si el cartón ya ha sido asignado
    if (!cartonesAsignados.contains(pIdentificadorCarton)) {
      cartonesAsignados.add(pIdentificadorCarton);
    }
  }

  /**
   * Obtiene la lista de identificadores de cartones asignados al jugador.
   *
   * @return La lista de identificadores de cartones asignados.
   */
  public List<String> getCartonesAsignados() {
    return cartonesAsignados;
  }

  /**
   * Verifica si el jugador tiene un cartón específico.
   *
   * @param pIdentificadorCarton El identificador del cartón a verificar.
   * @return `true` si el jugador tiene el cartón, `false` en caso contrario.
   */
  public boolean tieneCarton(String pIdentificadorCarton) {
    return cartonesAsignados.contains(pIdentificadorCarton);
  }
}
