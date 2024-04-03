import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SettingScreen extends Screen{
    private JSlider audio;
    private JButton changePassword, debug, credits, muteButton, exit;
    GameSound sound;
    Player user;
    Image plankIMG, scrollIMG, resizedMutedIMG, resizedUnMutedIMG;
    BufferedImage mutedIMG, unMutedIMG;
    public Image backgroundImage;
    boolean muted = false;
    public SettingScreen(FullScreenUI frame,Screen previous,Player user) {
        super(frame,previous,user);
        sound = new GameSound("backgroundmusic.wav");
        sound.play();
        prev = previous;
        audio = new JSlider(0,100,50);
        audio.addChangeListener(e -> changeVolume());
        audio.setOpaque(false);
        changePassword = new JButton("CHANGE PASSWORD");
        debug = new JButton("DEBUG");
        credits = new JButton("CREDITS");
        muteButton = new JButton();
        exit = new JButton();
        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(new File("resources/escape.png"));
            plankIMG = ImageIO.read(new File("resources/plank.png"));
            scrollIMG = ImageIO.read(new File("scroll.png"));
            mutedIMG = ImageIO.read(new File("muted.png"));
            unMutedIMG = ImageIO.read(new File("unMuted.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        exit.setIcon(new ImageIcon(resizedEsc));
        changePassword.addActionListener(e -> changePasswordButton());
        debug.addActionListener(e -> debugButton());
        credits.addActionListener(e -> creditsButton());
        muteButton.addActionListener(e -> muteButton());
        exit.addActionListener(e -> exitButton());
        this.add(credits);
        this.add(muteButton);
        this.add(exit);
        this.add(audio);
    }

    public void changeVolume(){
        sound.setVolume(audio.getValue());
    }

    public void setPrev(Screen prev){
        this.prev = prev;
        if(Objects.nonNull(this.prev)) {
            if (!(this.prev.getClass().getName().equals("MainMenu") || this.prev.getClass().getName().equals("LoginScreen") || this.prev.getClass().getName().equals("RegisterScreen"))) {
                this.remove(debug);
                this.add(changePassword);
            }
            else {
                this.add(debug);
            }
        }
    }
    public void setUser(Player user){
        this.user = user;
    }
    public void setComponents(Graphics g){
        int width = getWidth();
        int height = getHeight();
        resizedMutedIMG = mutedIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        resizedUnMutedIMG = unMutedIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        muteButton.setContentAreaFilled(false);
        muteButton.setBorderPainted(false);
        Font font = new Font("SansSerif", Font.BOLD, 36);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth("GEOCRAFT");
        int textHeight = metrics.getHeight();
        int sliderX = width/2 - width/10;
        int sliderY = height/3;
        int sliderWidth = width/3;
        int sliderHeight = height/15;
        audio.setBounds(width/3, height/2, width/3, sliderHeight);
        int textX = sliderX - textWidth;
        int textY = sliderY + (sliderHeight - textHeight) / 2 + metrics.getAscent();
        g.drawString("AUDIO",width/3, textY);
        Image scaledImage = plankIMG.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        Image scrollIMGScaled= scrollIMG.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(changePassword,scaledImage,width/75);
        createButtons(debug,scaledImage,width/60);
        createButtons(credits,scrollIMGScaled,width/60);
        exit.setBounds(width / 30, height/22, 50, 50);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        muteButton.setBounds(width/2+width/15,height/3,width/10,height/12);
        changePassword.setBounds(width/3+width/12, height - height/6, width/6, height/12);
        debug.setBounds(width/3+width/12,height - height/4,width/6,height/12);
        credits.setBounds(width-width/8,height - height/8,width/10,height/12);
    }
    public void changePasswordButton() {
        swapScreens(new ChangePasswordScreen(frame,this,user));
    }
    public void debugButton() {
        swapScreens(new DebugScreen(frame,this));
    }
    public void creditsButton() {
        swapScreens(new CreditsScreen(frame,this));
    }
    public void exitButton() {
        swapScreens(prev);
    }
    public void audioSlider() {
        // mute function
    }
    public void muteButton() {
        muted = !muted;
        muteButton.setContentAreaFilled(false);
        muteButton.setBorderPainted(false);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents(g2D);
        if(muted){
            sound.setVolume(0);
            muteButton.setIcon(new ImageIcon(resizedMutedIMG));
        } else {
            sound.setVolume(audio.getValue());
            muteButton.setIcon(new ImageIcon(resizedUnMutedIMG));
        }
        drawTitle(g2D);
    }
}
