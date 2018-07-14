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

    public enum Type {
        DOWN, UP, RIGHT, LEFT;
    }

    private double degreeBase;
    private double degreeGun;
    private final double MAX_ROTATE = 60;
    private float health;
    private boolean alive;
    private final int CANNON_RATE = 160;
    private int cannonCounter = -1;


    public Artillery(float x, float y, Type type, EntityManager entityManager) {
        super(x, y, 100, 100, entityManager);
        health = 2;
        alive = true;

        switch (type) {
            case RIGHT: degreeBase = 360; break;
            case UP: degreeBase = 270; break;
            case DOWN: degreeBase = 90; break;
            case LEFT: degreeBase = 180; break;
        }

    }

    @Override
    public void tick() {
        getDamage();
        shoot();
    }

    private void shoot() {
        if (alive) {

            //when to shoot...
            if (entityManager.deltaXToClosestPlayer(this) < (2 * Game.frameWidth / 3) && entityManager.deltaYToClosestPlayer(this) < (2 * Game.frameHeight / 3)) {
                degreeGun = MouseManager.angleToPlayer(entityManager.getClosestPlayer(this), this);
                if (-360 <= degreeGun - degreeBase && degreeGun - degreeBase <= -270)
                    degreeGun += 360;
                if (degreeGun - degreeBase < -MAX_ROTATE)
                    degreeGun = -MAX_ROTATE + degreeBase;
                if (degreeGun - degreeBase > MAX_ROTATE)
                    degreeGun = MAX_ROTATE + degreeBase;


                if (degreeGun - degreeBase != MAX_ROTATE && degreeGun - degreeBase != -MAX_ROTATE) {
                    if (cannonCounter == CANNON_RATE) {
                        entityManager.createEnemyCannon(x + width / 2, y + height / 2, degreeGun);
                        cannonCounter = -1;
                    }
                    cannonCounter++;

                }

            }
        }
    }

    private void getDamage() {
        if (alive) {

            //GETTING SHOT
            Bullet bullet = entityManager.doCollideWithFriendlyBullet(this);
            if (bullet != null) {
                entityManager.removeFriendlyBullet(bullet);
                health -= Bullet.DAMAGE;
            }

            Cannon cannon = entityManager.doCollideWithFriendlyCannon(this);
            if (cannon != null) {
                entityManager.removeFriendlyCannon(cannon);
                health -= Cannon.DAMAGE;
            }

            if (health <= 0)
                alive = false;

        }
    }


    @Override
    public void render(Graphics2D g) {

        BufferedImage baseImage;
        if (alive)
            baseImage = Assets.artilleryBase;
        else
            baseImage = Assets.artilleryDead;

        AffineTransform baseTransform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
        baseTransform.rotate(Math.toRadians(degreeBase), baseImage.getWidth() / 2, baseImage.getHeight() / 2);
        g.drawImage(baseImage, baseTransform, null);


        if (alive) {
            BufferedImage image = Assets.artilleryGun;
            AffineTransform transform = AffineTransform.getTranslateInstance((int) (x - Camera.getXOffset()), (int) (y - Camera.getYOffset()));
            transform.rotate(Math.toRadians(degreeGun), image.getWidth() / 2, image.getHeight() / 2);

            g.drawImage(image, transform, null);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x + 20, (int)y + 20, width - 40 , height - 40);
    }

}
