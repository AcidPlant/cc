package service;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean register(String username, String password, String role) {
        User user = new User(0, username, password, role);
        return userRepository.addUser(user);
    }

    public boolean deleteUser(String username) {
        return userRepository.deleteUser(username);
    }

    public boolean updateUserRole(String username, String newRole) {
        return userRepository.updateUserRole(username, newRole);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}