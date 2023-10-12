public class CartonBingo {
  private String identificador;
  private int[][] matriz;
  private int[][] matrizMarcado;

  public CartonBingo() {
    matriz = new int[5][5];
    matrizMarcado = new int[5][5];
    identificador = generarIdentificadorUnico();
    generarCarton();
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
    // Implementa la lógica para generar un identificador único
    // Debe ser 3 letras y 3 numeros
    return "PENE" + (int) (Math.random() * 1000); 
  }

  public String getIdentificador() {
    return identificador;
 }

 public int[][] getMatriz() {
    return matriz;
}

 public int[][] getMatrizMarcado() {
    return matrizMarcado;
}
}
