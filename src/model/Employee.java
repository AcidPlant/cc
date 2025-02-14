package model;
import java.math.BigDecimal;

public class Employee {
    private int id;
    private String name;
    private String position;
    private BigDecimal salary;

    // Конструктор с параметрами
    public Employee(int id, String name, String position, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Конструктор без параметров (полезен для ORM, JSON)
    public Employee() {}

    // Геттеры
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

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', position='" + position + "', salary=" + salary + "}";
    }
}
