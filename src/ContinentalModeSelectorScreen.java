import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ContinentalModeSelectorScreen represents the screen for selecting a continent in the game mode selection.
 */
public class ContinentalModeSelectorScreen extends Screen {
    /**
     * JButton for americas button
     */
    private JButton americas;
    /**
     * JButton for asia button
     */
    private JButton asia;
    /**
     * JButton for europe button
     */
    private JButton europe;
    /**
     * JButton for esc button
     */
    private JButton esc;
    /**
     * JButton for mode button
     */
    private String mode;
    /**
     * plank image
     */
    private Image plankIMG;


    /**
     * Constructs a ContinentalModeSelectorScreen object.
     *
     * @param frame    The FullScreenUI frame.
     * @param previous The previous screen.
     * @param user     The current player.
     * @param mode     The game mode.
     */
    public ContinentalModeSelectorScreen(FullScreenUI frame, Screen previous, Player user, String mode) {
        super(frame, previous);
        this.user = user;
        this.mode = mode;

        // Initialize buttons
        americas = new JButton("Americas");
        asia = new JButton("Asia");
        europe = new JButton("Europe");
        esc = new JButton();

        // Add action listeners to buttons
        americas.addActionListener(e -> americasButton());
        asia.addActionListener(e -> asiaButton());
        europe.addActionListener(e -> europeButton());
        esc.addActionListener(e -> escButton());

        // Load escape icon
        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(getClass().getResourceAsStream("/escape.png"));
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Resize and set escape button icon
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));

        // Add buttons to the screen
        this.add(americas);
        this.add(asia);
        this.add(europe);
        this.add(esc);
    }

    /**
     * Handles the action when the exit button is clicked.
     */
    public void exitButton() {
        swapScreens(prev);
    }

    /**
     * Updates the positions of the buttons on the screen.
     */
    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;

        // Scale the plank image
        Image scaledImage = plankIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);

        // Set button properties and bounds
        createButtons(americas, scaledImage, width / 60);
        createButtons(asia, scaledImage, width / 60);
        createButtons(europe, scaledImage, width / 60);
        americas.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        asia.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        europe.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        esc.setBounds(width / 30, height / 22, 50, 50);

        // Revalidate the screen
        revalidate();
    }

    /**
     * Handles the action when the Americas button is clicked.
     */
    public void americasButton() {
        swapScreens(new GameTypeSelectorScreen(frame, this, user, mode, "Americas"));
    }

    /**
     * Handles the action when the Asia button is clicked.
     */
    public void asiaButton() {
        swapScreens(new GameTypeSelectorScreen(frame, this, user, mode, "Asia"));
    }

    /**
     * Handles the action when the Europe button is clicked.
     */
    public void europeButton() {
        swapScreens(new GameTypeSelectorScreen(frame, this, user, mode, "Europe"));
    }

    /**
     * Handles the action when the escape button is clicked.
     */
    public void escButton() {
        swapScreens(prev);
    }

    /**
     * Handles the action when the logout button is clicked.
     */
    public void logOutButton() {
        frame.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background

        // Draw the text on the screen
        g.setFont(loadFont("/Viner.ttf", 32));
        g.drawString("Select a continent to explore!", mainButtonX, mainButtonY);

        // Update button positions
        updateButtonPositions();
    }
}
