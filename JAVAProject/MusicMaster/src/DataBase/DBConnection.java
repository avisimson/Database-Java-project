package DataBase;

import javafx.scene.control.CheckBox;

import java.sql.*;
import java.util.Random;

public class DBConnection {
    java.sql.Connection conn; // DB connection
    String ArtistFilter[] = new String[15];
    String SongFilter [] = new String[5];
    /**
     *
     * @return true if the connection was successfully set
     */
    public boolean openConnection() {

        System.out.print("Trying to connect... ");

        // creating the connection. Parameters should be taken from config file.
        String host = "localhost";
        String port = "3306";
        String schema = "musicmaster";
        String user = "root"; //username
        String password = "LINOY1234c"; //password to mysql

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + schema, user, password);
        } catch (SQLException e) {
            System.out.println("Unable to connect - " + e.getMessage());
            conn = null;
            return false;
        }
        System.out.println("Connected!");
        return true;
    }

    /**
     * close the connection
     */
    public void closeConnection() {
        // closing the connection
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Unable to close the connection - " + e.getMessage());
        }

    }
    /**
     * Shows executeQuery
     */
    public void GenreQuery(String[] GenreName) {
        int i = 0;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery
                ("select distinct(ArtistName) from artists,genre,genreartists " +
                        "where artists.ArtistID = genreArtists.ArtistID and genreartists.GenreID = genre.GenreID and " +
                        "(GenreName = \""+GenreName[0]+"\" or GenreName = \""+GenreName[1]+"\" " +
                        "or GenreName = \""+GenreName[2]+"\") limit 15;  ")) {
            while ((rs.next() == true) && (i < 15))  {
                ArtistFilter[i] = rs.getString("ArtistName");
                i++;
            }
            for(i = 0; i < ArtistFilter.length; i++){
                System.out.println(ArtistFilter[i]);
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
    }
    /**
     * create GenreList
     */
    public CheckBox[] GenreList() {
        int i = 0;
        CheckBox GenreList[] = new CheckBox[30];
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery
                ("select GenreName from genre")) {

            while (rs.next() == true){
                GenreList[i] = new CheckBox(rs.getString("GenreName"));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        return GenreList;
    }
    public String FilterSong() {
        Random rand = new Random();
        int i = 0;
        int artistFilter = rand.nextInt(ArtistFilter.length - 1);
        //  while(ArtistFilter[artistFilter] == null){
        //      System.out.println("HELP");
        //      artistFilter = rand.nextInt(ArtistFilter.length - 1);
        //  }
        System.out.println(artistFilter);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery
                ("select Title from songinfo,artists where artists.ArtistID = songinfo.ArtistID and " +
                        "artists.ArtistName = \""+ ArtistFilter[artistFilter]+ "\"")) {
            while ((rs.next() == true) && (i < 5)){
                SongFilter[i] = rs.getString("Title");
                System.out.println(SongFilter[i]);
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        int songFilter = rand.nextInt(SongFilter.length - 1);
        if(SongFilter[songFilter] == null) {
            //if to current artist don't enough songs
            songFilter = 1;
            System.out.println("HELP");
        }
        System.out.println(songFilter);
        System.out.println(SongFilter[songFilter]);
        //create four answer
        String curanswer = ArtistFilter[artistFilter];
        int idanswer = 0;
        //query for current answer
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistID from artists where ArtistName = \""+ ArtistFilter[artistFilter] + "\"")) {
            while (rs.next() == true){
                idanswer = rs.getInt("ArtistID");
                System.out.println(curanswer);
                System.out.println(idanswer);
            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        //query for confusion answers
        String confusionAns[] = new String[10];
        int j = 0;
        //search in 1 col
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.ArtistID and ArtistID =" + idanswer)) {
            while ((rs.next() == true)&&(j < 10)){
                confusionAns[j] = rs.getString("ArtistName");
                System.out.println(confusionAns[j]);
                j++;

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        //search in 2 col
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery
                ("select ArtistName from similarartists,artists " +
                        "where artists.ArtistID = similarArtists.ArtistID and similarToArtistID =" + idanswer)) {
            while ((rs.next() == true)&&(j < 10)){
                confusionAns[j] = rs.getString("ArtistName");
                System.out.println(confusionAns[j]);
                j++;

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        //choose 3 ans
        String ans1,ans2,ans3;
        int x = rand.nextInt(confusionAns.length - 1);
        while(confusionAns[x] == null)
            x = rand.nextInt(confusionAns.length - 1);
        ans1 = confusionAns[x];
        System.out.println("the wrong!!!");
        System.out.println(ans1);
        int y = rand.nextInt(confusionAns.length - 1);
        while((x == y) || ((confusionAns[y] == null)))
            y = rand.nextInt(confusionAns.length - 1);
        ans2 = confusionAns[y];
        System.out.println(ans2);
        int z = rand.nextInt(confusionAns.length - 1);
        while((z == y) ||(z == x) || (confusionAns[z] == null))
            z = rand.nextInt(confusionAns.length - 1);
        ans3 = confusionAns[z];
        System.out.println(ans3);
        return SongFilter[songFilter];
    }
}
