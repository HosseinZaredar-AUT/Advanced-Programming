package pack.graphics;

import pack.Game;
import pack.world.World;
import pack.entities.Entity;
import pack.input.MouseManager;
import pack.tiles.Tile;


public class Camera {

    private static float xOffset, yOffset;

    //not important
    private static float entityX, entityY;

    public static float getEntityX() {
        return entityX;
    }

    public static float getEntityY() {
        return entityY;
    }

    public Camera(float startXOffset, float startYOffset) {
        xOffset = startXOffset;
        yOffset = startYOffset;
    }

    public static void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > (World.getWidthInTiles() * Tile.TILEWIDTH - Game.frameWidth))
            xOffset = World.getWidthInTiles() * Tile.TILEWIDTH - Game.frameWidth;

        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > (World.getHeightInTiles() * Tile.TILEHEIGHT - Game.frameHeight))
            yOffset = World.getHeightInTiles() * Tile.TILEHEIGHT - Game.frameHeight;
    }

    public static void centerOnEntity(float x, float y, int width, int height) {
        xOffset = x - Game.frameWidth / 2 + width / 2 + (float) MouseManager.dx / 5;
        yOffset = y - Game.frameHeight / 2 + height / 2 + (float) MouseManager.dy / 5;

        checkBlankSpace();

        //not important but necessary
        entityX = x + (width / 2);
        entityY = y + (height / 2);
    }


    public static float getXOffset() {
        return xOffset;
    }

    public static float getYOffset() {
        return yOffset;
    }


}
