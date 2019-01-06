package Logic;

/**
 * class of High Score object that contain the details about high score table.
 */
public class HighScores {

    //members
    private String userName;
    private int score;

    /**
     * constructor of high score
     * @param userName is the name of user that played
     * @param score is the score of the user in the game
     */
    public HighScores(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }
    /**
     * function that return the user name
     * @return the user name.
     */
    public String getUserName() { return this.userName;}

    /**
     * function get that return the score of user
     * @return the score
     */
    public int getScore() { return this.score; }

    /**
     * function that update the name of user name
     * @param userName is the name of user
     */
    public void setUserName(String userName) {this.userName = userName;}

    /**
     * function that update the score of user
     * @param score is the score of user in game
     */
    public void setScore(int score) {this.score = score;}
}
