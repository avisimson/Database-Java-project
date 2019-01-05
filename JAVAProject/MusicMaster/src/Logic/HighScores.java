package Logic;

public class HighScores {

    private String userName;
    private int score;

    public HighScores(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }
    public String getUserName() { return this.userName;}

    public int getScore() { return this.score; }

    public void setUserName(String userName) {this.userName = userName;}

    public void setScore(int score) {this.score = score;}
}
