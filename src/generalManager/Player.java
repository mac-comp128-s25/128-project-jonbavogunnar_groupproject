package generalManager;

public class Player {

    private String team;
    private String name;
    private String nationality;
    private char position;
    private int fifaRating;
    private int offFifaRating;
    private int defFifaRating;
    private int goal;
    private int assist;

    public Player(String team, String name, String nationality, char position, int fifaRating, int offFifaRating, int defFifaRating) {
        this.team = team;
        this.name = name;
        this.nationality = nationality;
        this.position = position;
        this.fifaRating = fifaRating;
        this.offFifaRating = offFifaRating;
        this.defFifaRating = defFifaRating;
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

    public char getPosition() {
        return position;
    }

    public void setPosition(char position) {
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
