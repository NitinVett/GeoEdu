import javax.swing.*;
import java.awt.*;

public class SettingScreen extends Screen{
    JSlider audio;

    public SettingScreen(JFrame frame) {
        super(frame);
        audio = new JSlider();
        audio.setOpaque(false);
        this.add(audio);
        repaint();
    }

    public void setComponents(){
        int width = getWidth();
        int height = getHeight();


        audio.setBounds(width/2 - width/10,height/3,width/3,height/15);
        revalidate();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        drawTitle(g2D);
        setComponents();


    }
}
