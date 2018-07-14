package pack;

import pack.graphics.Assets;
import pack.input.KeyManager;
import pack.input.MouseManager;
import pack.states.MainMenuState;
import pack.states.State;
import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {


    private final int FPS = 60;
    private GameFrame frame;
    private static State state;


    public static int frameWidth, frameHeight;


    public Game(int width, int height) {
        frameWidth = width;
        frameHeight = height;
        init();

    }

    private void init() {
        frame = new GameFrame("JTanks");
        //to make it fullscreen
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.initBufferStrategy();

        KeyManager keyManager = new KeyManager();
        frame.addKeyListener(keyManager);
        MouseManager mouseManager = new MouseManager();
        frame.addMouseMotionListener(mouseManager);
        frame.addMouseListener(mouseManager);

        Assets.init();

    }

    public GameFrame getFrame() {
        return frame;
    }

    public void start() {

        state = new MainMenuState();
        ThreadPool.execute(this);
    }

    private void tick() {
        state.tick();
    }

    private void render() {

        if (frame.getBufferStrategy() == null)
            frame.initBufferStrategy();
        Graphics2D g = (Graphics2D) frame.getBufferStrategy().getDrawGraphics();

        try {
            g.clearRect(0, 0, frameWidth, frameHeight);

            state.render(g);

        } finally {
            g.dispose();
        }
        frame.getBufferStrategy().show();
        Toolkit.getDefaultToolkit().sync();
    }



    @Override
    public void run() {

        double timePerTick = 1000000000 / FPS;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;

        while (true){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                delta--;
            }

            if(timer >= 1000000000) {
                timer = 0;
            }

        }
    }

    public static void setState(State newState) {
        state = newState;
    }

}
