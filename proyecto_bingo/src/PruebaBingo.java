
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
        juego.registrarJugador("Oscar Roni", "oroni@estudiantec.cr", "102340756");
        juego.registrarJugador("Jozafath Perez", "jozperez@estudiantec.cr", "703060281");
        juego.registrarJugador("Jefferson Pozo", "jpozo@estudiantec.cr", "706750854");
        

        // Enviar cartones a los jugadores
        juego.enviarCartonAJugador(1, "102340756"); // Envia 5 cartones al jugador 1
        juego.enviarCartonAJugador(1, "703060281"); // Envia 5 cartones al jugador 2
        juego.enviarCartonAJugador(1, "706750854"); // Envia 5 cartones al jugador 3


        // Configurar el juego y comenzar
        juego.configurarJuego("Cuatro esquinas", 100.0);


        // Jugar hasta que haya un ganador
        while (!juego.mostrarGanador()) {
            // Canta un número
            juego.cantarNumero();

            // Verifica los cartones
            juego.verificarCartones();
        }

        // Muestra los números cantados
        System.out.println("Números Cantados: " + juego.getNumerosCantados()); // los numero cantados estan en el atributo "numerosCantados" hay que recorrer esta lista y mostrar lo que hy dentro


        // Imprimir ganadores
        for (String identificador : juego.getGanadores()) {
            Jugador ganador = juego.obtenerJugadorPorIdentificadorCarton(identificador);
            if (ganador != null) {
                System.out.println("Ganador: " + ganador.getNombre());
            }
        }
    }
}
