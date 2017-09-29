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
    private boolean right;
    private int defaultX;
    private int defaultY;
    private int defaultZ;
    
    public SpriteCard(SpriteSet spriteSet, Card card, boolean right) {
        super(spriteSet);
        this.card = card;
        this.right = right;
        character = loadImage(card.getName());
        rect.width = character.getWidth(null);
        rect.height = character.getHeight(null);
        image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        refresh();
    }
    
    public Card getCard() {
        return card;
    }
    
    public boolean isRight() {
        return right;
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
        g.setColor(right ? Color.BLUE : Color.RED);
        g.fillRect(0, 0, rect.width, rect.height);
        g.drawImage(character, 0, 0, 150, 150, null);
        g.setColor(Color.WHITE);
        g.setFont(font);
        drawShadowedString(g, String.valueOf(card.getUp()), 66, 28);
        drawShadowedString(g, String.valueOf(card.getRight()), 122, 86);
        drawShadowedString(g, String.valueOf(card.getDown()), 66, 142);
        drawShadowedString(g, String.valueOf(card.getLeft()), 6, 86);
    }
}
