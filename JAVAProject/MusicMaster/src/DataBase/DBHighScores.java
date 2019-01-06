package DataBase;

import Logic.Genre;
import Logic.HighScores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * class that manage the data of High score table in DB
 */
public class DBHighScores {
    //connection to DB
    private java.sql.Connection con = DBConnection.getInstance().getConnection();

    /**
     * function that execute query that return 10 best scores in table
     * @return list of 10 best scores in the table
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
     * function that insert new values of player that finish to play
     * @param highScores is the details about this player
     */
    public void UpdateHighScoresTable(HighScores highScores) {
        int result;
        //insert the new details to High Score Table
        try (Statement stmt = con.createStatement();) {
            result = stmt.executeUpdate("INSERT INTO highscores(UserName, Score) " +
                    "VALUES('" + highScores.getUserName() +"', " + highScores.getScore() + ")");
            System.out.println("Success - executeUpdate, result = " + result);

        } catch (SQLException e) {
            System.out.println("ERROR executeUpdate - " + e.getMessage());
        }
    }
}
