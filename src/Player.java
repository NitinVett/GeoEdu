public class Player extends User {

    private int highScore;
    private float accuracy;

    private int numGames;

    public Player() {
        this.accuracy = 100;
        this.highScore = 0;
        this.numGames = 0;
    }

    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
