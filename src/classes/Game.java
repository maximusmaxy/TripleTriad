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
import java.util.List;
import protocol.Message;

/**
 *
 * @author Max
 */
public class Game {

    //system
    private Input input;
    private Connection connection;
    private List<Card> cards;

    //mouse stuff
    private int offsetX;
    private int offsetY;
    private SpriteCard held;
    private int heldIndex;
    private SpriteCard hovered;

    //sprites
    private SpriteSet spriteSet;
    private SpriteConnection spriteConnection;

    //game objects
    private Player leftPlayer;
    private Player rightPlayer;
    private Board board;
    private Rules rules;
    private boolean player1;
    private boolean rightTurn;
    private int phase;

    //states
    private final int TURN = 1;
    private final int MAIN = 2;
    private final int BOARD = 3;
    private final int OPPONENT = 4;
    private final int FINISH = 5;

    public Game(Input input, Connection connection) {
        this.input = input;
        this.connection = connection;
        cards = loadCards();
        spriteSet = new SpriteSet();
        spriteConnection = new SpriteConnection(spriteSet, connection);
        leftPlayer = new Player();
        rightPlayer = new Player();
        board = new Board();
        rules = new Rules();
        phase = TURN;
        for (int i = 0; i < 5; i++) {
            leftPlayer.getCards()[i] = new SpriteCard(spriteSet, cards.get(0), false);
            leftPlayer.getCards()[i].setDefaultLocation(50, 50 + 100 * i, i);
            rightPlayer.getCards()[i] = new SpriteCard(spriteSet, cards.get(0), true);
            rightPlayer.getCards()[i].setDefaultLocation(1000, 50 + 100 * i, i);
        }
        connect();
    }

    public void update() {
        updateMessage();
        updateHover();
        switch (phase) {
            case TURN:
                updateTurn();
                break;
            case MAIN:
                updateMain();
                break;
            case BOARD:
                updateBoard();
                break;
            case OPPONENT:
                updateOpponent();
                break;
        }
        spriteSet.update();
    }
    
    private void updateMessage() {
        if (connection.messageType(Message.MESSAGE) ||
                connection.messageType(Message.EXIT)) {
            spriteConnection.refresh();
            connection.clearMessage();
        }
    }

    private void updateHover() {
        if (held == null) {
            SpriteCard hover = getMouseCard();
            if (hovered != null && hovered != hover) {
                hovered.reset();
            }
            if (hover != null) {
                hover.setZ(6);
                hovered = hover;
            }
        }
    }

    private void updateTurn() {
        if (!connection.messageType(Message.TURN)) {
            return;
        }
        spriteConnection.refresh();
        player1 = (boolean) connection.getObject();
        rightTurn = player1;
        connection.clearMessage();
        phase = rightTurn ? MAIN : OPPONENT;
    }

    private void updateMain() {
        if (input.isClicked()) {
            heldIndex = getMouseIndex();
            if (heldIndex >= 0) {
                held = rightPlayer.getCards()[heldIndex];
                offsetX = held.getX() - input.getPosition().x;
                offsetY = held.getY() - input.getPosition().y;
            }
        } else if (input.isReleased()) {
            if (held != null) {
                if (placeCard()) {
                    return;
                } else {
                    held.reset();
                }
                held = null;
            }
        }
        if (held != null) {
            held.setX(input.getPosition().x + offsetX);
            held.setY(input.getPosition().y + offsetY);
        }
    }

    private void updateBoard() {
        phase = rightTurn ? OPPONENT : MAIN;
        rightTurn = !rightTurn;
        held = null;
    }

    private void updateOpponent() {
        if (!connection.messageType(Message.PLAY))
            return;
        spriteConnection.refresh();
        int[] play = (int[])connection.getObject();
        held = leftPlayer.getCards()[play[0]];
        leftPlayer.getCards()[play[0]] = null;
        board.getCards()[play[1]][play[2]] = held;
        Rectangle rect = board.getRects()[play[1]][play[2]];
        held.setDefaultLocation(rect.x, rect.y, 0);
        connection.clearMessage();
        phase = BOARD;
    }

    private SpriteCard getMouseCard() {
        SpriteCard card;
        for (int i = 4; i >= 0; i--) {
            card = rightPlayer.getCards()[i];
            if (card != null && card.getRect().contains(input.getPosition())) {
                return card;
            }
            card = leftPlayer.getCards()[i];
            if (card != null && card.getRect().contains(input.getPosition())) {
                return card;
            }
        }
        return null;
    }

    private int getMouseIndex() {
        SpriteCard card;
        for (int i = 4; i >= 0; i--) {
            card = rightPlayer.getCards()[i];
            if (card != null && card.getRect().contains(input.getPosition())) {
                return i;
            }
        }
        return -1;
    }

    private boolean placeCard() {
        double x = held.getRect().getCenterX();
        double y = held.getRect().getCenterY();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getCards()[i][j] == null &&
                        board.getRects()[i][j].contains(x, y) ) {
                    held.setDefaultLocation(
                            board.getRects()[i][j].x, board.getRects()[i][j].y, 0);
                    board.getCards()[i][j] = held;
                    connection.sendPlay(heldIndex, i, j);
                    rightPlayer.getCards()[heldIndex] = null;
                    spriteConnection.refresh("Opponents turn");
                    phase = BOARD;
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Graphics2D g) {
        spriteSet.draw(g);
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

    public void connect() {
        connection.setHostName("localhost");
        connection.setPortNumber(6969);
        connection.connect();
        connection.start();
    }
}
