import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SettingScreen extends Screen{
    private JSlider audio;
    private JButton changePassword, debug, credits, highContrast, exit, changeFont;
    private JPanel prev;
    private ImageIcon mutedIMG, unMutedIMG;
    private JToggleButton muteButton;
    public SettingScreen(FullScreenUI frame,JPanel previous) {
        super(frame,previous);
        prev = previous;
        audio = new JSlider();
        audio.setOpaque(false);
        changePassword = new JButton("CHANGE PASSWORD");
        debug = new JButton("DEBUG");
        credits = new JButton("CREDITS");
        muteButton = new JToggleButton("MUTE");
        highContrast = new JButton("High Contrast");
        changeFont = new JButton("Change Font");
        exit = new JButton("EXIT");
        changePassword.addActionListener(e -> changePasswordButton());
        debug.addActionListener(e -> debugButton());
        credits.addActionListener(e -> creditsButton());
        muteButton.addActionListener(e -> muteButton());
        highContrast.addActionListener(e -> highContrast());
        changeFont.addActionListener(e -> changeFont());
        exit.addActionListener(e -> exitButton());

        //images
        mutedIMG = new ImageIcon("muted.png");
        unMutedIMG = new ImageIcon("unMuted.png");


        muteButton.setIcon(unMutedIMG);

        this.add(credits);
        this.add(muteButton);
        this.add(highContrast);
        this.add(changeFont);
        this.add(exit);
        this.add(audio);

    }

    public void setPrev(Screen prev){
        this.prev = prev;
        System.out.println(this.prev.getClass().getName());
        if(Objects.nonNull(this.prev)) {
            if (!(this.prev.getClass().getName().equals("MainMenu") || this.prev.getClass().getName().equals("LoginScreen") || this.prev.getClass().getName().equals("RegisterScreen"))) {
                this.add(changePassword);
                this.add(debug);
            }
        }
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
        audio.setBounds(width/3, height/2, width/3, sliderHeight);


        int textX = sliderX - textWidth;
        int textY = sliderY + (sliderHeight - textHeight) / 2 + metrics.getAscent();

        g.drawString("AUDIO",width/3, textY);

        exit.setBounds(width / 30, height/22, width / 10, height / 12);
        exit.setFont(new Font("SansSerif", Font.PLAIN, 24));
        muteButton.setBounds(width/2+width/15,height/3,width/10,height/12);
        muteButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        highContrast.setBounds(width/3+width/12,height - height/4,width/6,height/12);
        highContrast.setFont(new Font("SansSerif", Font.PLAIN, 24));
        changeFont.setBounds(width/3+width/12,height - height/3,width/6,height/12);
        changeFont.setFont(new Font("SansSerif", Font.PLAIN, 24));
        changePassword.setBounds(width/3, height - height/6, width/6, height/12);
        debug.setBounds(width-width/2,height - height/6,width/6,height/12);
        credits.setBounds(width-width/8,height - height/8,width/10,height/12);
        changePassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        debug.setFont(new Font("SansSerif", Font.PLAIN, 24));
        credits.setFont(new Font("SansSerif", Font.PLAIN, 24));
        repaint();
    }
    public void changePasswordButton() {
        swapScreens(new ChangePasswordScreen(frame,this));
    }
    public void debugButton() {
        swapScreens(new DebugScreen(frame,this));
    }
    public void creditsButton() {
        swapScreens(new CreditsScreen(frame,this));
    }
    public void exitButton() {
        swapScreens(new ExitScreen(frame,this));
    }
    //only changes background
    public void highContrast() {
        swapScreens(new ExitScreen(frame,this));
    }
    public void changeFont(){
        //
    }

    public void setGameSettings(){
        this.remove(changePassword);
        this.remove(debug);
        revalidate();
    }
    public void audioSlider() {
        // mute function
    }
    public void muteButton() {
        if (muteButton.isSelected()){
            muteButton.setIcon(unMutedIMG);
        }
        else {
            muteButton.setIcon(mutedIMG);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.remove(settings);
        setComponents(g);


    }
}
