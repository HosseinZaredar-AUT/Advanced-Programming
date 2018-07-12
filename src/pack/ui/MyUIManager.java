package pack.ui;

import pack.Game;
import pack.graphics.Assets;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyUIManager {

    private ArrayList<UIObject> objects;

    public MyUIManager() {

        objects = new ArrayList<>();
    }

    public void tick() {
        for(UIObject o : objects)
            o.tick();
    }

    public void render(Graphics2D g){
        g.drawImage(Assets.menuBackground, 0, 0, Game.frameWidth, Game.frameHeight, null);
        for(UIObject o : objects)
            o.render(g);
    }

    public void onMouseMove(MouseEvent e){
        for(UIObject o : objects)
            o.onMouseMove(e);
    }

    public void onMouseRelease(MouseEvent e){
        for(UIObject o : objects)
            o.onMouseRelease(e);
    }

    public void addObject(UIObject o){
        objects.add(o);
    }



}
