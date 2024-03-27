import javax.swing.*;
import java.awt.*;

public class SettingScreen extends Screen{
    JSlider audio;
    JButton changePassword, debug, credits, muteButton, highContrast, exit;

    public SettingScreen(JFrame frame) {
        super(frame);
        audio = new JSlider();
        audio.setOpaque(false);
        changePassword = new JButton("CHANGE PASSWORD");
        debug = new JButton("DEBUG");
        credits = new JButton("CREDITS");
        muteButton = new JButton("MUTE");
        highContrast = new JButton("High Contrast");
        exit = new JButton("EXIT");
        changePassword.addActionListener(e -> changePasswordButton());
        debug.addActionListener(e -> debugButton());
        credits.addActionListener(e -> creditsButton());
        muteButton.addActionListener(e -> muteButton());
        highContrast.addActionListener(e -> muteButton());
        exit.addActionListener(e -> exitButton());
        this.add(changePassword);
        this.add(debug);
        this.add(credits);
        this.add(muteButton);
        this.add(highContrast);
        this.add(exit);
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

        exit.setBounds(width / 30, height/22, width / 10, height / 12);
        exit.setFont(new Font("SansSerif", Font.PLAIN, 24));
        muteButton.setBounds(width-width/4,height/3,width/10,height/12);
        muteButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        highContrast.setBounds(width/3+width/12,height - height/4,width/6,height/12);
        highContrast.setFont(new Font("SansSerif", Font.PLAIN, 24));
        changePassword.setBounds(width/3, height - height/6, width/6, height/12);
        debug.setBounds(width-width/2,height - height/6,width/6,height/12);
        credits.setBounds(width-width/8,height - height/8,width/10,height/12);
        changePassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        debug.setFont(new Font("SansSerif", Font.PLAIN, 24));
        credits.setFont(new Font("SansSerif", Font.PLAIN, 24));
        repaint();
    }
    public void changePasswordButton() {
        swapScreens(new ChangePasswordScreen(frame));
    }
    public void debugButton() {
        swapScreens(new DebugScreen(frame));
    }
    public void creditsButton() {
        swapScreens(new CreditsScreen(frame));
    }
    public void exitButton() {
        swapScreens(new ExitScreen(frame));
    }
    public void audioSlider() {
        // mute function
    }
    public void muteButton() {
        // mute function;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);



        setComponents(g);


    }
}
