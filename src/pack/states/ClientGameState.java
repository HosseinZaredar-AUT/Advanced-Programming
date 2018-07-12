package pack.states;

import pack.entities.players.ClientPlayer;
import pack.entities.manager.EntityManager;
import pack.graphics.Camera;
import pack.input.KeyManager;
import pack.input.MouseManager;
import pack.network.Client;
import pack.world.World;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

public class ClientGameState extends State {

    private World world;
    EntityManager entityManager;
    ClientPlayer me;


    public ClientGameState() {
        world = new World("res/world/worldFile.txt");
    }



    @Override
    public void tick() {

        world.tick();

        //GETTING ME
        InputStream in = Client.getInputStream();
        try {
            ObjectInputStream objectIn = new ObjectInputStream(in);
            me = (ClientPlayer) objectIn.readObject();
            Camera.centerOnEntity(me);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }


        //GETTING ENTITY MANAGER
        InputStream in2 = Client.getInputStream();
        try {
            ObjectInputStream objectIn2 = new ObjectInputStream(in2);
            entityManager = (EntityManager) objectIn2.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void render(Graphics2D g) {
        world.render(g);
        entityManager.render(g);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
