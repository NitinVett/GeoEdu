import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;

public class Screen extends JPanel {
    JFrame frame;
    private Image backgroundImage;


    public Screen(JFrame frame) {

        this.frame = frame;
        //set the background for our screens here
        //this.setBackground();
        JButton settings = new JButton();
        loadBackgroundImage();
        //settings image here
        //settings.setIcon();

        //setting button location here
        //settings.setBounds();


        this.setVisible(true);
        settings.addActionListener(e -> settingsButton());
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
