package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBArtists {
    private String ArtistFilter[] = new String[15];
    private java.sql.Connection con = DBConnection.getInstance().getConnection();

    /**
     * function that execute query that return list of artist in this genres
     *
     * @param GenreName is the array of selected genres
     * @return the list of artist that songs in this genres
     */
    public String[] FilterArtistByGenre(String[] GenreName) {
        System.out.println(GenreName[0]);
        System.out.println(GenreName[1]);
        System.out.println(GenreName[2]);
        int sumOfGenre = 0;
        if (GenreName[1] == null)
            sumOfGenre = 1;
        else if (GenreName[2] == null)
            sumOfGenre = 2;
        else
            sumOfGenre = 3;
        FilterByGenre(GenreName, sumOfGenre);
        return ArtistFilter;
    }

    /**
     * function that return the artists that song in selected genre
     *
     * @param GenreChoice    is the array of selected genre
     * @param numberOfGenres number of selected genres
     */
    public void FilterByGenre(String[] GenreChoice, int numberOfGenres) {
        int i = 0;
        String genreNumberQuery = "";
        for (int j = 0; j < numberOfGenres; j++) {
            if (j != 0) {
                genreNumberQuery += " or ";
            }
            genreNumberQuery += "GenreName = \"";
            genreNumberQuery += GenreChoice[j];
            genreNumberQuery += "\"";
        }
        //query for two genre
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct(ArtistName) from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        genreNumberQuery + "limit 15;  ")) {
            while (rs.next() == true) {
                ArtistFilter[i] = rs.getString("ArtistName");
                i++;
            }
            for (i = 0; i < ArtistFilter.length; i++) {
                System.out.println(ArtistFilter[i]);
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
    }

}
