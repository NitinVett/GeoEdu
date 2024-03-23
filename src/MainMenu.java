import javax.swing.*;
import java.awt.*;

public class MainMenu extends Screen{

    //Image image;
    JButton login;
    JButton register;
    JButton exit;
    MainMenu() {

        login = new JButton();
        register = new JButton();
        exit = new JButton();

        login.addActionListener(e -> loginButton());
        login.setText("LOGIN");
        login.setBounds(getWidth()/2,getHeight()/3 + (getHeight()/10),getWidth()/5,getHeight()/10);

        register.addActionListener(e -> registerButton());
        register.setText("REGISTER");
        login.setBounds(getWidth()/2,getHeight()/3 + (getHeight()/10)*2,getWidth()/5,getHeight()/10);

        exit.addActionListener(e -> exitButton());
        exit.setText("EXIT");
        login.setBounds(getWidth()/2,getHeight()/3 + (getHeight()/10)*3,getWidth()/5,getHeight()/10);


        this.add(login);
        this.add(register);
        this.add(exit);
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width/2 - width/10;
        int mainButtonY =  height/3;
        int mainButtonYIncrement = height/10;
        login.setBounds(mainButtonX, mainButtonY, width/5, height/12);
        register.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width/5, height/12);
        exit.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement*2, width/5, height/12);
        revalidate();

    }



    public void loginButton(){

    }

    public void registerButton(){

    }

    public void exitButton(){

    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        drawTitle(g2D);
        updateButtonPositions();


    }
}
