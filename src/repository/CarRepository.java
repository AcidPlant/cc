package repository;

import config.DatabaseConnection;
import model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    public void addCar(Car car) {
        String query = "INSERT INTO cars (model, category, price, year) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getModel());
            statement.setString(2, car.getCategory());
            statement.setDouble(3, car.getPrice());
            statement.setInt(4, car.getYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при добавлении автомобиля: " + e.getMessage());
        }
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("model"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("year")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении списка автомобилей: " + e.getMessage());
        }
        return cars;
    }

    public Car getCarById(int id) {
        String query = "SELECT * FROM cars WHERE id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("model"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("year")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении автомобиля: " + e.getMessage());
        }
        return null;
    }

    public void deleteCar(int id) {
        String query = "DELETE FROM cars WHERE id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("✅ Автомобиль успешно удален.");
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при удалении автомобиля: " + e.getMessage());
        }
    }
}