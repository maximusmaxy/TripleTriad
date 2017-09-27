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
public class Sprite implements Comparable<Sprite> {
    
    protected SpriteSet spriteSet;
    protected Rectangle rect;
    protected Image image;
    protected int z;
    
    public Sprite(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
        spriteSet.add(this);
        rect = new Rectangle();
    }
    
    public Sprite(SpriteSet spriteSet, String imageName) {
        this(spriteSet);
        image = loadImage(imageName);
        rect.width = image.getWidth(null);
        rect.height = image.getHeight(null);
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
    
    public int getZ() {
        return z;
    }
    
    public void setX(int x) {
        rect.x = x;
    }
    
    public void setY(int y) {
        rect.y = y;
    }
    
    public void setZ(int z) {
        this.z = z;
        spriteSet.requestUpdate();
    }
    
    public void setLocation(Point p) {
        rect.setLocation(p);
    }
    
    public void setLocation(int x, int y) {
        rect.setLocation(x, y);
    }
    
    public void setLocation(int x, int y, int z) {
        rect.setLocation(x, y);
        this.z = z;
        spriteSet.requestUpdate();
    }
    
    public Image loadImage(String name) {
        try {
            ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + name + ".png"));
            Image image = icon.getImage();
            return image;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
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

    @Override
    public int compareTo(Sprite other) {
        return z - other.getZ();
    }
}
