package DataBase;

import Logic.Artist;
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
     * @param artistName is the name of artist.
     * @return the songs of random Artist.
     */
    public List<Song> FilterSong(String artistName) {
        int i = 0;
        //query that return the 5 songs of random artist
       // System.out.println("---SongsLists----");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select Title from songinfo,artists where artists.ArtistID = songinfo.ArtistID and " +
                        "artists.ArtistName = \"" + artistName + "\"")) {
            while ((rs.next() == true) && (i < 5)) {
                songFilter.add(i,new Song(-1,rs.getString("Title"),-1,
                        1,-1,2019,-1));
             //   System.out.println(songFilter.get(i).getTitle());
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return songFilter;
    }
    /**
     * function that return specific song of artist in order to play in background
     * @return random song.
     */
    public String FilterSpesificSong(List<Song> Songs) {
        Random rand = new Random();
        //choose random song from the list.
        int songNumFilter = rand.nextInt(Songs.size() - 1);
        //check if exist this num song , else return the second song
        if (Songs.get(songNumFilter).getTitle() == null) {
            //if to current artist don't enough songs
            songNumFilter = 1;
            System.out.println("HELP");
        }
       // System.out.println("---------The Song----------");
       // System.out.println(Songs.get(songNumFilter).getTitle());
        return Songs.get(songNumFilter).getTitle();
    }
}
