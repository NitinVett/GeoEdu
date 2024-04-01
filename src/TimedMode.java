import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TimedMode extends GameplayScreen {
    private final int timeLeft;
    private Timer timer;
    GameTesting game;
    public TimedMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2,int timeLeft){
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);
        this.timeLeft = timeLeft;
        this.game = gameTesting;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, getHeight() / 40));
        String timerText = "Time left: " + game.getTime() + "s";
        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(timerText)) / 2;
        int y = getHeight() / 50;
        g.drawString(timerText, x, y);
        repaint();
    }
}