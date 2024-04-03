import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The Screen class represents a JPanel used for various screens in the application.
 */
public class Screen extends JPanel {

    private Timer errorMessageTimer;
    private Font titleFont;
    private FontMetrics titleFontMetrics;
    private boolean settingsButtonBoundsSet = false;
    FullScreenUI frame;
    JButton settings;
    private Image backgroundImage;
    private JLabel errorMessageLabel;
    protected Screen prev;
    Player user;
    Cursor defaultCursor;
    Cursor customCursor;

    /**
     * Constructor for Screen without player object.
     *
     * @param frame The FullScreenUI frame.
     * @param previous The previous screen.
     */
    public Screen(FullScreenUI frame, Screen previous) {
        this.frame = frame;
        prev = previous;
        setUp();
        loadCustomCursors();
    }

    /**
     * Constructor for Screen with player object.
     *
     * @param frame The FullScreenUI frame.
     * @param previous The previous screen.
     * @param user The player object.
     */
    public Screen(FullScreenUI frame, Screen previous, Player user) {
        this.frame = frame;
        this.prev = previous;
        this.user = user;
        setUp();
        loadCustomCursors();
    }

    /**
     * Sets up the screen.
     */
    public void setUp() {
        loadBackgroundImage();
        settings = new JButton();
        errorMessageLabel = new JLabel();
        this.add(settings);
        Image image = new ImageIcon("resources/hamburger.png").getImage();
        Image newImage = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon settingsIcon = new ImageIcon(newImage);
        settings.setIcon(settingsIcon);
        settings.addActionListener(e -> settingsButton());
        this.setVisible(true);
        this.setLayout(null);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.requestFocusInWindow(true);
        this.addMouseListener(new CursorMouseListener());
        setUpKeyBindings();
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Loads custom cursors.
     */
    private void loadCustomCursors() {
        defaultCursor = createCustomCursor("click.png");
        customCursor = createCustomCursor("cursor.png");
    }

    /**
     * Creates a custom cursor from an image file.
     *
     * @param resourceName The name of the image resource.
     * @return The custom cursor.
     */
    private Cursor createCustomCursor(String resourceName) {
        try {
            BufferedImage cursorImg = ImageIO.read(getClass().getResource(resourceName));
            Image resizedImage = cursorImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            return Toolkit.getDefaultToolkit().createCustomCursor(resizedImage, new Point(0, 0), resourceName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the custom cursor.
     */
    private void setCustomCursor() {
        frame.setCursor(customCursor);
    }

    /**
     * Sets the default cursor.
     */
    private void setDefaultCursor() {
        frame.setCursor(defaultCursor);
    }

    /**
     * Adds mouse listeners to buttons.
     *
     * @param button The button to add mouse listeners to.
     */
    private void addMouseListeners(JButton button) {
        button.addMouseListener(new ButtonMouseListener(button));
    }

    /**
     * Loads the background image.
     */
    private void loadBackgroundImage() {
        URL imageURL = getClass().getClassLoader().getResource("wallpaper1.gif");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            backgroundImage = icon.getImage();
        } else {
            System.err.println("Resource not found: wallpaper1.gif");
        }
    }

    /**
     * Sets up key bindings.
     */
    public void setUpKeyBindings() {
        setUpKeyBinding(KeyEvent.VK_ESCAPE, "ESCAPE", () -> {
            if (Objects.nonNull(prev)) {
                swapScreens(prev);
            }
        });
    }

    /**
     * Sets up a key binding.
     *
     * @param keyCode The key code.
     * @param actionName The action name.
     * @param action The action to perform.
     */
    private void setUpKeyBinding(int keyCode, String actionName, Runnable action) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    /**
     * Swaps screens.
     *
     * @param panel The panel to swap to.
     */
    public void swapScreens(JPanel panel) {
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }

    /**
     * Handles the action when the settings button is clicked.
     */
    public void settingsButton() {
        swapScreens(frame.getSettings(this, user));
    }

    /**
     * Overrides the paintComponent method to paint the screen.
     *
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            int x = (getWidth() - backgroundImage.getWidth(null)) / 2;
            int y = (getHeight() - backgroundImage.getHeight(null)) / 2;
            g.drawImage(backgroundImage, x, y, this);
        }

        if (!settingsButtonBoundsSet) {
            int width = getWidth();
            int height = getHeight();
            settings.setBounds(width - width / 15, height / 22, 50, 50);
            settings.setBorder(BorderFactory.createEmptyBorder());
            settings.setContentAreaFilled(false);
            settings.setVisible(true);
            settingsButtonBoundsSet = true;
        }
    }

    /**
     * Disables the settings button.
     */
    public void disableSettingButton() {
        settings.setVisible(false);
    }

    /**
     * Creates buttons with specified properties.
     *
     * @param button The button to create.
     * @param scaledImage The scaled image for the button.
     * @param fontSize The font size for the button.
     */
    public void createButtons(JButton button, Image scaledImage, int fontSize){
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFont(loadFont("resources/Viner.ttf", fontSize));
        button.setIcon(new ImageIcon(scaledImage));
        button.addMouseListener(new ButtonMouseListener(button));
    }

    /**
     * Draws the title on the screen.
     *
     * @param g The Graphics2D object.
     */
    public void drawTitle(Graphics2D g) {
        if (titleFont == null) {
            titleFont = loadFont("resources/Viner.ttf", 112);
            titleFontMetrics = g.getFontMetrics(titleFont);
        }

        int titleWidth = titleFontMetrics.stringWidth("GEOCRAFT");
        int width = getWidth();
        int height = getHeight();
        double yValue = height*0.18;
        int xPosition = width / 2 - titleWidth / 2;
        int yPosition = (int) yValue;

        g.setFont(titleFont);
        g.drawString("GEOCRAFT", xPosition, yPosition);
    }

    /**
     * Draws the title with specified text.
     *
     * @param g The Graphics2D object.
     * @param title The title text.
     */
    public void drawTitle(Graphics2D g, String title) {
        if (titleFont == null) {
            titleFont = loadFont("resources/Viner.ttf", 112);
            titleFontMetrics = g.getFontMetrics(titleFont);
        }

        int titleWidth = titleFontMetrics.stringWidth(title);
        int width = getWidth();
        int height = getHeight();
        double yValue = height*0.18;
        int xPosition = width / 2 - titleWidth / 2;
        int yPosition = (int) yValue;

        g.setFont(titleFont);
        g.drawString(title, xPosition, yPosition);
    }

    /**
     * Displays an error message on the screen.
     *
     * @param message The error message to display.
     */
    public void displayErrorMessage(String message) {
        int width = getWidth();
        int messageHeight = 30;
        errorMessageLabel.setBounds(0, messageHeight / 2, width, messageHeight);
        errorMessageLabel.setText(message);
        errorMessageLabel.setHorizontalAlignment(JLabel.CENTER);
        errorMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        errorMessageLabel.setBackground(Color.WHITE);
        errorMessageLabel.setOpaque(true);
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setVisible(true);
        this.add(errorMessageLabel);
        revalidate();
        repaint();

        if (errorMessageTimer == null) {
            errorMessageTimer = new Timer(5000, e -> {
                errorMessageLabel.setVisible(false);
                this.remove(errorMessageLabel);
                revalidate();
                repaint();
            });
            errorMessageTimer.setRepeats(false);
            errorMessageTimer.start();
        } else {
            errorMessageTimer.restart();
        }
    }

    /**
     * Plays the music.
     */
    public void playMusic() {
        GameSound gsound = new GameSound(null);
        gsound.play();
    }

    /**
     * Sets focus listeners for text fields.
     *
     * @param textField The text field.
     * @param placeholder The placeholder text.
     */
    public void setFocusListeners(JTextField textField, String placeholder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                }
            }
        });
    }

    /**
     * Loads a font.
     *
     * @param link The path to the font file.
     * @param size The font size.
     * @return The loaded font.
     */
    public Font loadFont(String link, float size) {
        Font font = null;
        try {
            File fontStyle = new File(link);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }

    /**
     * Inner class to handle cursor change.
     */
    private class CursorMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            setCustomCursor();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setDefaultCursor();
        }
    }

    /**
     * Inner class to handle button mouse events.
     */
    class ButtonMouseListener extends MouseAdapter {
        private JButton button;

        ButtonMouseListener(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setForeground(Color.YELLOW);
            setDefaultCursor();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setForeground(Color.BLACK);
            setCustomCursor();
        }

    }
}
