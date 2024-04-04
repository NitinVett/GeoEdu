import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The GameTypeSelectorScreen class represents the user interface for selecting the game type.
 * Users can choose between Marathon, Timed, and Exploration game modes.
 */
public class GameTypeSelectorScreen extends Screen {

    /** Button to select the Marathon game mode. This mode might involve continuous play until a failure condition is met. */
    JButton marathon;

    /** Button to select the Timed game mode. In this mode, players are likely to be challenged to achieve something within a set time limit. */
    JButton timed;

    /** Button to select the Exploration game mode. This mode may encourage players to explore content at their own pace with less focus on scoring or time limits. */
    JButton exploration;

    /** Button to exit the game type selection screen and return to the previous screen. */
    JButton esc;

    /** The currently selected game mode, determining which type of game the player will engage with. */
    String mode;

    /** The continent selection, applicable in certain game modes where geographic or regional specificity is relevant. */
    String continent;

    /** Background image used for the game type selection screen, possibly to enhance visual appeal or theme consistency. */
    Image plankIMG;


    /**
     * Constructs a GameTypeSelectorScreen object. This screen allows the user to select a game mode
     * (Marathon, Timed, Exploration) and sets up the UI elements accordingly. It initializes buttons
     * for each game mode and an escape button, associating actions with each button to handle user selection.
     *
     * @param frame The FullScreenUI frame that contains this screen, providing context for screen transitions.
     * @param previous The previous screen object, allowing for navigation back to the prior state.
     * @param user The Player object representing the current user, used for game state initialization.
     * @param mode The selected game mode, affecting which game logic is applied.
     * @param continent The selected continent, relevant in certain game modes for filtering content.
     */
    public GameTypeSelectorScreen(FullScreenUI frame, Screen previous, Player user, String mode, String continent) {
        super(frame, previous, user);
        this.continent = continent;
        this.mode = mode;
        marathon = new JButton("Marathon");
        timed = new JButton("Timed");
        exploration = new JButton("Exploration");
        esc = new JButton();
        marathon.addActionListener(e -> marathonButton());
        timed.addActionListener(e -> timedButton());
        exploration.addActionListener(e -> explorationButton());
        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(  getClass().getResourceAsStream("/escape.png"));
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());
        this.add(marathon);
        this.add(timed);
        this.add(exploration);
        this.add(esc);
    }

    /**
     * Dynamically updates the positions and sizes of the game mode selection buttons and the escape button
     * based on the current dimensions of the window. This ensures that the UI elements are appropriately
     * scaled and positioned regardless of window size.
     */
    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width - width / 3;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;
        Image scaledImage = plankIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        createButtons(marathon, scaledImage, width / 60);
        createButtons(timed, scaledImage, width / 60);
        createButtons(exploration, scaledImage, width / 60);
        marathon.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        timed.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        exploration.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);
        revalidate();
    }

    /**
     * Initiates the game in Exploration mode by creating a new GameTesting instance with the specified mode
     * and transitioning to the gameplay screen. This method is triggered when the Exploration button is clicked.
     */
    public void explorationButton() {
        GameTesting playExploration = new GameTesting(frame, user, mode, continent, "Exploration");
        playExploration.newGame(false);
    }

    /**
     * Initiates the game in Marathon mode by creating a new GameTesting instance with the specified mode
     * and transitioning to the gameplay screen. This method is triggered when the Marathon button is clicked.
     */
    public void marathonButton() {
        GameTesting playMarathon = new GameTesting(frame, user, mode, continent, "Marathon");
        playMarathon.newGame(false);
    }

    /**
     * Initiates the game in Timed mode by creating a new GameTesting instance with the specified mode
     * and transitioning to the gameplay screen. This method is triggered when the Timed button is clicked.
     */
    public void timedButton() {
        GameTesting playTimed = new GameTesting(frame, user, mode, continent, "Timed");
        playTimed.newGame(false);
    }

    /**
     * Handles the action to exit the game type selection screen and return to the previous screen.
     * This method is triggered when the escape button is clicked.
     */
    public void exitButton() {
        swapScreens(prev);
    }

    /**
     * Custom painting method for drawing UI elements specific to the GameTypeSelectorScreen.
     * This includes any text, images, or backgrounds that are part of the screen's design.
     *
     * @param g The Graphics object used for drawing operations.
     */
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 8;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background
        g.setFont(loadFont("/Viner.ttf", 32));
        g.drawString("What game type will you play today!", mainButtonX, mainButtonY);
        updateButtonPositions();
        repaint();
    }
}
