package pack.graphics;

import pack.utils.ImageLoader;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player, dirt, hardWall, softWall[],
            playerBulletGun, playerCannonGun, bullet, cannon,
            repairFood, bulletFood, cannonFood, upgrader,
            artilleryBase, artilleryGun, artilleryDead, mine;


    public static void init() {

        dirt = pack.utils.ImageLoader.load("res/textures/dirt.png");
        player = pack.utils.ImageLoader.load("res/textures/player.png");
        hardWall = pack.utils.ImageLoader.load("res/textures/stone.png");
        playerBulletGun = pack.utils.ImageLoader.load("res/textures/playerBulletGun.png");
        playerCannonGun = pack.utils.ImageLoader.load("res/textures/playerCannonGun.png");
        bullet = pack.utils.ImageLoader.load("res/textures/bullet.png");
        cannon = pack.utils.ImageLoader.load("res/textures/cannon.png");

        softWall = new BufferedImage[4];
        softWall[0] = pack.utils.ImageLoader.load("res/textures/softWall1.png");
        softWall[1] = pack.utils.ImageLoader.load("res/textures/softWall2.png");
        softWall[2] = pack.utils.ImageLoader.load("res/textures/softWall3.png");
        softWall[3] = pack.utils.ImageLoader.load("res/textures/softWall4.png");
        repairFood = pack.utils.ImageLoader.load("res/textures/repairFood.png");
        cannonFood = pack.utils.ImageLoader.load("res/textures/cannonFood.png");
        bulletFood = pack.utils.ImageLoader.load("res/textures/bulletFood.png");
        upgrader = pack.utils.ImageLoader.load("res/textures/upgrader.png");
        artilleryBase = pack.utils.ImageLoader.load("res/textures/artilleryBase.png");
        artilleryGun = pack.utils.ImageLoader.load("res/textures/artilleryGun.png");
        artilleryDead = pack.utils.ImageLoader.load("res/textures/artilleryDead.png");

        mine = pack.utils.ImageLoader.load("res/textures/mine.png");



    }

}
