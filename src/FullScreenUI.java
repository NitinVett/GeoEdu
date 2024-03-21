import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FullScreenUI extends JFrame {
    BufferedImage backgroundImage = null;
    JLabel backgroundLabel;
    BufferedImage sunsetImage = null;
    public FullScreenUI() {



        // Set up the JFrame
        setUndecorated(false); // Remove window decorations (title bar, borders)
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set JFrame to fullscreen
        setResizable(false); // Disable resizing

        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Load the background image


        try {
            backgroundImage = ImageIO.read(new File("background.jpg")); // Replace "background.jpg" with your image file path
            sunsetImage = ImageIO.read(new File("sunset2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a JLabel to display the background image

        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);

        // Add the background label to the content pane
        getContentPane().add(backgroundLabel);
        getContentPane().setLayout(null);
        JButton newImage = new JButton();
        newImage.setBounds(300,300,300,300);
        getContentPane().add(newImage);


        newImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Yay");
                backgroundLabel.setIcon(new ImageIcon(sunsetImage));
                getContentPane().add(backgroundLabel);
                getContentPane().repaint();
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
        SwingUtilities.invokeLater(FullScreenUI::new);
    }
}
