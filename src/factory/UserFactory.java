package factory;

import model.User;

public class UserFactory {
    public static User createUser(int id, String username, String role) {
        return new User(id, username, role);
    }
}
