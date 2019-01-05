package DataBase;

import Logic.Artist;
import Logic.Genre;
import Logic.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DBSongs {
    private List<Song> songFilter = new LinkedList<>();
    private java.sql.Connection con = DBConnection.getInstance().getConnection();
    /**
     * function that return songs of random Artist.
     * @param artist is the name of artist.
     * @return the songs of random Artist.
     */
    public List<Song> FilterSong(Artist artist) {
        songFilter.clear();
        int i = 0;
        //query that return the 5 songs of random artist
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct SongID,year,Title,EndOfFadeIn from songinfo" +
                        " where ArtistID = " + artist.getArtistId() +" limit 5;")) {
            while (rs.next()) {
                songFilter.add(i,new Song(rs.getInt("SongID"),rs.getString("Title"),-1,
                        artist.getArtistId(),-1,rs.getInt("year"),rs.getFloat("EndOfFadeIn")));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return songFilter;
    }

    public List<Song> getListOfSimilarSongs(Song song, List<Genre> genres) {
        List<Song> songList =  new LinkedList<>();
        String genresId = "";
        for(int j = 0; j < genres.size(); j++) {
            if ( j != 0 ){
                genresId += ",";
            }
            genresId += genres.get(j).getGenreId();
        }
        int i = 0;
        //query that return the 5 songs of random artist
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct songid, title,year,artistid from songinfo ," +
                        "(select distinct artistid as newar from genreartists as a" +
                 ",(select distinct genreID as newgen from genreartists where ArtistID=" + song.getArtistId() +
                        " and genreID in("+genresId+ ")) as b" +
                         " where b.newgen=a.genreid) as n" +
                          " where n.newar=songinfo.artistid and year=(select year from songinfo where" +
                        " SongID= " + song.getSongId() +")" + " order by songid;")) {
            while (rs.next()) {
                songList.add(i,new Song(rs.getInt("SongID"),rs.getString("Title"),-1,
                        rs.getInt("artistID"),-1,rs.getInt("year"),-1));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return songList;
    }

}
