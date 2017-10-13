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
public class SpriteScore extends Sprite{
    
    private boolean player1;
    
    public SpriteScore(SpriteSet spriteSet, boolean player1) {
        super(spriteSet, 100, 100);
        this.player1 = player1;
        font = new Font("Helvetica", Font.BOLD, 64);
        if (player1)
            setLocation(862, 550);
        else
            setLocation(296, 550);
        visible = false;
        refresh(5);
    }
    
    public void refresh(int score) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        clearImage(g);
        if (player1) {
            g.setColor(Color.BLUE);
        }
        else {
            g.setColor(Color.RED);
        }
        g.setFont(font);
        drawShadowedString(g, String.valueOf(score), 0, 64);
    }
}
