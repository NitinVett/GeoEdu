public class Player extends User {


    public Player(String username, String password) {
        super(username, password);

    }

    public int getNumGames(){
        return Integer.parseInt(CsvHandler.getNumGamesPlayed(getUsername()));
    }

    public int getHighScore(){
        return Integer.parseInt(CsvHandler.getHighScore(getUsername()));
    }

    public float getAccuracy(){
        return Float.parseFloat(CsvHandler.getAccuracyRate(getUsername()));
    }
    public void setNumGames(int numGames){
        CsvHandler.changeNumGamesPlayed(getUsername(), Integer.toString(numGames));
    }
    public void setHighScore(int highScore){
        CsvHandler.changeHighScore(getUsername(), Integer.toString(highScore));
    }
    public void setAccuracy(float accuracy){
        CsvHandler.changeAccuracyRate(getUsername(), Float.toString(accuracy));
    }
    public void setGameData(String gameData){
        CsvHandler.changeListOfCountry(getUsername(),gameData);
    }
    public String getGameData(){
        return CsvHandler.getListOfCountry(getUsername());
    }

}
