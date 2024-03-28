import javax.swing.*;
import java.awt.*;

public class SettingScreen extends Screen{
    JSlider audio;
    JButton changePassword, debug, credits;

    public SettingScreen(JFrame frame) {
        super(frame);
        audio = new JSlider();
        audio.setOpaque(false);
        changePassword = new JButton("CHANGE PASSWORD");
        debug = new JButton("DEBUG");
        credits = new JButton("CREDITS");
        changePassword.addActionListener(e -> changePasswordButton());
        debug.addActionListener(e -> debugButton());
        credits.addActionListener(e -> creditsButton());
        this.add(changePassword);
        this.add(debug);
        this.add(credits);
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

        changePassword.setBounds(width/4, height, width, height/20);
        debug.setBounds(width/2,height + height/10,width/5,height/20);
        credits.setBounds(width/7,height + (height/10)*2,width/5,height/20);
        changePassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        debug.setFont(new Font("SansSerif", Font.PLAIN, 24));
        credits.setFont(new Font("SansSerif", Font.PLAIN, 24));



    }
    public void changePasswordButton() {
        //swapScreens(new ChangePasswordScreen(frame));
    }
    public void debugButton() {
        //swapScreens(new DebugScreen(frame));
    }
    public void creditsButton() {
        //swapScreens(new CreditsScreen(frame));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);



        setComponents(g);


    }
}