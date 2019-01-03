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
    public Artist FilterArtistDifferent(String nameArtist1, String nameArtist2, String nameArtist3) {
        Artist correctArtist = null;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistID,ArtistName from artists where ArtistID != \""+ nameArtist1 + "\" " +
                        "and ArtistID != \"" + nameArtist2 + "\" and ArtistID != \"" + nameArtist3 + "\" limit 1")) {
            while (rs.next() == true){
                correctArtist = new Artist(rs.getInt("ArtistID"),rs.getString("ArtistName")
                        ,-1) ;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return correctArtist;
    }
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

    public Artist FilterOneArtist(List<Artist> artistFilter) {
        Random rand = new Random();
        int i = 0;
        //choose random artist from the list.
        int artistNumFilter = rand.nextInt(artistFilter.size());
        System.out.println("-------The Artist-----------");
        System.out.println(artistFilter.get(artistNumFilter).getArtistName());
        return artistFilter.get(artistNumFilter);
    }
    /**
     * function that return the artists that song in selected genre
     * @param genreChoice is the array of selected genre
     */
    public void FilterByGenre(List<Genre> genreChoice) {
        int i = 0;
        String genreNumberQuery = "(";
        for (int j = 0; j < genreChoice.size(); j++) {
            if (j != 0) {
                genreNumberQuery += " or ";
            }
            genreNumberQuery += "GenreName = \"";
            genreNumberQuery += genreChoice.get(j).getGenreName();
            genreNumberQuery += "\"";
        }
        genreNumberQuery += ")";
        //query genre
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct(ArtistName),artists.ArtistID from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        genreNumberQuery + "limit 15;")) {
            while (rs.next()) {
                artistFilter.add(i, new Artist(rs.getInt("ArtistID")
                        ,rs.getString("ArtistName"), -1));
                i++;
            }
            System.out.println("-----ArtistList------");
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
     * @param artistName is the artist of this song
     * @return the list of artist that similar to artist of this song
     */
    public List<Artist> CreateConfusionAns (String artistName) {
        confusionArtist.clear();
        int idAnswer = IDRightArtist(artistName).getArtistId();
        System.out.println(idAnswer);
        int j = 0;
        System.out.println("--------------ConfuseAns---------------");
        //search in 1 col
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.similarToArtistID " +
                        "and similarArtists.ArtistID =" + idAnswer)) {
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
