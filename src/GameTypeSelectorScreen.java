import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameTypeSelectorScreen extends Screen{
    JButton marathon;
    JButton timed;
    JButton exploration;
    JButton esc;
    String mode;
    //JButton highScore;

    public GameTypeSelectorScreen(FullScreenUI frame, Screen previous, Player user, String mode, String continent) {
        super(frame,previous,user);
        this.mode=mode;
        marathon = new JButton();
        timed = new JButton();
        exploration = new JButton();
        esc = new JButton();

        marathon.addActionListener(e -> marathonButton());
        marathon.setText("Marathon Mode");
        marathon.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        timed.addActionListener(e -> timedButton());
        timed.setText("Timed Mode");
        timed.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        exploration.addActionListener(e -> explorationButton());
        exploration.setText("Exploration Mode");
        exploration.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        esc.addActionListener(e -> escButton());
        esc.setText("Esc");
        esc.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        this.add(marathon);
        this.add(timed);
        this.add(exploration);
        this.add(esc);
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width - width / 3;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;
        marathon.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        timed.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        exploration.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        esc.setBounds(1, 1, (width / 10) - 30, height / 12);
        //logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
        revalidate();

    }
    public void explorationButton() {
        try {
            GameTesting playExploration = new GameTesting(frame, user, "Global Mode",null,"Exploration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void marathonButton() {
        try {
            GameTesting playMarathon = new GameTesting(frame, user, "Global Mode",null,"Marathon");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void timedButton() {
        try {
            GameTesting playTimed = new GameTesting(frame, user, "Global Mode",null,"Timed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void logOutButton() {
        frame.dispose();
    }
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width - width / 3 + width / 16;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background

        g.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));
        g.drawString("Select a game type!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
