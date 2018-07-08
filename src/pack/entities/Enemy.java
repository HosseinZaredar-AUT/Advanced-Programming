package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.MouseManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private boolean up, down, right, left;
    private boolean upFinal, downFinal, rightFinal, leftFinal;
    private Player player;
    private double degree;
    public static double degreeGun;
    private double xMove, yMove;
    private final int SPEED = 6;
    private final int FIRE_Rate = 1; //the less, the faster
    private int fireCounter = 0;
    private double xPlus, yPlus;


    public Enemy(float x, float y, Player player) {
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
            Cannon cannon = EntityManager.createEnemyCannon(x, y, degreeGun);

            while (((Math.abs(cannon.getX() + cannon.xPlus - player.getX()) > 2)
                    || (Math.abs(cannon.getY() + cannon.yPlus - player.getY()) > 2)) && (Math.abs(cannon.getBounds().x) < Game.frameWidth) &&
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
                System.out.println(getBounds());
                if (EntityManager.doCollideWithHardWalls(this) != null) {
                    x -= Math.cos(Math.toRadians(degreeGun)) * SPEED;
                    y -= Math.sin(Math.toRadians(degreeGun)) * SPEED;
                    flag = false;
                }

            } else if (!flag) {
//
                boolean move = true;
                if ((player.xMove==0)&&(player.yMove==0))
                {
                    System.out.println("Adddddddddd");
                    if (player.getY()==y) {
                        if (player.getX() < x) {
                            right=true;
                            rightFinal=true;
                            up=true;
                            upFinal=true;
                        }
                        if (player.getX() > x) {
                            left=true;
                            leftFinal=true;
                            up=true;
                            upFinal=true;
                        }
                    }

                    if (player.getX()==x) {
                        if (player.getY() < y) {
                            right=true;
                            rightFinal=true;
                            down=true;
                            downFinal=true;
                        }
                        if (player.getY() > y) {
                            right=true;
                            rightFinal=true;
                            up=true;
                            upFinal=true;
                        }
                    }
                }
                else {
                    if (x > player.getX()) {

                        left = true;
                        leftFinal = true;
                    }
                    if (x < player.getX()) {
                        right = true;
                        rightFinal = true;
                    }
                    if (y >= player.getY()) {
                        up = true;
                        upFinal = true;
                    }
                    if (y < player.getY()) {
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
                                if ((up==false)&&(upFinal==true))
                                    up=true;
                                if ((down==false)&&(downFinal==true))
                                    down=true;
                            } else if (right) {
                                right=false;
                                if ((up==false)&&(upFinal==true))
                                    up=true;
                                if ((down==false)&&(downFinal==true))
                                    down=true;
                            }
                            else if (down){
                                down =false;
                                if ((right==false)&&(rightFinal==true))
                                    right=true;
                                if ((left==false)&&(leftFinal==true))
                                    left=true;
                            }
                            else if (up) {
                                up=false;
                                if ((right==false)&&(rightFinal==true))
                                    right=true;
                                if ((left==false)&&(leftFinal==true))
                                    left=true;
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


                if (!move) {
                    System.out.println("A");
                }

//
//                if (x - Camera.getXOffset() + width > Game.frameWidth || x - Camera.getXOffset() < 0 ||
//                        y - Camera.getYOffset() + height > Game.frameHeight || y - Camera.getYOffset() < 0) {
//                    x -= Math.cos(Math.toRadians(degree)) * SPEED;
//                    y -= Math.sin(Math.toRadians(degree)) * SPEED;
//
//                } else {
//
//                    y -= Math.sin(Math.toRadians(degree)) * SPEED;
//
//                    if (EntityManager.doCollideWithHardWalls(this) != null ||
//                            EntityManager.doCollideWithSoftWalls(this) != null)
//                        x -= Math.cos(Math.toRadians(degree)) * SPEED;;
//
//                    y += Math.sin(Math.toRadians(degree)) * SPEED;
//                    if (EntityManager.doCollideWithHardWalls(this) != null ||
//                            EntityManager.doCollideWithSoftWalls(this) != null)
//                        y -= Math.sin(Math.toRadians(degree)) * SPEED;

//                }

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

