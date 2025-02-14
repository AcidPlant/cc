package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123"; // Change this to your actual password
    private static final Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");

            // Load and execute schema.sql
            initializeDatabase();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found. Include it in your classpath!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed!", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private static void initializeDatabase() {
        try {
            String filePath = "src/resources/schema.sql"; // ✅ Set path to schema.sql

            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("❌ schema.sql NOT FOUND! Expected at: " + file.getAbsolutePath());
            }

            System.out.println("✅ Loading schema.sql from: " + file.getAbsolutePath());

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement();
                 BufferedReader reader = new BufferedReader(new FileReader(file))) {

                StringBuilder sql = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sql.append(line).append("\n");
                }

                stmt.execute(sql.toString());
                System.out.println("✅ Database schema loaded successfully.");
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Error loading database schema: " + e.getMessage(), e);
        }
    }
}

