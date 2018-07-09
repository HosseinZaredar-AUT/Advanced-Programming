package pack.entities;

import jdk.jfr.Enabled;
import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.MouseManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EnemyTank extends Entity {
    private double degree;
    private double degreeGun;
    private float health;
    private final int SPEED = 4;
    private final int FIRE_Rate = 40; //the less, the faster
    private int fireCounter = 0;


    public EnemyTank(float x, float y) {
        super(x, y, 100, 100);
        health = 1;
    }

    @Override
    public void tick() {
        getDamage();
        doAI();
    }

    private void doAI() {
        boolean up = false;
        boolean down = false;
        boolean right = false;
        boolean left = false;
        boolean upFinal = false;
        boolean downFinal = false;
        boolean leftFinal = false;
        boolean rightFinal = false;

        if ((x > Camera.getXOffset()) && (x < (Camera.getXOffset() + Game.frameWidth)) && (y > Camera.getYOffset())
                && (y < (Camera.getYOffset() + Game.frameHeight))) {
            degreeGun = MouseManager.angleWithEnemy(x, y);
            //same gun and tank
            boolean flag = true;
            Cannon cannon = EntityManager.createEnemyCannon(x, y, degreeGun);

            while (((Math.abs(cannon.getX() + cannon.xPlus - EntityManager.getPlayer().getX()) > 2)
                    || (Math.abs(cannon.getY() + cannon.yPlus - EntityManager.getPlayer().getY()) > 2)) && (Math.abs(cannon.getBounds().x) < Game.frameWidth) &&
                    (Math.abs(cannon.getBounds().y) < Game.frameHeight)) {
                if (EntityManager.doCollideWithHardWalls(cannon) != null) {
                    EntityManager.removeEnemyCannon(cannon);
                    flag = false;
                    break;
                }
                cannon.xPlus += Math.cos(Math.toRadians(degreeGun)) * SPEED;
                cannon.yPlus += Math.sin(Math.toRadians(degreeGun)) * SPEED;
            }

            cannon.xPlus = 0;
            cannon.yPlus = 0;
            if (fireCounter != FIRE_Rate) {
                fireCounter++;
                EntityManager.removeEnemyCannon(cannon);
            } else {
                fireCounter = -1;
            }

            //flag to show can shoot or no or to move toward player
            //we can move with xPlus and yPlus
            if (flag) {
                degree = degreeGun;
                x += Math.cos(Math.toRadians(degreeGun)) * SPEED;
                y += Math.sin(Math.toRadians(degreeGun)) * SPEED;
                if (EntityManager.doCollideWithHardWalls(this) != null ||
                        EntityManager.doCollideWithSoftWalls(this) != null ||
                        EntityManager.doCollideWithPlayer(this)!=null  ||
                        EntityManager.doCollideWithArtillery(this) != null) {
                    x -= Math.cos(Math.toRadians(degreeGun)) * SPEED;
                    y -= Math.sin(Math.toRadians(degreeGun)) * SPEED;
                    flag = false;

                }
            } else if (!flag) {

                boolean move = true;
                if ((EntityManager.getPlayer().xMove == 0) && (EntityManager.getPlayer().yMove == 0)) {

                    if (EntityManager.getPlayer().getY() == y) {
                        if (EntityManager.getPlayer().getX() < x) {
                            right = true;
                            rightFinal = true;
                            up = true;
                            upFinal = true;
                        }
                        if (EntityManager.getPlayer().getX() > x) {
                            left = true;
                            leftFinal = true;
                            up = true;
                            upFinal = true;
                        }
                    }

                    if (EntityManager.getPlayer().getX() == x) {
                        if (EntityManager.getPlayer().getY() < y) {
                            right = true;
                            rightFinal = true;
                            down = true;
                            downFinal = true;
                        }
                        if (EntityManager.getPlayer().getY() > y) {
                            right = true;
                            rightFinal = true;
                            up = true;
                            upFinal = true;
                        }
                    }
                } else {
                    if (x > EntityManager.getPlayer().getX()) {

                        left = true;
                        leftFinal = true;
                    }
                    if (x < EntityManager.getPlayer().getX()) {
                        right = true;
                        rightFinal = true;
                    }
                    if (y >= EntityManager.getPlayer().getY()) {
                        up = true;
                        upFinal = true;
                    }
                    if (y < EntityManager.getPlayer().getY()) {
                        down = true;
                        downFinal = true;
                    }
                }


                for (int i = 0; i < 3; i++) {
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

                    move = true;
                    x += Math.cos(Math.toRadians(degree)) * SPEED;
                    y += Math.sin(Math.toRadians(degree)) * SPEED;
                    if (EntityManager.doCollideWithHardWalls(this) != null) {

                        move = false;
                        x -= Math.cos(Math.toRadians(degree)) * SPEED;
                        y -= Math.sin(Math.toRadians(degree)) * SPEED;

                        if (i == 1) {
                            if (left) {
                                left = false;
                                if ((!up) && (upFinal))
                                    up = true;
                                if ((!down) && (downFinal))
                                    down = true;
                            } else if (right) {
                                right = false;
                                if ((!up) && (upFinal))
                                    up = true;
                                if ((!down) && (downFinal))
                                    down = true;
                            } else if (down) {
                                down = false;
                                if ((!right) && (rightFinal))
                                    right = true;
                                if ((!left) && (leftFinal))
                                    left = true;
                            } else if (up) {
                                up = false;
                                if ((!right) && (rightFinal == true))
                                    right = true;
                                if ((!left) && (leftFinal == true))
                                    left = true;
                            }
                            continue;
                        }

                        if (up) {
                            up = false;
                            continue;
                        }

                        if (down) {
                            down = false;
                            continue;
                        }

                        if (right) {
                            right = false;
                            continue;
                        }

                        if (left) {
                            left = false;
                            continue;
                        }

                    } else {
                        break;
                    }

                }


                if (!move) {
                    System.out.println("A");
                }


                if (x - Camera.getXOffset() + width > Game.frameWidth || x - Camera.getXOffset() < 0 ||
                        y - Camera.getYOffset() + height > Game.frameHeight || y - Camera.getYOffset() < 0) {
                    x -= Math.cos(Math.toRadians(degree)) * SPEED;
                    y -= Math.sin(Math.toRadians(degree)) * SPEED;

                } else {

                    y -= Math.sin(Math.toRadians(degree)) * SPEED;

                    if (EntityManager.doCollideWithHardWalls(this) != null ||
                            EntityManager.doCollideWithSoftWalls(this) != null  ||
                            EntityManager.doCollideWithArtillery(this) != null  ||
                            EntityManager.doCollideWithPlayer(this) != null)
                        x -= Math.cos(Math.toRadians(degree)) * SPEED;
                    ;

                    y += Math.sin(Math.toRadians(degree)) * SPEED;
                    if (EntityManager.doCollideWithHardWalls(this) != null ||
                            EntityManager.doCollideWithSoftWalls(this) != null  ||
                            EntityManager.doCollideWithArtillery(this) != null  ||
                            EntityManager.doCollideWithPlayer(this) != null)
                        y -= Math.sin(Math.toRadians(degree)) * SPEED;

                }

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
            EntityManager.removeEnemyTank(this);
        }
    }



    @Override
    public void render(Graphics2D g) {


        BufferedImage image = Assets.enemyTank;
        AffineTransform transform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
        transform.rotate(Math.toRadians(degree), image.getWidth() / 2, image.getHeight() / 2);


        g.drawImage(image, transform, null);


        BufferedImage imageGun = Assets.enemyTankGun;
        AffineTransform transformGun = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset() + 18), (int) (y - Camera.getYOffset() + 13));
        transformGun.rotate(Math.toRadians(degreeGun), imageGun.getWidth() / 4 + 4, imageGun.getHeight() / 4 + 4);


        g.drawImage(imageGun, transformGun, null);
    }

    @Override
    public Rectangle getBounds() {

        return new Rectangle((int) x, (int) y, width, height);
    }

}

