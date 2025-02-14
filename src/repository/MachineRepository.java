package repository;

import config.DatabaseConnection;
import model.Machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineRepository {
    public void addMachine(Machine machine) {
        String query = "INSERT INTO machines (name, capacity, status) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, machine.getName());
            statement.setInt(2, machine.getCapacity());
            statement.setString(3, machine.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении машины: " + e.getMessage());
        }
    }

    public List<Machine> getAllMachines() {
        List<Machine> machines = new ArrayList<>();
        String query = "SELECT * FROM machines";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                machines.add(new Machine(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("capacity"),
                        resultSet.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка машин: " + e.getMessage());
        }
        return machines;
    }
}