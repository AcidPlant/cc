package repository;

import model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CarRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addCar(Car car) {
        String sql = "INSERT INTO cars (model, category, year, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, car.getModel());
            stmt.setString(2, car.getCategory());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPrice());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                car.setId(keys.getInt(1));
            }

            System.out.println("✅ Машина успешно добавлена: " + car);
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении машины: " + e.getMessage());
        }
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("category"),
                        rs.getInt("year"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка машин: " + e.getMessage());
        }
        return cars;
    }

    public Optional<Car> getCarById(int id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Car(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("category"),
                        rs.getInt("year"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при поиске машины: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean deleteCar(int id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении машины: " + e.getMessage());
            return false;
        }
    }
}
