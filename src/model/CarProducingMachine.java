package model;
public class CarProducingMachine {
    private int id;
    private String name;
    private String model;
    private int capacity;
    private String status;  // Например: "ACTIVE", "INACTIVE", "MAINTENANCE"

    public CarProducingMachine(int id, String name, String model, int capacity, String status) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.capacity = capacity;
        this.status = status;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "🚜 Машина ID: " + id + ", Название: " + name + ", Модель: " + model +
                ", Производительность: " + capacity + " авто/день, Статус: " + status;
    }
}
