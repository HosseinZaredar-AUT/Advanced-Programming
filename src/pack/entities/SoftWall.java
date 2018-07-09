package pack.entities;

import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;

import java.awt.*;

public class SoftWall extends Entity {

    private final float MAX_HEALTH = 4;
    private float health;

    public SoftWall(float x, float y) {
        super(x, y, 100, 100);
        health = MAX_HEALTH;
    }

    @Override
    public void tick() {
        getDamage();
    }

    private void getDamage() {

        Cannon friendlyCannon = EntityManager.doCollideWithFriendlyCannon(this);
        if (friendlyCannon != null) {
            EntityManager.removeFriendlyCannon(friendlyCannon);
            health -= Cannon.DAMAGE;
        }

        Bullet friendlyBullet = EntityManager.doCollideWithFriendlyBullet(this);
        if (friendlyBullet != null) {
            EntityManager.removeFriendlyBullet(friendlyBullet);
            health -= Bullet.DAMAGE;
        }

        Bullet enemyBullet = EntityManager.doCollideWithEnemyBullet(this);
        if (enemyBullet != null) {
            EntityManager.removeEnemyBullet(enemyBullet);
            health -= Bullet.DAMAGE;
        }

        Cannon enemyCannon = EntityManager.doCollideWithEnemyCannon(this);
        if (enemyCannon != null) {
            EntityManager.removeEnemyCannon(enemyCannon);
            health -= Cannon.DAMAGE;
        }

        if (health <= 0) {
            EntityManager.removeSoftWall(this);

        }
    }


    @Override
    public void render(Graphics2D g) {
        if (health > 3)
            g.drawImage(Assets.softWall[0], (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), width, height, null);
        else if (health > 2)
            g.drawImage(Assets.softWall[1], (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), width, height, null);
        else if (health > 1)
            g.drawImage(Assets.softWall[2], (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), width, height, null);
        else if (health > 0)
        g.drawImage(Assets.softWall[3], (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), width, height, null);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof SoftWall))
            return false;

        SoftWall other = (SoftWall) obj;
        return (x == other.x) && (y == other.y);
    }

}
