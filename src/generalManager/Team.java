package generalManager;
import java.util.Map;

public class Team {
    
    private String name;
    private Map<String, Player> players;
    
    public Team(String name, Map<String, Player> players) {
        this.name = name;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public int getFifaRating() {
        int output = 0;
        for(Map.Entry<String, Player> entry : players.entrySet()) {
            output += entry.getValue().getFifaRating();
        }
        return output;
    }
}
