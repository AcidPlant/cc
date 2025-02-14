package Controllers;

import model.Order;
import service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService orderService = new OrderService();

    public Order getFullOrderDescription(int orderId) {
        return orderService.getFullOrderDescription(orderId);
    }

    public void viewOrderHistory(int userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            System.out.println("❌ Заказы не найдены.");
        } else {
            System.out.println("\n📦 История заказов:");
            orders.forEach(order -> {
                System.out.println("Заказ #" + order.getId() + ", Дата: " + order.getOrderDate());
                order.getOrderItems().forEach(item ->
                        System.out.println("  Продукт ID: " + item.getProductId() + ", Количество: " + item.getQuantity() + ", Цена: " + item.getPrice())
                );
            });
        }
    }
}