package repository;
import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getPosition());
            pstmt.setBigDecimal(3, emp.getSalary());
            pstmt.executeUpdate();
            System.out.println("✅ Сотрудник добавлен: " + emp);
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении сотрудника: " + e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getBigDecimal("salary")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка сотрудников: " + e.getMessage());
        }
        return employees;
    }
}
