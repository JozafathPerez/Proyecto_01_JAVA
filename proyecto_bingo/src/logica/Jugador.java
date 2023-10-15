package logica;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class Jugador {
  private String nombre;
  private String correo;
  private String cedula;
  private List<String> cartonesAsignados;

public Jugador(String nombre, String correo, String cedula) {
  this.nombre = nombre;
  this.correo = correo;
  this.cedula = cedula;
  cartonesAsignados = new ArrayList<>();
}

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

  public String getCedula() {
    return cedula;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  public void agregarCartonAjugador(String identificadorCarton) {
    // Verificar si el cartón ya ha sido asignado
    if (!cartonesAsignados.contains(identificadorCarton)) {
        cartonesAsignados.add(identificadorCarton);
    }
  }

  public List<String> getCartonesAsignados() {
    return cartonesAsignados;
  }
  
  public boolean tieneCarton(String identificadorCarton) {
    return cartonesAsignados.contains(identificadorCarton);
  }
}
