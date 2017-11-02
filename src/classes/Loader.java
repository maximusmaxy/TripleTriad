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
import java.util.Base64;
import java.util.BitSet;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;

/**
 * You can't get resources from other packages using static methods so this is a
 * stupid class that allows you to do it.
 *
 * @author Max
 */
public class Loader {

    private static final Loader loader = new Loader();
    private Card[] cards;

    public static Card[] loadCards() {
        return loader.getCards();
    }

    public static Image loadImage(String name) {
        return loader.image(name);
    }

    public static Clip loadClip(String name) {
        return loader.clip(name);
    }

    public Loader() {
        cards = cards();
    }

    private Card[] getCards() {
        return cards;
    }

//    public static boolean[] loadCollection(String username, String password) {
//        try {
//            boolean[] collection = new boolean[loadCards().length];
//            //String collectionString = getCollection(username, password);
////            if (collectionString == null) {
////                return null;
////            }
////            BitSet bitSet = BitSet.valueOf(Base64.getDecoder().decode(collectionString));
//            for (int i = 0; i < collection.length; i++) {
//                collection[i] = bitSet.get(i);
//            }
//            return collection;
//        } catch (Exception ex) {
//            System.err.println("Failed to load collection");
//            return null;
//        }
//    }

    public static boolean[] fullCollection() {
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
            int i = 0;
            while ((line = br.readLine()) != null) {
                split = line.split("\\|");
                cards.add(new Card(
                        split[0],
                        i,
                        Integer.parseInt(split[1]),
                        Integer.parseInt(split[2]),
                        Integer.parseInt(split[3]),
                        Integer.parseInt(split[4]),
                        Integer.parseInt(split[5])));
                i++;
            }
            is.close();
        } catch (IOException ex) {
            System.err.println("Error loading cards.");
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
            System.err.println("Error loading image: " + name + ".png");
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
            System.err.println("Error loading sound clip: " + name + ".wav");
            ex.printStackTrace();
        }
        return null;
    }

//    private static String getCollection(java.lang.String username, java.lang.String password) {
//        org.maximusmaxy.databaseservice.TTDBService_Service service = new org.maximusmaxy.databaseservice.TTDBService_Service();
//        org.maximusmaxy.databaseservice.TTDBService port = service.getTTDBServicePort();
//        return port.getCollection(username, password);
//    }
}
