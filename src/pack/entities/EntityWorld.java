package pack.entities;

import pack.entities.manager.EntityManager;
import pack.utils.FileLoader;


public class EntityWorld {


    public static int widthInEntity;
    public static int heightInEntity;

    public EntityWorld() {

        loadWorld("res/entityWorld/entityWorld.txt");

    }




    private void loadWorld(String path) {
        String file = FileLoader.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        // not automatic

        widthInEntity = 100;
        heightInEntity = 100;



        /*

        BulletFood : b
        CannonFood : c
        EnemyTank : d
        EnemyCar : e
        HardWall : f
        Mine : g
        Player : h
        RepairFood : i
        SoftWall : j
        Upgrader : k
        َ Artillery_Left : l
        َ Artillery_Right : m
        َ Artillery_Down : n
        َ Artillery_Up : o



        z : null
        */

        char z = ' ';
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                z = tokens[x+y*20].charAt(0);

                if (z == 'b') EntityManager.createBulletFood(x*widthInEntity,y*heightInEntity);
                if (z == 'c') EntityManager.createCannonFood(x*widthInEntity,y*heightInEntity);
                if (z == 'd') EntityManager.createEnemyTank(x*widthInEntity,y*heightInEntity);
                if (z == 'e') EntityManager.createEnemyCar(x*widthInEntity,y*heightInEntity);
                if (z == 'f') EntityManager.createHardWall(x*widthInEntity,y*heightInEntity);
                if (z == 'g') EntityManager.createMine(x*widthInEntity,y*heightInEntity);
                if (z == 'h') EntityManager.createPlayer(x*widthInEntity,y*heightInEntity);
                if (z == 'i') EntityManager.createRepairFood(x*widthInEntity,y*heightInEntity);
                if (z == 'j') EntityManager.createSoftWall(x*widthInEntity,y*heightInEntity);
                if (z == 'k') EntityManager.createUpgrader(x*widthInEntity,y*heightInEntity);
                if (z == 'l') EntityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.LEFT);
                if (z == 'm') EntityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.RIGHT);
                if (z == 'n') EntityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.DOWN);
                if (z == 'o') EntityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.UP);

            }
        }



    }

    public static int getWidthInEntity() {
        return widthInEntity;
    }

    public static int getHeightInEntity() {

        return heightInEntity;
    }

}
