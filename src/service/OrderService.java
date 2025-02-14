package service;

import src.model.Order;
import repository.OrderRepository;

import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository = new OrderRepository();

    public void printOrderDetails(int orderId) {
        Optional<Order> order = orderRepository.getOrderById(orderId);
        order.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("❌ Заказ не найден!")
        );
    }
}
