/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Max
 */
public class SpriteCard extends Sprite{
    private GameCard card;
    private Font font;
    private BufferedImage bufferedImage;
    private int defaultX;
    private int defaultY;
    private int defaultZ;
    
    public SpriteCard(SpriteSet spriteSet, GameCard card) {
        super(spriteSet, card.getName());
        this.bufferedImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        this.card = card;
        this.font = new Font("Helvetica", Font.BOLD, 32);
        refresh();
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
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        if (card.isLeft())
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.RED);
        g.fillRect(0, 0, rect.width, rect.height);
        g.drawImage(image, 0, 0, 150, 150, null);
        g.setColor(Color.WHITE);
        g.setFont(font);
        drawShadowedString(g, String.valueOf(card.getUp()), 66, 28);
        drawShadowedString(g, String.valueOf(card.getRight()), 122, 86);
        drawShadowedString(g, String.valueOf(card.getDown()), 66, 142);
        drawShadowedString(g, String.valueOf(card.getLeft()), 6, 86);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bufferedImage, null, rect.x, rect.y);
    }
}
