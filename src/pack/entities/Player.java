package pack.entities;

import pack.Game;
import pack.sound.ExampleSounds;
import pack.entities.manager.EntityManager;
import pack.graphics.*;
import pack.input.*;
import pack.states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Player extends Entity {

    private int number;
    private float startX;
    private float startY;
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
//    public static LocalTime localTime2;


    private int gunState; //1.Cannon, -1.Bullet
    private double cannonRate = 1; //the less, the faster
    private int cannonLevel = 0;
    private int bulletRate = 6; //the less, the faster
    private int bulletLevel = 0;
    private int bulletCounter = 0;


    private final float MAX_HEALTH = 25;
    private float health;

    private final int MAX_CANNON = 50;
    private int cannon;

    private final int MAX_BULLET = 200;
    private int bullet;


    public Player(float x, float y, int number, EntityManager entityManager) {
        super(x, y, 100, 100, entityManager);
        startX = x;
        startY = y;
        gunState = 1;
        health = MAX_HEALTH;
        bullet = MAX_BULLET;
        cannon = MAX_CANNON;
        xSpeed = (float)(Math.sqrt(2) / 2 * SPEED);
        ySpeed = (float)(Math.sqrt(2) / 2 * SPEED);
        this.number = number;
    }

    @Override
    public void tick() {
//        localTime2 = LocalTime.now();
//        if((GameState.localTime1.until(localTime2,SECONDS)>33)&&(entityManager.gameOver==true)) {
//            ExampleSounds.playgameSound1();
//            GameState.localTime1 = LocalTime.now();
//        }
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

        y -= yMove;

        if (entityManager.doCollideWithHardWalls(this) != null ||
                entityManager.doCollideWithSoftWalls(this) != null ||
                entityManager.doCollideWithEnemyTank(this) != null ||
                entityManager.doCollideWithEnemyCar(this) != null  ||
                entityManager.doCollideWithArtillery(this) != null)

            x -= xMove;

        y += yMove;
        if (entityManager.doCollideWithHardWalls(this) != null ||
                entityManager.doCollideWithSoftWalls(this) != null ||
                entityManager.doCollideWithEnemyTank(this) != null ||
                entityManager.doCollideWithEnemyCar(this) != null  ||
                entityManager.doCollideWithArtillery(this) != null)

            y -= yMove;

        Camera.centerOnEntity(this);
    }

    private void getFood() {
        if (bullet != MAX_BULLET) {
            BulletFood bulletFood = entityManager.doCollideWithBulletFood(this);
            if (bulletFood != null) {
//                ExampleSounds.playagree();
                bullet = MAX_BULLET;
                entityManager.removeBulletFood(bulletFood);
            }
        }

        if (cannon != MAX_CANNON) {
            CannonFood cannonFood = entityManager.doCollideWithCannonFood(this);
            if (cannonFood != null) {
//                ExampleSounds.playagree();
                cannon = MAX_CANNON;
                entityManager.removeCannonFood(cannonFood);
            }
        }

        if (health != MAX_HEALTH) {
            RepairFood repairFood = entityManager.doCollideWithRepairFood(this);
            if (repairFood != null) {
//                ExampleSounds.playagree();
                health = MAX_HEALTH;
                entityManager.removeRepairFood(repairFood);
            }
        }

    }

    private void upgrade() {
        Upgrader upgrader = entityManager.doCollideWithUpgrader(this);

        if (upgrader != null) {
            if (gunState == -1)
                updateBullet();
            else
                updateCannon();


            if (gunState == -1)
                updateBullet();
            else
                updateCannon();


            entityManager.removeUpgrader(upgrader);
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

//        ExampleSounds.playagree();

    }

    private void updateBullet() {
        if (bulletLevel < 2) {
            bulletLevel++;
            bulletRate--;
            bulletCounter = -1;
        } else if (cannonLevel < 3)
            updateCannon();

//        ExampleSounds.playagree();

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

                    entityManager.createFriendlyCannon((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun);

//                    ExampleSounds.playcannon();
                    if (cannonLevel == 2 || cannonLevel == 3) {
                        entityManager.createFriendlyCannon((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun + 8);
                        entityManager.createFriendlyCannon((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun - 8);
                    }
                    cannon--;

                    canShoot = false;
                    timeStart = System.nanoTime();
                }
            } else if (gunState == -1 && bullet > 0) {
                if (bulletCounter == bulletRate) {
                    entityManager.createFriendlyBullet((int) ((x + width / 2) - 12 + 65 * Math.cos(Math.toRadians(degreeGun))),(int) ((y + height / 2) + 60 * Math.sin(Math.toRadians(degreeGun))), degreeGun);

//                    ExampleSounds.playlightgun();

                    bullet--;
                    bulletCounter = -1;
                }
                bulletCounter++;
            }
        }

    }

    private void getDamage() {
        Bullet enemyBullet = entityManager.doCollideWithEnemyBullet(this);
        if (enemyBullet != null) {
//            ExampleSounds.playEnemyBulletToMyTank();
            health -= Bullet.DAMAGE;
            entityManager.removeEnemyBullet(enemyBullet);
        }

        Cannon enemyCannon = entityManager.doCollideWithEnemyCannon(this);
        if (enemyCannon != null) {
            health -= Cannon.DAMAGE;
            entityManager.removeEnemyCannon(enemyCannon);
        }

        Mine mine = entityManager.doCollideWithMine(this);
        if (mine != null) {
            health -= Mine.DAMAGE;
//            ExampleSounds.playEnemyBulletToMyTank();
            entityManager.removeMine(mine);
        }

        if (health <= 0) {
            entityManager.gameOver = true;
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

    public float getStartX() {
        return startX;
    }


    public float getStartY() {
        return startY;
    }

}
