package main.java.com.ismail.MicroCreditScoringSystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class ConnectionDB {
    private String url = "jdbc:mariadb://localhost:3306/micro_credit";
    private String usertName = "ismail";
    private String pass = "";

    public static ConnectionDB instance = null;
    private Connection connection = null;

    public static ConnectionDB getInstance() {
        if (instance == null)
            instance = new ConnectionDB();
        return instance;
    }

    private ConnectionDB() {
        try {
            connection = DriverManager.getConnection(url, usertName, pass);
         } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}


