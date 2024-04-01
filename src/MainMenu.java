import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends Screen {

    JButton login;
    JButton register;
    JButton exit;

    MainMenu(FullScreenUI frame) {
        super(frame, null);
        initializeButtons();
//        loadCustomCursors();
    }


    private void initializeButtons() {
        //playMusic();

//        ImageIcon icon = new ImageIcon("src/shade.png");
        login = new JButton("Login");
        login.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text horizontally
        login.setVerticalTextPosition(SwingConstants.CENTER);
        login.setFont(loadFont("resources/Viner.ttf", 26));
        //login.setForeground(Color.WHITE);
        login.setContentAreaFilled(false);
        login.setBorder(null);
        login.addActionListener(e -> loginButton());
        login.addMouseListener(new ButtonMouseListener(login));

        // Make button transparent
        register = new JButton();
        register.addActionListener(e -> registerButton());
        register.setText("Register");
        register.setFont(loadFont("resources/Viner.ttf", 26));
        register.setContentAreaFilled(false); // Make button transparent
        register.setBorder(null);
        register.addMouseListener(new ButtonMouseListener(register));

        // Exit
        exit = new JButton();
        exit.addActionListener(e -> exitButton());
        exit.setText("Exit");
        exit.setFont(loadFont("resources/Viner.ttf", 26));
        exit.setContentAreaFilled(false); // Make button transparent
        exit.setBorder(null);
        exit.addMouseListener(new ButtonMouseListener(exit));

        this.add(login);
        this.add(register);
        this.add(exit);
    }


    private void updateButtonPositions() {

        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 17;
        int mainButtonY = height / 3;
        int mainButtonYIncrement = height / 10;

        login.setBounds(mainButtonX, mainButtonY, (int) (width / 8.5), height / 13);
        register.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, (int) (width / 8.5), height / 13);
        exit.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, (int) (width / 8.5), height / 13);

        revalidate();

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

        revalidate();
        updateButtonPositions();
        //disableSettingButton();
    }

}
