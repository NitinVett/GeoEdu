import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RegisterScreen extends Screen {
    JTextField password, username, password_2;
    JButton register, esc;

    public RegisterScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        esc = new JButton();
        password = new JTextField("Enter Password", 16);
        username = new JTextField("Enter Username", 16);
        password_2 = new JTextField("Re-enter Password", 16);
        register = new JButton("REGISTER");
        this.add(password);
        this.add(username);
        this.add(password_2);
        this.add(register);
        register.addActionListener(e -> registerButton());
        setFocusListeners(password, "Enter Password");
        setFocusListeners(username, "Enter Username");
        setFocusListeners(password_2, "Re-enter Password");

        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(new File("src/escape.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());
        this.add(esc);

        repaint();


    }

    public void exitButton() {
        swapScreens(prev);
    }

    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;

        username.setBounds(mainButtonX, mainButtonY, width / 5, height / 20);
        password.setBounds(mainButtonX, mainButtonY + height / 10, width / 5, height / 20);
        password_2.setBounds(mainButtonX, mainButtonY + (height / 10) * 2, width / 5, height / 20);
        register.setBounds(mainButtonX, mainButtonY + (height / 10) * 3, width / 5, height / 20);
        password_2.setFont(new Font("SansSerif", Font.PLAIN, 24));
        username.setFont(new Font("SansSerif", Font.PLAIN, 24));
        password.setFont(new Font("SansSerif", Font.PLAIN, 24));
        esc.setBounds(width / 30, height/22, 50, 50);

        revalidate();


    }

    public void registerButton() {
        if (password.getText().equals(password_2.getText())) {
            String error = CsvHandler.addUser(username.getText(), password.getText());
            if (error.equals("APPROVED")) {
                swapScreens(new MainMenu(frame));
            } else {
                displayErrorMessage(error);
            }
        } else {
            displayErrorMessage("You passwords do not match");
        }


    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);


    }
}
