import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The GameSound class provides functionality for playing audio clips.
 */
public class GameSound {
    /**
     * clip of game music
     */
    private Clip clip;
    /**
     * volume control of game music
     */
    private FloatControl volumeControl;

    /**
     * Constructs a GameSound object with the audio file located at the specified path.
     * @param path The path to the audio file
     */
    public GameSound(String path) {
        try {

            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(path));
            clip = AudioSystem.getClip();
            clip.open(audio);
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        setVolume(15);
    }

    /**
     * Starts playing the audio clip.
     * If the clip is already playing, it rewinds to the beginning and starts again.
     */
    public void play() {
        if (!clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
            clip.loop(100);
        }
    }

    /**
     * Sets the volume level of the audio clip.
     * @param volume The desired volume level, ranging from 1 to 100.
     */
    public void setVolume(int volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();

            float scaledVolume = Math.max(volume, 1);

            float range = max - min;
            float scaleFactor = range / (float) Math.log(101);
            float db = (float) (Math.log(scaledVolume) * scaleFactor + min);

            db = Math.min(max, Math.max(min, db));
            volumeControl.setValue(db);
        } else {
            System.out.println("Volume control not supported.");
        }
    }
}
