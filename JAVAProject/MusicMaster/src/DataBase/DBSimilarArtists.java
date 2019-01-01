package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSimilarArtists {
    private String confusionAns [] = new String[10];
    private java.sql.Connection con = DBConnection.getInstance().getConnection();
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
    public String[] CreateConfusionAns (String artistName) {
        int idAnswer = IDRightArtist(artistName);
        int j = 0;
        //search in 1 col
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.ArtistID and ArtistID =" + idAnswer)) {
            while ((rs.next() == true)&&(j < 10)){
                confusionAns[j] = rs.getString("ArtistName");
                System.out.println(confusionAns[j]);
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
                confusionAns[j] = rs.getString("ArtistName");
                System.out.println(confusionAns[j]);
                j++;

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return confusionAns;
    }
}
