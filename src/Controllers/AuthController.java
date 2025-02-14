package Controllers;

import model.User;
import service.UserService;

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

    public boolean register(String username, String password, String role) {
        if (username == null || username.length() < 3 || !username.matches("[a-zA-Z0-9]+")) {
            System.out.println("❌ Имя пользователя должно содержать не менее 3 символов и состоять только из букв и цифр.");
            return false;
        }

        if (password == null || password.length() < 8 || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
            System.out.println("❌ Пароль должен содержать не менее 8 символов, включая хотя бы одну заглавную букву, одну строчную букву и одну цифру.");
            return false;
        }

        if (!"ADMIN".equals(role) && !"CUSTOMER".equals(role)) {
            System.out.println("❌ Некорректная роль. Допустимые значения: ADMIN, CUSTOMER.");
            return false;
        }

        boolean success = userService.register(username, password, role);
        if (success) {
            System.out.println("✅ Регистрация выполнена успешно.");
        } else {
            System.out.println("❌ Пользователь с таким именем уже существует.");
        }
        return success;
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