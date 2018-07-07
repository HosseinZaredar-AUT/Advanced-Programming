package pack.input;

import pack.graphics.Camera;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager extends MouseAdapter {

    public static boolean leftMouseButton;
    public static boolean rightMouseButton;
    public static double angle;
    public static double dx;
    public static double dy;

    @Override
    public void mouseMoved(MouseEvent e) {
        dx = e.getX() - Camera.getEntityX() + Camera.getXOffset();
        dy = e.getY() - Camera.getEntityY() + Camera.getYOffset();
        angle = (Math.atan2(dy, dx) / (Math.PI)) * 180;

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dx = e.getX() - Camera.getEntityX() + Camera.getXOffset();
        dy = e.getY() - Camera.getEntityY() + Camera.getYOffset();
        angle = (Math.atan2(dy, dx) / (Math.PI)) * 180;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            leftMouseButton = true;
        else if(SwingUtilities.isRightMouseButton(e))
            rightMouseButton = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            leftMouseButton = false;
        else if(SwingUtilities.isRightMouseButton(e))
            rightMouseButton = false;
    }

    public static double angleWithEnemy(float x,float y){
       float dx = x - Camera.getEntityX() ;
       float dy = y - Camera.getEntityY() ;
        return  (Math.atan2(dy, dx) / (Math.PI)) * 180 +180;
    }

}
