package generalManager;

import java.util.Comparator;
import java.util.Map;

public class PlayerComparator implements Comparator<Player> {

    private static final Map<Character, Double> POSITION_WEIGHTS = Map.of(
        'F', 1.5,
        'M', 1.2,
        'D', 1.0,
        'G', 0.6
    );

    private static double calculatePlayerValue(Player p) {
        return POSITION_WEIGHTS.getOrDefault(p.getPosition(), 1.0) * p.getFifaRating();
    }

    @Override
    public int compare(Player p1, Player p2) {
        return Double.compare(calculatePlayerValue(p1), calculatePlayerValue(p2));
    }

    public double getValue(Player p){
        return calculatePlayerValue(p);
    }
}
