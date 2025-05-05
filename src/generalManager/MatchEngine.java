package generalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchEngine {
    public static void MatchResults(Team team1, Team team2) {
        int team1Off = team1.getPlayers().values().stream().mapToInt(Player :: getOffFifaRating).sum();
        int team2Off = team2.getPlayers().values().stream().mapToInt(Player :: getOffFifaRating).sum();
        int team1Def = team1.getPlayers().values().stream().mapToInt(Player :: getDefFifaRating).sum();
        int team2Def = team2.getPlayers().values().stream().mapToInt(Player :: getDefFifaRating).sum();

        double team1ScoreChance = (double) team1Off / (team2Def + 1);
        double team2ScoreChance = (double) team2Off / (team1Def + 1);

        int team1Goals = (int)(Math.random() * team1ScoreChance);
        int team2Goals = (int)(Math.random() * team2ScoreChance);
        
        generateStats(team1, team1Goals);
        generateStats(team2, team2Goals);

    }

    private static void generateStats(Team team, int goals){
        List<Player> playerList = new ArrayList<>(team.getPlayers().values());
        int totalOffRating = playerList.stream().mapToInt(Player::getOffFifaRating).sum();

        for (int i = 0; i < goals; i++) {
            Player scorer = weightedRandomPlayersOff(playerList, totalOffRating);
            scorer.setGoal(scorer.getGoal() + 1);

            Player assister = weightedRandomPlayersByPosistion(playerList);
            while (assister.equals(scorer)) {
                assister = weightedRandomPlayersByPosistion(playerList);
            }
            assister.setAssist(assister.getAssist() + 1);
        }

    }

    private static Player weightedRandomPlayersOff(List<Player> players, int totalOffRating){
        double rand = Math.random() * totalOffRating;
        double cumulative = 0;

        for (Player player : players) {
            cumulative += player.getOffFifaRating();
            if (rand < cumulative) {
                return player;
            }
        }
        return players.get(players.size() - 1);
    }

    private static Player weightedRandomPlayersByPosistion(List<Player> players){
        Map<String, Double> weights = Map.of(
            "F", 0.8,
            "M", 1.0,
            "D", 0.2,
            "G", 0.001
        );

        double totalWeight = 0;
        List<Double> cumulativeWeights = new ArrayList<>();
        for (Player p : players) {
            double weight = weights.getOrDefault(p.getPosition(), 0.1);
            totalWeight += weight;
            cumulativeWeights.add(totalWeight);
        }

        double rand = Math.random() * totalWeight;
        for (int i = 0; i < players.size(); i++) {
            if (rand < cumulativeWeights.get(i)) {
                return players.get(i);
            }
        }
        return players.get(players.size() - 1);
    }
}
