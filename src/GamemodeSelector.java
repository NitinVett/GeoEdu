import javax.swing.*;
import java.awt.*;
public class GamemodeSelector extends Screen{
    JButton global;
    JButton continental;
    JButton microNations;
    JButton Esc;

    public GamemodeSelector(JFrame frame) {
        super(frame);
        global = new JButton();
        continental = new JButton();
        microNations = new JButton();
        Esc = new JButton();

        global.addActionListener(e -> global_button());
        global.setText("Global");

        continental.addActionListener(e -> continental_button());
        continental.setText("Continental");

        microNations.addActionListener(e -> microNation_Button());
        microNations.setText("Micro-nations");

        Esc.addActionListener(e -> Esc_button());
        Esc.setText("Esc");

        this.add(global);
        this.add(continental);
        this.add(microNations);
        this.add(Esc);
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 3 - width / 5;
        int mainButtonY = height / 3;
        int mainButtonYIncrement = height / 10;
        global.setBounds(mainButtonX, mainButtonY, width / 5, height / 12);
        continental.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        microNations.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        Esc.setBounds(mainButtonX - width / 8, (mainButtonY + height / 50) - 370, (width / 10) - 30, height / 12);
        //logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
        revalidate();

    }

    public void global_button() {

        swapScreens(new LoginScreen(frame));
    }

    public void continental_button() {
        swapScreens(new RegisterScreen(frame));
    }

    public void Esc_button() {
        swapScreens(new GameMainMenu(frame));
    }

    public void microNation_Button() {
        TutorialScreen tutorialScreen = new TutorialScreen(frame, this);
        frame.addKeyListener(tutorialScreen);
        swapScreens(tutorialScreen);
    }

    public void logOutButton() {
        frame.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("xx");
        super.paintComponent(g); // Paints the background
//        Graphics2D g2D = (Graphics2D) g;
//        drawTitle(g2D);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
