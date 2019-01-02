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


    public String FilterArtistDifferent(String nameAritst1, String nameArtist2, String nameArtist3) {
        String completeLastAns = "";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from artists where ArtistID != \""+ nameAritst1 + "\" " +
                        "and ArtistID != \"" + nameArtist2 + "\" and ArtistID != \"" + nameArtist3 + "\" limit 1")) {
            while (rs.next() == true){
                completeLastAns = rs.getString("ArtistName");
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return completeLastAns;
    }
    public String FilterArtistDifferentTwice(String nameArtist1,String nameArtist2) {
        String completeLastAns = "";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from artists where ArtistID != \""+ nameArtist1 + "\" " +
                        "and ArtistID != \"" + nameArtist2 + "\" limit 1")) {
            while (rs.next() == true){
                completeLastAns = rs.getString("ArtistName");
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return completeLastAns;
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

    public String FilterOneArtist(List<Artist> artistFilter) {
        Random rand = new Random();
        int i = 0;
        //choose random artist from the list.
        int artistNumFilter = rand.nextInt(artistFilter.size() - 1);
        System.out.println("-------The Artist-----------");
        System.out.println(artistFilter.get(artistNumFilter).getArtistName());
        return artistFilter.get(artistNumFilter).getArtistName();
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
                ("select distinct(ArtistName) from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        genreNumberQuery + "limit 15;")) {
            while (rs.next()) {
                artistFilter.add(i, new Artist(-1,rs.getString("ArtistName"), -1));
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
        confusionArtist.clear();
        int idAnswer = IDRightArtist(artistName);
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
