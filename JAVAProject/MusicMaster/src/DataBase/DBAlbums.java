package DataBase;

import Logic.Album;
import Logic.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DBAlbums {

    private java.sql.Connection con = DBConnection.getInstance().getConnection();

    public List<Album> getListOfAlbumsWithMoreThen3Songs() {
        List<Album> albumList =  new LinkedList<>();
        int i = 0;

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("SELECT distinct songinfo.AlbumID,albums.AlbumName FROM musicmaster.songinfo,albums," +
                        " (SELECT albumId,count(SongID) FROM musicmaster.songinfo group by albumId " +
                        " HAVING count(SongID) > 3 order by count(SongID) asc) as b where " +
                        " b.albumId = songinfo.AlbumID and songinfo.AlbumID = albums.AlbumID order by songinfo.AlbumID LIMIT 100")) {
            while (rs.next()) {
                albumList.add(i,new Album(rs.getInt("AlbumID"),rs.getString("AlbumName")));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return albumList;
    }
}
