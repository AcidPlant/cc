package src.model;

import model.OrderItem;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private User user;
    private List<OrderItem> items;
    private LocalDateTime createdAt;

    public Order(int id, User user, List<OrderItem> items, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "📦 Order{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                ", createdAt=" + createdAt +
                '}';
    }
}
