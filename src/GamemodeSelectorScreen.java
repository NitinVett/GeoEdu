import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The GamemodeSelectorScreen class represents the screen where the player can select different game modes.
 */
public class GamemodeSelectorScreen extends Screen {
    JButton global;
    JButton continental;
    JButton microNations;
    JButton esc;
    Image plankIMG;

    /**
     * Constructs a GamemodeSelectorScreen object with the specified parameters.
     * @param frame The FullScreenUI object.
     * @param previous The previous Screen object.
     * @param user The Player object.
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
            escIcon = ImageIO.read(new File("resources/escape.png"));
            plankIMG = ImageIO.read(new File("resources/plank.png"));
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
     * Updates the positions and sizes of buttons based on the current screen size.
     */
    private void updateButtonPositions() {
        // Implementation for updating button positions
    }

    /**
     * Handles the action when the Global button is clicked.
     */
    public void globalButton() {
        // Implementation for handling global button click
    }

    /**
     * Handles the action when the Continental button is clicked.
     */
    public void continentalButton() {
        // Implementation for handling continental button click
    }

    /**
     * Handles the action when the Micro Nations button is clicked.
     */
    public void microNationButton() {
        // Implementation for handling micro nations button click
    }

    /**
     * Handles the action when the Exit button is clicked.
     */
    public void exitButton() {
        // Implementation for handling exit button click
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background

        g.setFont(loadFont("resources/Viner.ttf", 32));
        g.drawString("Select a mode to explore!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
