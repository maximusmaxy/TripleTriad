/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Max
 */
public class SpriteCard extends Sprite{
    private Card card;
    private Font font;
    
    public SpriteCard(Card card) {
        super(card.getName());
        this.card = card;
        this.font = new Font("Helvetica", Font.BOLD, 32);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        super.draw(g);
        g.setColor(Color.WHITE);
        g.setFont(font);
        drawShadowedString(g, String.valueOf(card.getUp()), rect.x + 66, rect.y + 28);
        drawShadowedString(g, String.valueOf(card.getRight()), rect.x + 122, rect.y + 86);
        drawShadowedString(g, String.valueOf(card.getDown()), rect.x + 66, rect.y + 142);
        drawShadowedString(g, String.valueOf(card.getLeft()), rect.x + 6, rect.y + 86);
    }
}
