import java.io.IOException;

public class ExplorationMode extends GameplayScreen{
    public ExplorationMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2) throws IOException {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);
    }
}
