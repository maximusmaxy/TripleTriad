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
public class SpriteConnection extends Sprite {
    
    private Connection connection;
    
    public SpriteConnection(SpriteSet spriteSet, Connection connection) {
        super(spriteSet, 600, 50);
        this.connection = connection;
        setLocation(600, 0, 10);
        refresh("Waiting for connection");
    }
    
    public void refresh() {
        refresh(connection.getMessage());
    }
    
    public void refresh(String string) {
        System.out.println(string);
        Graphics2D g = (Graphics2D) image.getGraphics();
        clearImage(g);
        g.setFont(font);
        g.drawString(string, 0, 32);
    }
}
