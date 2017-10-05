package classes;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;

/**
 * You can't get resources from other packages using static methods so this
 * is a stupid class that allows you to do it.
 * @author Max
 */

public class Loader {

    private static final Loader loader = new Loader();
    
    public static Card[] loadCards() {
        return loader.cards();
    }
    
    public static Image loadImage(String name) {
        return loader.image(name);
    }
    
    public static Clip loadClip(String name) {
        return loader.clip(name);
    }
    
    //this class in the future will use a webservice to get the collection.
    //it will return null if it fails.
    //right now it just returns a full collection.
    public static boolean[] loadCollection(String username, String password) {
        boolean[] collection = new boolean[loadCards().length];
        Arrays.fill(collection, true);
        return collection;
    }
    
    public Card[] cards() {
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
                        Integer.parseInt(split[4]),
                        Integer.parseInt(split[5])));
            }
            is.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        return cards.toArray(new Card[cards.size()]);
    }
    
    public Image image(String name) {
        try {
            ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + name + ".png"));
            Image image = icon.getImage();
            return image;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
    public Clip clip(String name) {
        try {
            URL url = this.getClass().getResource("/sounds/" + name + ".wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
