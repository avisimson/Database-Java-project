package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class DBSongs {
    private String SongFilter [] = new String[5];
    private java.sql.Connection con = DBConnection.getInstance().conn;
    /**
     * function that return songs of random Artist.
     * @param ArtistFilter is array of artists.
     * @return the songs of random Artist.
     */
    public String[] FilterSong(String[] ArtistFilter) {
        Random rand = new Random();
        int i = 0;
        //choose random artist from the list.
        int artistFilter = rand.nextInt(ArtistFilter.length - 1);
        //query that return the 5 songs of random artist
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select Title from songinfo,artists where artists.ArtistID = songinfo.ArtistID and " +
                        "artists.ArtistName = \"" + ArtistFilter[artistFilter] + "\"")) {
            while ((rs.next() == true) && (i < 5)) {
                SongFilter[i] = rs.getString("Title");
                System.out.println(SongFilter[i]);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return SongFilter;
    }
    /**
     * function that return specific song of artist in order to play in background
     * @return random song.
     */
    public String FilterSpesificSong() {
        Random rand = new Random();
        //choose random song from the list.
        int songFilter = rand.nextInt(SongFilter.length - 1);
        //check if exist this num song , else return the second song
        if (SongFilter[songFilter] == null) {
            //if to current artist don't enough songs
            songFilter = 1;
            System.out.println("HELP");
        }
        return SongFilter[songFilter];
    }
}
