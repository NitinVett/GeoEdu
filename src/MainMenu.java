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

    /**
     * Button that triggers the login process, verifying user credentials against stored data
     * and granting access to the application's main features upon successful authentication.
     */
    private JButton loginButton;

    /**
     * Button that navigates to the registration screen, allowing new users to create an account
     * and gain access to the application's features.
     */
    private JButton registerButton;

    /**
     * Button that closes the application or returns the user to the previous screen,
     * providing an exit point from the current interface.
     */
    private JButton exitButton;

    /**
     * Label that displays a graphical representation or icon, in this context, a pirate.
     * Used to add thematic elements or visual identity to the screen.
     */
    private JLabel pirateLabel;

    /**
     * An image containing the original pirate graphic. This BufferedImage is typically loaded
     * from an external source and serves as the basis for any scaling or manipulation.
     */
    private BufferedImage pirateIMG;

    /**
     * A scaled version of the pirate image, resized to fit specific layout requirements or
     * design preferences on the screen.
     */
    private Image resizedPirateIMG;

    /**
     * An image used as the background for the screen, setting the visual theme and providing
     * a cohesive aesthetic appearance. This might cover the entire screen or specific areas.
     */
    private Image backgroundImg;



    /**
     * Constructs a MainMenu object with the specified FullScreenUI frame.
     *
     * @param frame The FullScreenUI frame that contains this screen.
     */
    MainMenu(FullScreenUI frame) {
        super(frame, null);
        try {

            pirateIMG = ImageIO.read(getClass().getResourceAsStream("/pirate.png"));
            backgroundImg = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
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
