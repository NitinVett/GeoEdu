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

    JButton marathon; // Button for Marathon mode
    JButton timed; // Button for Timed mode
    JButton exploration; // Button for Exploration mode
    JButton esc; // Escape button
    String mode; // Game mode
    String continent; // Continent selection
    Image plankIMG; // Background image

    /**
     * Constructs a GameTypeSelectorScreen object with the specified frame, previous screen, user, game mode, and continent.
     *
     * @param frame     The FullScreenUI frame that contains this screen.
     * @param previous  The previous Screen object to return to when navigating back.
     * @param user      The current player.
     * @param mode      The selected game mode.
     * @param continent The selected continent.
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
     * Updates the positions of buttons based on the current window size.
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
     * Starts the game in Exploration mode.
     */
    public void explorationButton() {
        GameTesting playExploration = new GameTesting(frame, user, mode, continent, "Exploration");
        playExploration.newGame(false);
    }

    /**
     * Starts the game in Marathon mode.
     */
    public void marathonButton() {
        GameTesting playMarathon = new GameTesting(frame, user, mode, continent, "Marathon");
        playMarathon.newGame(false);
    }

    /**
     * Starts the game in Timed mode.
     */
    public void timedButton() {
        GameTesting playTimed = new GameTesting(frame, user, mode, continent, "Timed");
        playTimed.newGame(false);
    }

    /**
     * Exits to the previous screen.
     */
    public void exitButton() {
        swapScreens(prev);
    }

    /**
     * Custom paint component method to draw additional UI elements, like titles or backgrounds.
     *
     * @param g The Graphics object to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 8;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background
        g.setFont(loadFont("resources/Viner.ttf", 32));
        g.drawString("What game type will you play today!", mainButtonX, mainButtonY);
        updateButtonPositions();
        repaint();
    }
}
