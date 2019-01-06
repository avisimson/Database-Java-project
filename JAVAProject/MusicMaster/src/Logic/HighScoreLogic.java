package Logic;


import DataBase.DBHighScores;

import java.util.List;
/**
 * class that manage the logic of high score screen and the their connection to correct DB.
 */
public class HighScoreLogic {
    //members
    private DBHighScores DB_highscores = new DBHighScores();

    /**
     * function that return list of 10 best results from high score table
     * @return list of 10 best results from high score table
     */
    public List<HighScores> getList10BestScores(){
        return DB_highscores.TheBest10Score();
    }
    /**
     * function that update the details of high score table(user , score)
     * @param highScores is current user that finish to play in game
     */
    public void setHighScoreTable(HighScores highScores) {DB_highscores.UpdateHighScoresTable(highScores);}
}
