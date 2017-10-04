/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Rectangle;
import java.util.Arrays;

/**
 *
 * @author Max
 */
public class Board {
    private Rectangle[][] rects;
    private SpriteCard[][] cards;
    private SpriteCapture[][] captures;
    
    public Board(SpriteSet spriteSet) {
        cards = new SpriteCard[3][3];
        rects = new Rectangle[3][3];
        captures = new SpriteCapture[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rects[j][i] = new Rectangle(375 + 150 * i, 112 + 150 * j, 150, 150);
                captures[j][i] = new SpriteCapture(spriteSet, rects[j][i].x, rects[j][i].y + 50);
            }
        }
    }

    public SpriteCard[][] getCards() {
        return cards;
    }
    
    public Rectangle[][] getRects() {
        return rects;
    }
    
    public boolean checkFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cards[j][i] == null)
                    return false;
            }
        }
        return true;
    }
    
    public void clear() {
        for (SpriteCard[] card : cards) {
            Arrays.fill(card, null);
        }
    }
    
    public SpriteCard getCard(SpriteCard card, int dx, int dy) {
        int x = -1;
        int y = -1;
        outer: for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (card == cards[j][i]) {
                    x = i;
                    y = j;
                    break outer;
                }
            }
        }
        if (x == -1 || y == -1)
            throw new RuntimeException("Couldn't find " + card.getCard().getName());
        x += dx;
        y += dy;
        if (x >= 0 && x <= 2 && y >= 0 && y <= 2) {
            return cards[y][x];
        }
        return null;
    }
    
    public void capture(SpriteCard card, boolean blue, String text) {
        int x = -1;
        int y = -1;
        outer: for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (card == cards[j][i]) {
                    x = i;
                    y = j;
                    break outer;
                }
            }
        }
        if (x == -1 || y == -1)
            throw new RuntimeException("Couldn't find " + card.getCard().getName());
        captures[y][x].activate(text, blue);
    }
}
