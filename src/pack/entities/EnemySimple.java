package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.MouseManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EnemySimple extends Entity {
    private boolean up, down, right, left;
    private boolean upFinal, downFinal, rightFinal, leftFinal;
    private Player player;
    private double degree;
    public static double degreeGun;
    private double xMove, yMove;
    private final int SPEED = 5;
    private final int FIRE_Rate = 7; //the less, the faster
    private int fireCounter = 0;
    private double xPlus, yPlus;


    public EnemySimple(float x, float y, Player player) {
        super(x, y, 100, 100);
        this.player = player;

    }

    @Override
    public void tick() {
        up = false;
        down = false;
        right = false;
        left = false;
        upFinal = false;
        downFinal = false;
        leftFinal = false;
        rightFinal = false;
        xPlus = 0;
        yPlus = 0;
        if ((x > Camera.getXOffset()) && (x < (Camera.getXOffset() + Game.frameWidth)) && (y > Camera.getYOffset())
                && (y < (Camera.getYOffset() + Game.frameHeight))) {
            degreeGun = MouseManager.angleWithEnemy(x, y);
            //same gun and tank
            boolean flag = true;
            Bullet bullet = EntityManager.createEnemyBullet(x, y, degreeGun);

            while (((Math.abs(bullet.getX() + bullet.xPlus - player.getX()) > 2)
                    || (Math.abs(bullet.getY() + bullet.yPlus - player.getY()) > 2)) &&
                    (Math.abs(bullet.getBounds().x) < Game.frameWidth) &&
                    (Math.abs(bullet.getBounds().y) < Game.frameHeight)) {
                if (EntityManager.doCollideWithHardWalls(bullet) != null) {
                    EntityManager.removeEnemyBullet(bullet);
                    flag = false;
                    break;
                }
                bullet.xPlus += Math.cos(Math.toRadians(degreeGun)) * SPEED;
                bullet.yPlus += Math.sin(Math.toRadians(degreeGun)) * SPEED;
            }
            bullet.xPlus = 0;
            bullet.yPlus = 0;
            if (fireCounter != FIRE_Rate) {
                fireCounter++;
                EntityManager.removeEnemyBullet(bullet);
            } else {
                fireCounter = -1;
            }

            //flag to show can shoot or no or to move toward player
            //we can move with xPlus and yPlus

            degree = degreeGun;
            x += Math.cos(Math.toRadians(degreeGun)) * SPEED;
            y += Math.sin(Math.toRadians(degreeGun)) * SPEED;
            System.out.println(getBounds());
            if ((EntityManager.doCollideWithHardWalls(this) != null)||
                    (EntityManager.doCollideWithPlayer(this)!=null)) {
                x -= Math.cos(Math.toRadians(degreeGun)) * SPEED;
                y -= Math.sin(Math.toRadians(degreeGun)) * SPEED;
                flag = false;
            }



        }


    }



    @Override
    public void render(Graphics2D g) {


        BufferedImage image = Assets.player;
        AffineTransform transform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
        transform.rotate(Math.toRadians(degree), image.getWidth() / 2, image.getHeight() / 2);


        g.drawImage(image, transform, null);


        BufferedImage imageGun = Assets.playerCannonGun;
        AffineTransform transformGun = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset() + 18), (int) (y - Camera.getYOffset() + 13));
        transformGun.rotate(Math.toRadians(degreeGun), imageGun.getWidth() / 4 + 4, imageGun.getHeight() / 4 + 4);


        g.drawImage(imageGun, transformGun, null);
    }

    @Override
    public Rectangle getBounds() {

        return new Rectangle((int) x, (int) y, width, height);
    }

}

