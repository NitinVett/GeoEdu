import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreScreen extends Screen {

    ArrayList<String> users;
    JButton next,previous;
    ArrayList<JLabel> playerList;
    public HighScoreScreen(JFrame frame,int page) {
        super(frame);
        users = CsvHandler.getHighScoreOrder();
        playerList = new ArrayList<>();
        next = new JButton("NEXT PAGE");
        previous = new JButton("PREVIOUS PAGE");
        for (int i = page*7;i<(page+1)*7;i++){
            if(!(i>users.size())){
                playerList.add(new JLabel(users.get(i)));


            }
        }

    }

    public void setComponents(){
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 4;
        int mainButtonYIncrement = height / 10;
        int mainButtonXIncrement = width/4;


        JLabel highScore = new JLabel("HIGHSCORE");
        JLabel rank = new JLabel("RANK");
        JLabel username = new JLabel("USERNAME");

        username.setFont(new Font("SansSerif", Font.BOLD, 24));
        highScore.setFont(new Font("SansSerif", Font.BOLD, 24));
        rank.setFont(new Font("SansSerif", Font.BOLD, 24));

        username.setBounds(mainButtonX, mainButtonY-mainButtonYIncrement, width / 5, height / 12);
        highScore.setBounds(mainButtonX+mainButtonXIncrement, mainButtonY-mainButtonYIncrement, width / 5, height / 12);
        rank.setBounds(mainButtonX-mainButtonXIncrement/2, mainButtonY-mainButtonYIncrement, width / 5, height / 12);

        this.add(rank);
        this.add(highScore);
        this.add(username);

        for(int i = 0;i<playerList.size();i++){

            highScore = new JLabel(CsvHandler.getHighScore(playerList.get(i).getText()));
            rank = new JLabel(Integer.toString(i+1));
            playerList.get(i).setBounds(mainButtonX, mainButtonY+mainButtonYIncrement*i, width / 5, height / 12);
            highScore.setBounds(mainButtonX+mainButtonXIncrement, mainButtonY+mainButtonYIncrement*i, width / 5, height / 12);
            rank.setBounds(mainButtonX-mainButtonXIncrement/2, mainButtonY+mainButtonYIncrement*i, width / 5, height / 12);

            playerList.get(i).setFont(new Font("SansSerif", Font.BOLD, 24));
            highScore.setFont(new Font("SansSerif", Font.BOLD, 24));
            rank.setFont(new Font("SansSerif", Font.BOLD, 24));
            highScore.setForeground(Color.red);
            playerList.get(i).setForeground(Color.PINK);
            this.add(rank);
            this.add(highScore);
            this.add(playerList.get(i));
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setComponents();
    }
}
