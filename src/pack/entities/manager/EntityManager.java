package pack.entities.manager;

import pack.entities.*;
import pack.graphics.Assets;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private static Player player;
    private static ArrayList<HardWall> hardWalls;
    private static ArrayList<SoftWall> softWalls;
    private static ArrayList<Cannon> friendlyCannons;
    private static ArrayList<Cannon> enemyCannons;
    private static ArrayList<Bullet> friendlyBullets;
    private static ArrayList<Bullet> enemyBullets;
    private static ArrayList<BulletFood> bulletFoods;
    private static ArrayList<CannonFood> cannonFoods;
    private static ArrayList<RepairFood> repairFoods;
    private static ArrayList<Upgrader> upgraders;
    private static ArrayList<Enemy> enemies;
    private static ArrayList<Artillery> artilleries;
    private static ArrayList<Mine> mines;



    public EntityManager() {
        hardWalls = new ArrayList<>();
        softWalls = new ArrayList<>();
        friendlyBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        friendlyCannons = new ArrayList<>();
        enemyCannons = new ArrayList<>();
        bulletFoods = new ArrayList<>();
        cannonFoods = new ArrayList<>();
        repairFoods = new ArrayList<>();
        upgraders = new ArrayList<>();
        enemies = new ArrayList<>();
        artilleries = new ArrayList<>();
        mines = new ArrayList<>();


    }


    //DELTA
    public static float deltaXWithPlayer(Entity e) {
        return e.getX() - player.getX();
    }

    public static float deltaYWithPlayer(Entity e) {
        return  e.getY() - player.getY();
    }


    //CREATORS
    public static void createPlayer(float x, float y) {
        player = new Player(x, y);
    }

    public static void createEnemy(float x, float y) {
        enemies.add(new Enemy(x, y, player));
    }

    public static void createArtillery(float x, float y) {
        artilleries.add(new Artillery(x, y));
    }

    public static void createMine(float x, float y) {
        mines.add(new Mine(x, y));
    }

    public static Cannon createFriendlyCannon(float x, float y, double angle) {
        Cannon cannon = new Cannon(x, y, angle);
        friendlyCannons.add(cannon);
        return cannon;
    }

    public static Cannon createEnemyCannon(float x, float y, double angle) {
        Cannon cannon = new Cannon(x, y, angle);
        enemyCannons.add(cannon);
        return cannon;
    }

    public static void createFriendlyBullet(float x, float y, double angle) {
        friendlyBullets.add(new Bullet(x, y, angle));
    }

    public static void createEnemyBullet(float x, float y, double angle) {
        enemyBullets.add(new Bullet(x, y, angle));
    }

    public static void createHardWall(float x, float y) {
        hardWalls.add(new HardWall(x, y));

    }

    public static void createSoftWall(float x, float y) {
        softWalls.add(new SoftWall(x, y));

    }

    public static void createBulletFood(float x, float y) {
        bulletFoods.add(new BulletFood(x, y));
    }

    public static void createCannonFood(float x, float y) {
        cannonFoods.add(new CannonFood(x, y));
    }

    public static void createRepairFood(float x, float y) {
        repairFoods.add(new RepairFood(x, y));
    }

    public static void createUpgrader(float x, float y) {
        upgraders.add(new Upgrader(x, y));
    }

    //REMOVERS
    public static void removeFriendlyCannon(Entity e) {
        friendlyCannons.remove(e);
    }

    public static void removeEnemyCannon(Entity e) {
        enemyCannons.remove(e);
    }

    public static void removeFriendlyBullet(Entity e) {
        friendlyBullets.remove(e);
    }

    public static void removeEnemyBullet(Entity e) {
        enemyBullets.remove(e);
    }

    public static void removeMine(Entity e) {
        mines.remove(e);
    }

    public static void removeSoftWall(Entity e) {
        softWalls.remove(e);
    }

    public static void removeBulletFood(Entity e) {
        bulletFoods.remove(e);
    }

    public static void removeCannonFood(Entity e) {
        cannonFoods.remove(e);
    }

    public static void removeRepairFood(Entity e) {
        repairFoods.remove(e);
    }

    public static void removeUpgrader(Entity e) {
        upgraders.remove(e);
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

    public static Cannon doCollideWithFriendlyCannon(Entity e) {
        for (Cannon b : friendlyCannons) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;

    }

    public static Cannon doCollideWithEnemyCannon(Entity e) {
        for (Cannon b : enemyCannons) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;

    }

    public static Bullet doCollideWithFriendlyBullet(Entity e) {
        for (Bullet b : friendlyBullets) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;
    }

    public static Bullet doCollideWithEnemyBullet(Entity e) {
        for (Bullet b : enemyBullets) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;
    }

    public static BulletFood doCollideWithBulletFood(Entity e) {
        for (BulletFood b : bulletFoods) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;
    }

    public static CannonFood doCollideWithCannonFood(Entity e) {
        for (CannonFood c : cannonFoods) {
            if (c.getBounds().intersects(e.getBounds()))
                return c;
        }
        return null;
    }

    public static RepairFood doCollideWithRepairFood(Entity e) {
        for (RepairFood r : repairFoods) {
            if (r.getBounds().intersects(e.getBounds()))
                return r;
        }
        return null;
    }

    public static Upgrader doCollideWithUpgrader(Entity e) {
        for (Upgrader u : upgraders) {
            if (u.getBounds().intersects(e.getBounds()))
                return u;
        }
        return null;
    }

    public static Mine doCollideWithMine(Entity e) {
        for (Mine m : mines) {
            if (m.getBounds().intersects(e.getBounds()))
                return m;
        }
        return null;
    }

    public static Artillery doCollideWithArtillery(Entity e) {
        for (Artillery a : artilleries) {
            if (a.getBounds().intersects(e.getBounds()))
                return a;
        }
        return null;
    }

    //TICK AND RENDER
    public void tick() {
        player.tick();

        for (Enemy e : enemies)
            e.tick();

        for (Artillery a : artilleries)
            a.tick();

        for (Artillery a : artilleries)
            a.tick();

        for (HardWall w : hardWalls)
            w.tick();

        //CURRENT MODIFICATION EXCEPTION HANDLING
        try {
            for (SoftWall w : softWalls)
                w.tick();

        } catch (Exception ex) {
        }

        for (Cannon f : friendlyCannons)
            f.tick();

        for (Cannon f : enemyCannons)
            f.tick();

        for (Bullet b : friendlyBullets)
            b.tick();

        for (Bullet b : enemyBullets)
            b.tick();

    }

    public void render(Graphics2D g) {


        for (HardWall w : hardWalls)
            w.render(g);

        for (SoftWall w : softWalls)
            w.render(g);

        for (Cannon f : friendlyCannons)
            f.render(g);

        for (Cannon f : enemyCannons)
            f.render(g);

        for (Bullet b : friendlyBullets)
            b.render(g);

        for (Bullet b : enemyBullets)
            b.render(g);

        for (BulletFood b : bulletFoods)
            b.render(g);

        for (CannonFood c : cannonFoods)
            c.render(g);

        for (RepairFood r : repairFoods)
            r.render(g);

        for (Upgrader u : upgraders)
            u.render(g);

        for (Mine m : mines)
            m.render(g);

        for (Enemy e : enemies)
            e.render(g);

        for (Artillery a : artilleries)
            a.render(g);


        player.render(g);


    }


}
