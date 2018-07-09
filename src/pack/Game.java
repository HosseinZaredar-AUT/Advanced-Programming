package pack;

import pack.graphics.Assets;
import pack.input.KeyManager;
import pack.input.MouseManager;
import pack.states.GameState;
import pack.states.State;
import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {


    private final int FPS = 90;

    private GameFrame frame;
    private State state;


    public static  int frameWidth, frameHeight;


    public Game(int width, int height) {
        frameWidth = width;
        frameHeight = height;
        init();

    }

    private void init() {
        frame = new GameFrame("JTanks");
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
        state = new GameState();
    }

    public GameFrame getFrame() {
        return frame;
    }

    public void start() {
        ThreadPool.execute(this);
    }

    private void tick() {
        state.tick();
    }

    private void render() {

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

        boolean gameOver = false;
        double timePerTick = 1000000000 / FPS;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;

        while (!gameOver) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                delta--;
            }

            if(timer >= 1000000000)
                timer = 0;

        }

    }
}
