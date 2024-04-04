import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The GamemodeSelectorScreen class provides a GUI for selecting different game modes within the application.
 * It allows the player to choose between global, continental, or micro nations modes, enforcing certain
 * requirements for mode access based on the player's high score.
 */
public class GamemodeSelectorScreen extends Screen {
    /**
     * JButton for global Button
     */
    JButton global;
    /**
     * JButton for continental Button
     */
    JButton continental;
    /**
     * JButton for microNations Button
     */
    JButton microNations;
    /**
     * JButton for esc Button
     */
    JButton esc;
    /**
     * plank image
     */
    Image plankIMG;

    /**
     * Constructs a GamemodeSelectorScreen with buttons for each game mode and an escape button.
     * Initializes buttons and loads necessary images.
     *
     * @param frame    The FullScreenUI object to which this screen belongs.
     * @param previous The previous screen from which the user navigated to this screen.
     * @param user     The Player object representing the current user.
     */
    public GamemodeSelectorScreen(FullScreenUI frame, Screen previous, Player user) {
        super(frame, previous, user);

        global = new JButton("Global");
        continental = new JButton("Continental");
        microNations = new JButton("Micro Nations");
        esc = new JButton();
        global.addActionListener(e -> globalButton());
        continental.addActionListener(e -> continentalButton());
        microNations.addActionListener(e -> microNationButton());
        esc.addActionListener(e -> exitButton());
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
        add(global);
        add(continental);
        add(microNations);
        add(esc);
    }

    /**
     * Dynamically updates the positions and appearances of the mode selection and escape buttons
     * based on the current screen dimensions. Uses the plank image for button backgrounds.
     */
    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 3 - width / 5;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;
        Image scaledImage = plankIMG.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(global,scaledImage,width/60);
        createButtons(continental,scaledImage,width/60);
        createButtons(microNations,scaledImage,width/60);
        global.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        continental.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        microNations.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);
        revalidate();
    }

    /**
     * Triggers the transition to the global game mode screen when the global mode button is clicked.
     */
    public void globalButton() {
        swapScreens(new GameTypeSelectorScreen(frame,this,user,"Global Mode",null));
    }

    /**
     * Triggers the transition to the continental game mode selection screen when the continental button is clicked.
     * Access is restricted based on the player's high score or if the player is a debugger.
     */
    public void continentalButton() {
        if(user.getHighScore() >= 25 || user.getUsername().equals("Debugger")) {
            swapScreens(new ContinentalModeSelectorScreen(frame, this, user, "Continental Mode"));
        } else {
            this.displayErrorMessage("You need a high score of 25 to play this gamemode, gain more score to unlock this mode!");
        }
    }

    /**
     * Triggers the transition to the micro nations game mode screen when the micro nations button is clicked.
     * Access is restricted based on the player's high score or if the player is a debugger.
     */
    public void microNationButton() {
        if(user.getHighScore() >= 100|| user.getUsername().equals("Debugger")) {
            swapScreens(new GameTypeSelectorScreen(frame, this, user, "Micro Nation Mode", null));
        }else {
            this.displayErrorMessage("You need a high score of 100 to play this gamemode, gain more score to unlock this mode!");
        }
    }

    /**
     * Handles the action when the Exit (esc) button is clicked, returning the user to the previous screen.
     */
    public void exitButton() {
        swapScreens(prev);
    }

    /**
     * Custom painting method for the GamemodeSelectorScreen. Draws the screen's title and updates the
     * button positions. Overrides the paintComponent method from the superclass.
     *
     * @param g The Graphics object used for drawing operations.
     */
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background

        g.setFont(loadFont("/Viner.ttf", 32));
        g.drawString("Select a mode to explore!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
