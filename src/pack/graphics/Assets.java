package pack.graphics;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player, dirt, hardWall, softWall[],
            playerBulletGun, playerCannonGun, bullet, fire,
            repairFood, BulletFood, CannonFood;

    public static void init() {

        dirt = pack.utils.ImageLoader.load("res/textures/dirt.png");
        player = pack.utils.ImageLoader.load("res/textures/player.png");
        hardWall = pack.utils.ImageLoader.load("res/textures/stone.png");
        playerBulletGun = pack.utils.ImageLoader.load("res/textures/playerBulletGun.png");
        playerCannonGun = pack.utils.ImageLoader.load("res/textures/playerCannonGun.png");
        bullet = pack.utils.ImageLoader.load("res/textures/bullet.png");
        fire = pack.utils.ImageLoader.load("res/textures/fire.png");
        softWall = new BufferedImage[4];
        softWall[0] = pack.utils.ImageLoader.load("res/textures/softWall1.png");
        softWall[1] = pack.utils.ImageLoader.load("res/textures/softWall2.png");
        softWall[2] = pack.utils.ImageLoader.load("res/textures/softWall3.png");
        softWall[3] = pack.utils.ImageLoader.load("res/textures/softWall4.png");
        repairFood = pack.utils.ImageLoader.load("res/textures/repairFood.png");
        CannonFood = pack.utils.ImageLoader.load("res/textures/cannonFood.png");
        BulletFood = pack.utils.ImageLoader.load("res/textures/bulletFood.png");

    }

}
