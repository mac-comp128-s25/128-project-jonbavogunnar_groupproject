package generalManager;

import java.util.List;

public class TeamValidator {
    public static boolean isValidTeam(List<Player> team) {
        if (team.size() != 11) return false;
        
        long g = team.stream().filter(p -> p.getPosition().equals("G")).count();
        long d = team.stream().filter(p -> p.getPosition().equals("D")).count();
        long m = team.stream().filter(p -> p.getPosition().equals("M")).count();
        long f = team.stream().filter(p -> p.getPosition().equals("F")).count();

        return g == 1 && d == 4 && m == 3 && f == 3;
    }
}
