
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
    public Screen(FullScreenUI frame, Screen previous) {
        this.frame = frame;
        prev = previous;
        setUp();
        loadCustomCursors();
    }
    public Screen(FullScreenUI frame, Screen previous, Player user) {
        this.frame = frame;
        this.prev = previous;
        this.user = user;
        setUp();
        loadCustomCursors();
    }
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
    private void loadCustomCursors() {
        defaultCursor = createCustomCursor("click.png");
        customCursor = createCustomCursor("cursor.png");
    }
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
    private void setCustomCursor() {
        frame.setCursor(customCursor);
    }
    private void setDefaultCursor() {
        frame.setCursor(defaultCursor);
    }
    private void addMouseListeners(JButton button) {
        button.addMouseListener(new ButtonMouseListener(button));
    }
    private void loadBackgroundImage() {
        // only works if gif is in src folder, aka class path
        //no: 3, 4 is too busy cannon, 5 cutting into geocraft,
        URL imageURL = getClass().getClassLoader().getResource("wallpaper1.gif");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            backgroundImage = icon.getImage();
        } else {
            System.err.println("Resource not found: meow " + "wallpaper1.gif");
        }
    }
    public void setUpKeyBindings() {
        setUpKeyBinding(KeyEvent.VK_ESCAPE, "ESCAPE", () -> {
            if (Objects.nonNull(prev)) {
                swapScreens(prev);
            }
        });
    }
    private void setUpKeyBinding(int keyCode, String actionName, Runnable action) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), actionName);
        getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }
    public void swapScreens(JPanel panel) {
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }
    //add functionality for setting button
    public void settingsButton() {
        swapScreens(frame.getSettings(this, user));
    }
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
            settings.setContentAreaFilled(false); // Make button transparent
            settings.setVisible(true);
            settingsButtonBoundsSet = true;
        }
    }

    //Call this function to disable settings button where u want
    public void disableSettingButton() {
        settings.setVisible(false);
    }

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

    public void drawTitle(Graphics2D g) {
        // Draw background image if necessary
        // g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Set font and font metrics if not already set
        if (titleFont == null) {
            titleFont = loadFont("resources/Viner.ttf", 112);
            titleFontMetrics = g.getFontMetrics(titleFont);
        }
        // Calculate title width
        int titleWidth = titleFontMetrics.stringWidth("GEOCRAFT");
        // Positioning
        int width = getWidth();
        int height = getHeight();
        double yValue = height*0.18;
        int xPosition = width / 2 - titleWidth / 2;
        int yPosition = (int) yValue;
        // Draw title
        g.setFont(titleFont);
        g.drawString("GEOCRAFT", xPosition, yPosition);
    }
    public void drawTitle(Graphics2D g, String title) {
        // Draw background image if necessary
        // g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        // Set font and font metrics if not already set
        if (titleFont == null) {
            titleFont = loadFont("resources/Viner.ttf", 112);
            titleFontMetrics = g.getFontMetrics(titleFont);
        }
        // Calculate title width
        int titleWidth = titleFontMetrics.stringWidth(title);
        // Positioning
        int width = getWidth();
        int height = getHeight();
        double yValue = height*0.18;
        int xPosition = width / 2 - titleWidth / 2;
        int yPosition = (int) yValue;
        // Draw title
        g.setFont(titleFont);
        g.drawString(title, xPosition, yPosition);
    }
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
                errorMessageLabel.setVisible(false); // Hide the message
                this.remove(errorMessageLabel); // Remove label from component hierarchy
                revalidate();
                repaint();
            });
            errorMessageTimer.setRepeats(false);
            errorMessageTimer.start(); // Start the timer
        } else {
            // Restart the timer
            errorMessageTimer.restart();
        }
    }
    public void playMusic() {
        GameSound gsound = new GameSound(null);
        gsound.play();
    }
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
    // Inner class to handle cursor change
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
    // Inner class to handle button mouse events
    class ButtonMouseListener extends MouseAdapter {
        private JButton button;
        ButtonMouseListener(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setForeground(Color.YELLOW); // Change text color

            setDefaultCursor();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setForeground(Color.BLACK); // Restore text color
            setCustomCursor(); // Change cursor
        }

    }
}


