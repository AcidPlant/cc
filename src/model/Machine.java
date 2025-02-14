package model;

public class Machine {
    private int id;
    private String name;
    private int capacity;
    private String status;

    public Machine(int id, String name, int capacity, String status) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }
}