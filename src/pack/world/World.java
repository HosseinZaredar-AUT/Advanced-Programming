package pack.world;

import pack.Game;
import pack.graphics.Camera;
import pack.tiles.Tile;
import pack.utils.FileLoader;
import java.awt.*;

public class World {


    public static int widthInTiles;
    public static int heightInTiles;
    private int[][] tiles;

    public World( String path) {

        loadWorld(path);
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        int xStart = (int) Math.max(0, Camera.getXOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(widthInTiles, (Camera.getXOffset() + Game.frameWidth) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, Camera.getYOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(heightInTiles, (Camera.getYOffset() + Game.frameHeight) / Tile.TILEHEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - Camera.getXOffset()),
                        (int) (y * Tile.TILEHEIGHT - Camera.getYOffset()));
            }
        }
    }

    public Tile getTile(int x, int y) {
        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null)
            return Tile.dirtTile;
        return t;
    }

    private void loadWorld(String path) {
        String file = FileLoader.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        widthInTiles = 20;
        heightInTiles = 20;


        tiles = new int[widthInTiles][heightInTiles];
        for (int y = 0; y < heightInTiles; y++) {
            for (int x = 0; x < widthInTiles; x++) {
                tiles[x][y] = FileLoader.parseInt(tokens[(x + y * widthInTiles) + 4]);
            }
        }
    }

    public static int getWidthInTiles() {
        return widthInTiles;
    }

    public static int getHeightInTiles() {
        return heightInTiles;
    }

}
