package repository;
import model.CarProducingMachine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConnection;

public class CarProducingMachineRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addMachine(CarProducingMachine machine) {
        String sql = "INSERT INTO car_producing_machines (name, type, capacity, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, machine.getName());
            pstmt.setString(2, machine.getModel());
            pstmt.setInt(3, machine.getCapacity());
            pstmt.setString(4, machine.getStatus());
            pstmt.executeUpdate();
            System.out.println("✅ Машина добавлена: " + machine);
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении машины: " + e.getMessage());
        }
    }

    public List<CarProducingMachine> getAllMachines() {
        List<CarProducingMachine> machines = new ArrayList<>();
        String sql = "SELECT * FROM car_producing_machines";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                machines.add(new CarProducingMachine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("capacity"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка машин: " + e.getMessage());
        }
        return machines;
    }
}
