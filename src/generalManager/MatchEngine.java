package generalManager;

import java.util.*;

public class MatchEngine {

    public static MatchResults matchResults(Team team1, Team team2) {
        int team1Off = sumOffense(team1);
        int team2Off = sumOffense(team2);
        int team1Def = sumDefense(team1);
        int team2Def = sumDefense(team2);

        double team1Chance = (double) team1Off / (team2Def + 1);
        double team2Chance = (double) team2Off / (team1Def + 1);

        int goals1 = simulateGoals(team1Chance);
        int goals2 = simulateGoals(team2Chance);

        List<String> scorers1 = generateGoalDetails(team1, goals1);
        List<String> scorers2 = generateGoalDetails(team2, goals2);

        MatchResults result = new MatchResults(team1, team2, goals1, goals2);
        result.homeScorers = scorers1;
        result.awayScorers = scorers2;

        System.out.println(result);
        return result;
    }

    private static int sumOffense(Team team) {
        int total = 0;
        for (Player p : team.getPlayers().values()) {
            total += p.getOffFifaRating();
        }
        return total;
    }

    private static int sumDefense(Team team) {
        int total = 0;
        for (Player p : team.getPlayers().values()) {
            total += p.getDefFifaRating();
        }
        return total;
    }

    private static int simulateGoals(double chance) {
        double adjusted = chance * 1.2;
        int goals = 0;
        while (Math.random() < (adjusted / (goals + 6))) {
            goals++;
        }
        return goals;
    }

    private static List<String> generateGoalDetails(Team team, int goalCount) {
        List<Player> players = new ArrayList<>(team.getPlayers().values());
        int totalOff = 0;
        for (Player p : players) totalOff += p.getOffFifaRating();

        List<String> details = new ArrayList<>();

        for (int i = 0; i < goalCount; i++) {
            Player scorer = pickByOffense(players, totalOff);
            scorer.setGoal(scorer.getGoal() + 1);

            Player assist;
            do {
                assist = pickByPosition(players);
            } while (assist == scorer);

            assist.setAssist(assist.getAssist() + 1);
            details.add("Goal: " + scorer.getName() + " | Assist: " + assist.getName());
        }

        return details;
    }

    private static Player pickByOffense(List<Player> players, int totalOff) {
        double rand = Math.random() * totalOff;
        double sum = 0;

        for (Player p : players) {
            sum += p.getOffFifaRating();
            if (rand < sum) return p;
        }

        return players.get(players.size() - 1);
    }

    private static Player pickByPosition(List<Player> players) {
        Map<String, Double> weights = Map.of(
            "F", 0.8, 
            "M", 1.0, 
            "D", 0.2, 
            "G", 0.001
        );

        double totalWeight = 0;
        List<Double> cumulative = new ArrayList<>();
        for (Player p : players) {
            double w = weights.getOrDefault(p.getPosition(), 0.1);
            totalWeight += w;
            cumulative.add(totalWeight);
        }

        double rand = Math.random() * totalWeight;
        for (int i = 0; i < players.size(); i++) {
            if (rand < cumulative.get(i)) return players.get(i);
        }

        return players.get(players.size() - 1);
    }
}
