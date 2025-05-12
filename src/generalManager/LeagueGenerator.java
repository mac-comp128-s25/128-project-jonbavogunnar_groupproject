package generalManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueGenerator {
    public static List<Team> generateLeagueTeams(Map<String, Player> fullPool, List<Player> userTeam) {
        List<Player> pool = new ArrayList<>(fullPool.values());
        pool.removeAll(userTeam);

        Collections.shuffle(pool);
        List<Team> teams = new ArrayList<>();

        int playersPerTeam = 11;
        int teamCount = 15;

        for (int i = 0; i < teamCount; i++) {
            Map<String, Player> teamMap = new HashMap<>();
            for (int j = 0; j < playersPerTeam && !pool.isEmpty(); j++) {
                Player p = pool.remove(0);
                teamMap.put(p.getName(), p);
            }
            teams.add(new Team("Team_" + (i + 1), teamMap));
        }
        return teams;
    }
}
