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
            String username = "postgres"; // Замените на ваше имя пользователя
            String password = "123"; // Замените на ваш пароль
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

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres"; // Замените на ваше имя пользователя
            String password = "123";
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database connection: " + e.getMessage());
        }
    }
}