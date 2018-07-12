package pack.entities;

import pack.entities.manager.EntityManager;
import pack.utils.FileLoader;

import java.io.Serializable;


public class EntityWorld implements Serializable {


    private int widthInEntity;
    private static int heightInEntity;
    private EntityManager entityManager;

    public EntityWorld(EntityManager entityManager) {
        this.entityManager = entityManager;
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

                if (z == 'b') entityManager.createBulletFood(x*widthInEntity,y*heightInEntity);
                if (z == 'c') entityManager.createCannonFood(x*widthInEntity,y*heightInEntity);
                if (z == 'd') entityManager.createEnemyTank(x*widthInEntity,y*heightInEntity);
                if (z == 'e') entityManager.createEnemyCar(x*widthInEntity,y*heightInEntity);
                if (z == 'f') entityManager.createHardWall(x*widthInEntity,y*heightInEntity);
                if (z == 'g') entityManager.createMine(x*widthInEntity,y*heightInEntity);
                if (z == 'h') entityManager.createPlayer(x*widthInEntity,y*heightInEntity);
                if (z == 'i') entityManager.createRepairFood(x*widthInEntity,y*heightInEntity);
                if (z == 'j') entityManager.createSoftWall(x*widthInEntity,y*heightInEntity);
                if (z == 'k') entityManager.createUpgrader(x*widthInEntity,y*heightInEntity);
                if (z == 'l') entityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.LEFT);
                if (z == 'm') entityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.RIGHT);
                if (z == 'n') entityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.DOWN);
                if (z == 'o') entityManager.createArtillery(x*widthInEntity,y*heightInEntity, Artillery.Type.UP);
            }
        }
    }


    public int getWidthInEntity() {
        return widthInEntity;
    }

    public int getHeightInEntity() {

        return heightInEntity;
    }

}
