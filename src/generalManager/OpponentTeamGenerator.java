package generalManager;

import java.util.*;
import java.util.stream.Collectors;

public class OpponentTeamGenerator {

    public static List<Player> generateOpponentTeam(Map<String, Player> allPlayers) {
        List<Player> pool = new ArrayList<>(allPlayers.values());
        Collections.shuffle(pool);

        List<Player> team = new ArrayList<>();
        int g = 0, d = 0, m = 0, f = 0;

        for (Player p : pool) {
            String pos = p.getPosition();
            if (pos.equals("G") && g < 1) {
                team.add(p);
                g++;
            } else if (pos.equals("D") && d < 4) {
                team.add(p);
                d++;
            } else if (pos.equals("M") && m < 3) {
                team.add(p);
                m++;
            } else if (pos.equals("F") && f < 3) {
                team.add(p);
                f++;
            }

            if (team.size() == 11) break;
        }

        // Fallback: if not enough players by position
        if (team.size() < 11) {
            Set<String> alreadyAdded = team.stream().map(Player::getName).collect(Collectors.toSet());
            for (Player p : pool) {
                if (!alreadyAdded.contains(p.getName())) {
                    team.add(p);
                    if (team.size() == 11) break;
                }
            }
        }

        return team;
    }
}
