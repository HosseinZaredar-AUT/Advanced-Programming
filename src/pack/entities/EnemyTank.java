package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.MouseManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EnemyTank extends Entity {
    private boolean up, down, right, left;
    private boolean upFinal, downFinal, rightFinal, leftFinal;
    private double degree;
    private double degreeGun;
    private final int SPEED = 4;
    private final int FIRE_Rate = 40; //the less, the faster
    private int fireCounter = 0;


    public EnemyTank(float x, float y) {
        super(x, y, 100, 100);

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

//if for game.width and game.height
        boolean equalXORY=true;
        if (((x > (Camera.getXOffset() - 100)) && (x < (Camera.getXOffset() + Game.frameWidth + 100))) || ((y > Camera.getYOffset() - 100)
                && (y < (Camera.getYOffset() + Game.frameHeight + 100)))
                ) {
            degreeGun = MouseManager.angleWithEnemy(x, y);
            //same gun and tank
            boolean flag = true;
            Cannon cannon = EntityManager.createEnemyCannon(x, y, degreeGun);

            while (((Math.abs(cannon.getX() + cannon.xPlus - EntityManager.getPlayer().getX()) > 2)
                    || (Math.abs(cannon.getY() + cannon.yPlus - EntityManager.getPlayer().getY()) > 2)) &&
                    (Math.abs(cannon.getBounds().x) < Game.frameWidth) &&
                    (Math.abs(cannon.getBounds().y) < Game.frameHeight)) {
                if ((EntityManager.doCollideWithHardWalls(cannon) != null)) {
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
                System.out.println(getBounds());
                if (EntityManager.doCollideWithHardWalls(this) != null) {
                    EntityManager.removeEnemyCannon(cannon);
                    x -= Math.cos(Math.toRadians(degreeGun)) * SPEED;
                    y -= Math.sin(Math.toRadians(degreeGun)) * SPEED;
                    flag = false;
                } else if (EntityManager.doCollideWithSoftWalls(this) != null ||
                        EntityManager.doCollideWithPlayer(this) != null) {
                    x -= Math.cos(Math.toRadians(degreeGun)) * SPEED;
                    y -= Math.sin(Math.toRadians(degreeGun)) * SPEED;
                    flag = false;
                }

            }
            if (!flag) {

                boolean move = false;
                if (((EntityManager.getPlayer().xMove == 0) && (EntityManager.getPlayer().yMove == 0))&&((Math.abs(EntityManager.getPlayer().getY() - y) < 4)||(Math.abs(EntityManager.getPlayer().getX() - x) < 4))) {
                    equalXORY=false;
                }else {
                    if ((EntityManager.getPlayer().xMove == 0) && (EntityManager.getPlayer().yMove == 0)) {
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
                        else if (right && down) {
                            degree = 45;
                        } else if (left && down)
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
                                    if ((up == false) && (upFinal == true))
                                        up = true;
                                    if ((down == false) && (downFinal == true))
                                        down = true;
                                } else if (right) {
                                    right = false;
                                    if ((up == false) && (upFinal == true))
                                        up = true;
                                    if ((down == false) && (downFinal == true))
                                        down = true;
                                    ;
                                } else if (down) {
                                    down = false;
                                    if ((right == false) && (rightFinal == true))
                                        right = true;
                                    if ((left == false) && (leftFinal == true))
                                        left = true;
                                } else if (up) {
                                    up = false;
                                    if ((right == false) && (rightFinal == true))
                                        right = true;
                                    if ((left == false) && (leftFinal == true))
                                        left = true;
                                }
                                continue;
                            }

                            if (up) {
                                up = false;
                                continue;
                            }
//                        if ((!up) && (upFinal)) {
//                            up = true;
//                        }
                            if (down) {
                                down = false;
                                continue;
                            }
//                        if ((!down) && (downFinal)) {
//                            down = true;
//                        }
                            if (right) {
                                right = false;
                                continue;
                            }
//                        if ((!right) && (rightFinal)) {
//                            right = true;
//                        }
                            if (left) {
                                left = false;
                                continue;
                            }
//                        if ((!left) && (leftFinal)) {
//                            left = true;
//                        }
                        } else {
                            break;
                        }

                    }
                }

                if ((!move)&&(!equalXORY)) {
                    System.out.println("A");
                }

                if (x - Camera.getXOffset() + width > Game.frameWidth || x - Camera.getXOffset() < 0 ||
                        y - Camera.getYOffset() + height > Game.frameHeight || y - Camera.getYOffset() < 0 || (
                        EntityManager.doCollideWithPlayer(this) != null)) {
                    x -= Math.cos(Math.toRadians(degree)) * SPEED;
                    y -= Math.sin(Math.toRadians(degree)) * SPEED;

                } else {

                    y -= Math.sin(Math.toRadians(degree)) * SPEED;

                    if (EntityManager.doCollideWithHardWalls(this) != null ||
                            EntityManager.doCollideWithSoftWalls(this) != null ||
                            EntityManager.doCollideWithEnemyTank(this) != null ||
                            EntityManager.doCollideWithEnemyCar(this) != null)
                        x -= Math.cos(Math.toRadians(degree)) * SPEED;
                    ;

                    y += Math.sin(Math.toRadians(degree)) * SPEED;
                    if (EntityManager.doCollideWithHardWalls(this) != null ||
                            EntityManager.doCollideWithSoftWalls(this) != null ||
                            EntityManager.doCollideWithEnemyTank(this) != null ||
                            EntityManager.doCollideWithEnemyCar(this) != null)
                        y -= Math.sin(Math.toRadians(degree)) * SPEED;

                }


            }


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

