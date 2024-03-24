import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends Screen{
    public RegisterScreen(JFrame frame) {
        super(frame);

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        drawTitle(g2D);


    }
}
