package DataBase;

import Logic.Album;
import Logic.Artist;
import Logic.Genre;
import Logic.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that handles all
 * SQL queries that return an song object.
 */
public class DBSongs {

    private java.sql.Connection con = DBConnection.getInstance().getConnection();  // DB connection

    /**
     * This function gets an artist and return his songs.
     * @param artist the artist
     * @return the songs of the artist.
     */
    public List<Song> FilterSong(Artist artist) {
        List<Song> songFilter = new LinkedList<>();
        int i = 0;
        //query that returns 5 songs of the artist
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct SongID,year,Title,EndOfFadeIn from songinfo" +
                        " where ArtistID = " + artist.getArtistId() +" limit 10;")) {
            while (rs.next()) {
                songFilter.add(i,new Song(rs.getInt("SongID"),rs.getString("Title"),-1,
                        artist.getArtistId(),rs.getInt("year"),rs.getFloat("EndOfFadeIn")));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return songFilter;
    }

    /**
     * This function gets a song and a list of chosen genres and creates a list of similar songs by
     * genre and year.
     * @param song - the current song
     * @param genres - the chosen genres by the player
     * @return list of similar songs
     */
    public List<Song> getListOfSimilarSongs(Song song, List<Genre> genres) {
        List<Song> songList =  new LinkedList<>();
        //create string
        String genresId = "";
        for(int j = 0; j < genres.size(); j++) {
            if ( j != 0 ){
                genresId += ",";
            }
            genresId += genres.get(j).getGenreId();
        }
        int i = 0;
        //query returns songs that are from artists that sing in the same genre as the artist of the current song
        // and chosen genres, that are also from the same year as the current song.
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct songid, title,year,artistid from songinfo ," +
                        "(select distinct artistid as newar from genreartists as a" +
                 ",(select distinct genreID as newgen from genreartists where ArtistID=" + song.getArtistId() +
                        " and genreID in("+genresId+ ")) as b" +
                         " where b.newgen=a.genreid) as n" +
                          " where n.newar=songinfo.artistid and year=" + song.getYear() +
                        " and songid != " + song.getSongId() + " order by songid limit 50;")) {
            while (rs.next()) {
                songList.add(i,new Song(rs.getInt("SongID"),rs.getString("Title"),-1,
                        rs.getInt("artistID"),rs.getInt("year"),-1));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return songList;
    }

    /**
     * This function finds similar songs to the given song by his artist genre and the chosen genres that are not
     * already in the similarSong list.
     * @param song - given song
     * @param songs - other similar songs
     * @param genres - the chosen genres by the player
     * @return  a list of similar songs
     */
    public List<Song> getDifferentSongsByGenre(Song song, List<Song> songs, List<Genre> genres) { ;
        List<Song> songList =  new LinkedList<>();
        //create string
        String genresId = "";
        for(int j = 0; j < genres.size(); j++) {
            if ( j != 0 ){
                genresId += ",";
            }
            genresId += genres.get(j).getGenreId();
        }
        String songsId =  "";
        //create string for other song id
        for(int j = 0; j < songs.size(); j++) {

            if ( j != 0 ){
                songsId += ",";
            }
            songsId += songs.get(j).getSongId();
        }
        String str = " and songid not in("+songsId+")";
        if(songs.size() == 0 ){
            str = "";
        }
        int i = 0;
        //query returns songs that are from artists that sing in the same genre as the artist of the current song
        // and chosen genres
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct songid, title,year,artistid from songinfo ," +
                        "(select distinct artistid as newar from genreartists as a" +
                        ",(select distinct genreID as newgen from genreartists where ArtistID=" + song.getArtistId() +
                        " and genreID in("+genresId+ ")) as b" +
                        " where b.newgen=a.genreid) as n" +
                        " where n.newar=songinfo.artistid and songid != "+ song.getSongId() + str + " order by songid limit 50;")) {
            while (rs.next()) {
                songList.add(i,new Song(rs.getInt("SongID"),rs.getString("Title"),-1,
                        rs.getInt("artistID"),rs.getInt("year"),-1));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return songList;
    }

    public List<Song> getListSognsByAlbum(Album album) {
        List<Song> songFilter = new LinkedList<>();

        int i = 0;
        //query that returns 10 songs of the artist
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("SELECT Songid,Title,Year,ArtistID,EndOfFadeIn FROM " +
                        "musicmaster.songinfo where songinfo.AlbumID ="+ album.getAlbumId())) {
            while (rs.next()) {
                songFilter.add(i,new Song(rs.getInt("SongID"),rs.getString("Title"),album.getAlbumId(),
                        rs.getInt("ArtistId"),rs.getInt("year"),rs.getFloat("EndOfFadeIn")));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return songFilter;
    }

}
