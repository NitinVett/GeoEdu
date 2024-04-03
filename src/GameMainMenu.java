import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The GameMainMenu class represents the main menu screen of the game.
 */
public class GameMainMenu extends Screen {
    JButton newGameButton;
    JButton continueButton;
    JButton tutorialButton;
    Image image;
    JButton highScoresButton;
    JButton logoutButton;
    String imagePath= "resources/plank.png";

    /**
     * Constructs a GameMainMenu object with the specified parameters.
     * @param frame The FullScreenUI object.
     * @param previous The previous Screen object.
     * @param user The Player object.
     */
    public GameMainMenu(FullScreenUI frame, Screen previous, Player user) {
        super(frame, previous, user);
        // Custom buttons
        newGameButton = new JButton("New Game");
        continueButton = new JButton("Continue");
        tutorialButton = new JButton("Tutorial");
        highScoresButton = new JButton("High Scores");
        logoutButton = new JButton("Log Out");
        // Listeners
        newGameButton.addActionListener(e -> newGameButton());
        continueButton.addActionListener(e -> continue_Button());
        logoutButton.addActionListener(e -> logOutButton());
        highScoresButton.addActionListener(e -> highScoreButton());
        tutorialButton.addActionListener(e -> tutorialButton());
        try {
            image = ImageIO.read(new File("resources/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        add(newGameButton);
        add(continueButton);
        add(tutorialButton);
        add(highScoresButton);
        add(logoutButton);
    }

    /**
     * Updates the positions and sizes of buttons based on the current screen size.
     */
    private void updateButtonPositions() {
        // Implementation for updating button positions
    }

    /**
     * Handles the action when the New Game button is clicked.
     */
    public void newGameButton() {
        // Implementation for handling new game button click
    }

    /**
     * Handles the action when the Continue button is clicked.
     */
    public void continue_Button() {
        // Implementation for handling continue button click
    }

    /**
     * Handles the action when the High Scores button is clicked.
     */
    public void highScoreButton() {
        // Implementation for handling high scores button click
    }

    /**
     * Handles the action when the Tutorial button is clicked.
     */
    public void tutorialButton() {
        // Implementation for handling tutorial button click
    }

    /**
     * Handles the action when the Log Out button is clicked.
     */
    public void logOutButton() {
        // Implementation for handling log out button click
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background
        drawTitle((Graphics2D) g);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
