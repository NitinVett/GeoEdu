import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;

public class DebugScreen extends Screen{
    JTextField password, username;
    JButton login;
    public DebugScreen(FullScreenUI frame,Screen previous) {
        super(frame, previous);
        password = new JTextField("Enter Password", 16);
        login = new JButton("LOGIN");
        this.add(password);
        this.add(login);
        login.addActionListener(e -> loginButton());
        setFocusListeners(password,"Enter Password");
        repaint();
    }
    public void setComponents(){
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width/2 - width/10;
        int mainButtonY =  height/3;

        password.setBounds(mainButtonX,mainButtonY + height/10,width/5,height/20);
        login.setBounds(mainButtonX,mainButtonY + (height/10)*2,width/5,height/20);
        password.setFont(new Font("SansSerif", Font.PLAIN, 24));
        login.setFont(new Font("SansSerif", Font.PLAIN, 24));
        revalidate();
    }
    //debugger password 1029384756
    public void loginButton(){
        String pass = "1029384756";
        if(pass.equals(password.getText())) {
            swapScreens(new GameMainMenu(frame,this));
        }else {
            displayErrorMessage("Wrong Password");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);


    }
}
