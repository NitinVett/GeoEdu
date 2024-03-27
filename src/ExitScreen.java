import javax.swing.*;
import java.awt.*;

public class ExitScreen extends Screen{
    JButton no, yes;

    public ExitScreen(JFrame frame) {
        super(frame);
        no = new JButton(" No");
        yes = new JButton("Yes");
        no.addActionListener(e -> noButton());
        yes.addActionListener(e -> yesButton());
        this.add(no);
        this.add(yes);
        repaint();
    }
    public void setComponents(Graphics g){
        int width = getWidth();
        int height = getHeight();
        int mainButtonY =  height/3;
        no.setBounds(width/2-width/15,mainButtonY+ (height/10)*4,width/20,height/20);
        yes.setBounds(width/3+width/6,mainButtonY+ (height/10)*4,width/20,height/20);
        no.setFont(new Font("SansSerif", Font.PLAIN, 24));
        yes.setFont(new Font("SansSerif", Font.PLAIN, 24));

        repaint();

    }
    public void noButton() {
        //have it bring u back to previous screen
        swapScreens(new SettingScreen(frame));
    }
    public void yesButton() {
        frame.dispose();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);



        setComponents(g);


    }
}
