package repository;

import config.DatabaseConnection;
import model.Employee;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    public void addEmployee(Employee employee) {
        String query = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition());
            statement.setBigDecimal(3, employee.getSalary());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении сотрудника: " + e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getBigDecimal("salary")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка сотрудников: " + e.getMessage());
        }
        return employees;
    }
}