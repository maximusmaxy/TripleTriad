/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author avata
 */
public class SpriteBackground extends Sprite{
    //inserts the background for the game:
    private Board board;
    private int defaultX;
    private int defaultY;
    private int defaultZ;

    public SpriteBackground(SpriteSet spriteSet, int width, int height) {
        super(spriteSet, width, height);
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
}
