package pack.entities;

import pack.graphics.*;
import pack.input.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private boolean up, down, right, left;
    private int degree;
    private double degreeGun;

    public Player(float x, float y) {
        super(x, y, 100, 100);
    }

    @Override
    public void tick() {
        up = KeyManager.up;
        down = KeyManager.down;
        left = KeyManager.left;
        right = KeyManager.right;

        if (up)
            y -= 6;
        if (down)
            y += 6;
        if (left)
            x -= 6;
        if (right)
            x += 6;

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
        AffineTransform transformGun = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()+18), (int) (y - Camera.getYOffset()+13));
        transformGun.rotate(Math.toRadians(degreeGun), imageGun.getWidth()/4 +4 , imageGun.getHeight()/4 +4);


        g.drawImage(imageGun, transformGun, null);
    }

}
