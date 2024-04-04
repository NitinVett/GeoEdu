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
     * Updates the positions and sizes of buttons based on the current screen size.
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
     * Handles the action when the Global button is clicked.
     */
    public void globalButton() {
        swapScreens(new GameTypeSelectorScreen(frame,this,user,"Global Mode",null));
    }

    /**
     * Handles the action when the Continental button is clicked.
     */
    public void continentalButton() {
        if(user.getHighScore() >= 25 || user.getUsername().equals("Debugger")) {
            swapScreens(new ContinentalModeSelectorScreen(frame, this, user, "Continental Mode"));
        } else {
            this.displayErrorMessage("You need a high score of 25 to play this gamemode, gain more score to unlock this mode!");
        }
    }

    /**
     * Handles the action when the Micro Nations button is clicked.
     */
    public void microNationButton() {
        if(user.getHighScore() >= 100|| user.getUsername().equals("Debugger")) {
            swapScreens(new GameTypeSelectorScreen(frame, this, user, "Micro Nation Mode", null));
        }else {
            this.displayErrorMessage("You need a high score of 100 to play this gamemode, gain more score to unlock this mode!");
        }
    }

    /**
     * Handles the action when the Exit button is clicked.
     */
    public void exitButton() {
        swapScreens(prev);
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
