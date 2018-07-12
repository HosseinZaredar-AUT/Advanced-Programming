package pack.entities;

import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import java.awt.*;

public class RepairFood extends Entity {

    public RepairFood(float x, float y, EntityManager entityManager) {
        super(x, y, 100, 100, entityManager);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.repairFood, (int)(x - Camera.getXOffset()), (int)(y - Camera.getYOffset()), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof RepairFood))
            return false;

        RepairFood other = (RepairFood) obj;
        return (x == other.x) && (y == other.y);
    }
}
