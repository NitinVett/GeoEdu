import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameTypeSelectorScreen extends Screen {
    JButton marathon;
    JButton timed;
    JButton exploration;
    JButton esc;
    String mode;
    //JButton highScore;

    public GameTypeSelectorScreen(FullScreenUI frame, Screen previous, Player user, String mode,String continent) {
        super(frame, previous,user);
        marathon = createCustomButton("Marathon");
        timed = createCustomButton("Timed");
        exploration = createCustomButton("Exploration");
        esc = new JButton();

        marathon.addActionListener(e -> marathonButton());
        timed.addActionListener(e -> timedButton());
        exploration.addActionListener(e -> explorationButton());

        BufferedImage escIcon = null;
        try {
            escIcon = ImageIO.read(new File("resources/escape.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());

        this.add(marathon);
        this.add(timed);
        this.add(exploration);
        this.add(esc);
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
        setButtonBackground(button);
        return button;
    }

    private void setButtonBackground(JButton button) {
        try {
            BufferedImage image = ImageIO.read(new File("resources/plank.png"));
            Image scaledImage = image.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width - width / 3;
        int mainButtonY = height / 3 + height / 11;
        int mainButtonYIncrement = height / 10;
        marathon.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        timed.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        exploration.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);        //logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
        revalidate();

    }
    public void explorationButton() {
        try {
            GameTesting playExploration = new GameTesting(frame, user, "Global Mode",null,"Exploration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void marathonButton() {
        try {
            GameTesting playMarathon = new GameTesting(frame, user, "Global Mode",null,"Marathon");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void timedButton() {
        try {
            GameTesting playTimed = new GameTesting(frame, user, "Global Mode",null,"Timed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void exitButton(){
        swapScreens(prev);
    }
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 8;
        int mainButtonY = height / 3 + height / 8;
        super.paintComponent(g); // Paints the background
        g.setFont(loadFont("resources/Viner.ttf", 32));
        g.drawString("What game type will you play today!", mainButtonX, mainButtonY);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
        repaint();
    }
}
