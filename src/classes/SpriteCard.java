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
    
    
    public SpriteCard(SpriteSet spriteSet, Card card, boolean right) {
        super(spriteSet);
        this.card = card;
        this.blue = right;
        System.out.println(card.getName());
        //character = Loader.loadImage(card.getName());
        //rect.width = character.getWidth(null);
        //rect.height = character.getHeight(null);
        //image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        refresh();
    }
    
    public Card getCard() {
        return card;
    }
    
    public boolean isBlue() {
        return blue;
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
        //draw background
        if (blue)
            g.drawImage(BLUE, 0, 0, 150, 150, null);
        else
            g.drawImage(RED, 0, 0, 150, 150, null);
        //draw character
        //g.drawImage(character, 0, 0, 150, 150, null);
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
}
