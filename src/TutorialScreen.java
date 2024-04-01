import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TutorialScreen extends Screen implements KeyListener {

    private final Screen previousScreen;
    private JLabel scroll;

    public TutorialScreen(FullScreenUI frame, Screen previousScreen) {
        super(frame,previousScreen);
        this.previousScreen = previousScreen;
        scroll = new JLabel();
        setFocusable(true);
        requestFocusInWindow();
        repaint();
    }

    public void displayTutorial(Graphics2D g2D) {
        String[] tutorialText = {
                "Welcome to Geocraft,",
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
                "In exploration, you have no time limit, lives or high score. This mode is purely for you to learn and explore.",
                "",
                "Click the button below to start exploration mode",
                "Good luck and have fun!"
        };



        g2D.setFont(new Font("SansSerif", Font.PLAIN, 24));
        g2D.setColor(Color.BLACK);

        int lineHeight = g2D.getFontMetrics().getHeight();
        int startY = 100;

        for (int i = 0; i < tutorialText.length; i++) {
            g2D.drawString(tutorialText[i], 100, startY + i * lineHeight);
        }
    }

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
        Graphics2D g2D = (Graphics2D) g;
        displayTutorial(g2D);
    }
}
