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
public class Player {
    private SpriteCard[] cards;
    private SpriteCard[] defaultCards;
    private int score;
    private boolean rightPlayer;
    private Rectangle[] rects;
    
    public Player(boolean rightPlayer) {
        this.rightPlayer = rightPlayer;
        cards = new SpriteCard[5];
        defaultCards = new SpriteCard[5];
        score = 5;
        rects = new Rectangle[5];
        int x = rightPlayer ? 1000 : 50;
        for (int i = 0; i < rects.length; i++) {
            rects[i] = new Rectangle(x, 50 + 100 * i, 150, 150);
        }
    }

    public SpriteCard[] getCards() {
        return cards;
    }
    
    public SpriteCard getCard(int index) {
        return cards[index];
    }
    
    public void setCards(SpriteSet spriteSet, Card[] cards, int[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            this.cards[i] = new SpriteCard(spriteSet, cards[indexes[i]], rightPlayer);
            this.cards[i].setDefaultLocation(rects[i].x, rects[i].y, i);
            this.defaultCards[i] = this.cards[i];
        }
    }
    
    public void setBack(boolean back) {
        for (SpriteCard card : cards) {
            card.setBack(back);
        }
    }
    
    public void reset() {
        score = 5;
        for (int i = 0; i < cards.length; i++) {
            cards[i] = defaultCards[i];
            cards[i].setDefaultLocation(rects[i].x, rects[i].y, i);
            cards[i].setBlue(rightPlayer);
        }
    }
    
    public void clear() {
        score = 5;
        Arrays.fill(cards, null);
        for (SpriteCard card : defaultCards) {
            card.dispose();
        }
        Arrays.fill(defaultCards, null);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int score) {
        this.score += score;
    }
}
