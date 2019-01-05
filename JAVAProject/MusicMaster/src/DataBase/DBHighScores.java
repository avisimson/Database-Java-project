package DataBase;

import Logic.Genre;
import Logic.HighScores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DBHighScores {
    private java.sql.Connection con = DBConnection.getInstance().getConnection();
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
}
