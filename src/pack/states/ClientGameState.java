package pack.states;

import pack.Game;
import pack.entities.BarbedWire;
import pack.entities.Bush;
import pack.entities.HardWall;
import pack.entities.players.ClientPlayer;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.graphics.Camera;
import pack.input.KeyManager;
import pack.input.MouseManager;
import pack.network.Client;
import pack.world.World;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ClientGameState extends State {

    private World world;
    private EntityManager entityManager;
    private boolean gotStatics = false;
    private ArrayList<HardWall> hardWalls;
    private ArrayList<BarbedWire> barbedWires;
    private ArrayList<Bush> bushes;
    private int c = 0;

    private int health;
    private int cannon;
    private int bullet;


    public ClientGameState() {
        world = new World("res/world/worldFile.txt");

    }


        @Override
    public void tick() {


         //GETTING STATIC STUFF FOR 1 TIME

         if (!gotStatics) {

             InputStream wallsIn = Client.getInputStream();
             try {
                 ObjectInputStream wallReader = new ObjectInputStream(wallsIn);
                 hardWalls = (ArrayList<HardWall>) wallReader.readObject();

             } catch (Exception ex) {
                 Game.setState(new MainMenuState());
                 return;
             }

             InputStream barbedWiresIn = Client.getInputStream();
             try {
                 ObjectInputStream barbedWireReader = new ObjectInputStream(barbedWiresIn);
                 barbedWires = (ArrayList<BarbedWire>) barbedWireReader.readObject();

             } catch (Exception ex) {
                 Game.setState(new MainMenuState());
                 return;
             }

             InputStream BushesIn = Client.getInputStream();
             try {
                 ObjectInputStream BushesReader = new ObjectInputStream(BushesIn);
                 bushes = (ArrayList<Bush>) BushesReader.readObject();

             } catch (Exception ex) {
                 Game.setState(new MainMenuState());
                 return;
             }


             gotStatics = true;
         }

         //SENDING CHEAT

            StringBuilder cheat = new StringBuilder();
            if (KeyManager.fullLife) {
                cheat.append("1");
                KeyManager.fullLife = false;
            } else
                cheat.append("0");

            if (KeyManager.fullCB) {
                cheat.append("1");
                KeyManager.fullCB =false;
            } else
                cheat.append("0");

            if (KeyManager.upgradeWeapon) {
                cheat.append("1");
                KeyManager.upgradeWeapon = false;
            } else
                cheat.append("0");

            OutputStream cheatOut = Client.getOutputStream();
            try {
                cheatOut.write(cheat.toString().getBytes());
            } catch (Exception ex) {
                Game.setState(new MainMenuState());
                return;
            }

        //SENDING INFO
        OutputStream out = Client.getOutputStream();
        StringBuilder stringBuilder = new StringBuilder();
        if (KeyManager.up)
            stringBuilder.append("1,");
        else
            stringBuilder.append("0,");

        if (KeyManager.down)
            stringBuilder.append("1,");
        else
            stringBuilder.append("0,");

        if (KeyManager.right)
            stringBuilder.append("1,");
        else
            stringBuilder.append("0,");

        if (KeyManager.left)
            stringBuilder.append("1,");
        else
            stringBuilder.append("0,");

        if (MouseManager.rightMouseButton)
            stringBuilder.append("1,");
        else
            stringBuilder.append("0,");

        if (MouseManager.leftMouseButton)
            stringBuilder.append("1,");
        else
            stringBuilder.append("0,");

        stringBuilder.append(MouseManager.angle);

        try {
            out.write(stringBuilder.toString().getBytes());
        } catch (IOException e) {
            Game.setState(new MainMenuState());
            return;
        }


        //GETTING PLAYERS STATE
        byte[] buffer = new byte[1024];
        int size = -1;
        InputStream positionIn = Client.getInputStream();
        try {
            size = positionIn.read(buffer);
        } catch (IOException e) {
            Game.setState(new MainMenuState());
            return;
        }

        String position = new String(buffer, 0, size);
        String[] tokens = position.split(",");

        float x = 0,y = 0;
        try {
            health = Integer.parseInt(tokens[0]);
            cannon = Integer.parseInt(tokens[1]);
            bullet = Integer.parseInt(tokens[2]);
            x = Float.parseFloat(tokens[3]);
            y = Float.parseFloat(tokens[4]);
        } catch (Exception ex) {

        }

        Camera.centerOnEntity(x, y, 100, 100);

        InputStream managerIn = Client.getInputStream();
            try {
                ObjectInputStream managerReader = new ObjectInputStream(managerIn);
                entityManager = (EntityManager) managerReader.readObject();


                //SAVE INTO FILE TO CHECK THE SIZE
//                c++;
//                if (c == 50) {
//                    try (FileOutputStream fout = new FileOutputStream("manager.bin");
//                         ObjectOutputStream ooo = new ObjectOutputStream(fout)) {
//
//                        ooo.writeObject(entityManager);
//                    }
//                }

                //

            } catch (Exception ex) {
                Game.setState(new MainMenuState());
                return;
            }
        }

    @Override
    public void render(Graphics2D g) {
        //THIS PART OF CODE DOESN'T RUN AT ALL, (BUG)
        if (entityManager.gameWin || entityManager.gameOver) {
            System.out.println("finish");
            Game.setState(new MainMenuState());
            return;
        }
        world.render(g);
        entityManager.render(g, false);
        for (HardWall h : hardWalls)
            if ((h.getX() + h.getWidth() > Camera.getXOffset()) && (h.getX() < (Camera.getXOffset() + Game.frameWidth)) && (h.getY() + h.getHeight() > Camera.getYOffset())
                    && (h.getY() < (Camera.getYOffset() + Game.frameHeight)))
            h.render(g);

        for (BarbedWire b : barbedWires)
            if ((b.getX() + b.getWidth() > Camera.getXOffset()) && (b.getX() < (Camera.getXOffset() + Game.frameWidth)) && (b.getY() + b.getHeight() > Camera.getYOffset())
                    && (b.getY() < (Camera.getYOffset() + Game.frameHeight)))
            b.render(g);

        for (Bush b : bushes)
            if ((b.getX() + b.getWidth() > Camera.getXOffset()) && (b.getX() < (Camera.getXOffset() + Game.frameWidth)) && (b.getY() + b.getHeight() > Camera.getYOffset())
                    && (b.getY() < (Camera.getYOffset() + Game.frameHeight)))
            b.render(g);

        renderPlayerSate(g);
    }

    private void renderPlayerSate(Graphics2D g) {
        g.drawImage(Assets.cannonNum, 15, 40, null);
        g.drawImage(Assets.bulletNum, 15, 110, null);
        g.drawImage(Assets.healthNum, 15, 180, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.drawString(cannon + "", 40, 110);
        g.drawString(bullet + "", 40, 180);
        g.drawString((int) health + "", 40, 225);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
