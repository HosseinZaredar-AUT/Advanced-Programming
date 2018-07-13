package pack.entities.players;

import pack.entities.*;
import pack.entities.manager.EntityManager;
import pack.network.Server;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ClientPlayer extends Player {

    private String command;
    private boolean rightClick, leftClick;
    public boolean hasLeft = false;
    private boolean sentHardWalls = false;


    public ClientPlayer(float x, float y, int number, EntityManager entityManager) {
        super(x, y, number, entityManager);
    }

    @Override
    public void tick() {


        //SENDING STATIC STUFF FOR 1 TIME
        if (!sentHardWalls) {
            OutputStream wallsOut = Server.getOutputStream(number);
            try {
                ObjectOutputStream wallWriter = new ObjectOutputStream(wallsOut);
                wallWriter.writeObject(entityManager.getHardWalls());
            } catch (IOException e) {
                return;
            }

            sentHardWalls = true;
        }


        //GETTING INFO FROM REAL CLIENT
        InputStream in = Server.getInputStream(number);
        byte[] buffer = new byte[1024];

        try {
            int size = in.read(buffer);
            command = new String(buffer, 0, size);

        } catch (IOException e) {
            hasLeft = true;
            return;
        }

        String[] tokens = command.split(",");
        up = tokens[0].equals("1");
        down = tokens[1].equals("1");
        right = tokens[2].equals("1");
        left = tokens[3].equals("1");

        rightClick = tokens[4].equals("1");
        leftClick = tokens[5].equals("1");
        degreeGun = Float.parseFloat(tokens[6]);




        move();
        getFood();
        upgrade();
        shoot();
        getDamage();
        prepareRender();

    }

    private void move() {
        //MOVEMENT

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
                    entityManager.doCollideWithArtillery(this) != null ||
                    entityManager.doCollideWithBarbedWires(this) != null)

                x -= xMove;

            y += yMove;
            if (entityManager.doCollideWithHardWalls(this) != null ||
                    entityManager.doCollideWithSoftWalls(this) != null ||
                    entityManager.doCollideWithEnemyTank(this) != null ||
                    entityManager.doCollideWithEnemyCar(this) != null  ||
                    entityManager.doCollideWithArtillery(this) != null ||
                    entityManager.doCollideWithBarbedWires(this) != null)

                y -= yMove;

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

            entityManager.removeUpgrader(upgrader);
        }

        //if both of them are fully upgraded, nothing upgrades, but the Star is removed anyways.
    }

    private void updateCannon() {
        if (cannonLevel < 3) {
            cannonLevel++;
            if (cannonLevel == 1 || cannonLevel == 3) {
                cannonRate -= 0.5;
            } else {
                cannonRate += 0.5;
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
        if (rightClick)
            gunState *= -1;

        if (leftClick) {

            timeEnd = System.nanoTime();
            if ((timeEnd - timeStart) / 1000000000 > cannonRate)
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


    public int getNumber() {
        return number;
    }

}
