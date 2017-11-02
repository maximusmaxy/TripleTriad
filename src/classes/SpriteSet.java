/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles sprite sorting for virtual Z values.
 * @author Max
 */
public class SpriteSet {
    private List<Sprite> sprites;
    private boolean update;
    
    public SpriteSet() {
        sprites = new ArrayList();
    }
    
    public void requestUpdate() {
        update = true;
    }
        
    public void add(Sprite sprite) {
        sprites.add(sprite);
    }
    
    public void remove(Sprite sprite) {
        sprites.remove(sprite);
    }
    
    public void update() {
        if (update) {
            sprites.sort(null);
            update = false;
        }
        for (Sprite sprite : sprites)
            sprite.update();
    }
    
    public void draw(Graphics2D g) {
        for (Sprite sprite : sprites)
            sprite.draw(g);
    }
}