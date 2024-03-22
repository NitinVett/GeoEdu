import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FullScreenUI extends JFrame {
    BufferedImage backgroundImage = null;
    JLabel backgroundLabel;

    private MapPictureArray picArray;

    private BufferedImage[] backgroundImages; // Array to store background images
    private int currentBackgroundIndex = 0; // Index of the current background image

    public FullScreenUI() {

        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        picArray = new MapPictureArray("Global");

        backgroundImages = picArray.getBackgroundImages();

        // Create a JLabel to display the background image

        backgroundLabel = new JLabel(new ImageIcon(backgroundImages[currentBackgroundIndex]));
        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);

        // Add the background label to the content pane
        getContentPane().add(backgroundLabel);
        getContentPane().setLayout(null);
        MyButton testbutton = new MyButton();
        getContentPane().add(testbutton);


        testbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentBackgroundIndex = (currentBackgroundIndex + 1) % backgroundImages.length;
                backgroundLabel.setIcon(new ImageIcon(backgroundImages[currentBackgroundIndex]));
            }

        });


        // Add any UI components or game elements here
        // For now, let's just display a message in the center of the screen

        GameSound test = new GameSound("test.wav");
        test.play();


        JLabel messageLabel = new JLabel("Fullscreen UI");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(Color.WHITE);
        Dimension labelSize = messageLabel.getPreferredSize();
        messageLabel.setBounds((screenSize.width - labelSize.width) / 2, (screenSize.height - labelSize.height) / 2, labelSize.width, labelSize.height);
        backgroundLabel.add(messageLabel);

        // Set up key listener to exit fullscreen when ESC key is pressed
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose(); // Close the fullscreen window
                }
            }
        });

        // Set JFrame to be visible
        setVisible(true);

        // Request focus so that key events are captured
        requestFocus();
    }

    public static void main(String[] args) {
        new FullScreenUI();
    }
}
