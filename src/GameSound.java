import javax.sound.sampled.*;
import java.io.File;

public class GameSound {
    private Clip clip;
    private FloatControl volumeControl;

    public GameSound(String path) {

        try {
            File file = new File(path); // Use the path parameter instead of hardcoding the file name.
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
            // Attempt to get the volume control from the clip
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVolume(15);
    }

    public void play() {
        if (!clip.isRunning()) {
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();
            clip.loop(100);
        }
    }

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