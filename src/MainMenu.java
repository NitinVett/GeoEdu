import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu extends Screen {

    //Image image;
    JButton login;
    JButton register;
    JButton exit;

    MainMenu(FullScreenUI frame) {

        super(frame,null);

        BufferedImage backgroundImage = null;

        ImageIcon icon = new ImageIcon("src/shade.png");
        login = new JButton("Login",icon);
        login.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text horizontally
        login.setVerticalTextPosition(SwingConstants.CENTER);
        login.setFont(new Font("Arial", Font.BOLD, 14));
        login.setForeground(Color.WHITE);
        login.setContentAreaFilled(false); // Make button transparent
        register = new JButton();
        exit = new JButton();

        login.addActionListener(e -> loginButton());
        //login.setIcon(icon);
        //login.setText("Login");

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
        swapScreens(new LoginScreen(frame,this));
    }

    public void registerButton() {
        swapScreens(new RegisterScreen(frame,this));
    }

    public void exitButton() {
        swapScreens(new ExitScreen(frame,this));

    }



    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); // Paints the background
        Graphics2D g2D = (Graphics2D) g;
        drawTitle(g2D);

        revalidate();
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues

    }
}
