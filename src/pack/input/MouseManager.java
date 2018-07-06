package pack.input;

import pack.graphics.Camera;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseManager extends MouseAdapter implements MouseMotionListener  {
    public static double angle;
    public static double dx;
    public static double dy;
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        dx = e.getX() - Camera.getEntityX() + Camera.getXOffset();
        dy = e.getY() - Camera.getEntityY() + Camera.getYOffset();
        angle = (Math.atan2(dy, dx) / (Math.PI)) * 180;
    }

}
