package pack.entities.manager;

import pack.entities.*;
import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private static Player player;
    private static ArrayList<HardWall> hardWalls;
    private static ArrayList<SoftWall> softWalls;
    private static ArrayList<Cannon> cannons;
    private static ArrayList<Bullet> bullets;
    private static ArrayList<BulletFood> bulletFoods;
    private static ArrayList<CannonFood> cannonFoods;
    private static ArrayList<Upgrader> upgraders;
    private static ArrayList<Enemy> enemies;
    private static ArrayList<EnemySimple> simpleEnemy;


    public EntityManager() {
        hardWalls = new ArrayList<>();
        softWalls = new ArrayList<>();
        cannons = new ArrayList<>();
        bullets = new ArrayList<>();
        bulletFoods = new ArrayList<>();
        cannonFoods = new ArrayList<>();
        upgraders = new ArrayList<>();
        enemies = new ArrayList<>();
simpleEnemy = new ArrayList<>();

    }

    //CREATORS
    public static void createPlayer(float x, float y) {
        player = new Player(x, y);
    }

    public static void createEnemy(float x, float y) {
        enemies.add(new Enemy(x, y,player));
    }


    public static void createEnemySimple(float x, float y) {
        simpleEnemy.add(new EnemySimple(x, y,player));
    }

    public static Cannon createCannon(float x, float y, double angle) {
        Cannon cannon = new Cannon(x, y, angle);
        cannons.add(cannon);
        return cannon;
    }

    public static void createBullet(float x, float y, double angle) {
        bullets.add(new Bullet(x, y, angle));
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

    public static void createUpgrader(float x, float y) {
        upgraders.add(new Upgrader(x, y));
    }

    //REMOVERS

    public static void removeCannon(Entity e) {
        cannons.remove(e);

    }

    public static void removeBullet(Entity e) {
        bullets.remove(e);

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
        if (player.getBounds().intersects(e.getBounds()))
            return player;
        return null;

    }

    public static Cannon doCollideWithCannon(Entity e) {
        for (Cannon b : cannons) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;

    }

    public static Bullet doCollideWithBullet(Entity e) {
        for (Bullet b : bullets) {
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

    public static Entity doCollideWithAllEnemies(Entity e) {
        for (Enemy c : enemies) {
            if (c.getBounds().intersects(e.getBounds()))
                return c;
        }
        for (EnemySimple c : simpleEnemy) {
            if (c.getBounds().intersects(e.getBounds()))
                return c;
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

    //TICK AND RENDER
    public void tick() {
        player.tick();

        for (Enemy e : enemies)
            e.tick();

        for (EnemySimple e : simpleEnemy)
            e.tick();

        for (HardWall w : hardWalls)
            w.tick();

        //CURRENT MODIFICATION EXCEPTION HANDLING
        try {
            for (SoftWall w : softWalls)
                w.tick();

        } catch (Exception ex) {
        }

        for (Cannon f : cannons)
            f.tick();

        for (Bullet b : bullets)
            b.tick();

    }

    public void render(Graphics2D g) {
        for (HardWall w : hardWalls)
            w.render(g);

        for (SoftWall w : softWalls)
            w.render(g);

        for (Cannon f : cannons)
            f.render(g);

        for (Bullet b : bullets)
            b.render(g);

        for (BulletFood b : bulletFoods)
            b.render(g);

        for (CannonFood c : cannonFoods)
            c.render(g);

        for (Upgrader u : upgraders)
            u.render(g);

        for (Enemy e : enemies)
            e.render(g);

        for (EnemySimple e : simpleEnemy)
            e.render(g);

        player.render(g);

    }


}
