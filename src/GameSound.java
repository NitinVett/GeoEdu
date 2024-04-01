import javax.sound.sampled.*;
import java.io.File;

public class GameSound {
    private Clip clip;

    public GameSound(String path) {
        try {
            File file = new File("test.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }

    public void setVolume(){

    }
}