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
    
    public static final Clip CAPTURE = Loader.loadClip("Capture!~");
    public static final Clip PLACE = Loader.loadClip("Place!~");
    public static final Clip COMBO = Loader.loadClip("Combo!~");
    public static final Clip CCCOMBO = Loader.loadClip("CCCOMBO!~");
    public static final Clip DEFEAT = Loader.loadClip("Defeat!~");
    public static final Clip VICTORY = Loader.loadClip("Victory!~");
    public static final Clip DRAW = Loader.loadClip("DRAW!~");
    
    public static void play(Clip clip) {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
