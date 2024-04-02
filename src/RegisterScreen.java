import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class RegisterScreen extends Screen {
    JTextField username;
    JPasswordField password, password_2;
    JButton register, esc;
    Image plankIMG;

    public RegisterScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        esc = new JButton();


        // Username field setup
        username = new JTextField("Enter Username", 16);
        username.setOpaque(false);
        username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        username.setForeground(new Color(255, 255, 255));
        username.setCaretColor(Color.WHITE);
        username.setFont(loadFont("resources/Viner.ttf", 24));

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

        password = new JPasswordField(16);

        password.setText("Enter Password");
        password.setEchoChar((char) 0); // Set initial echo char to 0 (no echo)
        password.setOpaque(false);
        password.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK)); // Remove border
        password.setForeground(new Color(255, 255, 255));
        password.setCaretColor(Color.WHITE);
        password.setFont(loadFont("resources/Viner.ttf", 24));
        password_2 = new JPasswordField(16);
        password_2.setText("Re-enter Password");
        password_2.setEchoChar((char) 0); // Set initial echo char to 0 (no echo)
        password_2.setOpaque(false);
        password_2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK)); // Remove border
        password_2.setForeground(new Color(255, 255, 255));
        password_2.setCaretColor(Color.WHITE);
        password_2.setFont(loadFont("resources/Viner.ttf", 24));
        // Add focus listeners to handle placeholder text behavior for password fields
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


        password_2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the password field gains focus
                if (Arrays.equals(password_2.getPassword(), "Re-enter Password".toCharArray())) {
                    password_2.setText("");
                    password_2.setEchoChar('*'); // Set echo char to '*' for password entry
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the password field is left empty
                if (password_2.getPassword().length == 0) {
                    password_2.setText("Re-enter Password");
                    password_2.setEchoChar((char) 0); // Set echo char to 0 (no echo)
                }
            }
        });

        // Register button setup
        register = new JButton("Register");
        register.addActionListener(e -> registerButton());

        // Add components to the panel
        this.add(username);
        this.add(password);
        this.add(password_2);
        this.add(register);

        // Esc button setup
        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(new File("resources/escape.png"));
            plankIMG = ImageIO.read(new File("resources/plank.png"));
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
        Image scaledImage = plankIMG.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(register,scaledImage,width/60);
        username.setBounds(mainButtonX, mainButtonY, width / 5, height / 20);
        password.setBounds(mainButtonX, mainButtonY + height / 10, width / 5, height / 20);
        password_2.setBounds(mainButtonX, mainButtonY + (height / 10) * 2, width / 5, height / 20);
        register.setBounds(mainButtonX+(width/20), mainButtonY + (height / 10) * 3, width / 10, height / 20);

        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);
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
            displayErrorMessage("Your passwords do not match");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);
    }
}
