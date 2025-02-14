package repository;

import config.DatabaseConnection;
import model.Order;
import model.OrderItem;
import model.User;

import java.sql.*;
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
                order.setOrderItems(orderItems); // Убедитесь, что orderItems устанавливается
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


    public boolean createOrder(int userId, List<OrderItem> orderItems) {
        String orderQuery = "INSERT INTO orders (user_id, order_date) VALUES (?, ?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false); // Начало транзакции

            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement orderItemStatement = connection.prepareStatement(orderItemQuery)) {

                // Вставка заказа
                orderStatement.setInt(1, userId);
                orderStatement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                orderStatement.executeUpdate();

                // Получение ID созданного заказа
                int orderId;
                try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("❌ Ошибка при создании заказа: не удалось получить ID.");
                    }
                }

                // Вставка элементов заказа
                for (OrderItem item : orderItems) {
                    orderItemStatement.setInt(1, orderId);
                    orderItemStatement.setInt(2, item.getProductId());
                    orderItemStatement.setInt(3, item.getQuantity());
                    orderItemStatement.setDouble(4, item.getPrice());
                    orderItemStatement.addBatch();
                }
                orderItemStatement.executeBatch();

                connection.commit(); // Завершение транзакции
                return true;
            } catch (SQLException e) {
                connection.rollback(); // Откат транзакции в случае ошибки
                System.err.println("❌ Ошибка при создании заказа: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка при создании заказа: " + e.getMessage());
            return false;
        }
    }
}