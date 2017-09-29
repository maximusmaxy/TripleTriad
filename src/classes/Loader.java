package classes;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * You can't get resources from other packages using static methods so this
 * is a stupid class that allows you to do it.
 * @author Max
 */

public class Loader {

    private static final Loader loader = new Loader();
    
    public static List<Card> loadCards() {
        return loader.cards();
    }
    
    public static Image loadImage(String name) {
        return loader.image(name);
    }
    
    public List<Card> cards() {
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
            System.out.println(ex.getMessage());
        }
        return cards;
    }
    
    public Image image(String name) {
        try {
            ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + name + ".png"));
            Image image = icon.getImage();
            return image;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
