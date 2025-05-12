package generalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeEngine {

    private static final double FAIRNESS_THRESHOLD = 0.98;
    private static final PlayerComparator comparator = new PlayerComparator();

    private static double calculateOfferValue(List<Player> players) {
        double total = 0;
        for (Player p : players) {
            total += comparator.getValue(p);
        }
        return total;
    }

    public static boolean evaluateTrade(List<Player> userOffer, List<Player> otherOffer) {
        double userValue = calculateOfferValue(userOffer);
        double computerValue = calculateOfferValue(otherOffer);

        System.out.printf("User offer value: %.2f, Computer offer value: %.2f%n", userValue, computerValue);

        return userValue >= computerValue * FAIRNESS_THRESHOLD;
    }

    private static boolean containsGoalie(List<Player> players) {
        for (Player p : players) {
            if (p.getPosition().equalsIgnoreCase("G")) {
                return true;
            }
        }
        return false;
    }


    public static boolean executeTrade(Team userTeam, Team otherTeam, List<String> userPlayersName, List<String> otherPlayersName){
        List<Player> userOffer = new ArrayList<>();
        List<Player> otherOffer = new ArrayList<>();

        for (String name : userPlayersName) {
            Player p = userTeam.getPlayers().get(name);
            if (p != null) userOffer.add(p);
        }

        for (String name : otherPlayersName) {
            Player p = otherTeam.getPlayers().get(name);
            if (p != null) otherOffer.add(p);
        }

        if (containsGoalie(userOffer) || containsGoalie(otherOffer)) {
            return false;
        }

        if (evaluateTrade(userOffer, otherOffer)) {
            for (Player p : userOffer) {
                userTeam.getPlayers().remove(p.getName());
                otherTeam.getPlayers().put(p.getName(), p);
            }

            for (Player p : otherOffer) {
                otherTeam.getPlayers().remove(p.getName());
                userTeam.getPlayers().put(p.getName(), p);
            }

            return true;
        } else {
            return false;
        }
    }
}   
