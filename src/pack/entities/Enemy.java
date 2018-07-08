package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.KeyManager;
import pack.input.MouseManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    private Player player;
    private double degree;
    public static double degreeGun;
    private double xMove, yMove;
    private final int SPEED = 6;
    private final int FIRE_Rate = 6; //the less, the faster
    private int fireCounter = 0;
    private double xPlus, yPlus;


    public Enemy(float x, float y, Player player) {
        super(x, y, 100, 100);
        this.player = player;

    }

    @Override
    public void tick() {
        if ((x > Camera.getXOffset()) && (x < (Camera.getXOffset() + Game.frameWidth)) && (y > Camera.getYOffset())
                && (y < (Camera.getYOffset() + Game.frameHeight))) {
            degreeGun = MouseManager.angleWithEnemy(x, y);
            //same gun and tank

            boolean flag = true;
            if (fireCounter == FIRE_Rate) {
                Cannon cannon = EntityManager.createCannon(x, y, degreeGun);
                fireCounter = -1;


                while ((Math.abs((cannon.getX() + cannon.xPlus) - player.getX()) > 10) && (Math.abs((cannon.getY() + cannon.yPlus) - player.getY()) > 10)) {
                    if (EntityManager.doCollideWithHardWalls(cannon) != null) {

                        EntityManager.removeCannon(cannon);
                        flag = false;
                        break;
                    }

                    cannon.xPlus += Math.cos(Math.toRadians(degreeGun)) * 6;
                    cannon.yPlus += Math.sin(Math.toRadians(degreeGun)) * 6;

                }

                cannon.xPlus = 0;
                cannon.yPlus = 0;
            }

            fireCounter++;

            degree = degreeGun;

            double temp = degree;
            float xTemp = x;
            float yTemp = y;
            for (int i = 0; i < 180; i += 2) {
                degree = temp + i;

                xPlus =  (Math.cos(Math.toRadians(degree)) * SPEED);
                yPlus =  (Math.sin(Math.toRadians(degree)) * SPEED);

                System.out.println(Math.cos(Math.toRadians(degree))*SPEED + " degree");
                if (EntityManager.doCollideWithHardWalls(this) == null) {
                    System.out.println(getBounds());
                    break;
                }

                degree = temp - i;

                xPlus = (int) (Math.cos(Math.toRadians(degree)) * SPEED);
                yPlus = (int) (Math.sin(Math.toRadians(degree)) * SPEED);

                if (EntityManager.doCollideWithHardWalls(this) == null) {
                    break;
                }
            }
            x += xPlus;
            y += yPlus;

            xPlus = 0;
            yPlus = 0;

            if (x - Camera.getXOffset() + width > Game.frameWidth || x - Camera.getXOffset() < 0 ||
                    y - Camera.getYOffset() + height > Game.frameHeight || y - Camera.getYOffset() < 0
                    || (EntityManager.doCollideWithPlayer(this) != null)) {
                x = xTemp;
                y = yTemp;
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
        if (xPlus<0)
            xPlus+=-3;
        if (yPlus<0)
            yPlus+=-3;

        if (xPlus>0)
            xPlus=+3;
        if (yPlus>0)
            yPlus+=3;

        return new Rectangle((int) x +(int)(xPlus) + 6, (int) y + (int)(yPlus) + 6, width - 6, height - 6);
    }

}

