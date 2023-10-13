
public class PruebaBingo {
    public static void main(String[] args) {
        // Crear un juego
        Juego juego = new Juego();

        // Crear cartones
        for (int i = 0; i < 10; i++) {
            CartonBingo carton = new CartonBingo();
            juego.agregarCarton(carton);
        }

        // Agregar jugadores al juego
        //juego.registrarJugador("Oscar Roni", "oroni@estudiantec.cr", "102340756");
        juego.registrarJugador("Jozafath Perez", "jozperez@estudiantec.cr", "703060281");
        juego.registrarJugador("Jefferson Pozo", "jpozo@estudiantec.cr", "706750854");
        

        // Enviar cartones a los jugadores
        //juego.enviarCartonAJugador(5, "102340756"); // Envia 5 cartones al jugador 1
        juego.enviarCartonAJugador(5, "703060281"); // Envia 5 cartones al jugador 2
        juego.enviarCartonAJugador(5, "706750854"); // Envia 5 cartones al jugador 3

        for (int i = 0; i < 10; i++) {
            CartonBingo carton = new CartonBingo();
            juego.agregarCarton(carton);
        }
    }
}
