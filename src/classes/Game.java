/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Max
 */
public class Game {
    
    private Input input;
    private List<Card> cards;
    
    //test stuff
    private SpriteCard test;
    private boolean held;
    private int offsetX;
    private int offsetY;
    
    public Game(Input input) {
        this.input = input;
        cards = loadCards();
        test = new SpriteCard(cards.get(0));
    }
    
    public void update() {
        if (input.isClicked()) {
            if (test.getRect().contains(input.getPosition())) {
                held = true;
                offsetX = test.getX() - input.getPosition().x;
                offsetY = test.getY() - input.getPosition().y;
            }
        }
        if (input.isReleased())
            held = false;
        if (held) {
            test.setX(input.getPosition().x + offsetX);
            test.setY(input.getPosition().y + offsetY);
        }
    }
    
    public void draw(Graphics2D g) {
        test.draw(g);
        if (input.isDown()) {
            g.drawString("Down", 200, 150);
        }
        else {
            g.drawString("Up", 200, 150);
        }
        if (input.isClicked()) {
            g.drawString("Clicked", 200, 200);
        }
        else {
            g.drawString("NotClicked", 200, 200);
        }
        if (input.isReleased()) {
            g.drawString("Released", 200, 250);
        }
        else {
            g.drawString("NotReleased", 200, 250);
        }
        g.drawString("X: " + input.getPosition().x, 200, 300);
        g.drawString("Y: " + input.getPosition().y, 200, 350);
    }
    
    public List<Card> loadCards() {
        List<Card> cards = new ArrayList();
        InputStream is = getClass().getResourceAsStream("/data/cards.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String[] split;
        String line;
        try {
            br.readLine();
            while ((line = br.readLine()) != null) {
                split = line.split("\\|");
                cards.add(new Card(
                        split[0],
                        Integer.parseInt(split[1]),
                        Integer.parseInt(split[2]),
                        Integer.parseInt(split[3]),
                        Integer.parseInt(split[4])));
            }
            is.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return cards;
    }
}
