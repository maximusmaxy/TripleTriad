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
public class Card {

    private String name;
    private int up;
    private int right;
    private int down;
    private int left;
    private int rank;
    
    public Card(String name, int up, int right, int down, int left, int rank) {
        this.name = name;
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUp() {
        return up;
    }

    public int getRight() {
        return right;
    }


    public int getDown() {
        return down;
    }


    public int getLeft() {
        return left;
    }

    public int getRank() {
        return rank;
    }
}
