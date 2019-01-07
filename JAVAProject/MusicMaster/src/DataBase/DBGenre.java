package DataBase;

import Logic.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that handles all
 * SQL queries that return an genre object.
 */
public class DBGenre {

    private java.sql.Connection con = DBConnection.getInstance().getConnection(); // DB connection

    /**
     * This function creates a list of genres from the genre table in the DB.
     * @return - list of genres
     */
    public List<Genre> GenreList() {
        List<Genre> genreList = new LinkedList<>();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select GenreID, GenreName from genre")) {

            while (rs.next()){
                genreList.add(new Genre(rs.getInt("GenreID"), rs.getString("GenreName")));
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return genreList;
    }
}
