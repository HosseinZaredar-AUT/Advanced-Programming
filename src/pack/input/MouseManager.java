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
    public static int rightMouseButtonFlag = 1;

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
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e))
            rightMouseButtonFlag *= -1;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            leftMouseButton = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            leftMouseButton = false;
    }

    public static double angleWithEnemy(float x,float y){
        float dx = x - Camera.getEntityX() ;
        float dy = y - Camera.getEntityY() ;
        return  (Math.atan2(dy, dx) / (Math.PI)) * 180 + 180;
    }

}
