/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author avata
 */
public class SpriteBackground extends Sprite{
    //inserts the background for the game:

    public SpriteBackground(SpriteSet spriteSet) {
        super(spriteSet, 1200, 675);
        setZ(-1);
        reset();
    }
    
    public void reset() {
        Image background = Loader.loadImage("Template");
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.drawImage(background, 0, 0, 1200, 675, null);
    }
}
