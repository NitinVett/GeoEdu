import javax.swing.*;
import java.awt.*;

public class GameTypeSelector extends Screen{
    JButton marathon;
    JButton timed;
    JButton exploration;
    JButton esc;
    //JButton highScore;

    public GameTypeSelector(FullScreenUI frame, Screen previous) {
        super(frame,previous);
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
        esc.setBounds(mainButtonX/mainButtonX, mainButtonY/mainButtonY, (width / 10) - 30, height / 12);
        //logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
        revalidate();

    }

    public void marathonButton() {
        swapScreens(new LoginScreen(frame,this));
    }

    public void timedButton() {
        swapScreens(new RegisterScreen(frame,this));
    }

    public void explorationButton() {
        TutorialScreen tutorialScreen = new TutorialScreen(frame, this);
        frame.addKeyListener(tutorialScreen);
        swapScreens(tutorialScreen);
    }

    public void escButton() {
        swapScreens(new GamemodeSelectorScreen(frame,this));
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
