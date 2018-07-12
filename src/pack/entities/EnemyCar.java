package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.entities.players.Player;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.MouseManager;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EnemyCar extends Entity {
    private double degree;
    private double degreeGun;
    private float health;
    private final int SPEED = 10;
    private final int FIRE_Rate = 7; //the less, the faster
    private int fireCounter = 0;


    public EnemyCar(float x, float y, EntityManager entityManager) {
        super(x, y, 100, 100, entityManager);
        health = 1;

    }

    @Override
    public void tick() {
        getDamage();
        doAI();

    }

    private void doAI() {
        if (entityManager.deltaXToClosestPlayer(this) < (2 * Game.frameWidth / 3) && entityManager.deltaYToClosestPlayer(this) < (2 * Game.frameHeight / 3)) {
            Player closestPlayer = entityManager.getClosestPlayer(this);
            degreeGun = MouseManager.angleToPlayer(closestPlayer, this);
            //same gun and tank
            Bullet bullet = entityManager.createEnemyBullet(x, y, degreeGun);

            while (((Math.abs(bullet.getX() + bullet.xPlus - closestPlayer.getX()) > 2)
                    || (Math.abs(bullet.getY() + bullet.yPlus - closestPlayer.getY()) > 2)) &&
                    (Math.abs(bullet.getBounds().x) < Game.frameWidth) &&
                    (Math.abs(bullet.getBounds().y) < Game.frameHeight)) {
                if (entityManager.doCollideWithHardWalls(bullet) != null) {
                    entityManager.removeEnemyBullet(bullet);
                    break;
                }
                bullet.xPlus += Math.cos(Math.toRadians(degreeGun)) * SPEED;
                bullet.yPlus += Math.sin(Math.toRadians(degreeGun)) * SPEED;
            }
            bullet.xPlus = 0;
            bullet.yPlus = 0;
            if (fireCounter != FIRE_Rate) {
                fireCounter++;
                entityManager.removeEnemyBullet(bullet);
            } else {
                fireCounter = -1;
            }

            //flag to show can shoot or no or to move toward player
            //we can move with xPlus and yPlus

            degree = degreeGun;
            x += Math.cos(Math.toRadians(degreeGun)) * SPEED;
            y += Math.sin(Math.toRadians(degreeGun)) * SPEED;
            if ((entityManager.doCollideWithHardWalls(this) != null)||
                    (entityManager.doCollideWithPlayer(this)!=null) ||
                    entityManager.doCollideWithSoftWalls(this) != null ||
                    entityManager.doCollideWithEnemyCar(this) != null ||
                    entityManager.doCollideWithEnemyTank(this) != null ||
                    entityManager.doCollideWithBarbedWires(this) != null) {
                x -= Math.cos(Math.toRadians(degreeGun)) * SPEED;
                y -= Math.sin(Math.toRadians(degreeGun)) * SPEED;
            }



        }
    }

    private void getDamage() {
        Bullet bullet = entityManager.doCollideWithFriendlyBullet(this);
        if (bullet != null) {
            health -= Bullet.DAMAGE;
            entityManager.removeFriendlyBullet(bullet);
        }

        Cannon cannon = entityManager.doCollideWithFriendlyCannon(this);
        if (cannon != null) {
            health -= Cannon.DAMAGE;
            entityManager.removeFriendlyCannon(cannon);
        }

        if (health <= 0) {
            entityManager.removeEnemyCar(this);
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
        transformGun.rotate(Math.toRadians(degreeGun), image.getWidth() / 2, image.getHeight() / 2);

        g.drawImage(imageGun, transformGun, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + 2 * SPEED / 3, (int) y + 2 * SPEED / 3, width - 4 * SPEED / 3 , height - 4 * SPEED / 3);
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

