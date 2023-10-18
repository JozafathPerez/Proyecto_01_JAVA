import interfazGrafica.Gui;
import logica.Juego;

public class App {
    
    /** 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Juego logica = new Juego();
        logica.cargarJugadoresDesdeCSV();
        new Gui(logica);

        // ESTO ES DE PRU-PRU-PRU-PRUEBAS *sonido de sirenas*
        //logica.crearCartones(5);

        // // Agregar jugadores al juego
        //logica.registrarJugador("Oscar Roni", "oroni@estudiantec.cr", "102340756");
        //logica.registrarJugador("Jozafath Perez", "jozperez@estudiantec.cr", "703060281");
        //logica.registrarJugador("Jefferson Pozo", "pozojefferson410@gmail.com", "1212");
        
        // // Enviar cartones a los jugadores
        //logica.enviarCartonAJugador(1, "102340756"); // Envia 5 cartones al jugador 1
        // logica.enviarCartonAJugador(1, "703060281"); // Envia 5 cartones al jugador 2
        //logica.enviarCartonAJugador(1, "706750854"); // Envia 5 cartones al jugador 3
    }
}
