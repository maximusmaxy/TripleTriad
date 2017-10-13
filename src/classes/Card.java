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

    public static final int WIDTH = 156;
    public static final int HEIGHT = 156;
    
    private String name;
    private int index;
    private int up;
    private int right;
    private int down;
    private int left;
    private int rank;
    
    public Card(String name, int index, int up, int right, int down, int left, int rank) {
        this.name = name;
        this.index = index;
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
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
