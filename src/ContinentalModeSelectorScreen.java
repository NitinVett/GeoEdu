import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ContinentalModeSelectorScreen extends Screen {
    JButton americas;
    JButton asia;
    JButton europe;
    JButton esc;
    String mode;
    //JButton highScore;

    public ContinentalModeSelectorScreen (FullScreenUI frame, Screen previous, Player user, String mode) {
        super(frame, previous);
        americas = createCustomButton("Americas");
        asia = createCustomButton("Asia");
        europe = createCustomButton("Europe");
        esc = new JButton();
        americas.addActionListener(e -> americasButton());
        asia.addActionListener(e -> asiaButton());
        europe.addActionListener(e -> europeButton());
        esc.addActionListener(e -> escButton());
        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(new File("resources/escape.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());

        this.add(americas);
        this.add(asia);
        this.add(europe);
        this.add(esc);
    }

    public void exitButton() {
        swapScreens(prev);
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
        setButtonBackground(button, "resources/plank.png");
        return button;
    }

    private void setButtonBackground(JButton button, String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Image scaledImage = image.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);        //logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
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
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background

        g.setFont(loadFont("resources/Viner.ttf", 32));
        g.drawString("Select a continent to explore!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
