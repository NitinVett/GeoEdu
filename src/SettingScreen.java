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

    public void setComponents(Graphics g){
        int width = getWidth();
        int height = getHeight();

        Font font = new Font("SansSerif", Font.BOLD, 36);
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth("GEOCRAFT");
        int textHeight = metrics.getHeight();


        int sliderX = width/2 - width/10;
        int sliderY = height/3;
        int sliderWidth = width/3;
        int sliderHeight = height/15;
        audio.setBounds(sliderX, sliderY, sliderWidth, sliderHeight);


        int textX = sliderX - textWidth;
        int textY = sliderY + (sliderHeight - textHeight) / 2 + metrics.getAscent();

        g.drawString("AUDIO", textX, textY);

        revalidate();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);



        setComponents(g);


    }
}
