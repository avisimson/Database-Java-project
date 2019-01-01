package DataBase;

import Logic.Artist;
import Logic.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DBArtists {
    private List<Artist> artistFilter = new LinkedList<>();
    private java.sql.Connection con = DBConnection.getInstance().getConnection();

    /**
     * function that execute query that return list of artist in this genres
     *
     * @param genreNames is the array of selected genres
     * @return the list of artist that songs in this genres
     */
    public List<Artist> FilterArtistByGenre(List<Genre> genreNames) {
        for(int i = 0; i < genreNames.size(); i++) {
            System.out.println(genreNames.get(i).getGenreName());
        }
        FilterByGenre(genreNames);
        return artistFilter;
    }

    /**
     * function that return the artists that song in selected genre
     *
     * @param genreChoice is the array of selected genre
     */
    public void FilterByGenre(List<Genre> genreChoice) {
        int i = 0;
        String genreNumberQuery = "";
        for (int j = 0; j < genreChoice.size(); j++) {
            if (j != 0) {
                genreNumberQuery += " or ";
            }
            genreNumberQuery += "GenreName = \"";
            genreNumberQuery += genreChoice.get(j).getGenreName();
            genreNumberQuery += "\"";
        }
        //query genre
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct(ArtistName) from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        genreNumberQuery + "limit 15;")) {
            while (rs.next()) {
                artistFilter.add(i, new Artist(-1,rs.getString("ArtistName"), -1));
                i++;
            }
            for (i = 0; i < artistFilter.size(); i++) {
                System.out.println(artistFilter.get(i).getArtistName());
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
    }

}
