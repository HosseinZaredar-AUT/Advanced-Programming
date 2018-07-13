package pack.states;

import pack.Game;
import pack.entities.HardWall;
import pack.entities.players.ClientPlayer;
import pack.entities.manager.EntityManager;
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
    private Game game;
    private EntityManager entityManager;
    private ClientPlayer me;
    private boolean gotHardWalls = false;
    private ArrayList<HardWall> hardWalls;
    private int count = 0;


    public ClientGameState(Game game) {
        world = new World("res/world/worldFile.txt");
        this.game = game;

    }


        @Override
    public void tick() {


         //GETTING STATIC STUFF FOR 1 TIME

         if (!gotHardWalls) {

             InputStream wallsIn = Client.getInputStream();
             try {
                 ObjectInputStream wallReader = new ObjectInputStream(wallsIn);
                 hardWalls = (ArrayList<HardWall>) wallReader.readObject();

             } catch (Exception ex) {
                 game.setState(new MainMenuState(game));
             }

             gotHardWalls = true;
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
            game.setState(new MainMenuState(game));
        }


        //GETTING POSITION TO CENTER CAMERA
        float x, y;
        byte[] buffer = new byte[1024];
        int size = -1;
        InputStream positionIn = Client.getInputStream();
        try {
            size = positionIn.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String position = new String(buffer, 0, size);
        String[] tokens = position.split(",");
        x = Float.parseFloat(tokens[0]);
        y = Float.parseFloat(tokens[1]);

        Camera.centerOnEntity(x, y, 100, 100);

        InputStream managerIn = Client.getInputStream();
            try {
                ObjectInputStream managerReader = new ObjectInputStream(managerIn);
                entityManager = (EntityManager) managerReader.readObject();

                //SAVE INTO FILE TO CHECK THE SIZE


                //

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    @Override
    public void render(Graphics2D g) {
        world.render(g);
        entityManager.render(g, false);
        for (HardWall h : hardWalls)
            h.render(g);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
