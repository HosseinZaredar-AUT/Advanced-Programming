package pack.entities;

import pack.Game;
import pack.entities.manager.EntityManager;
import pack.graphics.Camera;
import pack.tiles.Tile;
import pack.utils.FileLoader;
import java.awt.*;

public class EntityWorld {


    public static int witdthInEntity;
    public static int heightInEntity;

    public EntityWorld( String path) {

        loadWorld(path);
    }




    private void loadWorld(String path) {
        String file = FileLoader.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        // not automatic
        witdthInEntity = 100;
        heightInEntity = 100;

//5*7
        /*
        Artillery : a
        BulletFood : b
        CannonFood : c
        Enemy : d
        EnemySimple : e
        HardWall : f
        Mine : g
        Player : h
        RepairFood : i
        SoftWall : j
       Upgrader : k
       z : null
         */

        char z = ' ';
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                z = tokens[x+y*20].charAt(0);
                if (z == 'a') { EntityManager.createArtillery(x*witdthInEntity,y*heightInEntity);}
                if (z == 'b') { EntityManager.createBulletFood(x*witdthInEntity,y*heightInEntity);}
                if (z == 'c') { EntityManager.createCannonFood(x*witdthInEntity,y*heightInEntity);}
                if (z == 'd') { EntityManager.createEnemy(x*witdthInEntity,y*heightInEntity);}
                if (z == 'e') { EntityManager.createEnemySimple(x*witdthInEntity,y*heightInEntity);}
                if (z == 'f') { EntityManager.createHardWall(x*witdthInEntity,y*heightInEntity);}
                if (z == 'g') { EntityManager.createMine(x*witdthInEntity,y*heightInEntity);}
                if (z == 'h') { EntityManager.createPlayer(x*witdthInEntity,y*heightInEntity);}
                if (z == 'i') { EntityManager.createRepairFood(x*witdthInEntity,y*heightInEntity);}
                if (z == 'j') { EntityManager.createSoftWall(x*witdthInEntity,y*heightInEntity);}
                if (z == 'k') { EntityManager.createUpgrader(x*witdthInEntity,y*heightInEntity);}
            }
        }


    }

    public static int getwitdthInEntity() {
        return witdthInEntity;
    }

    public static int getheightInEntity() {
        return heightInEntity;
    }

}
