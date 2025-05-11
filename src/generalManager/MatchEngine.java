package generalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;


public class MatchEngine {
    public static MatchResults matchResults(Team team1, Team team2) {
        int team1Off = team1.getPlayers().values().stream().mapToInt(Player :: getOffFifaRating).sum();
        int team2Off = team2.getPlayers().values().stream().mapToInt(Player :: getOffFifaRating).sum();
        int team1Def = team1.getPlayers().values().stream().mapToInt(Player :: getDefFifaRating).sum();
        int team2Def = team2.getPlayers().values().stream().mapToInt(Player :: getDefFifaRating).sum();

        double team1ScoreChance = (double) team1Off / (team2Def + 1);
        double team2ScoreChance = (double) team2Off / (team1Def + 1);

        int team1Goals = (int)(Math.random() * team1ScoreChance);
        int team2Goals = (int)(Math.random() * team2ScoreChance);

         List<String> team1Scorers = generateStats(team1, team1Goals);
        List<String> team2Scorers = generateStats(team2, team2Goals);

        MatchResults result = new MatchResults(team1, team2, team1Goals, team2Goals);
        result.setTeam1Scorers(team1Scorers);
        result.setTeam2Scorers(team2Scorers);
        System.out.println(result);
        return result;
    }

    private static List<String> generateStats(Team team, int goals){
        List<Player> playerList = new ArrayList<>(team.getPlayers().values());
        int totalOffRating = playerList.stream().mapToInt(Player::getOffFifaRating).sum();
        List<String> goalDetails = new ArrayList<>();

        for (int i = 0; i < goals; i++) {
            Player scorer = weightedRandomPlayersOff(playerList, totalOffRating);
            scorer.setGoal(scorer.getGoal() + 1);

            Player assister = weightedRandomPlayersByPosistion(playerList);
            while (assister.equals(scorer)) {
                assister = weightedRandomPlayersByPosistion(playerList);
            }
            assister.setAssist(assister.getAssist() + 1);
            goalDetails.add("Goal " + scorer.getName() + "Assist " + assister.getName());
        }
        return goalDetails;
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
        return players.get(players.size() -  1);
    }
}
