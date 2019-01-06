package Logic;

/**
 * Data class of High Score object that contains the details about a high score.
 */
public class HighScores {

    //members
    private String userName;
    private int score;

    /**
     * Constructor
     * @param userName is the name of the user that played
     * @param score is the score of the user
     */
    public HighScores(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }
    /**
     * @return the user name.
     */
    public String getUserName() { return this.userName;}

    /**
     * @return the user score
     */
    public int getScore() { return this.score; }

    /**
     * function that updates the user name
     * @param userName is the username
     */
    public void setUserName(String userName) {this.userName = userName;}

    /**
     * function that updates the score o
     * @param score is the score
     */
    public void setScore(int score) {this.score = score;}
}
