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
public class Player {
    private SpriteCard[] cards;
    private int score;
    
    public Player() {
        cards = new SpriteCard[5];
        score = 5;
    }

    public SpriteCard[] getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
