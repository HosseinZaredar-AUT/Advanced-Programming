package pack.states;

import java.awt.*;

public abstract class State {

    public State() {

    }

    public abstract void tick();
    public abstract void render(Graphics2D g);

}
