import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    public Screen(){
        //set the background for our screens here
        //this.setBackground();
        JButton settings = new JButton();

        //settings image here
        //settings.setIcon();

        //setting button location here
        //settings.setBounds();

        settings.addActionListener(e -> settingsButton());
        this.setLayout(null);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
    }

    //add functionality for setting button
    public void settingsButton(){

    }

    public void drawTitle(Graphics2D g){
        g.setFont(new Font("SansSerif", Font.BOLD, 48));

        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth("GEOCRAFT");
        int xPosition = getWidth()/2 - titleWidth/2;
        int yPosition = getHeight()/10;

        g.drawString("GEOCRAFT", xPosition, yPosition);
    }




}
