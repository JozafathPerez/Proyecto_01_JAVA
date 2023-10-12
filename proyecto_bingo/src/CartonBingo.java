import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

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
    
    return "PEN" + formatoContador;
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

private void generarPDF() {
    Document document = new Document();
    try {
        PdfWriter.getInstance(document, new FileOutputStream(identificador + ".pdf"));
        document.open();

        // Crea una tabla para representar el cartón de bingo
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);

        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                PdfPCell cell = new PdfPCell();
                cell.setPhrase(new Phrase(Integer.toString(matriz[fila][columna]), new Font(baseFont, 16)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }
        }

        document.add(table);
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    } finally {
        document.close();
    }
}
}
