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

/**
 * Playground class, here I test out stuff
 */
public class FullScreenUI extends JFrame {

    private JLabel[] countryLabels;
    private int currentBackgroundIndex = 0; // Index of the current background image
    private int currentCountryIndex = 0;
    private JLabel backgroundLabel;
    private JLabel hintLabel;
    private JPanel countryPanel;
    private MapPictureArray picArray;
    private BufferedImage[] backgroundImages;

    public FullScreenUI() throws IOException {

        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setUndecorated(false); // Remove window decorations
        setResizable(false); // Disable resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close

        // Set full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setPreferredSize(screenSize);

        // Set up content pane
        getContentPane().setLayout(new BorderLayout());
        BufferedImage backgroundImage = ImageIO.read(new File("Country Data/Maps/bk.jpg"));
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        getContentPane().add(backgroundLabel);

        // Conducted test to ensure country objects can present their own Jlabels succesfully.
        Country pk = new Country("Pakistan");
        Country uk = new Country("UnitedKingdom");
        Country cn = new Country("China");
        Country ca = new Country("Canada");

        Country[] countries = new Country[]{pk, uk, cn, ca};
        countryLabels = new JLabel[4];
        countryLabels[0] = pk.getCountryMap();
        countryLabels[1] = uk.getCountryMap();
        countryLabels[2] = cn.getCountryMap();
        countryLabels[3] = ca.getCountryMap();


//        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);

        // Creating Jlabel for country map
//        JPanel countryPanel = new JPanel();

        countryPanel = new JPanel(new BorderLayout());
        countryPanel.setOpaque(false);
        countryPanel.setPreferredSize(new Dimension(500, 500)); // Adjust height as needed
        countryPanel.add(backgroundLabel, BorderLayout.CENTER);
        getContentPane().add(countryPanel, BorderLayout.NORTH);


        // Create hint label

        hintLabel = new JLabel("test, first out loop init");
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        hintLabel.setForeground(Color.BLACK);

        // Create hint panel
        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hintPanel.setOpaque(false);
        hintPanel.setPreferredSize(new Dimension(500, 100));
        hintPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hintPanel.add(hintLabel);
        getContentPane().add(hintPanel, BorderLayout.CENTER);


        // Add the panels to the background label
//        backgroundLabel.add(countryPanel);
//        backgroundLabel.add(hintPanel);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setPreferredSize(new Dimension(500, 100));
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);


        //getContentPane().add(testbutton);
        JButton testButton = new JButton("Next Country");
//        JPanel finalCountryPanel = countryPanel;
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentBackgroundIndex = (currentBackgroundIndex + 1) % countryLabels.length;
                currentCountryIndex = (currentCountryIndex + 1) % countries.length;

                countryPanel.removeAll();
                countryPanel.add(countryLabels[currentCountryIndex]);
                countryPanel.revalidate();
                countryPanel.repaint();

                String hints = countries[currentCountryIndex].getHints().getText();
                hintLabel.setText(hints);
            }
        });
        buttonsPanel.add(testButton);


        GameSound test = new GameSound("test.wav");
        test.play();


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

    public static void main(String[] args) throws IOException {
        //new FullScreenUI();
        JFrame main = new JFrame();
        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
        main.setUndecorated(true);
        main.setContentPane(new GamemodeSelector(main));
        main.setVisible(true);
        main.requestFocus();

    }
}
