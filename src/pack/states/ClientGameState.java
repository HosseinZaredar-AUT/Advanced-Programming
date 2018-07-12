package pack.states;

import pack.Game;
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
    private Game game;
    private EntityManager entityManager;
    private ClientPlayer me;


    public ClientGameState(Game game) {
        world = new World("res/world/worldFile.txt");
        this.game = game;
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

        } catch (Exception ex) {
            game.setState(new MainMenuState(game));
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


        //GETTING ENTITY MANAGER
        InputStream in2 = Client.getInputStream();
        try {
            ObjectInputStream objectIn2 = new ObjectInputStream(in2);
            entityManager = (EntityManager) objectIn2.readObject();

        } catch (Exception ex) {
            game.setState(new MainMenuState(game));
        }


    }

    @Override
    public void render(Graphics2D g) {
        world.render(g);
        entityManager.render(g);
        me.renderPlayerState(g);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
