package pack.states;

import pack.entities.manager.EntityManager;
import pack.world.World;
import java.awt.*;

public class GameState extends State{

    private EntityManager entityManager;
    private World world;

    public GameState() {
        world= new World("res/world/worldFile.txt");
        entityManager = new EntityManager();

        //TEST
        entityManager.createPlayer(10, 10);
        entityManager.createHardWall(1000, 100);
        entityManager.createHardWall(1000, 200);
        entityManager.createHardWall(1000, 300);
        entityManager.createHardWall(1000, 400);
        entityManager.createHardWall(1000, 500);
        entityManager.createHardWall(900, 500);
        entityManager.createHardWall(800, 500);
        entityManager.createHardWall(700, 500);
        entityManager.createSoftWall(700, 600);
        entityManager.createSoftWall(700, 700);
        entityManager.createSoftWall(700, 800);
        entityManager.createSoftWall(700, 900);


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

    }
}
