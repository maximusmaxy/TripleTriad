/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    private SpriteSet spriteSet;

    private SpriteCard[] leftCards;
    private SpriteCard[] rightCards;
    private SpriteCard held;
    private SpriteCard hovered;
    private int offsetX;
    private int offsetY;
    private Rectangle[][] boardRects;

    //test stuff
    private SpriteDebug debug;

    public Game(Input input) {
        this.input = input;
        cards = loadCards();
        debug = new SpriteDebug(input);
        spriteSet = new SpriteSet();
        leftCards = new SpriteCard[5];
        rightCards = new SpriteCard[5];
        for (int i = 0; i < leftCards.length; i++) {
            leftCards[i] = new SpriteCard(spriteSet, new GameCard(cards.get(0), true));
            leftCards[i].setDefaultLocation(50, 50 + 100 * i, i);     
            rightCards[i] = new SpriteCard(spriteSet, new GameCard(cards.get(0), false));
            rightCards[i].setDefaultLocation(1000, 50 + 100 * i, i);
        }
        boardRects = new Rectangle[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardRects[i][j] = new Rectangle(375 + 150 * i, 112 + 150 * j, 150, 150);
            }
        }
    }

    public void update() {
        if (input.isClicked()) {
            held = getMouseCard();
            if (held != null) {
                offsetX = held.getX() - input.getPosition().x;
                offsetY = held.getY() - input.getPosition().y;
            }
        } else if (input.isReleased()) {
            if (held != null) {
                Rectangle rect = getBoardRect();
                if (rect != null) {
                    held.setDefaultLocation(rect.x, rect.y, 0);
                } else {
                    held.reset();
                }
                held = null;
            }
        }
        if (held != null) {
            held.setX(input.getPosition().x + offsetX);
            held.setY(input.getPosition().y + offsetY);
        } else {
            SpriteCard hover = getMouseCard();
            if (hovered != null && hovered != hover) {
                hovered.reset();
            }
            if (hover != null) {
                hover.setZ(6);
                hovered = hover;
            }
        }
        spriteSet.update();
    }

    public SpriteCard getMouseCard() {
        for (int i = 4; i >= 0; i--) {
            if (leftCards[i].getRect().contains(input.getPosition())) {
                return leftCards[i];
            }
            if (rightCards[i].getRect().contains(input.getPosition())) {
                return rightCards[i];
            }
        }
        return null;
    }

    public Rectangle getBoardRect() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardRects[i][j].contains(
                        held.getRect().getCenterX(), held.getRect().getCenterY())) {
                    return boardRects[i][j];
                }
            }
        }
        return null;
    }

    public void draw(Graphics2D g) {
        spriteSet.draw(g);
        debug.draw(g);
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
