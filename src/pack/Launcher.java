package pack;

import pack.Sound.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
