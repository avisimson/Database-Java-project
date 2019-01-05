package DataBase;

import Logic.Artist;
import Logic.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DBArtists {
    private List<Artist> artistFilter = new LinkedList<>();
    private java.sql.Connection con = DBConnection.getInstance().getConnection();
    private List<Artist> confusionArtist = new LinkedList<>();

    /**
     * function that return artist that not equal to exist artists in the question
     * @param nameArtist1 is the name of first artist - can't be null
     * @param nameArtist2 is the name of second artist - can be null
     * @param nameArtist3 is the name of third artist - can be null
     * @return the correct artist
     */
    public Artist FilterArtistDifferent(Artist nameArtist1, Artist nameArtist2, Artist nameArtist3) {
        Artist correctArtist = null;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct ArtistID,ArtistName from artists where ArtistID != \""+ nameArtist1.getArtistId() + "\" " +
                        "and ArtistID != \"" + nameArtist2.getArtistId() + "\" and ArtistID != \"" + nameArtist3.getArtistId() + "\" limit 1")) {
            while (rs.next()){
                correctArtist = new Artist(rs.getInt("ArtistID"),rs.getString("ArtistName")
                        ,-1) ;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return correctArtist;
    }

    /**
     *
     * function that execute query that return list of artist in this genres
     *
     * @param genreNames is the array of selected genres
     * @return the list of artist that songs in this genres
     */
    public List<Artist> FilterArtistByGenre(List<Genre> genreNames) {
        int i = 0;
        String genreNumberQuery = "(";
        for (int j = 0; j < genreNames.size(); j++) {
            if (j != 0) {
                genreNumberQuery += " or ";
            }
            genreNumberQuery += "GenreName = \"";
            genreNumberQuery += genreNames.get(j).getGenreName();
            genreNumberQuery += "\"";
        }
        genreNumberQuery += ")";
        //query genre
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct artists.ArtistID, ArtistName from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        genreNumberQuery + "limit 50;")) {
            while (rs.next()) {
                artistFilter.add(i, new Artist(rs.getInt("ArtistID")
                        ,rs.getString("ArtistName"), -1));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return artistFilter;
    }


    /**
     * function that return the id of artist
     * @param artistName is the name of the artist of the current song in run
     * @return the id Artist of this artist
     */
    public Artist IDRightArtist(String artistName) {
        Artist correctArtist = null;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistID,ArtistName from artists where ArtistName = \""+ artistName + "\"")) {
            while (rs.next() == true){
                correctArtist = new Artist(rs.getInt("ArtistID"),rs.getString("ArtistName")
                        ,-1);
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return correctArtist;
    }
    /**
     * function that create list of  different confusion ans (according to table "SimilarArtists"
     * @param artist is the artist of this song
     * @return the list of artist that similar to artist of this song
     */
    public List<Artist> CreateConfusionAns (Artist artist) {
        confusionArtist.clear();
        int idAnswer = artist.getArtistId();
        System.out.println(idAnswer);
        int j = 0;
        System.out.println("--------------ConfuseAns---------------");
        //search in 1 col
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select  artists.ArtistID, ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.similarToArtistID " +
                        "and similarArtists.ArtistID =" + artist.getArtistId() +" limit 10")) {
            while (rs.next()){
                confusionArtist.add(j, new Artist(rs.getInt("ArtistID"),rs.getString("ArtistName"), -1));
                j++;

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        //search in 2 col
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select artists.ArtistID, ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.ArtistID " +
                        "and similarToArtistID =" + artist.getArtistId() +" limit 10")) {
            while (rs.next()){
                confusionArtist.add(j, new Artist(rs.getInt("ArtistID"),rs.getString("ArtistName"), -1));
                j++;

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return confusionArtist;
    }

}
