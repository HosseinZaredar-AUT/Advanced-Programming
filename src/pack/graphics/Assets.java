package pack.graphics;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player, dirt, hardWall, softWall[], playerGun, bullet;

    public static void init() {

        dirt = pack.utils.ImageLoader.load("res/textures/dirt.png");
        player = pack.utils.ImageLoader.load("res/textures/player.png");
        hardWall = pack.utils.ImageLoader.load("res/textures/stone.png");
        playerGun = pack.utils.ImageLoader.load("res/textures/tankGun1.png");
        bullet = pack.utils.ImageLoader.load("res/textures/bullet.png");
        softWall = new BufferedImage[4];
        softWall[0] = pack.utils.ImageLoader.load("res/textures/softWall1.png");
        softWall[1] = pack.utils.ImageLoader.load("res/textures/softWall2.png");
        softWall[2] = pack.utils.ImageLoader.load("res/textures/softWall3.png");
        softWall[3] = pack.utils.ImageLoader.load("res/textures/softWall4.png");

    }

}
