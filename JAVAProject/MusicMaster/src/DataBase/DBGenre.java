package DataBase;

import Logic.Genre;
import javafx.scene.control.CheckBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DBGenre {

    private java.sql.Connection con = DBConnection.getInstance().getConnection();

    public List<Genre> GenreList() {
        int i = 0;
        List<Genre> genreList = new LinkedList<>();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select GenreName from genre")) {

            while (rs.next() == true){
                genreList.add(new Genre(-1, rs.getString("GenreName")));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return genreList;
    }
}
