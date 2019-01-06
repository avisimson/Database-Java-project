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
     * This function finds similar artists to the given artist by his genre and the chosen genres that are not
     * already in the similarArtist list.
     * @param artist - given artist
     * @param artists - other similar artists
     * @param genres - the chosen genres by the player
     * @return a list of similar artists
     */
    public List<Artist> FilterArtistDifferent(Artist artist,List<Artist> artists,List<Genre> genres) {
        List<Artist> list =  new LinkedList<>();
        String artistsId = "";
        String genresId = "";
        String artistId = String.valueOf(artist.getArtistId());

        // create string for genres
        for(int j = 0; j < genres.size(); j++) {
            if ( j != 0 ){
                genresId += ",";
            }
            genresId += genres.get(j).getGenreId();
        }

        //create string for other artists id
        for(int j = 0; j < artists.size(); j++) {

            if ( j != 0 ){
                artistsId += ",";
            }
            artistsId += artists.get(j).getArtistId();
        }
        String str = "where artistid not in("+artistsId+")";
        if(artists.size() == 0 ){
            str = "";
        }

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct artistid,ArtistName from artists,(select distinct artistid as newar from genreartists as a,"+
                                "(select distinct genreID as newgen from genreartists  where ArtistID="+artistId+ " and " +
                                "genreID in("+genresId+")) as b) as c " +str + "LIMIT 3")) {

            while (rs.next() && list.size() <3){
                list.add( new Artist(rs.getInt("ArtistID"),rs.getString("ArtistName")
                        ,-1)) ;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return list;
    }



    /**
     * function that execute query that return list of artist in this genres
     * This function gets a list of genres, executes query and returns a list of artists
     * that sings in those genres.
     * @param genreNames is the list of selected genres
     * @return list of artist that sings in this genres
     */
    public List<Artist> FilterArtistByGenre(List<Genre> genreNames) {
        int i = 0;

        // create string
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

        // execute query
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
     * This function creates a list of  different confusion ans (according to table "SimilarArtists")
     * @param artist is the artist of current song
     * @return the list of the similar artists
     */
    public List<Artist> CreateConfusionAns (Artist artist) {
        confusionArtist.clear();
        int idAnswer = artist.getArtistId();
        System.out.println(idAnswer);
        int j = 0;
        System.out.println("--------------ConfuseAns---------------");
        //search in the first col
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
        //search in the second col
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
