package repository;

import model.User;

import java.sql.*;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
        private final List<User> users = new ArrayList<>();

        public void addUser(User user) {
            users.add(user);
        }

        public List<User> getAllUsers() {
            return new ArrayList<>(users);
        }

        public Optional<User> getUserById(int id) {
            return users.stream().filter(user -> user.getId() == id).findFirst();
        }
    }
