import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenu extends Screen {

    private JButton login;
    private JButton register;
    private JButton exit;

    private Font buttonFont;

    MainMenu(FullScreenUI frame) {
        super(frame, null);
        initializeComponents();
        //loadResources();
    }

    private void initializeComponents() {
        login = createButton("Login", e -> loginButton());
        register = createButton("Register", e -> registerButton());
        exit = createButton("Exit", e -> exitButton());

        add(login);
        add(register);
        add(exit);


    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.addActionListener(listener);
        button.setFont(loadFont("resources/Viner.ttf", 32));
        button.addMouseListener(new ButtonMouseListener(button));
        return button;
    }


    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 17;
        int mainButtonY = (int) (height / 2.5);
        int mainButtonYIncrement = height / 10;

        setButtonBounds(login, mainButtonX, mainButtonY, width, height, mainButtonYIncrement * 0);
        setButtonBounds(register, mainButtonX, mainButtonY, width, height, mainButtonYIncrement * 1);
        setButtonBounds(exit, mainButtonX, mainButtonY, width, height, mainButtonYIncrement * 2);
    }

    private void setButtonBounds(JButton button, int x, int y, int width, int height, int yOffset) {
        button.setBounds(x, y + yOffset, (int) (width / 8.5), height / 13);
    }

    public void loginButton() {
        swapScreens(new LoginScreen(frame, this));
    }

    public void registerButton() {
        swapScreens(new RegisterScreen(frame, this));
    }

    public void exitButton() {
        swapScreens(new ExitScreen(frame, this));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background
        Graphics2D g2D = (Graphics2D) g;
        drawTitle(g2D);
        updateButtonPositions();

    }
}
