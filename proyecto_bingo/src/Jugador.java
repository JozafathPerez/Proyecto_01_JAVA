import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class Jugador {
  private String nombre;
  private String correo;
  private int cedula;

  public Jugador() {
    // Implementar esta picha, playos, yo no voy hacer nada, ya voy a mimir. post.. Manden porno
  }

public Jugador(String nombre, String correo, int cedula) {
  this.nombre = nombre;
  this.correo = correo;
  this.cedula = cedula;
}

public void agregarJugadorACSV() {
  String csvFile = "Jugadores.csv"; // Nombre del archivo csv
  try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
    String[] datos = {
      this.nombre,
      this.correo,
      String.valueOf(this.cedula)
    };
    writer.writeNext(datos);
  } catch (IOException e) {
    e.printStackTrace();
    }
  }

  // Getters y setters (métodos para acceder y modificar los atributos)
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public int getCedula() {
    return cedula;
  }

  public void setCedula(int cedula) {
    this.cedula = cedula;
  }
}
