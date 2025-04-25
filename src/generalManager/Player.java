package generalManager;

public class Player {

    private String team;
    private String name;
    private String nationality;
    private char position;
    private int fifaRating;

    public Player(String team, String name, String nationality, char position, int fifaRating) {
        this.team = team;
        this.name = name;
        this.nationality = nationality;
        this.position = position;
        this.fifaRating = fifaRating;
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

    @Override
    public String toString() {
        return name + ": ";
    }
}
