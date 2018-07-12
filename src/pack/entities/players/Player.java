package pack.entities.players;

import pack.entities.Entity;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    protected int number;
    protected boolean up, down, right, left;
    protected int degree;
    protected double degreeGun;
    protected float xMove, yMove;
    protected final int SPEED = 20;
    protected float xSpeed;
    protected float ySpeed;

    protected boolean canShoot = true;
    protected double timeStart;
    protected double timeEnd;

    protected int gunState; //1.Cannon, -1.Bullet
    protected double cannonRate = 1; //the less, the faster
    protected int cannonLevel = 0;
    protected int bulletRate = 6; //the less, the faster
    protected int bulletLevel = 0;
    protected int bulletCounter = 0;


    protected final float MAX_HEALTH = 25;
    protected float health;

    protected final int MAX_CANNON = 50;
    protected int cannon;

    protected final int MAX_BULLET = 200;
    protected int bullet;


    public float getXMove() {
        return xMove;
    }

    public float getYMove() {
        return yMove;
    }

    public Player(float x, float y, int number, EntityManager entityManager) {
        super(x, y, 100, 100, entityManager);
        this.number = number;
        gunState = 1;
        health = MAX_HEALTH;
        bullet = MAX_BULLET;
        cannon = MAX_CANNON;
        xSpeed = (float)(Math.sqrt(2) / 2 * SPEED);
        ySpeed = (float)(Math.sqrt(2) / 2 * SPEED);

    }


    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        BufferedImage image = Assets.player;
        AffineTransform transform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
        transform.rotate(Math.toRadians(degree), image.getWidth()/2 , image.getHeight()/2 );


        g.drawImage(image, transform, null);

        BufferedImage imageGun = null;

        if (gunState == 1) {
            switch (cannonLevel) {
                case 0: imageGun = Assets.playerCannonGun[0]; break;
                case 1: imageGun = Assets.playerCannonGun[1]; break;
                case 2: imageGun = Assets.playerCannonGun[2]; break;
                case 3: imageGun = Assets.playerCannonGun[3]; break;
            }
        }
        else {
            switch (bulletLevel) {
                case 0: imageGun = Assets.playerBulletGun[0]; break;
                case 1: imageGun = Assets.playerBulletGun[1]; break;
                case 2: imageGun = Assets.playerBulletGun[2]; break;
            }

        }

        AffineTransform transformGun = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()) + 10, (int) (y - Camera.getYOffset()));
        transformGun.rotate(Math.toRadians(degreeGun), imageGun.getWidth() / 2 - 12 , imageGun.getHeight() / 2);

        g.drawImage(imageGun, transformGun, null);
    }

    public void renderPlayerState(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        g.drawString("Cannon: " + cannon, 15, 60);
        g.drawString("Bullet: " + bullet, 15, 90);
        g.drawString("Health: " + health, 15, 120);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + 2 * SPEED / 3, (int) y + 2 * SPEED / 3, width -  4 * SPEED / 3 , height - 4 * SPEED / 3);
    }

}
