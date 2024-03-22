import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MapPictureArray {
    MapPictureArray(){
        try {
            backgroundImage = ImageIO.read(new File("background.jpg")); // Replace "background.jpg" with your image file path
            sunsetImage = ImageIO.read(new File("sunset2.jpg"));
            gifd = ImageIO.read(new File("l.gif"));

            backgroundImages = new BufferedImage[]{backgroundImage, sunsetImage, gifd};
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}
