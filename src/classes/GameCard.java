/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Max
 */
public class GameCard {
    private Card card;
    private boolean left;
    
    public GameCard(Card card, boolean left) {
        this.card = card;
        this.left = left;
    }
    
    public String getName() {
        return card.getName();
    }
    
    public int getUp() {
        return card.getUp();
    }
    
    public int getRight() {
        return card.getRight();
    }
    
    public int getDown() {
        return card.getDown();
    }
    
    public int getLeft() {
        return card.getLeft();
    }
    
    public boolean isLeft() {
        return left;
    }
}
