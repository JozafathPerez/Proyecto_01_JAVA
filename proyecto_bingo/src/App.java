import interfazGrafica.Gui;
import logica.Juego;

public class App {
    public static void main(String[] args) throws Exception {
        Juego juegoLogica = new Juego();
        new Gui(juegoLogica);
    }
}
