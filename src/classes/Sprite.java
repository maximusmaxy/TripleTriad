/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Max
 */
public class Sprite {
    
    protected Rectangle rect;
    protected Image image;
    
    public Sprite() {
        rect = new Rectangle();
    }
    
    public Sprite(String imageName) {
        this();
        setImage(imageName);
    }
    
    public Rectangle getRect() {
        return rect;
    }
    
    public int getX() {
        return rect.x;
    }
    
    public int getY() {
        return rect.y;
    }
    
    public void setX(int x) {
        rect.x = x;
    }
    
    public void setY(int y) {
        rect.y = y;
    }
    
    public void setLocation(Point p) {
        rect.setLocation(p);
    }
    
    public void setLocation(int x, int y) {
        rect.setLocation(x, y);
    }
    
    public void setImage(String name) {
        try {
            ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + name + ".png"));
            image = icon.getImage();
            rect.width = image.getWidth(null);
            rect.height = image.getHeight(null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }
    
    protected void drawShadowedString(Graphics2D g, String s, int x, int y) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawString(s, x + 2, y + 2);
        g.setColor(oldColor);
        g.drawString(s, x, y);
    }
}
