/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Max
 */
public class SpriteCard extends Sprite{
    private Card card;
    private Image character;
    private boolean blue;
    private boolean back;
    private int defaultX;
    private int defaultY;
    private int defaultZ;
    
    //static images
    public static final Image[] BORDERS = new Image[] {
        Loader.loadImage("Border1"),
        Loader.loadImage("Border2"),
        Loader.loadImage("Border3")
    };
    public static final Image BLUE = Loader.loadImage("Blue");
    public static final Image RED = Loader.loadImage("Red");
    public static final Image BACK = Loader.loadImage("Back");

    public SpriteCard(SpriteSet spriteSet, Card card, boolean right) {
        super(spriteSet, 150, 150);
        this.card = card;
        this.blue = right;
        character = Loader.loadImage(card.getName());
        refresh();
    }
    
    public Card getCard() {
        return card;
    }
    
    public int getIndex() {
        return card.getIndex();
    }
    
    public int getUp() {
        return card.getUp();
    }
    
    public int getRight() {
        return card.getRight();
    }
    
    public int getDown() {
        return card.getDown();
    }
    
    public int getLeft() {
        return card.getLeft();
    }
    
    public boolean isBlue() {
        return blue;
    }
    
    public boolean isBack() {
        return back;
    }
    
    public void setBlue(boolean blue) {
        if (this.blue != blue) {
            this.blue = blue;
            refresh();
        }
    }
    
    public void setBack(boolean back) {
        if (this.back != back) {
            this.back = back;
            refresh();
        }
    }
    
    public void setDefaultLocation(int x, int y, int z) {
        defaultX = x;
        defaultY = y;
        defaultZ = z;
        setLocation(x, y, z);
    }
    
    public void reset() {
        setLocation(defaultX, defaultY, defaultZ);
    }
    
    public void refresh() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        //
        if (back) {
            g.drawImage(BACK, 0, 0, 150, 150, null);
            return;
        }
        //draw background
        if (blue)
            g.drawImage(BLUE, 0, 0, 150, 150, null);
        else
            g.drawImage(RED, 0, 0, 150, 150, null);
        //draw character
        g.drawImage(character, 0, 0, 150, 150, null);
        //draw border
        g.drawImage(BORDERS[card.getRank() - 1], 0, 0, 150, 150, null);
        //draw text
        g.setColor(Color.WHITE);
        g.setFont(font);
        drawShadowedString(g, String.valueOf(card.getUp()), 66, 28);
        drawShadowedString(g, String.valueOf(card.getRight()), 122, 86);
        drawShadowedString(g, String.valueOf(card.getDown()), 66, 142);
        drawShadowedString(g, String.valueOf(card.getLeft()), 6, 86);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        character.flush();
    }
}
