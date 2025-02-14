package repository;

import config.DatabaseConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении пользователя: " + e.getMessage());
        }
        return null;
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении пользователя: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении пользователя: " + e.getMessage());
        }
        return false;
    }

    public boolean updateUserRole(String username, String newRole) {
        String query = "UPDATE users SET role = ? WHERE username = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newRole);
            statement.setString(2, username);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при изменении роли пользователя: " + e.getMessage());
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка пользователей: " + e.getMessage());
        }
        return users;
    }
}