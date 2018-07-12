package pack.entities.manager;

import pack.Game;
import pack.entities.*;
import pack.entities.players.ClientPlayer;
import pack.entities.players.Player;
import pack.entities.players.ServerPlayer;
import pack.graphics.Camera;
import pack.network.Client;
import pack.network.Server;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class EntityManager implements Serializable {

    public boolean gameOver = false;
    private ServerPlayer serverPlayer = null;
    private ArrayList<ClientPlayer> clientPlayers;
    private ArrayList<ClientPlayer> newClientPlayers;

    private ArrayList<HardWall> hardWalls;
    private ArrayList<BarbedWire> barbedWires;
    private ArrayList<SoftWall> softWalls;
    private ArrayList<Cannon> friendlyCannons;
    private ArrayList<Cannon> enemyCannons;
    private ArrayList<Bullet> friendlyBullets;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<BulletFood> bulletFoods;
    private ArrayList<CannonFood> cannonFoods;
    private ArrayList<RepairFood> repairFoods;
    private ArrayList<Upgrader> upgraders;
    private ArrayList<EnemyTank> enemyTanks;
    private ArrayList<EnemyCar> enemyCars;
    private ArrayList<Artillery> artilleries;
    private ArrayList<Mine> mines;
    private ArrayList<Bush> bushes;


    public EntityManager() {
        hardWalls = new ArrayList<>();
        barbedWires = new ArrayList<>();
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
        bushes = new ArrayList<>();
        clientPlayers = new ArrayList<>();
        newClientPlayers = new ArrayList<>();

    }

    //might need to return player
    public void addClientPlayer(int number) {
        ClientPlayer player = new ClientPlayer(this.serverPlayer.getStartX(), this.serverPlayer.getStartY(), number, this);
        newClientPlayers.add(player);
        System.out.println("client added");

    }

    public Player getClosestPlayer(Entity e) {
        Player closestPlayer = serverPlayer;
        for (ClientPlayer cp : clientPlayers) {
            if (Math.pow(cp.getX() - e.getX(), 2) + Math.pow(cp.getY() - e.getY(), 2) < Math.pow(closestPlayer.getX() - e.getX(), 2) + Math.pow(closestPlayer.getY() - e.getY(), 2))
                closestPlayer = cp;
        }
        return closestPlayer;
    }

    public float deltaXToClosestPlayer(Entity e) {
        float deltaX = Math.abs(serverPlayer.getX() - e.getX());
        for (ClientPlayer cp : clientPlayers) {
            if (Math.abs(cp.getX() - e.getX()) < deltaX)
                deltaX = Math.abs(cp.getX() - e.getX());
        }
        return deltaX;
    }

    public float deltaYToClosestPlayer(Entity e) {
        float deltaY = Math.abs(serverPlayer.getY() - e.getY());
        for (ClientPlayer cp : clientPlayers) {
            if (Math.abs(cp.getY() - e.getY()) < deltaY)
                deltaY = Math.abs(cp.getY() - e.getY());
        }
        return deltaY;
    }

    public ServerPlayer getServerPlayer() {
        return serverPlayer;
    }


    //CREATORS
    public void createServerPlayer(float x, float y) {
        serverPlayer = new ServerPlayer(x, y, 0, this);
    }

    public void createEnemyTank(float x, float y) {
        enemyTanks.add(new EnemyTank(x, y, this));
    }


    public void createEnemyCar(float x, float y) {
        enemyCars.add(new EnemyCar(x, y, this));
    }


    public void createArtillery(float x, float y, Artillery.Type type) {
        artilleries.add(new Artillery(x, y, type, this));
    }

    public void createMine(float x, float y) {
        mines.add(new Mine(x, y, this));
    }

    public void createFriendlyCannon(float x, float y, double angle) {
        Cannon cannon = new Cannon(x, y, angle, this);
        friendlyCannons.add(cannon);
    }

    public Cannon createEnemyCannon(float x, float y, double angle) {
        Cannon cannon = new Cannon(x, y, angle, this);
        enemyCannons.add(cannon);
        return cannon;
    }

    public void createFriendlyBullet(float x, float y, double angle) {
        friendlyBullets.add(new Bullet(x, y, angle, this));
    }

    public Bullet createEnemyBullet(float x, float y, double angle) {
        Bullet bullet = new Bullet(x, y, angle, this);
        enemyBullets.add(bullet);
        return bullet;
    }

    public void createHardWall(float x, float y) {
        hardWalls.add(new HardWall(x, y, this));

    }

    public void createBarbedWire(float x, float y) {
        barbedWires.add(new BarbedWire(x, y, this));

    }

    public void createBush(float x, float y) {
        bushes.add(new Bush(x, y, this));

    }

    public void createSoftWall(float x, float y) {
        softWalls.add(new SoftWall(x, y, this));

    }

    public void createBulletFood(float x, float y) {
        bulletFoods.add(new BulletFood(x, y, this));
    }

    public void createCannonFood(float x, float y) {
        cannonFoods.add(new CannonFood(x, y, this));
    }

    public void createRepairFood(float x, float y) {
        repairFoods.add(new RepairFood(x, y, this));
    }

    public void createUpgrader(float x, float y) {
        upgraders.add(new Upgrader(x, y, this));
    }

    //REMOVERS
    public void removeFriendlyCannon(Entity e) {
        friendlyCannons.remove(e);
    }

    public void removeEnemyCannon(Entity e) {
        enemyCannons.remove(e);
    }

    public void removeFriendlyBullet(Entity e) {
        friendlyBullets.remove(e);
    }

    public void removeEnemyBullet(Entity e) {
        enemyBullets.remove(e);
    }

    public void removeMine(Entity e) {
        mines.remove(e);
    }

    public void removeEnemyTank(Entity e) {
        enemyTanks.remove(e);
    }

    public void removeEnemyCar(Entity e) {
        enemyCars.remove(e);
    }

    public void removeSoftWall(Entity e) {
        softWalls.remove(e);
    }

    public void removeBulletFood(Entity e) {
        bulletFoods.remove(e);
    }

    public void removeCannonFood(Entity e) {
        cannonFoods.remove(e);
    }

    public void removeRepairFood(Entity e) {
        repairFoods.remove(e);
    }

    public void removeUpgrader(Entity e) {
        upgraders.remove(e);
    }


    //COLLISION CHECK

    //returns null if it does not collide with anything, else returns the entity which it collides
    public Entity doCollideWithHardWalls(Entity e) {
        for (HardWall w : hardWalls) {
            if (w.getBounds().intersects(e.getBounds()))
                return w;
        }
        return null;
    }

    public Entity doCollideWithBarbedWires(Entity e) {
        for (BarbedWire b : barbedWires) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;
    }

    public Entity doCollideWithSoftWalls(Entity e) {
        for (SoftWall w : softWalls) {
            if (w.getBounds().intersects(e.getBounds()))
                return w;
        }
        return null;
    }

    public Entity doCollideWithPlayer(Entity e) {
        if (serverPlayer.getBounds().intersects(e.getBounds()))
            return serverPlayer;
        for (ClientPlayer cp : clientPlayers) {
            if (cp.getBounds().intersects(e.getBounds()))
                return cp;
        }
        return null;
    }

    public Cannon doCollideWithFriendlyCannon(Entity e) {
        for (Cannon b : friendlyCannons) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;

    }

    public Cannon doCollideWithEnemyCannon(Entity e) {
        for (Cannon b : enemyCannons) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;

    }

    public Bullet doCollideWithFriendlyBullet(Entity e) {
        for (Bullet b : friendlyBullets) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;
    }

    public Bullet doCollideWithEnemyBullet(Entity e) {
        for (Bullet b : enemyBullets) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;
    }

    public BulletFood doCollideWithBulletFood(Entity e) {
        for (BulletFood b : bulletFoods) {
            if (b.getBounds().intersects(e.getBounds()))
                return b;
        }
        return null;
    }

    public CannonFood doCollideWithCannonFood(Entity e) {
        for (CannonFood c : cannonFoods) {
            if (c.getBounds().intersects(e.getBounds()))
                return c;
        }
        return null;
    }

    public RepairFood doCollideWithRepairFood(Entity e) {
        for (RepairFood r : repairFoods) {
            if (r.getBounds().intersects(e.getBounds()))
                return r;
        }
        return null;
    }

    public Upgrader doCollideWithUpgrader(Entity e) {
        for (Upgrader u : upgraders) {
            if (u.getBounds().intersects(e.getBounds()))
                return u;
        }
        return null;
    }

    public Mine doCollideWithMine(Entity e) {
        for (Mine m : mines) {
            if (m.getBounds().intersects(e.getBounds()))
                return m;
        }
        return null;
    }

    public Artillery doCollideWithArtillery(Entity e) {
        for (Artillery a : artilleries) {
            if (a.getBounds().intersects(e.getBounds()))
                return a;
        }
        return null;
    }

    public EnemyTank doCollideWithEnemyTank(Entity e) {
        for (EnemyTank en : enemyTanks) {
            if (en.getBounds().intersects(e.getBounds()) && !en.equals(e))

                return en;
        }
        return null;
    }

    public EnemyCar doCollideWithEnemyCar(Entity e) {
        for (EnemyCar en : enemyCars) {

            if (en.getBounds().intersects(e.getBounds()) && !en.equals(e))
                return en;
        }
        return null;
    }


    //TICK AND RENDER
    public void tick() {

        addNewClientPlayers();

        if (clientPlayers.size() > 0) {
            for (ClientPlayer cp : clientPlayers)
                cp.tick();

        }

        serverPlayer.tick();

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


        //REMOVING GONE CLIENTS
        Iterator<ClientPlayer> it = clientPlayers.iterator();
        while (it.hasNext()) {
            ClientPlayer current = it.next();
            if (current.hasLeft)
                it.remove();
        }

        if (clientPlayers.size() > 0) {
            for (ClientPlayer cp : clientPlayers) {

                OutputStream out = Server.getOutputStream(cp.getNumber());
                try {
                    ObjectOutputStream objectOut = new ObjectOutputStream(out);
                    objectOut.writeObject(this);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void render(Graphics2D g) {


        for (HardWall w : hardWalls) {
            if ((w.getX() + w.getWidth() > Camera.getXOffset()) && (w.getX() < (Camera.getXOffset() + Game.frameWidth)) && (w.getY() + w.getHeight() > Camera.getYOffset())
                    && (w.getY() < (Camera.getYOffset() + Game.frameHeight)))
                w.render(g);
        }

        for (BarbedWire b : barbedWires) {
            if ((b.getX() + b.getWidth() > Camera.getXOffset()) && (b.getX() < (Camera.getXOffset() + Game.frameWidth)) && (b.getY() + b.getHeight() > Camera.getYOffset())
                    && (b.getY() < (Camera.getYOffset() + Game.frameHeight)))
                b.render(g);
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


        serverPlayer.render(g);

        if (clientPlayers.size() > 0) {
            for (ClientPlayer cp : clientPlayers) {

                cp.render(g);
            }

        }

        for (Bush b : bushes) {
            if ((b.getX() + b.getWidth() > Camera.getXOffset()) && (b.getX() < (Camera.getXOffset() + Game.frameWidth)) && (b.getY() + b.getHeight() > Camera.getYOffset())
                    && (b.getY() < (Camera.getYOffset() + Game.frameHeight)))
            b.render(g);
        }

    }


    private void addNewClientPlayers() {
        clientPlayers.addAll(newClientPlayers);
        newClientPlayers.clear();
    }


}
