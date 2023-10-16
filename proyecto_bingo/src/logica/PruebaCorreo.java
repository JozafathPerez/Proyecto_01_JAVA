package logica;

import java.util.List;

public class PruebaCorreo {
  public static void main(String[] args) {
    // Crea una instancia de la clase CuentaCorreo
    CuentaCorreo cuenta = new CuentaCorreo("bingosocialmold@gmail.com");

    // Obtener los comentarios de los mensajes de correo
    List<String> comentarios = cuenta.obtenerComentariosDeMensajes();

    // Itera a través de los comentarios e imprímelos
    System.out.println("==============================================");
    for (String comentario : comentarios) {

        System.out.println("Comentario: " + comentario);
        System.out.println("==============================================");
    }
  }
}
