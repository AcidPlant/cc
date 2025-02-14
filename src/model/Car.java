package model;


public class Car {
    private int id;
    private String model;
    private String category;
    private int year;
    private double price;

    // Конструктор с параметрами
    public Car(int id, String model, String category, int year, double price) {
        this.id = id;
        this.model = model;
        this.category = category;
        this.year = year;
        this.price = price;
    }

    // Конструктор без параметров (нужен для ORM и JSON)
    public Car() {}

    // Геттеры
    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "🚗 Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", category='" + category + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
}

