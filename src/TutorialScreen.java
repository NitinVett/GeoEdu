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
    private BufferedImage scrollImage;
    private Image resizedScroll, plankIMG,backgroundImage;
    public TutorialScreen(FullScreenUI frame, Screen previousScreen, Player user) {
        super(frame,previousScreen, user);
        this.previousScreen = previousScreen;
        setFocusable(true);
        requestFocusInWindow();
        explorationButton = new JButton();
        explorationButton.addActionListener(e -> explorationButton());
        explorationButton.setText("Exploration Mode");
        explorationButton.setFont(loadFont("resources/Viner.ttf", 17));
        this.add(explorationButton);
        scrollLabel = gameRundown();
        //scrollImage = null;
        try {
            plankIMG = ImageIO.read(new File("resources/plank.png"));
            scrollImage = ImageIO.read(new File("scroll.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scrollLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text horizontally
        scrollLabel.setVerticalTextPosition(SwingConstants.CENTER);
        scrollLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scrollLabel.setForeground(Color.BLACK);
        this.add(scrollLabel);
        repaint();
    }
    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        explorationButton.setBounds(width/3+width/9, height-height/8, width / 9, height / 15);
        revalidate();
    }
    private void explorationButton() {
        GameTesting playExploration = new GameTesting(frame, user, "Global Mode",null,"Exploration");
        playExploration.newGame(false);
    }
    public void setComponents(Graphics g){
        int width = getWidth();
        int height = getHeight();
        Image scaledImage= plankIMG.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(explorationButton,scaledImage,width/90);
        resizedScroll = scrollImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        scrollLabel.setIcon(new ImageIcon(resizedScroll));
        scrollLabel.setBounds(0,height/30,width,height);
        scrollLabel.setFont(new Font("SansSerif", Font.PLAIN, 19));
        repaint();
        revalidate();
    }
    private JLabel gameRundown(){
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
                "If you ever want to go back screens try clicking the escape button on your keyboard.\n"+
                "\n"+
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
    @Override
    public void keyPressed(KeyEvent e) {
        // Listen for key press event
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
        swapScreens(prev);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents(g);
        updateButtonPositions();
    }
}
