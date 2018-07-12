package pack.entities;

import pack.entities.manager.EntityManager;
import pack.utils.FileLoader;

import java.io.Serializable;


public class EntityWorld implements Serializable {


    private int widthInEntity;
    private int heightInEntity;
    private int entityWidth = 100;
    private int entityHeight = 100;
    private EntityManager entityManager;

    public EntityWorld(EntityManager entityManager) {
        this.entityManager = entityManager;
        loadWorld("res/entityWorld/entityWorld.txt");


    }




    private void loadWorld(String path) {
        String file = FileLoader.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        // not automatic

        widthInEntity = Integer.parseInt(tokens[0]);
        heightInEntity = Integer.parseInt(tokens[1]);



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
        for (int y = 0; y < widthInEntity ; y++) {
            for (int x = 0; x < heightInEntity; x++) {
                z = tokens[x + (y * 20) + 2].charAt(0);

                if (z == 'b') entityManager.createBulletFood(x * entityWidth, y * entityHeight);
                if (z == 'c') entityManager.createCannonFood(x * entityWidth, y * entityHeight);
                if (z == 'd') entityManager.createEnemyTank(x * entityWidth, y * entityHeight);
                if (z == 'e') entityManager.createEnemyCar(x * entityWidth, y * entityHeight);
                if (z == 'f') entityManager.createHardWall(x * entityWidth, y * entityHeight);
                if (z == 'g') entityManager.createMine(x * entityWidth, y * entityHeight);
                if (z == 'h') entityManager.createServerPlayer(x * entityWidth, y * entityHeight);
                if (z == 'i') entityManager.createRepairFood(x * entityWidth, y * entityHeight);
                if (z == 'j') entityManager.createSoftWall(x * entityWidth, y * entityHeight);
                if (z == 'k') entityManager.createUpgrader(x * entityWidth, y * entityHeight);
                if (z == 'l') entityManager.createArtillery(x * entityWidth, y * entityHeight, Artillery.Type.LEFT);
                if (z == 'm') entityManager.createArtillery(x * entityWidth, y * entityHeight, Artillery.Type.RIGHT);
                if (z == 'n') entityManager.createArtillery(x * entityWidth, y * entityHeight, Artillery.Type.DOWN);
                if (z == 'o') entityManager.createArtillery(x * entityWidth, y * entityHeight, Artillery.Type.UP);
            }
        }
    }


}
