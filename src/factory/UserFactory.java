package factory;

import model.User;
import model.Admin;
import model.Customer;

public class UserFactory {
    public static User createUser(int id, String username, String password, String role) {
        if ("ADMIN".equals(role)) {
            return new Admin(id, username, password, role);
        } else if ("CUSTOMER".equals(role)) {
            return new Customer(id, username, password, role);
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
    }
}
//Not needed