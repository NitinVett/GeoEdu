import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class LoginScreen extends Screen {
    JTextField username;
    JPasswordField password;
    JButton login, esc;

    public LoginScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        esc = new JButton();

        // Username field setup
// Username field setup
        username = new JTextField("Enter Username", 16);
        username.setOpaque(false);
        username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        username.setForeground(new Color(255, 255, 255));
        username.setCaretColor(Color.WHITE);
        username.setFont(loadFont("resources/pirate.ttf", 24));
        // Add a focus listener to handle placeholder text behavior
        username.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the username field gains focus
                if (username.getText().equals("Enter Username")) {
                    username.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the username field is left empty
                if (username.getText().isEmpty()) {
                    username.setText("Enter Username");
                }
            }
        });

        // Password field setup
        password = new JPasswordField(16);

        password.setText("Enter Password");
        password.setEchoChar((char) 0); // Set initial echo char to 0 (no echo)
        password.setOpaque(false);
        password.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK)); // Remove border
        password.setForeground(new Color(255, 255, 255));
        password.setCaretColor(Color.WHITE);
        password.setFont(loadFont("resources/pirate.ttf", 24));

        // Add a focus listener to handle placeholder text behavior for the password field
        password.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the password field gains focus
                if (Arrays.equals(password.getPassword(), "Enter Password".toCharArray())) {
                    password.setText("");
                    password.setEchoChar('*'); // Set echo char to '*' for password entry
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the password field is left empty
                if (password.getPassword().length == 0) {
                    password.setText("Enter Password");
                    password.setEchoChar((char) 0); // Set echo char to 0 (no echo)
                }
            }
        });

        // Login button setup
        login = createCustomButton("Login");
        login.addActionListener(e -> loginButton());


        // Add components to the panel
        this.add(password);
        this.add(username);
        this.add(login);

        // Esc button setup
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
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setFont(loadFont("resources/Viner.ttf", 28));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        //setButtonBackground(button);
        return button;
    }

    private void setButtonBackground(JButton button,int width,int height) {
        try {
            BufferedImage image = ImageIO.read(new File("resources/plank.png"));
            Image scaledImage = image.getScaledInstance(width/10, height/20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;

        username.setBounds(mainButtonX, mainButtonY, width / 5, height / 20);
        password.setBounds(mainButtonX, mainButtonY + height / 10, width / 5, height / 20);
        setButtonBackground(login,width,height);


        login.setBounds(mainButtonX+(width/20), mainButtonY + (height / 10) * 2, width / 10, height / 20);


        username.setFont(loadFont("resources/Viner.ttf", 24));
        password.setFont(loadFont("resources/Viner.ttf", 24));
        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);
        revalidate();
    }

    public void loginButton() {
        String enteredUsername = username.getText();
        char[] enteredPassword = password.getPassword();
        String enteredPasswordString = new String(enteredPassword);

        String storedPassword = CsvHandler.getPassword(enteredUsername);

        if (Objects.nonNull(storedPassword) && storedPassword.equals(enteredPasswordString)) {
            Player user = new Player(enteredUsername, enteredPasswordString);
            swapScreens(new GameMainMenu(frame, this, user));
        } else {
            displayErrorMessage("You have entered an incorrect username or password, please try again or register an account");
        }

        // Clear the password field after retrieving the password
        Arrays.fill(enteredPassword, '0');
        password.setText("Enter Password");
        password.setEchoChar((char) 0);
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
