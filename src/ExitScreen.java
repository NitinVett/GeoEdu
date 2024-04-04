import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The ExitScreen class extends the Screen class to present a user interface
 * for confirming the application's exit intention. It displays options to either
 * exit or remain in the application, enhancing user experience by preventing
 * accidental closures.
 */
public class ExitScreen extends Screen {
    /**
     * JButton for no, yes and esc button
     */
    JButton no, yes, esc;

    /**
     * Constructs an ExitScreen with Yes, No, and Escape buttons.
     *
     * @param frame    The main application window, encapsulated within a FullScreenUI object,
     *                 that manages the transition between different screens.
     * @param previous The screen from which the user navigated to the ExitScreen.
     */
    public ExitScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);

        no = createCustomButton("No");
        yes = createCustomButton("Yes");

        no.addActionListener(e -> noButton());
        yes.addActionListener(e -> yesButton());
        this.add(no);
        this.add(yes);

        esc = new JButton();
        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(getClass().getResourceAsStream("/escape.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());
        this.add(esc);
        repaint();
    }

    /**
     * Defines the action to be taken when the exit button (Esc) is clicked.
     * Typically, this would revert to the previous screen, allowing the user
     * to cancel the exit process.
     */
    public void exitButton() {
        swapScreens(prev);
    }

    /**
     * Sets up and positions the components on the screen. Adjusts the position
     * and appearance of the Yes, No, and Escape buttons.
     *
     * @param g The Graphics object used for drawing.
     */
    public void setComponents(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonY = height / 3;

        setButtonBackground(no, width, height);
        setButtonBackground(yes, width, height);
        no.setBounds(width / 3 + width / 6, mainButtonY + (height / 10) * 4, width / 20, height / 20);
        yes.setBounds(width / 2 - width / 15, mainButtonY + (height / 10) * 4, width / 20, height / 20);

        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);

        repaint();
    }

    /**
     * Creates a customized JButton with specified text. The appearance of the button
     * is tailored to fit the theme of the ExitScreen, including font and transparency settings.
     *
     * @param text The text to display on the button.
     * @return A JButton object customized according to the ExitScreen's theme.
     */
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setFont(loadFont("/Viner.ttf", 28));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        return button;
    }

    /**
     * Applies a background image to a JButton, resizing it to fit the button's dimensions.
     * This method enhances the visual appeal of buttons by adding texture or imagery.
     *
     * @param button The JButton object to which the background image is applied.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    private void setButtonBackground(JButton button, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
            Image scaledImage = image.getScaledInstance(width/20, height/20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes when the "No" button is clicked, typically reverting to the previous screen,
     * allowing the user to continue using the application.
     */
    public void noButton() {
        swapScreens(prev);
    }

    /**
     * Executes when the "Yes" button is clicked, closing the application frame and thus,
     * exiting the application.
     */
    public void yesButton() {
        frame.dispose();
    }

    /**
     * Overrides the paintComponent method to perform custom drawing, including setting
     * up the components and drawing the title.
     *
     * @param g The Graphics object for drawing operations.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents(g2D);
        drawTitle(g2D);
    }
}
