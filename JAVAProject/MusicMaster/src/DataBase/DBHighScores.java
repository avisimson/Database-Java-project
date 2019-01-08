package DataBase;

import Logic.HighScores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that handles all
 * SQL queries that return an HighScore object.
 */
public class DBHighScores {

    private java.sql.Connection con = DBConnection.getInstance().getConnection(); // DB connection

    /**
     * This function executes a query and returns the highest ten scores in the highScore table.
     * @return a list of 10 best scores in the table
     */
    public List<HighScores> TheBest10Score(){
        List<HighScores> highScoresList = new LinkedList<>();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select * from highscores order by Score desc limit 10 ")) {
            while (rs.next()){
                highScoresList.add(new HighScores(rs.getString("UserName"), rs.getInt("Score")));
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return highScoresList;
    }

    /**
     * This function inserts new highScore to the table.
     * @param highScores the highScore to insert.(player name and score)
     */
    public void UpdateHighScoresTable(HighScores highScores) {
        int result;
        //insert the new details to High Score Table
        try (Statement stmt = con.createStatement();) {
            result = stmt.executeUpdate("INSERT INTO highscores(UserName, Score) " +
                    "VALUES('" + highScores.getUserName() +"', " + highScores.getScore() + ")");

        } catch (SQLException e) {
            System.out.println("ERROR executeUpdate - " + e.getMessage());
        }
    }
}
