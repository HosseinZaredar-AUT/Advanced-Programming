package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.MouseManager;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EnemyCar extends Entity {
    private double degree;
    public static double degreeGun;
    private float health;
    private final int SPEED = 5;
    private final int FIRE_Rate = 7; //the less, the faster
    private int fireCounter = 0;


    public EnemyCar(float x, float y) {
        super(x, y, 100, 100);
        health = 1;

    }

    @Override
    public void tick() {
        getDamage();
        doAI();

    }

    private void doAI() {
        if ((x > Camera.getXOffset()) && (x < (Camera.getXOffset() + Game.frameWidth)) && (y > Camera.getYOffset())
                && (y < (Camera.getYOffset() + Game.frameHeight))) {
            degreeGun = MouseManager.angleWithEnemy(x, y);
            //same gun and tank
            boolean flag = true;
            Bullet bullet = EntityManager.createEnemyBullet(x, y, degreeGun);

            while (((Math.abs(bullet.getX() + bullet.xPlus - EntityManager.getPlayer().getX()) > 2)
                    || (Math.abs(bullet.getY() + bullet.yPlus - EntityManager.getPlayer().getY()) > 2)) &&
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
            if ((EntityManager.doCollideWithHardWalls(this) != null)||
                    (EntityManager.doCollideWithPlayer(this)!=null) ||
                    EntityManager.doCollideWithSoftWalls(this) != null ||
                    EntityManager.doCollideWithEnemyCar(this) != null ||
                    EntityManager.doCollideWithEnemyTank(this) != null) {
                x -= Math.cos(Math.toRadians(degreeGun)) * SPEED;
                y -= Math.sin(Math.toRadians(degreeGun)) * SPEED;
                flag = false;
            }



        }
    }

    private void getDamage() {
        Bullet bullet = EntityManager.doCollideWithFriendlyBullet(this);
        if (bullet != null) {
            health -= Bullet.DAMAGE;
            EntityManager.removeFriendlyBullet(bullet);
        }

        Cannon cannon = EntityManager.doCollideWithFriendlyCannon(this);
        if (cannon != null) {
            health -= Cannon.DAMAGE;
            EntityManager.removeFriendlyCannon(cannon);
        }

        if (health <= 0) {
            EntityManager.removeEnemyCar(this);
        }
    }



    @Override
    public void render(Graphics2D g) {


        BufferedImage image = Assets.enemyCar;
        AffineTransform transform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
        transform.rotate(Math.toRadians(degree), image.getWidth() / 2, image.getHeight() / 2);


        g.drawImage(image, transform, null);


        BufferedImage imageGun = Assets.enemyCarGun;
        AffineTransform transformGun = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
        transformGun.rotate(Math.toRadians(degreeGun), imageGun.getWidth() / 2, imageGun.getHeight() / 2);

        g.drawImage(imageGun, transformGun, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof EnemyCar))
            return false;

        EnemyCar other = (EnemyCar) obj;
        return (x == other.x) && (y == other.y);
    }

}

