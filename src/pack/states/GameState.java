package pack.states;

import pack.entities.manager.EntityManager;
import pack.world.World;
import java.awt.*;
import java.util.ArrayList;

public class GameState extends State{

    private EntityManager entityManager;
    private World world;

    private long lastRender;
    private ArrayList<Float> fpsHistory;

    public GameState() {
        
        fpsHistory = new ArrayList<>();
        lastRender = -1;
        
        world= new World("res/world/worldFile.txt");
        entityManager = new EntityManager();

        //TEST
        entityManager.createPlayer(10, 10);

        entityManager.createHardWall(1000, 200);
        entityManager.createHardWall(1000, 300);
        entityManager.createHardWall(1000, 400);
        entityManager.createHardWall(1000, 500);



        entityManager.createSoftWall(700, 600);
        entityManager.createSoftWall(700, 700);
        entityManager.createSoftWall(700, 800);
        entityManager.createSoftWall(700, 900);


        //entityManager.createEnemy(1190, 400);
        entityManager.createEnemySimple(400, 1800);

//        entityManager.createBulletFood(1000, 1000);
//        entityManager.createBulletFood(1250, 1000);
//        entityManager.createCannonFood(100, 1000);
//        entityManager.createCannonFood(250, 1000);

//        entityManager.createUpgrader(200 ,1400);
//        entityManager.createUpgrader(200 ,1600);
//        entityManager.createUpgrader(700 ,1400);
//
//        entityManager.createRepairFood(1700, 1800 );

        entityManager.createArtillery(1900, 1000);

//        entityManager.createEnemy(1190, 400);



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
}
