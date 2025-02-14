package model;

import java.math.BigDecimal;

public class Employee {
    private int id;
    private String name;
    private String position;
    private BigDecimal salary;

    public Employee(int id, String name, String position, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}