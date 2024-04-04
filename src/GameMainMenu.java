import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The GameMainMenu class represents the main menu screen of the game.
 */
public class GameMainMenu extends Screen {
    /**
     * JButton for newGameButton
     */
    JButton newGameButton;
    /**
     * JButton for continueButton
     */
    JButton continueButton;
    /**
     * JButton for tutorialButton
     */
    JButton tutorialButton;
    /**
     * image
     */
    Image image;
    /**
     * JButton for highScoresButton
     */
    JButton highScoresButton;
    /**
     * JButton for logoutButton
     */
    JButton logoutButton;
    /**
     * plank image path
     */


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
            image = ImageIO.read(getClass().getResourceAsStream("plank.png"));
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
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;
        int mainButtonYIncrement = height / 10;
        Image scaledImage = image.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(newGameButton, scaledImage,width/60);
        createButtons(continueButton, scaledImage,width/60);
        createButtons(tutorialButton, scaledImage,width/60);
        createButtons(highScoresButton, scaledImage,width/60);
        createButtons(logoutButton, scaledImage,width/60);
        newGameButton.setBounds(mainButtonX, mainButtonY, width / 5, height / 12);
        continueButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        tutorialButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        highScoresButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        logoutButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 4, width / 5, height / 12);
        revalidate();
    }

    /**
     * Handles the action when the New Game button is clicked.
     */
    public void newGameButton() {
        swapScreens(new GamemodeSelectorScreen(frame,this,user));
    }

    /**
     * Handles the action when the Continue button is clicked.
     */
    public void continue_Button() {
        if(!user.getGameData().equals("None")) {
            String type = user.getGameData().split(";")[1].split(":")[1];
            String mode = user.getGameData().split(";")[2].split(":")[1];
            String continent = user.getGameData().split(";")[3].split(":")[1];

            GameTesting game = new GameTesting(frame, user, mode, continent, type);
            game.loadFile(user.getGameData());
        }
        else {
            this.displayErrorMessage("You have no saved game available");
        }
    }

    /**
     * Handles the action when the High Scores button is clicked.
     */
    public void highScoreButton() {
        swapScreens(new HighScoreScreen(frame,0,this));
    }

    /**
     * Handles the action when the Tutorial button is clicked.
     */
    public void tutorialButton() {
        swapScreens(new TutorialScreen(frame, this, user));
    }

    /**
     * Handles the action when the Log Out button is clicked.
     */
    public void logOutButton() {
        frame.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background
        drawTitle((Graphics2D) g);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
