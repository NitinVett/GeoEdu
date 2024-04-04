import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * StatScreen class represents the screen displaying player statistics.
 * It extends Screen.
 */
public class StatScreen extends Screen {

    /**
     * Button that, when clicked, returns the user to the previous screen. It is commonly used
     * in various interfaces to navigate back in the application's screen hierarchy.
     */
    JButton backButton;

    /**
     * An image used as a decorative background element or for button icons within the interface.
     * The plank image is typically used to add thematic consistency or enhance the visual appeal
     * of the screen.
     */
    Image plankIMG;


    /**
     * Constructor for StatScreen.
     *
     * @param frame The FullScreenUI frame.
     * @param previous The previous screen.
     * @param user The player object.
     */
    public StatScreen(FullScreenUI frame, Screen previous, Player user) {
        super(frame, previous, user);
        backButton = new JButton("Main Menu");
        backButton.addActionListener(e -> {
            back();
        });
        try {
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.add(backButton);
    }

    /**
     * Method to navigate back to the main menu.
     */
    public void back() {
        swapScreens(new GameMainMenu(frame, null, user));
    }

    /**
     * Set the components such as buttons.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        Image scaledImage = plankIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        createButtons(backButton, scaledImage, width / 60);
        backButton.setBounds(getWidth() / 2 - (getWidth() / 10 / 2), getHeight() - getHeight() / 5, getWidth() / 10, getHeight() / 10);
    }

    /**
     * Override method to paint the component.
     *
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Cast to Graphics2D for advanced drawing features
        Graphics2D g2d = (Graphics2D) g;
        // Set antialiasing for smoother fonts and lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw title
        drawTitle(g2d, "STATS");
        int height = getHeight();
        int width = getWidth();
        // Draw stat labels and values
        g2d.setFont(new Font("Arial", Font.BOLD, width / 50)); // Set font for labels
        g2d.drawString("High Score:", width / 3, height / 2 - height / 20);
        g2d.drawString("Accuracy:", width / 3, height / 2 + height / 120);
        // Draw stat values with some spacing from labels
        g2d.drawString(Integer.toString(user.getHighScore()), width / 3 + width / 5, height / 2 - height / 20);
        String accuracy = String.format("%.2f", user.getAccuracy()) + "%";
        g2d.drawString(accuracy, width / 3 + width / 5, height / 2 + height / 120);
        setComponents();
    }
}
