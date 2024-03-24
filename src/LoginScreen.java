import javax.swing.*;
import java.awt.*;

public class LoginScreen extends Screen{
    public LoginScreen(JFrame frame) {
        super(frame);
        System.out.println("z");
        repaint();


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        drawTitle(g2D);


    }
}
