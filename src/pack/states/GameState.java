package pack.states;

import pack.sound.ExampleSounds;
import pack.entities.EntityWorld;
import pack.entities.manager.EntityManager;
import pack.world.World;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class GameState extends State {

    private World world;
    private long lastRender;
    private ArrayList<Float> fpsHistory;
    private EntityWorld entityWorld;
    private EntityManager entityManager;
//    public static LocalTime localTime1;




    public GameState() {
        //localTime1=LocalTime.now();

        fpsHistory = new ArrayList<>();
        lastRender = -1;

//        ExampleSounds exampleSounds = new ExampleSounds();
        //ExampleSounds.playgameSound1();

        entityManager = new EntityManager();
        if (entityManager == null)
            System.out.println("NULL");
        world = new World("res/world/worldFile.txt");
        entityWorld = new EntityWorld(entityManager);

    }

    @Override
    public void tick() {
        world.tick();
        entityManager.tick();

    }

    @Override
    public void render(Graphics2D g) {

        world.render(g);
        entityManager.render(g);

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
