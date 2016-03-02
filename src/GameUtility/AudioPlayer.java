/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Khai Nguyen, Khoi Le & Alice Shen
 * Date: Nov 12, 2015
 * Time: 4:48:42 PM
 *
 * Project: csci205_final_project
 * Package: GameObject
 * File: AudioPlayer
 * Description:
 *
 * ****************************************
 */
package GameUtility;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Khoi Le
 */
public class AudioPlayer {
    private Clip clip;
    private DataLine.Info dataLineInfo;
    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;

    public AudioPlayer(String path) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            audioFormat = audioInputStream.getFormat();
            dataLineInfo = new DataLine.Info(Clip.class, audioFormat);
            clip = (Clip) AudioSystem.getLine(dataLineInfo);
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
        clip.close();
    }
}
