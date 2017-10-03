/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import frames.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import protocol.Message;

/**
 *
 * @author Maxie
 */
public class Game {

    //system
    private GamePanel panel;
    private Input input;
    private Connection connection;
    private Card[] cards;
    private boolean[] collection;

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
    public final int START = 1;
    public final int MAIN = 2;
    public final int BOARD = 3;
    public final int OPPONENT = 4;
    public final int FINISH = 5;

    public Game(GamePanel panel, Input input, Connection connection) {
        this.panel = panel;
        this.input = input;
        this.connection = connection;
        cards = Loader.loadCards();
        spriteSet = new SpriteSet();
        spriteConnection = new SpriteConnection(spriteSet, connection);
        leftPlayer = new Player();
        rightPlayer = new Player();
        board = new Board();
        rules = new Rules();
        phase = START;
        for (int i = 0; i < 5; i++) 
        {
            leftPlayer.getCards()[i] = new SpriteCard(spriteSet, cards[0], false);
            leftPlayer.getCards()[i].setDefaultLocation(50, 50 + 100 * i, i);
            rightPlayer.getCards()[i] = new SpriteCard(spriteSet, cards[1], true);
            rightPlayer.getCards()[i].setDefaultLocation(1000, 50 + 100 * i, i);
/*
            leftPlayer.getCards()[i] = new SpriteCard(spriteSet, cards.get(2), false);
            leftPlayer.getCards()[i].setDefaultLocation(50, 50 + 100 * i, i);
            rightPlayer.getCards()[i] = new SpriteCard(spriteSet, cards.get(3), true);
            rightPlayer.getCards()[i].setDefaultLocation(1000, 50 + 100 * i, i);
*/
        }
    }

    public void setRules(boolean open, boolean random, boolean same, boolean plus, boolean combo) {
        rules.setOpen(open);
        rules.setRandom(random);
        rules.setSame(same);
        rules.setPlus(plus);
        rules.setCombo(combo);
    }
    
    public void setCollection(boolean[] collection) {
        this.collection = collection;
    }
    
    public void start() {
        if (player1) {
            phase = MAIN;
            spriteConnection.refresh("Your turn.");
        }
        else {
            phase = OPPONENT;
            spriteConnection.refresh("Opponents turn.");
        }
    }

    public void update() {
        updateMessage();
        updateHover();
        switch (phase) {
            case START:
                updateStart();
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
        if (connection.messageType(Message.MESSAGE)
                || connection.messageType(Message.EXIT)) {
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

    private void updateStart() {
        if (connection.messageType(Message.TURN)) {
            spriteConnection.refresh();
            player1 = (boolean) connection.getObject();
            rightTurn = player1;
            connection.clearMessage();
            if (player1) {
                panel.getRules().setVisible(true);
            }
        }
        else if (connection.messageType(Message.RULES)) {
            boolean[] obj = (boolean[]) connection.getObject();
            setRules(obj[0], obj[1], obj[2], obj[3], obj[4]);
            connection.clearMessage();
            start();
        }
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
        if (!connection.messageType(Message.PLAY)) {
            return;
        }
        spriteConnection.refresh();
        int[] play = (int[]) connection.getObject();
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
                if (board.getCards()[i][j] == null
                        && board.getRects()[i][j].contains(x, y)) {
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
}
