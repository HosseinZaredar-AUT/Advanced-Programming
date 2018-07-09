package pack.entities;

import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;

import java.awt.*;

public class HardWall extends Entity {

    public HardWall(float x, float y) {
        super(x, y, 100, 100);
    }

    @Override
    public void tick() {
        Bullet friendlyBullet = EntityManager.doCollideWithFriendlyBullet(this);
        if (friendlyBullet != null)
            EntityManager.removeFriendlyBullet(friendlyBullet);

        Cannon friendlyCannon = EntityManager.doCollideWithFriendlyCannon(this);
        if (friendlyCannon != null)
            EntityManager.removeFriendlyCannon(friendlyCannon);

        Bullet enemyBullet = EntityManager.doCollideWithEnemyBullet(this);
        if (enemyBullet != null)
            EntityManager.removeEnemyBullet(enemyBullet);

        Cannon enemyCannon = EntityManager.doCollideWithEnemyCannon(this);
        if (enemyCannon != null)
            EntityManager.removeEnemyCannon(enemyCannon);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.hardWall, (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), width, height, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);

    }
}
