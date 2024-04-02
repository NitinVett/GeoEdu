import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TutorialScreen extends Screen implements KeyListener {

    private final Screen previousScreen;
    //private JPanel scrollPanel;
    private JLabel scrollLabel;
    private JButton explorationButton;

    public TutorialScreen(FullScreenUI frame, Screen previousScreen) {
        super(frame,previousScreen);
        this.previousScreen = previousScreen;
        setFocusable(true);
        requestFocusInWindow();


        explorationButton = new JButton();

        explorationButton.addActionListener(e -> explorationButton());
        explorationButton.setText("Exploration Mode");
        explorationButton.setFont(loadFont("resources/RubikScribble-Regular.ttf", 17));

        this.add(explorationButton);

        int height = getHeight();
        scrollLabel = getHints();
        BufferedImage scrollImage = null;
        try {
            scrollImage = ImageIO.read(new File("resources/scroll2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedScroll = scrollImage.getScaledInstance(1900, 1400, Image.SCALE_SMOOTH);
        scrollLabel.setIcon(new ImageIcon(resizedScroll));


        scrollLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text horizontally
        scrollLabel.setVerticalTextPosition(SwingConstants.CENTER);
        //scrollLabel.setVerticalAlignment(SwingConstants.TOP);
        scrollLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scrollLabel.setForeground(Color.BLACK);
        this.add(scrollLabel);

        repaint();
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        double xValue = width / 2 - ((height/15)*2);
        double yValue = height * 0.75;
        explorationButton.setBounds((int) xValue, (int) yValue, width / 9, height / 15);
        revalidate();

    }

    public void explorationButton() {
        //code here
    }

    public void setComponents(Graphics g){
        int width = getWidth();
        int height = getHeight();
        //int mainButtonY = height/5;

        scrollLabel.setBounds(width/5 - width/5,height/25 - height/7,1900,1400);

        scrollLabel.setFont(new Font("SansSerif", Font.PLAIN, 19));


        repaint();

    }

    private JLabel getHints(){
        JLabel hints = new JLabel();
        String textHint = "Welcome to Geocraft,\n" +
                "\n" +
                "1. Select a game mode (Global, Continental or Micro-Nations)\n" +
                "2. Select a game type (Timed, Marathon or Exploration)\n" +
                "\n" +
                "In timed mode, you have 3 minutes to score as many points as you can\n" +
                "You earn 10 points for a correct answer and lose 5 incorrect answer\n" +
                "\n" +
                "In marathon mode, you have 3 lives to score as many points as you can, 10 points per correct answer\n" +
                "You will lose a life for each wrong answer you get. The game is over when you are out of lives\n" +
                "\n" +
                "In both timed and marathon mode, 2 points will be deducted if you choose to view a flag hint\n" +
                "4 points will be deducted if you choose to view a text hint\n" +
                "\n" +
                "In exploration, you have no time limit, lives or high score. This mode is purely for you to learn and explore.\n" +
                "\n" +
                "Click the button below to start exploration mode\n" +
                "Good luck and have fun!";

        try {
            String[] lines = textHint.split("\n");
            StringBuilder content = new StringBuilder();

            for (String line : lines) {
                content.append(line).append("<br>");
            }
            hints.setText("<html>" + content.toString() + "</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hints;
    }
    /*public void displayTutorial(Graphics2D g2D) {
        String[] tutorialText = {
                "Welcome to Geocraft",
                "",
                "1. Select a game mode (Global, Continental or Micro-Nations)",
                "2. Select a game type (Timed, Marathon or Exploration)",
                "",
                "In timed mode, you have 3 minutes to score as many points as you can",
                "You earn 10 points for a correct answer and lose 5 incorrect answer",
                "",
                "In marathon mode, you have 3 lives to score as many points as you can, 10 points per correct answer",
                "You will lose a live for each wrong answer you get. The game is over when you are out of lives",
                "",
                "In both timed and marathon mode, 2 points will be deducted if you choose to view a flag hint",
                "4 points will be deducted if you choose to view a text hint",
                "",
                "Good luck and have fun!"
        };

        g2D.setFont(new Font("SansSerif", Font.PLAIN, 24));
        g2D.setColor(Color.BLACK);

        int lineHeight = g2D.getFontMetrics().getHeight();
        int startY = 100;

        for (int i = 0; i < tutorialText.length; i++) {
            g2D.drawString(tutorialText[i], 100, startY + i * lineHeight);
        }
    }*/

    @Override
    public void keyPressed(KeyEvent e) {
        // Listen for key press event
        System.out.println("testing if click");
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_ESCAPE) {
            // Return to the previous screen when Enter or Escape is pressed
            switchToPreviousScreen();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Unused
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
    }

    private void switchToPreviousScreen() {
        JFrame mainFrame = previousScreen.frame;
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(previousScreen);
        mainFrame.getContentPane().revalidate();
        mainFrame.getContentPane().repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents(g);
        //drawTitle((Graphics2D) g);
        updateButtonPositions();
        /*Graphics2D g2D = (Graphics2D) g;
        try {
            displayTutorial(g2D);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }
}
