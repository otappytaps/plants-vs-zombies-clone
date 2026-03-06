package game;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SystemMusic {
    
    private Clip clip;

    /**
     * to play, stop, and loop audio clips using the Java Sound API.
     * It supports .wav files and is used to handle game background
     * @param filePath to the .wav file
     * @throws UnsupportedAudioFileException if the audio file format is not supported
     * @throws IOException if an I/O error occurs while reading the file
     * @throws LineUnavailableException if the system cannot open the audio line
     */

    public SystemMusic(String filePath)throws UnsupportedAudioFileException, IOException, LineUnavailableException{

        File file = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

    }

    /**
     * Starts audio playback from the current position.
     */

    public void play(){

        clip.start();

    }

    /**
     * Stops audio playback and resets the clip to the beginning.
     */
    public void stop(){

        clip.stop();
        clip.setFramePosition(0);

    }

    /**
     * Returns whether the clip is currently playing.
     * @return true if  clip is running, false otherwise
     */
    public boolean isRunning(){

        return clip.isRunning();

    }

    /**
     * Loops the clip continuously until stopped.
     */

    public void loop(){

        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }
    
       


    

    

}
