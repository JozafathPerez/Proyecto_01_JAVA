
public class PruebaCartonBingo {
    public static void main(String[] args) {
        int cantidadCartones = 10; // Cantidad de cartones a generar

        for (int i = 0; i < cantidadCartones; i++) {
            CartonBingo carton = new CartonBingo();
            System.out.println("Identificador del Cartón: " + carton.getIdentificador());
            mostrarCarton(carton);
            mostrarMatrizMarcado(carton);
            System.out.println(); // Espacio en blanco para separar los cartones
        }
    }

    // Método para mostrar los números en un cartón
    private static void mostrarCarton(CartonBingo carton) {
        int[][] matriz = carton.getMatriz();
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                System.out.print(matriz[fila][columna] + "\t");
            }
            System.out.println(); // Cambio de fila
        }
    }

        // Método para mostrar los números en un cartón
    private static void mostrarMatrizMarcado(CartonBingo carton) {
        int[][] matrizM = carton.getMatrizMarcado();
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                System.out.print(matrizM[fila][columna] + "\t");
            }
            System.out.println(); // Cambio de fila
        }
    }
}
