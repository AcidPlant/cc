package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres"; // Replace with your PostgreSQL username
            String password = "123"; // Replace with your PostgreSQL password
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Database connection established.");
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}