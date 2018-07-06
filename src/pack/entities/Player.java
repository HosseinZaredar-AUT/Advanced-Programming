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
    private int xMove, yMove;
    private final int SPEED = 6;

    public Player(float x, float y) {
        super(x, y, 100, 100);

    }

    @Override
    public void tick() {
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

        //Collision detection
        if (x - Camera.getXOffset() + width > Game.frameWidth || x - Camera.getXOffset() < 0 ||
                y - Camera.getYOffset() + height > Game.frameHeight || y - Camera.getYOffset() < 0 ||
                EntityManager.doCollideWithWalls(this) != null) {
            x -= xMove;
            y -= yMove;
        }

        degreeGun = MouseManager.angle;

        Camera.centerOnEntity(this);
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


        BufferedImage imageGun = Assets.playerGun;
        AffineTransform transformGun = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset() + 18), (int) (y - Camera.getYOffset()+ 13));
        transformGun.rotate(Math.toRadians(degreeGun), imageGun.getWidth() / 4 + 4 , imageGun.getHeight() / 4 + 4);


        g.drawImage(imageGun, transformGun, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x + 6, (int)y + 6, width - 6, height - 6);
    }

}
