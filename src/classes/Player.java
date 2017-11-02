/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Random;

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
    
    public static final int GAP = 109;
    public static final int OVERLAP = 46;
    
    public Player(boolean rightPlayer) {
        this.rightPlayer = rightPlayer;
        cards = new SpriteCard[5];
        defaultCards = new SpriteCard[5];
        score = 5;
        rects = new Rectangle[5];
        int x = rightPlayer ? 930 : 105;
        for (int i = 0; i < rects.length; i++) {
            rects[i] = new Rectangle(x, 41 + GAP * i, Card.WIDTH, Card.HEIGHT);
        }
    }

    public SpriteCard[] getCards() {
        return cards;
    }
    
    public SpriteCard getCard(int index) {
        return cards[index];
    }
    
    public void addCard(SpriteSet spriteSet, int cardIndex) {
        int lastIndex = getLastIndex();
        if (lastIndex == 5) {
            throw new RuntimeException("Can't add card to " + 
                    (rightPlayer ? "right" : "left") + " player, hand is full");
        }
        setCard(spriteSet, cardIndex, lastIndex);
    }
    
    public int getLastIndex() {
        for (int i = 0; i < defaultCards.length; i++) {
            if (defaultCards[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public void setCard(SpriteSet spriteSet, int cardIndex, int handIndex) {
        this.cards[handIndex] = new SpriteCard(spriteSet, Loader.loadCards()[cardIndex], rightPlayer);
        Rectangle rect = rects[handIndex];
        this.cards[handIndex].setDefaultLocation(rect.x, rect.y, handIndex);
        this.defaultCards[handIndex] = this.cards[handIndex];
    }
    
    public void setCards(SpriteSet spriteSet, int[] indexes, boolean back) {
        for (int i = 0; i < indexes.length; i++) {
            setCard(spriteSet, indexes[i], i);
            cards[i].setBack(back);
        }
    }
    
    public void removeCard() {
        int lastIndex = getLastIndex();
        if (lastIndex == 0) {
            throw new RuntimeException("Can't remove card from " + 
                    (rightPlayer ? "right" : "left") + " player, no card to remove");
        }
        if (lastIndex == -1) {
            lastIndex = 5;
        }
        cards[lastIndex - 1].dispose();
        cards[lastIndex - 1] = null;
        defaultCards[lastIndex - 1] = null;
    }
    
    public void randomCards(SpriteSet spriteSet, boolean[] collection) {
        Card[] cardList = Loader.loadCards();
        Random rnd = new Random();
        int index = 0;
        while (index < 5) {
            int randomInt = rnd.nextInt(collection.length);
            boolean contains = false;
            for (int i = 0; i < index; i++) {
                if (cards[i].getIndex() == randomInt) {
                    contains = true;
                    break;
                }
            }
            if (contains) {
                continue;
            }
            if (collection[randomInt]) {
                addCard(spriteSet, randomInt);
            }
            else {
                continue;
            }
            index++;
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
