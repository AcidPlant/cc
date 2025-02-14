package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private User user;
    private List<OrderItem> orderItems = new ArrayList<>(); // Инициализация списка
    private Date orderDate;

    public Order(int id, User user) {
        this.id = id;
        this.user = user;
    }

    public Order(int id, User user, Date orderDate) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}