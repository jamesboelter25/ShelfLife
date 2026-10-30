package com.shelflife;

import java.sql.Connection;
import java.sql.DriverManager;

// this is how I connect to my MySQL database
// I didn't put my password in here. it comes from environment variables
// (DB_URL, DB_USER, DB_PASSWORD) because we learned not to put passwords in the code
public class DBConnection {

    public static Connection getConnection() throws java.sql.SQLException {

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        // if I didn't set them, use my normal local settings
        if (url == null) {
            url = "jdbc:mysql://localhost:3306/shelflife";
        }
        if (user == null) {
            user = "root";
        }
        if (password == null) {
            password = "";
        }

        // I have to load the mysql driver first or Tomcat can't find it
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find the MySQL driver!!");
            e.printStackTrace();
        }

        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
