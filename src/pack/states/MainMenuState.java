package pack.states;

import pack.Game;
import pack.ThreadPool;
import pack.entities.manager.EntityManager;
import pack.graphics.Assets;
import pack.input.MouseManager;
import pack.network.Client;
import pack.network.Server;
import pack.ui.ClickListener;
import pack.ui.MyUIManager;
import pack.ui.UIImageButton;

import java.awt.*;

public class MainMenuState extends State {

    private MyUIManager uiManager;
    private Game game;

    public MainMenuState(Game game) {
        this.game = game;
        uiManager = new MyUIManager();
        MouseManager.setUIManager(uiManager);

        initButtons();

    }

    private void initButtons() {
        UIImageButton createGame = new UIImageButton(250, 100, 300, 45, Assets.createGame, new ClickListener() {
            @Override
            public void onClick() {
                GameState gameState = new GameState();
                ThreadPool.execute(new Server(gameState));
                game.setState(gameState);
                MouseManager.setUIManager(null);
            }
        });

        UIImageButton joinGame = new UIImageButton(250, 150, 300, 45, Assets.joinGame, new ClickListener() {
            @Override
            public void onClick() {
                Client client = new Client("127.0.0.1");
                boolean connected = client.connect();
                if (connected) {
                    game.setState(new ClientGameState(game));
                    MouseManager.setUIManager(null);

                }

            }
        });

        UIImageButton exit = new UIImageButton(250, 200, 300, 45, Assets.exit, new ClickListener() {
            @Override
            public void onClick() {
                System.exit(0);
            }
        });

        uiManager.addObject(createGame);
        uiManager.addObject(joinGame);
        uiManager.addObject(exit);
    }


    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        uiManager.render(g);
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }
}
