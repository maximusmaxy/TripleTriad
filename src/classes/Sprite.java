/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Class for handling buffered images and drawing them to the screen.
 * @author Max
 */
public class Sprite implements Comparable<Sprite> {
    
    protected SpriteSet spriteSet;
    protected Rectangle rect;
    protected BufferedImage image;
    protected int z;
    protected Font font;
    protected boolean visible;
    
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    
    protected Sprite(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
        spriteSet.add(this);
        rect = new Rectangle();
        font = new Font("Helvetica", Font.BOLD, 32);
        visible = true;
    }
    
    public Sprite(SpriteSet spriteSet, int width, int height) {
        this(spriteSet);
        rect.width = width;
        rect.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
        if (this.z != z)
            spriteSet.requestUpdate();
        this.z = z;
    }
    
    public void setLocation(Point p) {
        rect.setLocation(p);
    }
    
    public void setLocation(int x, int y) {
        rect.setLocation(x, y);
    }
    
    public void setLocation(int x, int y, int z) {
        rect.setLocation(x, y);
        setZ(z);
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setImage(Image image) {
        rect.width = image.getWidth(null);
        rect.height = image.getHeight(null);
        this.image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) this.image.getGraphics();
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }
        
    protected void clearImage(Graphics2D g) {
        g.setBackground(TRANSPARENT);
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
    }
    
    protected void drawShadowedString(Graphics2D g, String s, int x, int y) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawString(s, x + 3, y + 3);
        g.setColor(oldColor);
        g.drawString(s, x, y);
    }
    
    protected void drawCenteredShadowedString(Graphics2D g, String s, int x, int y, int width, int height) {
        FontMetrics metrics = g.getFontMetrics(font);
        int rx = x + (width - metrics.stringWidth(s)) / 2;    
        int ry = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        drawShadowedString(g, s, rx, ry); 
    }
    
    //for overiding
    public void update() {}
    
    public void draw(Graphics2D g) {
        if (visible)
            g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }
    
    public void dispose() {
        image.flush();
        spriteSet.remove(this);
    }
    
    @Override
    public int compareTo(Sprite other) {
        return z - other.getZ();
    }
}
