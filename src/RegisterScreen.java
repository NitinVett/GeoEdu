import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends Screen{
    JTextField password, username, password_2;
    JButton register;
    public RegisterScreen(JFrame frame) {
        super(frame);
        password = new JTextField("Enter Password", 16);
        username = new JTextField("Enter Username", 16);
        password_2 = new JTextField("Re-enter Password", 16);
        register= new JButton("REGISTER");
        this.add(password);
        this.add(username);
        this.add(password_2);
        this.add(register);
        register.addActionListener(e -> registerButton());
        setFocusListeners(password,"Enter Password");
        setFocusListeners(username,"Enter Username");
        setFocusListeners(password_2,"Re-enter Password");
        repaint();


    }
    public void setComponents(){
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width/2 - width/10;
        int mainButtonY =  height/3;

        username.setBounds(mainButtonX, mainButtonY, width/5, height/20);
        password.setBounds(mainButtonX,mainButtonY + height/10,width/5,height/20);
        password_2.setBounds(mainButtonX,mainButtonY + (height/10)*2,width/5,height/20);
        register.setBounds(mainButtonX,mainButtonY + (height/10)*3,width/5,height/20);
        password_2.setFont(new Font("SansSerif", Font.PLAIN, 24));
        username.setFont(new Font("SansSerif", Font.PLAIN, 24));
        password.setFont(new Font("SansSerif", Font.PLAIN, 24));
        revalidate();


    }
    public void registerButton(){
        int error = CsvHandler.addUser(username.getText());
        if(error == 0) {

            swapScreens(new MainMenu(frame));
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);


    }
}
