package pack.entities.manager;

import pack.entities.*;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private static Player player;
    private static ArrayList<HardWall> hardWalls;
    private static ArrayList<Fire> fires;

    public EntityManager() {
        hardWalls = new ArrayList<>();
        fires = new ArrayList<>();

    }

    //CREATORS
    public static void createPlayer(float x, float y) {
        player = new Player(x, y);
    }

    public static void createFire() {


    }

    public static void createHardWall(float x, float y) {
        hardWalls.add(new HardWall(x, y));

    }

    //REMOVERS
    public static void removeWall(Entity e) {

    }

    public static void removeFire(Entity e) {

    }


    //COLLISION CHECK

    //returns null if it does not collide with anything, else returns the entity which it collides
    public static Entity doCollideWithWalls(Entity e) {
        for (HardWall w : hardWalls) {
            if (w.getBounds().intersects(e.getBounds()))
                return w;
        }
        return null;
    }

    public static Entity doCollideWithPlayer(Entity e) {
        return null;

    }

    public static Entity doCollideWithFire(Entity e) {
        return null;

    }

    //TICK AND RENDER
    public void tick() {
        player.tick();
    }

    public void render(Graphics2D g) {
        for (HardWall w : hardWalls)
            w.render(g);
        player.render(g);
    }







}
