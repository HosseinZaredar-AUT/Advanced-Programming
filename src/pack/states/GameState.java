package pack.states;

import pack.world.World;
import pack.entities.Player;
import java.awt.*;

public class GameState extends State{

    private Player player;
    private World world;

    public GameState() {
        player = new Player(100f, 100f);
        world= new World("res/world/worldFile.txt");
    }

    @Override
    public void tick() {
        world.tick();
        player.tick();

    }

    @Override
    public void render(Graphics2D g) {

        world.render(g);
        player.render(g);

    }
}
