package config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;




public class DatabaseInitializer {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String sql = new String(Files.readAllBytes(Paths.get("src/main/resources/schema.sql")));
            statement.execute(sql);

            System.out.println("✅ База данных загружена из `schema.sql`!");
        } catch (SQLException | IOException e) {
            System.err.println("❌ Ошибка при загрузке базы данных: " + e.getMessage());
        }
    }
}