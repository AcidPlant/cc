package model;

public class OrderItem {
    private Car car;
    private int quantity;

    public OrderItem(Car car, int quantity) {
        this.car = car;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "🛒 OrderItem{" +
                "car=" + car +
                ", quantity=" + quantity +
                '}';
    }
}
