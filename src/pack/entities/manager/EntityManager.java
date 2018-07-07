package pack.entities.manager;

import pack.entities.*;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private static Player player;
    private static ArrayList<HardWall> hardWalls;
    private static ArrayList<SoftWall> softWalls;
    private static ArrayList<Bullet> bullets;

    public EntityManager() {
        hardWalls = new ArrayList<>();
        softWalls = new ArrayList<>();
        bullets = new ArrayList<>();

    }

    //CREATORS
    public static void createPlayer(float x, float y) {
        player = new Player(x, y);
    }

    public static void createFire(float x, float y, double angle) {
        bullets.add(new Bullet(x, y, angle));
    }

    public static void createHardWall(float x, float y) {
        hardWalls.add(new HardWall(x, y));

    }

    public static void createSoftWall(float x, float y) {
        softWalls.add(new SoftWall(x, y));

    }

    //REMOVERS


    public static void removeBullet(Entity e) {
        bullets.remove(e);

    }

    public static void removeSoftWall(Entity e) {
        softWalls.remove(e);

    }


    //COLLISION CHECK

    //returns null if it does not collide with anything, else returns the entity which it collides
    public static Entity doCollideWithHardWalls(Entity e) {
        for (HardWall w : hardWalls) {
            if (w.getBounds().intersects(e.getBounds()))
                return w;
        }
        return null;
    }

    public static Entity doCollideWithSoftWalls(Entity e) {
        for (SoftWall w : softWalls) {
            if (w.getBounds().intersects(e.getBounds()))
                return w;
        }
        return null;
    }

    public static Entity doCollideWithPlayer(Entity e) {
        return null;

    }

    public static Entity doCollideWithBullet(Entity e) {
        for (Bullet b : bullets) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;

    }

    //TICK AND RENDER
    public void tick() {
        player.tick();

        for (HardWall w : hardWalls)
            w.tick();

        //CURRENT MODIFICATION EXCEPTION HANDLING
        try {
            for (SoftWall w : softWalls)
                w.tick();

        } catch (Exception ex) {
        }

        for (Bullet f : bullets)
            f.tick();

    }

    public void render(Graphics2D g) {
        for (HardWall w : hardWalls)
            w.render(g);

        for (SoftWall w : softWalls)
            w.render(g);

        for (Bullet f : bullets)
            f.render(g);

        player.render(g);
    }







}
