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
        Bullet bullet = EntityManager.doCollideWithBullet(this);
        if (bullet != null)
            EntityManager.removeBullet(bullet);

        Cannon cannon = EntityManager.doCollideWithCannon(this);
        if (cannon != null)
            EntityManager.removeCannon(cannon);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.hardWall, (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), width, height, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width - 10, height -10);
    }
}
