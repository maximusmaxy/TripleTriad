/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import frames.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
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
    private SpriteCard selected;
    private int selectedIndex;
    private SpriteCard hovered;

    //sprites
    private SpriteSet spriteSet;
    private SpriteConnection spriteConnection;
    private SpriteScore leftScore;
    private SpriteScore rightScore;
    private SpriteCapture[][] spriteCaptures;

    //game objects
    private Player leftPlayer;
    private Player rightPlayer;
    private Board board;
    private Rules rules;

    //game variables
    private boolean player1;
    private boolean rightTurn;
    private int phase;
    private int wait;
    private int playX;
    private int playY;

    //board calculation
    private SpriteCard[] others;
    private SpriteCard[] captures;
    private boolean[] sames;
    private int[] pluses;
    private Queue<SpriteCard> combos;
    private int sameCount;
    private int plusCount;

    //states
    public final int START = 1;
    public final int MAIN = 2;
    public final int BOARD = 3;
    public final int OPPONENT = 4;
    public final int FINISH = 5;

    //directions
    public final int UP = 0;
    public final int RIGHT = 1;
    public final int DOWN = 2;
    public final int LEFT = 3;

    public Game(GamePanel panel, Input input, Connection connection) {
        this.panel = panel;
        this.input = input;
        this.connection = connection;
        cards = Loader.loadCards();
        phase = START;
        spriteSet = new SpriteSet();
        spriteConnection = new SpriteConnection(spriteSet, connection);
        leftScore = new SpriteScore(spriteSet, false);
        rightScore = new SpriteScore(spriteSet, true);
        spriteCaptures = new SpriteCapture[3][3];
        others = new SpriteCard[4];
        captures = new SpriteCard[4];
        sames = new boolean[4];
        pluses = new int[4];
        combos = new LinkedList();
        leftPlayer = new Player(false);
        rightPlayer = new Player(true);
        board = new Board(spriteSet);
        rules = new Rules();


        //test
        leftPlayer.setCards(spriteSet, cards, new int[]{0, 0, 0, 0, 0});
        leftPlayer.setBack(!rules.isOpen());
        rightPlayer.setCards(spriteSet, cards, new int[]{1, 1, 1, 1, 1});
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
        } else {
            phase = OPPONENT;
            spriteConnection.refresh("Opponents turn.");
        }
    }

    public void update() {
        updateMessage();
        updateHover();
        spriteSet.update();
        if (!updateWait()) {
            return;
        }
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
            case FINISH:
                updateFinish();
                break;
        }
    }

    private void updateMessage() {
        if (connection.messageType(Message.MESSAGE)
                || connection.messageType(Message.EXIT)) {
            spriteConnection.refresh();
            connection.clearMessage();
        }
    }

    private void updateHover() {
        if (selected == null) {
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

    private boolean updateWait() {
        if (wait > 0) {
            wait--;
            return false;
        }
        return true;
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
        } else if (connection.messageType(Message.RULES)) {
            boolean[] obj = (boolean[]) connection.getObject();
            setRules(obj[0], obj[1], obj[2], obj[3], obj[4]);
            connection.clearMessage();
            start();
        }
    }

    private void updateMain() {
        if (input.isClicked()) {
            selectedIndex = getMouseIndex();
            if (selectedIndex >= 0) {
                selected = rightPlayer.getCards()[selectedIndex];
                offsetX = selected.getX() - input.getPosition().x;
                offsetY = selected.getY() - input.getPosition().y;
            }
        } else if (input.isReleased()) {
            if (selected != null) {
                if (placeCard()) {
                    return;
                } else {
                    selected.reset();
                }
                selected = null;
            }
        }
        if (selected != null) {
            selected.setX(input.getPosition().x + offsetX);
            selected.setY(input.getPosition().y + offsetY);
        }
    }

    private void updateBoard() {
        play(selected, false);
        while (combos.size() > 0) {
            play(combos.poll(), true);
        }
        rightScore.refresh(rightPlayer.getScore());
        leftScore.refresh(leftPlayer.getScore());
        if (board.checkFull()) {
            String title;
            if (rightPlayer.getScore() > leftPlayer.getScore()) {
                title = "You win!";
            } else if (rightPlayer.getScore() < leftPlayer.getScore()) {
                title = "You lose!";
            } else {
                title = "It's a draw!";
            }
            spriteConnection.refresh(title);
            if (player1) {
                panel.getRematch().setTitle(title);
                panel.getRematch().setVisible(true);
            }
            phase = FINISH;
        } else {
            phase = rightTurn ? OPPONENT : MAIN;
            rightTurn = !rightTurn;
        }
        selected = null;
    }

    private void play(SpriteCard card, boolean combo) {
        //reset
        Arrays.fill(others, null);
        Arrays.fill(captures, null);
        Arrays.fill(sames, false);
        Arrays.fill(pluses, 0);
        sameCount = 0;
        plusCount = 0;
        //captures
        others[UP] = board.getCard(card, 0, -1);
        others[RIGHT] = board.getCard(card, 1, 0);
        others[DOWN] = board.getCard(card, 0, 1);
        others[LEFT] = board.getCard(card, -1, 0);
        capture(card, others[UP], UP, combo);
        capture(card, others[RIGHT], RIGHT, combo);
        capture(card, others[DOWN], DOWN, combo);
        capture(card, others[LEFT], LEFT, combo);
        //same
        if (rules.isSame() && sameCount > 1) {
            for (int i = 0; i < sames.length; i++) {
                if (sames[i] && card.isBlue() != others[i].isBlue()) {
                    score(others[i], card.isBlue(), rules.isCombo(), "Same!");
                }
            }
        }
        //plus
        if (rules.isPlus() && plusCount > 1) {
            for (int i = 0; i < pluses.length; i++) {
                if (pluses[i] != 0 && card.isBlue() != others[i].isBlue()) {
                    int count = 1;
                    for (int j = 0; j < pluses.length; j++) {
                        if (i != j && pluses[i] == pluses[j]) {
                            count++;
                        }
                    }
                    if (count > 1) {
                        score(others[i], card.isBlue(), rules.isCombo(), "Plus!");
                    }
                }
            }
        }
        //captures
        for (int i = 0; i < captures.length; i++) {
            if (captures[i] != null && card.isBlue() != captures[i].isBlue()) {
                score(captures[i], card.isBlue(), combo, combo ? "Combo!" : "Score!");
            }
        }
    }

    private void capture(SpriteCard card, SpriteCard other, int direction, boolean combo) {
        if (other == null) {
            return;
        }
        int cardValue = 0;
        int otherValue = 0;
        switch (direction) {
            case UP:
                cardValue = card.getUp();
                otherValue = other.getDown();
                break;
            case RIGHT:
                cardValue = card.getRight();
                otherValue = other.getLeft();
                break;
            case DOWN:
                cardValue = card.getDown();
                otherValue = other.getUp();
                break;
            case LEFT:
                cardValue = card.getLeft();
                otherValue = other.getRight();
                break;
        }
        if (card.isBlue() != other.isBlue() && cardValue > otherValue) {
            captures[direction] = other;
        }
        if (!combo) {
            if (rules.isSame() && cardValue == otherValue) {
                sames[direction] = true;
                sameCount++;
            }
            if (rules.isPlus()) {
                pluses[direction] = cardValue + otherValue;
                plusCount++;
            }
        }
    }

    private void score(SpriteCard card, boolean blue, boolean combo, String text) {
        card.setBlue(blue);
        if (combo) {
            combos.add(card);
        }
        if (blue) {
            rightPlayer.addScore(1);
            leftPlayer.addScore(-1);
        } else {
            rightPlayer.addScore(-1);
            leftPlayer.addScore(1);
        }
        board.capture(card, blue, text);
    }

    private void updateOpponent() {
        if (!connection.messageType(Message.PLAY)) {
            return;
        }
        spriteConnection.refresh();
        int[] play = (int[]) connection.getObject();
        playX = play[1];
        playY = play[2];
        selected = leftPlayer.getCards()[play[0]];
        if (!rules.isOpen()) {
            selected.setBack(false);
        }
        leftPlayer.getCards()[play[0]] = null;
        board.getCards()[play[2]][play[1]] = selected;
        Rectangle rect = board.getRects()[play[2]][play[1]];
        selected.setDefaultLocation(rect.x, rect.y, 0);
        connection.clearMessage();
        phase = BOARD;
    }

    private void updateFinish() {
        if (!connection.messageType(Message.REMATCH)) {
            return;
        }
        switch ((int) connection.getObject()) {
            case 1: //change to constant later
                rematch();
                break;
            case 2: //change to constant later
                rules();
                break;
            case 3: //change to constant later
                cards();
                break;
        }
        connection.clearMessage();
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
        double x = selected.getRect().getCenterX();
        double y = selected.getRect().getCenterY();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getCards()[j][i] == null
                        && board.getRects()[j][i].contains(x, y)) {
                    playX = i;
                    playY = j;
                    selected.setDefaultLocation(
                            board.getRects()[j][i].x, board.getRects()[j][i].y, 0);
                    board.getCards()[j][i] = selected;
                    connection.sendPlay(selectedIndex, i, j);
                    rightPlayer.getCards()[selectedIndex] = null;
                    spriteConnection.refresh("Opponents turn");
                    phase = BOARD;
                    return true;
                }
            }
        }
        return false;
    }

    public void rematch() {
        leftPlayer.reset();
        if (!rules.isOpen()) {
            leftPlayer.setBack(false);
        }
        rightPlayer.reset();
        leftScore.refresh(5);
        rightScore.refresh(5);
        rightTurn = player1;
        board.clear();
        start();
    }

    public void rules() {
        reset();
    }

    public void cards() {
        reset();
    }

    public void reset() {
        leftPlayer.clear();
        rightPlayer.clear();
        board.clear();
        rightTurn = player1;
        phase = START;
    }

    public void draw(Graphics2D g) {
        spriteSet.draw(g);
    }
}
