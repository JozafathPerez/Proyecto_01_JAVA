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

  }

  private void generarNumeroAleatorio(int columna) {
    
  }

  private boolean contieneNumeroEnColumna(int columna, int numero) {
    return false;
  }

  private String generarIdentificadorUnico() {
    // Implementa la lógica para generar un identificador único
    // Debe ser 3 letras y 3 numeros
    return "PENE" + (int) (Math.random() * 1000);
  }
}
