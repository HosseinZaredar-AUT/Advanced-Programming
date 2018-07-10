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
    private double degreeGun;
    public float xMove, yMove;
    private final int SPEED = 10;
    private float xSpeed;
    private float ySpeed;

    private boolean canShoot = true;
    private double timeStart;
    private double timeEnd;


    private int gunState; //1.Cannon, -1.Bullet
    private double cannonRate = 1; //the less, the faster
    private int cannonLevel = 0;
    private int bulletRate = 6; //the less, the faster
    private int bulletLevel = 0;
    private int bulletCounter = 0;


    private final float MAX_HEALTH = 5;
    private float health;

    private final int MAX_CANNON = 50;
    private int cannon;

    private final int MAX_BULLET = 200;
    private int bullet;


    public Player(float x, float y) {
        super(x, y, 100, 100);
        gunState = 1;
        health = MAX_HEALTH;
        bullet = MAX_BULLET;
        cannon = MAX_CANNON;
        xSpeed = (float)(Math.sqrt(2) / 2 * SPEED);
        ySpeed = (float)(Math.sqrt(2) / 2 * SPEED);

    }

    @Override
    public void tick() {

        move();
        getFood();
        upgrade();
        shoot();
        getDamage();
        prepareRender();
    }


    private void move() {
        //MOVEMENT
        up = KeyManager.up;
        down = KeyManager.down;
        left = KeyManager.left;
        right = KeyManager.right;

        xMove = 0;
        yMove = 0;

        if (up)
            yMove = -ySpeed;
        if (down)
            yMove = ySpeed;
        if (left)
            xMove = -xSpeed;
        if (right)
            xMove = xSpeed;

        x += xMove;
        y += yMove;

        if (x - Camera.getXOffset() + width > Game.frameWidth || x - Camera.getXOffset() < 0 ||
                y - Camera.getYOffset() + height > Game.frameHeight || y - Camera.getYOffset() < 0) {
            x -= xMove;
            y -= yMove;

        } else {

            y -= yMove;


            if (EntityManager.doCollideWithHardWalls(this) != null ||
                    EntityManager.doCollideWithSoftWalls(this) != null ||
                    EntityManager.doCollideWithEnemyTank(this) != null ||
                    EntityManager.doCollideWithEnemyCar(this) != null  ||
                    EntityManager.doCollideWithArtillery(this) != null)

                x -= xMove;

            y += yMove;
            if (EntityManager.doCollideWithHardWalls(this) != null ||
                    EntityManager.doCollideWithSoftWalls(this) != null ||
                    EntityManager.doCollideWithEnemyTank(this) != null ||
                    EntityManager.doCollideWithEnemyCar(this) != null  ||
                    EntityManager.doCollideWithArtillery(this) != null)

                y -= yMove;

        }
        Camera.centerOnEntity(this);
    }

    private void getFood() {
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

    }

    private void upgrade() {
        Upgrader upgrader = EntityManager.doCollideWithUpgrader(this);

        if (upgrader != null) {
            if (gunState == -1)
                updateBullet();
            else
                updateCannon();

            EntityManager.removeUpgrader(upgrader);
        }

        //if both of them are fully upgraded, nothing upgrades, but the Star is removed anyways.
    }

    private void updateCannon() {
        if (cannonLevel < 3) {
            cannonLevel++;
            if (cannonLevel == 1 || cannonLevel == 3) {
                cannonRate -= 20;
            } else {
                cannonRate += 20;
            }
        } else if (bulletLevel < 2)
            updateBullet();

    }

    private void updateBullet() {
        if (bulletLevel < 2) {
            bulletLevel++;
            bulletRate--;
            bulletCounter = -1;
        } else if (cannonLevel < 3)
            updateCannon();
    }

    private void shoot() {
        gunState = MouseManager.rightMouseButtonFlag;

        degreeGun = MouseManager.angle;
        if (MouseManager.leftMouseButton) {

            timeEnd = System.nanoTime();
            if ((timeEnd - timeStart) / 1000000000 > 1)
                canShoot = true;

            if (gunState == 1 && cannon > 0) {
                if (canShoot) {

                    EntityManager.createFriendlyCannon((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun);
                    if (cannonLevel == 2 || cannonLevel == 3) {
                        EntityManager.createFriendlyCannon((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun + 8);
                        EntityManager.createFriendlyCannon((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun - 8);
                    }
                    cannon--;

                    canShoot = false;
                    timeStart = System.nanoTime();
                }
            } else if (gunState == -1 && bullet > 0) {
                if (bulletCounter == bulletRate) {
                    EntityManager.createFriendlyBullet((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun);
                    bullet--;
                    bulletCounter = -1;
                }
                bulletCounter++;
            }
        }

    }

    private void getDamage() {
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
            EntityManager.gameOver = true;
        }
    }

    private void prepareRender() {
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


        System.out.println(degreeGun);
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
