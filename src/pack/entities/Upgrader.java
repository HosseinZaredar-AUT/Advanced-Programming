package pack.entities;

import pack.graphics.Assets;
import pack.graphics.Camera;

import java.awt.*;

public class Upgrader extends Entity {


    public Upgrader(float x, float y) {
        super(x, y, 100, 100);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.upgrader, (int)(x - Camera.getXOffset()), (int)(y- Camera.getYOffset()), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Upgrader))
            return false;

        Upgrader other = (Upgrader) obj;
        return (x == other.x) && (y == other.y);
    }

}
