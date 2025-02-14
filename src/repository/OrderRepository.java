package repository;

import model.Car;
import src.model.Order;
import model.OrderItem;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public Optional<Order> getOrderById(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Получение информации о заказе
            PreparedStatement orderStmt = connection.prepareStatement(
                    "SELECT o.id, u.id as user_id, u.username, u.role, o.created_at " +
                            "FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?");
            orderStmt.setInt(1, id);
            ResultSet orderResult = orderStmt.executeQuery();

            if (!orderResult.next()) return Optional.empty();

            User user = new User(orderResult.getInt("user_id"),
                    orderResult.getString("username"),
                    orderResult.getString("role"));

            List<OrderItem> items = new ArrayList<>();

            // Получение позиций заказа
            PreparedStatement itemsStmt = connection.prepareStatement(
                    "SELECT c.id, c.model, c.category, c.year, c.price, oi.quantity " +
                            "FROM order_items oi JOIN cars c ON oi.car_id = c.id WHERE oi.order_id = ?");
            itemsStmt.setInt(1, id);
            ResultSet itemsResult = itemsStmt.executeQuery();

            while (itemsResult.next()) {
                Car car = new Car(
                        itemsResult.getInt("id"),
                        itemsResult.getString("model"),
                        itemsResult.getString("category"),
                        itemsResult.getInt("year"),
                        itemsResult.getDouble("price"));
                items.add(new OrderItem(car, itemsResult.getInt("quantity")));
            }

            return Optional.of(new Order(
                    orderResult.getInt("id"),
                    user,
                    items,
                    orderResult.getTimestamp("created_at").toLocalDateTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
