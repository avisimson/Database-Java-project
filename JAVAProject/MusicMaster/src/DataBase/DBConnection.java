package DataBase;

import javafx.scene.control.CheckBox;
import java.sql.*;
import java.util.Random;

public class DBConnection {
    private java.sql.Connection conn; // DB connection
    private static DBConnection instance;
    private String ArtistFilter[] = new String[15];
    private String SongFilter [] = new String[5];

    private DBConnection() { }
    /**
     *
     * @return
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
            instance.openConnection();
        }
        return instance;
    }
    public java.sql.Connection getConnection() {
        return conn;
    }
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
        String password = "Rkehat19"; //password to mysql

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

}
