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

    /**
     * A reference to the previous screen within the application's navigation hierarchy.
     * This variable is marked as final, indicating that the reference to the previous screen
     * is set once upon creation and does not change throughout the lifecycle of this object.
     */
    private final Screen previousScreen;

    /**
     * A JLabel used to display a scroll image or text within the UI. This label can serve
     * as a decorative element or as a container for textual information presented on a scroll-like background.
     */
    private JLabel scrollLabel;

    /**
     * A JButton that, when clicked, initiates the exploration game mode. This button provides
     * users with the option to start or resume an exploration-based gameplay experience.
     */
    private JButton explorationButton;

    /**
     * A BufferedImage object representing the scroll image. This image can be used as a background
     * for text or other UI elements to enhance the thematic design of the screen.
     */
    private BufferedImage scrollImage;

    /**
     * A resized version of the scroll image adapted for specific UI requirements. This image is derived
     * from the original scrollImage to fit different screen sizes or design layouts while maintaining
     * the intended aesthetic.
     */
    private Image resizedScroll;

    /**
     * An Image object representing a plank-themed background or decorative element. Similar to the scroll image,
     * this can be used to augment the visual theme of the screen, possibly as a background for buttons or text fields.
     */
    private Image plankIMG;


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
        explorationButton.setFont(loadFont("/Viner.ttf", 17));
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