import javax.swing.*;
import java.awt.*;

public class GamemodeSelectorScreen extends Screen{
    JButton global;
    JButton continental;
    JButton microNations;
    JButton esc;
    //JButton highScore;

    public GamemodeSelectorScreen(FullScreenUI frame, Screen previous) {
        super(frame,previous);
        global = new JButton();
        continental = new JButton();
        microNations = new JButton();
        esc = new JButton();

        //highScore = new JButton();
        /*
        Font font = null;
        try {
            File fontStyle = new File("resources/RubikScribble-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(44f);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        global.addActionListener(e -> globalButton());
        global.setText("Global");
        global.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        continental.addActionListener(e -> continentalButton());
        continental.setText("Continental");
        continental.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        microNations.addActionListener(e -> microNationButton());
        microNations.setText("Micro-nations");
        microNations.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        esc.addActionListener(e -> escButton());
        esc.setText("Esc");
        esc.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));


        this.add(global);
        this.add(continental);
        this.add(microNations);
        this.add(esc);
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 3 - width / 5;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;
        global.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        continental.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        microNations.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);

        //redo these bounds
        esc.setBounds(mainButtonX/mainButtonX, mainButtonY/mainButtonY, (width / 10) - 30, height / 12);
        //logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
        revalidate();

    }

    public void globalButton() {
        swapScreens(new LoginScreen(frame,this));
    }

    public void continentalButton() {
        swapScreens(new RegisterScreen(frame,this));
    }

    public void microNationButton() {
        TutorialScreen tutorialScreen = new TutorialScreen(frame, this,user);
        frame.addKeyListener(tutorialScreen);
        swapScreens(tutorialScreen);
    }
    public void escButton() {
        //swapScreens(new GameMainMenu(frame,this));
    }

    public void logOutButton() {
        frame.dispose();
    }


    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 3 - width / 7;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background

        g.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));
        g.drawString("Select a game mode!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
