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
    private List<Artist> confusionArtist = new LinkedList<>();
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
    /**
     * function that return the id of artist
     * @param artistName is the name of the artist of the current song in run
     * @return the id Artist of this artist
     */
    public int IDRightArtist(String artistName) {
        int idanswer = 0;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistID from artists where ArtistName = \""+ artistName + "\"")) {
            while (rs.next() == true){
                idanswer = rs.getInt("ArtistID");
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return idanswer;
    }
    /**
     * function that create list of  different confusion ans (according to table "SimilarArtists"
     * @param artistName is the artist of this song
     * @return the list of artist that similar to artist of this song
     */
    public List<Artist> CreateConfusionAns (String artistName) {
        int idAnswer = IDRightArtist(artistName);
        int j = 0;
        //search in 1 col
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.ArtistID and ArtistID =" + idAnswer)) {
            while ((rs.next() == true)&&(j < 10)){
                confusionArtist.add(j, new Artist(-1,rs.getString("ArtistName"), -1));
                System.out.println(confusionArtist.get(j).getArtistName());
                j++;

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        //search in 2 col
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.ArtistID and similarToArtistID =" + idAnswer)) {
            while ((rs.next() == true)&&(j < 10)){
                confusionArtist.add(j, new Artist(-1,rs.getString("ArtistName"), -1));
                System.out.println(confusionArtist.get(j).getArtistName());
                j++;

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return confusionArtist;
    }

}
