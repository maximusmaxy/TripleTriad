/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Max
 */
public class Input extends MouseAdapter{

    private JPanel gamePanel;
    private Point position;
    private boolean down;
    private boolean clicked;
    private boolean released;
    private boolean oldDown;
    
    public Input(JPanel panel) {
        gamePanel = panel;
        position = new Point();
    }
    
    public void update() {
        Point screenPosition = MouseInfo.getPointerIn‌​fo().getLocation();
        Point panelPosition = gamePanel.getLocationOnScreen();
        position.setLocation(screenPosition.getX() - panelPosition.getX(),
                screenPosition.getY() - panelPosition.getY());
        clicked = down && !oldDown;
        released = !down && oldDown;
        oldDown = down;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        down = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        down = false;
    }
    
    public Point getPosition() {
        return position;
    }

    public boolean isDown() {
        return down;
    }
    
    public boolean isClicked() {
        return clicked;
    }

    public boolean isReleased() {
        return released;
    }
}
