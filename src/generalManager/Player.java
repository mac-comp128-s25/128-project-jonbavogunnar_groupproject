package generalManager;

public class Player {

    private String team;
    private String name;
    private String nationality;
    private String position;
    private int fifaRating;
    private int offFifaRating;
    private int defFifaRating;
    private int goal;
    private int assist;
    private int playerVal;

    public Player(String team, String name, String nationality, String position, int fifaRating, int offFifaRating, int defFifaRating) {
        this.team = team;
        this.name = name;
        this.nationality = nationality;
        this.position = position;
        this.fifaRating = fifaRating;
        this.offFifaRating = offFifaRating;
        this.defFifaRating = defFifaRating;
    }
    
    public int playerValue(Player player) {
        if (player.position == "G") {
            return player.fifaRating;
        } else if (player.position == "D") {
            return player.defFifaRating;
        } else if (player.position == "M") {
            return (player.offFifaRating + player.defFifaRating) / 2;
        } else {
            return player.offFifaRating;
        }
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getFifaRating() {
        return fifaRating;
    }

    public void setFifaRating(int fifaRating) {
        this.fifaRating = fifaRating;
    }

    public int getOffFifaRating() {
        return offFifaRating;
    }

    public void setOffFifaRating(int offFifaRating) {
        this.offFifaRating = offFifaRating;
    }

    public int getDefFifaRating() {
        return defFifaRating;
    }

    public void setDefFifaRating(int defFifaRating) {
        this.defFifaRating = defFifaRating;
    }

    public int getGoal(){ 
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist){
        this.assist = assist;
    }

    @Override
    public String toString() {
        return name + ": " + nationality;
    }
}
