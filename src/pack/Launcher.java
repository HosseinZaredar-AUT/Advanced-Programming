package pack;

/**
 * Program start.
 */
public class Launcher {

    public static void main(String[] args) {
        ThreadPool.init();

        Game game = new Game(1280, 720);
        game.start();

    }
}
