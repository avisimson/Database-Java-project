package DataBase;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Random;

public class DBConnection {
    private java.sql.Connection conn; // DB connection
    private static DBConnection instance;

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
        String url = null , username = null , password = null;
        BufferedReader br;
        String fileName = "src/DataBase/dbconnectionconfig.txt";
        try {
            br = new BufferedReader(new FileReader(fileName));
            //the parameters for connection to DB.
            url = br.readLine().replace("url: ","");
            username = br.readLine().replace("username: ","");
            password = br.readLine().replace("password: ","");
        } catch (Exception e) {
            System.out.println("couldn't read from configuration file - " + e.getMessage());
        }
        // creating the connection. Parameters should be taken from config file.
        String host = "localhost";
        String port = "3306";

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + url, username, password);
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
     * Shows executeUpdate
     */
    public void demoExecuteUpdate() {
        int result;

        try (Statement stmt = conn.createStatement();) {
            result = stmt.executeUpdate("INSERT INTO highscores(UserName, Score) " + "VALUES('Yakir', 2500)");
           // result = stmt.executeUpdate("INSERT INTO demo(fname, lname) " + "VALUES('Ryan','Gosling')");
            // result = stmt.executeUpdate("DELETE FROM demo");
            System.out.println("Success - executeUpdate, result = " + result);

        } catch (SQLException e) {
            System.out.println("ERROR executeUpdate - " + e.getMessage());
        }
    }
}
