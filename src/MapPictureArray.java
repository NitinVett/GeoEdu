import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class returns an array of buffered images.
 * To load an image, specify the game type in string form.
 * Sample usage is in the FullScreenUI java file.
 * Each game type would have a folder with a number of images labeled from 0 to n.
 */
public class MapPictureArray extends JFrame {

    /**
     * An array of BufferedImage objects used to store various background images. These images can be used
     * to dynamically change the background of a screen or to cycle through different backgrounds in response
     * to specific user actions or events within the application. Being final, this array must be initialized
     * at the time of declaration or within the constructor of the class.
     */
    private final BufferedImage[] backgroundImages;

    /**
     * Constructs a MapPictureArray object with the specified game mode.
     *
     * @param gameMode The game mode specifying the type of images to load.
     */
    public MapPictureArray(String gameMode) {

        int noOfImages;
        String folderPath = switch (gameMode) {
            case "Global", "Exploration" -> {
                // Change this value to the number of images in the respective folder
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
        for (int i = 0; i < noOfImages; i++) {
            try {
                String imagePath = "/" + i + ".png";
                BufferedImage backgroundImage = ImageIO.read(getClass().getResource(imagePath));
                backgroundImages[i] = backgroundImage;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Main method for testing the MapPictureArray class.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new MapPictureArray("Global");
    }

    /**
     * Gets the array of background images.
     *
     * @return The array of buffered images.
     */
    public BufferedImage[] getBackgroundImages() {
        return backgroundImages;
    }
}
