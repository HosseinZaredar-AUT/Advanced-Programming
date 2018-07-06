package pack.graphics;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player, dirt, stone,playerGun;

    public static void init() {

        dirt = pack.utils.ImageLoader.load("res/textures/dirt.png");
        player = pack.utils.ImageLoader.load("res/textures/player.png");
        stone = pack.utils.ImageLoader.load("res/textures/stone.png");
        playerGun = pack.utils.ImageLoader.load("res/textures/tankGun1.png");

    }

}
