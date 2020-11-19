package com.library.db;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
//import com.mysql.jdbc.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public final class DBConnection {
    private static Connection connection;
    private static final Path path = Paths.get("connection.properties");
    private static String url;
    private static String userName;
    private static String password;
    private static DBConnection instance = null;

    public static DBConnection getInstance() {
//        try {
//            DriverManager.registerDriver(new SQLServerDriver());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    static {
//        System.out.println("Inside static block");
        connect();
//        System.out.println("URL: " + url);
    }

    private static void setConnectionUrl() {
        try (InputStream in = new FileInputStream("C:\\Users\\elen.khachatryan\\IdeaProjects\\Library\\src\\main\\resources\\connection.properties")) {
//            System.out.println("Inside properties.");
            SQLServerDriver.register();
            Properties prop = new Properties();
            prop.load(in);
            url = prop.getProperty("url");
            userName = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void connect() {
        setConnectionUrl();
        try {
            connection = DriverManager.getConnection(url, userName, password);
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "kkk434544");
//        (url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet passQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        return result;
    }
}
