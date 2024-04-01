import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExitScreen extends Screen {
    JButton no, yes, esc;

    public ExitScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);

        no = new JButton(" No");
        yes = new JButton("Yes");
        no.addActionListener(e -> noButton());
        yes.addActionListener(e -> yesButton());
        this.add(no);
        this.add(yes);
        esc = new JButton();
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

    public void exitButton() {
        swapScreens(prev);
    }

    public void setComponents(Graphics g) {

        int width = getWidth();
        int height = getHeight();
        int mainButtonY = height / 3;

        no.setBounds(width / 3 + width / 6, mainButtonY + (height / 10) * 4, width / 20, height / 20);
        yes.setBounds(width / 2 - width / 15, mainButtonY + (height / 10) * 4, width / 20, height / 20);
        no.setFont(new Font("SansSerif", Font.PLAIN, 24));
        yes.setFont(new Font("SansSerif", Font.PLAIN, 24));
        esc.setBounds(width / 30, height / 22, 50, 50);

        repaint();

    }

    public void noButton() {
        //have it bring u back to previous screen
        //swapScreens(new SettingScreen(frame,this));
        swapScreens(prev);
    }

    public void yesButton() {
        frame.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents(g);
    }
}
