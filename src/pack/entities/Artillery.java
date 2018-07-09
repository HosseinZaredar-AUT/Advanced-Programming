package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.MouseManager;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Artillery extends Entity {

    private double degreeGun;
    private float health;
    private boolean alive;
    private final int CANNON_RATE = 180;
    private int cannonCounter = -1;


    public Artillery(float x, float y) {
        super(x, y, 100, 100);
        health = 2;
        alive = true;
    }

    @Override
    public void tick() {

        if (alive) {

            //todo this is not a good calculation
            if ((x > Camera.getXOffset()) && (x < (Camera.getXOffset() + Game.frameWidth)) && (y > Camera.getYOffset())
                    && (y < (Camera.getYOffset() + Game.frameHeight))) {
                degreeGun = MouseManager.angleWithEnemy(x, y) - 180;
                if (degreeGun > 50)
                    degreeGun = 50;
                if (degreeGun < -50)
                    degreeGun = -50;

                //SHOOTING
                if (cannonCounter == CANNON_RATE) {
                    EntityManager.createEnemyCannon(x + width / 2 + 16, y + height / 2, degreeGun + 180);
                    cannonCounter = -1;
                }
                cannonCounter++;



            }

            //GETTING SHOT
            Bullet bullet = EntityManager.doCollideWithFriendlyBullet(this);
            if (bullet != null) {
                EntityManager.removeFriendlyBullet(bullet);
                health -= Bullet.DAMAGE;
            }

            Cannon cannon = EntityManager.doCollideWithFriendlyCannon(this);
            if (cannon != null) {
                EntityManager.removeFriendlyCannon(cannon);
                health -= Cannon.DAMAGE;
            }

            if (health <= 0)
                alive = false;

        }


    }

    @Override
    public void render(Graphics2D g) {
        if (alive)
            g.drawImage(Assets.artilleryBase, (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), null);
        else
            g.drawImage(Assets.artilleryDead, (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), null);

        if (alive) {
            BufferedImage image = Assets.artilleryGun;
            AffineTransform transform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
            transform.rotate(Math.toRadians(degreeGun), image.getWidth() / 2 + 16, image.getHeight() / 2);

            g.drawImage(image, transform, null);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x , (int)y , width , height);
    }

}
