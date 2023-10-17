package logica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * La clase `CartonBingo` representa un cartón de bingo con números aleatorios.
 */
public class CartonBingo {
  private String identificador;
  private int[][] matriz;
  private int[][] matrizMarcado;
  private static int contadorId = 0;
  public static int totalCartones = 0;
  
  /**
   * Constructor de la clase `CartonBingo`.
   * Crea un nuevo cartón con números aleatorios y lo guarda como una imagen PNG.
   */
  public CartonBingo() {
    matriz = new int[5][5];
    matrizMarcado = new int[5][5];
    identificador = generarIdentificadorUnico();
    generarCarton();
    generarPNG();
    totalCartones++;
  }

  /**
   * Genera un cartón de bingo con números aleatorios.
   */
  private void generarCarton() {
    for (int columna = 0; columna < 5; columna++) {
      generarNumeroAleatorio(columna);
    }
  }

  /**
   * Genera un número aleatorio para una columna del cartón.
   * @param pColumna El índice de la columna.
   */
  private void generarNumeroAleatorio(int pColumna) {
    int limiteInferior = pColumna * 15 + 1; 
    int limiteSuperior = (pColumna + 1) * 15;   

    for (int fila = 0; fila < 5; fila ++) {
      int numero;
      do {
        numero = (int) (Math.random() * (limiteSuperior - limiteInferior + 1)) + limiteInferior; 
      } while (contieneNumeroEnColumna(pColumna, numero)); 

      matriz[fila][pColumna] = numero;
    }
  }

  /**
   * Verifica si un número ya existe en una columna del cartón.
   * @param pColumna El índice de la columna.
   * @param pNumero El número a verificar.
   * @return `true` si el número ya existe en la columna, `false` en caso contrario.
   */
  private boolean contieneNumeroEnColumna(int pColumna, int pNumero) {
    for (int fila = 0; fila < 5; fila++) {
      if (matriz[fila][pColumna] == pNumero) {
        return true; // El número ya existe en la columna
      }
    }
    return false; // El número no existe en la columna
  }

  /**
   * Genera un identificador único para el cartón.
   * @return El identificador único.
   */
  private String generarIdentificadorUnico() {
    String formatoContador = String.format("%03d", contadorId);
    contadorId++;
    
    return "JJO" + formatoContador; // Joza, Jeff, Oscar
  }

    /**
   * Genera una imagen PNG del cartón de bingo en la carpeta ./cartones/
   */
  private void generarPNG() {
    int width = 634; // Ancho de la imagen
    int height = 748; // Alto de la imagen
  
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();
  
    // Generar un número aleatorio del 1 al 3
    Random random = new Random();
    int numeroAleatorio = random.nextInt(3) + 1;
  
    // Determinar el nombre de la imagen según el número aleatorio
    String nombreImagen;
    if (numeroAleatorio == 1) {
      nombreImagen = "ImagenesDeCartones/CartonNaranja.png";
    } else if (numeroAleatorio == 2) {
      nombreImagen = "ImagenesDeCartones/CartonAzul.png";
    } else {
      nombreImagen = "ImagenesDeCartones/CartonVerde.png";
    }
  
    // Cargar la imagen de fondo
    try {
      BufferedImage background = ImageIO.read(new File(nombreImagen));
  
      // Dibuja la imagen de fondo
      g2d.drawImage(background, 0, 0, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  
    // Configura la fuente y el color para los números
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 56));
  
    // Dibuja los números en la imagen
    for (int fila = 0; fila < 5; fila++) {
      for (int columna = 0; columna < 5; columna++) {
        int numero = matriz[fila][columna];
        String numeroStr = String.valueOf(numero);
        int x = columna * (561 / 5) + 66; // Ajustar la horizontal
        int y = fila * (580 / 5) + 177; // Ajusta la posición vertical
  
        g2d.drawString(numeroStr, x, y);
      }
    }
  
    // Dibuja el identificador abajo
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.PLAIN, 26));
    g2d.drawString(identificador, 271, 720); // Ajusta la posición vertical
  
    g2d.dispose();
  
    try {
      String pngFileName = "proyecto_bingo/cartones/" + identificador + ".png";
      File outputFile = new File(pngFileName);
      outputFile.getParentFile().mkdirs(); // Crea el directorio si no existe
      ImageIO.write(image, "png", outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Obtiene el identificador único de un cartón por su índice.
   *
   * @param pIndice El índice del cartón.
   * @return El identificador único del cartón o nulo si el índice está fuera de rango.
   */
  public static String obtenerIdentificadorPorIndice(int pIndice) {
    if (pIndice >= 0 && pIndice < totalCartones) {
        return "JJO" + String.format("%03d", pIndice);
    }
    return null; // Devuelve nulo si el índice está fuera de rango
  }

  /**
   * Imprime la matriz de marcado del cartón en la consola.
   */
  public void imprimirMatrizMarcado() {
    System.out.println("Matriz de Marcado para el cartón " + identificador + ":");
    for (int fila = 0; fila < 5; fila++) {
        for (int columna = 0; columna < 5; columna++) {
            System.out.print(matrizMarcado[fila][columna] + " ");
        }
        System.out.println(); // Salto de línea para la siguiente fila
    }
  }
  
  // Getters y setters (métodos para acceder y modificar los atributos)

  /**
   * Obtiene el identificador único del cartón.
   *
   * @return El identificador único del cartón.
   */
  public String getIdentificador() {
    return identificador;
  }

  /**
   * Obtiene la matriz de números del cartón.
   *
   * @return La matriz de números del cartón.
   */
  public int[][] getMatriz() {
      return matriz;
  }

  /**
   * Obtiene la matriz de marcado del cartón.
   *
   * @return La matriz de marcado del cartón.
   */
  public int[][] getMatrizMarcado() {
      return matrizMarcado;
  }

  /**
   * Establece un nuevo valor para una casilla en la matriz de marcado del cartón.
   *
   * @param pFila       La fila de la casilla.
   * @param pColumna    La columna de la casilla.
   * @param pNuevoValor El nuevo valor para la casilla.
   */
  public void setValorCasilla(int pFila, int pColumna, int pNuevoValor) {
      matrizMarcado[pFila][pColumna] = pNuevoValor;
  }

  /**
   * Establece el nuevo valor el contador de identificadores
   */

   public static void setCartonId() {
    contadorId = 0;
   }
}
