import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class returns a buffered images array
 * To load image, simply specify the game type in string form.
 * Sample usage is in fullscreenUI java file
 */
public class MapPictureArray extends JFrame {

    private final BufferedImage[] backgroundImages; // Array to store background images

    public MapPictureArray(String gameMode) {

        int noOfImages;
        String folderPath = switch (gameMode) {
            case "Global", "Exploration" -> {
                //Change this value to number of images in respective folder
                noOfImages = 4;
                yield "Maps/Global";
            }
            case "Continental" -> {
                noOfImages = 20;
                yield "Maps/Continental";
            }
            default -> throw new IllegalStateException("Unexpected value: " + gameMode);
        };
        backgroundImages = new BufferedImage[noOfImages];
        for (int i = 0; i < noOfImages; i++)
            try {
                String imagePath = folderPath + "/" + i + ".png";
                BufferedImage backgroundImage = ImageIO.read(new File(imagePath));
                backgroundImages[i] = backgroundImage;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {
        new MapPictureArray("Global");
    }

    public BufferedImage[] getBackgroundImages() {
        return backgroundImages;
    }


}