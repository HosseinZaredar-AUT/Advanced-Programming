package pack.states;

import pack.entities.Artillery;
import pack.entities.EntityWorld;
import pack.entities.manager.EntityManager;
import pack.world.World;
import java.awt.*;
import java.util.ArrayList;

public class GameState extends State{

    private World world;

    private long lastRender;
    private ArrayList<Float> fpsHistory;
    private EntityWorld entityWorld;

    public GameState() {
        
        fpsHistory = new ArrayList<>();
        lastRender = -1;

        new EntityManager();
        world = new World("res/world/worldFile.txt");
        entityWorld = new EntityWorld();


    }

    @Override
    public void tick() {
        world.tick();
        EntityManager.tick();

    }

    @Override
    public void render(Graphics2D g) {

        world.render(g);
        EntityManager.render(g);

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
}
