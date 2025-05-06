package generalManager;

public class PlayerComparator {
    private Player player;
    public boolean comparePlayers(Player p1, Player p2) {
        if (player.playerValue(p1) - player.playerValue(p2) > 5 || player.playerValue(p1) - player.playerValue(p2) <-5) {
            return false;
        }
        return true;
    }
}
