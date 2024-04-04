import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The MainMenu class represents the main menu screen of the application.
 * It contains buttons for login, registration, and exiting the application.
 */
public class MainMenu extends Screen {

    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;
    private JLabel pirateLabel;
    private BufferedImage pirateIMG;
    private Image resizedPirateIMG;
    private Image backgroundImg;
    private Font buttonFont;

    /**
     * Constructs a MainMenu object with the specified FullScreenUI frame.
     *
     * @param frame The FullScreenUI frame that contains this screen.
     */
    MainMenu(FullScreenUI frame) {
        super(frame, null);
        try {
            pirateIMG = ImageIO.read(new File("resources/pirate.png"));
            backgroundImg = ImageIO.read(new File("resources/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        exitButton = new JButton("Exit");

        loginButton.addActionListener(e -> loginButton());
        registerButton.addActionListener(e -> registerButton());
        exitButton.addActionListener(e -> exitButton());

        pirateLabel = new JLabel();
        this.add(pirateLabel);
        this.add(loginButton);
        this.add(registerButton);
        this.add(exitButton);
        repaint();
    }

    /**
     * Initializes the components of the main menu screen.
     * Adjusts the size and position of the pirate image.
     */
    private void initializeComponents() {
        int width = getWidth();
        int height = getHeight();
        resizedPirateIMG = pirateIMG.getScaledInstance(width / 2, height / 2, Image.SCALE_SMOOTH);
        pirateLabel.setBounds(width / 2 + width / 7, height / 3, width, height);
        pirateLabel.setIcon(new ImageIcon(resizedPirateIMG));
        repaint();
        revalidate();
    }

    /**
     * Updates the positions of the buttons on the main menu screen.
     */
    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - (width / 8) / 2;
        Image scaledImage = backgroundImg.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        createButtons(loginButton, scaledImage, width / 60);
        createButtons(registerButton, scaledImage, width / 60);
        createButtons(exitButton, scaledImage, width / 60);
        loginButton.setBounds(mainButtonX, height / 3, width / 8, height / 12);
        registerButton.setBounds(mainButtonX, height / 3 + height / 7 - height / 100, width / 8, height / 12);
        exitButton.setBounds(mainButtonX, height / 2 + height / 10, width / 8, height / 12);
    }

    /**
     * Handles the action when the login button is clicked.
     * Swaps the screen to the login screen.
     */
    public void loginButton() {
        swapScreens(new LoginScreen(frame, this));
    }

    /**
     * Handles the action when the register button is clicked.
     * Swaps the screen to the register screen.
     */
    public void registerButton() {
        swapScreens(new RegisterScreen(frame, this));
    }

    /**
     * Handles the action when the exit button is clicked.
     * Swaps the screen to the exit screen.
     */
    public void exitButton() {
        swapScreens(new ExitScreen(frame, this));
    }

    /**
     * Overrides the paintComponent method to paint additional UI elements.
     *
     * @param g The Graphics object to protect.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background
        Graphics2D g2D = (Graphics2D) g;
        drawTitle(g2D);
        updateButtonPositions();
        initializeComponents();
    }
}
