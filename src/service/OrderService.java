package service;

import model.Order;
import repository.OrderRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository = new OrderRepository();

    public Order getFullOrderDescription(int orderId) {
        return orderRepository.getFullOrderDescription(orderId);
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.getOrdersByUserId(userId);
    }
}