package pack.entities;

import java.awt.*;

public abstract class Entity {

    protected float x, y;
    protected int width, height;

    public Entity(float x, float y, int width, int height){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public abstract Rectangle getBounds();


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
