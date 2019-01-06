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
     * singleton function
     * @return the instance of DBconnection
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
            instance.openConnection();
        }
        return instance;
    }

    /**
     * function that get the state of connection to DB
     * @return the state of connection to DB
     */
    public java.sql.Connection getConnection() {
        return conn;
    }
    /**
     *
     * @return true if the connection was successfully set
     */
    public boolean openConnection() {

        System.out.print("Trying to connect... ");
        //reading the details of url, username and password of my schema from configuration connection file
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
}
