package pack.states;

import pack.entities.EntityWorld;
import pack.entities.manager.EntityManager;
import pack.world.World;
import java.awt.*;
import java.util.ArrayList;

public class GameState extends State {

    public enum Difficulty {
        EASY, NORMAL, HARD;
    }

    public static float maxPlayerHealth;
    public static int maxPlayerCannon;
    public static int maxPlayerBullet;
    private World world;
    private long lastRender;
    private ArrayList<Float> fpsHistory;
    private EntityWorld entityWorld;
    private EntityManager entityManager;
//    public static LocalTime localTime1;



    public GameState(Difficulty difficulty) {
        //localTime1=LocalTime.now();

        fpsHistory = new ArrayList<>();
        lastRender = -1;

//        ExampleSounds exampleSounds = new ExampleSounds();
        //ExampleSounds.playgameSound1();

        switch (difficulty) {
            case EASY: maxPlayerHealth = 8;
            maxPlayerCannon = 100;
            maxPlayerBullet = 300;
            break;
            case NORMAL: maxPlayerHealth = 6;
            maxPlayerCannon = 60;
            maxPlayerBullet = 200;
            break;
            case HARD: maxPlayerHealth = 4;
            maxPlayerCannon = 40;
            maxPlayerBullet = 100;
        }

        entityManager = new EntityManager();
        world = new World("res/world/worldFile.txt");

        switch (difficulty) {
            case HARD:
                entityWorld = new EntityWorld(entityManager, "res/entityWorld/backgroundWorldHard.txt", "res/entityWorld/foregroundWorld.txt");
                break;
            case NORMAL:
                entityWorld = new EntityWorld(entityManager, "res/entityWorld/backgroundWorldNormal.txt", "res/entityWorld/foregroundWorld.txt");
            case EASY:
                entityWorld = new EntityWorld(entityManager, "res/entityWorld/backgroundWorldEasy.txt", "res/entityWorld/foregroundWorld.txt");
        }

    }

    @Override
    public void tick() {
        entityManager.tick();
    }

    @Override
    public void render(Graphics2D g) {

        world.render(g);
        entityManager.render(g, true);
        entityManager.getServerPlayer().renderPlayerState(g);

        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
                    avg, (currentRender - lastRender));
            g.setColor(Color.CYAN);
            g.setFont(g.getFont().deriveFont(18.0f));
            int strWidth = g.getFontMetrics().stringWidth(str);
            int strHeight = g.getFontMetrics().getHeight();
            g.drawString(str, 900, 70);
        }
        lastRender = currentRender;

    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}