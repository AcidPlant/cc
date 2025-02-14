package Controllers;

import model.Order;
import model.OrderItem;
import service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService orderService = new OrderService();

    public Order getFullOrderDescription(int orderId) {
        return orderService.getFullOrderDescription(orderId);
    }
    public boolean createOrder(int userId, List<OrderItem> orderItems) {
        return orderService.createOrder(userId, orderItems);
    }
    public void viewOrderHistory(int userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            System.out.println("❌ Заказы не найдены.");
        } else {
            System.out.println("\n📦 История заказов:");
            orders.forEach(order -> {
                System.out.println("Заказ #" + order.getId() + ", Дата: " + order.getOrderDate());
                List<OrderItem> orderItems = order.getOrderItems();
                if (orderItems != null) { // Проверка на null
                    orderItems.forEach(item ->
                            System.out.println("  Продукт ID: " + item.getProductId() + ", Количество: " + item.getQuantity() + ", Цена: " + item.getPrice())
                    );
                } else {
                    System.out.println("  ❌ Элементы заказа отсутствуют.");
                }
            });
        }
    }
}