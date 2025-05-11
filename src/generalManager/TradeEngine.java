package generalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeEngine {
    private static final Map<Character, Double> POSITION_WEIGHTS = Map.of(
        'F', 1.5,
        'M', 1.2,
        'D', 1.0,
        'G', 0.6
    );

    private static final double FAIRNESS_THRESHOLD = 0.92;

    private static double calculatePlayerValue(Player p) {
        return POSITION_WEIGHTS.getOrDefault(p.getPosition(), 1.0) * p.getFifaRating();
    }

    private static double calculateOfferValue(List<Player> players) {
        return players.stream().mapToDouble(TradeEngine::calculatePlayerValue).sum();
    }

    public static boolean evaluateTrade(List<Player> userOffer, List<Player> otherOffer) {
        double userValue = calculateOfferValue(userOffer);
        double computerValue = calculateOfferValue(otherOffer);

        System.out.printf("User offer value: %.2f, Computer offer value: %.2f%n", userValue, computerValue);

        return userValue >= computerValue * FAIRNESS_THRESHOLD;
    }

    public static void executeTrade(Team userTeam, Team otherTeam, List<String> userPlayersName, List<String> otherPlayersName){
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

        if (evaluateTrade(userOffer, otherOffer)) {
            for (Player p : userOffer) {
                userTeam.getPlayers().remove(p.getName());
                otherTeam.getPlayers().put(p.getName(), p);
            }

            for (Player p : otherOffer) {
                otherTeam.getPlayers().remove(p.getName());
                userTeam.getPlayers().put(p.getName(), p);
            }

            System.out.println("Trade accepted!");
        } else {
            System.out.println("Trade rejected. Your offer isn't strong enough.");
        }
    }
}
