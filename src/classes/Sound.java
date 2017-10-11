/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.sound.sampled.Clip;

/**
 *
 * @author Max
 */
public class Sound {
    
    public static final Clip CAPTURE = Loader.loadClip("Combo!~");
    public static final Clip PLACE = Loader.loadClip("Place!~");
    
    public static void play(Clip clip) {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
