/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Graphics2D;

/**
 *
 * @author Max
 */
public class SpriteDebug {
    
    private Input input;
    
    public SpriteDebug(Input input) {
        this.input = input;
    }
    
    public void draw(Graphics2D g) {
        if (input.isDown()) {
            g.drawString("Down", 200, 150);
        }
        else {
            g.drawString("Up", 200, 150);
        }
        if (input.isClicked()) {
            g.drawString("Clicked", 200, 200);
        }
        else {
            g.drawString("NotClicked", 200, 200);
        }
        if (input.isReleased()) {
            g.drawString("Released", 200, 250);
        }
        else {
            g.drawString("NotReleased", 200, 250);
        }
        g.drawString("X: " + input.getPosition().x, 200, 300);
        g.drawString("Y: " + input.getPosition().y, 200, 350);
    }
}
