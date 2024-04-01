import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TimedMode extends GameplayScreen {
    private int timeLeft = 60;
    private Timer timer;

    public TimedMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2) throws IOException {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);


        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                repaint();
            } else {
                ((Timer)e.getSource()).stop(); // Stop the timer
                System.out.println("TIME UP");
                swapScreens(new StatScreen(frame,this));

            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.BOLD, getHeight() / 40));

        String timerText = "Time left: " + timeLeft + "s";

        FontMetrics metrics = g.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(timerText)) / 2;

        int y = getHeight() / 50;


        g.drawString(timerText, x, y);
    }
}