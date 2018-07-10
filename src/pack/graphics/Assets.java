package pack.graphics;

import pack.utils.ImageLoader;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player, dirt, hardWall, softWall[],
            playerBulletGun[], playerCannonGun[], bullet, cannon,
            repairFood, bulletFood, cannonFood, upgrader,
            artilleryBase, artilleryGun, artilleryDead, mine,
            enemyTank, enemyTankGun, enemyCar, enemyCarGun;


    public static void init() {

        dirt = pack.utils.ImageLoader.load("res/textures/dirt.png");
        player = pack.utils.ImageLoader.load("res/textures/player.png");
        hardWall = pack.utils.ImageLoader.load("res/textures/stone.png");

        playerBulletGun = new BufferedImage[3];

        playerBulletGun[0] = pack.utils.ImageLoader.load("res/textures/playerBulletGun0.png");
        playerBulletGun[1] = pack.utils.ImageLoader.load("res/textures/playerBulletGun1.png");
        playerBulletGun[2] = pack.utils.ImageLoader.load("res/textures/playerBulletGun2.png");

        playerCannonGun = new BufferedImage[4];

        playerCannonGun[0] = pack.utils.ImageLoader.load("res/textures/playerCannonGun0.png");
        playerCannonGun[1] = pack.utils.ImageLoader.load("res/textures/playerCannonGun1.png");
        playerCannonGun[2] = pack.utils.ImageLoader.load("res/textures/playerCannonGun2.png");
        playerCannonGun[3] = pack.utils.ImageLoader.load("res/textures/playerCannonGun3.png");

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

        enemyTank = pack.utils.ImageLoader.load("res/textures/enemyTank.png");
        enemyTankGun = pack.utils.ImageLoader.load("res/textures/enemyTankGun.png");
        enemyCar = pack.utils.ImageLoader.load("res/textures/enemyCar.png");
        enemyCarGun = pack.utils.ImageLoader.load("res/textures/enemyCarGun.png");


    }

}
