package model;

public class Car {
    private int id;
    private String model;
    private String category;
    private double price;
    private int year;

    public Car(int id, String model, String category, double price, int year) {
        this.id = id;
        this.model = model;
        this.category = category;
        this.price = price;
        this.year = year;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }
}