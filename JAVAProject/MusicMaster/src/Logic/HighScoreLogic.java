package Logic;


import DataBase.DBHighScores;

import java.util.List;

public class HighScoreLogic {
    private DBHighScores DB_highscores = new DBHighScores();
    public List<HighScores> getList10BestScores(){
        return DB_highscores.TheBest10Score();
    }
}
