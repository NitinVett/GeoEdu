
00
public class FullScreenUI extends JFrame {
    BufferedImage backgroundImage = null;
    JLabel backgroundLabel;
    JLabel newback;
    BufferedImage sunsetImage = null;
    BufferedImage gifd = null;

    private BufferedImage[] backgroundImages; // Array to store background images
    private int currentBackgroundIndex = 0; // Index of the current background image

    public FullScreenUI() {

        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Load the background image


        try {
            backgroundImage = ImageIO.read(new File("background.jpg")); // Replace "background.jpg" with your image file path
            sunsetImage = ImageIO.read(new File("sunset2.jpg"));
            gifd = ImageIO.read(new File("l.gif"));

            backgroundImages = new BufferedImage[]{backgroundImage, sunsetImage, gifd};
        } catch (IOException e) {
            e.printStackTrace();
        }

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
//                getContentPane().remove(backgroundLabel); // Remove the previous background
//                newback = new JLabel(new ImageIcon(sunsetImage));
//                newback.setBounds(0, 0, screenSize.width, screenSize.height); // Set bounds to cover the entire screen
//                getContentPane().add(newback);
//                getContentPane().repaint();
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
        SwingUtilities.invokeLater(FullScreenUI::new);
    }
}
