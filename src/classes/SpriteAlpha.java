/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;

/**
 * Extends sprite and adds alpha
 * @author Max
 */
public class SpriteAlpha extends Sprite {
    
    private AlphaComposite alpha;
    
    public SpriteAlpha(SpriteSet spriteSet, int width, int height) {
        super(spriteSet, width, height);
        alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
    }
    
    public float getAlpha() {
        return alpha.getAlpha();
    }
    
    public void setAlpha(float alpha) {
        if (alpha != getAlpha())
            this.alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    }
    
    @Override
    public void draw(Graphics2D g) {
        Composite c = g.getComposite();
        g.setComposite(alpha);
        super.draw(g);
        g.setComposite(c);
    }
}
