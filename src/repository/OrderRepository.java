package repository;

import config.DatabaseConnection;
import model.Order;
import model.OrderItem;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    public Order getFullOrderDescription(int orderId) {
        String query = "SELECT o.id, o.user_id, u.username, oi.product_id, oi.quantity, oi.price " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "JOIN order_items oi ON o.id = oi.order_id " +
                "WHERE o.id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            List<OrderItem> orderItems = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                if (order == null) {
                    order = new Order(
                            resultSet.getInt("id"),
                            new User(resultSet.getInt("user_id"), resultSet.getString("username"), null, null)
                    );
                }
                orderItems.add(new OrderItem(
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price")
                ));
            }
            if (order != null) {
                order.setOrderItems(orderItems);
            }
            return order;
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении данных заказа: " + e.getMessage());
        }
        return null;
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, user_id, order_date FROM orders WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt("id"),
                        new User(resultSet.getInt("user_id"), null, null, null),
                        resultSet.getDate("order_date")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при получении истории заказов: " + e.getMessage());
        }
        return orders;
    }
}