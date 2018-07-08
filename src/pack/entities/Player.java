package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.*;
import pack.input.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private boolean up, down, right, left;
    private int degree;
    public static double degreeGun;
    public int xMove, yMove;
    private final int SPEED = 10;

    private int gunState; //1.Cannon, -1.Bullet
    private int cannonRate = 40; //the less, the faster
    private int cannonLevel = 0;
    private int cannonCounter = 0;
    private int bulletRate = 6; //the less, the faster
    private int bulletLevel = 0;
    private int bulletCounter = 0;


    private final float MAX_HEALTH = 5;
    private float health;

    private final int MAX_CANNON = 30;
    private int cannon;

    private final int MAX_BULLET = 100;
    private int bullet;


    public Player(float x, float y) {
        super(x, y, 100, 100);
        gunState = 1;
        health = MAX_HEALTH;
        bullet = MAX_BULLET;
        cannon = MAX_CANNON;

    }

    @Override
    public void tick() {

        //MOVEMENT
        up = KeyManager.up;
        down = KeyManager.down;
        left = KeyManager.left;
        right = KeyManager.right;

        xMove = 0;
        yMove = 0;

        if (up)
            yMove = -SPEED;
        if (down)
            yMove = SPEED;
        if (left)
            xMove = -SPEED;
        if (right)
            xMove = SPEED;

        x += xMove;
        y += yMove;

        if (x - Camera.getXOffset() + width > Game.frameWidth || x - Camera.getXOffset() < 0 ||
                y - Camera.getYOffset() + height > Game.frameHeight || y - Camera.getYOffset() < 0) {
            x -= xMove;
            y -= yMove;

        } else {
            x -= xMove;
            y -= yMove;

            x += xMove;
            if (EntityManager.doCollideWithHardWalls(this) != null ||
                    EntityManager.doCollideWithSoftWalls(this) != null)
                x -= xMove;

            y += yMove;
            if (EntityManager.doCollideWithHardWalls(this) != null ||
                    EntityManager.doCollideWithSoftWalls(this) != null)
                y -= yMove;

        }

        Camera.centerOnEntity(this);

        //FOOD
        if (bullet != MAX_BULLET) {
            BulletFood bulletFood = EntityManager.doCollideWithBulletFood(this);
            if (bulletFood != null) {
                bullet = MAX_BULLET;
                EntityManager.removeBulletFood(bulletFood);
            }
        }

        if (cannon != MAX_CANNON) {
            CannonFood cannonFood = EntityManager.doCollideWithCannonFood(this);
            if (cannonFood != null) {
                cannon = MAX_CANNON;
                EntityManager.removeCannonFood(cannonFood);
            }
        }

        if (health != MAX_HEALTH) {
            RepairFood repairFood = EntityManager.doCollideWithRepairFood(this);
            if (repairFood != null) {
                health = MAX_HEALTH;
                EntityManager.removeRepairFood(repairFood);
            }
        }

        //UPGRADERS
        Upgrader upgrader = EntityManager.doCollideWithUpgrader(this);
        if (upgrader != null) {

            //TODO take care of levels getting more that 3
            if (gunState == -1) {
                bulletLevel++;
                bulletRate--;
                bulletCounter = -1;
            } else {
                cannonLevel++;
                if (cannonLevel == 1 || cannonLevel == 3) {
                    cannonRate -= 20;
                } else {
                    cannonRate += 20;
                }
                cannonCounter = -1;
            }
            EntityManager.removeUpgrader(upgrader);

        }

        //SHOOT
        gunState = MouseManager.rightMouseButtonFlag;

        degreeGun = MouseManager.angle;
        if (MouseManager.leftMouseButton) {
            //todo make it precise...

            if (gunState == 1 && cannon > 0) {
                if (cannonCounter == cannonRate) {
                    EntityManager.createFriendlyCannon(x + width / 2+10, y + height / 2+10, degreeGun+3);
                    if (cannonLevel == 2 || cannonLevel == 3) {
                        EntityManager.createFriendlyCannon(x + width / 2, y + height / 2, degreeGun + 8);
                        EntityManager.createFriendlyCannon(x + width / 2, y + height / 2, degreeGun + 8);
                    }
                    cannon--;
                    cannonCounter = -1;
                }
                cannonCounter++;
            } else if (gunState == -1 && bullet > 0) {
                if (bulletCounter == bulletRate) {
                    EntityManager.createFriendlyBullet(x + width / 2, y + height / 2, degreeGun);
                    bullet--;
                    bulletCounter = -1;
                }
                bulletCounter++;
            }
        }

        //GETTING DAMAGE
        Bullet enemyBullet = EntityManager.doCollideWithEnemyBullet(this);
        if (enemyBullet != null) {
            health -= Bullet.DAMAGE;
            EntityManager.removeEnemyBullet(enemyBullet);
        }

        Cannon enemyCannon = EntityManager.doCollideWithEnemyCannon(this);
        if (enemyCannon != null) {
            health -= Cannon.DAMAGE;
            EntityManager.removeEnemyCannon(enemyCannon);
        }

        Mine mine = EntityManager.doCollideWithMine(this);
        if (mine != null) {
            health -= Mine.DAMAGE;
            EntityManager.removeMine(mine);
        }

        if (health <= 0) {
            //TODO something
            health = MAX_HEALTH;
        }




    }


    @Override
    public void render(Graphics2D g) {

        if (up && right)
            degree = -45;
        else if (up && left)
            degree = -135;
        else if (right && down)
            degree = 45;
        else if (left && down)
            degree = 135;
        else if (down)
            degree = 90;
        else if (up)
            degree = -90;
        else if (right)
            degree = 0;
        else if (left)
            degree = 180;


        BufferedImage image = Assets.player;
        AffineTransform transform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
        transform.rotate(Math.toRadians(degree), image.getWidth()/2 , image.getHeight()/2 );


        g.drawImage(image, transform, null);

        //TODO take care of different level images...
        BufferedImage imageGun;
        if (gunState == 1)
            imageGun = Assets.playerCannonGun;
        else
            imageGun = Assets.playerBulletGun;

        AffineTransform transformGun = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset() + 18), (int) (y - Camera.getYOffset()+ 13));
        transformGun.rotate(Math.toRadians(degreeGun), imageGun.getWidth() / 4 + 4 , imageGun.getHeight() / 4 + 4);


        g.drawImage(imageGun, transformGun, null);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        g.drawString("Cannon: " + cannon, 15, 60);
        g.drawString("Bullet: " + bullet, 15, 90);
        g.drawString("Health: " + health, 15, 120);

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString("Cannon Level: " + cannonLevel, 15, 170);
        g.drawString("Bullet Level: " + bulletLevel, 15, 200);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x + 6, (int)y + 6, width - 6, height - 6);
    }

}
