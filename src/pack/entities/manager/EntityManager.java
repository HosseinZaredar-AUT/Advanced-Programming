package pack.entities.manager;

import pack.Game;
import pack.entities.*;
import pack.graphics.Camera;
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
    private static ArrayList<EnemyTank> enemyTanks;
    private static ArrayList<EnemyCar> enemyCars;
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
        enemyTanks = new ArrayList<>();
        enemyCars = new ArrayList<>();
        artilleries = new ArrayList<>();
        mines = new ArrayList<>();

    }

    //CREATORS
    public static void createPlayer(float x, float y) {
        player = new Player(x, y);
    }

    public static void createEnemyTank(float x, float y) {
        enemyTanks.add(new EnemyTank(x, y));
    }

    public static void createEnemyCar(float x, float y) {
        enemyCars.add(new EnemyCar(x, y));
    }


    public static void createArtillery(float x, float y, Artillery.Type type) {
        artilleries.add(new Artillery(x, y, type));
    }

    public static void createMine(float x, float y) {
        mines.add(new Mine(x, y));
    }

    public static void createFriendlyCannon(float x, float y, double angle) {
        Cannon cannon = new Cannon(x, y, angle);
        friendlyCannons.add(cannon);
    }

    public static Cannon createEnemyCannon(float x, float y, double angle) {
        Cannon cannon = new Cannon(x, y, angle);
        enemyCannons.add(cannon);
        return cannon;
    }

    public static void createFriendlyBullet(float x, float y, double angle) {
        friendlyBullets.add(new Bullet(x, y, angle));
    }

    public static Bullet createEnemyBullet(float x, float y, double angle) {
        Bullet bullet = new Bullet(x, y, angle);
        enemyBullets.add(bullet);
        return bullet;
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

    public static void removeEnemyTank(Entity e) {
        enemyTanks.remove(e);
    }

    public static void removeEnemyCar(Entity e) {
        enemyCars.remove(e);
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
        if (player.getBounds().intersects(e.getBounds()))
            return player;
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

    public static EnemyTank doCollideWithEnemyTank(Entity e) {
        for (EnemyTank en : enemyTanks) {
            if (en.getBounds().intersects(e.getBounds()) && !en.equals(e))
                return en;
        }
        return null;
    }

    public static EnemyCar doCollideWithEnemyCar(Entity e) {
        for (EnemyCar en : enemyCars) {
            if (en.getBounds().intersects(e.getBounds()))
                return en;
        }
        return null;
    }


    //GETTERS
    public static Player getPlayer() {
        return player;
    }


    //TICK AND RENDER
    public static void tick() {

        player.tick();

        try {
            for (EnemyTank e : enemyTanks)
                e.tick();

        } catch (Exception ex) {
        }


        try {
            for (EnemyCar e : enemyCars)
                e.tick();

        } catch (Exception ex) {
        }



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

    public static void render(Graphics2D g) {


        for (HardWall w : hardWalls) {
            if ((w.getX() + w.getWidth() > Camera.getXOffset()) && (w.getX() < (Camera.getXOffset() + Game.frameWidth)) && (w.getY() + w.getHeight() > Camera.getYOffset())
                    && (w.getY() < (Camera.getYOffset() + Game.frameHeight)))
            w.render(g);
        }



        for (SoftWall w : softWalls) {
            if ((w.getX() + w.getWidth() > Camera.getXOffset()) && (w.getX() < (Camera.getXOffset() + Game.frameWidth)) && (w.getY() + w.getHeight() > Camera.getYOffset())
                    && (w.getY() < (Camera.getYOffset() + Game.frameHeight)))
            w.render(g);
        }

        for (Cannon f : friendlyCannons) {
            if ((f.getX() + f.getWidth() > Camera.getXOffset()) && (f.getX() < (Camera.getXOffset() + Game.frameWidth)) && (f.getY() + f.getHeight() > Camera.getYOffset())
                    && (f.getY() < (Camera.getYOffset() + Game.frameHeight)))
            f.render(g);
        }

        for (Cannon f : enemyCannons) {
            if ((f.getX() + f.getWidth() > Camera.getXOffset()) && (f.getX() < (Camera.getXOffset() + Game.frameWidth)) && (f.getY() + f.getHeight() > Camera.getYOffset())
                    && (f.getY() < (Camera.getYOffset() + Game.frameHeight)))
            f.render(g);
        }

        for (Bullet b : friendlyBullets) {
            if ((b.getX() + b.getWidth() > Camera.getXOffset()) && (b.getX() < (Camera.getXOffset() + Game.frameWidth)) && (b.getY() + b.getHeight() > Camera.getYOffset())
                    && (b.getY() < (Camera.getYOffset() + Game.frameHeight)))
            b.render(g);
        }

        for (Bullet b : enemyBullets) {
            if ((b.getX() + b.getWidth() > Camera.getXOffset()) && (b.getX() < (Camera.getXOffset() + Game.frameWidth)) && (b.getY() + b.getHeight() > Camera.getYOffset())
                    && (b.getY() < (Camera.getYOffset() + Game.frameHeight)))
            b.render(g);
        }

        for (BulletFood b : bulletFoods) {
            if ((b.getX() + b.getWidth() > Camera.getXOffset()) && (b.getX() < (Camera.getXOffset() + Game.frameWidth)) && (b.getY() + b.getHeight() > Camera.getYOffset())
                    && (b.getY() < (Camera.getYOffset() + Game.frameHeight)))
            b.render(g);
        }

        for (CannonFood c : cannonFoods) {
            if ((c.getX() + c.getWidth() > Camera.getXOffset()) && (c.getX() < (Camera.getXOffset() + Game.frameWidth)) && (c.getY() + c.getHeight() > Camera.getYOffset())
                    && (c.getY() < (Camera.getYOffset() + Game.frameHeight)))
            c.render(g);
        }

        for (RepairFood r : repairFoods) {
            if ((r.getX() + r.getWidth() > Camera.getXOffset()) && (r.getX() < (Camera.getXOffset() + Game.frameWidth)) && (r.getY() + r.getHeight() > Camera.getYOffset())
                    && (r.getY() < (Camera.getYOffset() + Game.frameHeight)))
            r.render(g);
        }

        for (Upgrader u : upgraders) {
            if ((u.getX() + u.getWidth() > Camera.getXOffset()) && (u.getX() < (Camera.getXOffset() + Game.frameWidth)) && (u.getY() + u.getHeight() > Camera.getYOffset())
                    && (u.getY() < (Camera.getYOffset() + Game.frameHeight)))
            u.render(g);
        }

        for (Mine m : mines) {
            if ((m.getX() + m.getWidth() > Camera.getXOffset()) && (m.getX() < (Camera.getXOffset() + Game.frameWidth)) && (m.getY() + m.getHeight() > Camera.getYOffset())
                    && (m.getY() < (Camera.getYOffset() + Game.frameHeight)))
            m.render(g);
        }

        for (EnemyTank e : enemyTanks) {
            if ((e.getX() + e.getWidth() > Camera.getXOffset()) && (e.getX() < (Camera.getXOffset() + Game.frameWidth)) && (e.getY() + e.getHeight() > Camera.getYOffset())
                    && (e.getY() < (Camera.getYOffset() + Game.frameHeight)))
            e.render(g);
        }

        for (EnemyCar e : enemyCars) {
            if ((e.getX() + e.getWidth() > Camera.getXOffset()) && (e.getX() < (Camera.getXOffset() + Game.frameWidth)) && (e.getY() + e.getHeight() > Camera.getYOffset())
                    && (e.getY() < (Camera.getYOffset() + Game.frameHeight)))
            e.render(g);
        }

        for (Artillery a : artilleries) {
            if ((a.getX() + a.getWidth() > Camera.getXOffset()) && (a.getX() < (Camera.getXOffset() + Game.frameWidth)) && (a.getY() + a.getHeight() > Camera.getYOffset())
                    && (a.getY() < (Camera.getYOffset() + Game.frameHeight)))
                a.render(g);
        }


        player.render(g);


    }


}
