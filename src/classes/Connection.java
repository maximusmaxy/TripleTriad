/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import protocol.*;

/**
 *
 * @author Max
 */

public class Connection extends Thread {
    private Socket socket;
    private String hostName;
    private int portNumber;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Queue<Message> messages;
    
    public Connection() {
        messages = new LinkedList();
    }
    
    public Boolean hasMessage() {
        return messages.size() > 0;
    }
    
    public Boolean messageType(int type) {
        if (!hasMessage())
            return false;
        return messages.peek().getType() == type;
    }
    
    public String getMessage() {
        return messages.peek().getMessage();
    }
    
    public int getType() {
        return messages.peek().getType();
    }
    
    public Object getObject() {
        return messages.peek().getObject();
    }
    
    public void clearMessage() {
        messages.poll();
    }
    
    public void sendPlay(int index, int x, int y) {
        sendMessage(new Message("Your Turn.", Message.PLAY, new int[] {index, x, y}));
    }
    
    public void sendExit() {
        sendMessage(new Message("Your opponent has left the game.", Message.EXIT, null));
    }
    
    public void sendRules(boolean open, boolean random, boolean same, boolean plus, boolean combo) {
        sendMessage(new Message("Opponent's turn.", Message.RULES,
                new boolean[] {open, random, same, plus, combo}));
    }

    public void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error sending message.");
            e.printStackTrace();
        }
    }
    
    public boolean connect(String hostName, int portNumber) {
        try {
            socket = new Socket(hostName, portNumber);
            out =  new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            start();
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Host Name: " + hostName + "was not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't connect to: " + hostName);
            e.printStackTrace();
        } 
        return false;
    }
    
    @Override
    public void run() {
        Message inMessage;
        try {
            while ((inMessage = (Message)in.readObject()) != null) {
                messages.add(inMessage);
            }
        } catch (IOException ex) {
            System.err.println("Error with input stream.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.err.println("Class not found.");
            ex.printStackTrace();
        }
    }
}
