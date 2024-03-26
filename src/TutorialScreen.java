import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TutorialScreen extends Screen implements KeyListener {

    private final Screen previousScreen;

    public TutorialScreen(JFrame frame, Screen previousScreen) {
        super(frame);
        this.previousScreen = previousScreen;
        setFocusable(true);
        requestFocusInWindow();
        repaint();
    }

    public void displayTutorial(Graphics2D g2D) {
        String[] tutorialText = {
                "Welcome to the Tutorial!",
                "",
                "1. Step One: Do this.",
                "2. Step Two: Do that.",
                "3. Step Three: Do something else.",
                "",
                "Press Enter or Escape to return to the previous screen."
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
