/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Max
 */
public class SpriteCapture extends SpriteAlpha {

    private int fade;
    private String text;
    private boolean blue;
    private int defaultX;
    private int defaultY;
    
    public SpriteCapture(SpriteSet spriteSet, int x, int y) {
        super(spriteSet, Card.WIDTH, 50);
        defaultX = x;
        defaultY = y;
        setLocation(defaultX, defaultY, 11);
        setAlpha(0.0f);
    }

    public void activate(String text, boolean blue) { 
        if (this.text != text || this.blue != blue) {
            refresh(text, blue);
            this.text = text;
            this.blue = blue;
        }
        setLocation(defaultX, defaultY, 11);
        setAlpha(1.0f);
        fade = 60;
    }
    
    public void refresh(String text, boolean blue) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        clearImage(g);
        if (blue) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.RED);
        }
        g.setFont(font);
        drawCenteredShadowedString(g, text, 0, 0, rect.width, rect.height);
    }
    
    @Override
    public void update() {
        if (fade > 0) {
            rect.y--;
            fade--;
            if (fade <= 20) {
                setAlpha(fade / 20.0f);
            }
        }
    }
}
