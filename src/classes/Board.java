/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Rectangle;

/**
 *
 * @author Max
 */
public class Board {
    private Rectangle[][] rects;
    private SpriteCard[][] cards;
    
    public Board() {
        cards = new SpriteCard[3][3];
        rects = new Rectangle[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rects[i][j] = new Rectangle(375 + 150 * i, 112 + 150 * j, 150, 150);
            }
        }
    }

    public SpriteCard[][] getCards() {
        return cards;
    }
    
    public Rectangle[][] getRects() {
        return rects;
    }
}
