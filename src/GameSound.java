import javax.sound.sampled.*;
import java.io.File;

public class GameSound {
    private Clip clip;

    public GameSound(String path) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audio);
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