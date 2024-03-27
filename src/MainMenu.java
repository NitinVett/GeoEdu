import javax.swing.*;
import java.awt.*;

public class MainMenu extends Screen {

    //Image image;
    JButton login;
    JButton register;
    JButton exit;

    MainMenu(JFrame frame) {
        super(frame);

        login = new JButton();
        register = new JButton();
        exit = new JButton();

        login.addActionListener(e -> loginButton());
        login.setText("LOGIN");

        register.addActionListener(e -> registerButton());
        register.setText("REGISTER");

        exit.addActionListener(e -> exitButton());
        exit.setText("EXIT");

        this.add(login);
        this.add(register);
        this.add(exit);
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;
        int mainButtonYIncrement = height / 10;
        login.setBounds(mainButtonX, mainButtonY, width / 5, height / 12);
        register.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        exit.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        revalidate();
    }


    public void loginButton() {
        swapScreens(new LoginScreen(frame));
    }

    public void registerButton() {
        swapScreens(new RegisterScreen(frame));
    }

    public void exitButton() {
        frame.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); // Paints the background
        Graphics2D g2D = (Graphics2D) g;
        drawTitle(g2D);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
