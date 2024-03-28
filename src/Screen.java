import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;

public class Screen extends JPanel {
    FullScreenUI frame;
    JButton settings;
    private Image backgroundImage;
    private JLabel errorMessageLabel;

    public Screen(FullScreenUI frame,JPanel previous) {
        this.frame = frame;
        loadBackgroundImage();
        settings = new JButton();
        errorMessageLabel = new JLabel();

        this.add(settings);
        //settings image here

        //settings.setIcon();

        //setting button location here

        settings.addActionListener(e -> settingsButton());
        settings.setText("SETTINGS");
        this.setVisible(true);
        this.setLayout(null);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

    }

    private void loadBackgroundImage() {
        URL imageURL = getClass().getClassLoader().getResource("video.gif");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            backgroundImage = icon.getImage();

        } else {
            System.err.println("Resource not found: " + "src/video.gif");
        }
    }


    public void swapScreens(JPanel panel) {
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }

    //add functionality for setting button
    public void settingsButton() {
       swapScreens(frame.getSettings(this));

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int x = (getWidth() - backgroundImage.getWidth(null)) / 2;
            int y = (getHeight() - backgroundImage.getHeight(null)) / 2;
            g.drawImage(backgroundImage, x, y, this);
        }
        drawTitle((Graphics2D) g);
        int width = getWidth();
        int height = getHeight();
        settings.setBounds(width-width/8, height/22, width / 10, height / 12);
        settings.setFont(new Font("SansSerif", Font.PLAIN, 24));

    }

    public void drawTitle(Graphics2D g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g.setFont(new Font("SansSerif", Font.BOLD, 48));

        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth("GEOCRAFT");
        int xPosition = getWidth() / 2 - titleWidth / 2;
        int yPosition = getHeight() / 10;

        g.drawString("GEOCRAFT", xPosition, yPosition);
    }

    public void displayErrorMessage(String message) {
        int width = getWidth();
        int messageHeight = 30;

        errorMessageLabel.setBounds(0, messageHeight/2, width, messageHeight);
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
            System.out.println("here");
            errorMessageLabel.setVisible(false); // Hide the message
            revalidate();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void setGameSettings(){

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


}
