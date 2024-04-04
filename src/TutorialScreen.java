import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * TutorialScreen class represents the screen that provides instructions and options to the user for tutorial purposes.
 * It extends Screen and implements KeyListener to handle keyboard events.
 */
public class TutorialScreen extends Screen implements KeyListener {

    private final Screen previousScreen;
    private JLabel scrollLabel;
    private JButton explorationButton;
    private BufferedImage scrollImage;
    private Image resizedScroll, plankIMG;

    /**
     * Constructor for TutorialScreen.
     *
     * @param frame The FullScreenUI frame.
     * @param previousScreen The previous screen before entering the tutorial.
     * @param user The player object.
     */
    public TutorialScreen(FullScreenUI frame, Screen previousScreen, Player user) {
        super(frame, previousScreen, user);
        this.previousScreen = previousScreen;
        setFocusable(true);
        requestFocusInWindow();
        explorationButton = new JButton();
        explorationButton.addActionListener(e -> explorationButton());
        explorationButton.setText("Exploration Mode");
        explorationButton.setFont(loadFont("resources/Viner.ttf", 17));
        this.add(explorationButton);
        scrollLabel = gameRundown();
        try {
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
            scrollImage = ImageIO.read(getClass().getResourceAsStream("/scroll.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scrollLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        scrollLabel.setVerticalTextPosition(SwingConstants.CENTER);
        scrollLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scrollLabel.setForeground(Color.BLACK);
        this.add(scrollLabel);
        repaint();
    }

    /**
     * Update the positions of buttons based on the current screen size.
     */
    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        explorationButton.setBounds(width / 3 + width / 9, height - height / 8, width / 9, height / 15);
        revalidate();
    }

    /**
     * Action to perform when Exploration button is clicked.
     */
    private void explorationButton() {
        GameTesting playExploration = new GameTesting(frame, user, "Global Mode", null, "Exploration");
        playExploration.newGame(false);
    }

    /**
     * Set the components such as buttons and labels.
     *
     * @param g The Graphics object.
     */
    public void setComponents(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Image scaledImage = plankIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        createButtons(explorationButton, scaledImage, width / 90);
        resizedScroll = scrollImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        scrollLabel.setIcon(new ImageIcon(resizedScroll));
        scrollLabel.setBounds(0, height / 30, width, height);
        scrollLabel.setFont(new Font("SansSerif", Font.PLAIN, 19));
        repaint();
        revalidate();
    }

    /**
     * Generate and format the JLabel for game instructions.
     *
     * @return JLabel with formatted game instructions.
     */
    private JLabel gameRundown() {
        JLabel hints = new JLabel();
        String textHint = "Welcome to Geocraft,\n" +
                "\n" +
                "1. Select a game mode (Global, Continental or Micro-Nations)\n" +
                "2. Select a game type (Timed, Marathon or Exploration)\n" +
                // More instructions...
                "Good luck and have fun!";
        try {
            String[] lines = textHint.split("\n");
            StringBuilder content = new StringBuilder();
            for (String line : lines) {
                content.append(line).append("<br>");
            }
            hints.setText("<html>" + content.toString() + "</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hints;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_ESCAPE) {
            switchToPreviousScreen();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Unused
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
    }

    /**
     * Switch to the previous screen.
     */
    private void switchToPreviousScreen() {
        swapScreens(prev);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents(g);
        updateButtonPositions();
    }
}