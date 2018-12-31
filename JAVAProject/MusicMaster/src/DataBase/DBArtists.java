package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBArtists {
    private String ArtistFilter[] = new String[15];
    private java.sql.Connection con = DBConnection.getInstance().getConnection();
    /**
     * function that execute query that return list of artist in this genres
     * @param GenreName is the array of selected genres
     * @return the list of artist that songs in this genres
     */
    public String[] FilterArtistByGenre(String[] GenreName) {
        System.out.println(GenreName[0]);
        System.out.println(GenreName[1]);
        System.out.println(GenreName[2]);
        int sumOfGenre = 0;
        if(GenreName[1] == null)
            sumOfGenre = 1;
        else if (GenreName[2] == null)
            sumOfGenre = 2;
        else
            sumOfGenre = 3;
        switch (sumOfGenre){
            case 1:
                FilterByOneGenre(GenreName);
                break;
            case 2:
                FilterByTwoGenre(GenreName);
                break;
            case 3:
                FilterByThreeGenre(GenreName);
                break;
        }
        return ArtistFilter;
    }
    /**
     * function that return the artists that song in selected genre
     * @param GenreChoice is the array of selected genre
     */
    public void FilterByOneGenre(String[] GenreChoice) {
        int i = 0;
        //query for two genre
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct(ArtistName) from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        "(GenreName = \""+GenreChoice[0]+"\" limit 15;  ")) {
            while (rs.next() == true)  {
                ArtistFilter[i] = rs.getString("ArtistName");
                i++;
            }
            for(i = 0; i < ArtistFilter.length; i++){
                System.out.println(ArtistFilter[i]);
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
    }
    /**
     * function that return the artists that song in one of two selected genres
     * @param GenreChoice is the array of selected genre
     */
    public void FilterByTwoGenre(String[] GenreChoice) {
        int i = 0;
        //query for two genre
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct(ArtistName) from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        "(GenreName = \""+GenreChoice[0]+"\" or GenreName = \""+GenreChoice[1]+"\" " +
                        ") limit 15;  ")) {
            while (rs.next() == true)  {
                ArtistFilter[i] = rs.getString("ArtistName");
                i++;
            }
            for(i = 0; i < ArtistFilter.length; i++){
                System.out.println(ArtistFilter[i]);
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
    }
    /**
     * function that return the artists that song in one of three selected genres
     * @param GenreChoice is the array of selected genre
     */
    public void FilterByThreeGenre(String[] GenreChoice) {
        int i = 0;
        //query for three genre
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct(ArtistName) from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        "(GenreName = \""+GenreChoice[0]+"\" or GenreName = \""+GenreChoice[1]+"\" " +
                        "or GenreName = \""+GenreChoice[2]+"\") limit 15;  ")) {
            while ((rs.next() == true) && (i < 15))  {
                ArtistFilter[i] = rs.getString("ArtistName");
                i++;
            }
            for(i = 0; i < ArtistFilter.length; i++){
                System.out.println(ArtistFilter[i]);
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
    }

}
