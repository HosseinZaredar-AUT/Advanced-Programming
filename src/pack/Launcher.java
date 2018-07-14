package pack;

import java.awt.*;

/**
 * Program start.
 */
public class Launcher {

    public static void main(String[] args) {
        ThreadPool.init();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //pass x and y to have fullscreen
        Game game = new Game(1280, 720);
        game.start();

    }
}
