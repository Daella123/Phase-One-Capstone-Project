package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (url == null || user == null) {
            throw new IllegalStateException("Database connection properties (DB_URL, DB_USER) must be set as environment variables");
        }
        return DriverManager.getConnection(url, user, password);
    }
}
