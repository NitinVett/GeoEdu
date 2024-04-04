import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * SettingScreen class represents the screen for game settings.
 * It extends Screen.
 */
public class SettingScreen extends Screen {

    /**
     * Slider for adjusting the game's audio volume. Users can slide to increase or decrease the volume.
     */
    private JSlider audio;

    /**
     * Button to navigate to the change password screen, allowing users to update their account password.
     */
    private JButton changePassword;

    /**
     * Button for accessing the debug features of the game, typically used for troubleshooting or testing.
     */
    private JButton debug;

    /**
     * Button to view the game's credits, acknowledging the creators and contributors.
     */
    private JButton credits;

    /**
     * Button to toggle the game's audio between mute and unmute states.
     */
    private JButton muteButton;

    /**
     * Button to exit from the current settings screen and return to the previous screen or menu.
     */
    private JButton exit;

    /**
     * Button for logging out of the user's account and returning to the login screen.
     */
    private JButton logout;

    /**
     * Manages playback of game sounds and music, including volume control and muting functionality.
     */
    private GameSound sound;

    /**
     * Represents the current player, holding data such as preferences and gameplay statistics.
     */
    private Player user;

    /**
     * Images used for the settings screen's aesthetic elements, like backgrounds or thematic icons.
     */
    private Image plankIMG, scrollIMG, resizedMutedIMG, resizedUnMutedIMG;

    /**
     * Buffered images for the mute and unmute icons, used alongside the mute toggle button.
     */
    private BufferedImage mutedIMG, unMutedIMG;

    /**
     * The main background image for the settings screen, enhancing the visual appeal.
     */
    public Image backgroundImage;

    /**
     * Indicates whether the game's sound is currently muted, toggled via the mute button.
     */
    private boolean muted = false;


    /**
     * Constructor for SettingScreen.
     *
     * @param frame The FullScreenUI frame.
     * @param previous The previous screen.
     * @param user The player object.
     */
    public SettingScreen(FullScreenUI frame, Screen previous, Player user) {
        super(frame, previous, user);

        sound = new GameSound("backgroundmusic.wav");
        sound.play();
        prev = previous;
        audio = new JSlider(0, 100, 15);
        audio.addChangeListener(e -> changeVolume());
        audio.setOpaque(false);
        changePassword = new JButton("CHANGE PASSWORD");
        debug = new JButton("DEBUG");
        credits = new JButton("CREDITS");
        muteButton = new JButton();
        exit = new JButton();
        logout = new JButton("EXIT");
        disableSettingButton();
        BufferedImage escIcon = null;
        try {

            escIcon = ImageIO.read(  getClass().getResourceAsStream("/escape.png"));
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
            scrollIMG = ImageIO.read(getClass().getResourceAsStream("/scroll.png"));
            mutedIMG = ImageIO.read(getClass().getResourceAsStream("/muted.png"));
            unMutedIMG = ImageIO.read(getClass().getResourceAsStream("/unMuted.png"));
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
        logout.addActionListener(e -> logoutButton());
        this.add(credits);
        this.add(muteButton);
        this.add(exit);
        this.add(logout);
        this.add(audio);
    }
    /**
     * Method to change the volume based on the slider value.
     */
    public void changeVolume() {
        sound.setVolume(audio.getValue());
    }
    /**
     * Method to set the previous screen.
     *
     * @param prev The previous screen.
     */
    public void setPrev(Screen prev) {
        this.prev = prev;
        if (Objects.nonNull(this.prev)) {
            if (!(this.prev.getClass().getName().equals("MainMenu") || this.prev.getClass().getName().equals("LoginScreen") || this.prev.getClass().getName().equals("RegisterScreen"))) {
                this.remove(debug);
                this.add(changePassword);
            } else {
                this.add(debug);
            }
        }
    }
    /**
     * Method to set the user.
     *
     * @param user The player object.
     */
    public void setUser(Player user) {
        this.user = user;
    }
    /**
     * Method to set the components such as buttons.
     *
     * @param g The Graphics object.
     */
    public void setComponents(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        disableSettingButton();
        resizedMutedIMG = mutedIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        resizedUnMutedIMG = unMutedIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        muteButton.setContentAreaFilled(false);
        muteButton.setBorderPainted(false);
        Font font = new Font("SansSerif", Font.BOLD, 36);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth("GEOCRAFT");
        int textHeight = metrics.getHeight();
        int sliderX = width / 2 - width / 10;
        int sliderY = height / 3;
        int sliderWidth = width / 3;
        int sliderHeight = height / 15;
        audio.setBounds(width / 3, height / 2, width / 3, sliderHeight);
        int textX = sliderX - textWidth;
        int textY = sliderY + (sliderHeight - textHeight) / 2 + metrics.getAscent();
        g.drawString("AUDIO", width / 3, textY);
        Image scaledImage = plankIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        Image scrollIMGScaled = scrollIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        createButtons(changePassword, scaledImage, width / 75);
        createButtons(debug, scaledImage, width / 60);
        createButtons(credits, scrollIMGScaled, width / 60);
        createButtons(logout,scaledImage,width/60);
        logout.setBounds(width/30, height - height / 8, width / 10, height / 12);
        exit.setBounds(width / 30, height / 22, 50, 50);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        muteButton.setBounds(width / 2 + width / 15, height / 3, width / 10, height / 12);
        changePassword.setBounds(width / 3 + width / 12, height - height / 6, width / 6, height / 12);
        debug.setBounds(width / 3 + width / 12, height - height / 4, width / 6, height / 12);
        credits.setBounds(width - width / 8, height - height / 8, width / 10, height / 12);
    }
    /**
     * Method to handle the action when the change password button is clicked.
     */
    public void changePasswordButton() {
        swapScreens(new ChangePasswordScreen(frame, this, user));
    }
    /**
     * Method to handle the action when the debug button is clicked.
     */
    public void debugButton() {
        swapScreens(new DebugScreen(frame, this));
    }
    /**
     * Method to handle the action when the credits button is clicked.
     */
    public void creditsButton() {
        swapScreens(new CreditsScreen(frame, this));
    }
    /**
     * Method to handle the action when the exit button is clicked.
     */
    public void exitButton() {
        swapScreens(prev);
    }
    /**
     * Method to handle the action when the logout button is clicked.
     */
    public void logoutButton(){
        frame.dispose();
    }
    /**
     * Method to handle the action when the mute button is clicked.
     */
    public void muteButton() {
        muted = !muted;
        muteButton.setContentAreaFilled(false);
        muteButton.setBorderPainted(false);
    }

    /**
     * Override method to paint the component.
     *
     * @param g The Graphics object.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents(g2D);
        if (muted) {
            sound.setVolume(0);
            muteButton.setIcon(new ImageIcon(resizedMutedIMG));
        } else {
            sound.setVolume(audio.getValue());
            muteButton.setIcon(new ImageIcon(resizedUnMutedIMG));
        }
        drawTitle(g2D);
    }
}