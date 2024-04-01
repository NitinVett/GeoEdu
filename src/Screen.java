
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
    FullScreenUI frame;
    JButton settings;
    private Image backgroundImage;
    private JLabel errorMessageLabel;
    Screen prev;
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

        ImageIcon imgIcon = new ImageIcon("resources/hamburger.png");
        Image image = imgIcon.getImage();
        Image newImage = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon settingsIcon = new ImageIcon(newImage);
        settings.addActionListener(e -> settingsButton());
        settings.setIcon(settingsIcon);
        //settings.setText("SETTINGS");

        this.setVisible(true);
        this.setLayout(null);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        frame.setVisible(true);
        this.requestFocusInWindow(true);
        setUpKeyBindings();
        frame.revalidate();
        frame.repaint();
        this.addMouseListener(new CursorMouseListener());
    }

    private void loadCustomCursors() {
        try {
            BufferedImage cursorImg = ImageIO.read(getClass().getResource("click.png"));
            Image resizedImage1 = cursorImg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(resizedImage1, new Point(0, 0), "DefaultCursor");

            BufferedImage cursorImg2 = ImageIO.read(getClass().getResource("cursor.png"));
            Image resizedImage2 = cursorImg2.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            customCursor = Toolkit.getDefaultToolkit().createCustomCursor(resizedImage2, new Point(0, 0), "CustomCursor");
        } catch (IOException e) {
            e.printStackTrace();
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
            System.err.println("Resource not found: meow " + "wallpaper5.gif");
        }
    }

    public void setUpKeyBindings() {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        this.getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.nonNull(prev)) {
                    swapScreens(prev);
                }
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
        // remove this from
        drawTitle((Graphics2D) g);
        int width = getWidth();
        int height = getHeight();
        settings.setBounds(width - width / 15, height / 22, 50, 50);
//        settings.setFont(new Font("SansSerif", Font.PLAIN, 24));
        settings.setBorder(BorderFactory.createEmptyBorder());
        settings.setContentAreaFilled(false); // Make button transparent
        settings.setVisible(true);

    }

    //Call this function to disable settings button where u want
    public void disableSettingButton() {
        settings.setVisible(false);
    }

    public void drawTitle(Graphics2D g) {

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.setFont(loadFont("resources/Viner.ttf", 96));
        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth("GEOCRAFT");
        int width = getWidth();
        int height = getHeight();

        // Positioning country panel
        double xValue = (width - 450) / 2; // Adjust this value as needed
        double yValue = height * 0.20;


        int xPosition = getWidth() / 2 - titleWidth / 2;
        int yPosition = (int) yValue;
        g.drawString("GEOCRAFT", xPosition, yPosition);

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

        int delay = 5000;
        Timer timer = new Timer(delay, e -> {
            errorMessageLabel.setVisible(false); // Hide the message
            revalidate();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
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


