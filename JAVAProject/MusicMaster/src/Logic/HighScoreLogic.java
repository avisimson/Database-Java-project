package Logic;

import DataBase.DBHighScores;

import java.util.List;

/**
 * This class manages the logic of the high score screen and the connection to the DB.
 */
public class HighScoreLogic {
    //members
    private DBHighScores DB_highscores = new DBHighScores();

    /**
     * This function returns the highest ten scores in the highScore table.
     * @return a list of 10 best scores in the table
     */
    public List<HighScores> getList10BestScores(){
        return DB_highscores.TheBest10Score();
    }

    /**
     * This function inserts a new high score to the table.
     * @param highScore is the current user score that we insert to the table.
     */
    public void setHighScoreTable(HighScores highScore) {DB_highscores.UpdateHighScoresTable(highScore);}
}
