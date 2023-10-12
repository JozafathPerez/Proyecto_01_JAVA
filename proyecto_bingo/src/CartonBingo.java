import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class CartonBingo {
  private String identificador;
  private int[][] matriz;
  private int[][] matrizMarcado;
  private static int contadorId = 0;

  public CartonBingo() {
    matriz = new int[5][5];
    matrizMarcado = new int[5][5];
    identificador = generarIdentificadorUnico();
    generarCarton();
    generarPNG();
  }

  private void generarCarton() {
    for (int columna = 0; columna < 5; columna++) {
      generarNumeroAleatorio(columna);
    }
  }

  private void generarNumeroAleatorio(int columna) {
    int limiteInferior = columna * 15 + 1; 
    int limiteSuperior = (columna + 1) * 15;   

    for (int fila = 0; fila < 5; fila ++) {
      int numero;
      do {
        numero = (int) (Math.random() * (limiteSuperior - limiteInferior + 1)) + limiteInferior; 
      } while (contieneNumeroEnColumna(columna, numero)); 

      matriz[fila][columna] = numero;
    }
  }

  private boolean contieneNumeroEnColumna(int columna, int numero) {
    for (int fila = 0; fila < 5; fila++) {
      if (matriz[fila][columna] == numero) {
        return true; // El número ya existe en la columna
      }
    }
    return false; // El número no existe en la columna
  }

  private String generarIdentificadorUnico() {
    String formatoContador = String.format("%03d", contadorId);
    contadorId++;
    
    return "JJO" + formatoContador; // Joza, Jeff, Oscar
  }

  // Getters y setters (métodos para acceder y modificar los atributos)
  public String getIdentificador() {
    return identificador;
 }

 public int[][] getMatriz() {
    return matriz;
}

 public int[][] getMatrizMarcado() {
    return matrizMarcado;
}

public void generarPNG() {
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
        String pngFileName = "cartones/" + identificador + ".png";
        File outputFile = new File(pngFileName);
        outputFile.getParentFile().mkdirs(); // Crea el directorio si no existe
        ImageIO.write(image, "png", outputFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
