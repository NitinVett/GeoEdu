import javax.swing.*;
import java.awt.*;

public class ContinentalModeSelectorScreen extends Screen{
    JButton americas;
    JButton asia;
    JButton europe;
    JButton esc;
    String mode;
    //JButton highScore;

    public ContinentalModeSelectorScreen(FullScreenUI frame, Screen previous, Player user, String mode) {
        super(frame,previous,user);
        this.mode=mode;
        americas = new JButton();
        asia = new JButton();
        europe = new JButton();
        esc = new JButton();

        americas.addActionListener(e -> americasButton());
        americas.setText("Americas");
        americas.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        asia.addActionListener(e -> asiaButton());
        asia.setText("Asia");
        asia.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        europe.addActionListener(e -> europeButton());
        europe.setText("Europe");
        europe.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));

        esc.addActionListener(e -> escButton());
        esc.setText("Esc");
        esc.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));


        this.add(americas);
        this.add(asia);
        this.add(europe);
        this.add(esc);
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;
        americas.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        asia.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        europe.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        esc.setBounds(1, 1, (width / 10) - 30, height / 12);
        //logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
        revalidate();

    }

    public void americasButton() {
        swapScreens(new GameTypeSelectorScreen(frame,this,user,mode,"Americas"));
    }

    public void asiaButton() {
        swapScreens(new GameTypeSelectorScreen(frame,this,user,mode,"Asia"));
    }
    public void europeButton() {
        swapScreens(new GameTypeSelectorScreen(frame,this,user,mode,"Europe"));
    }

    public void escButton() {
        swapScreens(prev);
    }
    public void logOutButton() {
        frame.dispose();
    }


    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 27;
        int mainButtonY = height / 3 + height / 8;

        super.paintComponent(g); // Paints the background

        g.setFont(loadFont("resources/RubikScribble-Regular.ttf", 20));
        g.drawString("Select a continent!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
