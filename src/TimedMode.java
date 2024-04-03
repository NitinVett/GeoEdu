import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

/**
 * TimedMode class represents the screen for gameplay in timed mode.
 * It extends GameplayScreen.
 */
public class TimedMode extends GameplayScreen {

    private final int timeLeft;
    private GameTesting game;

    /**
     * Constructor for TimedMode.
     *
     * @param gameTesting The GameTesting instance.
     * @param previous The previous screen.
     * @param user The player object.
     * @param correctCountry The correct country object.
     * @param incorrect1 The first incorrect country object.
     * @param incorrect2 The second incorrect country object.
     * @param timeLeft The time left for the timed mode.
     */
    public TimedMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry,
                     Country incorrect1, Country incorrect2, int timeLeft) {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);
        this.timeLeft = timeLeft;
        this.game = gameTesting;
        repaint();
    }

    /**
     * Override method to paint the component.
     *
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, getHeight() / 40));
        String timerText = "Time left: " + game.getTime() + "s";
        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(timerText)) / 20;
        int y = getHeight() / 15;
        g.drawString(timerText, x, y);
        repaint();
    }
}
