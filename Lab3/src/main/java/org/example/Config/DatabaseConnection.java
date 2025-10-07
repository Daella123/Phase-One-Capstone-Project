package org.example.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (url == null || user == null) {
            throw new IllegalStateException("Environment variables DB_URL and DB_USER must be set");
        }
        return DriverManager.getConnection(url, user, password);
    }
}
