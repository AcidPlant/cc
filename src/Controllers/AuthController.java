package Controllers;

import config.DatabaseConnection;
import model.User;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AuthController {
    private final UserService userService = new UserService();

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Имя пользователя не может быть пустым.");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("❌ Пароль не может быть пустым.");
            return null;
        }

        User user = userService.login(username, password);
        if (user != null) {
            System.out.println("✅ Вход выполнен успешно.");
        } else {
            System.out.println("❌ Неверное имя пользователя или пароль.");
        }
        return user;
    }

    public void register(String username, String password, String role) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.err.println("❌ Имя пользователя и пароль не могут быть пустыми.");
            return;
        }

        if (!"ADMIN".equals(role) && !"CUSTOMER".equals(role)) {
            System.err.println("❌ Некорректная роль. Допустимые значения: ADMIN, CUSTOMER.");
            return;
        }

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {

            statement.setString(1, username);
            statement.setString(2, password); // В реальном приложении пароль должен быть хэширован
            statement.setString(3, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Пользователь успешно зарегистрирован.");
            } else {
                System.err.println("❌ Ошибка при регистрации пользователя.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при регистрации: " + e.getMessage());
        }
    }



    public boolean deleteUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Имя пользователя не может быть пустым.");
            return false;
        }

        boolean success = userService.deleteUser(username);
        if (success) {
            System.out.println("✅ Пользователь " + username + " успешно удален.");
        } else {
            System.out.println("❌ Пользователь с таким именем не найден.");
        }
        return success;
    }

    public boolean updateUserRole(String username, String newRole) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("❌ Имя пользователя не может быть пустым.");
            return false;
        }

        if (!"ADMIN".equals(newRole) && !"CUSTOMER".equals(newRole)) {
            System.out.println("❌ Некорректная роль. Допустимые значения: ADMIN, CUSTOMER.");
            return false;
        }

        boolean success = userService.updateUserRole(username, newRole);
        if (success) {
            System.out.println("✅ Роль пользователя " + username + " успешно изменена на " + newRole + ".");
        } else {
            System.out.println("❌ Пользователь с таким именем не найден.");
        }
        return success;
    }

    public void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("❌ Пользователи не найдены.");
        } else {
            System.out.println("\n👥 Список всех пользователей:");
            users.forEach(user ->
                    System.out.println("ID: " + user.getId() + ", Имя: " + user.getUsername() + ", Роль: " + user.getRole())
            );
        }
    }
}