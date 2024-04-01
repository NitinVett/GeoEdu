import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LoginScreen extends Screen {
    JTextField password, username;
    JButton login, esc;

    public LoginScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        esc = new JButton();
        password = new JTextField("Enter Password", 16);
        username = new JTextField("Enter Username", 16);
        login = new JButton("LOGIN");
        this.add(password);
        this.add(username);
        this.add(login);
        login.addActionListener(e -> loginButton());
        setFocusListeners(password, "Enter Password");
        setFocusListeners(username, "Enter Username");

        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(new File("resources/escape.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());
        this.add(esc);
        repaint();

    }

    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;

        username.setBounds(mainButtonX, mainButtonY, width / 5, height / 20);
        password.setBounds(mainButtonX, mainButtonY + height / 10, width / 5, height / 20);
        login.setBounds(mainButtonX, mainButtonY + (height / 10) * 2, width / 5, height / 20);
        username.setFont(new Font("SansSerif", Font.PLAIN, 24));
        password.setFont(new Font("SansSerif", Font.PLAIN, 24));
        esc.setBounds(width / 30, height / 22, 50, 50);

        revalidate();


    }


    public void loginButton() {
        System.out.println(username.getText());
        String pass = CsvHandler.getPassword(username.getText());
        System.out.println(pass);
        if (Objects.nonNull(pass) && pass.equals(password.getText())) {
            Player user = new Player(username.getText(), password.getText());
            swapScreens(new GameMainMenu(frame, this, user));

        } else {
            displayErrorMessage("Incorrect username/password");
        }
    }

    public void exitButton() {
        swapScreens(prev);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);


    }
}
